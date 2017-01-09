## Beta kód

Beta kód je jednoduchý algoritmus pro kódování přirozených čísel. Zobrazí každé přirozené číslo *N* jako jeho zápis v binární číselné soustavě.

| Číslo | Kód
|---|---
| 1 | 1
| 2 | 10
| 3 | 11
| 4 | 100
| 5 | 101
| 10 | 1010
| 100 | 1100100

### Příklad

#### Kódování čísla 75

Binární zápis čísla je možné vytvořit několika způsoby. Jedním z nich je i rozklad čísla na součet mocnin dvojky. Číslo 75 rozložíme jako součet **64** (2 na 6) a 11. Číslo 11 rozložíme jako součet **8** (2 na 3) a 3. Číslo 3 rozložíme jako součet **2** (2 na 1) a **1** (2 na 0). Nyní si nalezené mocniny zapíšeme do tabulky.

| Pozice | Mocnina | Bit
|---|---|---
| 6 | 64 | 1
| 5 | 32 | 0
| 4 | 16 | 0
| 3 | 8 | 1
| 2 | 4 | 0
| 1 | 2 | 1
| 0 | 1 | 1


Beta kód je shodný s binárním zápisem čísla 75, výsledek je tedy *1001011*.

#### Dekódování čísla 10010110

Převod binárního čísla do dekadické soustavy lze také provést různými způsoby, zvolme například tento:

| Bit | Pozice | Mocnina
|---|---|---
| 1 | 7 | 128
| 0 | 6 | 64
| 0 | 5 | 32
| 1 | 4 | 16
| 0 | 3 | 8
| 1 | 2 | 4
| 1 | 1 | 2
| 0 | 0 | 1

Výsledné číslo je součet takových mocnin dvojky, u kterých je v binárním zápisu na odpovídající pozici jednička:

Součet = 128 + 32 + 8 + 4 = *172*

### Beta kód s čárkou

Beta kód (binární zápis) každého přirozeného čísla začíná číslicí jedna. Beta kód s čárkou tuto jedničku vynechává a ušetří tak jeden bit.

| Číslo | Beta kód | Beta kód s čárkou
|---|---|---
| 1 | 1 | prázdný řetězec
| 2 | 1*0* | 0
| 3 | 1*1* | 1
| 4 | 1*00* | 00
| 5 | 1*01* | 01
| 10 | 1*010* | 010
| 100 | 1*100100* | 100100

#### Příklad

##### Kódování čísla 75

Binární zápis čísla 75 je **1001011** (viz beta kód). Beta kód s čárkou pouze odebere z binárního zápisu první jedničku. Výsledek je tedy *001011*.

##### Dekódování čísla 0010110

Ke vstupnímu řetězci přidáme na začátek jedničku a výsledný řetězec **10010110** převedeme jako obyčejné binární číslo do dekadické soustavy (viz beta kód). Výsledek je tedy *172*.

### Reference

- předmět X36KOD na ČVUT