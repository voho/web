## Práce s vlákny

Vlákna a výrazové prostředky pro jejich spolupráci jsou v jazyce Java přímo integrované a kombinací různých postupů lze implementovat velmi širokou paletu vícevláknových synchronizačních schémat. Mechanismus práce s vlákny v jazyce Java využívá synchronizační primitivum nazvané **monitor**. Monitor si lze představit jako přepážku v bance, ke které může najednou přijít jen jeden klient. Ostatní lidé, kteří chtějí přepážku navštívit, musí čekat ve frontě. V jazyce Java má každý objekt svůj vlastní monitor, proto se pojmy "objekt" a "monitor" tak často zaměňují.

Základní jazykové prostředky pro práci s vlákny:

- konstrukce *synchronized*
- metoda *wait*
- metoda *notify*
- metoda *notifyAll*

Základní třídy a rozhraní pro práci s vlákny:

- třída *Thread*
- rozhraní *Runnable*

### Konstrukce "synchronized"

Konstrukcí "synchronized" lze synchronizovat blok, instanční metodu nebo statickou metodu. Jednotlivé případy budou popsány dále.

#### Synchronizované instanční metody

Nejjednodušší synchronizace na úrovni metod zajistí, že pro jednu instancí objektu může do všech jeho synchronizovaných metod vstoupit právě jedno vlákno. Ostatní (nesynchronizované) metody nijak ovlivněné nejsou.

##### Jednoduchý čítač bez synchronizace

Nechť je deklarována jednoduchá třída představující čítač se dvěma metodami *increment* a *get*. Metoda *increment* zvýší hodnotu čítače a metoda *get* vrátí jeho aktuální hodnotu. 

```java
public class Counter {
  private int counter = 0;
  
  public void increment() {
    this.counter++;
  }
  
  public int get() {
    return this.counter;
  }
}
```

Je-li tato třída použita ve vícevláknovém prostředí, může dojít (mimo jiné) k situaci, kdy jedno vlákno zvyšuje hodnotu čítače (je tedy v metodě *increment*) a druhé vlákno jeho hodnotu ve stejném okamžiku čte (je tedy v metodě *get*). V tomto případě není jasné, která hodnota čítače je správná - jestli hodnota původní nebo hodnota inkrementovaná - a program kvůli tomu může dokonce dávat rozdílné výstupy pro stejné vstupy v závislosti na náhodě. To většinou není požadovaný stav. Říká se, že tato třída není vláknově bezpečná (non thread-safe).

Aby k této a podobným komplikacím nedocházelo, je nutné mít vlákna pod kontrolou a na úrovni zdrojového kódu specifikovat, za jakých podmínek může vlákno do nějaké jeho části vstoupit. Na místě je i upozornění, že v jednovláknových aplikacích nemá synchronizace žádný význam a nijak neovlivňuje provádění programu.

##### Jednoduchý čítač se synchronizací

Jedinou změnou v kódu je doplnění klíčového slova *synchronized* k oběma metodám. Jestliže teď některé vlákno vstoupí do jedné z nich, ostatní vlákna nemohou vstoupit ani do metody *increment*, ani do metody *get*, případně do jiných synchronizovaných metod téže instance. Tím se čítač stal vláknově bezpečným (thread-safe).

```java
public class Counter {
  private int counter = 0;
  
  public synchronized void increment() {
    this.counter++;
  }
  
  public synchronized int get() {
    return this.counter;
  }
}
```

Synchronizace na úrovni metod je jen zjednodušený zápis obecnější konstrukce *synchronized*, která bude popsána dále. Osobně doporučuji používat vždy jen tu delší variantu s uvedením monitoru. Jednak se může synchronizace v hlavičce metody při dědění "vytratit" a jednak to svádí k tomu řešit všechny synchronizační problémy právě takto na úrovni celé třídy. Uvedení monitoru vždy vyjadřuje lepší porozumění danému synchronizačnímu problému.

```java
// zjednodušený zápis

public synchronized void test() {
  // ...
}

// odpovídající plný zápis

public void test() {
  synchronized (this) {
    // ...
  }
}
```

#### Synchronizované statické metody

Obdobně jako u instančních metod, je i u statických metod možnost zápisu klíčového slova *synchronized* přímo do hlavičky metody. Narozdíl od instanční metody ale není ve statickém kontextu k dispozici ukazatel na aktuální objekt *this*, a tak synchronizace probíhá na třídě, ve které je statická metoda deklarována. Tím pádem zámek sdílejí všechny instance dané třídy.

```java
class Example {
  // zjednodušený zápis

  public static synchronized void test() {
    // ...
  }
  
  // odpovídající plný zápis

  public static void test() {
    synchronized (Example.class) {
      // ...
    }
  }
}
```

#### Synchronizované bloky

Synchronizovat lze nejen metody, ale i jednotlivé menší bloky kódu. V tomto případě se v konstrukci *synchronized* musí uvést tzv. **monitor**, což je instance objektu s následujícím významem: **do všech synchronizovaných bloků se stejným monitorem může vstoupit nanejvýš jedno vlákno současně**. V případě synchronizovaných metod je jako monitor použita instance *this* a je synchronizován celý blok představující tělo metody. 

Naprosto stejný význam jako výše uvedený příklad má tedy i následující konstrukce.

```java
public class Counter {
  private int counter = 0;
  private final Object counterMonitor = new Object();
  
  public void increment() {
    synchronized (this.counterMonitor) {
      this.counter++;
    }
  }
  
  public int get() {
    synchronized (this.counterMonitor) {
      return this.counter;
    }
  }
}
```

Pokud v uvedeném příkladu nějaké vlákno vstoupí například do metody *increment*, nejprve ověří, zda je monitor *this* volný. Pokud je, zamkne tento monitor pro sebe a začne vykonávat tělo metody. Ostatní vlákna se při pokusu o zavolání metody *increment* nebo *get* zablokují a umístí do fronty, než původní vlákno volání metody dokončí a odemkne monitor *this*. Jedno z čekajících vláken se pak probudí a celý proces se opakuje podobným způsobem.

Tato konstrukce má tu výhodu, že je z ní mnohem lépe vidět podle jakého monitoru se synchronizace provádí a použitím vhodného monitoru ji lze provádět i mnohem jemněji (např. podle nějakého zámku skupin souvisejících proměnných). Někdy totiž není nutné zamknout celý objekt kvůli jediné operaci, což může snižovat efektivitu programu.

```java
public class DoubleCounter {
  private final Something a = new Something();
  private final Something b = new Something();
  
  public void modify(int aVal, int bVal) {
    synchronized (a) {
      a.modify(aVal);
    }
    
    synchronized (b) {
      b.modify(bVal);
    }
  }
  
  // ... getA() = synchronized(a)
  // ... getB() = synchronized(b)
}
```

### Metody wait, notify, notifyAll

Zajímavé prostředky pro synchronizaci vláken obsahuje i základní třída *Object*, která obsahuje monitor. Je to metoda *wait* a její protipóly *notify* a *notifyAll*. Jak již název napovídá, metoda *wait* souvisí s čekáním na upozornění, které je odesláno metodami *notify* a *notifyAll*. Chování těchto metod je ale nutné popsat podrobněji. 

- *wait* - aktuální vlákno se zablokuje a odemkne monitor
- *notify* - jedno "náhodně" vybrané vlákno zablokované na monitoru se aktivuje
- *notifyAll* - všechna vlákna zablokovaná na monitoru se aktivují

Všechny tyto metody lze volat pouze na monitorech (objektech), které aktuální vlákno vlastní, jinak dojde k chybě (*IllegalMonitorStateException*).

Jak již bylo řečeno, všechny tyto metody jsou definovány v základní třídě *Object*, a proto je lze volat na každém objektu v jazyce Java. Každý objekt tedy může vystupovat v roli monitoru. Kvůli přehlednosti je dobré množství monitorů co nejvíce omezit (avšak s ohledem na řešenou situaci a výkon programu) a synchronizaci provádět pouze tam, kde je to nutné.

```java
// objekt "lock" bude vystupovat v roli monitoru
final Object lock = new Object();

synchronized (lock) {
  // vlákno 1, které vstoupí do tohoto bloku, uzamkne monitor
  // (ostatní vlákna tedy do tohoto bloku vstoupit již nemohou)
  
  // po zavolání metody "wait" se vlákno 1 vzdá zámku a zablokuje se
  lock.wait();
  
  // nyní do bloku mohou vstupovat i jiná vlákna, protože je monitor odemčený
  // jakmile některé z nich zavolá metodu "notify" na monitoru, vlákno 1 se uvolní
}
```

### Třída Thread

Třída *Thread* představuje vlákno. První myšlenkou při deklaraci nového vlákna je často vytvoření dalšího potomka třídy *Thread*. Tento postup je však nutné zvážit vzhledem k dostupnosti rozhraní *Runnable*. To předepisuje pouze metodu *run* a nemá žádného předka, zatímco třídy *Thread* obsahuje velké množství dalších metody, jejichž dostupnost nemusí být žádoucí - kdokoliv totiž bude mít k dispozici instanci takové třídy, může její vlákno libovolně spouštět, přerušovat, blokovat, atd. Pokud to tedy je možné a sémanticky přípustné, doporučuje se pro deklaraci vláken využívat rozhraní *Runnable*.

Některé důležité metody vlákna:

- *start* - spustí vlákno
- *interrupt* - přeruší vlákno (viz *InterruptedException*)
- *join* - počkat na ukončení daného vlákna
- *sleep* - vlákno uspí na zadaný počet milisekund

#### Vytvoření vlákna

```java
// tělo vlákna

final Runnable threadBody = new Runnable() {
  @Override
  public void run() {
    // kód prováděný vláknem
  }
};
    
// příprava pojmenovaného vlákna

final Thread thread = new Thread(threadBody, "MyFirstThread");
  
// spuštění vlákna

thread.start();
```

#### Životní cyklus vlákna

```uml:activity
New --> Runnable : " start()"
Runnable -right-> Running
Running -down-> Blocked : " wait(), suspend()"
Blocked -up-> Running: " notify(), resume()"
Running -right-> Terminated : " run() method ends"
```

### Fork-Join pool

Speciální typ poolu je vhodný pro řešení problémů, které se mohou dále rozpadat na různé podúlohy a konečný výsledek je sestaven až na konci, a to rekurzivně z mnoha dílčích výsledků.

Nejprve definujme úlohu. Cílem je sečíst číslice v seznamu čísel.

```java
private static class RecursiveSumTask extends RecursiveTask<Long> {
        private final List<Integer> input;

        RecursiveSumTask(final List<Integer> input) {
            this.input = input;
        }

        @Override
        protected Long compute() {
            if (input.size() > 10) {
                // create sub-tasks
                final List<Integer> leftSubList = this.input.subList(0, input.size() / 2);
                final List<Integer> rightSubList = this.input.subList(input.size() / 2, input.size());
                final RecursiveSumTask leftHalf = new RecursiveSumTask(leftSubList);
                final RecursiveSumTask rightHalf = new RecursiveSumTask(rightSubList);

                // execute sub-tasks asynchronously
                LOG.info("Executing left half: {}", leftSubList);
                leftHalf.fork();
                LOG.info("Executing right half: {}", rightSubList);
                rightHalf.fork();

                // join and aggregate partial results
                LOG.info("Aggregating partial results together.");
                return leftHalf.join() + rightHalf.join();
            } else {
                // actual atomic computation (for lists whose length is below a threshold)
                final long sum = input.stream().mapToLong(Long::valueOf).sum();
                LOG.info("Sum of {} is {}.", input, sum);
                return sum;
            }
        }
    }
```

A takhle se to například používá:

```java
public static Long solve(final List<Integer> input) {
    // create fork join pool with the parallelism of 10 threads
    final ForkJoinPool forkJoinPool = new ForkJoinPool(10);
    // invoke the main task
    final long result = forkJoinPool.invoke(new RecursiveSumTask(input));
    // start shutdown
    forkJoinPool.shutdown();
    return result;
}
```

### Atomické hodnoty

Pro každý typ atomické hodnoty i pro reference existuje odpovídající atomická třída. Například pro *Integer* je to *AtomicInteger*, pro *boolean* je to *AtomicBoolean*, a podobně. Pro referenci je to *AtomicReference*. Tyto třídy mají mnoho metod pro zápis i pro čtení a jsou implementovány tak, aby byly vláknově bezpečné. Nejběžnější metodou pro čtení je *get()* a pro zápis *set()*.

```java
AtomicInteger i = new AtomicInteger(42);
i.set(100);
int result = i.get();
```

```java
AtomicReference<URL> r = new AtomicReference<URL>();
r.set(new URL("http://google.com"));
URL result = r.get();
```

### ThreadLocal

Třída *ThreadLocal* představuje jakousi mapu hodnot, ve které je klíčem aktuální vlákno. Každé vlákno tedy čte a zapisuje jen tu "svou" hodnotu a ostatní neovlivňuje. Přístup k hodnotě je vláknově bezpečný. Třída *ThreadLocal* obsahuje tři důležité metody: *get*, *set* a *remove*. Metoda *get* vrátí hodnotu pro dané vlákno, metoda *set* ji změní a metoda *remove* znovu inicializuje úložiště pro dané vlákno výsledkem volání metody *initialValue*, kterou je možné přetížit.

```java
private static ThreadLocal<String> data = new ThreadLocal<String>() {
    @Override
    protected String initialValue() {
        return "HelloWorld";
    }
};
```

Proměnné typu *ThreadLocal* jsou ve třídě zpravidla deklarovány jako statické konstanty (*private static final*).

### Reference

- http://docs.oracle.com/javase/tutorial/essential/concurrency/index.html
- http://www.javamex.com/tutorials/wait_notify_how_to.shtml
- http://www.artima.com/insidejvm/ed2/threadsynch.html
