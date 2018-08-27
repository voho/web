## Selection Sort (řazení výběrem)

Selection Sort je základní řadící algoritmus, velmi jednoduchý na pochopení i implementaci. Na začátku řazení se celé pole považuje za **neseřazenou část**. Jedním průchodem neseřazenou částí je nalezeno **minimum**, které se prohodí s prvek na začátku neseřazené části. Po této operaci je neseřazená část kratší o jeden prvek a algoritmus pokračuje stejným způsobem dál až na konec pole. Řadící algoritmus selection sort je **nepřirozený**, **nestabilní** a jeho asymptotická složitost je € O(n^2) €.

### Implementace (Java)

```include:java
SelectionSort.java
```

### Ukázkový běh

| Pole | Operace
|---|---
| 5 2 3 9 1 | Toto pole chceme seřadit.
| 5 2 3 9 (1) | Nalezené minimum je 1, prohodíme s 5.
| **1** 2 3 9 5 | Jeden prvek je seřazen.
| **1** (2) 3 9 5 | Nalezené minimum je 2. Nic neprohodíme.
| **1 2** 3 9 5 | Dva prvky jsou seřazeny.
| **1 2** (3) 9 5 | Nalezené minimum je 3. Nic neprohodíme.
| **1 2 3** 9 5 | Tři prvky jsou seřazeny.
| **1 2 3** 9 (5) | Nalezené minimum je 5, prohodíme s 9.
| **1 2 3 5** 9 | Čtyři prvky jsou seřazeny.
| **1 2 3 5 9** | Dojeli jsme na poslední prvek, pole je seřazeno.