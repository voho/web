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

#### Singleton

```java
/**
 * Třída, která má globálně nejvýše jednu instanci.
 *
 * @author Vojtěch Hordějčuk
 */
public class Singleton
{
  /**
   * jedinečná globální instance třídy
   */
  private static Singleton instance = null;

  /**
   * Vrátí jedinečnou globální instanci této třídy. Pokud instance neexistuje,
   * bude vytvořena. Aby byla metoda bezpečná, je nutné přidat kvalifikátor
   * "synchronized".
   *
   * @return jedinečná globální instance třídy
   */
  public synchronized static Singleton getInstance()
  {
    if (Singleton.instance == null)
    {
      // globální instance neexistuje, je třeba ji vytvořit

      Singleton.instance = new Singleton();
    }

    return Singleton.instance;
  }

  /**
   * Konstruktor nesmí být veřejný, aby jej nebylo možné volat, a tak obcházet
   * výchozí mechanismus.
   */
  private Singleton()
  {
    // ...
  }
}
```

#### Test

```java
public static void main(String[] args)
{
  // získat globální instanci třídy a vykonat akci

  Singleton.getInstance().doSomething();
}
```

### Reference

- předmět X36ASS na FEL ČVUT
- předmět X36OBP na FEL ČVUT
- http://www.oodesign.com/singleton-pattern.html
- http://objekty.vse.cz/Objekty/Vzory-Singleton
- http://sourcemaking.com/design_patterns/singleton