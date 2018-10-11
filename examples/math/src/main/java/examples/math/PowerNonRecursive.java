package examples.math;

import java.math.BigInteger;

public class PowerNonRecursive {
    public static BigInteger power(final BigInteger base, final BigInteger power) {
        if (BigInteger.ZERO.compareTo(power) > 0) {
            // power < 0
            throw new IllegalArgumentException("Power must be >= 0.");
        } else if (BigInteger.ZERO.equals(power)) {
            // power = 0
            return BigInteger.ONE;
        } else {
            BigInteger result = BigInteger.ONE;
            BigInteger tempBase = base;
            BigInteger tempPower = power;
            while (!tempPower.equals(BigInteger.ZERO)) {
                if (tempPower.testBit(0)) {
                    // tempPower is odd
                    result = result.multiply(tempBase);
                }
                // tempPower = tempPower * 2
                tempPower = tempPower.shiftRight(1);
                // tempBase = tempBase ^ 2
                tempBase = tempBase.multiply(tempBase);
            }
            return result;
        }
    }
}
