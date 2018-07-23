package random;

import org.junit.Test;

import java.util.Arrays;

public class RouletteWheelSelectionTest {
    @Test
    public void test() {
        final int experiments = 100000;

        final int[] counts = new int[3];
        final double[] weights = {10.0, 20.0, 1.0};

        for (int i = 0; i < experiments; i++) {
            final int index = RouletteWheelSelection.randomWeightedInteger(weights);
            counts[index]++;
        }

        System.out.println(Arrays.toString(counts));
    }
}