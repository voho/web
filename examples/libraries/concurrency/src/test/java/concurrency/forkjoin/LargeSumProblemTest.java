package concurrency.forkjoin;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LargeSumProblemTest {
    @Test
    public void test() {
        int length = 100;
        List<Integer> input = Collections.nCopies(length, 1);
        assertEquals((long) length, (long) LargeSumProblem.solve(input));
    }
}