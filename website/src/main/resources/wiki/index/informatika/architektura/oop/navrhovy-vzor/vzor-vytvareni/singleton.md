## GoF: Singleton (jedináček)

### Situace

V systému má existovat třída, která může mít nejvýše jednu globálně sdílenou instanci a zároveň sama centralizuje k této své instanci přístup.

### Řešení

Teoreticky je možné vytvořit instanci libovolné třídy na libovolném místě. Toto chování však není žádoucí u třídy, u které požadujeme omezenit počet instancí na jednu, centrálně spravovanou.

V třídě samé tedy zavedeme novou soukromou statickou proměnnou, která bude obsahovat jedinou existující a globálně sdílenou instanci dané třídy. Tuto instanci lze buď vytvořit ihned při inicializaci třídy (princip **eager loading**), nebo až v případě potřeby (princip **lazy loading**). Ve druhém případě je třeba dávat pozor na řízení přístupu více vláken. Abychom dále omezili možnost vytvářet instance této třídy, měl by se skrýt její konstruktor.

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

### Implementace

Správná implementace singletonu je závislá na jazyku, který použijeme. 
Je nutné zjistit, jak v daném jazyce korektně implementovat následující požadavky:

* vláknová bezpečnost (aby dvě různá vlákna v jeden okamžik nevytvářela dvě různé instance singletonu)
* efektivita (aby se při získávání již existující instance singletonu neprováděly zbytečné operace)
* volitelně i líná inicializace (aby se singleton vytvořil až v případě potřeby) 
* odolnost vůči zničení či změně existující instance pomocí reflexe
* možnost serializace singletonu

V jazyce Java existuje několik způsobů, jak toto zajistit.

#### Singleton (jednoduchý)

Jedinou nevýhodou tohoto jednoduchého přístupu je, že neumožňuje línou inicializaci. 
To však nemusí vadit.

```java
public final class Singleton {
    public static final Singleton INSTANCE = new Singleton();
    
    private Singleton() {
        // ...
    }
}
```

#### Singleton (realizace s použitím dvojité kontroly)

Tento postup umožňuje línou inicializaci za cenu mírně nepřehledné metody pro získání instance.
Tento přístup je však nutné pro zajištění vláknové bezpečnosti.

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

V této implementaci je instance singletonu vytvořena classloaderem, protože je navázána na vnitřní třídu.
Líná inicializace není zajištěna, ale singleton se inicializuje až při načtení třídy.
Původním autorem techniky je zřejmě [Bill Pugh](http://www.cs.umd.edu/~pugh/).

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