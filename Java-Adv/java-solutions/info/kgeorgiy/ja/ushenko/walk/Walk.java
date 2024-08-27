package info.kgeorgiy.ja.ushenko.walk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Walk {
    private static final int bufferSize = 1024;

    public static void main(String[] args) {
        if (args == null) {
            System.err.println("Arguments should be non-null");
            return;
        }
        if (args.length != 2) {
            System.err.printf("Wrong amount of arguments. %d instead of 2%n", args.length);
            return;
        }
        if (args[0] == null || args[1] == null) {
            System.err.printf("Arguments should be non-null ->%s %s<-%n", args[0], args[1]);
            return;
        }

        if (!createPathIfNeeded(args[1])) return;

        try (BufferedWriter wr = Files.newBufferedWriter(Path.of(args[1]))) {
            try (BufferedReader filesToRead = Files.newBufferedReader(Path.of(args[0]))) {
                String fileName;
                int hash;

                while ((fileName = filesToRead.readLine()) != null) {
                    try (InputStream fileToRead = Files.newInputStream(Path.of(fileName))) {
                        hash = countHash(fileToRead);
                    } catch (final SecurityException | IOException | InvalidPathException e) {
                        hash = 0;
                        System.err.printf("Unable to read %s: %s%n", fileName, e.getMessage());
                    }
                    wr.write(String.format("%08x %s%n", hash, fileName));
                }

                } catch (final SecurityException | IOException | InvalidPathException e) {
                System.err.printf("Unable to read input file: %s%n", e.getMessage());
            }
        } catch (final SecurityException | IOException | InvalidPathException e) {
            System.err.printf("Unable to write output file: %s%n", e.getMessage());
        }
    }

    private static int countHash(InputStream streamReader) throws IOException {
        int hash = 0;
        byte[] currChar = new byte[bufferSize];
        int length;
        while ((length = streamReader.read(currChar)) != -1) {
            for (int i = 0; i < length; i++) {
                hash += currChar[i] & 0xff;
                hash += hash << 10;
                hash ^= hash >>> 6;
            }
        }
        hash += hash << 3;
        hash ^= hash >>> 11;
        hash += hash << 15;

        return hash;
    }

    private static boolean createPathIfNeeded(String sPath) {
        try {
            Path toGo = Paths.get(sPath).getParent();
            if (toGo != null) Files.createDirectories(toGo);
        } catch (final IOException | InvalidPathException e) {
            System.err.printf("Unable to create directories: %s%n", e.getMessage());
            return false;
        }
        return true;
    }
}
