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

Náš příklad bude představovat osobu a její denní činnosti. Konkrétní zaměstnání bude implementováno jako potomek této třídy.

```include:java
gof/template/Person.java
```

#### Zaměstnanec

```include:java
gof/template/Worker.java
```

#### Dítě

```include:java
gof/template/Child.java
```

#### Použití

```include:java
gof/template/Example.java
```

### Reference

- http://sourcemaking.com/design_patterns/template_method