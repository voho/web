## Návrhové vzory

> Humans are pattern-seeking story-telling animals, and we are quite adept at telling stories about patterns, whether they exist or not. *Michael Shermer*

I když je každý softwarový projekt jedinečný, často se v nich vyskytují podobné třídy úloh a pro jejich řešení se používají srovnatelné prostředky. Proto se profesionálové snaží zjistit, jaká řešení typických úloh jsou nejlepší a tato úspěšná řešení sdílet se svými méně zkušenými kolegy. Tyto poznatky postupem času krystalizují do obecných forem a stávají se cennou znalostí každého analytika či vývojáře, který nechce znovu objevovat kolo. Takto ustálené znalosti se označují jako **návrhové vzory**. Taková definice je však stále velmi obecná a v současnosti se nejčastěji používá v oblasti [objektového programování](wiki/oop).

Kvalitní popis návrhového vzoru by měl nastínit kontext, ve kterém se daný vzor používá, popsat jeho strukturu, uvést konkrétní příklad implementace a v neposlední řadě i diskutovat výhody a nevýhody jeho použití. Dále je velmi vhodné daný návrhový vzor srovnat s ostatními a zdůraznit jejich společné i odlišné vlastnosti.

Zkušenosti z praxe ukazují, že znalost návrhových vzorů skutečně zjednodušuje práci a jejich adaptace pomáhá zlepšovat strukturu výsledných zdrojových kódů. Někteří kritici však namítají, že se jedná o zbytečné konstrukce obcházející nedokonalosti některých imperativních programovacích jazyků. Proto by bezmyšlenkovité používání návrhových vzorů nikdy nemělo nahradit vlastní úsudek a zdravý rozum.

### Výhody

- umožňují jednoduše přepoužít expertní řešení bez nutné dlouhodobé zkušenosti
- vyjasňují některé pojmy a zjednodušují tak komunikaci mezi vývojáři

### Nevýhody

- vzory samy o sobě nevedou k přepoužití kódu
- vzory nelze v diskusi jednoduše obhájit či odmítnout, vhodnost jejich použití se projeví v praxi až po delší době
- integraci vzoru provádí člověk a při jeho výběru či implementace může udělat chybu

### Důležitá literatura

#### Gang of Four

> Think of the GoF as helping losers lose less. *R. Gabriel*

Jako "gang of four" (gang čtyř) se označuje skupina programátorů, kteří stojí za slavnou publikací z roku 1994 s názvem [Design Patterns: Elements of Reusable Object-Oriented Software](http://en.wikipedia.org/wiki/Design_Patterns). Jsou to Erich Gamma, Richard Help, Ralph Johnson a John Vlissides. Ve zmíněné publikaci představili první skupinu dvaceti tří dnes již klasických a notoricky známých [návrhových vzorů](wiki/navrhovy-vzor). Alias skupiny zřejmě odkazuje na mocenskou skupinu **四人幫**, která se snažila dostat k moci v Číně po smrti Mao Ce-tunga.

![Design Patterns: Elements of Reusable Object-Oriented Software](http://images.pearsoned-ema.com/jpeg/large/9780201633610.jpg)

#### Martin Fowler

Významný programátor Martin Fowler popsal další návrhové vzory v roce 2003 v publikací [Patterns of Enterprise Application Architecture](http://www.amazon.com/gp/product/0321127420?ie=UTF8&tag=martinfowlerc-20&linkCode=as2&camp=1789&creative=9325&creativeASIN=0321127420). Věnuje se zde především velkým podnikovým aplikacím a problémům, na které lze narazit při jejich návrhu. Stručný výtah z knihy si na [jeho webu](http://martinfowler.com/eaaCatalog/) můžete přečíst i online.

![Patterns of Enterprise Application Architecture](http://martinfowler.com/books/eaa.jpg)
