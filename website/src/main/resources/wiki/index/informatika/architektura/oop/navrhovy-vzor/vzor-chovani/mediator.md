## GoF: Mediator (prostředník)

Mediator je návrhový vzor pro návrh struktury objektů, které mezi sebou komunikují. Zjednodušeně řečeno: místo toho, aby spolu objekty komunikovaly přímo, využijí k tomu prostředníka.

### Problém

Jako příklad může posloužit letecký provoz. Ve vzduchu se nachází tři letadla. Každá posádka může kontaktovat další dvě, což dohromady dává šest možných spojení. Jejich množství by ale rychle rostlo, jak by přibývala další letadla, až by se komunikace ve vzduchu stala nezvladatelnou (teoreticky existuje až €n(n-1)€ možných spojení, kde €n€ je počet letadel).

```uml:class
Plane1 -down- Plane2
Plane1 -down- Plane3
Plane2 -right- Plane3
```

### Řešení

Vytvoříme novou entitu, se kterou budou komunikovat všechna letadla. Nazveme ji řídící věž. Každé letadlo bude mít spojení pouze s řídící věží, která bude posléze podle potřeby sama kontaktovat jiná letadla. Po zavedení kontrolní věže sice vzroste celkový počet objektů, ale počet spojení se zmenší na €2n€.

```uml:class
Plane1 -up- Control
Plane2 -up- Control
Plane3 -up- Control
```

Obecně tedy vytváříme objekt zvaný **prostředník**, který zapouzdřuje veškerou komunikaci mezi souvisejícími objekty. Objekt, který chce s těmito objekty komunikovat, se obrátí na prostředníka. Prostředník posléze zvolí vhodnou akci, například přepošle zprávu jinému objektu nebo skupině objektů. Ten, kdo s objekty skrze prostředníka komunikuje, nemusí mít žádnou znalost o rozhraní objektů, které prostředník využívá.

### Související vzory

- [Facade](wiki/facade) - Podobnost spočívá v tom, že oba vzory představují určitou abstrakci nad svými závislostmi, ale Facade jednak nepřidává žádnou novou funkcionalitu a závislosti sama o ní neví. Na druhou stranu, instanci prostředníka může vlastnit kdokoliv, kdo chce s daným module komunikovat, tedy i všechny závislosti.
- [Observer](wiki/observer) - Observer se zaměřuje na univerzální rozhraní pro dynamické přidávání a odebírání závislostí a jeho primárním účelem je jednosměrná komunikace s těmito závislostmi při výskytu určité události. Informace o ní se šíří jednoduše od pozorovaného směrem k pozorovateli.

### Příklad

```uml:class
Program -right-> LoggerMediator
LoggerMediator -down-> StdOutLogger
LoggerMediator -down-> StdErrLogger
```

#### Loggery

```java
public class StdOutLogger {
    public void print(final String message) {
        System.out.println(message);
    }
}
```

```java
public class StdErrLogger {
    public void print(final String message) {
        System.err.println(message);
    }
}
```

#### Prostředník

```java
public class LoggerMediator {
    private final StdOutLogger stdOutLogger;
    private final StdErrLogger stdErrLogger;

    public LoggerMediator(final StdOutLogger stdOutLogger, final StdErrLogger stdErrLogger) {
        this.stdOutLogger = stdOutLogger;
        this.stdErrLogger = stdErrLogger;
    }

    public void logInfo(final String message) {
        stdOutLogger.print("INFO: " + message);
    }

    public void logError(final String message) {
        stdOutLogger.print("ERROR: " + message);
        stdErrLogger.print(message);
    }
}
```

#### Použití

```java
final LoggerMediator mediator = new LoggerMediator(
        new StdOutLogger(),
        new StdErrLogger()
);

mediator.logInfo("Application started.");
mediator.logError("Application error.");
mediator.logInfo("Application terminated.");
```

### Reference

- https://sourcemaking.com/design_patterns/mediator