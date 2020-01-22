## Zámky v jazyku Java

!TODO!

### Lock

```java
ReentrantLock lock = new ReentrantLock();
int count = 0;

void increment() {
    lock.lock();
    try {
        count++;
    } finally {
        lock.unlock();
    }
}
```

### ReadWriteLock  (zámek pro čtení a zápis)

```java
// TODO
```

### StampedLock (zámek s časovým razítkem)

```java
// TODO
```

### Semaphor (semafor)

Semafor je užitečný v případě, když potřebujeme omezit počet vláken vstupující do určité sekce.

Semafor se vytvoří s určitým pevně daným počtem povolenek.
Když vlákno požádá semafor o jednu či více povolenek pomocí metody *acquire()*, semafor vlákno uspí, než jich zajistí dostatečné množství.
Jakmile vlákno svou práci dokončí, musí opět uvolnit své povolenky (nebo jejich část) pomocí metody *release()*.

```include:java
locks/SemaphoreExample.java
```

### CountDownLatch

```java
// TODO
```
