## Kvadratická rovnice

Kvadratická rovnice je matematická rovnice, ve které je nejvyšší stupeň (mocnina) neznámé roven dvěma. Tento typ rovnice má nejvýše dvě řešení.

### Řešení

Každou kvadratickou rovnici lze upravit do následujícího tvaru:

€€
\begin{align*}
ax^2 + bx + c &= 0 \\
a,b,c &\in C \\
a \neq 0
\end{align*}
€€

- *x* = neznámá
- *a* = kvadratický koeficient
- *b* = lineární koeficient
- *c* = absolutní člen

#### Výpočet diskriminantu

Prvním krokem k řešení je výpočet tzv. **diskriminantu**, který se označuje písmenem *D*. Ten se vypočte dosazením do následujícího vzorce:

€€ D = b^2 - 4 \cdot a \cdot c €€

Další postup závisí na jeho hodnotě.

#### Diskriminant > 0

Je-li diskriminant větší než nula, bude mít rovnice dvě řešení, která se z koeficientů a diskriminantu vypočítají takto:

€€ x_{1,2} = \frac{-b \pm \sqrt{D}}{2a} €€

Je-li diskriminant komplexní, lze pro jeho odmocnění použít **Moivrovu větu**.

€€ 
\begin{align*}
D &= \left| D \right| (\cos{x} + i \sin{x}) \\
\sqrt{D} &= (\left| D \right| (\cos{x} + i \sin{x}))^{\frac{1}{2}} \\
\sqrt{D} &= \left\{ \left| D \right|^{\frac{1}{2}} (\cos{\frac{x+2k\pi}{2}} + i \sin{\frac{x+2k\pi}{2}}) \right\} \\
0 \leq &k \leq 1; k \in N
\end{align*}
€€

#### Diskriminant = 0

Je-li diskriminant nulový, bude mít rovnice jedno řešení. Vzorec pro výpočet tohoto řešení vychází ze vzorce pro *D*>0, ale díky nulovému diskriminantu jej lze zjednodušit.

€€
\begin{align*}
x &= \frac{-b \pm \sqrt{D}}{2a} \\
x &= \frac{-b \pm \sqrt{0}}{2a} \\
x &= \frac{-b}{2a}
\end{align*}
€€

#### Diskriminant < 0

A konečně, je-li diskriminant menší než nula, rovnice nemá řešení v oboru reálných čísel.

V oboru komplexních čísel má však rovnice pro diskriminant menší než nula dvě komplexně sdružená řešení, která lze vypočítat takto:

€€ 
\begin{align*}
x &= \frac{-b \pm \sqrt{D}}{2a} \\
x &= \frac{-b \pm \sqrt{(i^2)(-D)}}{2a} \\
x &= \frac{-b \pm \sqrt{i^2}\sqrt{-D}}{2a} \\
x &= \frac{-b \pm i \sqrt{-D}}{2a}
\end{align*}
€€

### Vyjádření funkce grafem

Chceme-li zakreslit graf funkce € y = ax^2+bx+c €, nejjednodušší je získat průsečíky z osou *x* a podle znaménka koeficientu *a* nasměrovat "zobáček" funkce dolů (pro kladný koeficient) nebo nahoru (pro záporný koeficient). Průsečíky s osou *x* získáme tak, že položíme *y* rovno nule, podobně pro osu *y*.

![grafy různých kvadratických funkcí dle diskriminantu](http://mathonweb.com/help_ebook/html/functions_3/fungraph02.gif)

### Vyjádření funkce z grafu

Pro nalezení zápisu kvadratické funkce vyznačené grafem potřebujeme alespoň tři její body €(x_1,y_1)€, €(x_2,y_2)€, €(x_3,y_3)€. Snadno dostupnými body budou typicky průniky s osami *x*, *y* nebo vrchol. Tím získáme soustavu tří rovnic se třemi neznámými *a*, *b*, *c*:

€€
\begin{align*}
y_1 &= a{x_1}^2+bx_1+c \\
y_2 &= a{x_2}^2+bx_2+c \\
y_3 &= a{x_3}^2+bx_3+c
\end{align*}
€€

Tuto soustavu vyřešíme a koeficienty dosadíme do obecného vzorce kvadratické funkce.

### Reference

- http://en.wikipedia.org/wiki/Quadratic_equation