## Formální aritmetika

Formální aritmetika leží na pomezí matematiky, logiky a filozofie a používá se pro studium hranic axiomatické metody v matematice.
Jedním z cílů bylo například najít nejmenší množinu předpokladů (axiomů), ze kterých bude možné odvodit aritmetiku přirozených čísel. 
Jedním z nejdůležitějších výsledků jsou tzv. **Gödelovy věty o neúplnosti**.  

### Presburgerova aritmetika

![Mojżesz Presburger](presburger.jpg){.right}

Presburgerova aritmetika (**Mojżesz Presburger**, 1929 - 1943) je formální systém prvního řádu umožňující definovat přirozená čísla a jejich součet. Je dokázáno, že je tato aritmetika **konzistentní** (v Presburgerově aritmetice neexistuje výrok, který by byl odvoditelný z axiomů společně se svou negací), **úplná** (každý výrok Presburgerovy aritmetiky, nebo jeho negaci je možné odvodit z axiomů) a **rozhodnutelná** (existuje algoritmus, který jednoznačně rozhodne, zda je daný výrok Presburgerovy aritmetiky pravdivý či nikoliv).

#### Axiomy

Nechť je dán jazyk *L* obsahující konstantní symbol *0*, binární funkční symbol *+* a unární funkční symbol *S(x)* (následník).

€€
\begin{align}
\lnot (0 &= S(x)) \\
(S(x) &= S(y)) \rightarrow x = y \\
x + 0 &= x \\
x + S(y) &= S(x+y) \\
\forall \varphi_L : (\varphi(0) \land \forall x(\varphi(x) &\rightarrow \varphi(S(x)))) \rightarrow \forall y : \varphi(y) \\
\end{align} 
€€

1. 0 není následníkem žádného čísla
1. jestliže je následník jednoho čísla roven následníkovi čísla druhého, jsou obě čísla stejná
1. rekurzivní definice sčítání (krajní případ)
1. rekurzivní definice sčítání (obecný případ)
1. schéma indukce pro všechny formule € \varphi € jazyka *L* (pokud platí €\varphi(0)€ a z platnosti €\varphi(x)€ plyne platnost €\varphi(S(x))€, platí formule €\varphi€ pro všechny prvky)

### Robinsonova aritmetika

![Raphael Mitchel Robinson](robinson.jpg){.right}

Robinsonova aritmetika (**Raphael Mitchel Robinson**, 1950 - 1995) je formální systém prvního řádu umožňující definovat přirozená čísla, jejich součet a násobení. Tato aritmetika je **neúplná** (existují výroky, které jsou v ní nedokazatelné) a **nerozhodnutelná** (neexistuje algoritmus, který by pro každý výrok určil, zda je v Robinsonově aritmetice pravdivý či nikoliv).

#### Axiomy

Nechť je dán jazyk *L*, obsahující konstantní symbol *0*, dva binární funkční symboly *+* a *.* a jeden unární funkční symbol *S(x)* (následník). 

€€
\begin{align}
\lnot (S(x) &= 0) \\
(S(x) = S(y)) &\rightarrow x = y \\
(y = 0) &\lor \; \exists x (S(x) = y) \\
x + 0 &= x \\
x + S(y) &= S(x+y) \\
x \cdot 0 &= 0 \\
x \cdot S(y) &=(x \cdot y) + x \\
\end{align} 
€€

1. *0* není následníkem žádného čísla
1. jestliže je následník jednoho čísla roven následníkovi čísla druhého, jsou obě čísla stejná
1. každé číslo je buď *0*, nebo následník nějakého jiného čísla
1. rekurzivní definice sčítání (krajní případ)
1. rekurzivní definice sčítání (obecný případ)
1. rekurzivní definice násobení (krajní případ)
1. rekurzivní definice násobení (obecný případ)

### Reference

- http://mathworld.wolfram.com/PresburgerArithmetic.html
- https://en.wikipedia.org/wiki/G%C3%B6del%27s_incompleteness_theorems