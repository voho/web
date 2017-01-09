## GoF: Prototype (prototyp)

### Situace

V systému se nachází třída, jejíž instance je přijatelnější či efektivnější nějakým způsobem replikovat než vytvářet nové. Důvody mohou být různé. Objekt například může při vytváření provádět zbytečné výpočty, alokovat nadbytečné duplikáty již existujících objektů, apod. 

### Problém

Zbytečné výpočty či instance v paměti jsou vždy něco, čeho je lepší se vyvarovat. Při vytváření nové instance je výhodné maximálně využít již existující objekty za předpokladu, že se nebudou měnit. 

Dalším omezením může být i nutnost znát přesný název třídy při vytváření její nové instance (například operátorem *new*). 

### Rešení

Třída určená ke kopírování se nazývá **prototyp**. Prototyp má vytváření svých kopií plně ve své vlastní režii. Implementuje metodu vracející novou kopii objektu, která přebírá všechny potřebné reference z prototypu. Některé atributy nové kopie mohou být při kopírování pozměněny.

#### UML diagram

```uml:class
class Prototype {
  clone(): Prototype
}

note right of Prototype
  Prototype clone = new Prototype();
  clone.setValue1(this.getValue1());
  clone.setValue2(this.getValue2());
  // ...
  return clone;
end note
```

#### Varianty

Vytvořené kopie prototypu mohou být volné a roztroušené nebo naopak vytvářené a spravované jednou centrální třídou. Tato třída může řídit vytváření a mazání v ní obsažených prototypů.

##### Správce prototypů

```java
public class PrototypeManager {
    private Map<String,Cloneable> cache;

    public PrototypeManager() {
        this.cache = new HashMap<String,Cloneable>();
    }

    public void register(String key, Cloneable f) {
        this.cache.put(key, f);
    }

    public void unregister(String key) {
        this.cache.remove(key);
    }

    public Cloneable clone(String key) {
        if (!this.cache.containsKey(key)) {
            throw new NoSuchElementException();
        }

        return this.cache.get(key).clone();
    }
}
```

#### Příklad

```java
public class Cell {
    private final Gene[] dna;

    public Cell() {
        // vytvoříme buňku s náhodným DNA
        this(Gene.randomDna());
    } 

    public Cell(Gene[] pDna) {
        // vytvoříme buňku se zadaným DNA
        this.dna = pDna;
    }

    public Cell clone() {
        // nejprve vytvoříme identický klon
        final Cell clone = new Cell(this.dna);
        // následně provedeme malou mutaci
        final int randomIndex = (int) (Math.random() * clone.dna.length);
        clone.dna[randomIndex] = Gene.randomGene();
        // nakonec klon vrátíme
        return clone;
    }
}
```

```java
// operátor "new" je použit jen jednou
Cell first = new Cell();
// dále už se pokračuje klonováním
Cell c1 = first.clone();
Cell c2 = first.clone();
Cell c3 = c1.clone();
Cell c4 = c3.clone();
// ...
```

### Reference

- http://sourcemaking.com/design_patterns/prototype
- http://www.oodesign.com/prototype-pattern.html