## GoF: Singleton (jedináček)

### Situace

V systému má existovat třída, která může mít nejvýše jednu globálně sdílenou instanci a zároveň sama centralizuje k této své instanci přístup.

### Řešení

Správná implementace singletonu silně závisí na jazyku, který použijeme, protože každý z nich nabízí jiné prostředky a konstrukce. Na implementaci máme následující požadavky:

* nesmí být možné vytvářet instance singletonu jinde, než v singletonu samém
* vytváření singletonu musí proběhnout nejvýše jednou, a proto je nutné zajistit vláknovou bezpečnost, aby dvě různá vlákna v jeden okamžik nevytvářela dvě různé instance singletonu
* při získávání již existující instance singletonu se nesmí provádět žádné zbytečné operace
* nemělo by být možné použít reflexi a zničit či zaměnit instanci singletonu během vykonávání programu
* instance singletonu by měla být serializovatelná

```uml:class
class EagerSingleton {
  -{static} instance: EagerSingleton = new EagerSingleton()
  +{static} getInstance(): EagerSingleton
}
class LazySingleton {
  -{static} instance: LazySingleton = null
  +{static} getInstance(): LazySingleton
}
note bottom of LazySingleton
  if (instance == null) then
    instance = new LazySingleton();
  return instance;
end note
```

### Implementace (Java)

V jazyce Java existuje několik způsobů, jak uvedené požadavky zajistit.

#### Singleton (jednoduchý)

Tento postup je velmi jednoduchý, ale zároveň neimplementuje všechny požadavky. Je však vláknově bezpečný a efektivní.

```java
public final class Singleton {
    public static final Singleton INSTANCE = new Singleton();
    
    private Singleton() {
        // ...
    }
}
```

#### Singleton (realizace s použitím dvojité kontroly)

Tento postup umožňuje línou inicializaci za cenu mírně nepřehledné metody pro získání instance. Je vláknově bezpečný.

```java
public final class Singleton {
    private static Singleton INSTANCE = null;
    
    private Singleton() {
        // ...
    }
    
    public static Singleton getInstance() {
        if (INSTANCE == null) {
            synchronized (Singleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Singleton();
                }
            }
        }
        
        return INSTANCE;
    }
}
```

#### Singleton (realizace vnitřní pomocnou třídou)

V této implementaci je instance singletonu vytvořena classloaderem během načítání třídy. Původním autorem techniky je zřejmě [Bill Pugh](http://www.cs.umd.edu/~pugh/).

```java
public final class Singleton {
    private Singleton() {
        // ...
    }
    
    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    private static class SingletonHolder {
        private static Singleton INSTANCE = new Singleton();
    }
}
```

#### Singleton (realizace výčtovým typem)

Tuto techniku doporučuje [Joshua Bloch](https://en.wikipedia.org/wiki/Joshua_Bloch) ve [své přednášce](http://www.youtube.com/watch?v=pi_I7oD_uGI#t=28m50s).
Podle něj se jedná o nejlepší a nejrobustnější implementaci, která implementuje všechny rozumné požadavky, které na singleton můžeme mít.

> This approach is functionally equivalent to the public field approach, except that it is more concise, provides the serialization machinery for free, and provides an ironclad guarantee against multiple instantiation, even in the face of sophisticated serialization or reflection attacks. While this approach has yet to be widely adopted, a single-element enum type is the best way to implement a singleton. *Joshua Bloch*

```java
public enum Singleton {
    INSTANCE;
    
    // ...
}
```

#### Použití

```java
Singleton.getInstance().doSomething();
```

### Reference

- předmět X36ASS na FEL ČVUT
- předmět X36OBP na FEL ČVUT
- http://stackoverflow.com/questions/70689/what-is-an-efficient-way-to-implement-a-singleton-pattern-in-java
- http://www.oodesign.com/singleton-pattern.html
- http://objekty.vse.cz/Objekty/Vzory-Singleton
- http://sourcemaking.com/design_patterns/singleton
