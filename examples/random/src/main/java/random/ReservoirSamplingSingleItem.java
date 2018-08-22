package random;

import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class ReservoirSamplingSingleItem {
    /**
     * Randomly select one item from a stream of items of unknown length.
     *
     * @param stream stream of elements
     * @param <T>    element type
     * @return list of random elements from the stream
     */
    public static <T> Optional<T> reservoirSampling(final Iterator<T> stream) {
        Optional<T> R = Optional.empty();

        int k = 1;

        // for each element, accept it with probability 1/k
        // if accepted, then replace R with the new element

        while (stream.hasNext()) {
            final T nextElement = stream.next();

            final double pOfReplacement = 1.0 / (double) k;
            final double randomNumber = ThreadLocalRandom.current().nextDouble();

            if (randomNumber < pOfReplacement) {
                R = Optional.of(nextElement);
            }

            k++;
        }

        return R;
    }
}
