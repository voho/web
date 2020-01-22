## ThreadLocal

Třída *ThreadLocal* nám umožňuje každému vláknu poskytnou samostatnou instanci nějakého objektu.
Představuje jakousi mapu hodnot, ve které je klíčem aktuální vlákno.

Každé vlákno má přístup jen ke "své" vlastní hodnotě a ostatní neovlivňuje. 

Třída *ThreadLocal* obsahuje tři důležité metody: *get*, *set* a *remove*:

* Metoda *get* vrátí hodnotu pro dané vlákno.
* Metoda *set* hodnotu pro dané vlákno změní
* Metoda *remove* přepíše hodnotu pro dané vlákno výsledkem volání metody *initialValue*.

Proměnné typu *ThreadLocal* jsou ve třídě zpravidla deklarovány jako statické konstanty (*private static final*).

```include:java
threadlocal/ThreadLocalExample.java
```
