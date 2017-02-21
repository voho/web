## Skip list

> Because these data structures are linked lists with extra pointers that skip over intermediate nodes, I named them skip lists. *William Pugh*

Skip list je dynamická datová struktura, kterou v roce 1990 představil **William Pugh** v článku [Skip Lists: A Probabilistic Alternative to Balanced Trees](ftp://ftp.cs.umd.edu/pub/skipLists/skiplists.pdf). Má podobné uplatnění jako [binární vyhledávací stromy](wiki/datova-struktura-binarni-strom), ale je jednodušší na implementaci a v některých situacích dokonce rychlejší. 

Tato datová struktura je založena na seřazeném [seznamu](wiki/datova-struktura-seznam), který je možné procházet po několika různých úrovních. Nejnižší úroveň obsahuje všechny prvky. Druhá úroveň jejich podmnožinu, třetí úroveň podmnožinu této podmnožiny, a tak dále, až do nejvyšší úrovně, ve které se nachází pouze relativně malý počet prvků. Čím méně prvků v dané úrovni je, tím větší je rozdíl mezi klíči dvou sousedních prvků. Při vyhledávání tedy lze začít velkými skoky a pak se čím dál menšími skoky přibližovat k požadovanému klíči.

```dot:digraph
dpi=70
splines=polyline
node [margin="0,0.1"]
nodesep=0
rankdir=LR
head [shape=record,label="<n>head|<i0>L1|<i1>L2|<i2>L3 (1.)"]
foot [shape=record,label="<n>foot|<i0>null|<i1>null|<i2>null"]
t1 [shape=record,label="<n>1|<i0>|<i1>"]
t2 [shape=record,label="<n>3|<i0>"]
t3 [shape=record,label="<n>4|<i0>|<i1>|<i2>"]
t4 [shape=record,label="<n>7|<i0>|<i1>"]
t5 [shape=record,label="<n>10|<i0>"]
t6 [shape=record,label="<n>33|<i0>|<i1>"]
head:i0->t1:i0
t1:i0->t2:i0
t2:i0->t3:i0
t3:i0->t4:i0
t4:i0->t5:i0
t5:i0->t6:i0
t6:i0->foot:i0
head:i1->t1:i1
t1:i1->t3:i1
t3:i1->t4:i1
t4:i1->t6:i1
t6:i1->foot:i1
head:i2->t3:i2
t3:i2->foot:i2
head [fillcolor=beige]
foot [fillcolor=beige]
```

Příklad vyhledávní hodnoty *7* (červeně jsou zvýrazněny navštívené prvky a ukazatele, číslo v závorkách udává pořadí, ve kterém byl daný prvek navštíven):

```dot:digraph
dpi=70
splines=polyline
node [margin="0,0.1"]
nodesep=0
rankdir=LR
head [shape=record,label="<n>head|<i0>L1|<i1>L2|<i2>L3 (1)"]
foot [shape=record,label="<n>foot|<i0>null|<i1>null|<i2>null"]
t1 [shape=record,label="<n>1|<i0>|<i1>"]
t2 [shape=record,label="<n>3|<i0>"]
t3 [shape=record,label="<n>4|<i0>(8)|<i1>(5)|<i2>(3)"]
t4 [shape=record,label="<n>7|<i0>(10)|<i1>(7)"]
t5 [shape=record,label="<n>10|<i0>"]
t6 [shape=record,label="<n>33|<i0>|<i1>"]
head:i0->t1:i0
t1:i0->t2:i0
t2:i0->t3:i0
t3:i0->t4:i0 [color=red,label="(9)"]
t4:i0->t5:i0
t5:i0->t6:i0
t6:i0->foot:i0
head:i1->t1:i1
t1:i1->t3:i1
t3:i1->t4:i1 [color=red,label="(6)"]
t4:i1->t6:i1
t6:i1->foot:i1
head:i2->t3:i2 [color=red,label="(2)"]
t3:i2->foot:i2 [color=red,label="(4)"]
head [fillcolor=beige]
foot [fillcolor=beige]
head [color=red]
t3 [color=red]
t4 [color=red]
```

### Operace

#### Vyhledání hodnoty

Vyhledávání určitého klíče začíná na nejvyšší úrovni. Ukazatel začne procházet seřazené prvky v zadané úrovni a zastaví se až před klíčem, který je větší nebo roven hledanému klíči, nebo pokud dojde na konec seznamu. Pokračuje pak o úroveň níže, dokud se nedostane až do nejnižší první úrovně. 

Po zastavení ukazatele v první úrovni je nutné ověřit klíč prvku, který stojí před ukazatelem. Pokud jeho klíč odpovídá hledanému klíči, požadovaný prvek byl nalezen. Jakýkoliv jiný případ znamená, že klíč se v seznamu nenachází.

#### Vkládání hodnoty

Při vkládání hodnoty ke klíči se nejprve vyhledá místo, kam se nový prvek vloží. K tomu se použije podobný algoritmus, jako k vyhledávání, jen si pro každou úroveň uchováme pozici, ve které došlo k přeskočení na nižší úroveň a tuto informaci využijeme při vkládání nového prvku v případě, že se tam nenachází. Pokud na tomto místě již prvek se stejným klíčem existuje, je pouze přepsána jeho hodnota. Pokud je třeba prvek vytvořit, nejvyšší úroveň nového prvku je vybrána náhodně tak, aby rozdělení respektovalo následující princip: čím vyšší úroveň, tím nižší pravděpodbnost jejího výběru. Tento přístup zajistí, že na počátku hledání v nejvyšší úrovni nebude nutné procházet tolik prvků, abychom se dostali poblíž požadovaného klíče. 

Pro zjištění náhodné úrovně lze využít například tento jednoduchý algoritmus:

```java
public int getRandomLevel(double fallProbability, int maxNumberOfLevels) {
    int level = 0;

    while (Math.random() < fallProbability && level < maxNumberOfLevels - 1) {
        level++;
    }

    return level;
}
```

Jako pravděpodobnost "pádu" do vyšší úrovně (*fallProbability*) se používá nejčastěji hodnota 0.5, počet úrovní (*maxNumberOfLevels*) je pak typicky odhadován jako logaritmus očekávaného množství prvků se základem 2.

#### Odstranění hodnoty

Hodnotu lze odstranit na základě jejího klíče. Opět se použije podobný vyhledávací algoritmus jako pro vkládání - pole s nejbližšími předchůdci požadovaného klíče se bude opět hodit.


### Implementace

- https://github.com/voho/examples/tree/master/skiplist

#### Rozhraní skip listu

```java
/**
 * Skip list interface.
 */
public interface SkipList<K extends Comparable<? super K>, V> {
    /**
     * Gets a value by its key.
     *
     * @param key key to search for
     * @return value found (or nothing)
     */
    Optional<V> get(K key);

    /**
     * Checks if there is a mapping for the specified key.
     *
     * @param key key to search for
     * @return TRUE if there is a mapping for that key, FALSE otherwise
     */
    default boolean contains(final K key) {
        return get(key).isPresent();
    }

    /**
     * Saves a value under the given key.
     * Could a value already exists for the given key, it would be overwritten.
     *
     * @param key key
     * @param value value
     */
    void insert(K key, V value);

    /**
     * Removes the value stored for the given key.
     *
     * @param key key
     * @return TRUE if the key was found and value removed, FALSE if nothing was found
     */
    boolean delete(K key);
}
```

#### Konkrétní implementace rozhraní

```java
public class DefaultSkipList<K extends Comparable<? super K>, V> implements SkipList<K, V> {
    /**
     * probability of skipping to a higher level
     */
    private static final double P_LEVEL_SKIP = 0.5;
    /**
     * header element (always the same, never empty)
     */
    private final Element header;
    /**
     * maximum number of levels allowed
     */
    private final int maxNumberOfLevels;
    /**
     * top level currently in the list
     */
    private int topLevel;

    public DefaultSkipList(final int maxNumberOfLevels) {
        this.maxNumberOfLevels = maxNumberOfLevels;
        this.topLevel = 0;
        this.header = new Element(null, null);
    }

    // READING
    // =======

    @Override
    public Optional<V> get(final K keyToSearchFor) {
        final Element x = lookup(keyToSearchFor, null);

        if (x != null && x.key.equals(keyToSearchFor)) {
            return Optional.of(x.value);
        }

        return Optional.empty();
    }

    // WRITING
    // =======

    @Override
    public void insert(final K desiredKey, final V desiredValue) {
        final Element[] update = createArrayOfElements();
        final Element x = lookup(desiredKey, update);

        if (x != null && x.key.equals(desiredKey)) {
            x.value = desiredValue;
            return;
        }

        final int randomItemLevel = getRandomLevel();
        topLevel = Math.max(topLevel, randomItemLevel);

        final Element newElement = new Element(desiredKey, desiredValue);

        for (int i = 0; i <= randomItemLevel; i++) {
            final Element insertAfter = update[i];
            newElement.forward[i] = insertAfter.forward[i];
            insertAfter.forward[i] = newElement;
        }
    }

    @Override
    public boolean delete(final K desiredKey) {
        final Element[] update = createArrayOfElements();
        final Element x = lookup(desiredKey, update);

        if (x == null || !x.key.equals(desiredKey)) {
            // not present - deletion not necessary

            return false;
        }

        // present - delete node by joining the list

        for (int i = 0; i <= topLevel; i++) {
            if (update[i].forward[i] == x) {
                // skip the node being removed
                update[i].forward[i] = x.forward[i];
            } else {
                // no need to continue further
                break;
            }
        }

        // lower the list level if necessary

        while (topLevel > 0 && header.forward[topLevel] == null) {
            topLevel--;
        }

        return true;
    }

    // HELPER METHODS
    // ==============

    /**
     * Important helper method for performing key lookup.
     * Besides the lookup it can also build an array of closest predecessors for each level.
     * If the target array is given as argument instead of NULL, it will result to this:
     * <ul>
     * <li>levels outside of range - initialized with header</li>
     * <li>levels in range - the closest element preceding the key looked up is stored</li>
     * </ul>
     * This array of predecessors can be used to simplify other operations.
     *
     * @param key key to lookup
     * @param closestPredecessorsTarget array of predecessors to update (must be of sufficient length)
     * @return best candidate found or NULL
     */
    private Element lookup(final K key, final Element[] closestPredecessorsTarget) {
        if (closestPredecessorsTarget != null) {
            // initialize predecessors with header

            for (int i = 0; i < maxNumberOfLevels; i++) {
                closestPredecessorsTarget[i] = header;
            }
        }

        Element x = header;

        for (int i = topLevel; i >= 0; i--) {
            // move forward through level as long as the keys are lower

            while (x.forward[i] != null && x.forward[i].key.compareTo(key) < 0) {
                x = x.forward[i];
            }

            if (closestPredecessorsTarget != null) {
                // store closest element on each level

                closestPredecessorsTarget[i] = x;
            }
        }

        // only the lowest-level successor can be the candidate for sure

        return x.forward[0];
    }

    private int getRandomLevel() {
        int level = 0;

        while (Math.random() < P_LEVEL_SKIP && level < maxNumberOfLevels - 1) {
            level++;
        }

        return level;
    }

    @SuppressWarnings("unchecked")
    private Element[] createArrayOfElements() {
        return (Element[]) Array.newInstance(Element.class, maxNumberOfLevels);
    }

    /**
     * Generic element.
     */
    private class Element {
        private final Element[] forward;
        private final K key;
        private V value;

        private Element(final K key, final V value) {
            this.key = key;
            this.value = value;
            this.forward = createArrayOfElements();
        }
    }
}
```

### Reference

- [ftp://ftp.cs.umd.edu/pub/skipLists/skiplists.pdf](ftp://ftp.cs.umd.edu/pub/skipLists/skiplists.pdf)
- http://www.cs.tau.ac.il/~shanir/nir-pubs-web/Papers/OPODIS2006-BA.pdf
- http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/8u40-b25/java/util/concurrent/ConcurrentSkipListMap.java/
- http://ticki.github.io/blog/skip-lists-done-right/
