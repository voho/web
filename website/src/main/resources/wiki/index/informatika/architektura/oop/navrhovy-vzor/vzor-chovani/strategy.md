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

```java
/**
 * Strategie pro násobení dvou čísel.
 * 
 * @author Vojtěch Hordějčuk
 */
public interface Strategy
{
  /**
   * Vynásobí dvě čísla.
   * 
   * @param a
   * činitel
   * @param b
   * činitel
   * @return součin
   */
  public int multiply(int a, int b);
}
```

```java
/**
 * Standardní implementace násobení.
 * 
 * @author Vojtěch Hordějčuk
 */
public class TimesStrategy implements Strategy
{
  @Override
  public int multiply(int a, int b)
  {
    return a * b;
  }
}
```

```java
/**
 * Implementace násobení pomocí sčítání.
 * 
 * @author Vojtěch Hordějčuk
 */
public class PlusStrategy implements Strategy
{
  @Override
  public int multiply(int a, int b)
  {
    int i = Math.abs(b);
    int r = a;
    
    while (i > 1)
    {
      r += a;
      i--;
    }
    
    return (b < 0) ? -r : r;
  }
}
```

##### Kontext

Kontext uchovává zvolenou strategii.

```java
/**
 * Kontext obsahující strategii.
 * 
 * @author Vojtěch Hordějčuk
 */
public class Context
{
  /**
   * aktivní strategie
   */
  private Strategy strategy;
  
  /**
   * Vytvoří novou instanci.
   * 
   * @param strategy
   * zvolená strategie
   */
  public Context(Strategy strategy)
  {
    this.strategy = strategy;
  }
  
  /**
   * Vynásobí dvě čísla.
   * 
   * @param a
   * činitel
   * @param b
   * činitel
   * @return součin
   */
  public int multiply(int a, int b)
  {
    return strategy.multiply(a, b);
  }
}
```

##### Test

Násobení dvou čísel se provede dvakrát, pokaždé s jinou strategií. V obou případech by měl být výsledek stejný, a to i přesto, že byly použité dva naprost odlišné algoritmy. Klient nemusí vědět o tom, jak algoritmy pracují.

```java
// test první strategie

Context context1 = new Context(new TimesStrategy());
int r1 = context1.multiply(3, 5);
System.out.println(r1); // 3*5 = 15

// test druhé strategie

Context context2 = new Context(new PlusStrategy());
int r2 = context2.multiply(3, 5);
System.out.println(r2); // 3*5 = 3+3+3+3+3 = 15
```

### Reference

- předmět X36ASS na FEL ČVUT
- předmět X36OBP na FEL ČVUT
- http://www.vico.org/pages/PatronsDisseny/Pattern%20Strategy/index.html
- http://objekty.vse.cz/Objekty/Vzory-Strategy