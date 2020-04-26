package concurrency.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Tester {
    public static void main(final String[] args) {
        final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);

        new Thread(new Producer(queue)).start();
        new Thread(new Consumer(queue)).start();
    }
}
