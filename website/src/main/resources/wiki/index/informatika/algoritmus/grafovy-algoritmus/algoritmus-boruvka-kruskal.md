## Algoritmus Borůvka-Kruskal

Borůvkův/Kruskalův [algoritmus](wiki/algoritmus) slouží k nalezení **minimální kostry** souvislého ohodnoceného [grafu](wiki/graf) a navzájem si jsou natolik podobné, že je lze považovat za jeden algoritmus. Liší se tím, že Borůvkův algoritmus přidává hrany, zatímco Kruskalův uzly. Vstupem obou algoritmů je souvislý ohodnocený graf, výstupem minimální kostra (minimum spanning tree). Oba algoritmy jsou zástupci tzv. **hladových algoritmů** (greedy algorithms).

Borůvkův algoritmus je pojmenovaný po **Otakaru Borůvkovi** (1899-1995), který jej publikoval v roce 1926 ve článku "O jistém problému minimálním". V článku navrhl algoritmus pro návrh efektivní elektrické sítě na Moravě, což je jeden z pěkných příkladů aplikace teorie grafů a vyhledávání minimální kostry v praxi. Druhým nezávislým autorem je americký matematik **Joseph Kruskal**, narozený v roce 1928.

### Kroky algoritmu

- Vytvoř seznam *L* hran vstupního grafu *G* a seřaď je do neklesajícího pořadí podle jejich ohodnocení.
- Vytvoř podgraf *S* grafu *G*, který je zpočátku prázdný.
- Pro každou hranu ze seznamu *L* proveď:
 - Nevznikne-li v grafu *S* přidáním této hrany kružnice, přidej ji do grafu *S* (včetně krajních uzlů).
- Graf *S* je minimální kostrou grafu *G*.

### Příklad

#### Inicializace

Nejprve vytvoříme seznam *L* hran vstupního grafu *G* a seřadíme je do neklesajícího pořadí podle ohodnocení hran (pořadí hran se stejným ohodnocením není důležité). Podgraf *S* grafu *G* (označený zelenou barvou) je prázdný, zbývá spojit 6 uzlů.

| D,E (1) | B,C (1) | B,D (2) | B,E (3) | B,F (3) | A,B (5) | D,F (9)
|---

```dot:graph
rankdir=LR
A--B [label=" 5"]
B--F [label=" 3"]
D--F [label=" 9"]
B--D [label=" 2"]
B--E [label=" 3"]
D--E [label=" 1"]
B--C [label=" 1"]
{rank=same; A;B;C;}
{rank=same; F;D;}
```

#### Krok 1

První hrana v seznamu *L* je *(D,E)*. V grafu *S* jejím přidáním nevznikne kružnice, tak ji do něho přidáme. Podgraf *S* obsahuje první hranu, zbývá spojit 4 uzly.

| **D,E (1)** | B,C (1) | B,D (2) | B,E (3) | B,F (3) | A,B (5) | D,F (9)
|---

```dot:graph
rankdir=LR
A--B [label=" 5"]
B--F [label=" 3"]
D--F [label=" 9"]
B--D [label=" 2"]
B--E [label=" 3"]
D--E [label=" 1",color=forestgreen,penwidth=3]
B--C [label=" 1"]
{rank=same; A;B;C;}
{rank=same; F;D;}

E[fillcolor=seagreen1]
D[fillcolor=seagreen1]
```

#### Krok 2

Další hrana v seznamu *L* je *(B,C)*. Jejím přidáním nevznikne v grafu *S* kružnice, tak ji opět přidáme. Podgraf *S* obsahuje dvě hrany, zbývá spojit 2 uzly.

| - | **B,C (1)** | B,D (2) | B,E (3) | B,F (3) | A,B (5) | D,F (9)
|---

```dot:graph
rankdir=LR
A--B [label=" 5"]
B--F [label=" 3"]
D--F [label=" 9"]
B--D [label=" 2"]
B--E [label=" 3"]
D--E [label=" 1",color=forestgreen,penwidth=3]
B--C [label=" 1",color=forestgreen,penwidth=3]
{rank=same; A;B;C;}
{rank=same; F;D;}

E[fillcolor=seagreen1]
D[fillcolor=seagreen1]
B[fillcolor=seagreen1]
C[fillcolor=seagreen1]
```

#### Krok 3

Další hrana v seznamu *L* je *(B,D)*. Jejím přidáním nevznikne v grafu *S* kružnice, tak ji přidáme. Podgraf *S* obsahuje tři hrany, zbývá spojit 2 uzly.

| - | - | **B,D (2)** | B,E (3) | B,F (3) | A,B (5) | D,F (9)
|---

```dot:graph
rankdir=LR
A--B [label=" 5"]
B--F [label=" 3"]
D--F [label=" 9"]
B--D [label=" 2",color=forestgreen,penwidth=3]
B--E [label=" 3"]
D--E [label=" 1",color=forestgreen,penwidth=3]
B--C [label=" 1",color=forestgreen,penwidth=3]
{rank=same; A;B;C;}
{rank=same; F;D;}

E[fillcolor=seagreen1]
D[fillcolor=seagreen1]
B[fillcolor=seagreen1]
C[fillcolor=seagreen1]
```
#### Krok 4

Další hrana v seznamu *L* je *(B,E)*. Jejím přidáním by však v grafu *S* vznikla kružnice, tak ji přeskočíme. Podgraf *S* stále obsahuje tři hrany, zbývá spojit 2 uzly

| - | - | - | **B,E (3)** | B,F (3) | A,B (5) | D,F (9)
|---

```dot:graph
rankdir=LR
A--B [label=" 5"]
B--F [label=" 3"]
D--F [label=" 9"]
B--D [label=" 2",color=forestgreen,penwidth=3]
B--E [label=" 3",color=red,penwidth=3]
D--E [label=" 1",color=forestgreen,penwidth=3]
B--C [label=" 1",color=forestgreen,penwidth=3]
{rank=same; A;B;C;}
{rank=same; F;D;}

E[fillcolor=seagreen1]
D[fillcolor=seagreen1]
B[fillcolor=seagreen1]
C[fillcolor=seagreen1]
```

#### Krok 5

Další hrana v seznamu *L* je *(B,F)*. Jejím přidáním nevznikne v grafu *S* kružnice, tak ji přidáme. Podgraf *S* obsahuje čtyři hrany, zbývá spojit poslední uzel.

| - | - | - | - | **B,F (3)** | A,B (5) | D,F (9)
|---

```dot:graph
rankdir=LR
A--B [label=" 5"]
B--F [label=" 3",color=forestgreen,penwidth=3]
D--F [label=" 9"]
B--D [label=" 2",color=forestgreen,penwidth=3]
B--E [label=" 3",color=red,penwidth=3]
D--E [label=" 1",color=forestgreen,penwidth=3]
B--C [label=" 1",color=forestgreen,penwidth=3]
{rank=same; A;B;C;}
{rank=same; F;D;}

E[fillcolor=seagreen1]
D[fillcolor=seagreen1]
B[fillcolor=seagreen1]
C[fillcolor=seagreen1]
F[fillcolor=seagreen1]
```

#### Krok 6

Další hrana v seznamu *L* je *(A,B)*. Jejím přidáním nevznikne v grafu *S* kružnice, tak ji přidáme. Podgraf *S* obsahuje pět hran, všechny uzly jsou spojeny!

| - | - | - | - | - | **A,B (5)** | D,F (9)
|---

```dot:graph
rankdir=LR
A--B [label=" 5",color=forestgreen,penwidth=3]
B--F [label=" 3",color=forestgreen,penwidth=3]
D--F [label=" 9"]
B--D [label=" 2",color=forestgreen,penwidth=3]
B--E [label=" 3",color=red,penwidth=3]
D--E [label=" 1",color=forestgreen,penwidth=3]
B--C [label=" 1",color=forestgreen,penwidth=3]
{rank=same; A;B;C;}
{rank=same; F;D;}

E[fillcolor=seagreen1]
D[fillcolor=seagreen1]
B[fillcolor=seagreen1]
C[fillcolor=seagreen1]
F[fillcolor=seagreen1]
A[fillcolor=seagreen1]
```

#### Krok 7

Poslední hrana v seznamu *L* je *(D,F)*. Jejím přidáním však vznikne v grafu *S* kružnice, tak ji přidat nemůžeme. Kromě toho je již minimální kostra hotová. Podgraf *S* obsahuje stále pět hran, všechny uzly jsou spojeny.

| - | - | - | - | - | - | **D,F (9)**
|---

```dot:graph
rankdir=LR
A--B [label=" 5",color=forestgreen,penwidth=3]
B--F [label=" 3",color=forestgreen,penwidth=3]
D--F [label=" 9",color=red,penwidth=3]
B--D [label=" 2",color=forestgreen,penwidth=3]
B--E [label=" 3",color=red,penwidth=3]
D--E [label=" 1",color=forestgreen,penwidth=3]
B--C [label=" 1",color=forestgreen,penwidth=3]
{rank=same; A;B;C;}
{rank=same; F;D;}

E[fillcolor=seagreen1]
D[fillcolor=seagreen1]
B[fillcolor=seagreen1]
C[fillcolor=seagreen1]
F[fillcolor=seagreen1]
A[fillcolor=seagreen1]
```

Výpočet je dokončen, vytvořený graf *S* (zelené hrany) je minimální kostra grafu *G*.

### Složitost

Borůvkův-Kruskalův algoritmus je možné jednoduše implementovat se složitostí € O(E \log |V|) €. Použitím pokročilejších datových struktur a efektivnějších řadících algoritmů je však možné složitost ještě dále snížit.

### Reference

- Dr. Jeane Stynes, Computer Science
- http://www.ics.uci.edu/~eppstein/161/960206.html
- http://www.cam.zcu.cz/~ryjacek/students/DMA/skripta/