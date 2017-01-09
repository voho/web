## Nejmenší společný násobek

Nejmenší společný násobek přirozených čísel *a* a *b* je nejmenší přirozené číslo *C*, které je celočíselným násobkem obou daných čísel, tedy platí, že € C = m \cdot a + n \cdot b €, kde € a,b,m,n,C \in N €. Nejmenší společný násobek se značí jako *NSN* (nejmenší společný násobek) či anglicky jako *LCM* (least common multiple).

### Výpočet

#### Faktorizace na prvočísla

Každé přirozené číslo můžeme rozepsat jakou součin prvočísel. Tento součin poté upravíme do mocninového tvaru.

€€
\begin{align*}
5 &= 5 = 5^1 \\
6 &= 2 \cdot 3 = 2^1 \cdot 3^1 \\
12 &= 2 \cdot 2 \cdot 3 = 2^2 \cdot 3^1 \\
55 &= 5 \cdot 11 = 5^1 \cdot 11^1 \\
\end{align*} 
€€

Nejmenší společný násobek z tohoto rozkladu vytvoříme následujícím způsobem:

- zapíšeme si největší mocniny všech prvočísel z rozkladu
- vytvoříme součin všech zapsaných mocnin prvočísel

##### Příklad

Hledáme nejmenší společný násobek čísel *45* a *102*. Nejprve je tedy rozepíšeme na prvočísla.

€€
\begin{align*}
45 &= 3 \cdot 3 \cdot 5 = 3^2 \cdot 5^1 \\
102 &= 2 \cdot 3 \cdot 17= 2^1 \cdot 3^1 \cdot 17^1 \\
\end{align*} 
€€

Nyní najdeme největší mocniny všech prvočísel, která se v rozkladu vyskytují: 

€€ 2^1, 3^2, 5^1, 17^1 €€
 
Nyní tato čísla vynásobíme a dostaneme nejmenší společný násobek: 

€€ \mathrm{lcm}(45,102) = 2^1 \cdot 3^2 \cdot 5^1 \cdot 17^1 = 1530 €€

### Reference

- http://mathworld.wolfram.com/Multiple.html
- http://mathworld.wolfram.com/LeastCommonMultiple.html