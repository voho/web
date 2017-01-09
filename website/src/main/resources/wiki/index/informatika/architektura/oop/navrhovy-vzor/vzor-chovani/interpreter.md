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

```java
public interface LogicalExpression {
    boolean evaluate(Context context);
}
```

```java
public class LogicalNotOperator implements LogicalExpression {
    private final LogicalExpression operand;

    public LogicalNotOperator(final LogicalExpression operand) {
        this.operand = operand;
    }

    @Override
    public boolean evaluate(final Context context) {
        return !operand.evaluate(context);
    }

    @Override
    public String toString() {
        return String.format("NOT %s", operand);
    }
}
```

```java
public class LogicalAndOperator implements LogicalExpression {
    private final LogicalExpression leftOperand;
    private final LogicalExpression rightOperand;

    public LogicalAndOperator(final LogicalExpression leftOperand, final LogicalExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public boolean evaluate(final Context context) {
        return leftOperand.evaluate(context) && rightOperand.evaluate(context);
    }

    @Override
    public String toString() {
        return String.format("(%s AND %s)", leftOperand, rightOperand);
    }
}
```

```java
public class LogicalOrOperator implements LogicalExpression {
    private final LogicalExpression leftOperand;
    private final LogicalExpression rightOperand;

    public LogicalOrOperator(final LogicalExpression leftOperand, final LogicalExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }


    @Override
    public boolean evaluate(final Context context) {
        return leftOperand.evaluate(context) || rightOperand.evaluate(context);
    }

    @Override
    public String toString() {
        return String.format("(%s OR %s)", leftOperand, rightOperand);
    }
}
```

Nakonec to bude proměnná, která bude svou hodnotu hledat kontextu. To zajistí, že ji bude možné měnit bez nutnosti měnit strukturu výrazu.

```java
public class LogicalVariable implements LogicalExpression {
    private final String name;

    public LogicalVariable(final String name) {
        this.name = name;
    }

    @Override
    public boolean evaluate(final Context context) {
        return context.getVariableValue(this.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
```

##### Kontext

```java
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Context {
    private final Map<String, Boolean> variableValues;

    public Context() {
        this.variableValues = new HashMap<>();
    }

    public void setVariableToFalse(final String variableName) {
        this.variableValues.put(variableName, false);
    }

    public void setVariableToTrue(final String variableName) {
        this.variableValues.put(variableName, true);
    }

    public boolean getVariableValue(final String variableName) {
        return Optional.ofNullable(this.variableValues.get(variableName)).orElseThrow(NoSuchElementException::new);
    }
}
```

###### Použití

Takto se návrhový vzor použije: nejprve vytvoříme strukturu výrazu v paměti a kontext, který posléze použijeme k vyhodnocení hodnoty výrazu. 

Ukázkový výraz: €((a \land \neg b) \lor c)€

```java
LogicalExpression root = new LogicalOrOperator(
        new LogicalAndOperator(
                new LogicalVariable("a"),
                new LogicalNotOperator(
                        new LogicalVariable("b")
                )
        ),
        new LogicalVariable("c")
);

Context context = new Context();
context.setVariableToFalse("a");
context.setVariableToTrue("b");
context.setVariableToFalse("c");

// = FALSE

boolean result = root.evaluate(context);
```

### Reference

- https://sourcemaking.com/design_patterns/interpreter