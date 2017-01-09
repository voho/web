## Úloha Producer-Consumer (producent-konzument)

### Situace

Jedno vlákno generuje datové položky (tzv. **producent**), zatímco je jiné vlákno zpracovává (tzv. **konzument**). Společně komunikují pomocí fronty o omezené velikosti, do které producent položky přidává a konzument je z ní odebírá. Dále musí platit následující podmínky:

- konzument musí čekat v případě, že je fronta prázdná (musí počkat, než producent vytvoří a do fronty umístí konzumovatelnou datovou položku)
- producent musí čekat v případě, že je fronta plná (musí počkat, než konzument jednu datovou položku z fronty odebere ke konzumaci a uvolní tak místo pro novou)

### Řešení pomocí monitorů

Řešení lze realizovat pomocí dvou monitorů:

- první monitor hlídá situaci, kdy je fronta prázdná - konzument nemá co konzumovat a musí počkat do doby, než producent vytvoří alespoň jeden element
- druhý monitor hlídá situaci, kdy je fronta plná - producent nemá kam ukládat nově vytvořené elementy a musí počkat do doby, než konzument alespoň jeden element zkonzumuje
- dále je potřeba zajistit, aby frontu využívalo v každý okamžik pouze jedno vlákno (mutex) a každý vlákno mělo stav fronty za všech okolností aktuální

#### Implementace 

##### Monitory

```java
public class Monitors {
    private final Object fullMonitor = new Object();
    private final Object emptyMonitor = new Object();

    public void waitFull() throws InterruptedException {
        synchronized (fullMonitor) {
            fullMonitor.wait();
        }
    }

    public void waitEmpty() throws InterruptedException {
        synchronized (emptyMonitor) {
            emptyMonitor.wait();
        }
    }

    public void notifyFull() {
        synchronized (fullMonitor) {
            fullMonitor.notify();
        }
    }

    public void notifyEmpty() {
        synchronized (emptyMonitor) {
            emptyMonitor.notify();
        }
    }
}
```

##### Buffer

```java
public class Buffer<T> {
    private final int maxQueueSize;
    private final Queue<T> queue;

    public Buffer(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
        this.queue = new ArrayDeque<T>(this.maxQueueSize);
    }

    public void add(T value, Monitors synchronizer) throws InterruptedException {
        while (isFull()) {
            // buffer is full - wait until (at least) one element is removed
            synchronizer.waitFull();
        }

        boolean wasEmpty = isEmpty();

        synchronized (queue) {
            queue.add(value);
        }

        if (wasEmpty) {
            // the first element was added to buffer - notify waiting consumers
            synchronizer.notifyEmpty();
        }
    }

    public T take(Monitors synchronizer) throws InterruptedException {
        while (isEmpty()) {
            // buffer is empty - wait until an element is added
            synchronizer.waitEmpty();
        }

        boolean wasFull = isFull();
        T value = queue.poll();

        if (wasFull) {
            // an element was removed from the buffer - notify waiting producers
            synchronizer.notifyFull();
        }

        return value;
    }

    private boolean isEmpty() {
        synchronized (queue) {
            return queue.size() == 0;
        }
    }

    private boolean isFull() {
        synchronized (queue) {
            return queue.size() == maxQueueSize;
        }
    }
}
```

##### Test

```java
final Buffer<Integer> buffer = new Buffer<Integer>(10);
final Monitors monitors = new Monitors();
final int numberOfElements = 20;

new Thread(new Runnable() {
	@Override
	public void run() {
		for (int i = 0; i < numberOfElements; i++) {
			try {
				System.out.println(buffer.take(monitors));
			} catch (Exception ex) {
				// NOP
			}
		}
	}
}).start();

new Thread(new Runnable() {
	@Override
	public void run() {
		for (int i = 0; i < numberOfElements; i++) {
			try {
				Thread.sleep(200);
				buffer.add(i, monitors);
			} catch (InterruptedException ex) {
				// NOP
			}
		}
	}
}).start();
```

### Řešení pomocí semaforů

Řešení pomocí semaforů vyžaduje dva semafory:

- první semafor představuje počet prázdných míst ve frontě - tento semafor je inicializovaný zpočátku na maximální velikost fronty (na začátku jsou všechna místa ve frontě prázdná)
- druhý semafor představuje počet zaplněných míst ve frontě - tento semafor je inicializovaný na nulu (na začátku nejsou ve frontě žádné elementy)

Jakmile producent vytvoří nějaký element, pokusí se snížit semafor s počtem prázdných míst. Pokud je semafor s počtem prázdných míst nulový, producent se zablokuje a čeká, než konzument semafor vrátí zpátky na kladnou hodnotu. Jakmile má semafor kladnou hodnotu, producent vloží element do fronty a zvýší semafor s počtem zaplněných míst.

Konzument se chová symetricky. Nejprve zkusí snížit semafor s počtem zaplněných míst a pokud je tento semafor nulový, konzument se zablokuje a čeká, než jej producent vrátí na kladnou hodnotu. Jakmile má semafor kladnou hodnotu, konzument vybere z fronty element, zkonzumuje jej a zvýší semafor s počtem prázdných míst.

#### Implementace

##### Semafory

```java
public class Semaphores {
    private final Semaphore fullCount;
    private final Semaphore emptyCount;

    public Semaphores(int maxQueueSize) {
        this.fullCount = new Semaphore(0);
        this.emptyCount = new Semaphore(maxQueueSize);
    }

    public void downEmptyCount() throws InterruptedException {
        emptyCount.acquire();
    }

    public void upEmptyCount() {
        emptyCount.release();
    }

    public void downFullCount() throws InterruptedException {
        fullCount.acquire();
    }

    public void upFullCount() {
        fullCount.release();
    }
}
```

##### Buffer

```java
public class Buffer<T> {
    private final Semaphores semaphores;
    private final Queue<T> queue;

    public Buffer(int maxQueueSize) {
        this.semaphores = new Semaphores(maxQueueSize);
        this.queue = new ArrayDeque<T>(maxQueueSize);
    }

    public void add(T value) throws InterruptedException {
        this.semaphores.downEmptyCount();

        synchronized (this.queue) {
            this.queue.add(value);
        }

        this.semaphores.upFullCount();
    }

    public T take() throws InterruptedException {
        this.semaphores.downFullCount();

        final T item;

        synchronized (this.queue) {
            item = queue.poll();
        }

        this.semaphores.upEmptyCount();
        return item;
    }
}
```

##### Test

```java
final Buffer<Integer> buffer = new Buffer<Integer>(10);
final int numberOfElements = 20;

new Thread(new Runnable() {
	@Override
	public void run() {
		for (int i = 0; i < numberOfElements; i++) {
			try {
				System.out.println(buffer.take());
			} catch (Exception ex) {
				// NOP
			}
		}
	}
}).start();

new Thread(new Runnable() {
	@Override
	public void run() {
		for (int i = 0; i < numberOfElements; i++) {
			try {
				Thread.sleep(200);
				buffer.add(i);
			} catch (InterruptedException ex) {
				// NOP
			}
		}
	}
}).start();
```