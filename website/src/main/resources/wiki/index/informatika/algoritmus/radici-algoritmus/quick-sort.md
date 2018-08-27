## Quick sort (rychlé řazení)

![Charles Hoare (1968)](hoare.jpg){.right}

Quick sort je řadící algoritmus, inspirovaný přístupem "rozděl a panuj" (divide et impera). V roce 1962 jej popsal sir **Charles Antony Richard Hoare**.

Ve vstupním poli je nejprve zvolen jeden prvek, tzv. **pivot**, který slouží jako etalon pro prvky zbývající. Zbytek pole je poté přeuspořádán tak, aby byly prvky menší než pivot před pivotem a prvky větší nebo rovné pivotu za pivotem. Algoritmus je potom rekurzivně spuštěn na obou těchto částech.

Algoritmus quick sort lze naimplementovat různým způsobem - ovlivňovat lze způsob selekce pivotu, stabilitu a velikost potřebné pomocné paměti. Algoritmus **není přirozený** a je efektivní až pro větší pole, proto se pro ta malá zpravidla nevyužívá.

Jeho teoretická asymptotická složitost je sice v nejhorším případě až € O(n^2) €, ale při vhodné volbě pivotu může klesnout až na obvyklejší hodnotu € O(n \cdot \log n) €.

Základní kroky algoritmu:

- volba pivotu
- přesun prvků menších než pivot před něj
- přesun prvků větších nebo rovných pivotu za něj
- spuštění algoritmu na část pole obsahující menší prvky
- spuštění algoritmu na část pole obsahující větší prvky (prvky stejné jako pivot je možné přeskočit)

### Implementace (Java)

```include:java
QuickSort.java
```