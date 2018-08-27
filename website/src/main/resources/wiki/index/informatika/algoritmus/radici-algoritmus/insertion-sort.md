## Insertion Sort (řazení vkládáním)

Insertion sort je jednoduchý řadící algoritmus, založený na myšlence postupného posouvání všech prvků v poli na správné místo v již seřazené posloupnosti. Každý prvek se posouvá k počátku tak dlouho, dokud před ním není prvek menší. Algoritmus insertion sort je **přirozený**, **stabilní** a jeho asymptotická složitost je € O(n^2) €.

### Implementace (Java)

```include:java
InsertionSort.java
```

### Ukázkový běh

| Pole | Operace
|---|---
| 5 2 3 9 1 | Toto pole chceme seřadit insertion sortem.
| **5** 2 3 9 1 | První prvek je seřazen.
| **5** (2) 3 9 1 | Číslo 2 < 5, takže posuneme 5 a 2 vložíme před ní.
| **2 5** 3 9 1 | Dvě čísla jsou již seřazená.
| **2 5** (3) 9 1 | Číslo 3 < 5, ale 3 > 2, takže posuneme 5 a 3 vložíme před ní.
| **2 3 5** 9 1 | Tři čísla jsou již seřazená.
| **2 3 5** (9) 1 | Číslo 9 > 5, takže ho nebudeme posouvat.
| **2 3 5 9** 1 | Čtyři čísla jsou již seřazená.
| **2 3 5 9** (1) | Číslo 1 < 9, 1 < 5, 1 < 3, 1 < 2, takže posuneme 2359 a 1 vložíme před ně.
| **1 2 3 5 9** | Pole je seřazeno.