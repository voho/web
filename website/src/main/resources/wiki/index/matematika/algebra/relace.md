## Relace

Množina *R* je relace na množinách *A_1* až *A_n* právě když jsou všechny její prvky uspořádané n-tice prvků po řadě z množin *A_1* až *A_n*. Relaci lze definovat i pomocí kartézského součinu. Relace na množinách *A_1* až *A_n* je libovolná podmnožina kartézského součinu množin *A_1* až *A_n*, tedy platí € R \subseteq A_1 \times \ldots \times A_n €. Protože je relace [množina](wiki/mnozina), lze na ní aplikovat všechny operace i vlastnosti, které platí pro množinu.

### Binární relace

Množina *R* je binární relace na množinách *A* a *B* právě tehdy když jsou všechny její prvky uspořádané dvojice prvků z množiny *A* a *B*, tedy platí € R \subseteq A \times B €. Z toho vyplývá, že z výsledku lze vypátrat i původní dvojici hodnot. Jinak zapsáno platí, že € \forall z \in R \;\exists\; x,y : z = (x, y) €.

Množina všech prvků *x* se nazývá **definiční obor** (domain) relace *R*, množina všech prvků *y* je **obor hodnot** (range) relace *R*.

€€
\begin{align*}
D_R &= \{x \;|\; (x, y) \in R\} \\
R_R &= \{y \;|\; (x, y) \in R\}
\end{align*}
€€

Skutečnost, že nějaká dvojice prvků leží v relaci, lze zapisovat různými způsoby.

€€
(a,b) \in R \sim a R b \sim R(a,b)
€€

### Podrelace

Jako podrelace relace *R* se označuje každá relace *S*, pro kterou platí, že € S \subseteq R €.

### Inverzní relace

Jako inverzní relace k relaci *R* se označuje každá relace *S*, pro kterou platí, že € x S y \leftrightarrow y R x €.

### Vlastnosti

#### Reflexivita

Reflexivní relace vyjadřuje, že je každý prvek ve vztahu sám se sebou.

€€
\forall x \; R(x,x)
€€

```dot:digraph
ratio=0.5
rankdir = LR
nodesep = 0.5
{rank = same; A; C;}
A -> B [color=gray]
B -> C [color=gray]
C -> A [color=gray]
A -> C [color=gray]
  
A -> A [color=forestgreen,penwidth=2]
B -> B [color=forestgreen,penwidth=2]
C -> C [color=forestgreen,penwidth=2]
```

#### Antireflexivita

Antireflexivní (ireflexivní) relace vyjadřuje, že prvek není nikdy ve vztahu sám se sebou.

€€
\forall x \; \lnot R(x,x)
€€

#### Symetrie

Symetrická relace vyjadřuje vzájemný vztah dvou prvků.

€€
\forall x,y \; (R(x,y) \rightarrow R(y,x))
€€

```dot:digraph
ratio=0.5
rankdir = LR
nodesep = 1.5
A -> B [color=gray]
B -> C [color=gray]
  
B -> A [color=forestgreen,penwidth=2]
C -> B [color=forestgreen,penwidth=2]
}
```

#### Antisymetrie

Antisymetrická relace vyjadřuje vztah, který není opětován.

€€
\forall x,y \; ((R(x,y) \land R(y,x)) \rightarrow (x=y))
€€

#### Asymetrie

Asymetrická relace se někdy označuje jako silně antisymetrická.

€€
\forall x,y \; (R(x,y) \rightarrow \lnot R(y,x))
€€

#### Tranzitivita

Tranzitivní relace vyjadřuje přenos vztahu mezi prvky.

€€
\forall x,y,z \; ((R(x,y) \land R(y,z)) \rightarrow R(x,z))
€€

```dot:digraph
rankdir = LR
nodesep = 1
A -> B [color=gray]
B -> C [color=gray]
  
A -> C [color=forestgreen,penwidth=2]
```

#### Intranzitivita

Intranzitivní relace vyjadřuje, že se vztah mezi prvky nikdy nepřenáší.

€€
\forall x,y,z \; ((R(x,y) \land R(y,z)) \rightarrow \lnot R(x,z))
€€

#### Konexnost

Konexní relace vyjadřuje, že jsou všechny prvky propojeny sítí vztahů.

€€
\forall x,y \; ((x \neq y) \rightarrow (R(x,y) \lor R(y,x)))
€€

#### Inkonexnost

Inkonexní relace vyjadřuje, že existují takové dva různé prvky, které mezi sebou nemají žádný vztah.

€€
\exists x,y \; ((x \neq y) \land \lnot R(x,y) \land \lnot R(y,x))
€€

### Uzávěry

#### Reflexivní uzávěr

Reflexivní uzávěr relace *R* je nejmenší reflexivní relace *S* (ve smyslu počtu prvků) taková, že *R* je podrelace (podmnožina) *S*.

#### Symetrický uzávěr

Symetrický uzávěr relace *R* je nejmenší symetrická relace *S* (ve smyslu počtu prvků) taková, že *R* je podrelace *S*.

#### Tranzitivní uzávěr

Tranzitivní uzávěr relace *R* je nejmenší tranzitivní relace *S* (ve smyslu počtu prvků) taková, že *R* je podrelace *S*.

```dot:digraph
rankdir = LR
nodesep = 1
A -> B [color=gray]
B -> C [color=gray]
C -> D [color=gray]
```

```dot:digraph
{rank=same;A;B;C;D;}
nodesep=0.5
A -> B [color=gray]
B -> C [color=gray]
C -> D [color=gray]
A -> C [color=forestgreen,penwidth=2]
A -> D [color=forestgreen,penwidth=2]
B -> D [color=forestgreen,penwidth=2]
```

### Speciální relace

#### Relace ekvivalence

Relace *R* je relace **ekvivalence** právě když je reflexivní, symetrická a tranzitivní.

#### Relace uspořádání

Relace *R* je relace **uspořádání** právě když je reflexivní, antisymetrická a tranzitivní.

#### Relace ostrého uspořádání

Relace *R* je relace **ostrého uspořádání** právě když je ireflexivní, antisymetrická a tranzitivní.

### Reference

- předmět X01AVT na FEL ČVUT
- K. Devlin: Jazyk matematiky
- http://plato.stanford.edu/entries/set-theory/primer.html
- http://www.math.ufl.edu/~jal/set_theory.html
- http://www.phil.muni.cz/fil/logika/pl.php