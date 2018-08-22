package random;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ReservoirSamplingMultipleItemsTest {
    @Test
    public void reservoirSamplingMultipleElements() {
        final List<Integer> a = Arrays.asList(0, 1);

        final int[] counts = new int[a.size()];

        for (int i = 0; i < 10000000; i++) {
            final List<Integer> rrr = ReservoirSamplingMultipleItems.reservoirSampling(a.iterator(), 1);
            counts[rrr.get(0)]++;
        }

        System.out.println(Arrays.toString(counts));
    }
}