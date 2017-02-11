## Pravidelné topologie

Jako pravidelné topologie lze označit [speciální grafy](wiki/graf), které lze celé jednoznačně vygenerovat na základě určitých parametrů. Parametry ovlivňují velikost grafu, počet jeho hran a jeho strukturu. Nejpoužívanější topologie byly pojmenovány, formálně popsány a prozkoumány.

Jednotlivé topologie se od sebe odlišují např. hustotou hran, poloměrem, průměrem, chybovým průměrem, symetrií, škálovatelností nebo náročností na implementaci.

Jednou z nejčastějších aplikací pravidelných topologií jsou masivně paralelní multiprocesorové systémy a superpočítače.

### Hyperkrychle

Hyperkrychle (hypercube) je regulární graf s logaritmických stupněm uzlů.

| Vlastnost | Platnost
| --- | ---
| hranová symetrie | ano
| uzlová symetrie | ano
| bipartitní | ano
| hiearchicky rekurzivní | ano

![Q(4)](topo_q4.png)

#### Množina uzlů a hran

Uzly *n*-dimenzionální hyperkrychle **Q** jsou *n*-bitová slova. Hrany se nachází mezi uzly, které se liší právě v jednom bitu.

€€
\begin{align*}
\forall x &\in \{0, 1\}^n: \\
\forall i &\in \{0 \ldots n-1\}: \\
V(Q_n) &= x \\
E(Q_n) &= \{ \langle x, \mathrm{neg}_i (x) \rangle \} \\
\end{align*}
€€

### Mřížka

Mřížka je zobecněním hyperkrychle. Narozdíl od ní může mít v každé dimenzi jiný počet uzlů. Tato flexibilita s sebou ale přináší asymetrii a tím pádem i rozdílné vlastnosti uzlů na jednotlivých pozicích. Prakticky se používají nejčastěji mřížky obdélníkové a kvádrové, protože odpovídají rozmístění výpočetních uzlů na ploše čipu (2D) či v prostoru (3D).

| Vlastnost | Platnost
| --- | ---
| uzlová symetrie | obecně ne
| bipartitní | ano
| hiearchicky rekurzivní | ano

![M(4,3,2)](topo_m432.png)

#### Množina uzlů a hran

€€
\begin{align*}
\forall i &\in \{0 \ldots z-2\}: \\
V(M(z_1, \ldots, z_n)) &= \{(a_1, \ldots, a_n)\} \\
E(M(z_1, \ldots, z_n)) &= \{\langle(\ldots, i, \ldots), (\ldots, i + 1, \ldots) \rangle\}
\end{align*}
€€

### Toroid

Toroid je mřížka, jejíž okraje jsou propojeny.

#### Množina uzlů a hran

€€
\begin{align*}
\forall i &\in \{0 \ldots z-1\}: \\
V(K(z_1, \ldots, z_n)) &= \{(a_1, \ldots, a_n)\} \\
E(K(z_1, \ldots, z_n)) &= \{\langle(\ldots, i, \ldots), (\ldots, i \oplus_{z_i} 1, \ldots) \rangle\}
\end{align*}
€€

### Kružnice propojené krychlí

Kružnice propojené krychlí (cube conencted cycles) patří mezi řídké hyperkubické topologie. Lze si je představit jako hyperkrychle, které mají místo uzlů kružnice. Stupeň všech uzlů je 3.

| Vlastnost | Platnost
| --- | ---
| hranová symetrie | ne (pouze částečná)
| uzlová symetrie | ano
| bipartitní | ano
| hiearchicky rekurzivní | ne

![CCC(3)](topo_ccc3.png)

#### Množina uzlů a hran

Každý uzel je popsán jako uspořádaná dvojice dvou čísel - **čísla uzlu v kružnici** (přirozené číslo) a **adresy kružnice** (*n*-bitové slovo). Hrany lze rozdělit na dvě skupiny. Hrany v první skupině se nazývají **křížové hrany** (hyperkubické) a spojují dva stejnolehlé uzly dvou kružnic, jejichž adresa se liší právě v jednom bitu. Hrany ve druhé skupině se označují jako **přímé hrany** (kružnicové) a spojují uzly v rámci jednotlivých kružnic.

€€
\begin{align*}
\forall x &\in \{0, 1\}^n: \\
\forall i &\in \{0 \ldots n-1\}: \\
V(CCC_n) &= \{ (i, x) \} \\
E_H(CCC_n) &= \{ \langle (i, x), (i, \mathrm{neg}_i (x)) \rangle \} \\
E_C(CCC_n) &= \{ \langle (i, x), (i \oplus_n 1, x) \rangle \} \\
E(CCC_n) &= E_H \cup E_C \\
\end{align*}
€€

### Zabalený motýlek

Zabalený motýlek (wrapped butterfly) patří mezi řídké hyperkubické topologie. Základní vlastnosti má stejné jako **CCC**, ale obsahuje více hran, má větší bisekční šířku a menší průměr. Stupeň všech uzlů je 4.

| Vlastnost | Platnost
| --- | ---
| hranová symetrie | ne (pouze částečná)
| uzlová symetrie | ano
| bipartitní | pouze pro sudá n
| hiearchicky rekurzivní | ne

#### Množina uzlů a hran

Každý uzel je popsán jako uspořádaná dvojice **čísla uzlu v kružnici** (přirozené číslo) a **adresy kružnice** (*n*-bitové slovo). Hrany lze rozdělit na dvě skupiny, a to **křížové hrany** (hyperkubické) a **přímé hrany** (kružnicové).

€€
\begin{align*}
\forall x &\in \{0, 1\}^n: \\
\forall i &\in \{0 \ldots n-1\}: \\
V(wBF_n) &= \{ (i, x) \} \\
E_H(wBF_n) &= \{ \langle (i, x), (i \oplus_n 1, \mathrm{neg}_i (x)) \rangle \} \\
E_C(wBF_n) &= \{ \langle (i, x), (i \oplus_n 1, x) \rangle \} \\
E(wBF_n) &= E_H \cup E_C \\
\end{align*}
€€

### Obyčejný motýlek

Obyčejný motýlek (ordinary butterfly) patří mezi řídké hyperkubické topologie. Stupeň krajních uzlů je 2, vnitřní uzly mají stupeň 4.

| Vlastnost | Platnost
| --- | ---
| hranová symetrie | ne
| uzlová symetrie | ne
| bipartitní | ano
| hiearchicky rekurzivní | ano

![oBF(3)](topo_obf3.png)

#### Množina uzlů a hran

€€
\begin{align*}
\forall x &\in \{0, 1\}^n: \\
\forall i &\in \{0 \ldots n\}: \\
\forall j &\in \{0 \ldots n-1\}: \\
V(oBF_n) &= \{ (i, x) \} \\
E_H(oBF_n) &= \{ \langle (j, x), (j + 1, \mathrm{neg}_j (x)) \rangle \} \\
E_C(oBF_n) &= \{ \langle (j, x), (j + 1, x) \rangle \} \\
E(oBF_n) &= E_H \cup E_C \\
\end{align*}
€€

### Reference

- předmět X36PAR na FEL ČVUT