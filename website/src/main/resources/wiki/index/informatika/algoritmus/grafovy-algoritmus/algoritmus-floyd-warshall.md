## Algoritmus Floyd-Warshall

![Stephen Warshall (1969)](warshall.png){.right}

Grafový algoritmus Floyd-Warshall slouží k nalezení nejkratších cest mezi všemi dvojicemi uzlů. Během roku 1962 byl v různých podobách představen několika na sobě nezávislými autory. Princip algoritmu však lze vysledovat až do roku 1956 (Kleenův algoritmus). 

Algoritmus si bohužel neporadí se zápornými cykly uvnitř grafu - proto se pro jeho správnou funkci předpokládá, že tam žádné nejsou.

### Kroky algoritmu

#### Inicializace

V paměti se inicializuje čtvercová matice o velikosti € n \times n €, kde € n € je počet uzlů. Každý řádek a sloupec odpovídá jednomu uzlu, zatímco číslo v matici představuje minimální nalezenou vzdálenost mezi touto dvojicí uzlů.

Matice se inicializuje dle následujících pravidel:

- pro všechny hrany vedoucí z uzlu ve sloupci *a* do uzlu na řádku *b* se vyplní délka této hrany
- na hlavní diagonálu se vyplní nuly, protože vzdálenost z uzlu do toho samého uzlu je 0
- do ostatních buněk se vyplní nekonečno, protože vzdálenost mezi těmito uzly není definována

```java
final Matrix<Integer, NODE> matrix = new Matrix<>(nodes.size());

for (int iX = 0; iX < matrix.size(); iX++) {
    for (int iY = 0; iY < matrix.size(); iY++) {
        final NODE xNode = nodes.get(iX);
        final NODE yNode = nodes.get(iY);

        if (iX == iY) {
            // the same node - distance is zero
            matrix.setMinimumDistance(iX, iY, 0);
            matrix.setPredecessor(iX, iY, xNode);
        } else {
            final Optional<Integer> distance = input.getDistance(xNode, yNode);

            if (distance.isPresent()) {
                // edge is defined - define distance
                matrix.setMinimumDistance(iX, iY, distance.get());
                matrix.setPredecessor(iX, iY, xNode);
            }
        }
    }
}
```

#### Výpočet

Výpočet se skládá ze třech vnořených cyklů. V každé iteraci hlavního cyklu se hledá možnost zkrácení cesty mezi všemi dvojicemi uzlů "oklikou" přes jeden uzel vybraný hlavním cyklem. Jsou samozřejmě ignorovány neplatné možnosti, kdy danou zkratkou nelze projít, protože mezi vyhodnocovanými uzly nevede žádná cesta. Pokud je však nalezená "zkratka" výhodnější, záznam v matici se aktualizuje a nalezená zkratka se uloží, aby bylo možné později nejkratší nalezenou cestu zrekonstruovat.

```dot:digraph
{rank=same;X,Y;}
nodesep=1;
X->Y [label="X-Y"]
X->d [color=forestgreen,label="zkratka X-d-Y"]
d->Y [color=forestgreen]
d [fillcolor=grey]
```

```java
for (int iDetour = 0; iDetour < matrix.size(); iDetour++) {
    for (int iX = 0; iX < matrix.size(); iX++) {
        for (int iY = 0; iY < matrix.size(); iY++) {
            final Optional<Integer> detourPart1 = matrix.get(iX, iDetour);
            final Optional<Integer> detourPart2 = matrix.get(iDetour, iY);

            if (detourPart1.isPresent() && detourPart2.isPresent()) {
                final int detourDistance = detourPart1.get() + detourPart2.get();
                final Optional<Integer> currentDistance = matrix.get(iX, iY);

                if (!currentDistance.isPresent() || detourDistance < currentDistance.get()) {
                    // the detour is better than what we have so far
                    final NODE detourNode = nodes.get(iDetour);
                    matrix.setMinimumDistance(iX, iY, detourDistance);
                    matrix.setPredecessor(iX, iY, detourNode);
                }
            }
        }
    }
}
```

### Implementace (Java)

- https://github.com/voho/examples/tree/master/floyd-warshall

### Příklad

Mějme například tento graf:

```dot:digraph
rankdir=LR
{rank=same;A,B;}
{rank=same;C,D;}
A->B [label=" 1"]
B->C [label=" 2"]
C->D [label=" 3"]
D->A [label=" 4"]
D->B [label=" 7"]
```

Matice se inicializuje do následující podoby (v řádcích je první uzel, ve sloupcích druhý):

|   | A | B | C | D
|---|---|---|---|---
| **A** | 0 | 1 | - | -
| **B** | - | 0 | 2 | -
| **C** | - | - | 0 | 3
| **D** | 4 | 7 | - | 0

Po výpočtu vypadá matice takto:

|   | A | B | C | D
|---|---|---|---|---
| **A** | 0 | 1 | 3 | 6
| **B** | 9 | 0 | 2 | 5
| **C** | 7 | 8 | 0 | 3
| **D** | 4 | 5 | 7 | 0

Z matice lze tedy například přečíst, že nejkratší cesta z uzlu *A* do uzlu *C* má délku 3, nebo že nejkratší cesta z uzlu *B* do uzlu *A* má délku 9. Také je patrné, že je celá vyplněná, což znamená, že pro každé dva uzly existuje nějaká cesty, která je spojuje.

Pokud se společně s maticí nejkratších délek udržuje i seznam předchůdců, vypadá takto:

|   | A | B | C | D
|---|---|---|---|---
| **A** | A | A | B | C
| **B** | D | B | B | C
| **C** | D | D | C | C
| **D** | D | A | B | D

Matici předchůdců lze využít k rekonstrukci nejkratší cesty mezi dvěma uzly. Tato cesta se rekonstruuje pozpátku, tedy od cíle směrem k prvnímu uzlu. Pokud například hledáme cestu z uzlu *B* do uzlu *A*, postupujeme takto:

- hledáme cestu mezi uzly *B* a *A*
- hodnota v řádku *B* a sloupci *A* je *D* - tzn. cesta bude *B* - **???** - *D* - *A*
- dále hledáme cestu mezi uzly *B* a *D*
- hodnota v řádku *B* a sloupci *D* je *C* - tzn. cesta bude *B* - **???** - *C* - *D* - *A*
- dále hledáme cestu mezi uzly *B* a *C*
- hodnota v řádku *B* a sloupci *C* je *B* - dorazili jsme na počátek, hledaná nejkratší cesta bude *B* - *C* - *D* - *A*

### Reference

- http://mathworld.wolfram.com/Floyd-WarshallAlgorithm.html
- http://www.algorithmist.com/index.php/Floyd-Warshall's_Algorithm
- http://math.mit.edu/~rothvoss/18.304.1PM/Presentations/1-Chandler-18.304lecture1.pdf
- https://cseweb.ucsd.edu/classes/sp06/cse101/notes-apr11-floyd-warshall.pdf