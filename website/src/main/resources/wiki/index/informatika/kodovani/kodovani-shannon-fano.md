## Shannon-Fanovo kódování

Shannon-Fanovo [kódování](wiki/kodovani) je technika pro sestavení **prefixového kódu** založená na seznamu symbolů a počtech jejich výskytů (případně pravděpodobnostech). Metodu nezávisle na sobě publikovali v roce 1949 Claude Elwood Shannon s Warrenem Weaverem a Robert Mario Fano. 

Shannon-Fanův algoritmus lze využít i pro kompresi dat - je to metoda statistická, dvouprůchodová, semiadaptivní a asymetrická. Narozdíl od [Huffmanova kódování](wiki/kodovani-huffman) není optimalita výsledného kódu zaručena.

### Pseudokód

1. Pro danou zprávu vytvoř seznam symbolů a zjisti počet jejich výskytů (nebo pravděpodobnost).
1. Seznam symbolů seřaď do neklesající posloupnosti podle této hodnoty.
1. Kódy všech symbolů jsou zpočátku prázdné.
1. Rozděl seznam na dvě poloviny tak, aby součet sledovaných hodnot byl v obou částech zhruba stejný.
1. Ke kódům symbolů v levé polovině připoj *0*, ke kódům symbolů v pravé polovině připoj *1*.
1. Rekurzivně opakuj od **kroku 4** na levou i pravou polovinu seznamu.

### Příklad

#### Tabulka symbolů a jejich četností

Symboly jsou seřazené do neklesající posloupnosti podle počtu výskytů.

| Symbol | Počet výskytů |
|---|---|
| F | 5 |
| D | 4 |
| C | 4 |
| A | 3 |
| B | 3 |
| E | 2 |

##### Inicializace

Seznam je *(F,D,C,A,B,E)*. Kódy všech symbolů jsou prázdné.

| Symbol | Kód |
|---|---
| F | - |
| D | - |
| C | - | 
| A | - |
| B | - |
| E | - |

```dot:digraph
FDCABE [label="F, D, C, A, B, E",fillcolor=khaki]
```

##### Krok 1

Seznam je *(F,D,C,A,B,E)*. Nyní jej rozdělíme na dvě poloviny s příbližně stejným součtem četností.

- *(F,D)* - součet četností: 5 + 4 = 9
- *(C,A,B,E)* - součet četností: 4 + 3 + 3 + 2 = 12

| Symbol | Kód |
|---|---
| F | 0 |
| D | 0 |
| C | 1 |
| A | 1 |
| B | 1 |
| E | 1 |

```dot:digraph
FD [label="F, D",fillcolor=khaki]
CABE [label="C, A, B, E",fillcolor=khaki]
FDCABE->FD [label=" 0"]
FDCABE->CABE [label=" 1"]
FDCABE [shape=point]
```

##### Krok 2

Seznamy jsou *(F,D)* a *(C,A,B,E)*. Nyní je oba rozdělíme na poloviny s příbližně stejným součtem četností.

- *(F,D)* - součet četností: 5 + 4 = 9
  - *(F)* - součet četností: 5
  - *(D)* - součet četností: 4
- *(C,A,B,E)* - součet četností: 4 + 3 + 3 + 2 = 12
  - *(C,A)* - součet četností: 4 + 3 = 7
  - *(B,E)* - součet četností: 3 + 2 = 5

| Symbol | Kód |
|---|--- | 
| F | 0*0* |
| D | 0*1* |
| C | 1*0* |
| A | 1*0* | 
| B | 1*1* |
| E | 1*1* |

```dot:digraph
FD [label="F, D",fillcolor=khaki]
CABE [label="C, A, B, E",fillcolor=khaki]
CA [label="C, A",fillcolor=khaki]
BE [label="B, E",fillcolor=khaki]
FDCABE->FD [label=" 0"]
FDCABE->CABE [label=" 1"]
FD->F [label=" 0"]
FD->D [label=" 1"]
CABE->CA [label=" 0"]
CABE->BE [label=" 1"]
FDCABE [shape=point]
FD [shape=point]
CABE [shape=point]
```

##### Krok 3

Seznamy *(F)* a *(D)* již rozložit nejdou, zbývá zpracovat seznamy *(C,A)* a *(B,E)*. Opět je oba rozdělíme na poloviny s příbližně stejným součtem četností.

- *(F,D)* - součet četností: 5 + 4 = 9
  - *(F)* - součet četností: 5
  - *(D)* - součet četností: 4
- *(C,A,B,E)* - součet četností: 4 + 3 + 3 + 2 = 12
  - *(C,A)* - součet četností: 4 + 3 = 7
    - *(C)* - součet četností: 4
    - *(A)* - součet četností: 3
  - *(B,E)* - součet četností: 3 + 2 = 5
    - *(B)* - součet četností: 3
    - *(E)* - součet četností: 2

| Symbol | Kód |
|---|---|
| F | 00 |
| D | 01 |
| C | 10*0* |
| A | 10*1* |
| B | 11*0* |
| E | 11*1* |

```dot:digraph
FD [label="F, D",fillcolor=khaki]
CABE [label="C, A, B, E",fillcolor=khaki]
CA [label="C, A",fillcolor=khaki]
BE [label="B, E",fillcolor=khaki]
FDCABE->FD [label=" 0"]
FDCABE->CABE [label=" 1"]
FD->F [label=" 0"]
FD->D [label=" 1"]
CA->C [label=" 0"]
CA->A [label=" 1"]
BE->B [label=" 0"]
BE->E [label=" 1"]
CABE->CA [label=" 0"]
CABE->BE [label=" 1"]
FDCABE [shape=point]
FD [shape=point]
CABE [shape=point]
CA [shape=point]
BE [shape=point]
```

##### Výsledek

| Symbol | Kód |
|---|---|
| F | 00 |
| D | 01 |
| C | 100 |
| A | 101 |
| B | 110 |
| E | 111 |

### Reference

- http://www.stringology.org/DataCompression/sf/index_cs.html
- http://en.wikipedia.org/wiki/Shannon-Fano_coding