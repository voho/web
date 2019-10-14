package bloom;

import org.junit.Test;

import java.util.function.Function;

public class Example {
    @Test
    public void test() {
        final Function<String, Integer> hash1 = s -> s.toLowerCase().hashCode();
        final Function<String, Integer> hash2 = s -> s.toUpperCase().hashCode();
        final BloomFilter<String> filter = new DefaultBloomFilter<>(10, hash1, hash2);

        filter.add("Hello");
        filter.add("World");
    }
}
