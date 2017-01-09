## GoF: Decorator (dekorátor)

Návrhový vzor dekorátor se používá pro dynamické přidání či změnu funkčnosti nějakého objektu bez nutnosti jeho změny či použití dědičnosti. Jako dekorátor se označuje třída, která obdrží nějakou instanci, kterou obohatí o novou funkcionalitu.

### Řešení

```uml:class
class Component <<interface>> {
  operation()
}
class ConcreteComponent {
  operation()
}
class DecoratedComponent {
  - delegate: Component
  operation()
  additionalOperation1()
  additionalOperation2()
  additionalOperation3()
}
note right
    uses the delegation to execute operations
end note
ConcreteComponent .up.|> Component
DecoratedComponent .up.|> Component
```

#### Příklad

##### Rozhraní tiskárny

```java
public interface Printer {
    void printCharacter(char c);

    void goToNextLine();
}
```

##### Jednoduchá tiskárna

```java
public class SimplePrinter implements Printer {
    @Override
    public void printCharacter(final char c) {
        System.out.print(c);
    }

    @Override
    public void goToNextLine() {
        System.out.println();
    }
}
```

##### Řádky zalamující tiskárna

```java
public class WrappingPrinter implements Printer {
    private final Printer printer;
    private final int maxCharsPerLine;
    private int charsPerCurrentLine;

    public WrappingPrinter(final Printer printer, final int maxCharsPerLine) {
        this.printer = printer;
        this.maxCharsPerLine = maxCharsPerLine;
        this.charsPerCurrentLine = 0;
    }

    @Override
    public void printCharacter(final char c) {
        if (charsPerCurrentLine == maxCharsPerLine) {
            goToNextLine();
        }

        printer.printCharacter(c);
        charsPerCurrentLine++;
    }

    @Override
    public void goToNextLine() {
        printer.goToNextLine();
        charsPerCurrentLine = 0;
    }
}
```

##### Tiskárna řetězců

```java
public class StringPrinter implements Printer {
    private final Printer printer;

    public StringPrinter(final Printer printer) {
        this.printer = printer;
    }

    @Override
    public void printCharacter(final char c) {
        printer.printCharacter(c);
    }

    @Override
    public void goToNextLine() {
        printer.goToNextLine();
    }

    public void printString(final String s) {
        for (int i = 0; i < s.length(); i++) {
            printCharacter(s.charAt(i));
        }

        goToNextLine();
    }
}
```

### Související vzory

- [Proxy](wiki/proxy) - dekorátor je speciálním druhem proxy, který přidává funkce
- [Adaptér](wiki/adapter) - adaptér je speciálním druhem dekorátoru, který pouze přizpůsobuje rozhraní dekorovaného objektu

### Reference

- http://javarevisited.blogspot.ie/2015/01/adapter-vs-decorator-vs-facade-vs-proxy-pattern-java.html
- http://stackoverflow.com/questions/350404/how-do-the-proxy-decorator-adapter-and-bridge-patterns-differ
- http://stackoverflow.com/questions/3489131/what-is-the-difference-between-the-facade-proxy-adapter-and-decorator-design