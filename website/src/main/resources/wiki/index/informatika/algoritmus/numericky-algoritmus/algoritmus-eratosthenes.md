## Eratosthenovo síto

Eratosthenovo síto (Eratosthenesz Kyrény, 276 až 194 př. n. l.) je jednoduchý [algoritmus](wiki/algoritmus) pro vyhledání prvočísel ležících v určitém intervalu.
Základním principem algoritmu je fakt, že po nalezení prvočísla lze ze seznamu potenciálních prvočísel vyškrtnout všechny jeho násobky.

![Princip Eratosthenova síta](https://upload.wikimedia.org/wikipedia/commons/b/b9/Sieve_of_Eratosthenes_animation.gif)

[Množinu](wiki/mnozina) prvočísel nejprve algoritmus inicializuje na všechna přirozená čísla od 2 do *N*, kde *N* je horní hranice vyhledávání.
Algoritmus pak v každém kroce vezme první prvek ze seznamu, označí jej jako prvočíslo a odebere z něj i všechny jeho násobky.
Algoritmus pokračuje do té doby, než je seznam prázdný.

1. Pracovní seznam = všechna přirozená čísla od 2 do *N*, kde *N* je horní hranice vyhledávání
2. Množina prvočísel = prázdná množina
3. Pro první prvek *t* z pracovního seznamu:
    1. Přidej prvek *t* do množiny prvočísel.
    1. Z pracovního seznamu odeber všechny jeho násobky (*t*, *2t*, *3t*...).
4. Opakuj algoritmus od bodu 3, dokud není pracovní seznam prázdný.

### Příklad

Zkusíme najít všechna prvočísla menší než 20.

| Krok | Pracovní seznam | Odstraněná čísla | Množina prvočísel
|---|---|---|---
| 1 | 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 | - | (prázdná)
| 2 | 3, 5, 7, 9, 11, 13, 15, 17, 19 | *2*, 4, 6, 8, 10, 12, 14, 16, 18, 20 | 2
| 3 | 5, 7, 11, 13, 17, 19 | *3*, 9, 15 | 2, 3
| 4 | 7, 11, 13, 17, 19 | *5* | 2, 3, 5
| 5 | 11, 13, 17, 19 | *7* | 2, 3, 5, 7
| 6 | 13, 17, 19 | *11* | 2, 3, 5, 7, 11
| 7 | 17, 19 | *13* | 2, 3, 5, 7, 11, 13
| 8 | 19 | *17* | 2, 3, 5, 7, 11, 13, 17
| 8 | - | *19* | 2, 3, 5, 7, 11, 13, 17, 19

### Implementace (Java)

```java
private static List<Integer> findPrimes(final int upperBound) {
    // čísla má smysl testovat pouze do druhé odmocniny horní meze

    final int upperBoundRoot = (int) Math.sqrt(upperBound);

    // true = je prvočíslo
    // false = není prvočíslo

    final BitSet isPrime = new BitSet(upperBound);

    // všechna čísla kromě 0, 1 nejprve označíme jako potenciální prvočísla

    isPrime.set(2, upperBound);

    for (int testedPrime = 2; testedPrime <= upperBoundRoot; testedPrime++) {
        if (isPrime.get(testedPrime)) {
            for (int testedPrimeMultiple = 2 * testedPrime;
                 testedPrimeMultiple <= upperBound;
                 testedPrimeMultiple += testedPrimeMultiple) {
                isPrime.clear(testedPrimeMultiple);
            }
        }
    }

    // pouze sesbírá všechna označená prvočísla do seznamu

    final List<Integer> primes = new LinkedList<>();

    int index = 0;

    while (index != -1) {
        index = isPrime.nextSetBit(index + 1);
        primes.add(index);
    }

    return primes;
}
```

### Reference

- http://www.hbmeyer.de/eratosiv.htm
