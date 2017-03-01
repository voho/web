## Asymptotická složitost

Asymptotická složitost je nástrojem k porovnávání **efektivity** algoritmů. Složitost vyjadřuje, jak roste **náročnost** algoritmu (doba výpočtu nebo potřebná paměť) s rostoucím množstvím **vstupních dat**. Dobu výpočtu lze definovat jako počet všech elementárních operací, které algoritmus vykoná při zpracování daného vstupu. Každá tato operace musí být dokončena v konstantním čase a patří mezi ně například základní aritmetické operace, porovnání dvou hodnot či přiřazení.

Protože je algoritmus obecný a jeho konkrétní implementace se mohou více či méně lišit, je k jejich srovnávání nutné použít metodu "odolnou" vůči specifikám jednotlivých platforem. Především se složitost počítá tzv. **asymptoticky** a během "výpočtu" se zanedbávají nepodstatné členy (např. aditivní a multiplikativní konstanty).

Asymptotickou složitost si lze představit jako "řád růstu", tedy "zařazení" algoritmu do nějaké kategorie (lineární, exponenciální, logaritmická...), která umožňuje porovnávat efektivitu algoritmů mezi sebou, a to nezávisle na implementaci a použité platformě.

### Notace Omega

Množina Omega funkce *f* je množina všech funkcí, které mají "stejný" nebo "vyšší" řád růstu jako funkce *f*.

Prohlásíme-li, že složitost algoritmu leží v množině Omega funkce *f*, znamená to, že bude **náročnost algoritmu růst vždy stejně nebo více** než funkce *f*. Notaci Omega lze tedy chápat jako vyjádření "nejlepšího případu".

![notace Omega omezuje řád růstu náročnosti zdola](bigo_omega.png)

#### Formální definice

€€ 
f(x) \in \Omega(g(x)) \rightarrow \exists C, x_0: C > 0, \forall (x>x_0): |C \cdot g(x)| \leq |f(x)|
€€ 

**Příklad:** € n^2 \in \Omega(n) €, € \log n \in \Omega(1) €

### Notace Omikron

Množina Omikron funkce *f* je množina všech funkcí, které mají "stejný" nebo "nižší" řád růstu jako funkce *f*.

Prohlásíme-li, že složitost algoritmu leží v množině Omikron funkce *f*, znamená to, že bude **náročnost algoritmu růst vždy stejně nebo méně** než funkce *f*. Notaci Omikron tedy lze chápat jako vyjádření "nejhoršího případu".

![notace Omega omezuje řád růstu náročnosti shora](bigo_omicron.png)

#### Formální definice

€€ 
f(x) \in O(g(x)) \rightarrow \exists C, x_0: C > 0, \forall (x>x_0) \; |f(x)| \leq |C \cdot g(x)|
€€

**Příklad:** € n^2 \in O(n^3) €, € \log n \in O(n) €

### Notace Theta

Množina Theta funkce *f* je množina všech funkcí, které mají "stejný" řád růstu jako funkce *f*.

Prohlásíme-li, že složitost algoritmu leží v množině Theta funkce *f*, znamená to, že bude **náročnost algoritmu růst vždy stejně** jako funkce *f*.

U některých algoritmů však nelze složitost pomocí množiny Theta specifikovat.

![notace Omega omezuje řád růstu náročnosti z obou směrů](bigo_theta.png)

#### Formální definice

€€ 
f(x) \in \Theta(g(x)) \rightarrow \exists C, C': C,C' > 0, \forall (x>x_0) \; |C \cdot g(x)| \leq |f(x)| \leq |C' \cdot g(x)|
€€

Také platí tato ekvivalence:

€€
f(x) \in \Theta(g(x)) \rightarrow f(x) \in O(g(x)) \land f(x) \in \Omega(g(x))
€€

**Příklad:** € 5102.445n^2 \in \Theta(n^2) €, € 5 \in \Theta(1) €

### Obvyklé třídy složitosti

| Asymptotická složitost | Vyjádření | Popis a nejčastější případ
|---|---|---
| konstantní | € O(1) € | Počet operací je pro libovolně velká vstupní data zhruba stejný. Typicky se jedná o nějaký jednoduchý matematický výpočet, jeden přístup k paměti, apod.
| logaritmická | € O(\log n) € | Typicky ideální případ vyhledávání ve stromě s € n € prvky. |
| lineární | € O(n) € | Náročnost algoritmu se zvyšuje podobně jako velikost vstupních dat. Typicky jeden průchod polem. Některé algoritmy s touto složitostí mohou být implenentovány i proudově.
| lineárně logaritmická | € O(n \cdot \log(n)) € | Typická složitost rozumného řadícího algoritmu. |
| kvadratická | € O(n^2) € | Náročnost algoritmu roste jako druhá mocnina velikosti vstupních dat. Typicky průchod všech dvojic v poli.
| polynomiální | € O(n^k), k \in R € | |
| exponenciální | € O(k^n), k \in R € | |
| faktoriálová | € O(n!) € | Typicky vyhodnocování všech možných permutací € n € prvků, například v brute-force algoritmech. |

![porovnání složitostí](bigo.png)

### Reference

- http://kam.mff.cuni.cz/~kuba/ka/ka.pdf
