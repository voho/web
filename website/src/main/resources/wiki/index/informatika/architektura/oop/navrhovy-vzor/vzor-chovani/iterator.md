## GoF: Iterator (iterátor)

### Situace

Existuje uspořádaná množina prvků, kterou chceme systematicky procházet. Nezajímá nás, jakou strukturu tato množina má, nebo jak přesně je uložena v paměti. Důležité pro nás je pouze správné pořadí prvků při jejím procházení. Procházet lze například [seznam](wiki/datova-struktura-seznam), [pole](wiki/datova-struktura-pole), klíče nebo hodnoty [hashovací tabulky](wiki/datova-struktura-hash-tabulka), nebo uzly či hrany [grafu](wiki/datova-struktura-graf).

Mohou existovat různé druhy procházení - například prosté dopředné (forward), zpětné (backward) a náhodné (random). Můžeme také požadovat, aby bylo možné průchod do určité míry ovládat. Například určit, o kolik elementů se chceme přesouvat, změnit směr procházení nebo možnost vrátit se zpět na začátek.

Vytvoříme tedy třídu, která bude zapouzdřovat tuto logiku procházení prvků v určitém pořadí a navenek nám bude poskytovat pouze další prvek v pořadí a informaci o tom, zda je takový prvek k dispozici, abychom mohli procházení ve správný čas ukončit.

Je zvykem iterátory implementovat s minimálním vnitřním stavem. Procházená datová struktura se tedy nikdy nekopíruje do iterátoru, ten pouze systematicky prochází její prvky, k čemuž využívá její vnější rozhraní. 

Iterátor by nikdy neměl měnit procházenou datovou strukturu. Tak je možné jednu množinu nezávisle procházet i více iterátory najednou.

### Řešení

```uml:class
class List<T> <<interface>> {
  getElementAtIndex(index: int): T
  getSize(): int
}

class Iterator<T> <<interface>> {
  getNext(): T
  next(): boolean
}

Client --> Iterator
ListIterator .up.|> Iterator
ListIterator -down-> List
MapIterator .up.|> Iterator
ArrayIterator .up.|> Iterator
```

Podrobněji si popíšeme nejjednodušší iterátor, tedy prostý dopředný. Tento iterátor stojí vždy "před" nějakým prvkem a na požádání se posune a zároveň "přejetý" prvek vrátí. Takto vypadá průchod iterátoru seznamem:

| seznam s vyznačenou pozicí iterátoru *(P)* | next | hasNext
|---
| *(P)* 1. 2. 3. | 1. | true
| 1. *(P)* 2. 3. | 2. | true
| 1. 2. *(P)* 3. | 3. | true
| 1. 2. 3. *(P)* | - | false

### Implementace

#### Rozhraní seznamu

Než začneme implementovat iterátor, musíme definovat rozhraní, nad kterým budeme iterovat. Pro tento příklad to bude jednoduchý lineární [seznam](wiki/datova-struktura-seznam) s konečnou velikostí, který umožňuje adresovat své prvky celočíselným indexem.

```java
public interface List<T> {
    T getElementAtIndex(int index);

    int getSize();
}
```

#### Rozhraní iterátoru

Každý iterátor vrací prvky a informuje o stavu procházení, aby jeho uživatel věděl, zda jsou dostupné další prvky. Pro dopředné procházení tedy specifikujeme tyto metody:

```java
public interface SimpleIterator<T> {
    boolean hasNext();

    T next();
}
```

#### Implementace pro pole

```java
public class ForwardArrayIterator<T> implements SimpleIterator<T> {
    private final T[] array;
    private int index;

    public ForwardArrayIterator(final T[] array) {
        this.array = array;
        index = -1;
    }

    @Override
    public boolean hasNext() {
        // check we stand before the last element
        return index < array.length - 1;
    }

    @Override
    public T next() {
        // move to the next element
        index++;
        // check bounds (if assertions are enabled)
        assert index <= array.length - 1;
        return array[index];
    }
}
```

#### Použití

```java
final Integer[] array = {1,2,3};
final SimpleIterator<Integer> iterator = new ForwardArrayIterator<Integer>(array);

while (iterator.hasNext()) {
    System.out.println(iterator.next());
}
```