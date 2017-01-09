## Graf jako datová struktura

Způsobů pro reprezentaci [grafu](wiki/graf) v paměti je několik a každá má své výhody a nevýhody. Reprezentaci je nutné vždy pečlivě vybrat pro zadanou situaci na základě několika kritérií:

- **druh grafu** - orientovaný a neorientovaný graf, multigraf, atd.
- **hustota grafu** - jiné reprezentace jsou vhodnější pro husté grafy, jiné pro řídké grafy
- **dynamika grafu** - některé reprezentace jsou vhodnější pro reprezentaci statických grafů (v čase se nemění), jiné pro reprezentaci dynamických grafů (v čase se mění)
- **velikost grafu** - některé reprezentace jsou paměťově úspornější než jiné
- **režie** - ke každé reprezentaci se vztahuje určitá režie

### Reprezentace v paměti

Všechny reprezentace budeme demonstrovat na následujícím grafu:

```dot:graph
rankdir=LR
1--2 [label=" e1"];
1--3 [label=" e2"];
1--4 [label=" e3"];
3--4 [label=" e4"];
```

#### Matice incidence (incidence matrix)

Graf s *V* uzly a *E* hranami lze reprezentovat [maticí](wiki/matice) (dvojrozměrným [polem](wiki/datova-struktura-pole)) o velikosti € V \times E €, ve které sloupce reprezentují hrany a řádky uzly. Každé políčko v matici pak obsahuje pravdivostní hodnotu určující incidenci mezi hranou v daném sloupci a uzlem na daném řádku.

Příklad reprezentace výše uvedeného grafu pomocí matice incidence:

€€ \begin{pmatrix}
1 & 1 & 1 & 0 \\ 
1 & 0 & 0 & 0 \\ 
0 & 1 & 0 & 1 \\ 
0 & 0 & 1 & 1
\end{pmatrix} €€

##### Vlastnosti

- efektivní reprezentace hustých grafů
- rychlé získání sousedních uzlů
- složitější přidávání / odebírání v případě, že měníme velikost matice
- v každém sloupci budou vždy dvě pravdivé hodnoty (hrana spojuje vždy právě dva uzly)

#### Matice souvislosti (adjacency matrix)

Graf s *V* uzly a *N* hranami lze reprezentovat [maticí](wiki/matice) (dvojrozměrným [polem](wiki/datova-struktura-pole)) o velikosti € V \times V €, ve které řádky i sloupce reprezentují uzly. Každé políčko v matici pak obsahuje pravdivostní hodnotu určující přítomnost hrany mezi uzlem na daném řádku a uzlem v daném sloupci.

Příklad reprezentace výše uvedeného grafu pomocí matice souvislosti:

€€ \begin{pmatrix}
\square & 1 & 1 & 1 \\ 
1 & \square & 0 & 0 \\ 
1 & 0 & \square & 1 \\ 
1 & 0 & 1 & \square
\end{pmatrix} €€

##### Vlastnosti

- efektivní reprezentace hustých grafů
- rychlé získání sousedních uzlů
- složitější přidávání / odebírání v případě, že měníme velikost matice
- diagonála se ignoruje u grafů, které neumožňují smyčky hrany (self-loops)
- v případě neorientovaných grafů je matice symetrická podél diagonály (sousednost dvou uzlů je totiž symetrické relace)

##### Rozšíření

- orientované grafy: matice nebude symetrická podél diagonály a bude zavedeno pořadí indexace
- multigrafy: místo pravdivostní hodnoty budou v matici přirozená čísla určující počet hran
- vážené hrany: místo pravdivostní hodnoty budou v matici přirozená čísla určující váhu hrany
- smyčky: začnou se číst a používat čísla na diagonále

#### Dynamická reprezentace

Dynamická reprezentace umožňuje reprezentovat grafy libovolné velikosti, omezená je jen volnou pamětí. Zpravidla umožňuje volné přidávání uzlů a hran a používá se tam, kde se graf mění. Podobná reprezentace může vypada například takto:

```java
interface Node<T> {
  T getValue();
}

interface Graph<T> {
  Set<Node<T>> getNodes();
  Set<Node<T>> getAdjacentNodes(Node<T> node);
  void addNode(Node<T> nodeToAdd);
  void removeNode(Node<T> nodeToRemove);
  void addEdge(Node<T> a, Node<T> b);
  void removeEdge(Node<T> a, Node<T> b);
  // ...
}
```

##### Vlastnosti

- množství dat je přímo závislé na počtu uzlů a hran v grafu
- každý prvek však zavírá o něco více místa než stejný prvek v matici
- přidání uzlu je snadnější než jeho odebrání (v tom případě se musí odebírat i všechny hrany, které do něj vedou)
- tato reprezentace je vhodnější pro řídké grafy

### Reference

- https://www.cs.auckland.ac.nz/software/AlgAnim/graph_rep.html
- https://people.mpi-inf.mpg.de/~mehlhorn/ftp/Toolbox/GraphRep.pdf
- http://www.stoimen.com/blog/2012/08/31/computer-algorithms-graphs-and-their-representation/
- http://www.sommer.jp/aa10/aa8.pdf
- http://graphstream-project.org/