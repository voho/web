## Merge sort (řazení sléváním)

Merge sort (řazení sléváním) je řadící algoritmus, založený na myšlence rekurzivního dělení pole na dvě poloviny a jejich následném spojování (slévání) do neklesající posloupnosti. Popsal jej **John von Neumann** v roce 1945. Merge sort je **přirozený** a **stabilní**.

Mezi výhody tohoto řadícího algorimu patří logaritmická časová složitost € O(n \cdot \log n) €. Nevýhodou je nutnost alokace druhého pomocného pole o stejné velikosti, jako je pole řazené.

Základní kroky algoritmu:

- rekurzivní dělení pole na poloviny, až do triviálního případu (jeden prvek)
- jeden prvek se triviálně pokládá za seřazený
- zpětné spojení obou seřazených polovin do neklesající posloupnosti

![grafická reprezentace průběhu řazení](http://www.java2novice.com/images/merge_sort.png)

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