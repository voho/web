## Statistika

Dalším matematickým nástrojem, který se potýká s náhodou, je statistika. Statistika slouží ke hledání a ověřování pravděpodobnostního modelu systému na základě jeho pozorování. S její pomocí lze z několika možných příčin vybrat tu nejpravděpodobnější a najít v systému i takové závislosti, které nejsou zcela zjevné. Statistika se používá i k vytváření a ladění pravděpodobnostního modelu daného systému.

Statistika je o kladení otázek. Jak známo, na špatnou otázku existuje pouze špatná odpověď. A naopak, v dobré otázce je již část odpovědi ukryta. Proto jsou výsledky statistiky tak často (i záměrně) špatně interpretovány a chápány.

```dot:digraph
rankdir=LR
edge [arrowhead=vee]
consequence [fillcolor=khaki,shape=rectangle]
"cause 1" -> consequence:n
"cause 2" -> consequence:w
"cause 3" -> consequence:s
```

### Předpoklady

#### Zákon velkých čísel

Volně řečeno, zákon velkých čísel říká, že s rostoucím počtem opakovaní nezávislých pokusů se empirické charakteristiky, které popisují výsledky těchto pokusů, blíží k teoretickým charakteristikám.

Slabý zákon velkých čísel říká, že pro jakkoliv malé toleranční pásmo stačí provést jen dostatečně velký počet pokusů, aby bylo velmi pravděpodobné, že se průměr pozorování bude blížit teoretické střední hodnotě (tj. bude ve zvoleném tolerančním pásmu).

€€ \lim _{n\to \infty } \mathrm{P} \!\left(\,|{\overline {X}}_{n}-\mu |>\varepsilon \,\right)=0 €€

Silný zákon velkých čísel říká, že pokud počet pokusů konverguje k nekonečnu, pravděpodobnost toho, že průměr pozorování bude roven teoretické střední hodnotě, bude roven jedné.

€€ \mathrm{P} \!\left(\lim _{n\to \infty }{\bar {X}}_{n}=\mu \right)=1 €€

#### Centrální limitní věta

Volně řečeno, centrální limitní věta říká, že pokud je nějaká náhodná veličina součtem velkého množství nezávislých náhodných veličin, můžeme její rozdělení považovat za přibližně normální.

### Statistická rozdělení

#### Alternativní rozdělení

Alternativní rozdělení je diskrétní rozdělení pravděpodobnosti náhodné proměnné, která s pravděpodobností €p€ nabývá hodnoty 1 a s pravděpodobností €1-p€  hodnoty 0. Jde také o speciální případ binomického rozdělení, kde €n=1€.

Střední hodnota
: € \mathrm{E}(X)=p €

Rozptyl
: € \mathrm{D}(X)=p(1-p) €

#### Rovnoměrné rozdělení

Rovnoměrné rozdělení €R(a,b)€ na intervalu €(a,b)€, kde € a<b €, má ve všech bodech v tomto intervalu konstantní hustotu pravděpodobnosti.

€€ f(x)=\begin{cases}\frac{1}{b-a} & x \in (a,b)\\ 0 & x \notin (a,b)\end{cases} €€

Střední hodnota
: € \mathrm{E}(X)=\frac{a+b}{2} €

Rozptyl
: € \mathrm{D}(X)=\frac{{(b-a)}^{2}}{12} €

Příklady: Chyba při zaokrouhlování, hod kostkou (diskrétní).

#### Binomické (Bernoulliho) rozdělení

Binomické rozdělení €B(n,p)€ popisuje četnost výskytu náhodného jevu v €n€ nezávislých pokusech, v nichž má jev stále stejnou pravděpodobnost €p€. Pokud €n=1€, jde o alternativní rozdělení. Pro velký počet pokusů a malé pravděpodobnosti (€ n \to \infty, p \to 0 €) přechází v rozdělení Poissonovo (pro velká €n€ je také obtížné počítat velká [kombinační čísla](wiki/kombinatorika)).

Příklady: 

- Počet orlů a pannen v několika po sobě následujících hodech mincí (diskrétní)
- Počet hlasů pro dva kandidáty ve volbách
- Počet mužů a žen zaměstnaných v nějaké společnosti
- Počet denních transakcí, které jsou v souladu s regulací
- Počet úspěšných telefonních prodejů, provedených za určitou dobu
- Počet zmetků ve výrobě za určitou dobu

Hustota pravděpodobnosti
: €P(X=x) = \frac{n!}{x!(n-x)} \cdot p^x \cdot (1-p)^{n-x}€

Střední hodnota
: € \mathrm{E}(X)=n \cdot p €

Rozptyl
: € \mathrm{D}(X)=n \cdot p(1-p) €

#### Poissonovo rozdělení

Poissonovo rozdělení €P(\lambda)€ popisuje četnost výskytu náhodného jevu v mnoha nezávislých pokusech, v nichž má jev stále stejnou malou pravděpodobnost €p€. Jedná se o speciální případ binomického rozdělení pro velké €n€ a malé €p€. 
Můžeme říci, že popisuje situace, ve kterých sledujeme výskyt určité málo pravděpodobné události v čase (protože čas je z našeho hlediska spojitý) a kdy se pravděpodobnost jevu dále snižuje s tím, jak daný časový interval zkracujeme.

Příklady:

- Denní počet zákazníků, kteří dorazí na určitou pobočku banky
- Počet nehod na určitém úseku dálnice
- Počet návštěv na webové stránce za hodinu
- Počet tísňových hovorů v Praze během dne
- Měsíční poptávka po určitém produktu
- Počet překlepů v knize

Hustota pravděpodobnosti
: €P(X=x) = e^{-\lambda t \frac{\lambda t^x}{x!}}€

Střední hodnota
: € \mathrm{E}(X)=\lambda €

Rozptyl
: € \mathrm{D}(X)=\lambda €

#### Normální (Gaussovo) rozdělení

!TODO!

### Aplikace

#### Testování hypotéz

!TODO!

#### Odhady

!TODO!

### Reference

- https://www.utdallas.edu/~scniu/OPRE-6301/documents/Important_Probability_Distributions.pdf
- https://is.muni.cz/do/rect/el/estud/prif/ps15/statistika/web/pages/zakon-velkych-cisel.html
- https://is.muni.cz/do/rect/el/estud/prif/ps15/statistika/web/pages/centralni-limitni-veta.html
- https://homen.vsb.cz/~oti73/cdpast1/KAP05/PRAV5.HTM
- http://fyzmatik.pise.cz/871-zakon-velkych-cisel.html
- http://home.zcu.cz/~friesl/hpsb/slabzvc.html