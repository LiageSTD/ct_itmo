package info.kgeorgiy.ja.ushenko.iterative;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;

public class ParallelMapperImpl implements ParallelMapper {
    private final Thread[] threads;

    private final Deque<Runnable> taskPool = new ArrayDeque<>();

    /**
     * Creates a new instance of {@code ParallelMapperImpl}.
     * @param threads : Amount of threads to use.
     */

    public ParallelMapperImpl(int threads) {
        this.threads = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            this.threads[i] = new Thread(() -> {
                while (!Thread.interrupted()) {
                    Runnable task;
                    synchronized (taskPool) {
                        while (taskPool.isEmpty()) {
                            try {
                                taskPool.wait();
                            } catch (InterruptedException e) {
                                return;
                            }
                        }
                        task = taskPool.poll();
                    }
                    task.run();
                }
            });
            this.threads[i].start();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T, R> List<R> map(Function<? super T, ? extends R> f, List<? extends T> items) throws InterruptedException {
        List<R> result = new ArrayList<>(Collections.nCopies(items.size(), null));
        Controller controller = new Controller(items.size());
        for (int i = 0; i < items.size(); i++) {
            final int index = i;
            try {
                synchronized (taskPool) {
                    taskPool.add(
                            () -> {
                                result.set(index, f.apply(items.get(index)));
                                controller.notifyThatTaskIsDone();
                            }
                    );
                    taskPool.notify();
                }
            } catch (Exception e) {
                controller.processException(e);
            }
        }
        controller.waitForAllTasks();
        if (controller.haveException()) {
            RuntimeException exception = new RuntimeException("An error occurred");
            exception.addSuppressed(controller.getException());
            throw exception;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private class Controller {
        int amountOfTasksInProcess;
        private Exception exception = null;

        public Controller(int amountOfTasksInProcess) {
            this.amountOfTasksInProcess = amountOfTasksInProcess;
        }

        // synchronized methods to work with tasks
        public synchronized void notifyThatTaskIsDone() {
            amountOfTasksInProcess--;
            if (amountOfTasksInProcess == 0) {
                notify();
            }
        }

        public synchronized void waitForAllTasks() throws InterruptedException {
            while (amountOfTasksInProcess > 0) {
                wait();
            }
        }

        // synchronized methods to work with exception
        public synchronized void processException(Exception e) {
            if (exception == null) {
                exception = e;
            } else {
                exception.addSuppressed(e);
            }
        }

        public synchronized boolean haveException() {
            return exception != null;
        }

        public synchronized Exception getException() {
            return exception;
        }
    }
}


