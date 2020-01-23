## Atomické hodnoty

Jazyk Java obsahuje speciální kontejnery, které lze použít jako vláknově bezpečné proměnné pro určitý typ hodnot. Hodnotu kontejneru lze bezpečně číst a přepisovat z více vláken, přičemž pro aktualizaci jsou k dispozici i další užitečné metody, jako je **přečti a přepiš** (getAndSet), **porovnej a přepiš** (compareAndSet), **inkrementace/dekrementace** (getAndIncrement(), getAndDecrement()), a další.

Kontejnery jsou definovány pro tři primitivní typy a referenční typ:

- *javadoc:java.util.concurrent.atomic.AtomicBoolean* - pro primitivní hodnotu typu *boolean*
- *javadoc:java.util.concurrent.atomic.AtomicInteger* - pro primitivní hodnotu typu *int*
- *javadoc:java.util.concurrent.atomic.AtomicLong* - pro primitivní hodnotu typu *long*
- *javadoc:java.util.concurrent.atomic.AtomicReference* - pro libovolný typ

Pro některé z nich existují i varianty pro [pole](wiki/datova-struktura-pole):

- *javadoc:java.util.concurrent.atomic.AtomicIntegerArray* - pro pole primitivních hodnot typu *int*
- *javadoc:java.util.concurrent.atomic.AtomicLongArray* - pro pole primitivních hodnot typu *long*
- *javadoc:java.util.concurrent.atomic.AtomicReferenceArray* - pro pole libovolného typu

```include:java
atomic/AtomicCounterTest.java
```