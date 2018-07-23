package hash.bloom;

/**
 * Simple Bloom filter.
 */
public interface BloomFilter<T> {
    /**
     * cached natural logarithm of 2
     */
    double LN_OF_2 = Math.log(2);

    /**
     * Returns the optimal number of hash functions (approximate).
     *
     * @param numBits     number of bits
     * @param numElements number of elements expected
     * @return number of hash functions
     */
    static int getOptimalNumberOfHashFunctions(final int numBits, final int numElements) {
        if (numElements <= 0) {
            return 1;
        } else {
            return Math.max(1, (int) Math.round((double) numBits / numElements * LN_OF_2));
        }
    }

    /**
     * Returns the optimal number of bits (approximate).
     *
     * @param numElements                    number of elements expected
     * @param wantedFalsePositiveProbability wanted probability of false positives
     * @return number of bits
     */
    static int getOptimalNumberOfBits(final int numElements, double wantedFalsePositiveProbability) {
        if (wantedFalsePositiveProbability == 0) {
            wantedFalsePositiveProbability = Double.MIN_VALUE;
        }

        return Math.max(1, (int) (-numElements * Math.log(wantedFalsePositiveProbability) / (LN_OF_2 * LN_OF_2)));
    }

    /**
     * Checks if the element is in the set.
     * WARNING: sometimes can return false positive - TRUE for an element which is NOT in the set.
     * On the other hand, there are no negative positives - the FALSE value is always correct.
     *
     * @param element element to find
     * @return TRUE if the element is probably in the set, FALSE otherwise
     */
    boolean probablyContains(T element);

    /**
     * Adds an element into the set.
     *
     * @param element element to add
     */
    void add(T element);

    /**
     * Returns the expected false positive probability.
     *
     * @return false positive probability
     */
    default double expectedFalsePositiveProbability() {
        return Math.pow((double) getNumberOfActiveBits() / getNumberOfBits(), getNumberOfHashFunctions());
    }

    /**
     * Returns the number of active bits (number of bits which are 1).
     *
     * @return number of bits
     */
    int getNumberOfActiveBits();

    /**
     * Returns the total number of available bits.
     *
     * @return number of bits
     */
    int getNumberOfBits();

    /**
     * Returns the hash function count.
     *
     * @return hash function count
     */
    int getNumberOfHashFunctions();
}
