package concurrency.forkjoin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class LargeSumProblem {
    private static final Logger LOG = LoggerFactory.getLogger(LargeSumProblem.class);

    public static Long solve(final List<Integer> input) {
        // create fork join pool with the parallelism of 10 threads
        final ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        // invoke the main task
        final long result = forkJoinPool.invoke(new RecursiveSumTask(input));
        forkJoinPool.shutdown();
        return result;
    }

    private static class RecursiveSumTask extends RecursiveTask<Long> {
        private final List<Integer> input;

        RecursiveSumTask(final List<Integer> input) {
            this.input = input;
        }

        @Override
        protected Long compute() {
            if (input.size() > 10) {
                // create sub-tasks
                final List<Integer> leftSubList = input.subList(0, input.size() / 2);
                final List<Integer> rightSubList = input.subList(input.size() / 2, input.size());
                final RecursiveSumTask leftHalf = new RecursiveSumTask(leftSubList);
                final RecursiveSumTask rightHalf = new RecursiveSumTask(rightSubList);

                // execute sub-tasks asynchronously
                LOG.info("Executing left half: {}", leftSubList);
                leftHalf.fork();
                LOG.info("Executing right half: {}", rightSubList);
                rightHalf.fork();

                // join and aggregate partial results
                LOG.info("Aggregating partial results together.");
                return leftHalf.join() + rightHalf.join();
            } else {
                // actual atomic computation (for lists whose length is below a threshold)
                final long sum = input.stream().mapToLong(Long::valueOf).sum();
                LOG.info("Sum of {} is {}.", input, sum);
                return sum;
            }
        }
    }
}
