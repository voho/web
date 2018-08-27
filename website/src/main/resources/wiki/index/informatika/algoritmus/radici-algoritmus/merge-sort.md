## Merge sort (řazení sléváním)

Merge sort (řazení sléváním) je řadící algoritmus, založený na myšlence rekurzivního dělení [pole](wiki/datova-struktura-pole) na dvě poloviny a jejich následném spojování (slévání) do neklesající posloupnosti. Popsal jej **John von Neumann** v roce 1945. Merge sort je **přirozený** a **stabilní**.

Mezi výhody tohoto řadícího algorimu patří logaritmická [časová složitost](wiki/asymptoticka-slozitost) € O(n \cdot \log n) €. Nevýhodou je nutnost alokace druhého pomocného pole o stejné velikosti, jako je pole řazené.

Základní kroky algoritmu:

- rekurzivní dělení pole na poloviny, až do triviálního případu (jeden prvek)
- jeden prvek se triviálně pokládá za seřazený
- zpětné spojení obou seřazených polovin do neklesající posloupnosti

```dot:digraph
ratio=0.6;
node [shape=record];
edge [dir=none];
A1 [label="38|27|43|3|9|82|10"];
A1 -> B1;
A1 -> B2;
B1 [label="38|27|43|3"];
B2 [label="9|82|10"];
B1 -> C1;
B1 -> C2;
B2 -> C3;
B2 -> C4;
C1 [label="38|27"];
C2 [label="43|3"];
C3 [label="9|82"];
C4 [label="10"];
C1 -> D1;
C1 -> D2;
C2 -> D3;
C2 -> D4;
C3 -> D5;
C3 -> D6;
C4 -> D7;
D1 [label="38"];
D2 [label="27"];
D3 [label="43"];
D4 [label="3"];
D5 [label="9"];
D6 [label="82"];
D7 [label="10"];

D1 -> E1 [color=blue, penwidth=5];
D2 -> E1 [color=blue, penwidth=5];
D3 -> E2 [color=blue, penwidth=5];
D4 -> E2 [color=blue, penwidth=5];
D5 -> E3 [color=blue, penwidth=5];
D6 -> E3 [color=blue, penwidth=5];
D7 -> E4 [color=blue, penwidth=5];
E1 [label="27|38"];
E2 [label="3|43"];
E3 [label="9|82"];
E4 [label="10"];
E1 -> F1 [color=blue, penwidth=5];
E2 -> F1 [color=blue, penwidth=5];
E3 -> F2 [color=blue, penwidth=5];
E4 -> F2 [color=blue, penwidth=5];
F1 [label="3|27|38|43"];
F2 [label="9|10|82"];
F1 -> G1 [color=blue, penwidth=5];
F2 -> G1 [color=blue, penwidth=5];
G1 [label="3|9|10|27|38|43|82"];
```

### Implementace (Java)

```include:java
MergeSort.java
```

### Implementace (Prolog)

```prolog
% ROZDĚLOVÁNÍ
% ===========

% triviální případy
divide([],[],[]).
divide([A],[A],[]).
% první prvek seznamu vložit na začátek druhého, druhý prvek na začátek třetího, poté rekurzivně zopakovat se zbytkem seznamu
divide([H1,H2|T],[H1|T1],[H2|T2]) :- divide(T,T1,T2).

% SLÉVÁNÍ
% =======

% triviální případy
merge(A,[],A).
merge([],A,A).
% je-li prvek H1 menší nebo roven H2, vložit jej na začátek seznamu a rekurzivně opakovat
merge([H1|T1],[H2|T2],[H1|Y]) :- H1 =< H2,merge(T1,[H2|T2],Y).
% je-li prvek H1 větší než H2, vložit jej na konec seznamu a rekurzivně opakovat
merge([H1|T1],[H2|T2],[H2|Y]) :- H1 > H2,merge([H1|T1],T2,Y).

% ŘAZENÍ
% ======

% triviální případy
sorter([],[]).
sorter([A],[A]).
% zajistit správné pořadí dvojice prvků
sorter([A,B],[A,B]) :- A =< B.
sorter([A,B],[B,A]) :- A > B.
% obecný případ - rozdělit pole na dvě části, seřadit je a poté spojit dohromady
sorter(A,B) :- divide(A,P1,P2),sorter(P1,R1),sorter(P2,R2),merge(R1,R2,B).
```

### Ukázkový běh

| Pole | Operace
|---|---
| 5 3 2 1 4 2 | Toto pole chceme seřadit merge sortem.
| 5 3 2 . 1 4 2 | Rozdělení na poloviny.
| 5 3 . 2 .. 1 4 . 2 | Rozdělení na další poloviny.
| 5 . 3 .. 2 ... 1 . 4 .. 2 | Rozdělení na další poloviny (nyní máme jednoprvková pole).
| 3 5 . 2 .. 1 4 . 2 | Slévání nejmenších (triviálních) polí do větších.
| 2 3 5 . 1 2 4 | Slévání dalších polí do větších.
| 1 2 2 3 4 5 | Slévání dalších polí do větších.
| 1 2 2 3 4 5 | Pole je seřazeno.
