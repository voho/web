## Algoritmus Dijkstra

![Edsger Dijkstra (1969)](dijkstra.jpg){.right}

Dijkstrův algoritmus je grafový algoritmus vytvořený nizozemským vědcem **Edsgerem Wybem Dijkstrou** (1930-2002), který jej navrhl v Amsterdamu po nakupování se svou snoubenkou během dvaceti minut, a to bez tužky a papíru. 
Slouží k vyhledání **nejkratší cesty** z počátečního uzlu do všech ostatních uzlů **ohodnoceného grafu**. Poradí si však jen s nezáporným ohodnocením hran. Často se používá v **routovacích protokolech**, například v algoritmu OSPF (Open Shortest Path First).

Vstupem algoritmu je nezáporně ohodnocený graf a počáteční uzel, výstupem je datová struktura (například pole nebo tabulka) obsahující délky nejkratších cest z počátečního uzlu do ostatních uzlů.

> One morning I was shopping in Amsterdam with my young fiancée, and tired, we sat down on the café terrace to drink a cup of coffee and I was just thinking about whether I could do this, and I then designed the algorithm for the shortest path. As I said, it was a twenty-minute invention. *Edsger Dijkstra*

### Kroky algoritmu

Vstup
: ohodnocený graf *G*, počáteční uzel *s*

Výstup
: asociativní pole *D(u)*, udávající nejkratší vzdálenost mezi uzlem *s* a uzlem *u*

- **INICIALIZACE:**
- Vytvoř množinu uzlů *X*.
- Do množiny *X* vlož počáteční uzel *s*.
- Vytvoř asociativní pole čísel pro každý uzel *D(u)*.
- Inicializuj hodnoty pole *D* takto:
  - pro počáteční uzel *s* = 0
  - pro každý uzel *u* sousedící s počátečním uzlem *s* = ohodnocení hrany *(s,u)*
  - pro ostatní uzly = nekonečno
- **VÝPOČET:**
- Dokud nejsou v množině *X* všechny uzly grafu *G*, opakuj:
  - Najdi uzel *w* s minimální hodnotou *D(w)*.
  - Přidej uzel *w* do množiny *X*.
  - Pro každý uzel *u* sousedící s uzlem *w* který není v množině *X* proveď:
    - hodnota *D(u)* je minimum ze stávající hodnoty a *D(w)* plus ohodnocení hrany *(w,u)*.

### Příklad

#### Inicializace

Vstupem algoritmu je následující graf, počátečním uzlem *s* je uzel *A*.

```dot:graph
{rank=same; B; E;}
{rank=same; A; C; F;}
{rank=same; D;}
B--E [label=" 3"]
A--B [label=" 10"]
C--B [label=" 5"]
C--E [label=" 8"]
F--E [label=" 1"]
A--C [label=" 3"]
D--A [label=" 4"]
D--C [label=" 1"]
D--F [label=" 7"]
A [fillcolor=greenyellow]
```

#### Všechny kroky v tabulce

Každý řádek tabulky představuje jeden krok algoritmu, ve sloupcích jsou uzly grafu *G* a množina *X*. Jednotlivé buňky obsahují hodnotu *D(u)* odpovídajícího uzlu *u* ve sloupci. Zkratka "max" označuje nekonečno.

| A | B | C | D | E | F | množina X
|---|---|---|---|---|---|---
| 0 | 10, *A* | 3, *A* | 4, *A* | max | max | A
| - | 8, *C* | - | 4, *A* | 11, *C* | max | A, C
| - | 8, *C* | - | - | 11, *C* | 11, *D* | A, C, D
| - | - | - | - | 11, *C* | 11, *D* | A, C, D, B
| - | - | - | - | - | 11, *D* | A, C, D, B, E
| - | - | - | - | - | - | A, C, D, B, E, F

#### Výsledek

| A | B | C | D | E | F
|---|---|---|---|---|---
| 0 | 8, *C* | 3, *A* | 4, *A* | 11, *C* | 11, *D*

### Složitost

Dijkstrův algoritmus lze naimplementovat s asymptotickou složitostí € O(E + V \log V) €, kde €V€ je počet uzlů a €E€ je počet hran.

### Implementace

```include:java
Dijkstra.java
```

### Reference

- Dr. Jeane Stynes, Computer Science
- http://www.cs.umd.edu/~shankar/417-F01/Slides/chapter4a-aus/sld012.htm
- http://www.cse.wustl.edu/cs/cs/archive/CS423_FL00/Chapter4a/img11.html