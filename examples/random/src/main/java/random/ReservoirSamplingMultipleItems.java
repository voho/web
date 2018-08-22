package random;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ReservoirSamplingMultipleItems {
    /**
     * Randomly select k items from a stream of items of unknown length.
     *
     * @param stream stream of elements
     * @param s      reservoir size (number of elements)
     * @param <T>    element type
     * @return list of random elements from the stream
     */
    public static <T> List<T> reservoirSampling(final Iterator<T> stream, final int s) {
        final List<T> R = new ArrayList<>(s);

        int k = 1;

        // fill R with the first s elements

        while (stream.hasNext() && k <= s) {
            final T nextElement = stream.next();
            R.add(nextElement);
            k++;
        }

        // for each element, accept it with probability s/k
        // if accepted, then replace a randomly selected element in R with the new element

        while (stream.hasNext()) {
            final T nextElement = stream.next();

            final double pOfReplacement = (double) s / (double) k;
            final double randomNumber = ThreadLocalRandom.current().nextDouble();

            if (randomNumber < pOfReplacement) {
                final int randomIndex = ThreadLocalRandom.current().nextInt(s);
                R.set(randomIndex, nextElement);
            }

            k++;
        }

        return R;
    }
}
