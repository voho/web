## Pool (fond)

### Situace

V systému existuje nějaký objekt, jehož vytvoření je náročné (na paměť nebo na čas) a zároveň je vyžadován více klienty na více místech.

### Problém

V řadě situací není žádoucí, aby vzniklo a najednou v paměti existovalo příliš mnoho instancí nějaké třídy (přesná kvantifikace tohoto počtu se liší případ od případu, záleží na velikosti instance a náročnosti jejího vytvoření). Typickým příkladem jsou různá spojení, jejichž navázání trvá až několik sekund a není jich zároveň potřeba tolik.

### Řešení

1. Získat referenci na [tovární třídu](wiki/abstract-factory) pro vytváření nových instancí znovupoužitelných objektů.
1. Deklarovat statickou či dynamickou kolekci znovupoužitelných instancí.
1. Přidat veřejnou metodu pro získání volné instance a pro návrat již nevyužívané instance do fondu.
1. Rozhodnout, jak se bude fond chovat v případě, že už žádné volné instance nejsou k dispozici:
 - vytvořit vždy novou (neomezená kapacita fondu)
 - skončit s výjimkou
 - počkat do doby než někdo jiný některou instanci vrátí

#### UML diagramy

```uml:class
class ReusablePool <<interface>> {
  acquire(): ReusableItem
  release(item: ReusableItem)
}

class ReusableItem <<interface>> {
}

class ReusableItemFactory <<interface>> {
  create(): ReusableItem
}

Client --> ReusablePool
ReusablePool -right-> ReusableItemFactory
Client .right.> "needs" ReusableItem
ReusableItemFactory ..> "creates" ReusableItem
```

### Související vzory

- [Abstract Factory](wiki/abstract-factory) - pro vytváření nových instancí pro fond
- [Factory Method](wiki/factory-method) - jiný způsob vytváření instancí

### Příklad

#### Tovární třída

```java
/**
 * Továrna pro vytváření nových instancí pro fond.
 * @author Vojtěch Hordějčuk
 * @param <T> typ objektu
 */
public interface Factory<T> {
  /**
   * Vytvoří novou instanci objektu.
   * @return nová instance
   */
  T newInstance();
}
```

#### Implementace fondu

```java
/**
 * Implementace návrhového vzoru fond (Pool).
 * @author Vojtěch Hordějčuk
 * @param <T> třída uvnitř fondu
 */
public class Pool<T> {
  /**
   * tovární třída pro naplnění fondu
   */
  private final Factory<T> factory;
  /**
   * seznam volných instancí
   */
  private final List<T> free;
  /**
   * maximální velikost fondu (počet instancí)
   */
  private final int maxInstances;
  /**
   * aktuální velikost fondu (počet použitých instancí)
   */
  private int usedInstances;
  
  /**
   * Vytvoří novou instanci.
   * @param pFactory tovární třída pro naplnění fondu
   * @param pMaxInstances maximální velikost fondu (počet instancí)
   */
  public Pool(final Factory<T> pFactory, final int pMaxInstances) {
    this.factory = pFactory;
    this.free = new LinkedList<T>();
    this.maxInstances = pMaxInstances;
    this.usedInstances = 0;
  }
  
  /**
   * Vrátí volnou instanci (pokud nějaké existuje). Pokud volné instance
   * neexistují a nelze je doplnit, vlákno se zablokuje dokud jiné vlákno
   * instanci nevrátí.
   * @return volná instance
   * @throws InterruptedException výjimka při přerušení čekajícího vlákna
   */
  public T acquireFreeInstance() throws InterruptedException {
    synchronized (this.free) {
      if (this.free.isEmpty()) {
        // nejsou volné žádné instance
        
        if (this.usedInstances < this.maxInstances) {
          // další instance lze vytvořit použitím továrny a vložit do fondu
          
          final T fresh = this.factory.newInstance();
          this.free.add(fresh);
          Pool.log("Fresh instance created: " + fresh);
        }
        else {
          // další instance nelze vytvořit, a tak budeme čekat až nějaké vlákno
          
          while (this.free.isEmpty()) {
            Pool.log("Waiting for free instance...");
            this.free.wait();
          }
        }
      }
      
      final T toBeUsed = this.free.remove(0);
      this.usedInstances++;
      Pool.log("Returning free instance: " + toBeUsed);
      return toBeUsed;
    }
  }
  
  /**
   * Vrátí nepoužívanou instanci zpět do fondu, aby mohla být znovu použita.
   * @param usedNoMore instance pro vrácení do fondu
   */
  public void giveBackInstance(final T usedNoMore) {
    synchronized (this.free) {
      Pool.log("Unused instance given back: " + usedNoMore);
      this.usedInstances--;
      this.free.add(usedNoMore);
      this.free.notify();
    }
  }
  
  /**
   * Pomocná metoda pro logování.
   * @param message zpráva
   */
  private static void log(final String message) {
    System.out.println(Thread.currentThread().getName() + ": " + message);
  }
}
```

#### Test

```java
// velikost fondu
final int poolCapacity = 3;
// počet testovacích vláken
final int threadCount = 10;
// počet cyklů každého vlákna do ukončení
final int maxThreadLoops = 3;

// připravit tovární třídu

final Factory<Object> factory = new Factory<Object>() {
  @Override
  public Object newInstance() {
    return new Object();
  }
};

// přípravit fond

final Pool<Object> pool = new Pool<Object>(factory, poolCapacity);

// spustit testovací vlákna

for (int i = 0; i < threadCount; i++) {
  Runnable runnable = new Runnable() {
    /**
     * číslo aktuálního cyklu
     */
    private int loopNumber = 1;
    
    @Override
    public void run() {
      try {
        while (this.loopNumber++ <= maxThreadLoops) {
          // vzít volnou instanci z fondu
          final Object myOwnInstance = pool.acquireFreeInstance();
          // nějakou chvíli počkat
          Thread.sleep(Math.round(Math.random() * 1000));
          // vrátit instanci zpět do fondu
          pool.giveBackInstance(myOwnInstance);
        }
      }
      catch (final Exception x) {
        // výjimku budeme ignorovat
        x.printStackTrace();
      }
    }
  };
  
  new Thread(runnable, "T" + i).start();
}
```

### Reference

- Rudolf Pecinovský: Návrhové vzory
- http://www.javamex.com/tutorials/wait_notify_how_to.shtml