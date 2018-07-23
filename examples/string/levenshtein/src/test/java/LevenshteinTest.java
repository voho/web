import org.junit.Test;

import static org.junit.Assert.*;

public class LevenshteinTest {
    @Test
    public void test() {
        testUsingAllMethodsBothSides(0, "", "");
        testUsingAllMethodsBothSides(0, "johnny", "johnny");
        testUsingAllMethodsBothSides(1, "John", "john");
        testUsingAllMethodsBothSides(3, "sitting", "kitten");
        testUsingAllMethodsBothSides(3, "saturday", "sunday");
        testUsingAllMethodsBothSides(8, "raisethysword", "rosettacode");
        testUsingAllMethodsBothSides(4, "dog", "buggy");
        testUsingAllMethodsBothSides(3, "nanny", "man");
    }

    private void testUsingAllMethodsBothSides(final int expectedLength, final String either, final String another) {
        testUsingAllMethodsSingleSide(expectedLength, either, another);
        testUsingAllMethodsSingleSide(expectedLength, another, either);
    }

    private void testUsingAllMethodsSingleSide(final int expectedLength, final String first, final String second) {
        assertEquals(expectedLength, Levenshtein.distanceWagnerFischerOptimized(first.toCharArray(), second.toCharArray()));
        assertEquals(expectedLength, Levenshtein.distanceWagnerFischer(first.toCharArray(), second.toCharArray()));
    }
}