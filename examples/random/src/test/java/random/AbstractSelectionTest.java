package random;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public abstract class AbstractSelectionTest {
    private static final int LARGE_EXPERIMENTS = 10_000_000;
    private static final int SMALL_EXPERIMENTS = 100;

    @Test
    public void testNonZeroWeights() {
        test(new double[]{10.0, 20.0, 1.0});
    }

    @Test
    public void testNonZeroSmallWeights() {
        test(new double[]{0.1, 0.3, 0.6});
    }

    protected abstract int randomIndex(double[] weights);

    private void test(final double[] weights) {
        final int[] counts = new int[weights.length];

        for (int i = 0; i < LARGE_EXPERIMENTS; i++) {
            counts[randomIndex(weights)]++;
        }

        System.out.println("Counts: " + Arrays.toString(counts));

        final int totalCount = Arrays.stream(counts).sum();
        assertEquals(LARGE_EXPERIMENTS, totalCount);

        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights.length; j++) {
                final double ratioC = (double) counts[i] / (double) counts[j];
                final double ratioW = weights[i] / weights[j];
                // ratios should be the same (do not try this at home)
                assertEquals(ratioW, ratioC, 0.1);
            }
        }
    }

    @Test
    public void testEmptyElements() {
        for (int i = 0; i < SMALL_EXPERIMENTS; i++) {
            assertEquals(-1, randomIndex(new double[0]));
        }
    }

    @Test
    public void testSingleElementWithNonZeroWeight() {
        for (int i = 0; i < SMALL_EXPERIMENTS; i++) {
            assertEquals(0, randomIndex(new double[]{42.0}));
        }
    }

    @Test
    public void testSingleElementWithZeroWeight() {
        for (int i = 0; i < SMALL_EXPERIMENTS; i++) {
            assertEquals(0, randomIndex(new double[]{0.0}));
        }
    }
}
