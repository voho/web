## Vektor

Jednou ze základních kvantit v matematice je **vektor**, pojem obecnější než číslo. Slovo "vektor" pochází z latiny a znamená "nositel". V informatice se tento pojem užívá v přeneseném smyslu jako označení pro homogenní či heterogenní kolekci dat.

Nechť *T* je [těleso](wiki/teleso). Vektor dimenze *n* nad tělesem *T* je uspořádaná n-tice € (x_1, \ldots, x_n) € prvků tělesa *T*. Číslo €x_i€ se nazývá €i€-tá souřadnice tohoto vektoru.

### Zápis

#### Algebraický zápis

€€ \overline{x} \sim \overrightarrow{x} \sim (x_1,\ldots,x_n) €€

#### Geometrické vyjádření

Geometricky si lze dvourozměrný vektor představit jako orientovanou úsečku z bodu *A* do bodu *B*. Orientace vektoru je vyjádřena umístěním šipky. Vede-li šipka do bodu *B*, bod *A* se označuje jako **počáteční**, zatímco bod *B* jako **koncový**. Délka úsečky odpovídá délce vektoru. Vektor se dá (stejně jako úsečka) zapsat ve tvaru *|AB|*.

![geometrický význam dvourozměrného vektoru](vector.png)

### Operace s vektorem

#### Součet dvou vektorů

Součet dvou vektorů stejné dimenze *n* nad stejným tělesem *T* je definován jako:

€€ \overline{x} + \overline{y} = (x_1+y_1,\ldots,x_n+y_n) €€

Součet dvou vektorů dimenze *n* nad tělesem *T* je tedy vektor stejné dimenze nad stejným tělesem.

#### Součet a rozdíl dvou vektorů

Ke každým dvěma vektorům *a* a *b* existuje právě jeden vektor *x* takový, že platí:

€€ \overline{b} + \overline{x} = \overline{a} €€

Vektor *x* se v tomto případě nazývá **rozdílem vektorů** *a* a *b*.

€€ \overline{x} = \overline{a} - \overline{b} \rightarrow \overline{a} - \overline{b} = \overline{a} + (-\overline{b}) €€

Z toho vyplývá, že odečíst vektor je totéž jako přičíst vektor opačný.

Součet dvou vektorů *x* a *y* je vektor:

€€ \overline{x} + \overline{y} = (x_1+y_1,\ldots,x_n+y_n) €€

![součet vektorů](https://upload.wikimedia.org/wikipedia/commons/thumb/e/e8/Linalg_vector_addition_2.png/409px-Linalg_vector_addition_2.png)

Rozdíl dvou vektorů *x* a *y* je vektor:

€€ \overline{x} - \overline{y} = (x_1-y_1,\ldots,x_n-y_n) €€

![rozdíl vektorů](https://www.mathsisfun.com/algebra/images/vector-subtract.gif)

#### Součin vektoru se skalárem

Součin vektoru dimenze *n* nad tělesem *T* s prvkem *a* z tělesa *T* je definován jako:

€€ a \cdot \overline{x} = (a \cdot x_1,\ldots,a \cdot x_n) €€

Součin vektoru dimenze *n* nad tělesem *T* a prvku *a* z tělesa *T* je tedy opět vektor stejné dimenze nad stejným tělesem. Pokud je prvek *a* záporný, orientace vektoru se změní.

![násobení vektoru skalárem](http://upload.wikimedia.org/wikipedia/commons/thumb/8/8e/VectorCalculations_6.png/175px-VectorCalculations_6.png)
#### Skalární součin

Skalární (vnitřní) součin je funkce, která dvojici vektorů *x* a *y* přiřazuje skalár *a*. Je-li výsledný skalár roven nule, oba vektory *x* a *y* jsou na sebe navzájem **kolmé**. V **ortonormální bázi** se dá skalární součin dvou vektorů *x* a *y* vypočítat takto:

€€ \overline{x} \cdot \overline{y} = x_1 \cdot y_1 + \ldots + x_n \cdot y_n €€

#### Vektorový součin

Vektorový (vnější) součin je definován pouze ve **třírozměrném prostoru** s **ortonormální bází**. Je to funkce, která dvojici vektorů *x* a *y* přiřazuje vektor. Algebraicky se tato funkce dá vyjádřit takto:

€€ \overline{x} \times \overline{y} = (a_2 \cdot b_3-a_3 \cdot b_2,a_3 \cdot b_1-a_1 \cdot b_3,a_1 \cdot b_2-a_2 \cdot b_1) €€

Výsledný vektor *z* je kolmý na oba vektory *x* a *y* a jeho norma odpovídá obsahu rovnoběžníku ohraničeného vektory *x* a *y*.

#### Operátor DEL

Operátor *del* je vektor, jehož komponenty jsou **parciální derivace** jednotlivých souřadnic operandu.

€€ \vec{\nabla} = \begin{pmatrix}
\frac{\partial}{\partial x} \\
\frac{\partial}{\partial y} \\
\frac{\partial}{\partial z} \\
\end{pmatrix} €€

#### Operátor DIV

Operátor *div* je **divergence** vektorového pole.

€€ \mathrm{div} \vec{v} = \nabla \cdot \vec{v} = \frac{\partial u}{\partial x} + \frac{\partial v}{\partial y} + \frac{\partial w}{\partial z} €€

#### Operátor ROT

Operátor *rot* je **rotace** vektorového pole.

€€ \mathrm{rot} \vec{v} = \nabla \times \vec{v} = \begin{pmatrix}
w_y - v_z \\
u_z - w_x \\
v_x - u_y \\
\end{pmatrix} €€

### Vektorový prostor

Množina všech vektorů dimenze *n* nad tělesem *T* spolu s definovanými operacemi sčítání vektorů a násobení vektoru prvkem tělesa *T* se nazývá **aritmetický vektorový prostor** dimenze *n* nad tělesem *T*. Zapisuje se jako *T^n*. Prvky tělesa *T* se označují jako **skaláry**.

V každém vektorovém prostoru *V* nad tělesem *T* platí:

- nulový vektor *0* je určený jednoznačně
- opačný vektor *-x* je určený vektorem *x* jednoznačně

Dále platí následující vztahy:

€€ 
\begin{align*}
\forall \overline{x} \in V \;&:\; -(-\overline{x}) = \overline{x} \\
\forall \overline{x} \in V, a \in T \;&:\; \overline{0} \cdot \overline{x} = \overline{0} \cdot a = \overline{0} \\
\forall \overline{x} \in V \;&:\; -\overline{x} = (-\overline{1}) \cdot \overline{x} \\
\forall \overline{x} \in V, a \in T \;&:\; a \cdot \overline{x} = 0 \rightarrow (a = \overline{0} \lor x = \overline{0}) \\
\end{align*}
€€

### Norma vektoru

Normou vektoru *x* může být každá reálná funkce na vektorovém prostoru *V*, která splňuje následující podmínky:

€€
\begin{align*}
|\overline{x}| &\geq 0 \\
|\overline{x}| &= 0 \leftrightarrow \overline{x} = \overline{0} \\
|\overline{x}+\overline{y}| &\leq |\overline{x}| + |\overline{y}| \\
|k \cdot \overline{x}| &= |k| \cdot |\overline{x}|
\end{align*}
€€

Po řadě se jedná o:

1. nezápornost
1. nulovost pouze pro nulové vektory
1. trojúhelníkovou nerovnost
1. homogenitu

Vektorový prostor s normou se nazývá **normovaný vektorový prostor**. Pro dvourozměrné a třírozměrné vektorové prostory se norma označuje názorněji jako **velikost** nebo **délka**. Každá norma dále umožňuje zavedení tzv. **metriky**, což je zobecněná vzdálenost.

#### Eukleidovská norma

Nejčastěji se používá norma eukleidovská, která je definovaná jako odmocnina ze součtu druhých mocnin souřadnic vektoru.

€€ |\overline{x}| = \sqrt{{x_1}^2 + \ldots + {x_n}^2} €€

### Speciální vektory

#### Nulový vektor

Nulový vektor má všechny souřadnice rovné nule. Z fyzikálního hlediska nemá směr ani orientaci. 

€€ \overline{0} = (0,\ldots,0) €€

#### Jednotkový vektor

Jako jednotkový vektor se označuje vektor s jednotkovou normou.

€€ \overline{1} = \frac{\overline{V}}{|\overline{V}|}, |\overline{1}| = 1 €€

### Reference

- http://artemis.osu.cz/mmmat/maintxt.htm
- http://www.karlin.mff.cuni.cz/~tuma/NNlinalg.htm