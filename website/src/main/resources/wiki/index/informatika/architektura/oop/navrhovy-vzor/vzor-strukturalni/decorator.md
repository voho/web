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

```include:java
gof/decorator/Printer.java
```

##### Jednoduchá tiskárna

```include:java
gof/decorator/SimplePrinter.java
```

##### Řádky zalamující tiskárna

```include:java
gof/decorator/WrappingPrinter.java
```

##### Tiskárna řetězců

```include:java
gof/decorator/StringPrinter.java
```

##### Příklad použití

```include:java
gof/decorator/Example.java
```

### Související vzory

- [Proxy](wiki/proxy) - dekorátor je speciálním druhem proxy, který přidává funkce
- [Adaptér](wiki/adapter) - adaptér je speciálním druhem dekorátoru, který pouze přizpůsobuje rozhraní dekorovaného objektu

### Reference

- http://javarevisited.blogspot.ie/2015/01/adapter-vs-decorator-vs-facade-vs-proxy-pattern-java.html
- http://stackoverflow.com/questions/350404/how-do-the-proxy-decorator-adapter-and-bridge-patterns-differ
- http://stackoverflow.com/questions/3489131/what-is-the-difference-between-the-facade-proxy-adapter-and-decorator-design