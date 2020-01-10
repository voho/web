## Utility (knihovna)

Jakmile se v systému objeví skupina souvisejících statických metod, u kterých existuje možnost, že by mohly být užitečné i v jiných částech systému, je vhodné uvažovat o jejich přesunu na nějaké lepší místo. Návrhový vzor Utility říká, že takovým místem má být nová třída. Třída plná statických metod má několik charakteristických vlastností, které je dobré si uvědomit a zvýšit kvalitu její deklarace.

K volání statických metod a čtení statických konstant především není třeba vytvářet instanci třídy a dokonce se to považuje za nežádoucí – instance by například zbytečně zabírala místo v paměti. Proto je lepší vytváření instancí pro jistotu úplně zakázat. Ideální je maximálně využít vyjadřovacích schopností daného jazyka, aby dodržování pravidla hlídal již kompilátor (např. v jazyce Java stačí deklarovat jediný prázdný privátní konstruktor bez parametrů).

Dále se doporučuje zamezit možnosti od takové třídy dědit, protože to nedává smysl. Hlavním přínosem dědičnosti je totiž sdílení kódu a ten lze jednodušeji sdílet voláním metod knihovní třídy. I zde je nejlepší využít vyjadřovacích prostředků daného jazyka (např. v jazyce Java stačí k deklaraci třídy přidat modifikátor **final**).

Výše popsaná třída se nazývá **knihovna**, anglicky **utility class** či **library class**.

První výhodou knihovny je její dobrá testovatelnost – statické metody se totiž obecně velmi dobře jednotkově testují. Další výhodou je zvýšení přehlednosti sdružováním souvisejících metod na jedno jasně určené místo. Jakmile se metoda dostane do knihovny, je pohodlně dostupná celému systému (pokud není její viditelnost omezena) a je-li dostatečně obecná, může snižovat počet duplicit podobného kódu. Toto vše platí ale jen za předpokladu, že všichni programátoři systému znají obsah knihoven a knihovny také používají.

Nevýhodou je možná až příliš nestydaté vystavování metod vnějšímu světu, což může signalizovat nedokonalý objektový návrh. Velké množství knihoven pracujících s rozličnými objekty jde proti základní myšlence objektového programování, a to umisťování dat a funkcí s nimi souvisejících blízko sebe.

### Postup

1. vytipovat související statické metody, které by mohly být užitečné i jiným částem systému
1. vytipované metody přesunout do nově vytvořené knihovní třídy
1. zamezit možnosti vytvořit instanci knihovní třídy (např. jediný privátní konstruktor)
1. zamezit možnosti vytvořit potomka knihovní třídy (např. klíčové slovo **final**)
1. původní volání přesunutých metod nahradit voláním metod v knihovně

### Příklad

#### Knihovní třída

```include:java
gof/utility/ArrayUtility.java
```

#### Použití

```include:java
gof/utility/Example.java
```

### Reference

- Rudolf Pecinovský: Návrhové vzory