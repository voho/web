package examples.math;

import java.math.BigInteger;

public class PowerTrivial {
    public static BigInteger power(final BigInteger base, final BigInteger power) {
        if (BigInteger.ZERO.compareTo(power) > 0) {
            // power < 0
            throw new IllegalArgumentException("Power must be >= 0.");
        } else if (BigInteger.ZERO.equals(power)) {
            // power = 0
            return BigInteger.ONE;
        } else {
            BigInteger result = base;
            BigInteger remains = power;
            while (remains.compareTo(BigInteger.ONE) > 0) {
                result = result.multiply(base);
                remains = remains.subtract(BigInteger.ONE);
            }
            return result;
        }
    }
}