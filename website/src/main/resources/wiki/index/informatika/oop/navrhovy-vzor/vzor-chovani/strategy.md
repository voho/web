## GoF: Strategy

### Situace

Pro řešení daného problém existuje několik různých [algoritmů](wiki/algoritmus), které mají stejné rozhraní (vstup i výstup). Klient má být od konkrétního algoritmu odstíněn, ale může ovlivnit, jaký algoritmus se použije.

### Problém

Implementace různých algoritmů pro řešení jednoho problému si přímo říká o jejich zobecnění na úrovni vstupu a výstupu. Tak budou algoritmy vzájemně zaměnitelné a klient bude mít možnost zvolit takový algoritmus, který mu v danou chvíli nejlépe vyhovuje. Někdy například bude výhodnější použít pomalejší algoritmus nenáročný na paměť, jindy ten nejrychlejší možný, a to bez ohledu na použité prostředky. Odstíněním klienta od konkrétního algoritmu se zvýší vzájemná nezávislost jednotlivých součástí systému.

### Řešení

Vstup a výstup algoritmů se zobecní a extrahuje do rozhraní. Toto rozhraní bude implementováno několika třídami, přičemž každá z těchto tříd představuje právě jeden algoritmus. Odstínění klienta od konkrétního algoritmu zajistí další třída, která bude uchovávat zvolený algoritmus a delegovat na něj všechny potřebné požadavky od klienta.

#### Varianty

- třída s algoritmem zvolí konkrétní algoritmus sama na základě parametrů
- konkrétní algoritmus se předává v konstruktoru
- konkrétní algoritmus lze nastavit speciální metodou (setterem)

#### UML diagramy

```uml:class
class Strategy <<interface>> {
}
class ConcreteStrategy1 extends Strategy {
}
class ConcreteStrategy2 extends Strategy {
}
class Context {
  setStrategy(s: Strategy)
}
Context -right-> Strategy
Client --> Context
Client ..> Strategy
note right on link: client chooses strategy
```

#### Související vzory

- [State](wiki/state) - vnitřní stav ovlivňuje klient pouze nepřímo

#### Implementace

##### Strategie

Násobení dvou čísel lze implementovat různě. V příkladu jsou to způsoby dva: standardní násobení a postupné sčítání. Oba způsoby výpočtu implementují stejné rozhraní, protože jsou volně zaměnitelné.

```include:java
gof/strategy/Strategy.java
```

```include:java
gof/strategy/TimesStrategy.java
```

```include:java
gof/strategy/PlusStrategy.java
```

##### Kontext

Kontext uchovává zvolenou strategii.

```include:java
gof/strategy/Context.java
```

##### Test

Násobení dvou čísel se provede dvakrát, pokaždé s jinou strategií. V obou případech by měl být výsledek stejný, a to i přesto, že byly použité dva naprost odlišné algoritmy. Klient nemusí vědět o tom, jak algoritmy pracují.

```include:java
gof/strategy/Example.java
```

### Reference

- předmět X36ASS na FEL ČVUT
- předmět X36OBP na FEL ČVUT
- http://www.vico.org/pages/PatronsDisseny/Pattern%20Strategy/index.html
- http://objekty.vse.cz/Objekty/Vzory-Strategy