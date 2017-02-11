## Pravděpodobnost

> Operace je prý úspěšná jen u 10% pacientů, ale nebojte se. Pan doktor již devět pacientů operoval a všichni zemřeli.

Teorie pravděpodobnosti je matematický nástroj pro **modelování** systémů, kde budoucí pravdivost jevů závisí na **náhodě** nebo okolnostech, které nejsou zcela známé. Lze ji použít i pro zjednodušené modelování systémů, které jsou pro přesné modelování příliš složité. Pravděpodobnostní model se snaží co možná nejlépe předpovídat budoucí chování takových systémů.

```dot:digraph
rankdir=LR
edge [arrowhead=vee]
event [fillcolor=khaki,shape=rectangle]
event -> "consequence 1":w
event -> "consequence 2":w
event -> "consequence 3":w
```

Historie teorie pravděpodobnosti sahá až k **Laplaceovi** (1749-1829), **Gaussovi** (1777-1855) a dalším významným matematikům té doby, kteří si korespondovali s hráči hazardních her. Empirický pohled na věc přinesl až **Richard von Mises** (1883-1953). Ten se na pravděpodobnost jevu díval jako na relativní četnost jeho výskytu v dlouhé sérii pokusů. O axiomatickou definici pravděpodobnosti se ještě o pár let později zasloužil **Andrej Nikolajevič Kolmogorov** (1903-1987).

Existují i různé jiné pohledy na pravděpodobnost, jako je například zjednodušená **subjektivní sázková definice** (Bruno de Finetti, Frank Ramsey). Podle něj je pravděpodobnost "férový kurz" vypsaný na výskyt nějakého jevu.

### Základní pojmy

#### Elementární jev

Pokus, jehož výsledek není jednoznačně určen podmínkami, za kterých je prováděn, se nazývá **náhodný pokus**. Takový pokus dává při opakování za stejných podmínek rozdílné výsledky.

Každý možný měřitelný výsledek takového pokusu se nazývá **elementární jev**. Množina všech elementárních jevů daného náhodného pokusu se označuje velkým písmenem **omega**.

Jako **náhodný jev** se označuje každá podmnožina množiny **omega**. Je to událost, o které se dá jednoznačně prohlásit, zda nastala, nebo nenastala (podobně jako se dá o logickém výroku prohlásit, zda je pravdivý či nepravdivý). Na náhodný jev lze pohlížet i jako na množinu elementárních jevů. Elementární jev formálně nemusí být jevem.

Pro jevy platí vše, co platí pro množiny a lze s nimi provádět množinové operace.

##### Příklad

Pojmy si ukážeme na známém příkladu - hodu kostkou. Množina elementárních jevů *omega* obsahuje prvky **{1, 2, 3, 4, 5, 6}**. Každé toto číslo je elementární jev.

![množina elementárních jevů pro hod kostkou](omega1.png)

Jev "padlo sudé číslo" je podle definice podmnožina množiny *omega* a obsahuje prvky **{2, 4, 6}**. Padne-li jedno z těchto čísel, jev "padlo sudé číslo" nastal. Padne-li jiné číslo, jev nenastal.

![jev "padlo sudé číslo" (A)](omega2.png)

Definujeme další jev "padl násobek tří", který obsahuje prvky **{3, 6}**. Všimněte si, že elementární jev *6* se nachází v obou jevech. Pokud tedy na kostce padne číslo *6*, nastaly oba jevy současně. Je tomu tak, protože elementární jev *6* leží v **průniku** obou jevů. A podobně, pokud padne některé z čísel **{2, 3, 4, 6}**, můžeme prohlásit, že nastal alespoň jeden z jevů. To proto, že tyto elementární jevy leží ve **sjednocení** obou jevů.

Jestliže padne některé z čísel **{1, 5}**, nenastal ani jeden z uvedených jevů.

![jev "padlo sudé číslo" (A) a jev "padl násobek tří" (B)](omega3.png)

### Základní pravděpodobnostní modely

#### Laplaceův model

Nejjednodušší model teorie pravděpodobnosti, nazvaný Laplaceův model, předpokládá náhodný pokus s konečným počtem různých a vzájemně se vylučujících výsledků (elementárních jevů). Tyto jevy musí být všechny **stejně možné** (např. díky symetrii a homogenitě).

Za těchto předpokladů je pravděpodobnost jevu, který nastává právě při *k* pokusech z celkových *n*, rovna *k/n*.

Tato definice je velmi vágní a točí se v kruhu, protože spojení "stejně možné" znamená to samé jako "stejně pravděpodobné". Definice ale obsahuje jednoduchý [algoritmus](wiki/algoritmus), jak pravděpodobnost jevu *A* vypočítat.

€€ 
\begin{align*}
|\Omega| &< \infty \\
A &\subseteq \Omega \\
P(A) &= \frac{|A|}{|\Omega|} \in \langle 0,1 \rangle \\
P(\Omega) &= 1 \\
P(\varnothing) &= 0 \\
\end{align*}
€€

Při výpočtu mohutnosti množin se často používají základní kombinatorické vzorce.

##### Problémy

Jednoduchost Laplaceova modelu, která vychází z jeho definice a zvolenými předpoklady, přináší řádu omezení. Tyto problémy zapříčinily vznik **statistiky** a pokročilejších modelů teorie pravděpodobnosti:

- definice kruhem (stejně možné = stejně pravděpodobné)
- neumožňuje modelovat systémy, ve kterých mají elementární jevy různou pravděpodobnost (dokáže je pouze aproximovat)
- nedovoluje, aby byla množina jevů nekonečná
- nedovoluje iracionální hodnoty pravděpodobností
- neumožňuje, aby se udál jev s nulovou pravděpodobností

##### Příklad 1 - zadání

Sázková kancelář vydala *10,000* losů a každý prodává za cenu *10€*. Jeden los vyhrává hlavní cenu *10,000€*, 10 losů vedlejší cenu *1,000€* a dalších 100 losů *100€*. Rozhodněte, zda je cena losu přiměřená a jaký bude zisk sázkové kanceláře po prodeji všech losů.

##### Příklad 1 - řešení

Množinou elementárních jevů *omega* jsou jednotlivé losy a její mohutnost je *10,000*. Náhodnou veličinou *X* je výhra na losu. Přiměřenou cenu losu vypočítáme jako střední hodnotu náhodné veličiny *X*:

€€ \mathrm{EX} = \frac{1}{10000} (10000 + 10 \cdot 1000 + 100 \cdot 100 + 9889 \cdot 0) = 3 €€

Los by měl stát *3€*, aby byla soutěž "férová". Stojí však *10€*, takže prodejem jednoho losu vydělá společnost *7€*. Celkem si tedy sázková kancelář přijde na *70,000€* (tržby *100,000€* - náklady *30,000€*).

### Kolmogorův model

Kolmogorův model je obecnější a složitější, než model Laplaceův. Narozdíl od něho dovoluje, aby byla množina elementárních jevů nekonečná a jejich pravděpodobnosti byly různé.

Jako **pravděpodobnostní prostor** se označuje trojice *(omega,A,P)*, kde *omega* je množina elementárních jevů, *A* je systém jevů a *P* je pravděpodobnostní funkce (pravděpodobnostní míra). Prvky této trojice jsou omezeny určitými pravidly.

Systém jevů je množina jevů, tedy množina podmnožin množiny *omega* (ne nutně všech), pro kterou platí:

€€
\begin{align*}
\varnothing &\in \mathbb{A} \\
\alpha &\in \mathbb{A} \rightarrow \overline{\alpha} \in \mathbb{A} \\
\forall n \in \mathbb{N} \;:\; \alpha_n &\in \mathbb{A} \rightarrow \bigcup_{n \in \mathbb{N}} \alpha_n \in \mathbb{A} \\
\end{align*}
€€

Formálně se takový systém podmnožin označuje jako **sigma-algebra**. Například jednou z možných sigma-algeber množiny **{a,b,c,d}** je množina **{{0},{a,b},{c,d},{a,b,c,d}}**.

Nejmenší sigma-algebrou podmnožin reálných čísel, která obsahuje všechny intervaly, je **Borelova sigma-algebra**. Značí se velkým písmenem *B* a obsahuje všechny intervaly otevřené, uzavřené i polouzavřené, jejich spočetná sjednocení a některé další množiny.

Jako **pravděpodobnostní funkce** se označuje taková funkce, pro kterou platí:

€€
\begin{align*}
P &\;:\; \mathbb{A} \rightarrow \langle 0,1 \rangle \\
P(\Omega) &= 1 \\
\forall \alpha,\beta \in \mathbb{A} &\;:\; \alpha \cap \beta = \varnothing \rightarrow P(\alpha \cup \beta) = P(\alpha) + P(\beta))\\
\end{align*}
€€

### Srovánání modelů

| Laplaceův model | Kolmogorův model |
|---|---
| konečný počet jevů | nekonečný počet jevů jevů
| racionální pravděpodobnost  | pravděpodobnost může být i iracionální
| jevy s nulovou pravděpodobností jsou nemožné | jevy s nulovou pravděpodobností jsou možné
| pravděpodobnosti jsou určeny jen strukturou jevů | pravděpodobnosti nejsou určeny jen strukturou jevů

### Důsledky a odvozené vztahy

#### Jev opačný

€€ 
\begin{align*}
\overline{A} &= \Omega \setminus A \\
P(\overline{A}) &= 1 - P(A) \\
\end{align*}
€€

#### Součet jevů

Součet jevů *A*, *B* nastane právě tehdy, když nastane alespoň jeden z jevů *A* nebo *B*.

€€ P(A \cup B) = P(A) + P(B) - P(A \cap B) €€

Vzorec pro tři jevy vytvořený principem inkluze a exkluze:

€€ 
\begin{align*}
| A \cup B \cup C | &= |A| + |B| + |C| - \\
& -(|A \cap B|+|A \cap C|+|B \cap C|) - \\
& -(|A \cap B \cap C|)
\end{align*} 
€€

#### Součin jevů

Součin jevů *A*, *B* nastane právě tehdy, když nastanou oba jevy *A* a *B* současně.

€€ P(A \cap B) = P(A) \cdot P(B) €€

#### Jevy nezávislé

Nezávislé jevy *A* a *B* jsou takové jevy, pro které platí:

€€ P(A \cap B) = P(A) \cdot P(B) €€

Rozlišuje se nezávislost podvojná a vzájemná. Ze vzájemné nezávislosti vyplývá nezávislost podvojná, ne však naopak.

#### Jevy neslučitelné

Dva jevy se nazývají neslučitelné, jestliže nemohou nastat současně. Jsou reprezentovány disjunktními množinami. Pro dva neslučitelné jevy *A*, *B* tedy platí:

€€ P(A \cup B) = P(A) + P(B) €€

Rozlišuje se neslučitelnost podvojná a vzájemná.

#### Systém neslučitelných jevů

Jevy *A1* až *An* tvoří systém neslučitelných jevů, mají-li všechny dvojice dvou různých jevů prázdný průnik. Tento systém se nazývá **úplný**, právě když jejich sjednocením vznikne celá množina *omega*.

![systém neslučitelných jevů](omega4.png)

#### Benferroniho nerovnost

€€ P(A \cap B) \geq P(A) + P(B) - 1 €€

#### Podmíněná pravděpodobnost

Podmíněná pravděpodobnost hovoří o pravděpodobnosti jevu *A* za předpokladu, že je jiný jev *B* již jistý.

Jev *B* je jistý, proto se *B* stane "novou množinou omega" a "novým neznámým jevem" je průnik *A* a *B*. Již zde je jasné, v případě nulového průniku obou jevů bude nulová i podmíněná pravděpodobnost.

€€ P (A|B) = \frac{P(A \cap B)}{P(B)} €€

Vztah lze upravit do dalšího často používaného tvaru:

€€ P(A \cap B) = P(A) \cdot P(B|A) = P(B) \cdot P(A|B) €€

#### Věta o úplné pravděpodobnosti

Nechť je dán (spočetný) úplný systém jevů *B1* až *Bn* s nenulovou pravděpodobností a libovolný jev *A*. Pro pravděpodobnost jevu *A* platí:

€€ P(A) = \sum_{i=1}^{n} P(B_i) \cdot P(A|B_i) €€

#### Bayesova věta

Bayesova věta uvádí do vztahu podmíněnou pravděpodobnost jevů *A*, *B* a podmíněnou pravděpodobnost opačnou. Autorem je anglický duchovní **Thomas Bayes** (1702-1761).

€€ P(A|B) = \frac{P(B|A) \cdot P(A)}{P(B)} €€

Pro úplný systém vzájemně neslučitelných jevů *A1* až *An* lze vztah s využitím věty o úplné pravděpodobnosti přepsat do rozšířeného tvaru:

€€ P(A_i|B) = \frac{P(B|A_i) \cdot P(A_i)}{\sum_{j=1}^{n} P(B|A_j) \cdot P(A_j)} €€

Tuto větu lze použít například ke klasifikaci. Může existovat několik jevů *B1* až *Bn*, které budou hrát roli **příznaků**. Jevy *A1* až *An* budou představovat sledované **třídy**. Po naměření příznaku *Bj* se dle Bayesovy věty vypočítají pravděpodobnosti *P(Ai|Bj)* a vybere se ta nejvyšší.

Znalost či zkušenost v tomto případě reprezentují podmíněné pravděpodobnosti *P(Bj|Ai)*. "Obvyklá" pravděpodobnost dané třídy je reprezentována tzv. **apriorní pravděpodobností** *P(Ai)*. Vypočítaná podmíněná pravděpodobnost dané třídy se pak označuje jako **aposteriorní pravděpodobnost** *P(Ai|Bj)*.

### Reference

- předmět X01MVT na FEL ČVUT
- M. Navara: Pravděpodobnost a matematická statistika
- V. Rogalewicz: Pravděpodobnost a statistika pro inženýry