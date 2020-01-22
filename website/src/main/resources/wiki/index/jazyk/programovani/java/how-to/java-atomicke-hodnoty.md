## Atomické hodnoty

Atomickou hodnotou zde označuje vláknově bezpečný kontejner pro určitý typ hodnot.
Pro každý typ atomické hodnoty a pro referenční typ existuje odpovídající atomická třída.
Pro *Integer* je to *AtomicInteger*, pro *boolean* je to *AtomicBoolean*, a podobně. 
Pro referenční typ (libovolný objekt) je to *AtomicReference*. 

Nejběžnější metodou pro čtení je *get()* a pro zápis *set()*.

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
