## Alfa kód

Alfa kód je jednoduchý algoritmus pro kódování přirozených čísel. Zobrazí každé přirozené číslo *N* jako *N* bitů. Všechny bity až na poslední jsou **nula**, poslední bit je **jedna**. Alfa kód je prefixový.

| Číslo | Kód
|---|---
| 1 | 1
| 2 | 01
| 3 | 001
| 4 | 0001
| 5 | 00001

### Příklad

#### Kódování čísla 8

Číslo **8** se zapíše jako 8 bitů, z nichž 7 bude **nula**, osmý bit **jedna**. Výsledek je tedy *00000001*.

#### Dekódování řetězce 0000010010001

Nejjednodušeji daný řetězec rozkódujeme tak, že jej rozdělíme na části. Každá část bude končit jedničkou. Když toto provedeme se zadaným řetězcem, dostaneme následující tři části:

- **000001**, která má 6 bitů, je tedy číslem *6*
- **001**, která má 3 bity, je tedy číslem *3*
- **0001**, která má 4 bity, je tedy číslem *4*

### Reference

- předmět X36KOD na ČVUT