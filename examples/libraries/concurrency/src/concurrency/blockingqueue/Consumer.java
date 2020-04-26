package concurrency.blockingqueue;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private final BlockingQueue<Integer> queue;

    public Consumer(final BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                final Integer take = queue.take();
                process(take);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void process(final Integer take) throws InterruptedException {
        System.out.println("[Consumer] Take: " + take);
        Thread.sleep(500);
    }
}
