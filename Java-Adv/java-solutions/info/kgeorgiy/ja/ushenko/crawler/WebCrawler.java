package info.kgeorgiy.ja.ushenko.crawler;

import info.kgeorgiy.java.advanced.crawler.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.stream.Collectors;

// :NOTE: javadoc
public class WebCrawler implements NewCrawler {

    private final Downloader downloader;
    private final ExecutorService downloaders;
    private final ExecutorService exctractors;

    /**
    Constructor for the WebCrawler class.

    @param downloader - downloader for the program
    @param amountOfDownloaders - amount of downloaders
    @param amountOfExtractors - amount of extractors
    @param perHost - amount of links per host
     */
    public WebCrawler(Downloader downloader, int amountOfDownloaders, int amountOfExtractors, int perHost) {
        this.downloader = downloader;
        downloaders = Executors.newFixedThreadPool(amountOfDownloaders);
        exctractors = Executors.newFixedThreadPool(amountOfExtractors);
    }
    /**
    Main method for running the program.

    @param args - arguments for the program. Should contain 5 arguments: url, depth, downloaders, extractors, perHost
     */
    public static void main(String[] args) {
        if (args == null || args.length != 5) {
            System.err.println("Incorrect arguments");
        }
        Downloader mainDownloader;
        String url;
        int depth;
        int downloaders;
        int extractors;
        int perHost;
        // :NOTE: add validation
        try {
            url = args[0];
            depth = Integer.parseInt(args[1]);
            downloaders = Integer.parseInt(args[2]);
            extractors = Integer.parseInt(args[3]);
            perHost = Integer.parseInt(args[4]);
        } catch (final NullPointerException | NumberFormatException e) {
            System.err.println("Incorrect arguments");
            return;
        }
        try {
            mainDownloader = new CachingDownloader(1);
        } catch (IOException e) {
            System.err.printf("Unable to create downloader: %s%n", e.getMessage());
            return;
        }
        Result result = new WebCrawler(mainDownloader, downloaders, extractors, perHost).download(url, depth);
        System.out.println("Success: " + result.getDownloaded());
        System.out.println("Errors: " + result.getErrors());
    }
    /**
        {@inheritDoc}
     */
    @Override
    public Result download(String url, int depth, Set<String> excludes) {
        final Phaser phaser = new Phaser(1);
        Set<String> downloaded = ConcurrentHashMap.newKeySet();
        Map<String, IOException> errors = new ConcurrentHashMap<>();
        Set<String> currentLayer = ConcurrentHashMap.newKeySet();
        currentLayer.add(url);
        for (int i = 0; i < depth; i++) {
            final Set<String> nextLayer = ConcurrentHashMap.newKeySet();
            for (String link : currentLayer) {
                if (downloaded.contains(link) || errors.containsKey(link) || excludes.stream().anyMatch(link::contains))
                    continue;
                phaser.register();
                downloaders.submit(new DownloadTask(link, downloaded, nextLayer, excludes, errors, phaser));
            }
            phaser.arriveAndAwaitAdvance();
            currentLayer = ConcurrentHashMap.newKeySet();
            currentLayer.addAll(nextLayer);
        }
        return new Result(
                downloaded.stream()
                        .filter(link -> !errors.containsKey(link))
                        .collect(Collectors.toList()),
                errors
        );
    }

    /**
        {@inheritDoc}
     */
    @Override
    public Result download(String url, int depth) {
        return download(url, depth, new HashSet<>());
    }

    /**
        {@inheritDoc}
     */
    @Override
    public void close() {
        downloaders.close();
        exctractors.close();
    }

    private class DownloadTask implements Runnable {

        private final String link;
        private final Set<String> downloaded;
        private final Set<String> nextLayer;
        private final Set<String> excludes;
        private final Map<String, IOException> errors;
        private final Phaser phaser;

        public DownloadTask(
                String link,
                Set<String> downloaded,
                Set<String> nextLayer,
                Set<String> excludes,
                Map<String, IOException> errors,
                Phaser phaser
        ) {
            this.link = link;
            this.downloaded = downloaded;
            this.nextLayer = nextLayer;
            this.excludes = excludes;
            this.errors = errors;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                Document document = downloader.download(link);
                downloaded.add(link);
                phaser.register();
                exctractors.submit(
                        new ExtractTask(
                                document,
                                nextLayer,
                                excludes,
                                downloaded,
                                errors,
                                link,
                                phaser
                        )
                );
            } catch (IOException e) {
                errors.put(link, e);
            } finally {
                phaser.arriveAndDeregister();
            }
        }
    }

    private static class ExtractTask implements Runnable {

        private final Document document;
        private final Set<String> nextLayer;
        private final Set<String> excludes;
        private final Set<String> downloaded;
        private final Map<String, IOException> errors;
        private final String link;
        private final Phaser phaser;

        public ExtractTask(
                Document document,
                Set<String> nextLayer,
                Set<String> excludes,
                Set<String> downloaded,
                Map<String, IOException> errors,
                String link, Phaser phaser
        ) {
            this.document = document;
            this.nextLayer = nextLayer;
            this.excludes = excludes;
            this.downloaded = downloaded;
            this.errors = errors;
            this.link = link;
            this.phaser = phaser;
        }

        @Override
        public void run() {
            try {
                List<String> extractedLinks = document.extractLinks();
                nextLayer.addAll(
                        // :NOTE: filter
                        extractedLinks.stream()
                                .filter(currLink -> excludes.stream().noneMatch(currLink::contains))
                                .filter(currLink -> !downloaded.contains(currLink))
                                .filter(currlink -> !errors.containsKey(currlink))
                                .toList()
                );
            } catch (IOException e) {
                errors.put(link, e);
            } finally {
                phaser.arriveAndDeregister();
            }
        }
    }
}