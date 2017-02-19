## Algoritmus Jarník-Prim

![Prof. RNDr. Vojtěch Jarník, DrSc.](jarnik.png) {.right}

Jarníkův-Primův algoritmus je grafový algoritmus pro vyhledávání **minimální kostry souvislého ohodnoceného grafu**. Je pojmenovaný podle českého matematika **Vojtěcha Jarníka** (1897-1970) a amerického matematika **Roberta Claye Prima** (narozen 1921). Oba tento algoritmus sice popsali nezávisle na sobě, ale první byl Jarník (1930).

### Kroky algoritmu

- Vytvoř prázdný graf *T*.
- Do grafu *T* vlož libovolný jeden uzel vstupního grafu *G*.
- Dokud má graf *T* méně uzlů než vstupní graf *G*, opakuj:
  - Najdi hranu s minimálním ohodnocením, spojující graf *T* s rozdílem grafů *G-T*
  - Přidej tuto hranu do grafu *T*.

### Příklad

#### Inicializace

Graf *T*, který Jarníkův-Primův algoritmus postupně generuje, je zpočátku prázdný. Modře jsou označeny hrany, které algoritmus v daném kroku ověřuje, zelený je graf *T* a žlutý naposledy přidaný uzel.

```dot:graph
rankdir=LR
A--F [label=" 1"]
F--E [label=" 2"]
E--B [label=" 3"]
A--B [label=" 5"]
B--D [label=" 2"]
E--D [label=" 1"]
B--C [label=" 1"]
B--G [label=" 3"]
D--G [label=" 9"]
{rank=same; A;F;E;}
{rank=same; B;D;}
```

#### Krok 1

Náhodně je vybrán a do grafu *T* přidán uzel *A*. Bez prvního uzlu nemůže být algoritmus spuštěn. Na konkrétním uzlu skutečně nezáleží, protože budou nakonec v kostře zahrnuty všechny.

Graf *T* má menší počet uzlů než graf *G*, algoritmus tedy pokračuje.

```dot:graph
rankdir=LR
A--F [label=" 1"]
F--E [label=" 2"]
E--B [label=" 3"]
A--B [label=" 5"]
B--D [label=" 2"]
E--D [label=" 1"]
B--C [label=" 1"]
B--G [label=" 3"]
D--G [label=" 9"]
{rank=same; A;F;E;}
{rank=same; B;D;}
A [fillcolor=greenyellow]
```

#### Krok 2

Nyní algoritmus vyhledá hranu s nejmenším ohodnocením, která graf *T* spojuje se zbývajícími uzly (rozdíl grafů *G-T*). Kandidáty jsou hrany *(A-B)*, *(A-F)*. Vybrána je hrana *(A-F)*, protože má menší ohodnocení. Graf *T* má stále menší počet uzlů než graf *G*, algoritmus tedy pokračuje.

```dot:graph
rankdir=LR
A--F [label=" 1",penwidth=3,color=blue]
F--E [label=" 2"]
E--B [label=" 3"]
A--B [label=" 5",penwidth=3,color=blue]
B--D [label=" 2"]
E--D [label=" 1"]
B--C [label=" 1"]
B--G [label=" 3"]
D--G [label=" 9"]
{rank=same; A;F;E;}
{rank=same; B;D;}
A [fillcolor=beige]
F [fillcolor=greenyellow]
```

#### Krok 3

Dalšími kandidáty na rozšíření grafu *T* jsou hrany *(F-E)*, *(A-B)*. Vybrána je hrana *(F-E)*, protože má menší ohodnocení. Graf *T* má stále menší počet uzlů než graf *G*, algoritmus tedy pokračuje.

```dot:graph
rankdir=LR
A--F [label=" 1",penwidth=3,color=forestgreen]
F--E [label=" 2",penwidth=3,color=blue]
E--B [label=" 3"]
A--B [label=" 5",penwidth=3,color=blue]
B--D [label=" 2"]
E--D [label=" 1"]
B--C [label=" 1"]
B--G [label=" 3"]
D--G [label=" 9"]
{rank=same; A;F;E;}
{rank=same; B;D;}
A [fillcolor=beige]
F [fillcolor=beige]
E [fillcolor=greenyellow]
```
#### Krok 4

Dalšími kandidáty na rozšíření grafu *T* jsou hrany *(A-B)*, *(E-B)*, *(E-D)*. Vybrána je hrana *(E-D)*, protože má menší ohodnocení. Graf *T* má stále menší počet uzlů než graf *G*, algoritmus tedy pokračuje.

```dot:graph
rankdir=LR
A--F [label=" 1",penwidth=3,color=forestgreen]
F--E [label=" 2",penwidth=3,color=forestgreen]
E--B [label=" 3",penwidth=3,color=blue]
A--B [label=" 5",penwidth=3,color=blue]
B--D [label=" 2"]
E--D [label=" 1",penwidth=3,color=blue]
B--C [label=" 1"]
B--G [label=" 3"]
D--G [label=" 9"]
{rank=same; A;F;E;}
{rank=same; B;D;}
A [fillcolor=beige]
F [fillcolor=beige]
E [fillcolor=beige]
D [fillcolor=greenyellow]
```

#### Krok 5

Dalšími kandidáty na rozšíření grafu *T* jsou hrany *(A-B)*, *(E-B)*, *(D-B)*, *(D-G)*. Vybrána je hrana *(D-B)*, protože má menší ohodnocení. Graf *T* má stále menší počet uzlů než graf *G*, algoritmus tedy pokračuje.

```dot:graph
rankdir=LR
A--F [label=" 1",penwidth=3,color=forestgreen]
F--E [label=" 2",penwidth=3,color=forestgreen]
E--B [label=" 3",penwidth=3,color=blue]
A--B [label=" 5",penwidth=3,color=blue]
B--D [label=" 2",penwidth=3,color=blue]
E--D [label=" 1",penwidth=3,color=forestgreen]
B--C [label=" 1"]
B--G [label=" 3"]
D--G [label=" 9",penwidth=3,color=blue]
{rank=same; A;F;E;}
{rank=same; B;D;}
A [fillcolor=beige]
F [fillcolor=beige]
E [fillcolor=beige]
D [fillcolor=beige]
B [fillcolor=greenyellow]
```

#### Krok 6

Dalšími kandidáty na rozšíření grafu *T* jsou hrany *(B-C)*, *(B-G)*, *(D-G)*. Vybrána je hrana *(B-C)*, protože má menší ohodnocení. Všimněte si, že hrany *(A-B)* a *(E-B)* uvažovány nejsou, protože nespojují graf *T* s rozdílem grafů *G-T*. Graf *T* má však stále menší počet uzlů než graf *G*, algoritmus tedy pokračuje.

```dot:graph
rankdir=LR
A--F [label=" 1",penwidth=3,color=forestgreen]
F--E [label=" 2",penwidth=3,color=forestgreen]
E--B [label=" 3"]
A--B [label=" 5"]
B--D [label=" 2",penwidth=3,color=forestgreen]
E--D [label=" 1",penwidth=3,color=forestgreen]
B--C [label=" 1",penwidth=3,color=blue]
B--G [label=" 3",penwidth=3,color=blue]
D--G [label=" 9",penwidth=3,color=blue]
{rank=same; A;F;E;}
{rank=same; B;D;}
A [fillcolor=beige]
F [fillcolor=beige]
E [fillcolor=beige]
D [fillcolor=beige]
B [fillcolor=beige]
C [fillcolor=greenyellow]
```

#### Krok 7

Dalšími kandidáty na rozšíření grafu *T* jsou hrany *(B-G)*, *(D-G)*. Vybrána je hrana *(B-G)*, protože má menší ohodnocení. Graf *T* má stejný počet uzlů jako graf *G*, výpočet může skončit.

```dot:graph
rankdir=LR
A--F [label=" 1",penwidth=3,color=forestgreen]
F--E [label=" 2",penwidth=3,color=forestgreen]
E--B [label=" 3"]
A--B [label=" 5"]
B--D [label=" 2",penwidth=3,color=forestgreen]
E--D [label=" 1",penwidth=3,color=forestgreen]
B--C [label=" 1",penwidth=3,color=forestgreen]
B--G [label=" 3",penwidth=3,color=blue]
D--G [label=" 9",penwidth=3,color=blue]
{rank=same; A;F;E;}
{rank=same; B;D;}
A [fillcolor=beige]
F [fillcolor=beige]
E [fillcolor=beige]
D [fillcolor=beige]
B [fillcolor=beige]
C [fillcolor=beige]
G [fillcolor=greenyellow]
```

#### Krok 8

Výpočet je dokončen. Graf *T* je minimální kostra grafu *G*.

```dot:graph
rankdir=LR
A--F [label=" 1",penwidth=3,color=forestgreen]
F--E [label=" 2",penwidth=3,color=forestgreen]
E--B [label=" 3"]
A--B [label=" 5"]
B--D [label=" 2",penwidth=3,color=forestgreen]
E--D [label=" 1",penwidth=3,color=forestgreen]
B--C [label=" 1",penwidth=3,color=forestgreen]
B--G [label=" 3",penwidth=3,color=forestgreen]
D--G [label=" 9"]
{rank=same; A;F;E;}
{rank=same; B;D;}
A [fillcolor=beige]
F [fillcolor=beige]
E [fillcolor=beige]
D [fillcolor=beige]
B [fillcolor=beige]
C [fillcolor=beige]
G [fillcolor=beige]
```

### Časová složitost

Jarníkův-Primův algoritmus lze naimplementovat se složitostí € O(|E| + |V| \log (|V|)) €.

### Reference

- Dr. Jeane Stynes, Computer Science
- http://www.ics.uci.edu/~eppstein/161/960206.html