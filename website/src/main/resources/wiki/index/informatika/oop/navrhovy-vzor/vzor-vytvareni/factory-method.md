## GoF: Tovární metoda (Factory Method)

Jako tovární metoda (factory method) se označuje metoda, jejíž účel je vytvořit novou instanci nějakého objektu a vytvořenou instanci vrátit. Druh objektu i jeho počáteční vlastnosti jsou dané přijatými parametry, případně i stavem objektu, který tovární metodu poskytuje. 

### Instanční tovární metoda

Instanční tovární metoda se používá tam, kde je vytvářený objekt nějakým způsobem odvozený od aktuální instance třídy, která tovární metodu poskytuje. Tyto metody se používají hlavně u tzv. **konstantních tříd** (immutable classes), jejichž stav nelze po vytvoření měnit a pro změnu některého z parametrů je třeba vytvořit instanci novou. Ačkoliv tato vlastnost nevypadá na první pohled výhodně, umožňuje zvýšit podíl čistých funkcí v programu a tak do objektového programovacího jazyka převzít některé výhody funkcionálního programování.

#### Příklady

```java
public class Rectangle {
  private final int a;
  private final int b;

  public Rectangle(int a, int b) {
    this.a = a;
    this.b = b;
  }

  // první dvě tovární metody vytváří novou instanci se změnou jednoho rozměru

  public Rectangle setA(int newA) {
    return new Rectangle(newA, this.b);
  }

  public Rectangle setB(int newB) {
    return new Rectangle(this.a, newB);
  }

  // další tovární metoda vytváří obdélník s násobenými rozměry

  public Rectangle grow(int factor) {
    return new Rectangle(this.a * factor, this.b * factor);
  }
}
```

### Statická tovární metoda

Tovární metoda může být statická, pokud všechny informace nutné pro vytvoření požadovaného objektu dostane jako parametry. Takové tovární metody lze sdružovat do logických celků a pro každý takový celek vytvořit tzv. **tovární třídu**. Taková třída zpravidla obsahuje jen spolu související statické tovární metody.

#### Příklady

```java
public static Rectangle createRectangle(int a, int b) {
  return new Rectangle(a, b);
}
```

```java
public static Rectangle createSquare(int a) {
  return new Rectangle(a, a);
}
```

### Reference

- http://www.oodesign.com/factory-method-pattern.html
