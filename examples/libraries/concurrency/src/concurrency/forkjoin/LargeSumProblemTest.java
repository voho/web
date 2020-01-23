package concurrency.forkjoin;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LargeSumProblemTest {
    @Test
    public void test() {
        final int length = 100;
        final List<Integer> input = Collections.nCopies(length, 1);
        assertEquals(length, (long) LargeSumProblem.solve(input));
    }
}
