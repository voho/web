## Grupa

### Definice

Prvek *a* z množiny *A* monoidu € (A, \circ, e) € s neutrálním prvkem *e* je **invertibilní** právě tehdy, když k němu v nosné [množině](wiki/mnozina) *A* existuje **inverzní prvek** *z* takový, že € a \circ z = e = z \circ a €. Tento prvek je určen jednoznačně.

Grupa je každý [monoid](wiki/monoid) € (A, \circ, e) €, ve kterém jsou všechny prvky nosné množiny **invertibilní** dle výše uvedené definice. Platí tedy, že € \forall a \in A: \exists z \in A: a \circ z = e = z \circ a €.

Grupa je **komutativní**, právě když je operace € \circ € komutativní.

### Příklady

Grupou je například množina celých čísel spolu s operací sčítání a nulou jako neutrálním prvkem € (\mathbb{Z}, +, 0) €. Inverzním prvkem k číslu *A* je číslo *-A*.

Grupou je i množina reálných čísel spolu s operací násobení a jedničkou jako neutrálním prvkem € (\mathbb{R}, +, 1) €. Inverzním prvkem k číslu *A* je číslo *1/A*.

### Podgrupa

Nechť € (A,\circ,e) € je grupa. Trojice € (B,+,g) € je její podgrupa, právě když platí:

€€
\begin{align}
B &\subseteq A \\
g &= e \\
\forall b &\in B : b^{-1} \in B
\end{align}
€€

#### Lagrangeova věta

Nechť € (A,\circ,e) € je konečná grupa a € (B,+,g) € její podgrupa. Pak řád podgrupy € (B,+,g) € (počet prvků množiny *B*) dělí řád grupy € (A,\circ,e) € (počet prvků množiny *A*).

#### Podgrupa generovaná prvkem

Nechť € (A,\circ,e) € je konečná grupa. Umocnění prvku *a* na celé číslo *b* je definováno jako *b*-násobné postupné použití operace *o* na prvek *a*:

€€
a \in A, n \in Z
€€

€€
\begin{align}
a^0 &= e \\
a^1 &= a \\
a^n &= a \circ a^{n-1} \\
a^{-n} &= {(a^{-1})}^n
\end{align}
€€

Nechť € (A,\circ,e) € je konečná grupa. Její podgrupa € (B,\circ,e) € se nazývá podgrupa generovaná prvkem *a*, pokud platí, že:

€€
a \in A, B = \{ a, a^2, \ldots, a^{r(a)} = e \}
€€

V tom případě se prvek *a* nazývá **generátor**. Nejmenší možné kladné číslo *r(a)* se nazývá **řád prvku a**.

Podgrupa generovaná prvkem *a* se značí takto:

€€
B = \langle a \rangle
€€

### Cyklická grupa

Každá grupa, která má generátor, se nazývá **cyklická grupa**. Všechny podgrupy cyklické grupy jsou také cyklické.

Je-li řád prvku *a* roven *r*, pak platí tento vztah:

€€
r(a^k) = \frac{r(a)}{\mathrm{gcd}(r(a),k)}
€€

### Grupový homomorfizmus

Nechť € (A,\circ,e) € a € (B,+,g) € jsou grupy. [Zobrazení](wiki/zobrazeni) *f* je grupový homomorfizmus, právě když platí:

€€
f : A \to B
€€

€€
\begin{align}
\forall x,y &\in A: \\
f (x + y) &= f (x) * f (y) \\
f (e) &= g \\
f (x^{{-1}_A}) &= (f (x))^{{-1}_B}
\end{align}
€€

Stručně řečeno, grupový homomorfizmus respektuje **binární operaci**, **neutrály** a **inverzi**.

### Grupový izomorfizmus

Grupový izomorfizmus je grupový homomorfizmus, který je vzájemně jednoznačným zobrazením (bijekcí). Dvě grupy € (A,\circ,e) € a € (B,+,g) € jsou izomorfní, právě když existuje nějaký izomorfizmus *f*, pro který platí:

€€
f : A \to B
€€

Pokud toto platí, lze tuto vlastnost zapisovat jako:

€€
A \simeq B
€€

### Reference

- předmět X01AVT na FEL ČVUT