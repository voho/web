## GoF: Facade (fasáda)

### Situace

Složitost systému je příliš vysoká vzhledem k nejčastěji využívaným funkcím nebo je z nějakých jiných důvodů vhodné vytvořit jednotné rozhraní k určité části jeho funkcionality.

```uml:class
left to right direction

class Layer1 {
  layer1Operation1()
  layer1Operation2()
}

class Layer2 {
  layer2Operation2()
  layer2Operation3()
}

class Layer3 {
  layer3Operation1()
  layer3Operation2()
}

Client -down-> Layer1
Client -down-> Layer2
Client -down-> Layer3

note bottom of Client
  operation1: layer1, layer3
  operation2: layer1, layer2, layer3
  operation3: layer2
end note
```

### Řešení

Řešení je mezi systémem a vnějším světem vytvořit další mezivrstvu, na níž se soustředí vnější požadavky a která odstíní klienty od systému. Mezivrstva bude tyto požadavky distribuovat dál mezi jednotlivé komponenty systému, které jsou pro splnění požadavku potřebné. Tato mezivrstva se nazývá **fasáda** a podobně jako skutečná fasáda u domů zakrývá detaily, které nemají být vidět (cihly). Klientovi odpadne nutnost znát systém do detailu a místo toho bude využívat fasádu s mnohem jednodušším rozhraním.

Výhody tohoto řešení jsou následující:

- sníží počet tříd, které musí klient znát a se kterými musí komunikovat
- umožní výměnu či změnu komponent "za fasádou" beze změny jejího vnějšího rozhraní
- vznikne možnost k funkcím jednotlivých komponent přidat i jednoduchou řídící logiku
- centralizace dané úlohy sníží duplicitu kódu
- vznikne možnost ke komponentám přidat jednoduchou řídící logiku

```uml:class
left to right direction

class Layer1 {
  layer1Operation1()
  layer1Operation2()
}

class Layer2 {
  layer2Operation2()
  layer2Operation3()
}

class Layer3 {
  layer3Operation1()
  layer3Operation2()
}

class Facade {
  operation1()
  operation2()
  operation3()
}

Client -> Facade
Facade -down-> Layer1
Facade -down-> Layer2
Facade -down-> Layer3
```

#### Varianty

Fasádu nemusí tvořit jediná třída, některé systémy mohou vyžadovat fasádu rozsáhlejší. Složitost fasády by však nikdy neměla přesáhnout složitost zapouzdřeného systému samotného či obtížnost řešené úlohy.

Za nejjednodušší formu fasády lze považovat i [návrhový vzor Proxy](wiki/proxy), ve kterém tvoří odstíněný systém jedna jediná třída.

### Příklad

Vhodným příkladem fasády může být například jednoduchá matematická knihovna, která bude klientovi poskytovat několik jednoduchých (avšak netriviálních) operací. K provedení těchto "složitějších" operací budou použity jiné třídy představující elementární výpočty. Klient by zcela jistě mohl vzít elementární výpočty a vše provést sám - fasáda však koncentruje všechny užitečné výpočty na jedno místo, zabrání duplicitě kódu a v neposlední řadě i odstraní nutnost o elementárních výpočtech vůbec vědět.

```uml:class
class Adder {
  add(a: number, b: number): number
  subtract(a: number, b: number): number
}

class Multiplier {
  multiply(a: number, b: number): number
  divide(:a number, b: number): number
}

class Facade {
  negative(a: number): number
  mean(a: number, b: number): number
  square(a: number): number
}

Facade -down-> Adder
Facade -down-> Multiplier
Client -> Facade
```

#### Elementární výpočty

Nejprve vytvoříme třídy, které zajistí elementární výpočty. Bude to sčítačka a násobička.

```java
public class Adder {
  /**
   * Vrátí součet dvou čísel.
   * @param a sčítanec
   * @param b sčítanec
   * @return součet
   */
  public double add(final double a, final double b) {
    return a + b;
  }

  /**
   * Vrátí rozdíl dvou čísel.
   * @param a menšenec
   * @param b menšitel
   * @return rozdíl
   */
  public double subtract(final double a, final double b) {
    return a - b;
  }
}
```

```java
public class Multiplier {
  /**
   * Vrátí činitel dvou čísel.
   * @param a činitel
   * @param b činitel
   * @return součin
   */
  public double multiply(final double a, final double b) {
    return a * b;
  }

  /**
   * Vrátí podíl dvou čísel.
   * @param a dělenec
   * @param b dělitel
   * @return podíl
   */
  public double divide(final double a, final double b) {
    return a / b;
  }
}
```

#### Fasáda

Z elementárních výpočtů vytvoříme kompozici, kterou rozšíříme o několik metod. Vzniklá třída bude naší novou fasádou.

```java
public class Facade {
  /**
   * sčítačka
   */
  private final Adder adder = new Adder();
  /**
   * násobička
   */
  private final Multiplier multiplier = new Multiplier();

  /**
   * Vrátí číslo opačné.
   * @param a číslo A
   * @return číslo opačné k číslu A
   */
  public double negative(final double a) {
    return this.adder.subtract(0, a);
  }

  /**
   * Vrátí aritmetický průměr dvou čísel.
   * @param a první číslo
   * @param b druhé číslo
   * @return průměr obou čísel
   */
  public double mean(final double a, final double b) {
    return this.multiplier.divide(this.adder.add(a, b), 2.0);
  }

  /**
   * Vrátí druhou mocninu (čtverec) čísla.
   * @param a číslo A
   * @return druhá mocnina čísla A
   */
  public double square(final double a) {
    return this.multiplier.multiply(a, a);
  }
}
```

#### Použití

```java
final Facade facade = new Facade();

// = 5
System.out.println(facade.negative(-5));
// = 15
System.out.println(facade.mean(10, 20));
// = 25
System.out.println(facade.square(5));
```

### Reference

- Rudolf Pecinovský: Návrhové vzory
- http://c2.com/cgi/wiki?FacadePattern