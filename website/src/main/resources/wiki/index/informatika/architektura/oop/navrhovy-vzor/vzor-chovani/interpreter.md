## GoF: Interpreter (překladač)

Návrhový vzor Interpreter slouží k návrhu struktury, která umožní reprezentaci a vyhodnocování výrazů jednoduchého [formálního jazyka](wiki/formalni-jazyk), například aritmetického výrazu nebo vyhledávacího dotazu. Ve skutečném světě je to například hudebník, který zná význam jednotlivých hudebních značek (noty, pomlky, předznamenání), jejich struktur (takty, sloky) a dokáže je interpretovat hrou na svůj hudební nástroj. Převádí tedy jeden "jazyk" na druhý - v tomto případě notový zápis na zvuk.

### Problém

Řešíme reprezentaci výrazu nějakého (formálního) jazyka jako abstraktní struktury objektů v paměti, abychom s ním mohli dále pracovat - například přeložit jej či vyhodnotit. Návrhový vzor však **neřeší**, jak převést řetězec do této struktury, pouze to, jak má tato struktura vypadat. Zpracováním řetězců (parsování) a jejich převodem na [abstraktní syntaktické stromy](wiki/datova-struktura-strom) se totiž zabývá několik vědeckých oborů a teorie formálních jazyků a gramatik se rozpíná přes IT až k [matematice](wiki/matematika) a lingvistice.

### Řešení

Reprezentovat výraz jednoduchého formálního jazyka znamená vytvořit hierarchickou strukturu, ve které existují dva základní druhy prvků:

- **primitivní výraz**, který je kompletní sám o sobě a ke svému vyhodnocení nepotřebuje žádné další výrazy 
- **složený výraz**, který se skládá z libovolných dalších výrazů (primitivních či složených) a tyto výrazy potřebuje ke svému vyhodnocení

Podobná hierarchie je reprezentována návrhovým vzorem [Composite](wiki/composite), který zde využijeme, pouze upřesníme názvosloví a názvy tříd tak, aby odpovídaly danému použití.

```uml:class
class Expression {
  + solve(Context)
}
AtomicExpression -up-|> Expression
CompositeExpression -up-|> Expression
CompositeExpression o--> Expression
note bottom of CompositeExpression
  solves children expressions
end note
Client -down-> Expression
Client -down-> Context
Expression .left.> Context
```

#### Související vzory

- [Composite](wiki/composite) - obecnější varianta téhož, která není přímo určená pro reprezentaci výrazů
- [Iterator](wiki/iterator) - lze použít pro jednoduché procházení výsledné struktury
- [Visitor](wiki/visitor) - lze použít pro pokročilé procházení výsledné struktury

#### Příklad

##### Logické výrazy

Nejprve definujeme hierarchii logických výrazů:

```include:java
gof/interpreter/LogicalExpression.java
```

```include:java
gof/interpreter/LogicalNotOperator.java
```

```include:java
gof/interpreter/LogicalAndOperator.java
```

```include:java
gof/interpreter/LogicalOrOperator.java
```

Nakonec to bude proměnná, která bude svou hodnotu hledat kontextu. To zajistí, že ji bude možné měnit bez nutnosti měnit strukturu výrazu.

```include:java
gof/interpreter/LogicalVariable.java
```

##### Kontext

```include:java
gof/interpreter/Context.java
```

###### Příklad použití

Takto se návrhový vzor použije: nejprve vytvoříme strukturu výrazu v paměti a kontext, který posléze použijeme k vyhodnocení hodnoty výrazu. 

Ukázkový výraz: €((a \land \neg b) \lor c)€

```include:java
gof/interpreter/Example.java
```

### Reference

- https://sourcemaking.com/design_patterns/interpreter