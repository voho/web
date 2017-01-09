## Elektromagnetismus

### Elektromagnetické spektrum

| Skupina | Frekvence
|---|---
| radiové vlny | 3 kHz až 300 MHz
| mikrovlny | 0.3 až stovky GHz
| infračervené záření | 1e11 až 4e14 Hz
| viditelné světlo | 4e14 až 7,5e14 Hz
| ultrafialové záření | 7,5e14 až 6e16 Hz
| měkké rentgenové záření | 1e16 až 1e18 Hz
| tvrdé rentgenové záření | 1e18 až 1e22 Hz
| záření gama | více než 1e18 Hz

### Kirchhoffovy zákony

1. Součet všech proudů přitékajících do uzlu je v každém okamžiku roven nule. Proudy tekoucí do uzlu bereme se záporným znaménkem a proudy vytékající z uzlu s kladným znaménkem. (součet proudů do uzlu vstupujících je roven součtu proudů z uzlu vystupujících)
1. Součet úbytků napětí na rezistorech je v uzavřené smyčce stejný jako součet elektromotorických napětí zdrojů. (algebraický součet všech napětí v uzavřené smyčce je roven nule)

€€ \sum _{k=1}^{n}I_{k}=0, \sum _{k=1}^{n}U_{k}=0 €€

### Ohmův zákon

Elektrický proud v elektricky vodivém předmětu je přímo úměrný elektrickému napětí přiloženému na tento předmět, konstantou úměrnosti je vodivost. Je-li napětí na koncích vodiče stálé, je proud nepřímo úměrný odporu vodiče.

€€ I = G \cdot U = \frac{1}{R} \cdot U = \frac{U}{R} €€

- *I* je elektrický proud
- *G* je elektrická vodivost
- *U* je elektrické napětí
- *R* je elektrický odpor

### Maxwellovy rovnice

Maxwellovy rovnice zformuloval v roce 1865 skotský badatel **James Clark Maxwell**. Jsou to základní a velmi důležité vztahy klasické teorie elektromagnetického pole.

#### Integrální tvar

První rovnice odpovídá **Gaussovu zákonu**, druhá představuje zobecněný **Ampérův zákon**, třetí reprezentuje **Faradayův indukční zákon** a čtvrtá vyjadřuje **neexistenci magnetických nábojů**.

€€
\begin{align*}
\oint_{S} \mathbf{D} \cdot d \mathbf{S} &= Q \\
\oint_{l} \mathbf{H} \cdot d \mathbf{l} &= I + \int_{S} \frac{\partial \mathbf{D}}{\partial t} \cdot d \mathbf{S} \\
\oint_{l} \mathbf{E} \cdot d \mathbf{l} &= - \int_{S} \frac{\partial \mathbf{B}}{\partial t}\cdot d \mathbf{S} \\
\oint_{S} \mathbf{B} \cdot d \mathbf{S} &= 0 \\
\end{align*}
€€

#### Diferenciální tvar

První dvě rovnice popisují vztah mezi **nábojovou hustotou volných nábojů**, **hustotou volných proudů** a vektory **elektromagnetického pole**. Zbývající dvě rovnice představují obecně platné vlastnosti vektorů.

€€
\begin{align*}
\mathrm{div}\, \mathbf{D} &= \rho_{v} \\
\mathrm{rot}\, \mathbf{H} &= \mathbf{j} + \frac{\partial \mathbf{D}}{\partial t} \\
\mathrm{rot}\, \mathbf{E} &= - \frac{\partial \mathbf{B}}{\partial t} \\
\mathrm{div}\, \mathbf{B} &= 0 \\
\end{align*}
€€

### Součástky

#### Rezistor

Rezistor je elektrotechnická součástka, která má teoreticky jen jednu stálou a nezávislou vlastnost - **elektrický odpor** (*R*). Ten se udává v ohmech.

Vztah mezi proudem, napětím a odporem udávají následující vztahy odvozené z Ohmova zákona:

€€ 
\begin{align*}
I &= \frac {U}{R} \\
U &= {I} \cdot {R} 
\end{align*}
€€

Skutečný rezistor (lidově "odpor") je malá součástka, která mění část protékajícího proudu na teplo.

Rozdíly oproti ideálnímu rezistoru:

- vlastnosti odporu se mění s teplotou, stářím materiálu a charakteristikami proudu právě protékajícího rezistorem (např. jeho frekvencí v případě střídavého proudu)
- skutečný rezistor také vykazuje parazitní sériovou indukčnost a paralelní kapacitu, které se znatelně projevují hlavně při vyšších frekvencích
- parametry rezistoru nejsou nikdy zcela přesně známé
- skutečný rezistor vykazuje tepelné ztráty a je tedy energeticky neefektivní
- skutečný rezistor dokáže v teplo proměnit jen určitý výkon - nad tuto hranici dojde k jeho poškození
- dochází v něm k šumu (Johnsonův a tepelný šum)

Rezistory se dále vyrábí ve variantách, které dovolují změnu hodnoty jejich odporu. Jsou to například trimry (změna je sice možná, ale nepříliš snadná, aby k ní nedocházelo omylem) nebo potenciometry (změna je žádoucí a častá, tedy snadná, například u hlasitosti).

#### Kapacitor

Kapacitor je ideální elektrotechnická součástka, která má teoreticky jen jednu stálou a nezávislou vlastnost - **kapacitu** (*C*). Ten se udává ve faradech. Kapacitor si lze představit jako tlakovou nádobu či pružinu, která je schopna uchovat a na požádání vydat určitou energii.

Vztah mezi proudem, napětím a odporem udávají následující vztahy:

€€ 
\begin{align*}
U(t) &= \frac{Q(t)}{C} = \frac{1}{C}\int_{t_0}^t I(\tau) \mathrm{d}\tau + V(t_0) \\
I(t) &= \frac{\mathrm{d}Q(t)}{\mathrm{d}t} = C\frac{\mathrm{d}U(t)}{\mathrm{d}t} 
\end{align*}
€€

Sériovým zapojením dvou a více kondenzátorů se celková kapacita snižuje. Převrácenou hodnotu výsledné kapacity lze vypočítat jako součet převrácených hodnot jednotlivých kapacit. Paralelním zapojením kondenzátoru se naopak celková kapacita zvyšuje a výsledná kapacita se vypočte součtem jednotlivých kapacit.

Skutečný kapacitor (kondenzátor) uchovává energii ve formě elektrického pole mezi dvěma vodivými deskami (elektrodami) oddělenými dielektrikem. 

Rozdíly oproti ideálnímu kapacitoru:

- parametry kondenzátoru mění v závislosti na okolním prostředí (teplota) a charakteristice proudu, který do něj přichází
- skutečný kapacitor má maximální povolené napětí, jehož překročením se poškodí
- ve skutečném kapacitoru dochází i ke ztrátě napětí, takže se chová jako rezistor

#### Induktor

!TODO!

### Reference

- předmět X02FY2 na FEL ČVUT
- předmět X02FY1 na FEL ČVUT
- http://aldebaran.feld.cvut.cz/
- http://aldebaran.feld.cvut.cz/vyuka/fyzika_2_sieger/
- http://wiki.matfyz.cz/wiki/8._Maxwellovy_rovnice_a_jejich_z%C3%A1kladn%C3%AD_d%C5%AFsledky
- http://www.cg.tuwien.ac.at/research/vis/seminar9596/1-math/grad.html
- http://en.wikipedia.org/wiki/Maxwell%27s_equations
- http://mathworld.wolfram.com/MaxwellEquations.html
