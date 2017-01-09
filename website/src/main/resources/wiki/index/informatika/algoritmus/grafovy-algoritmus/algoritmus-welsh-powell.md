## Algoritmus Welsh-Powell

Algoritmus Welsh-Powell je [grafový algoritmus](wiki/grafovy-algoritmus) sloužící k hornímu odhadu **chromatického čísla** [grafu](wiki/graf). Jeho vstupem je graf *G* definovaný jako dvě [množiny](wiki/mnozina) - uzly *V* a hrany *E*. Výstupem je horní odhad, takže přesná hodnota chromatického čísla je vždy menší nebo rovna vypočítané hodnotě.

### Kroky algoritmu

- Inicializuj pracovní seznam *S* = *V* a počítadlo *i* = 1.
- Dokud není seznam *S* prázdný, opakuj:
 - Seřaď uzly v seznamu *S* do nerostoucí posloupnosti podle jejich stupně.
 - Obarvi první uzel v posloupnosti barvou číslo *i* a stejnou barvu postupně přiřaď i všem dalším uzlům, které s tímto uzlem nesousedí.
 - Ze seznamu *S* odeber všechny právě obarvené uzly.
 - Inkrementuj počítadlo *i*.

### Příklad

#### Inicializace

Na začátku jsou všechny uzly neobarvené (v závorkách jsou uvedeny stupně uzlů). Seznam *S* je naplněn všemi uzly a seřazen do nerostoucí posloupnosti.

| Seznam S | C(5) | E(4) | F(3) | B(2) | G(2) | A(1) | D(1)
|---

```dot:graph
nodesep = 0.3
rankdir=LR
{rank=same;"A(1)";"C(5)";}
{rank=same;"B(2)";"E(4)";"G(2)";}
"A(1)" -- "C(5)"
"B(2)" -- "C(5)"
"B(2)" -- "E(4)"
"D(1)" -- "C(5)"
"E(4)" -- "C(5)"
"E(4)" -- "F(3)"
"E(4)" -- "G(2)"
"F(3)" -- "C(5)"
"F(3)" -- "G(2)"
```

#### Krok 1

V první iteraci je vybrán první uzel (*C*) a spolu s dalšími uzly, které s ním nesousedí (*G*), je obarven barvou číslo *1* (modrá). Poté jsou obarvené uzly odebrány ze seznamu *S*.

| Seznam S | E(4) | F(3) | B(2) | A(1) | D(1)
|---

```dot:graph
nodesep = 0.3
rankdir=LR
{rank=same;"A(1)";"C(5)";}
{rank=same;"B(2)";"E(4)";"G(2)";}
"A(1)" -- "C(5)"
"B(2)" -- "C(5)"
"B(2)" -- "E(4)"
"D(1)" -- "C(5)"
"E(4)" -- "C(5)"
"E(4)" -- "F(3)"
"E(4)" -- "G(2)"
"F(3)" -- "C(5)"
"F(3)" -- "G(2)"
"C(5)" [fillcolor=khaki]
"G(2)" [fillcolor=khaki]
```

#### Krok 2

Seznam *S* není prázdný, algoritmus pokračuje dál. Ve druhé iteraci je opět vybrán první uzel (*E*) a spolu s dalšími uzly, které s ním nesousedí (*A*, *D*), je obarven barvou číslo *2* (žlutá). Poté jsou obarvené uzly odebrány ze seznamu *S*.

| Seznam S | F(3) | B(2)
|---

```dot:graph
nodesep = 0.3
rankdir=LR
{rank=same;"A(1)";"C(5)";}
{rank=same;"B(2)";"E(4)";"G(2)";}
"A(1)" -- "C(5)"
"B(2)" -- "C(5)"
"B(2)" -- "E(4)"
"D(1)" -- "C(5)"
"E(4)" -- "C(5)"
"E(4)" -- "F(3)"
"E(4)" -- "G(2)"
"F(3)" -- "C(5)"
"F(3)" -- "G(2)"
"C(5)" [fillcolor=khaki]
"G(2)" [fillcolor=khaki]
"A(1)" [fillcolor=seagreen1]
"D(1)" [fillcolor=seagreen1]
"E(4)" [fillcolor=seagreen1]
```

#### Krok 3

Seznam *S* stále není prázdný, algoritmus pokračuje dál. Ve třetí iteraci je opět vybrán první uzel (*F*) a spolu s dalšími uzly, které s ním nesousedí (*B*), je obarven barvou číslo *3* (zelená). Poté jsou obarvené uzly odebrány ze seznamu *S*.

| Seznam S | (prázdný)
|---

```dot:graph
nodesep = 0.3
rankdir=LR
{rank=same;"A(1)";"C(5)";}
{rank=same;"B(2)";"E(4)";"G(2)";}
"A(1)" -- "C(5)"
"B(2)" -- "C(5)"
"B(2)" -- "E(4)"
"D(1)" -- "C(5)"
"E(4)" -- "C(5)"
"E(4)" -- "F(3)"
"E(4)" -- "G(2)"
"F(3)" -- "C(5)"
"F(3)" -- "G(2)"
"C(5)" [fillcolor=khaki]
"G(2)" [fillcolor=khaki]
"A(1)" [fillcolor=seagreen1]
"D(1)" [fillcolor=seagreen1]
"E(4)" [fillcolor=seagreen1]
"B(2)" [fillcolor=lightblue]
"F(3)" [fillcolor=lightblue]
```

#### Krok 4

Seznam *S* je prázdný, algoritmus končí. Celkem byly použity tři barvy, chromatické číslo grafu je tedy menší nebo rovno třem.

### Reference

- Dr. Jeanne Stynes, Computer Science