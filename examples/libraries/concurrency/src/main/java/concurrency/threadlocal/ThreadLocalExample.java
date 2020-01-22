package concurrency.threadlocal;

import java.util.Arrays;
import java.util.List;

public class ThreadLocalExample {
    private static final ThreadLocal<String> THREAD_LOCAL_DATA = ThreadLocal.withInitial(() -> "no name");

    private static class NamedRunnable implements Runnable {
        private final String name;

        NamedRunnable(final String name) {
            this.name = name;
        }

        @Override
        public void run() {
            // write
            debug("Setting name: " + name);
            THREAD_LOCAL_DATA.set(name);

            // read
            debug("Getting name: " + THREAD_LOCAL_DATA.get());
        }
    }

    public static void main(final String[] args) {
        final List<Thread> threads = Arrays.asList(
                new Thread(new NamedRunnable("A"), "Thread A"),
                new Thread(new NamedRunnable("B"), "Thread B"),
                new Thread(new NamedRunnable("C"), "Thread C")
        );

        // start all threads in parallel
        threads.forEach(Thread::start);

        // wait for all threads to finish
        threads.forEach(a -> {
            try {
                a.join();
            } catch (final InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void debug(final String message) {
        System.out.println(Thread.currentThread().getName() + ": " + message);
    }
}
