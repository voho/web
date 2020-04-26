package concurrency.blockingqueue;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    private final BlockingQueue<Integer> queue;

    public Producer(final BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            process();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private void process() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            System.out.println("[Producer] Put: " + i);
            queue.put(i);
            System.out.println("[Producer] Remaining Capacity: " + queue.remainingCapacity());
            Thread.sleep(100);
        }
    }
}
