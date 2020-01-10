## Pravidlo Deméter

Projekt [Deméter](http://www.ccs.neu.edu/research/demeter/), pojmenovaný podle řecké bohyně obilí a plodnosti, byl zahájen na **Northeastern University** a věnuje se výzkumu v oblasti [objektového programování](wiki/oop). Jeho řešitelé postupně publikovali několik vědeckých článků, ze kterých mohou objektoví návrháři na celém světě čerpat užitečné rady a doporučení. Z nich je asi nejznámnější tzv. **pravidlo Deméter pro metody** (zkráceně též pravidlo Deméter nebo Demétřino pravidlo), za kterým stojí Karl Lieberherr, Ian Holland a Arthur Riel.

### Pravidlo pro metody

Pravidlo Deméter je obecné doporučení pro objektový návrh, které vychází z principu minimální znalosti objektu o zbytku systému. Jedná se také o návod, jak kontrolovat, že implementace metod zbytečně nezvyšuje závislost tříd na dalších třídách, které nejsou pro její funkci bezpodmínečně nutné. Objekt by podle něj měl činit minimální předpoklady o struktuře a chování ostatních objektů.  Toho lze dosáhnout tak, že bude každý objekt komunikovat jen se svými nejbližšími sousedy, kde sousednost dvou objektů znamená možnost jednoho objektu posílat zprávy objektu druhému (volat jeho veřejné metody).

Formálně pravidlo Deméter říká, že metoda *M* objektu *O* může volat metody jen a pouze těchto objektů:

- objektu *O*
- parametrů metody *M*
- objektů vytvořených v metodě *M*
- objektů obsažených v objektu *O*

V systému, který striktně dodržuje pravidlo Deméter, je o vykonání určité akce požádán kontejner, který zajistí vykonání této akce pomocí svých komponent. Výhodou takto navrženého systému je nízká mezikomponentová závislost, což zvýší rezistenci systému vůči změnám. Nevýhodou je však přítomnost velkého množství delegací, tedy malých "distribučních" metod, které mohou vést ke zmatení a k poklesu výkonu.

### Odvozená pravidla

Pravidlo Deméter bylo dále rozvíjeno i jinými autory, kteří přišli s vlastními výklady a dalšími podobnými pravidly. Zde je několik z nich:

1. Požadavky na službu externích objektů by se měly nacházet v interních neveřejných metodách. To dovolí podtřídám, aby tyto požadavky mohly překrýt a změnit (toto zhruba odpovídá návrhovému vzoru [Template](wiki/template).
1. Kdykoliv objekt žádá o vytvoření jiného externího objektu, měl by to udělat pomocí své vlastní neveřejné metody. To umožňuje podtřídám vytvářet instance jiných typů a využít výhod polymorfizmu.
1. Veřejná metoda *M* třídy *O* by měla volat pouze metody třídy *O* a metody svých nadtříd.

### Reference

- http://www.ccs.neu.edu/research/demeter/papers/publications.html
- http://www.ccs.neu.edu/research/demeter/demeter-method/LawOfDemeter/general-formulation.html
- http://www.ccs.neu.edu/research/demeter/papers/law-of-demeter/oopsla88-law-of-demeter.pdf
- http://www.cmcrossroads.com/bradapp/docs/demeter-intro.html
- http://www.c2.com/cgi/wiki?LawOfDemeter
