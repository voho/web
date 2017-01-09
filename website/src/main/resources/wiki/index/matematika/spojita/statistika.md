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

#### Slabý zákon velkých čísel

Volně řečeno, zákon velkých čísel říká, že s rostoucím počtem opakovaní nezávislých pokusů se empirické charakteristiky, které popisují výsledky těchto pokusů, blíží k teoretickým charakteristikám.

The weak law essentially states that for any nonzero margin specified, no matter how small, with a sufficiently large sample there will be a very high probability that the average of the observations will be close to the expected value; that is, within the margin.

€€ \lim _{n\to \infty } \mathrm{P} \!\left(\,|{\overline {X}}_{n}-\mu |>\varepsilon \,\right)=0 €€

#### Silný zákon velkých čísel

What this means is that as the number of trials n goes to infinity, the probability that the average of the observations is equal to the expected value will be equal to one.

€€ \mathrm{P} \!\left(\lim _{n\to \infty }{\bar {X}}_{n}=\mu \right)=1 €€

### Aplikace

#### Testování hypotéz

!TODO!

#### Odhady

!TODO!

### Reference

- https://is.muni.cz/do/rect/el/estud/prif/ps15/statistika/web/pages/zakon-velkych-cisel.html
- http://fyzmatik.pise.cz/871-zakon-velkych-cisel.html
- http://home.zcu.cz/~friesl/hpsb/slabzvc.html