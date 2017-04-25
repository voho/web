## Plovoucí náhodný výběr

Výběr jednoho náhodného prvku z pole je docela jednoduchý. 
Vygenerujeme náhodné číslo v rozmezí 0 až *n-1*, kde *n* je délka pole, a vrátíme prvek na dané pozici.
Pokud takových prvků chceme více, proceduru opakujeme, případně si pamatujeme prvky, které již byly vybrány, aby nebyly vybrány znovu.

Jak to ale uděláme, pokud je počet a výčet prvků předem neznámý nebo příliš velký na to, aby se vešel do paměti?
Lze prvky vybírat takovým způsobem, který zajistí, že každý prvek má stejnou šanci se do výběru dostat?

### Výběr jednoho prvku

Můžeme použít následující postup: 

1. Jakmile přečteme první prvek (€i=1€), uložíme ho do dočasné proměnné.
1. Každý další *i*-tý prvek (2, 3, 4...) uložíme do dočasné proměnné s pravděpodobností €1/i€. 
1. Po přečtení celého toku vrátíme prvek aktuálně uložený v dočasné proměnné.

Jak a proč tato technika funguje? 
Správnou funkci této metody lze dokázat indukcí.
!TODO!

Implementace této techniky v [jazyce Java](wiki/java):

```java
public class ReservoirSampling {
    public static <T> Optional<T> reservoirSamplingSingleElement(final Iterator<T> stream) {
        long i = 1;
        Optional<T> result = Optional.empty();

        while (stream.hasNext()) {
            final T nextElement = stream.next();
            final double pReplace = 1.0 / i;
            final double pRandom = Math.random();
            final boolean replaceResult = pRandom < pReplace;

            if (replaceResult) {
                result = Optional.of(nextElement);
            }

            i++;
        }

        return result;
    }
}
```

### Výběr k prvků

!TODO!

### Reference

- https://gregable.com/2007/10/reservoir-sampling.html
- http://www.cs.umd.edu/~samir/498/vitter.pdf
- https://xlinux.nist.gov/dads/HTML/reservoirSampling.html