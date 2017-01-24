## Úloha Readers-Writers (zápis-čtení)

### Situace

Několik vláken přistupuje ke sdílené paměti. Některé vlákna z paměti pouze čtou (čtenáři), některá do ní zapisují (zapisovatelé). Je požadováno, aby během zápisu nebylo umožněno současné čtení žádnému jinému vláknu než tomu, které provádí změny.

Typicky tato situace nastává v případě, že je v paměti umístěna nějaká sdílená datová struktura a během aktualizace může být nekonzistentní. Většinou není žádoucí, aby se nekonzistentní data šířila v programu dál, proto je potřeba strukturu uzamknout pro zápis, provést všechny potřebné změny z jednoho vlákna a posléze strukturu odemknout - jak pro čtení, tak i pro případný další zápis.

### Řešení pomocí semaforů

Triviální řešení spočívá v tom, že se okolo veškerého kódu pracujícího se sdílenou datovou strukturou umístí *mutex*, tedy zámek, do kterého může vstoupit pouze jedno vlákno současně. To sice řeší problém, ale velmi neefektivně - čtenáři by se vzájemně zbytečně blokovali. Dále by v případě neustálého čtení docházelo k situaci, kdy by se žádný zapisovatel do struktury nedostal (hladovění vlákna - tzv. **starving**) a podobně i v případě neustálého zápisu by nebylo strukturu možné číst. Proto musí existovat jiné, elegantnější řešení.

#### Implementace

##### Semafory

```java
public class Semaphores {
    private final Semaphore noWaiting = new Semaphore(1);
    private final Semaphore noAccessing = new Semaphore(1);

    public void downNoWaiting() throws InterruptedException {
        noWaiting.acquire();
    }

    public void downNoAccessing() throws InterruptedException {
        noAccessing.acquire();
    }

    public void upNoWaiting() {
        noWaiting.release();
    }

    public void upNoAccessing() {
        noAccessing.release();
    }
}
```

##### Sdílená datová struktura

```java
public class SharedData<T> {
    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
```

##### Reader

```java
public class Reader<T> {
    private boolean wereNoReaders;
    private boolean areNoReaders;
    private static int numReaders;
    private static final Object numReadersMonitor = new Object();

    public T read(SharedData<T> data, Semaphores synchronizer) throws InterruptedException {
        synchronizer.downNoWaiting();

        synchronized (numReadersMonitor) {
            wereNoReaders = (numReaders == 0);
            numReaders++;
        }

        if (wereNoReaders) {
            synchronizer.downNoAccessing();
        }

        synchronizer.upNoWaiting();

        // read shared data
        Thread.sleep(500);
        T value = data.getValue();

        synchronized (numReadersMonitor) {
            numReaders--;
            areNoReaders = (numReaders == 0);
        }

        if (areNoReaders) {
            synchronizer.upNoAccessing();
        }

        return value;
    }
}
```

##### Writer

```java
public class Writer<T> {
    public void write(T value, SharedData<T> data, Semaphores synchronizer) throws InterruptedException {
        synchronizer.downNoWaiting();
        synchronizer.downNoAccessing();
        synchronizer.upNoWaiting();

        // write data exclusively
        Thread.sleep(3000);
        data.setValue(value);

        synchronizer.upNoAccessing();
    }
}
```

##### Test

```java
final SharedData<Integer> data = new SharedData<Integer>();
final Semaphores synchronizer = new Semaphores();
final Reader<Integer> reader = new Reader<Integer>();
final Writer<Integer> writer = new Writer<Integer>();

final List<Thread> threads = new LinkedList<Thread>();

for (int i = 0; i < 5; i++) {
	threads.add(new Thread(new Runnable() {
		@Override
		public void run() {
			while (true) {
				try {
					Integer value = reader.read(data, synchronizer);
					System.out.println(System.currentTimeMillis() + ": read: " + value);
				} catch (InterruptedException ex) {
					// NOP
				}
			}
		}
	}));
}

for (int i = 0; i < 3; i++) {
	threads.add(new Thread(new Runnable() {
		@Override
		public void run() {
			for (int i = 0; i < 3; i++) {
				try {
					int newValue = (int) Math.round(Math.random() * 10000.0);
					System.out.println(System.currentTimeMillis() + ": write: " + newValue);
					writer.write(newValue, data, synchronizer);
				} catch (InterruptedException ex) {
					// NOP
				}
			}
		}
	}));
}

for (Thread thread : threads) {
	thread.start();
}

for (Thread thread : threads) {
	thread.join();
}
```