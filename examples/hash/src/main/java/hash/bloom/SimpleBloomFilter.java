package hash.bloom;

import java.util.BitSet;
import java.util.function.Function;

/**
 * Simple implementation of a bloom filter.
 */
public class SimpleBloomFilter<T> implements BloomFilter<T> {
    /**
     * number of bits in the bit set
     */
    private final int numBits;
    /**
     * bit set
     */
    private final BitSet bits;
    /**
     * hash functions
     */
    private final Function<T, Integer>[] hashFunctions;

    /**
     * Creates a new instance.
     *
     * @param numBits       number of bits (size of bit set)
     * @param hashFunctions hash functions to be used
     */
    @SafeVarargs
    public SimpleBloomFilter(final int numBits, final Function<T, Integer>... hashFunctions) {
        this.numBits = numBits;
        this.bits = new BitSet(this.numBits);
        this.hashFunctions = hashFunctions;
    }

    @Override
    public boolean probablyContains(final T element) {
        for (final Function<T, Integer> hashFunction : hashFunctions) {
            final int hashValue = hashFunction.apply(element);
            final int hashBasedIndex = getSafeIndexFromHash(hashValue);

            if (!bits.get(hashBasedIndex)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void add(final T element) {
        for (final Function<T, Integer> hashFunction : hashFunctions) {
            final int hashValue = hashFunction.apply(element);
            final int hashBasedIndex = getSafeIndexFromHash(hashValue);
            bits.set(hashBasedIndex);
        }
    }

    @Override
    public int getNumberOfActiveBits() {
        return bits.cardinality();
    }

    @Override
    public int getNumberOfBits() {
        return numBits;
    }

    @Override
    public int getNumberOfHashFunctions() {
        return hashFunctions.length;
    }

    /**
     * Transforms hash into a safe array index. Prevents overflow and negative numbers.
     *
     * @param hashValue hash value (any)
     * @return safe index into the bit set
     */
    private int getSafeIndexFromHash(final int hashValue) {
        // this just prevents the overflow and negative numbers
        final int index = hashValue % numBits;
        return index < 0 ? index + numBits : index;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getNumberOfBits());

        for (int i = 0; i < numBits; i++) {
            sb.append(bits.get(i) ? "1" : 0);
        }

        return sb.toString();
    }
}
