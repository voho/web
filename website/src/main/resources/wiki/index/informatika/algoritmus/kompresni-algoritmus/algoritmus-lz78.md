## Kompresní algoritmus LZ78

Kompresní algoritmus LZ78 (autoři Abraham Lempel, Jacob Ziv, rok 1978) patří mezi slovníkové kompresní metody. Jedná se o poměrně jednoduchou metodu, ale náročnou na paměť. Tento nedostatek se řeší různě, například "zmražením" slovníku při určité velikosti, jeho občasným vyprázdněním, odstraněním dlouho nepoužitých frází (NRU), a podobně.

Algoritmus pracuje v krocích. Slovník je tvořen [stromem](wiki/datova-struktura-strom), ve kterém každá cesta od kořene k listu představuje jednu **frázi** ze vstupního řetězce. Tento stroj je inicializován kořenovým uzlem (prázdnou frází) s indexem *0*. 

V každém kroku se ve slovníku se vyhledá nejdelší fráze, která odpovídá dosud nezpracované části vstupního řetězce (tj. cesta od kořene k listu). Na výstup je poté vloženo kódové slovo €(i,x)€, kde €i€ je **index fráze** ve slovníku a €x€ je první znak ze vstupu, který se v nalezené frázi nenachází. Nakonec je fráze ve slovníku o tento znak rozšířena a označena nejmenším možným nepoužitým číslem. 

Kompresní metoda LZ78 je slovníková, jednoprůchodová, adaptivní a symetrická.

### Příklady

#### Komprese řetězce ABRAKADAKABRA

##### Inicializace

Slovník je zpočátku prázdný a obsahuje pouze jeden kořenový uzel s indexem *0*.

```dot:digraph
node[shape=circle]
rankdir=LR
0
```

##### Krok 1

Načítáme symboly ze vstupu tak dlouho, dokud se jejich zřetězení nachází ve slovníku. Už první symbol **A** se ale ve slovníku nenachází, takže jej zakódujeme jako dvojici *(0,A)* a pak slovník rozšíříme. Vytvoříme nový uzel, spojíme jej s kořenem hranou označenou symbolem *A* a přiřadíme mu nejnižší volný index *1*.

- Výstup: **(0, A)**

```dot:digraph
node[shape=circle]
rankdir=LR
0->1[label=" A"]
```

##### Krok 2

Načítáme symboly ze vstupu tak dlouho, dokud se jejich zřetězení nachází ve slovníku. Už první symbol **B** se ale ve slovníku nenachází, takže jej zakódujeme jako dvojici *(0,B)* a pak slovník rozšíříme. Vytvoříme nový uzel, spojíme jej s kořenem hranou označenou symbolem *B* a přiřadíme mu nejnižší volný index *2*.

- Výstup: **(0, A)**, **(0, B)**

```dot:digraph
node[shape=circle]
rankdir=LR
0->1[label=" A"]
0->2[label=" B"]
```

##### Krok 3

Načítáme symboly ze vstupu tak dlouho, dokud se jejich zřetězení nachází ve slovníku. Už první symbol **R** se ale ve slovníku nenachází, takže jej zakódujeme jako dvojici *(0,R)* a pak slovník rozšíříme. Vytvoříme nový uzel, spojíme jej s kořenem hranou označenou symbolem *R* a přiřadíme mu nejnižší volný index *3*.

- Výstup: **(0, A)**, **(0, B)**, **(0, R)**

```dot:digraph
node[shape=circle]
rankdir=LR
0->1[label=" A"]
0->2[label=" B"]
0->3[label=" R"]
```

##### Krok 4

Načítáme symboly ze vstupu tak dlouho, dokud se jejich zřetězení nachází ve slovníku. Nejdelší nalezený řetězec je **A**, který začíná na indexu *1*. Po něm následuje symbol **K**. Na výstup vypíšeme dvojici *(1,K)* a rozšíříme slovník. Vytvoříme nový uzel, spojíme jej s uzlem *1* hranou označenou symbolem *K* a přiřadíme mu nejnižší volný index *4*.

- Výstup: **(0, A)**, **(0, B)**, **(0, R)**, **(1, K)**

```dot:digraph
node[shape=circle]
rankdir=LR
0->1[label=" A"]
0->2[label=" B"]
0->3[label=" R"]
1->4[label=" K"]
```

##### Krok 5

Načítáme symboly ze vstupu tak dlouho, dokud se jejich zřetězení nachází ve slovníku. Nejdelší nalezený řetězec je **A**, který začíná na indexu *1*. Po něm následuje symbol **D**. Na výstup vypíšeme dvojici *(1,D)* a rozšíříme slovník. Vytvoříme nový uzel, spojíme jej s uzlem *1* hranou označenou symbolem *D* a přiřadíme mu nejnižší volný index *5*.

- Výstup: **(0, A)**, **(0, B)**, **(0, R)**, **(1, K)**, **(1, D)**

```dot:digraph
node[shape=circle]
rankdir=LR
0->1[label=" A"]
0->2[label=" B"]
0->3[label=" R"]
1->4[label=" K"]
1->5[label=" D"]
```

##### Krok 6

Načítáme symboly ze vstupu tak dlouho, dokud se jejich zřetězení nachází ve slovníku. Nejdelší nalezený řetězec je **AK**, který začíná na indexu *4*. Po něm následuje symbol **A**. Na výstup vypíšeme dvojici *(4,A)* a rozšíříme slovník. Vytvoříme nový uzel, spojíme jej s uzlem *4* hranou označenou symbolem *A* a přiřadíme mu nejnižší volný index *6*.

- Výstup: **(0, A)**, **(0, B)**, **(0, R)**, **(1, K)**, **(1, D)**, **(4, A)**

```dot:digraph
node[shape=circle]
rankdir=LR
0->1[label=" A"]
0->2[label=" B"]
0->3[label=" R"]
1->4[label=" K"]
1->5[label=" D"]
4->6[label=" A"]
```

##### Krok 7

Načítáme symboly ze vstupu tak dlouho, dokud se jejich zřetězení nachází ve slovníku. Nejdelší nalezený řetězec je **B**, který začíná na indexu *2*. Po něm následuje symbol **R**. Na výstup vypíšeme dvojici *(2,R)* a rozšíříme slovník. Vytvoříme nový uzel, spojíme jej s uzlem *2* hranou označenou symbolem *R* a přiřadíme mu nejnižší volný index *7*.

- Výstup: **(0, A)**, **(0, B)**, **(0, R)**, **(1, K)**, **(1, D)**, **(4, A)**, **(2, R)**

```dot:digraph
node[shape=circle]
rankdir=LR
0->1[label=" A"]
0->2[label=" B"]
0->3[label=" R"]
1->4[label=" K"]
1->5[label=" D"]
4->6[label=" A"]
2->7[label=" R"]
```

##### Krok 8

Načítáme symboly ze vstupu tak dlouho, dokud se jejich zřetězení nachází ve slovníku. Nejdelší nalezený řetězec je **A**, který začíná na indexu *1*. Za ním ve vstupním řetězci nenásleduje nic. Na výstup vypíšeme dvojici *(1,konec)*, slovník již rozšiřovat nemusíme.

- Výstup: **(0, A)**, **(0, B)**, **(0, R)**, **(1, K)**, **(1, D)**, **(4, A)**, **(2, R)**, **(1, konec)**

```dot:digraph
node[shape=circle]
rankdir=LR
0->1[label=" A"]
0->2[label=" B"]
0->3[label=" R"]
1->4[label=" K"]
1->5[label=" D"]
4->6[label=" A"]
2->7[label=" R"]
```

#### Zpětná dekomprese

Zpětnou dekompresi lze provádět pomocí tabulky řetězců, která zpočátku obsahuje pouze řádek s indexem *0*, na kterém je uložen prázdný řetězec.

##### Krok 1

Je načteno kódové slovo *(0,A)*. Na výstup se vypíše slovo na řádku *0* zřetězené se symbolem *A*. Tabulka se o tento řetězec rozšíří.

| Řádek | Konstrukce slova | Slovo
|---|---|---
| 0 | prázdný řetězec | |
| 1 | řádek 0 + A | A

- Výstup: *A*

##### Krok 2

Je načteno kódové slovo *(0,B)*. Na výstup se vypíše slovo na řádku *0* zřetězené se symbolem *B*. Tabulka se o tento řetězec rozšíří.

| Řádek | Konstrukce slova | Slovo
|---|---|---
| 0 | prázdný řetězec | |
| 1 | řádek 0 + A | A
| 2 | řádek 0 + B | B

- Výstup: A*B*

##### Krok 3

Je načteno kódové slovo *(0,R)*. Na výstup se vypíše slovo na řádku *0* zřetězené se symbolem *R*. Tabulka se o tento řetězec rozšíří.

| Řádek | Konstrukce slova | Slovo
|---|---|---
| 0 | prázdný řetězec | |
| 1 | řádek 0 + A | A
| 2 | řádek 0 + B | B
| 3 | řádek 0 + R | R

- Výstup: AB*R*

##### Krok 4

Je načteno kódové slovo *(1,K)*. Na výstup se vypíše slovo na řádku *1* zřetězené se symbolem *K*. Tabulka se o tento řetězec rozšíří.

| Řádek | Konstrukce slova | Slovo
|---|---|---
| 0 | prázdný řetězec | |
| 1 | řádek 0 + A | A
| 2 | řádek 0 + B | B
| 3 | řádek 0 + R | R
| 4 | řádek 1 + K | AK

- Výstup: ABR*AK*

##### Krok 5

Je načteno kódové slovo *(1,D)*. Na výstup se vypíše slovo na řádku *1* zřetězené se symbolem *D*. Tabulka se o tento řetězec rozšíří.

| Řádek | Konstrukce slova | Slovo
|---|---|---
| 0 | prázdný řetězec | |
| 1 | řádek 0 + A | A
| 2 | řádek 0 + B | B
| 3 | řádek 0 + R | R
| 4 | řádek 1 + K | AK
| 5 | řádek 1 + D | AD

- Výstup: ABRAK*AD*

##### Krok 6

Je načteno kódové slovo *(4,A)*. Na výstup se vypíše slovo na řádku *4* zřetězené se symbolem *A*. Tabulka se o tento řetězec rozšíří.

| Řádek | Konstrukce slova | Slovo
|---|---|---
| 0 | prázdný řetězec | |
| 1 | řádek 0 + A | A
| 2 | řádek 0 + B | B
| 3 | řádek 0 + R | R
| 4 | řádek 1 + K | AK
| 5 | řádek 1 + D | AD
| 6 | řádek 4 + A | AKA

- Výstup: ABRAKAD*AKA*

##### Krok 7

Je načteno kódové slovo *(2,R)*. Na výstup se vypíše slovo na řádku *2* zřetězené se symbolem *R*. Tabulka se o tento řetězec rozšíří.

| Řádek | Konstrukce slova | Slovo
|---|---|---
| 0 | prázdný řetězec | |
| 1 | řádek 0 + A | A
| 2 | řádek 0 + B | B
| 3 | řádek 0 + R | R
| 4 | řádek 1 + K | AK
| 5 | řádek 1 + D | AD
| 6 | řádek 4 + A | AKA
| 7 | řádek 2 + R | BR

- Výstup: ABRAKADAKA*BR*

##### Krok 8

Je načteno kódové slovo *(1,konec)*. Na výstup se vypíše slovo na řádku *1* a dekódování skončí.

| Řádek | Konstrukce slova | Slovo
|---|---|---
| 0 | prázdný řetězec | |
| 1 | řádek 0 + A | A
| 2 | řádek 0 + B | B
| 3 | řádek 0 + R | R
| 4 | řádek 1 + K | AK
| 5 | řádek 1 + D | AD
| 6 | řádek 4 + A | AKA
| 7 | řádek 2 + R | BR

- Výstup: ABRAKADAKABR*A*

### Implementace (Java)

#### Kódové slovo

```include:java
LZ78Codeword.java
```

#### Komprese a dekomprese

```include:java
LZ78.java
```

### Reference

- předmět X36KOD na FEL ČVUT
- http://compressions.sourceforge.net/LempelZiv.html
- http://www.stringology.org/DataCompression/lz78/index_cs.html