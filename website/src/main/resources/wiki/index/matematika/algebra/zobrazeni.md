## Zobrazení

Zobrazení *f* z [množiny](wiki/mnozina) *A* do množiny *B* je [binární relace](wiki/relace), tedy podmnožina kartézského součinu *A* x *B*. Množina *A* se nazývá **definiční obor** (domain) a množina *B* **obor hodnot** (range).

### Vlastnosti

#### Surjektivní zobrazení

Zobrazení *f* z množiny *A* do *B* je surjektivní (na množinu) právě když každý obraz má alespoň jeden vzor.

€€
\forall y \in B : \exists x \in A, f(x) = y
€€

```dot:digraph
subgraph cluster_0 {
  node [style=filled,color=white];
  label="A (domain)"
  color=lightgray
  style=filled
  {rank=same; a1; a2; a3;}
}
subgraph cluster_1 {
  node [style=filled,color=white,shape=box];
  label="B (range)"
  color=skyblue
  style=filled
  {rank=same; b1; b2;}
}
a1 -> b1 [label="  f(a1)"]
a2 -> b2 [label="  f(a2)"]
a3 -> b2 [label="  f(a3)"]
```

#### Injektivní zobrazení

Zobrazení *f* z množiny *A* do *B* je injektivní (prosté) právě když každým dvěma různým vzorům přiřazuje dva různé obrazy. Ke každému injektivnímu zobrazení tedy lze vytvořit zobrazení inverzní. Injektivní zobrazení tedy **zachovává odlišnost**.

€€
\forall x,y \in A : x \neq y \rightarrow f(x) \neq f(y)
€€

```dot:digraph
subgraph cluster_0 {
  node [style=filled,color=white];
  label="A (domain)"
  color=lightgray
  style=filled
  {rank=same; a1; a2; a3;}
}
subgraph cluster_1 {
  node [style=filled,color=white,shape=box];
  label="B (range)"
  color=skyblue
  style=filled
  {rank=same; b1; b2;}
}
a1 -> b1 [label="  f(a1)"]
a2 -> b2 [label="  f(a2)"]
```

#### Bijektivní zobrazení

Zobrazení *f* z množiny *A* do *B* je bijektivní (vzájemně jednoznačné) právě když je zároveň **injektivní** i **surjektivní**.

```dot:digraph
subgraph cluster_0 {
  node [style=filled,color=white];
  label="A (domain)"
  color=lightgray
  style=filled
  {rank=same; a1; a2;}
}
subgraph cluster_1 {
  node [style=filled,color=white,shape=box];
  label="B (range)"
  color=skyblue
  style=filled
  {rank=same; b1; b2;}
}
a1 -> b1 [label="  f(a1)"]
a2 -> b2 [label="  f(a2)"]
b1:w -> a1:w [style=dashed,color=dimgray,label=" F(b1)",dir=backward]
b2:e -> a2:e [style=dashed,color=dimgray,label=" F(b2)",dir=backward]
```

### Funkce

Koncept funkce byl zaveden, aby bylo možné vyjádřit skutečnosti, že jedna kvantita je jednoznačně a deterministicky určena kvantitou jinou. Formálně je funkce **zobrazení**.

Je mnoho možností, jak funkci definovat a zapsat. Mezi nejčastější způsoby patří vzorec, tabulka, graf nebo [algoritmus](wiki/algoritmus) k jejímu výpočtu.

### Reference

- předmět X01AVT na FEL ČVUT
- K. Devlin: Jazyk matematiky
- http://plato.stanford.edu/entries/set-theory/primer.html
- http://www.math.ufl.edu/~jal/set_theory.html
- http://www.phil.muni.cz/fil/logika/pl.php