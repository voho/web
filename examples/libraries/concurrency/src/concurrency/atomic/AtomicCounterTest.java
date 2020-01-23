package concurrency.atomic;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class AtomicCounterTest {
    private static final int NUMBER_OF_THREADS = 42;

    @Test
    public void executeAtomicCounter() {
        final AtomicInteger counter = new AtomicInteger(0);

        // spustíme mnoho vláken, každé přičte jedničku
        executeManyTimesInParallel(NUMBER_OF_THREADS, counter::incrementAndGet);

        // ověříme, že se každé vlákno přičetlo právě jednou
        assertEquals(NUMBER_OF_THREADS, counter.get());
    }

    private static void executeManyTimesInParallel(final int numThreads, final Runnable task) {
        final ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < numThreads; i++) {
            executorService.submit(task);
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            // ignore this
        }
    }
}
