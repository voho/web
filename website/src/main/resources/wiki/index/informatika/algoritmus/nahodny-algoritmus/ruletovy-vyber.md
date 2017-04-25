## Ruletový výběr

Ruletový výběr je [algoritmus](wiki/algoritmus), který provádí výběr náhodnéhu prvku s [pravděpodobností](wiki/pravdepodobnost) odpovídající jeho váze, což je libovolné nezáporné číslo. 
Čím vyšší váha, tím vyšší pravděpodobnost, že bude daný prvek vybrán.

Obecně je tedy vstupem algoritmu [množina](wiki/mnozina) prvků a [zobrazení](wiki/zobrazeni), které pro každý prvek určí jeho váhu.
Váhy nemusí být celočíselné - jediný rozdíl je v tom, že celočíselné váhy se lépe ukazují na příkladu.
Hodnoty váhy mohou být teoreticky libovolně veliké, protože se porovnávají pouze samy mezi sebou, avšak implementace mohou nějaká omezení klást - například rozsahem datových typů.

Jak ruletový výběr funguje? Představme si, že máme prvky (*A*, *B*, *C*, *D*, *E*, *F*) s celočíselnými vahami (4, 1, 3, 8, 1, 1).
Z těchto údajů můžeme vytvořit následující ruletu, ve které každý prvek zopakujeme právě tolikrát, kolik udává jeho váha:

```dot:graph
node[shape=record]
A[label="A|A|A|A|B|C|C|C|D|D|D|D|D|D|D|D|E|F"]
```

Na pořadí prvků vůbec nezáleží, protože předpokládáme, že každé políčko rulety může být vybráno se stejnou pravděpodobností.
Proto lze stejných výsledků dosáhnout i s touto ruletou, ve které prvky náhodně přeházíme:

```dot:graph
node[shape=record]
A[label="B|A|D|D|A|D|C|D|D|A|E|D|C|C|F|D|A|D"]
```

Nyní už stačí jen vybrat náhodné políčko a po provedení dostatečně velkého množství výběrů uvidíme, že se relativní četnosti vybraných prvků blíží jejich vahám.

Jednoduchá implementace v [jazyce Java](wiki/java):

```java
public class RouletteWheelSelection {
    public static <T> T randomElement(final T[] elements, final double[] weights) {
        if (elements.length != weights.length) {
            throw new IllegalArgumentException("Both arrays must have the same length.");
        }
        
        return elements[randomWeightedInteger(weights)];
    }
    
    /**
     * Returns a random integer in range (0, weights.length-1), both inclusive.
     * Every integer n is selected with a probability that corresponds to its weight.
     * The weight of integer n is stored in as weights[n].
     * The weights can be an arbitrary numbers.
     * @param weights array of weights for each number (index = number)
     * @return random integer
     */
    public static int randomWeightedInteger(final double[] weights) {
        double rouletteSize = 0;

        for (int i = 0; i < weights.length; i++) {
            rouletteSize += weights[i];
        }

        final double randomRoulettePosition = Math.random() * rouletteSize;
        double roulettePosition = 0;

        for (int i = 0; i < weights.length; i++) {
            roulettePosition += weights[i];

            if (roulettePosition >= randomRoulettePosition) {
                return i;
            }
        }

        // if the array is empty
        // (other scenarios cannot end up here)
        return -1;
    }
}
```

### Reference

- https://www.youtube.com/watch?v=9JzFcGdpT8E