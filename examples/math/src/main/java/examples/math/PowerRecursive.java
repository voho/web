package examples.math;

import java.math.BigInteger;

public class PowerRecursive {
    public static BigInteger power(final BigInteger base, final BigInteger power) {
        if (BigInteger.ZERO.compareTo(power) > 0) {
            // power < 0
            throw new IllegalArgumentException("Power must be >= 0.");
        } else if (BigInteger.ZERO.equals(power)) {
            // power = 0
            return BigInteger.ONE;
        } else {
            if (power.testBit(0)) {
                // power is odd: r = a*(a^(b-1))
                final BigInteger subPower = power.subtract(BigInteger.ONE);
                final BigInteger subResult = power(base, subPower);
                return subResult.multiply(base);
            } else {
                // power is even: r = (a^(b/2))^2
                final BigInteger subPower = power.divide(new BigInteger("2"));
                final BigInteger subResult = power(base, subPower);
                return subResult.multiply(subResult);
            }
        }
    }
}
