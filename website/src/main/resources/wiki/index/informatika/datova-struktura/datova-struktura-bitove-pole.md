## Bitové pole (bit set)

Bitové pole je [pole](wiki/datova-struktura-pole) [bitů](wiki/bit), což znamená, že každý prvek tohoto pole může nabývat pouze dvou různých hodnot - například *0*, nebo *1*. V některých [programovacích jazycích](wiki/programovani) se kvůli úspoře paměti implementuje nad polem celých čísel s využitím [bitových operací](wiki/bit), což je zajímavé řešení, které stojí minimálně za zmínku. Jedno celé číslo zde tedy představuje buňku o velikosti 32 nebo 64 bitů (v závislosti na architektuře). Bitové pole jako datová struktura pak navenek poskytuje metody pro zjednodušený přístup k jednotlivým bitům nezávisle na tom, jak jsou jednotlivé bity uloženy.

### Operace

#### Čtení hodnoty

Parametrem pro čtení hodnoty je její index. Návratovou hodnotou je bit (*0* nebo *1*). Operace čtení se provádí tak, že se odpovídající buňka logicky vynásobí odpovídající bitovou masko (operace *AND*). Návratovou hodnotou je hodnota tohoto součinu.

Příklad čtení třetího bitu:

| Popis | Hodnota | Výsledek
|---|---|---
| buňka bitového pole | `00101001001010100000100100100011` | | 
| maska pro čtení třetího bitu | `00100000000000000000000000000000` | | 
| logický součin buňky a masky pro třetí bit | `00100000000000000000000000000000` | různé od nuly = třetí bit je nastaven 

Příklad čtení čtvrtého bitu:

| Popis | Hodnota | Výsledek 
|---|---|---
| buňka bitového pole | `00101001001010100000100100100011` | | 
| maska pro čtení čtvrtého bitu | `00010000000000000000000000000000` | | 
| logický součin buňky a masky pro třetí bit | `00000000000000000000000000000000` | nula = čtvrtý bit není nastaven 

#### Nastavení hodnoty

Parametrem pro nastavení hodnoty je její index a požadovaná hodnota (*0* nebo *1*). Někdy se místo toho používají operace dvě, přičemž jedna z nich (*set*) na zadaný index nastavuje přímo hodnotu *1* a ta druhá (*unset*) hodnotu *0*.

Operace nastavení hodnoty *1* se provádí tak, že se odpovídající buňka logicky sečte s odpovídající bitovou maskou (operace *OR*), pro nastavení hodnoty *0* se buňka musí logicky vynásobit inverzní bitovou maskou (operace *AND*).

Příklad zápisu hodnoty *1* na čtvrtý bit:

| Popis | Hodnota 
|---|---
| buňka bitového pole | `00101001001010100000100100100011` 
| maska pro zápis jedničky do čtvrtého bitu | `00010000000000000000000000000000` 
| logický součet buňky a inverzní masky | `00111001001010100000100100100011`

Příklad zápisu hodnoty *0* na čtvrtý bit:

| Popis | Hodnota
|---|---
| buňka bitového pole | `00111001001010100000100100100011` 
| maska pro zápis nuly do čtvrtého bitu | `00010000000000000000000000000000` 
| inverzní maska | `11101111111111111111111111111111` 
| logický součin buňky a inverzní masky | `00101001001010100000100100100011`

### Implementace

#### Rozhraní bitového pole

```java
public interface BitArray {
    int size();

    boolean get(int index);

    void set(int index);

    void unset(int index);
}
```

#### Implementace bitového pole

```java
public class DefaultBitArray implements BitArray {
    private static final int BITS_PER_BUCKET = Long.SIZE;
    private static final long FIRST_BIT = 1L << (Long.SIZE - 1);
    private final int sizeInBits;
    private final long[] bitBuckets;

    public DefaultBitArray(final int sizeInBits) {
        this.sizeInBits = sizeInBits;
        final int numBuckets = getNumberOfBuckets(sizeInBits);
        this.bitBuckets = new long[numBuckets];
    }

    private static int getNumberOfBuckets(final int sizeInBits) {
        // this is a "ceil" operation shortened
        return (sizeInBits + BITS_PER_BUCKET - 1) / BITS_PER_BUCKET;
    }

    private static long getFlag(final int index) {
        return FIRST_BIT >>> (index % BITS_PER_BUCKET);
    }

    @Override
    public int size() {
        return sizeInBits;
    }

    @Override
    public boolean get(final int index) {
        checkValidIndex(index);
        final int bucketIndex = index / BITS_PER_BUCKET;
        final long flag = getFlag(index);
        return (bitBuckets[bucketIndex] & flag) != 0;
    }

    @Override
    public void set(final int index) {
        checkValidIndex(index);
        final int bucketIndex = index / BITS_PER_BUCKET;
        final long flag = getFlag(index);
        bitBuckets[bucketIndex] |= flag;
    }

    @Override
    public void unset(final int index) {
        checkValidIndex(index);
        final int bucketIndex = index / BITS_PER_BUCKET;
        final long flag = ~(getFlag(index));
        bitBuckets[bucketIndex] &= flag;
    }

    @Override
    public String toString() {
        return IntStream
                .range(0, sizeInBits)
                .mapToObj(i -> get(i) ? "1" : "0")
                .collect(Collectors.joining());
    }

    private void checkValidIndex(final int index) {
        if (index < 0) {
            throw new IllegalArgumentException(String.format("Index must be >= 0, but was %d.", index));
        }

        if (index >= sizeInBits) {
            throw new IllegalArgumentException(String.format("Index must be < %d, but was %d.", sizeInBits, index));
        }
    }
}
```
