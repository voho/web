## Omega kód

Omega kód je jednoduchý algoritmus kódování přirozených čísel. Omega kód čísla 1 je definován jako *0*, kód obecného přirozeného čísla *N* > 1 lze definovat rekurzivně pomocí tohoto algoritmu:

1. Nechť řetězec *A* je binární zápis ([kódování beta](wiki/kodovani-beta)) čísla *N*.
1. Je-li délka řetězce *A* větší než 2, opakuj předchozí krok pro *N* = délka řetězce *A* zmenšená o jedničku.
1. Na konec kódového slova přidej *nulu*.

| Číslo | Konstrukce kódu | Kód
|---|---|---
| 1 | 0 | 0
| 2 | *10*.0 | 100
| 3 | *11*.0 | 110
| 4 | *10*.100.0 | 101000
| 5 | *10*.101.0 | 101010
| 10 | *11*.1010.0 | 1110100
| 100 | *10*.110.1100100.0 | 1011011001000

### Příklad

#### Kódování čísla 38

Binární zápis (beta kód) čísla 38 je **100110**, což uložím jako aktuální kódové slovo. Délka tohoto řetězce je ale 6, což je více než 2. Na začátek kódového slova tedy vložíme binární zápis čísla 6 - 1 = 5, což je **101**. Délka tohoto řetězce je 3, což je více než 2. Na začátek kódového slova tedy vložíme binární zápis čísla 3 - 1 = 2, což je **10**. Délka tohoto řetězce je 2, což není více než 2. Proto skončíme a na konec kódového slova přidáme nulu. Výsledné kódové slovo je tedy zřetězení řetězců 10, 101, 100110 a 0, což je **10101100110**.

### Omega kód s čárkou

Omega kód s čárkou funguje na stejném principu jako kód omega, ale rekurze se zastaví dříve a vznikají tak obecně kratší kódová slova. Omega kód s čárkou čísla 1 je definován jako *00*, kód obecného přirozeného čísla *N* > 1 lze definovat rekurzivně pomocí tohoto algoritmu:

1. Nechť řetězec *A* je binární zápis (beta kód) čísla *N*.
1. Je-li délka řetězce *A* větší než 3, opakuj předchozí krok pro *N* = délka řetězce *A* zmenšená o jedničku.
1. Na konec kódového slova přidej *nulu*.

| Číslo | Konstrukce kódu | Kód
|---|---|---
| 1 | 0.0 | 00
| 2 | *010*.0 | 0100
| 3 | *011*.0 | 0110
| 4 | *100*.0 | 1000
| 5 | *101*.0 | 1010
| 10 | *011*.1010.0 | 01110100
| 100 | *110*.1100100.0 | 11011001000

#### Příklad

##### Kódování čísla 38

Binární zápis (beta kód) čísla 38 je **100110**, což uložím jako aktuální kódové slovo. Délka tohoto řetězce je ale 6, což je více než 3. Na začátek kódového slova tedy vložíme binární zápis čísla 6 - 1 = 5, což je **101**. Délka tohoto řetězce je 3, což není více než 3. Proto skončíme a na konec kódového slova přidáme nulu. Výsledné kódové slovo je tedy zřetězení řetězců 101, 100110 a 0, což je **1011001100**.

### Reference

- předmět X36KOD na ČVUT