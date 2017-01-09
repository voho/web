## Kompresní algoritmus

Lidé mají odvěkou touhu získávat a shromažďovat informace. To není v informační době problém. Co je omezené, je kapacita médií a úložišť. Proto vznikaly a stále vznikají různé metody, jak na nich ušetřit místo a mít tak prostor pro další data. Také kapacitou přenosových kanálů se musí nakládat ekonomicky a každá možnost, jak jimi protlačit více informací za časovou jednotku, přijde telekomunikačním společnostem vhod. Právě komprese umožnila vznik a rychlý rozvoj technologií pro vzdálený přenos obrazu či videa.

Komprese je speciální druh kódování dat za účelem zmenšení jejich objemu odstraněním nadbytečné informace (redundance). Kompresní algoritmus definuje přesný postup, jak toho docílit. Musí obsahovat i odpovídající opačný postup pro dekompresi, který původní informaci zpětně zrekonstruuje. Kompresní algoritmus převádí **zdrojové jednotky** (symboly a posloupnosti symbolů - tzv. slova a posloupnosti slov) na **posloupnosti symbolů**. Zdrojové jednotky se mohou označovat jako **vzory** (originály) a výsledné posloupnosti jako **obrazy**.

### Princip komprese

Komprese je založená na odstraňování nadbytečnosti (redundance), která je způsobena především:

- opakováním symbolů
- opakováním slov či frází z těchto symbolů složených
- kontextovou závislostí (např. v angličtině následuje za *Q* většinou *U*)
- neefektivní přímou reprezentací
- nejednotným rozložením symbolů v textu

Obecným principem komprese je přiřazení krátkých kódů častým symbolům a delších kódů symbolům vzácnějším.

### Druhy komprese

#### Ztrátová komprese

Ztrátová komprese využívá nedokonalosti lidských smyslů. Je pouhou aproximací originálu - z informace odstraňuje detaily do té míry, dokud ji ještě lze uspokojivě rekonstruovat. Tento způsob komprese se nejčastěji používá pro hudbu, video a obrázky. Příklady: **JPEG, H.264, MPEG**.

#### Bezeztrátová komprese

Bezeztrátová komprese, jak již název napovídá, zachovává vždy kompletní informaci. Dekomprimovaná data jsou identická originálu. Příklady: **LZ77, LZ78, LZW, Huffmanovo kódování**.

### Další vlastnosti komprese

- **symetrická** - asymptotická složitost komprese i dekomprese je podobná
- **asymetrická** - asymptotická složitost komprese a dekomprese se od sebe významně liší (např. komprese trvá mnohem delší dobu)
- **kaskádová** - komprese je založená na postupném skládání funkcí, dekomprese probíhá v opačném pořadí s opačnými funkcemi
- **proudová** - na vstup kompresního algoritmu přichází jednotlivé symboly, výstup je vytvářen po symbolech
- **bloková** - na vstup kompresního algorimu přichází celý blok dat, výstup je vytvářen po blocích
- **statická** - model kompresního algoritmu se v čase nemění, je přesně dán v okamžiku implementace aplikace
- **semiadaptivní** - komprese má dva průchody: při prvním se projdou celá vstupní data a upraví se model kompresního algoritmu, při druhém průchodu se komprimuje
- **adaptivní** - kódy se připraví až na základě dat a proto se musí pokaždé přenést tabulka znaků
- **lokálně adaptivní** - např. metoda "move to front"
