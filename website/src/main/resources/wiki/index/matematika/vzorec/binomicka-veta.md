## Binomická věta

Binomická věta umožňuje rozepsat €n€-tou mocninu dvou sčítanců € (a+b)^n € jako součet € n+1 € sčítanců, které jsou tvořeny součinem přirozeného čísla a mocnin těchto proměnných. Vzorec pro použití binomické věty vypadá takto:

€€
\begin{align*}
(a+b)^n &= \sum_{k=0}^n \binom{n}{k} a^{n-k}b^k \\
& n,k \in N \end{align*}
€€

[Kombinační číslo](wiki/kombinatorika) *n* nad *k* lze vypočítat podle tohoto vzorce:

€€ \binom{n}{k} = \frac{n!}{k!(n-k)!} €€

Popišme si nyní jednotlivé členy na pravé straně:

- **suma od 0 do N (včetně)** - výsledkem věty tedy bude součet € n+1 € členů
- **kombinační číslo (N nad K)** - každý tento člen bude násoben přirozeným číslem
- **součin mocnin A a B** - každý člen bude obsahovat součin mocnin členů *A* a *B*

![geometrický význam binomické věty pro druhou mocninu](http://upload.wikimedia.org/wikipedia/commons/thumb/2/21/Binomio_al_cuadrado.svg/300px-Binomio_al_cuadrado.svg.png)

![geometrický význam binomické věty pro třetí mocninu](http://upload.wikimedia.org/wikipedia/commons/thumb/d/d3/Binomio_al_cubo.svg/300px-Binomio_al_cubo.svg.png)

### Příklad

Konkrétní výpočet si ukážeme na příkladu, ve kterém z obecného vzorce odvodíme vztahy pro druhou mocninu součtu a rozdílu dvou členů.

#### Druhá mocnina součtu dvou členů

1. Zadání a nalezení potřebných hodnot pro použití v obecném vzorci:

€€ (x+y)^2 \Rightarrow a=x,b=y,n=2 €€

2. Dosazení do obecného vzorce:

€€ (a+b)^2 = \sum_{k=0}^2 \binom{2}{k} a^{2-k}b^{k} €€

3. Rozepsání součtu dle obecného vzorce:

€€ (a+b)^2 = \binom{2}{0} a^2b^0 + \binom{2}{1} a^1b^1 + \binom{2}{2} a^0b^2 €€

4. Dopočítání kombinačních čísel:

€€ (a+b)^2 = 1 \cdot a^2b^0 + 2 \cdot a^1b^1 + 1 \cdot a^0b^2 €€

5. Zapsání upravaného výsledku:

€€ (a+b)^2 = a^2 + 2ab + b^2 €€

#### Druhá mocnina rozdílu dvou členů

Musíme si uvědomit, že nám plus v obecném vzorci nevadí - my si totiž můžeme převést rozdíl kladných čísel na součet kladného čísla a záporného čísla. Jediné, co se pak změní, bude znaménko u čísla b. Až se vám binomická věta dostane víc do krve, budete schopní počítat se záporným číslem b již ve vzorci pro součet (každý sčítanec s lichou mocninou b bude záporný).

1. Začneme stejně jako u výpočtu druhé mocniny součtu, a to nalezením hodnot:

€€ (x+y)^2 \Rightarrow a=x,b=-y,n=2 €€

2. Dosazení do obecného vzorce:

€€ (a+b)^2 = \sum_{k=0}^2 \binom{2}{k} a^{2-k}b^{k} €€

3. Rozepsání součtu dle obecného vzorce:

€€ (a+b)^2 = \binom{2}{0} a^2b^0 + \binom{2}{1} a^1b^1 + \binom{2}{2} a^0b^2 €€

4. Dopočítání kombinačních čísel:

€€ (a+b)^2 = 1 \cdot a^2b^0 + 2 \cdot a^1b^1 + 1 \cdot a^0b^2 €€

5. Zapsání upravaného výsledku:

€€ (a+b)^2 = a^2 + 2ab + b^2 €€

6. Dosazení původních čísel:

€€ \begin{align*} (a+b)^2 &= a^2 + 2ab + b^2 \\ &\rightarrow \left\{ a=x,b=-y \right\} \rightarrow \\ &= x^2 + 2x(-y) + (-y)^2 \\ &= x^2 - 2xy + y^2 \end{align*} €€

### Pascalův trojůhelník

V binomické větě můžeme najít zajímavou a užitečnou souvislost s Pascalovým trojúhelníkem. Pascalův trojúhelník je geometrické uspořádání čísel, ve kterém je každé číslo dáno součtem dvou čísel nad ním (v předcházejícím řádku), přičemž první dva řádky jsou tvořeny čísly *1* a *1, 1* (pro úplnost dodáme, že "vnějšek" trojúhelníku se chová jako nula).

€€
\begin{matrix}
&&&&&1\\
&&&&1&&1\\
&&&1&&2&&1\\
&&1&&3&&3&&1\\
&1&&4&&6&&4&&1\\
\end{matrix}
€€

Všimněte si, že např. ve třetím řádku najdeme čísla 1, 2, 1. Není to podobné kombinačním číslům ve vzorci (a + b) na druhou? Není to náhoda, tato souvislost se opravdu dá dobře použít.

Každý n-tý řádek Pascalova trojúhelníku můžeme brát jako seznam kombinačních čísel členů binomického rozkladu pro (n-1)-ní mocninu. Například pro třetí mocninu dvojčlenu vezmeme čtvrtý, pro čtvrtou pátý, pro desátou jedenáctý (...) řádek Pascalova trojúhelníku a nemusíme tedy složitě počítat kombinační čísla přes faktoriály. Hodně se to hodí např. při písemce, kdy si napíšete Pascalův trojúhelník s dostatečným počtem řádků (to jde rychle) a binomická věta se hned počítá lépe.