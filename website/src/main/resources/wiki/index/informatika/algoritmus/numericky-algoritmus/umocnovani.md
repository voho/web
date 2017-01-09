## Celočíselná mocnina

Algoritmus pro celočíselné mocnění vypočítá hodnotu výrazu €a^b€, kde €a \in Z€ se nazývá **mocněnec** (základ mocniny) a €b \in Z, b \geq 0€ **mocnitel** (exponent). Výsledek operace se označuje jako **mocnina**.

Trivální a neefektivní algoritmus je založen na opakovaném násobení základu sebou samým. Zdrojový kód může vypadat nějak takto:

```java
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
```

Neefektivita výše uvedeného algoritmu pramení z toho, že provádí daleko více výpočtů, než ve skutečnosti musí. Stačí se podívat na vzorce pro výpočet několika prvních mocnin a hned je patrná pravidelná struktura problému:

€€
\begin{align*}
a^1 &= a \\
a^2 &= a \cdot a = a^1 \cdot a^1 \\
a^3 &= a \cdot a \cdot a = a \cdot a^2 \\
a^4 &= a \cdot a \cdot a \cdot a = a^2 \cdot a^2 \\
a^5 &= a \cdot a \cdot a \cdot a \cdot a = a \cdot a^4 \\
a^6 &= \ldots
\end{align*}
€€

Nyní je zřejmé, že se vyšší mocniny systematicky skládají z nižších mocnin a to způsobem rozdílným pro sudé a liché exponenty. Obecně lze tedy odvodit následující rekurentní vztah pro rozklad vyšších mocnin na nižší:

€€
a^b= \begin{cases} 1 & b=0 \\ a \cdot a^{b-1} & \mbox{b}{\,mod\,}{\,2}=1 \\ (a^{b/2})^2 & \mbox{b}{\,mod\,}{\,2}=0\end{cases}
€€

Rekurentní vztah lze snadno převést na rekurzivní algoritmus. Ten má oproti triviálnímu řešení obrovskou výhodu v tom, že má jen [logaritmickou složitost](wiki/asymptoticka-slozitost).

```java
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
```

Ještě o něco lepší výkon dostaneme, převedeme-li rekurzivní variantu na nerekurzivní:

```java
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
```

Ani tento algoritmus není ještě zcela optimální. Efektivnější algoritmy jsou však velmi složité a jejich smysluplné aplikace se nachází snad už jen v kryptografii.