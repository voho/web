package concurrency.locks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    public static void main(final String[] args) {
        final List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            threads.add(new Thread(new ThrottledRunnable()));
        }

        threads.forEach(Thread::start);

        threads.forEach(a -> {
            try {
                a.join();
            } catch (final InterruptedException e) {

            }
        });
    }

    private static final class ThrottledRunnable implements Runnable {
        private static final Semaphore SEMAPHORE = new Semaphore(3);

        @Override
        public void run() {
            try {
                // blocks if no more permits are left
                debug("Acquiring permit...");
                SEMAPHORE.acquire(1);

                debug("WORKING (nah, just slacking a bit...");
                Thread.sleep(2000);

                // releases permits back to the semaphore
                debug("Releasing permit back.");
                SEMAPHORE.release(1);
            } catch (final InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void debug(final String message) {
        System.out.println(Thread.currentThread().getName() + ": " + message);
    }
}
