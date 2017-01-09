## Svaz

Uspořádaná množina *S* je **svaz**, jestliže v ní pro všechny dvojice prvků existují **suprema** i **infima**. Pak v ní existuje supremum a infimum každé dvouprvkové podmnožiny.

€€
\begin{align*}
& (S, \sqsubseteq) \\
& \forall x, y \in S : \\
& \sup(\{x,y\}) \in S \land \inf(\{x,y\}) \in S \\
\end{align*}
€€

Pokud má supremum i infimum každá konečná neprázdná podmnožina, lze mluvit o **úplném svazu**.

Protože je supremum i infimum pro množinu dvou prvků svazu určeno jednoznačně, lze na ně ve svazu pohlížet jako na binární operace.

€€
\begin{align*}
a \lor b &= \sup(\{a, b\}) \\
a \land b &= \inf(\{a, b\}) \\
\end{align*}
€€

Tyto operace se někdy označují jako **spojení** a **průsek**. Pro tyto operace a všechny prvky *a*, *b*, *c* z množiny *S* platí:

€€
\begin{align*}
a \lor a &= a \\
a \land a &= a  \\
a \lor b &= b \lor a  \\
a \land b &= b \land a  \\
a \lor (b \lor c) &= (a \lor b) \lor c  \\
a \land (b \land c) &= (a \land b) \land c  \\
a \lor (b \land a) &= a  \\
a \land (b \lor a) &= a  \\
\end{align*}
€€

### Supremum

Nechť *A* je uspořádaná množina s podmnožinou *B*. Prvek *c* z množiny *A* se nazývá **supremum** množiny *B*, jestliže je to nejmenší horní mez (majoranta, horní závora) množiny *B* v množině *A*. Jestliže supremum nějaké množiny existuje, je určeno jednoznačně.

€€
\begin{align*}
c = \sup(B \subseteq& A) \leftrightarrow \\
\forall a \in B :\;& a \sqsubseteq c \\
\forall d \in A, a \in B :\;& a \sqsubseteq d \rightarrow c \sqsubseteq d
\end{align*}
€€

### Infimum

Nechť *A* je uspořádaná množina s podmnožinou *B*. Prvek *c* z množiny *A* se nazývá **infimum** množiny *B*, jestliže je to největší dolní mez (minoranta, dolní závora) množiny *B* v množině *A*. Jestliže infimum nějaké množiny existuje, je určeno jednoznačně.

€€
\begin{align*}
c = \inf(B \subseteq& A) \leftrightarrow \\
\forall a \in B :\;& c \sqsubseteq a \\
\forall d \in A, a \in B :\;& d \sqsubseteq a \rightarrow d \sqsubseteq c
\end{align*}
€€

### Podsvaz

Podsvaz je podmnožina svazu, která je také sama svazem, tedy je uzavřena na operace **spojení** a **průsek**.

### Distributivní svaz

Svaz je **distributivní** právě tehdy, když neobsahuje **3-diamant** ani **5-pentagon** jako své podsvazy.

3-diamant (Hasseho diagram):

```dot:graph
ratio = 1;
a--1--c;
a--0--c;
0--b--1;
{rank=same;b;0;1}
```

5-pentagon (Hasseho diagram):

```dot:graph
rankdir = LR;
ratio = 0.3;
1--c--0--b--a--1;
```

### Komplementární svaz

Je dán svaz s infimem *0* a supremem *1*. Prvek *b* se nazývá **doplněk** prvku *a*, jestliže *sup(a,b) = 1* a zároveň *inf(a,b) = 0*. Svaz je **komplementární**, právě když každý jeho prvek má doplněk. V distributivním svazu má každý prvek nejvýše jeden doplněk.

Distributivní a komplementární svaz se nazývá Booleova algebra.

### Reference

- předmět X01AVT na FEL ČVUT