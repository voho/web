## GoF: Template (šablona)

Návrhový vzor Template se používá v případě, že existuje nějaký [algoritmus](wiki/algoritmus) s volitelnými kroky a my chceme, aby všechny potenciální podtřídy mohly požadované kroky algoritmu implementovat jinak bez nutnosti zasahovat do původního algoritmu.

### Řešení

Třídu obsahující algoritmus nazveme *Template*.

Každý krok algoritmu bude reprezentován voláním jedné metody. Tato metoda může obsahovat nějakou rozumnou výchozí implementaci (pokud taková existuje), nebo může být deklarována jako abstraktní, aby byly podtřídy k její implementaci donuceny. Potom již zbývá jen implementovat potřebné podtřídy a implementovat či překrýt potřebné kroky.

```uml:class
abstract class Template <<interface>> {
  +doComplexTask()
  {abstract} #taskStep1()
  {abstract} #taskStep2()
  {abstract} #taskStep3()
}

class ConcreteRealization1 extends Template {
  #taskStep1()
  #taskStep2()
  #taskStep3()
}
class ConcreteRealization2 extends Template {
  #taskStep1()
  #taskStep2()
  #taskStep3()
}

note bottom of ConcreteRealization2: all steps implemented in another way
note bottom of ConcreteRealization1: all steps implemented one way
note right of Template
  doComplexTask = (
    taskStep1()
    taskStep2()
    taskStep3()
  )
end note
```

### Varianty

- základní třída se šablonou může obsahovat výchozí implementaci některých kroků a podtřídy mění jen ty, které nevyhovují
- jedna třída může obsahovat více šablon
- jednotlivé kroky mohou být skryté či veřejné
- podtřída může změnit i pořadí kroků

### Související vzory

- [Strategy](wiki/strategy) - jednotlivé metody volané v šabloně jsou zapouzdřeny v samostatné třídě
- [Factory Method](wiki/factory-method) - speciální a celkem častý typ šablonové metody pro vytváření objektů

### Příklad

#### Osoba

Náš příklad bude představovat osobu a její denní činnosti. Konkrétní zaměstnání

```java
public abstract class Person {
  public void printDailyRoutine() {
    // this is a template method
    wake();
    work();
    relax();
    sleep();
  }

  abstract protected void wake();
  abstract protected void work();
  abstract protected void relax();
  abstract protected void sleep();
}
```

#### Zaměstnanec

```java
public class Worker extends Person {
  protected void wake() {
    System.out.println("Waking up at 6:00.");
  }

  protected void work() {
    System.out.println("Going to work. Working...");
  }

  protected void relax() {
    System.out.println("Watching stupid movies.");
  }

  protected void sleep() {
    System.out.println("Going to bed.");
  }
}
```

#### Dítě

```java
public class Child extends Person {
  protected void wake() {
    System.out.println("Waking up at 7:00.");
  }

  protected void work() {
    System.out.println("Going to school.");
  }

  protected void relax() {
    System.out.println("Playing with other kids.");
  }

  protected void sleep() {
    System.out.println("Going to bed.");
  }
}
```

#### Použití

```java
Person p = new Worker();
p.printDailyRoutine();

// -- prints --
// Waking up at 6:00.
// Going to work. Working...
// Watching stupid movies.
// Going to bed.
```

```java
Person p = new Child();
p.printDailyRoutine();

// -- prints --
// Waking up at 7:00.
// Going to school.
// Playing with other kids.
// Going to bed.
```

### Reference

- http://sourcemaking.com/design_patterns/template_method