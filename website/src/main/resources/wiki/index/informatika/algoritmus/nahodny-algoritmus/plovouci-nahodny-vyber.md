## Plovoucí náhodný výběr

Výběr jednoho náhodného prvku z pole je docela jednoduchý. 
Vygenerujeme náhodné číslo v rozmezí 0 až *n-1*, kde *n* je délka pole, a vrátíme prvek na dané pozici.
Pokud takových prvků chceme více, proceduru opakujeme, případně si pamatujeme prvky, které již byly vybrány, aby nebyly vybrány znovu.

Jak to ale uděláme, pokud je výčet prvků předem neznámý nebo příliš velký na to, aby se vešel do paměti?
Lze prvky vybírat takovým způsobem, který zajistí, že každý prvek má stejnou šanci se do výběru dostat?
Řešení nabízí tzv. plovoucí náhodný výběr (reservoir sampling).

### Výběr několika prvků

Pokud chceme vybrat *s* náhodných prvků z předem neznámého výčtu prvků, můžeme postupovat následovně:

1. Inicializujeme pole €R€ o velikosti €s€.
1. Prvních €s€ přijatých prvků postupně uložíme do pole €R€.
1. Pro každý další přijatý prvek na pozici €k = s+1 až n€:
    1. S pravděpodobností €s/k€ prvek přijmeme a nahradíme jím náhodně vybraný prvek v poli €R€.
1. Předchozí bod se opakuje, dokud nejsou přijaty všechny prvky.

Jak a proč tato technika funguje? 

Algoritmus vždy vybírá prvek *k* s pravdpěpodobností €1/k€. Pro prvek €k+1€ to tedy bude €1/(k+1)€.
Prvek €k+1€ může být vybrán v případě, že předchozí prvek €k€ byl přeskočen (€p=1/k€) a následující prvky ho nepřeskočí (€p=1-(1/(k+1))€).
Součinem těchto pravděpodobností dostáváme €p=1/k \cdot (1-(1/(k+1)))= 1/k \cdot (k/(k+1)) = 1/(k+1) €, což odopovídá očekávané hodnotě €1/n€.

!TODO!

Implementace této techniky v [jazyce Java](wiki/java):

```java
public class ReservoirSampling {
    /**
     * Randomly select k items from a stream of items of unknown length.
     * @param stream stream of elements
     * @param s reservoir size (number of elements)
     * @param <T> element type
     * @return list of s random elements from the stream
     */
    public static <T> List<T> reservoirSampling(final Iterator<T> stream, final int s) {
        final List<T> R = new ArrayList<>(s);

        int k = 1;

        // fill R with the first s elements

        while (stream.hasNext() && k <= s) {
            final T nextElement = stream.next();
            R.add(nextElement);
            k++;
        }

        // for each element, accept it with probability s/k
        // if accepted, then replace a randomly selected element in R with the new element

        while (stream.hasNext()) {
            final T nextElement = stream.next();

            final double pOfReplacement = (double) s / (double) k;
            final double randomNumber = ThreadLocalRandom.current().nextDouble();

            if (randomNumber < pOfReplacement) {
                final int randomIndex = ThreadLocalRandom.current().nextInt(s);
                R.set(randomIndex, nextElement);
            }

            k++;
        }

        return R;
    }
}
```

### Výběr jednoho prvku

Pokud potřebujeme vybrat jen jeden prvek, jedná se o speciální případ výše uvedeného algoritmu pro €k=1€.
Pro tento případ lze algoritmus trochu zjednodušit.

```java
public class ReservoirSampling {
    /**
     * Randomly select one item from a stream of items of unknown length.
     * @param stream stream of elements
     * @param <T> element type
     * @return one random element from the stream
     */
    public static <T> Optional<T> reservoirSampling(final Iterator<T> stream) {
        Optional<T> R = Optional.empty();

        int k = 1;

        // for each element, accept it with probability 1/k
        // if accepted, then replace R with the new element

        while (stream.hasNext()) {
            final T nextElement = stream.next();

            final double pOfReplacement = 1.0 / (double) k;
            final double randomNumber = ThreadLocalRandom.current().nextDouble();

            if (randomNumber < pOfReplacement) {
                R = Optional.of(nextElement);
            }

            k++;
        }

        return R;
    }
}
```

### Reference

- https://gregable.com/2007/10/reservoir-sampling.html
- http://www.cs.umd.edu/~samir/498/vitter.pdf
- https://xlinux.nist.gov/dads/HTML/reservoirSampling.html