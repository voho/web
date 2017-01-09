## Visitor (návštěvník)

### Situace

Je definována hiearchie tříd se společným předkem. Tento předek deklaruje metodu, která je v každé podtřídě implementována jinak. Zdrojový kód všech konkrétních implementací se má z podtříd extrahovat a přesunout do jedné nové třídy.

### Problém

Výhodou stávajícího řešení, kdy je specifický kód přímou součástí podtřídy je, že po přidání nové podtřídy nás kompilátor donutí implementovat specifické chování i v této nové podtřídě a pokud je tento kód krátký a jednoduchý, není důvod ho za každou cenu přesouvat pryč. Specifického kódu však na druhou stranu může být obrovské množství nebo se svým charakterem do podtřídy vysloveně nehodí. Pokud například podtřída představuje datovou entitu, není vhodné do ní přidávat jakoukoliv výpočetní či jinou složitější logiku.

```uml:class
class Parent <<interface>> {
  operation()
}
class Child1 extends Parent {
  operation()
}
class Child2 extends Parent {
  operation()
}
class Child3 extends Parent {
  operation()
}
Client -> Parent
note bottom of Child1: code 1
note bottom of Child2: code 2
note bottom of Child3: code 3
```

Je tedy žádoucí vytvořit objektový návrh takový, který umožní kód ze všech podtříd přesunout do třídy nové a ideálně opět využít vlastností kompilátoru, aby hlídal existenci specifické implementace v každé podtřídě. 

První řešení, které nás asi napadne, je triviální: přidat novou třídu a do ní umístit specifickou metodu pro každou podtřídu. Nová třída veřejně vystaví pouze tu nejobecnější možnou metodu - tedy tu, která jako parametr přijímá kořenovou třídu hiearchie. Tato metoda pak musí podle konkrétního typu parametru zvolit správnou metodu a spustit ji.

```uml:class
class Implementation {
  operation(a: Parent)
  doForChild1(a: Child1)
  doForChild2(a: Child2)
  doForChild3(a: Child3)
}
class Parent <<interface>> {
}
class Child1 extends Parent {
}
class Child2 extends Parent {
}
class Child3 extends Parent {
}
Client -> Implementation
```

V programovacích jazycích, které podporují tzv. multiple dispatch, stačí využít přetěžování metod. V nové třídě se nadeklarují metody přetížené tak, aby jako argument přijímaly každou existující podtřídu a výběr té správné varianty zajistí již jazyk za běhu automaticky. V ostatních jazycích (které podporují pouze single dispatch) se tento mechanizmus musí simulovat - triválně kaskádou podmínek *if*, které za běhu ověřují typ parametru a podle něho volají konkrétní metodu pro danou podtřídu.

```java
public void doSomething(Parent a)
{
  if (a instanceof Child1)
  {
    // zavolat implementaci pro třídu Child1
    this.doSomethingFor1((Child1) a);
  }
  else if (a instanceof Child2)
  {
    // zavolat implementaci pro třídu Child2
    this.doSomethingFor2((Child2) a);
  }
  else if (a instanceof Child3)
  {
    // zavolat implementaci pro třídu Child3
    this.doSomethingFor3((Child3) a);
  }
  else
  {
    // fallback pro neznámé podtřídy	
    throw new IllegalArgumentException();
  }
}

protected void doSomethingFor1(Child1 a) {...}
protected void doSomethingFor2(Child2 a) {...}
protected void doSomethingFor3(Child3 a) {...}
```

Tento způsob ale není příliš dobrý, protože je značně nepřehledný a podmínky se musí udržovat konzistentní s aktuální hiearchií odpovídajících tříd. Pokud se do hierarchie přidá nová podtřída a zapomene se na přidání odpovídající větve, může v nastíněném řešení dojít k chybě (**IllegalArgumentException**, viz větev *else*).

### Řešení

Obtížně udržovatelná a těžce srozumitelná kaskáda podmínek *if* se nahradí elegantní spoluprací dvou tříd, které jsou zde označeny jako **Place** a **Visitor**. Třída **Place** požádá vybranou instanci třídy **Visitor** o provedení akce a předá ji sama sebe jako parametr. Třída **Visitor** pak na této instanci zavolá požadovanou metodu, která odpovídá její třídě.

Takto se simuluje double-dispatch v jazycích, které ho nemají. V principu se k identitě příjemce metody přidává ještě druhá chybějící informace o odesílateli.

#### UML diagramy

```uml:class
Client -up-> Visitor
Client -> Place
note right on link: calls place.accept(visitor)
Visitor ..> Place
note right on link: method for each subtype
class Visitor <<interface>> {
  visitPlace1(p: ConcretePlace1)
  visitPlace2(p: ConcretePlace1)
}
class Place <<interface>> {
  accept(v: Visitor)
}
class ConcretePlace1 extends Place {
  accept(v: Visitor)
}
class ConcretePlace2 extends Place {
  accept(v: Visitor)
}
note bottom of ConcretePlace1: v.visitPlace1(this);
note bottom of ConcretePlace2: v.visitPlace2(this);
```

```uml:seq
Client -> Place: accept(visitor)
Place -> Visitor: visit(this)
Place <-- Visitor
Client <-- Place
```

```uml:seq
== Place1 ==
Client -> Place1: accept(visitor)
Place1 -> Visitor: visitPlace1(this)
Visitor --> Place1
Place1 --> Client
== Place2 ==
Client -> Place2: accept(visitor)
Place2 -> Visitor: visitPlace2(this)
Visitor --> Place2
Place1 --> Client
```

### Příklad

#### Cíle návštěv

Cíle návštěv budou dva. První z nich bude kino, druhé muzeum. Oba cíle budou implementovat společné rozhraní.

```java
/**
 * Nějaké zajímavé místo.
 * 
 * @author Vojtěch Hordějčuk
 */
public interface Place
{
  /**
   * Přišel návštěvník.
   * 
   * @param visitor
   * návštěvník
   */
  public void accept(Visitor visitor);
}
```

```java
/**
 * Kino.
 * 
 * @author Vojtěch Hordějčuk
 */
public class Cinema implements Place
{
  @Override
  public void accept(Visitor visitor)
  {
    System.out.println("do kina přišel " + visitor.toString());
    visitor.visit(this);
  }
}
```

```java
/**
 * Muzeum.
 * 
 * @author Vojtěch Hordějčuk
 */
public class Museum implements Place
{
  @Override
  public void accept(Visitor visitor)
  {
    System.out.println("do muzea přišel " + visitor.toString());
    visitor.visit(this);
  }
}
```

#### Návštěvníci

Návštěvníci budou dva. První z nich bude představovat hodného návštěvníka, druhý zlého. Oba budou implementovat společné rozhraní.

```java
/**
 * Obecný návštěvník.
 * 
 * @author Vojtěch Hordějčuk
 */
public interface Visitor
{
  /**
   * Navštíví zadaný koncert.
   * 
   * @param concert
   * cílový koncert
   */
  public void visit(Museum concert);
  
  /**
   * Navštíví zadané kino.
   * 
   * @param cinema
   * cílové kino
   */
  public void visit(Cinema cinema);
}
```

```java
/**
 * Hodný návštěvník.
 * 
 * @author Vojtěch Hordějčuk
 */
public class GoodVisitor implements Visitor
{
  @Override
  public void visit(Museum museum)
  {
    System.out.println(this.toString() + " je v muzeu");
  }
  
  @Override
  public void visit(Cinema cinema)
  {
    System.out.println(this.toString() + " je v kině");
  }
  
  @Override
  public String toString()
  {
    return "pan Hodný";
  }
}
```

```java
/**
 * Zlý návštěvník.
 * 
 * @author Vojtěch Hordějčuk
 */
public class EvilVisitor implements Visitor
{
  @Override
  public void visit(Museum museum)
  {
    System.out.println(this.toString() + " vykradl muzeum.");
  }
  
  @Override
  public void visit(Cinema cinema)
  {
    System.out.println(this.toString() + " udělal nepořádek v kině.");
  }
  
  @Override
  public String toString()
  {
    return "pan Zlý";
  }
}
```

#### Test

Vytvoří se oba druhy návštěvníků. Poté se vytvoří i obě cílová místa. Nakonec se návštěvníci pošlou do obou cílů.

```java
public static void main(String[] args)
{
  // vytvořit návštěvníky
    
  Visitor good = new GoodVisitor();
  Visitor evil = new EvilVisitor();
    
  // vytvořit cíle
   
  Place cinema = new Cinema();
  Place museum = new Museum();
    
  // hodný návštěvník
    
  cinema.accept(good);
  museum.accept(good);
    
  // zlý návštěvník
    
  cinema.accept(evil);
  museum.accept(evil);
}
```

### Reference

- předmět X36ASS na FEL ČVUT
- předmět X36OBP na FEL ČVUT
- http://www.oodesign.com/visitor-pattern.html
- http://sourcemaking.com/design_patterns/visitor
