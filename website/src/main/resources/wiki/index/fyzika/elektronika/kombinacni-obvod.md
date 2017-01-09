## Kombinační obvod

Jako kombinační obvody se označují digitální elektrické obvody, jejichž výstup (po ustálení) je závislý pouze na vstupu a není funkcí času (výstup je jednoznačně definovaný pro všechny možné kombinace vstupních proměnných). Kombinační obvody neobsahují žádnou paměť ani zpětnou vazbu. Signály se vždy šíří jen jedním směrem, a to ze vstupu na výstup.

Matematicky lze kombinační obvod popsat soustavou logických funkcí, kde je každá **výstupní** proměnná funkcí nějaké podmnožiny **vstupů**:

€€
\begin{align*}
y_1 &= f(x_1,x_2,...,x_n)\\
y_2 &= f(x_1,x_2,...,x_n)\\
\vdots & \\
y_n &= f(x_1,x_2,...,x_n)\\
\end{align*}
€€

### Vnitřní struktura

Kombinační obvody se zpravidla skládají z [logických hradel](wiki/logicke-hradlo). Protože i logická hradla jsou reálné součástky, musí se u všech kombinačních obvodů počítat se **zpožděním**, způsobeným především parazitní kapacitou každého hradla. Velikost tohoto zpoždění je závislá na technologii výroby hradel, struktuře obvodu a především na délce tzv. **kritické cesty**, což je nejdelší posloupnost hradel a vodičů na cestě od vstupu na výstup.

Dalším možným řešením je využití **adresovatelné paměti**. Vstupní proměnné figurují jako **adresa** do paměti, ve které jsou uloženy výstupní hodnoty. Taková paměť má stejný počet adresních bitů, jako je počet vstupů; a podobně má i jedno paměťové slovo právě tolik bitů, kolik je výstupů.

Při návrhu kombinačního obvodu postupujeme v těchto krocích:

1. Specifikace
1. Určení vstupů a výstupů
1. Pravdivostní tabulky
1. Booleovské rovnice
1. Návrh na úrovni hradel
1. Simulace na úrovni hradel
1. Realizace obvodu
1. Ověření návrhu

### Simulátory

- [Logic Lab](http://www.neuroproductions.be/logic-lab/) (zdarma)
- [Logic.ly](http://logic.ly/demo/) (demo)