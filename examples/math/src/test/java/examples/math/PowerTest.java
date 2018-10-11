package examples.math;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class PowerTest {
    @Test
    public void testEdgeCases() {
        assertPower(0, 0, 1);
        assertPower(1, 0, 0);
        assertPower(1, 1, 0);
    }

    @Test
    public void testRegularCases() {
        assertPower(3, 3, 1);
        assertPower(9, 3, 2);
        assertPower(27, 3, 3);
        assertPower(81, 3, 4);
        assertPower(243, 3, 5);
        assertPower(729, 3, 6);
    }

    private static void assertPower(final long expected, final long base, final long power) {
        final BigInteger expectedBig = new BigInteger(String.valueOf(expected));
        final BigInteger baseBig = new BigInteger(String.valueOf(base));
        final BigInteger powerBig = new BigInteger(String.valueOf(power));
        assertEquals(expectedBig, PowerTrivial.power(baseBig, powerBig));
        assertEquals(expectedBig, PowerRecursive.power(baseBig, powerBig));
        assertEquals(expectedBig, PowerNonRecursive.power(baseBig, powerBig));
    }
}