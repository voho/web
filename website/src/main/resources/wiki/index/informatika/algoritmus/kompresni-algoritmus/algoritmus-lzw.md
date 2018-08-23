## Kompresní algoritmus LZW

Kompresní algoritmus LZW (autoři Lempel, Ziv, Welch, 1984) je slovníková kompresní metoda. Jedná se o vylepšení [algoritmu LZ78](wiki/algoritmus-lz78). Hlavním rozdílem je kratší kódové slovo, které se skládá pouze z jednoho čísla - indexu slova ve slovníku. Algoritmus LZW je poměrně jednoduchý na implementaci, ale je nutné řešit velkou paměťovou náročnost slovníku. Tento nedostatek lze řeší různě, například "zmražením" slovníku při určité velikosti, jeho vymazáním, odstraněním dlouho nepoužitých frází (NRU) a podobně.

V každé iteraci algoritmu se hledá nejdelší fráze ve slovníku, která odpovídá dosud nezpracované části vstupního řetězce. Na výstup je poté vloženo kódové slovo, které se skládá z indexu fráze ve slovníku. Fráze ve slovníku je pak o tento znak rozšířena a označena nejmenším možným číslem.

Kompresní metoda LZW je slovníková, jednoprůchodová, adaptivní a symetrická.

### Příklad

#### Komprese řetězce ABRABABRA

##### Inicializace

Do slovníku se před kompresí vloží všechny symboly vstupního řetězce a spojí se s kořenem. Tyto symboly je vedle kódových slov také nutné přenést k příjemci.

```dot:digraph
node[shape=circle]
rankdir=LR
0->1[label=" A"]
0->2[label=" B"]
0->3[label=" R"]
```

##### Krok 1

Načítáme symboly ze vstupu tak dlouho, dokud se jejich zřetězení nachází ve slovníku. Nejdelší nalezený řetězec je **A** s indexem **1**. Na výstup vložíme tento index a slovník rozšíříme o první nenalezené slovo **AB**. Vytvoříme tedy nový uzel, spojíme jej s u uzlem **1** hranou označenou symbolem **B** a přiřadíme mu nejnižší volný index **4**. Ve vstupním řetězci se posuneme o jeden znak.

- Výstup: **1**

```dot:digraph
node[shape=circle]
rankdir=LR
0->1[label=" A"]
0->2[label=" B"]
0->3[label=" R"]
1->4[label=" B"]
```

##### Krok 2

Načítáme symboly ze vstupu tak dlouho, dokud se jejich zřetězení nachází ve slovníku. Nejdelší nalezený řetězec je **B** s indexem **2**. Na výstup vložíme tento index a slovník rozšíříme o první nenalezené slovo **BR**. Vytvoříme tedy nový uzel, spojíme jej s u uzlem **2** hranou označenou symbolem **R** a přiřadíme mu nejnižší volný index **5**. Ve vstupním řetězci se posuneme o jeden znak.

- Výstup: 1, **2**

```dot:digraph
node[shape=circle]
rankdir=LR
0->1[label=" A"]
0->2[label=" B"]
0->3[label=" R"]
1->4[label=" B"]
2->5[label=" R"]
```

##### Krok 3

Načítáme symboly ze vstupu tak dlouho, dokud se jejich zřetězení nachází ve slovníku. Nejdelší nalezený řetězec je **R** s indexem **3**. Na výstup vložíme tento index a slovník rozšíříme o první nenalezené slovo **RA**. Vytvoříme tedy nový uzel, spojíme jej s u uzlem **3** hranou označenou symbolem **A** a přiřadíme mu nejnižší volný index **6**. Ve vstupním řetězci se posuneme o jeden znak.

- Výstup: 1, 2, **3**

```dot:digraph
node[shape=circle]
rankdir=LR
0->1[label=" A"]
0->2[label=" B"]
0->3[label=" R"]
1->4[label=" B"]
2->5[label=" R"]
3->6[label=" A"]
```

##### Krok 4

Načítáme symboly ze vstupu tak dlouho, dokud se jejich zřetězení nachází ve slovníku. Nejdelší nalezený řetězec je **AB** s indexem **4**. Na výstup vložíme tento index a slovník rozšíříme o první nenalezené slovo **ABA**. Vytvoříme tedy nový uzel, spojíme jej s u uzlem **4** hranou označenou symbolem **A** a přiřadíme mu nejnižší volný index **7**. Ve vstupním řetězci se posuneme o dva znaky.

- Výstup: 1, 2, 3, **4**

```dot:digraph
node[shape=circle]
rankdir=LR
0->1[label=" A"]
0->2[label=" B"]
0->3[label=" R"]
1->4[label=" B"]
2->5[label=" R"]
3->6[label=" A"]
4->7[label=" A"]
```

##### Krok 5

Načítáme symboly ze vstupu tak dlouho, dokud se jejich zřetězení nachází ve slovníku. Nejdelší nalezený řetězec je **AB** s indexem **4**. Na výstup vložíme tento index a slovník rozšíříme o první nenalezené slovo **ABR**. Vytvoříme tedy nový uzel, spojíme jej s u uzlem **4** hranou označenou symbolem **R** a přiřadíme mu nejnižší volný index **8**. Ve vstupním řetězci se posuneme o dva znaky.

- Výstup: 1, 2, 3, 4, **4**

```dot:digraph
node[shape=circle]
rankdir=LR
0->1[label=" A"]
0->2[label=" B"]
0->3[label=" R"]
1->4[label=" B"]
2->5[label=" R"]
3->6[label=" A"]
4->7[label=" A"]
4->8[label=" R"]
```

##### Krok 6

Načítáme symboly ze vstupu tak dlouho, dokud se jejich zřetězení nachází ve slovníku. Nejdelší nalezený řetězec je **RA** s indexem **6**. Na výstup vložíme tento index a slovník rozšířovat nemusíme, protože jsme dosáhli konce vstupního řetězce.

- Výstup 1, 2, 3, 4, 4, **6**

```dot:digraph
node[shape=circle]
rankdir=LR
0->1[label=" A"]
0->2[label=" B"]
0->3[label=" R"]
1->4[label=" B"]
2->5[label=" R"]
3->6[label=" A"]
4->7[label=" A"]
4->8[label=" R"]
```

#### Zpětná dekomprese

Zpětnou dekompresi lze provádět pomocí tabulky řetězců. Příjemce nejprve obdrží symboly, které se ve vstupním řetězci vyskytovaly, a vloží je do tabulky.

##### Inicializace

Do tabulky se vloží přijaté symboly.

| Řádek | Konstrukce slova | Slovo
|---|---|---
| 1 | přijato | A
| 2 | přijato | B
| 3 | přijato | R

##### Krok 1

Je načteno kódové slovo *1*. Na výstup se vypíše slovo z řádku 1. Následující kódové slovo je *2*, slovník se rozšíří o nové slovo tvořené celým řádkem 1 a prvním symbolem z řádku 2.

- Výstup: *A*

| Řádek | Konstrukce slova | Slovo
|---|---|---
| 1 | přijato | A
| 2 | přijato | B
| 3 | přijato | R
| 4 | řádek 1 + první symbol řádku 2 | AB

##### Krok 2

Je načteno kódové slovo *2*. Na výstup se vypíše slovo z řádku 2. Následující kódové slovo je *3*, slovník se rozšíří o nové slovo tvořené celým řádkem 2 a prvním symbolem z řádku 3.

- Výstup: A*B*

| Řádek | Konstrukce slova | Slovo
|---|---|---
| 1 | přijato | A
| 2 | přijato | B
| 3 | přijato | R
| 4 | řádek 1 + první symbol řádku 2 | AB
| 5 | řádek 2 + první symbol řádku 3 | BR

##### Krok 3

Je načteno kódové slovo *3*. Na výstup se vypíše slovo z řádku 3. Následující kódové slovo je *4*, slovník se rozšíří o nové slovo tvořené celým řádkem 3 a prvním symbolem z řádku 4.

- Výstup: AB*R*

| Řádek | Konstrukce slova | Slovo
|---|---|---
| 1 | přijato | A
| 2 | přijato | B
| 3 | přijato | R
| 4 | řádek 1 + první symbol řádku 2 | AB
| 5 | řádek 2 + první symbol řádku 3 | BR
| 6 | řádek 3 + první symbol řádku 4 | RA

##### Krok 4

Je načteno kódové slovo *4*. Na výstup se vypíše slovo z řádku 4. Následující kódové slovo je *4*, slovník se rozšíří o nové slovo tvořené celým řádkem 4 a prvním symbolem z řádku 4.

- Výstup: ABR*AB*

| Řádek | Konstrukce slova | Slovo
|---|---|---
| 1 | přijato | A
| 2 | přijato | B
| 3 | přijato | R
| 4 | řádek 1 + první symbol řádku 2 | AB
| 5 | řádek 2 + první symbol řádku 3 | BR
| 6 | řádek 3 + první symbol řádku 4 | RA
| 7 | řádek 4 + první symbol řádku 4 | ABA

##### Krok 5

Je načteno kódové slovo *4*. Na výstup se opět vypíše slovo z řádku 4. Následující kódové slovo je *6*, slovník se rozšíří o nové slovo tvořené celým řádkem 4 a prvním symbolem z řádku 6.

- Výstup: ABRAB*AB*

| Řádek | Konstrukce slova | Slovo
|---|---|---
| 1 | přijato | A
| 2 | přijato | B
| 3 | přijato | R
| 4 | řádek 1 + první symbol řádku 2 | AB
| 5 | řádek 2 + první symbol řádku 3 | BR
| 6 | řádek 3 + první symbol řádku 4 | RA
| 7 | řádek 4 + první symbol řádku 4 | ABA
| 8 | řádek 4 + první symbol řádku 6 | ABR

##### Krok 5

Je načteno kódové slovo *6*. Na výstup se vypíše slovo z řádku 6. Na vstupu již nejsou žádná další kódová slova, proto algoritmus skončí.

- Výstup: ABRABAB*RA*

| Řádek | Konstrukce slova | Slovo
|---|---|---
| 1 | přijato | A
| 2 | přijato | B
| 3 | přijato | R
| 4 | řádek 1 + první symbol řádku 2 | AB
| 5 | řádek 2 + první symbol řádku 3 | BR
| 6 | řádek 3 + první symbol řádku 4 | RA
| 7 | řádek 4 + první symbol řádku 4 | ABA
| 8 | řádek 4 + první symbol řádku 6 | ABR

### Implementace (Java)

#### Kódové slovo

```include:java
LZWCodeword.java
```

#### Komprese a dekomprese

```include:java
LZW.java
```

### Reference

- předmět X36KOD na FEL ČVUT