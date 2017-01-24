## Množina

Pro běžné počítání je naprosto dostačující si představit množinu jako soubor několika různých prvků. Není důležité, co tyto prvky jsou nebo co představují. Mohou to být čísla, barvy, tvary, slova či dokonce jiné množiny. Důležité je, aby bylo možné od sebe jednotlivé prvky rozeznat a jednoznačně určit, které prvky do množiny patří a které ne.

> Množinou rozumíme souhrn určitých a rozlišitelných objektů existujících v naší mysli. Těmto objektům říkáme prvky množiny. *Georg Cantor*

Tato jednoduchá a intuitivní představa o množinách se označuje jako Cantorova nebo také naivní teorie množin. Byla vytvořena v druhé polovině 19. století. Její jednoduchost je však bohužel i její zkázou, protože lze po krátké úvaze dospět k paradoxu, který v roce 1901 popsal Bertrand Russell.

Podstatu Russellova paradoxu lze snadno pochopit z tohoto příkladu: Holič ze Sevilly holí právě ty ze sevillských mužů, kteří se neholí sami. Holí holič sám sebe? Pokud se sám holí, tak se holit nemůže, protože holí jen ty, kteří se sami neholí. Pokud ale sám sebe neholí, tak se holit musí, protože holí jen ty, co se sami neholí.

Paradox byl posléze vyřešen vytvořením axiomatických teorií množin (např. Gödel-Bernays, Zermelo-Fraenkel).

Existence tohoto paradoxu ale není tak kritická, jak by se mohlo zdát. Pokud se matematik drží "rozumných" množin, naivní teorie mu bohatě vystačí.

### Zápis množin

Skutečnost, že prvek *a* patří do množiny *M*, se zapisuje jako € a \in M €.

Množina, která neobsahuje žádné prvky, se nazývá **prázdná množina** a zapisuje se jako € \emptyset = \\{\\} €.

Množina *A* se nazývá **podmnožinou** množiny *B*, když každý prvek množiny *A* je zároveň prvkem množiny *B*. Tato skutečnost se zapisuje jako € A \subseteq B €. Dále platí, že každá množina je svou vlastní podmnožinou a prázdná množina je podmnožinou každé množiny: € A \subseteq A € a také € \emptyset \subseteq A €.

### Zápis obsahu množin

- € A = \\{a,b,1,\circ,X\\} € - definice výčtem
- € A = \\{1,2,3,\ldots\\} € - definice posloupností (musí být zřejmá)
- € A = \\{ x \;|\; P(x) \\} € - definice predikátem
- € \emptyset = \\{ \\} € - prázdná množina

### Operace

#### Rovnost

Dvě množiny jsou si rovny, právě když obsahují stejné prvky.

#### Průnik

Průnik několika množin je množina, která obsahuje takové prvky, které se vyskytují ve všech těchto množinách.

€€
A_1 \cap \ldots \cap A_n = \{x \;|\; x \in A_1 \land \ldots \land x \in A_n \}
€€

#### Sjednocení

Sjednocení několika množin je množina, která obsahuje takové prvky, které se vyskytují alespoň v jedné z těchto množin.

€€
A_1 \cup \ldots \cup A_n = \{x \;|\; x \in A_1 \lor \ldots \lor x \in A_n \}
€€

#### Rozdíl

€€
A - B = \{x \;|\; x \in A \land x \notin B \}
€€

#### Odvozené struktury

##### Neuspořádaná dvojice

Neuspořádaná dvojice prvků *a* a *b* (zapisovaná jako *{a,b}*) je množina, která obsahuje jen a pouze prvky *a* a *b*. Pořadí, ve kterém jsou prvky uvedeny, nehraje roli.

€€
\forall a \neq b : \{a, b\} = \{b, a\}
€€

##### Uspořádaná dvojice

Uspořádaná dvojice prvků *a* a *b* (zapisovaná jako *(a,b)*) je množina tvořená neuspořádanou dvojicí *{a,b}* a množinou obsahující právě jeden prvek *a*. Tato definice si zaslouží vysvětlení: množina s jedním prvkem *a* označuje, který prvek je v neuspořádané dvojici *{a,b}* první. Zápis ve tvaru *(a,b)* je pouze zkrácený zápis takto zkonstruované množiny.

€€
\begin{align*}
\forall a \neq b : (a,b) &= \{\{a\},\{a,b\}\} \\
(a, b) = (a', b') \rightarrow a' &= a \land b' = b
\end{align*}
€€

##### Uspořádaná n-tice

Uspořádaná n-tice je definována rekurzivně pomocí menší n-tice (a tak dále) až do uspořádané dvojice.

€€
(a_1, a_2, \ldots, a_n) = ((a_1, a_2, \ldots, a_{n-1}), a_n)
€€

##### Kartézský součin

Kartézský součin dvou množin *A* a *B* je množina všech uspořádaných dvojic prvků *a* z množiny *A* a prvků *b* z množiny *B*.

€€
A \times B = \{(a, b) \;|\; a \in A, b \in B\}
€€

Pro konečné množiny A a B platí, že € |A \times B| = |A| \cdot |B| €.

Kartézský součin několika množin *A_1* až *A_n* je množina všech uspořádaných n-tic prvků *a_1* z množiny *A_1*, prvků *a_2* z množiny *A_2*, a tak dále až do *A_n*.

€€
A_1 \times \ldots \times A_n = \{(a_1, \ldots, a_n) \;|\; a_1 \in A_1, \ldots, a_n \in A_n\}
€€

### Reference

- předmět X01AVT na FEL ČVUT
- K. Devlin: Jazyk matematiky
- http://plato.stanford.edu/entries/set-theory/primer.html
- http://www.math.ufl.edu/~jal/set_theory.html
- http://www.phil.muni.cz/fil/logika/pl.php
