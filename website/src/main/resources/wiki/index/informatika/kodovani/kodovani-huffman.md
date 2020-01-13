## Huffmanovo kódování

Huffmanův algoritmus je jednoduchý [grafový algoritmus](wiki/grafovy-algoritmus) pro kompresi dat. Vstupem algoritmu je [množina](wiki/mnozina) znaků spolu s četnostmi jejich výskytu ve zprávě. Výstupem je Huffmanův strom, pomocí kterého lze jednotlivé znaky zakódovat do binárního kódu i dekódovat zpět. Během přenosu zprávy se však musí kromě zakódovaného binárního řetězce přenést i použitý Huffmanův strom (není-li dohodnutý předem).

Komprese spočívá v tom, že se častěji používané znaky zakódují menším počtem bitů než znaky méně časté. Výsledný kód se nazývá **prefixový**, protože žádné kódové slovo není prefixem nějakého jiného. To zaručuje jednoznačnost kódování i dekódování.

### Pseudokód

- Vytvoř [graf](wiki/graf), který se skládá z €n€ kořenových stromů. Každý strom €T_i€ se skládá z jednoho kořenového uzlu reprezentujícího jeden symbol €S_i€. Váha stromu €w(T_i)€ je rovna četnosti použití daného symbolu €S_i€ ve zprávě.
- Dokud není graf souvislý, opakuj:
  - Najdi dva různé stromy €T_1€ a €T_2€ s minimální váhou.
  - Vytvoř nový kořenový strom €T_3€ s novým kořenem, jehož levý potomek je €T_1€ a pravý potomek €T_2€. Váha tohoto nového stromu je rovna součtu vah stromů €T_1€ a €T_2€, tedy €w(T_3)=w(T_1)+w(T_2)€.
  - Odeber oba dva stromy €T_1€, €T_2€ z grafu.
- Výsledný binární kořenový strom se nazývá **Huffmanův strom**. Každou jeho levou hranu označ nulou a pravou hranu jedničkou. Kód pro každý znak je dán cestou z kořene do odpovídajícího listu tak, že se postupně zapisují binární čísla u navštívených hran.

Stejné znaky se stejnými četnostmi mohou díky rozdílné implementaci vytvořit i několik rozdílných Huffmanových stromů - délka kódových slov pro stejné symboly je však shodná.

### Příklad

Chceme zakódovat a zkomprimovat zprávu "AHOJ, JAK SE MAS, KAMARADE?". Tato zpráva je dlouhá 27 znaků a obsahuje 13 různých symbolů.

#### Triviální kód

Následující tabulka udává jednotlivé znaky, jejich četnost a triviální kódování:

| Znak | Četnost | Triviální kód |
|---|---|---|
| mezera | 4 | 0000 |
| , | 2 | 0001 |
| ? | 1 | 0010 |
| A | 6 | 0011 |
| D | 1 | 0100 |
| E | 2 | 0101 |
| H | 1 | 0110 |
| J | 2 | 0111 |
| K | 2 | 1000 |
| M | 2 | 1001 |
| O | 1 | 1010 |
| R | 1 | 1011 |
| S | 2 | 1100 |

Zpráva by se pomocí triviálního kódování zakódovala do následujícího řetězce:

`0011,  0110,  1010,  0111,  0001,  0000,  0111,  0011, 1000,  0000,  1100,  0101,  0000,  1001,  0011,  1100, 0001,  0000,  1000,  0011,  1001,  0011,  1011,  0011, 0100,  0101,  0010`

Výpočtem zjistíme, že výsledná zpráva má velikost 108 bitů.

#### Huffmannův kód

Prvním krokem kódování je sestavení Huffmanova stromu. V prvním kroku máme stromy představující symboly a váhy jsou rovny jejich četnosti.

```dot:digraph
dpi=60
node[shape=circle,margin=0]
_
DOT [label=",",fillcolor=khaki]
ASK [label="?",fillcolor=khaki]
A [fillcolor=khaki]
D [fillcolor=khaki]
E [fillcolor=khaki]
H [fillcolor=khaki]
J [fillcolor=khaki]
K [fillcolor=khaki]
M [fillcolor=khaki]
O [fillcolor=khaki]
R [fillcolor=khaki]
S [fillcolor=khaki]
```

Následující kroky mohou vypadat například takto:

- *?(1)* + *D(1)* s výslednou vahou 2
- *H(1)* + *O(1)* s výslednou vahou 2
- *R(1)* + *,(2)* s výslednou vahou 3
- *?D(2)* + *E(2)* s výslednou vahou 4
- *HO(2)* + *J(2)* s výslednou vahou 4
- *K(2)* + *M(2)* s výslednou vahou 4
- *R,(3)* + *S(2)* s výslednou vahou 5
- *_(4)* + *?DE(4)* s výslednou vahou 8
- *HOJ(4)* + *KM(4)* s výslednou vahou 8
- *A(6)* + *R,S(5)* s výslednou vahou 11
- *_?DE(8)* + *HOJKM(8)* s výslednou vahou 16
- *_?DEHOJKM(16)* + *AR,S(11)* s výslednou vahou 27
- strom je dokončen

Výsledný Huffmanův strom:

```dot:digraph
dpi=60
rankdir=LR
node[shape=circle,margin=0.1]

ASKD [label="2"]
ASKD->ASK [label=" 0"]
ASKD->D [label=" 1"]

ASKDE [label="4"]
ASKDE->ASKD [label=" 0"]
ASKDE->E [label=" 1"]

_ASKDE [label="8"]
_ASKDE->_ [label=" 0"]
_ASKDE->ASKDE [label=" 1"]

HO [label="2"]
HO->H [label=" 0"]
HO->O [label=" 1"]

HOJ [label="4"]
HOJ->HO [label=" 0"]
HOJ->J [label=" 1"]

KM [label="4"]
KM->K [label=" 0"]
KM->M [label=" 1"]

HOJKM [label="8"]
HOJKM->HOJ [label=" 0"]
HOJKM->KM [label=" 1"]

_ASKDEHOJKM [label="16"]
_ASKDEHOJKM->_ASKDE [label=" 0"]
_ASKDEHOJKM->HOJKM [label=" 1"]

RDOT [label="3"]
RDOT->R [label=" 0"]
RDOT->DOT [label=" 1"]

RDOTS [label="5"]
RDOTS->RDOT [label=" 0"]
RDOTS->S [label=" 1"]

ARDOTS [label="11"]
ARDOTS->A [label=" 0"]
ARDOTS->RDOTS [label=" 1"]

_ASKDEHOJKMARDOTS [label="27"]
_ASKDEHOJKMARDOTS->_ASKDEHOJKM [label=" 0"]
_ASKDEHOJKMARDOTS->ARDOTS [label=" 1"]

_
DOT [label=", (2)",fillcolor=khaki]
ASK [label="? (1)",fillcolor=khaki]
A [label="A(6)",fillcolor=khaki]
D [label="D(1)",fillcolor=khaki]
E [label="E(2)",fillcolor=khaki]
H [label="H(1)",fillcolor=khaki]
J [label="J(2)",fillcolor=khaki]
K [label="K(2)",fillcolor=khaki]
M [label="M(2)",fillcolor=khaki]
O [label="O(1)",fillcolor=khaki]
R [label="R(1)",fillcolor=khaki]
S [label="S(2)",fillcolor=khaki]
```

Výsledná kódovací tabulka:

| Znak | Četnost | Triviální kód (pro srovnání) | Huffmanův kód |
|---|---|---|---|
| mezera | 4 | 0000 | 000 |
| , | 2 | 0001 | 1100 |
| ? | 1 | 0010 | 00100 |
| A | 6 | 0011 | 10 |
| D | 1 | 0100 | 00101 |
| E | 2 | 0101 | 0011 |
| H | 1 | 0110 | 01010 |
| J | 2 | 0111 | 0100 |
| K | 2 | 1000 | 0110 |
| M | 2 | 1001 | 0111 |
| O | 1 | 1010 | 01011 |
| R | 1 | 1011 | 1101 |
| S | 2 | 1100 | 111 |

Pomocí Huffmanova kódu se zpráva zakóduje takto:

`10,  01010,  01011,  0100,  000,  1100,  0100,  10, 0110,  1100,  111,  0011,  1100,  0111,  10,  111, 000,  1100,  0110,  10,  0111,  10,  1101,  10, 00101,  0011,  00100`

Výpočtem zjistíme, že výsledná zpráva má velikost 96 bitů. Spolu se zprávou je však nutné přenést i odpovídající Huffmanův strom (není-li dohodnutý předem) a tak se výhoda Huffmanova kódování projeví až u zpráv určité délky.

### Reference

- Dr. Jeanne Stynes, Computer Science
