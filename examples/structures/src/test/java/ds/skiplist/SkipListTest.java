package ds.skiplist;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SkipListTest {
    private static final Logger log = LoggerFactory.getLogger(SkipListTest.class);
    private static final Random RANDOM = new SecureRandom();
    private static final List<Integer> SIZES = Arrays.asList(1, 3, 5, 6, 10, 100);

    @Test
    public void testDecreasing() {
        for (final int size : SIZES) {
            final SkipList<Integer, String> list = new DefaultSkipList<>(size);

            for (int i = 10; i >= 1; i--) {
                assertFalse(list.contains(i));
                list.insert(i, getStringValue(i));
                assertTrue(list.contains(i));
            }
        }
    }

    @Test
    public void testIncreasing() {
        for (final int size : SIZES) {
            final SkipList<Integer, String> list = new DefaultSkipList<>(size);

            for (int i = 1; i <= 10; i++) {
                assertFalse(list.contains(i));
                list.insert(i, getStringValue(i));
                assertTrue(list.contains(i));
            }
        }
    }

    @Test
    public void loadTest() {
        for (final int size : SIZES) {
            final SkipList<Integer, String> list = new DefaultSkipList<>(size);

            for (int i = 1; i <= 100; i++) {
                // insert

                final int random1 = RANDOM.nextInt(20);
                list.insert(random1, getStringValue(random1));
                assertTrue(list.get(random1).isPresent());
                assertTrue(list.get(random1).get().equals(getStringValue(random1)));

                // delete

                final int random2 = RANDOM.nextInt(20);
                final boolean wasThere = list.contains(random2);
                assertEquals(wasThere, list.delete(random2));
                assertFalse(list.contains(random2));
                assertFalse(list.get(random2).isPresent());

                // print

                log.debug("====== {} ======", i);
                log.debug(list.toString());
            }
        }
    }

    private String getStringValue(final int i) {
        return String.format("V%d", i);
    }
}