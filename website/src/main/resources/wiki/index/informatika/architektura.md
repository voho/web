## Systémová architektura

Co je softwarová architektura a jaká je její úloha?

> Architecture is about the important stuff. Whatever that is *R. Johnson*

Formálně řečeno, architektura je soubor komponent a jejich vzájemných vztahů. 
Je to **množina důležitých rozhodnutí** o věcech, které můžeme ovlivnit.

Architektura software se často přirovnává k architektuře budov.
Tato analogie nefunguje příliš dobře, ale nějaká podobnost tu je.

* Architektura vzniká v určitém čase, s určitými předpoklady, a řeší známé požadavky.
* Architektura předepisuje výsledný tvar či ideál, kterého se snažíme dosáhnout. 
* Všichni, kteří na produktu pracují, jsou s tímto ideálem obeznámeni, mají společnou vizi a tak mohou efektivněji spolupracovat.
* Architektura poskytuje podrobné plány a rozličné pohledy na výsledný produkt, čímž odstraňuje nejednoznačnost.

Jsou tu však i nějaké zásadní rozdíly.

Software lze narozdíl od staveb celý do posledních kousíčků rozebrat a opět složit.
K jeho stavbě není potřeba žádný materiál, pouze čas.

Architekt vyvažuje mezi náklady a přidanou hodnotu a snaží se navrhnout takovou architekturu, která skvěle vyřeší ty nejdůležitější požadavky a bude alespoň uspokojivě řešit požadavky méně důležité.

> Good design adds value faster than it adds cost. *Thomas C. Gale*

Kvalitní architektura se projeví v čase. Čím je původní architektura kvalitnější, tím méně času a nákladů je potřeba k implementaci dalšího požadavku. Za cenu pomalejšího rozjezdu projektu získáme rychlejší růst přidané hodnoty později.

![Vliv kvality software na růst přidané hodnoty](https://martinfowler.com/articles/is-quality-worth-cost/both.png)

Jako v každém oboru lidské činnosti, i pro softwarovou architekturu existují určité šablony a vzory, které lze s určitou dávkou kreativity přepoužít. 
U ustálených vzorů již známe jejich výhody a nevýhody v praxi, a můžeme se tedy vyhnout některým chybám.
Zároveň se můžeme inspirovat myšlenkami někoho, kdo již podobný problém řešil, a ušetřit si čas.
Vzory se samozřejmě i různě kombinovat a prolínat.
 
Několik z těchto vzorů naleznete i zde na dalších podstránkách.

> There are two ways of constructing a software design. One way is to make it so simple that there are obviously no deficiencies. And the other way is to make it so complicated that there are no obvious deficiencies. *C. A. R. Hoare*

### Reference

* https://martinfowler.com/architecture/
* https://learning.oreilly.com/library/view/software-architecture-patterns/9781491971437/
* https://towardsdatascience.com/10-common-software-architectural-patterns-in-a-nutshell-a0b47a1e9013
