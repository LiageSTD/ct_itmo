package info.kgeorgiy.ja.ushenko.iterative;

import info.kgeorgiy.java.advanced.iterative.NewScalarIP;
import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IterativeParallelism implements NewScalarIP {
    private final ParallelMapper parallelMapper;

    public IterativeParallelism(ParallelMapper parallelMapper) {
        this.parallelMapper = parallelMapper;
    }

    private <T, M> Stream<M> mThreadFunc(
            int amountOfThreads,
            int step, List<? extends T> values,
            Function<Stream<? extends T>, M> func
    ) throws InterruptedException {
        List<? extends T> stepList = IntStream.iterate(0, i -> i < values.size(), i -> i + step)
                .mapToObj(values::get)
                .toList();
        int splitSize = Math.min(amountOfThreads, stepList.size());

        List<Stream<? extends T>> splitted = splitOnSublists(stepList, splitSize);

        if (parallelMapper != null) {
            return parallelMapper.map(func, splitted).stream();
        }
        List<M> result = new ArrayList<>(Collections.nCopies(splitted.size(), null));
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < splitted.size(); i++) {
            final int iter = i;
            Thread thread = new Thread(() -> result.set(iter, func.apply(splitted.get(iter))));
            threads.add(thread);
            thread.start();
        }

        joinThreads(threads);
        return result.stream();
    }

    private static void joinThreads(List<Thread> threads) throws InterruptedException {
        InterruptedException exceptions = null;

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (final InterruptedException e) {
                if (exceptions == null) {
                    threads.forEach(Thread::interrupt);
                    exceptions = e;
                } else {
                    exceptions.addSuppressed(e);
                }
            }
        }
        if (exceptions != null) {
            System.err.printf("An error occurred! %n %s", exceptions.getCause());
            throw exceptions;
        }
    }

    private static <T> List<Stream<? extends T>> splitOnSublists(List<? extends T> stepList, int splitSize) {
        int block = stepList.size() / splitSize;
        int lefties = stepList.size() % splitSize;

        List<Stream<? extends T>> splitted = new ArrayList<>();
        int left = 0;
        for (int i = 0; i < splitSize; i++) {
            int right = left + block + (i < lefties ? 1 : 0);
            splitted.add(stepList.subList(left, right).stream());
            left = right;
        }
        return splitted;
    }

    //--------------------------------------ADV---------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T maximum(int threads, List<? extends T> values, Comparator<? super T> comparator, int step)
            throws InterruptedException {
        return mThreadFunc(
                threads,
                step,
                values,
                (stream -> stream.max(comparator).orElseThrow())
        ).max(comparator)
                .orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T minimum(int threads, List<? extends T> values, Comparator<? super T> comparator, int step)
            throws InterruptedException {
        return mThreadFunc(
                threads,
                step,
                values,
                (stream -> stream.min(comparator).orElseThrow())
        ).min(comparator)
                .orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> boolean all(
            int threads, List<? extends T> values, Predicate<? super T> predicate, int step
    ) throws InterruptedException {
        return mThreadFunc(threads, step, values, (stream -> stream.allMatch(predicate)))
                .allMatch(Boolean::booleanValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> boolean any(int threads, List<? extends T> values, Predicate<? super T> predicate, int step)
            throws InterruptedException {
        return mThreadFunc(
                threads,
                step,
                values,
                (stream -> stream.anyMatch(predicate))
        ).anyMatch(Boolean::booleanValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> int count(int threads, List<? extends T> values, Predicate<? super T> predicate, int step)
            throws InterruptedException {
        return mThreadFunc(
                threads,
                step,
                values,
                (stream -> (int) stream.filter(predicate).count())
        ).mapToInt(Integer::intValue)
                .sum();
    }

    //    ---------------------------------------BASE---------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T maximum(int threads, List<? extends T> values, Comparator<? super T> comparator)
            throws InterruptedException {
        return mThreadFunc(
                threads,
                1,
                values,
                (stream -> stream.max(comparator).orElseThrow())
        ).max(comparator)
                .orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T minimum(int threads, List<? extends T> values, Comparator<? super T> comparator)
            throws InterruptedException {
        return maximum(threads, values, comparator.reversed());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> boolean all(int threads, List<? extends T> values, Predicate<? super T> predicate)
            throws InterruptedException {
        return mThreadFunc(
                threads,
                1,
                values,
                (stream -> stream.allMatch(predicate))
        ).allMatch(Boolean::booleanValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> boolean any(int threads, List<? extends T> values, Predicate<? super T> predicate)
            throws InterruptedException {
        return mThreadFunc(
                threads,
                1,
                values,
                (stream -> stream.anyMatch(predicate))
        ).anyMatch(Boolean::booleanValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> int count(int threads, List<? extends T> values, Predicate<? super T> predicate)
            throws InterruptedException {
        return mThreadFunc(
                threads,
                1,
                values,
                (stream -> (int) stream.filter(predicate).count())
        ).mapToInt(Integer::intValue)
                .sum();
    }
}
