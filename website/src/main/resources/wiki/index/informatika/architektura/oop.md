## Objektově orientované programování

> The phrase 'object-oriented' means a lot of things. Half are obvious, and the other half are mistakes. *Paul Graham*

Elementárním prvkem objektového programování je **objekt**, což je koncepčně ucelená jednotka skrývající svůj vnitřní stav. S vnitřním stavem objektu mohou tedy pracovat jen jeho vlastní procedury. Ty jsou z okolního prostředí ovládány pomocí veřejného rozhraní. Protože nic kromě veřejného rozhraní okolní svět nevidí, může od všeho ostatního abstrahovat a nad objektem uvažovat bez znalosti jeho vnitřní struktury. 

Objekty lze vzájemně uvádět do různých vztahů, například je skládat dohromady k vytvoření větších celků (kompozice), vytvářet mezi nimi rozličné hierarchie (zobecnění, dědičnost) nebo je seskupovat (např. množina, seznam). Návrh těchto vztahů je předmětem **objektového návrhu** v analytické fázi.

### Základní pojmy

#### Objekt (object)

Objekt je koncepčně ucelená jednotka daná těmito součástmi:

- vnitřní stav (soubor proměnných, tzv. **atributy** - mohou to být i další objekty)
- vnitřní chování (soubor procedur, tzv. **metody**)
- protokol zpráv (udává veřejné rozhraní a způsob jeho mapování na vnitřní procedury objektu)

#### Třída (class)

Třída je schéma, které popisuje vnitřní strukturu objektu a jeho vnější rozhraní. Na základě třídy je možné tvořit jednotlivé objekty. Některé jazyky mají schopnost modifikovat třídy za běhu (tzv. **reflexe**) nebo prohlížet jejich strukturu (tzv. **introspekce**).

#### Instance třídy (instance)

Instance třídy je datová struktura v paměti počítače, vytvořená podle dané třídy. Jednotlivé instance se od sebe liší umístěním (adresou) v paměti a vnitřním stavem (obsahem vnitřních proměnných). Adresu aktuální instance mívá programátor v objektově orientovaných jazycích k dispozici pod speciálním klíčovým slovem (např. *this* nebo *self*).

#### Typ (type)

Typ je velmi abstraktní pojem, který se v objektovém programování používá jako prostředek pro omezení množiny hodnot, kterých může nabývat určitá proměnná . Toto dobrovolné omezení slouží ke kontrole korektnosti programu a jeho dokumentaci. Ve většině programovacích jazyků jsou některé základní typy (řetězec, číslo, objekt, atd.) předdefinovány a nové typy se vytváří jejich rekurzivním skládáním.

Ve většině objektově orientovaných jazyků je typ nějakým způsobem vázán na třídu, nebo je třída přímo typem instance.

#### Atributy

Jako atributy se označují proměnné které definují vnitřní stav objektu.

#### Metody

Jako metody se označují procedury, které (jako jediné) pracují s vnitřním stavem objektu.

### Vzájemné vztahy objektů

#### Zobecnění

Zobecnění potlačuje rozdíly mezi třídami a zdůrazňuje společné vlastnosti. Opakem zobecnění (generalizace) je specifikace.

```uml:class
bike -up-|> vehicle
mountain -up-|> bike
car -up-|> vehicle
lorry -up-|> car
truck -up-|> car
sedan -up-|> car
```

Tento princip se v objektově orientovaných jazycích velmi často používá jako prostředek pro znovupoužití kódu a k implementaci polymorfismu. Pro tento účel se používá častěji pojem **dědičnost** (inheritance). Dědičnost umožňuje vytvářet hiearchie mezi třídami. Třída výše v hierarchii se nazývá **předek** (nadřída, superclass) a třída níže v hierarchii **potomek** (podtřída, subclass). Potomek může mít i více předků, což někdy vede na tzv. problém diamantu. Proto se ve většině jazyků používá dědičnost jednoduchá (každá třída může mít jen jednoho potomka). Řešením jsou i tzv. **traity**, které zjemňují granularitu objektového kódu.

Formálně je dědičnost € C=P+\delta P €, kde €P€ je předek a €C€ je potomek. Úspora kódu při vhodném použití dědičnosti je zajištěna tak, že se v potomkovi uvádí jen změny, které jej odlišují od předka.


Dědičnost umožňuje programátorovi:

- bez zbytečné práce a duplicity kódu zachovat to, co bylo v předkovi dobré
- dodat to, co předkovi chybí
- změnit to, co mu v předkovi nevyhovuje

Použitím dědičnosti by neměl být porušen tzv. **substituční princip Liskové** (Liskov substitution principle).

#### Klasifikace

Klasifikace potlačuje detaily instancí a zdůrazňuje vlastnosti třídy jako celku. Opakem klasifikace je instanciace.

```uml:class
my -up-|> car
your -up-|> car
his -up-|> car
```

#### Kompozice

Kompozice potlačuje detaily komponent a zdůrazňuje rysy celku. Opakem kompozice je dekompozice.

```uml:class
head -up-|> human
torso -up-|> human
hands -up-|> human
left_hand -up-|> hands
right_hand -up-|> hands
legs -up-|> human
left_leg -up-|> legs
right_leg -up-|> legs
```

#### Seskupování

Seskupování potlačuje rozdíly jednotlivých objektů ve skupině a zdůrazňuje skupinu jako celek. Opakem seskupování je individualizace.

```uml:class
peters_car -up-|> white_cars
jacks_car -up-|> white_cars
dianas_car -up-|> blue_cars
blue_cars -up-|> cars
white_cars -up-|> cars
```

### Historie

Historie objektově orientovaného programování začala v šedesátých letech 20. století. Prvním programovacím jazykem s třídami a objekty byla **Simula 67**, za jejímž vznikem stáli dva norští vědci (Kristen Nygaard, Ole-Johan Dahl). Tento jazyk byl původně určen k simulaci interakcí několika lodí.

Simula 67 inspirovala několik vědců z firmy XEROX, kteří následně vytvořili vlastní jazyk Smalltalk a začali jako první používat termín „objektově orientované programování“. Simula 67 silně ovlivnila i Bjarne Stroustrupa při návrhu jazyka C++.
