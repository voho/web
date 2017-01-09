## Fronta (queue)

Fronta je lineární datová struktura určená pro ukládání prvků a jejich opětovný výběr ve stejném pořadí, v jakém byly do fronty přidány - nejstarší přidaný prvek je vybrán jako první (**FIFO** = first in first out). S frontami se setkáváme v každodenním životě a vyskytují se všude tam, kde je třeba férově vyřešit rozdílné rychlosti vstupů a výstupů, nebo jak odložit zpracování úloh na později a přitom zachovat jejich pořadí.

Fronta v základní variantě poskytuje tyto funkce:

- *enqueue(a)* = vloží prvek *a* na konec fronty
- *dequeue()* = vrátí první (nejstarší) prvek ze začátku fronty a odebere jej

```dot:digraph
splines=ortho
table [shape=record,label="<n>_ _ _|<h>hello|world|this|is|...|<t>queue"]
head [shape=none]
tail [shape=none]
head->table:h
tail->table:t
add->table:n
table:t->take
{rank=same;add;take;}
```

### Implementace

#### Pomocí spojového seznamu

Implementace využívající [spojový seznam](wiki/datova-struktura-seznam) má teoreticky neomezenou kapacitu, ale prvky zabírají více paměti, než by mohly (ke každému prvku totiž náleží ukazatel na prvek následující).

```java
/**
 * Queue implemented using linked list.
 *
 * @author Vojtěch Hordějčuk
 */
public class MyLinkedQueue<DATA> {
    private static class Node<DATA> {
        private DATA innerValue;
        private Node<DATA> next;
    }

    private Node<DATA> first;
    private Node<DATA> last;

    /**
     * Enqueues a value by placing it at the end of the queue.
     *
     * @param value value to be added
     */
    public void enqueue(final DATA value) {
        final Node<DATA> newLast = new Node<DATA>();
        newLast.innerValue = value;

        if (first == null) {
            // first element
            first = newLast;
            last = newLast;
        } else {
            last.next = newLast;
            last = newLast;
        }
    }

    /**
     * Dequeues (retrieves and removes) the oldest value in the queue.
     * Throws exception if the queue is empty.
     *
     * @return the oldest value in the queue
     */
    public DATA dequeue() {
        if (first == null) {
            throw new IllegalStateException("Queue is empty.");
        }

        final DATA valueToReturn = first.innerValue;

        if (first == last) {
            // last element
            first = null;
            last = null;
        } else {
            first = first.next;
        }
        
        return valueToReturn;
    }

    /**
     * Checks if the queue is empty.
     *
     * @return TRUE if the queue is empty, FALSE otherwise
     */
    public boolean isEmpty() {
        return first == null;
    }
}
```

#### Pomocí pole

Implementace fronty pomocí [pole](wiki/datova-struktura-pole) je zajímavá tím, že se pro dosažení maximálního využití paměti musí ukazatele po přejetí konce pole vrátit zpět na jeho začátek.

```java
/**
 * Queue implementation using array.
 *
 * @author Vojtěch Hordějčuk
 */
public class MyArrayQueue<DATA> {
    private final DATA[] storage;
    private int top;
    private int bottom;

    /**
     * Creates a new instance.
     *
     * @param capacity queue capacity
     */
    public MyArrayQueue(final int capacity) {
        this.storage = (DATA[]) new Object[capacity];
        this.top = -1;
        this.bottom = -1;
    }

    /**
     * Enqueues a value by placing it at the end of the queue.
     *
     * @param value value to be added
     */
    public void enqueue(final DATA value) {
        if (top < 0) {
            // first element
            top = 0;
            bottom = 0;
            storage[0] = value;
        } else {
            final int bottomToBe = (bottom + 1) % storage.length;

            if (bottomToBe == top) {
                throw new IllegalStateException("Queue is full.");
            }

            bottom = bottomToBe;
            storage[bottom] = value;
        }
    }

    /**
     * Dequeues (retrieves and removes) the oldest value in the queue.
     * Throws exception if the queue is empty.
     *
     * @return the oldest value in the queue
     */
    public DATA dequeue() {
        if (top < 0) {
            throw new IllegalStateException("Queue is empty.");
        } else if (top == bottom) {
            // last element
            final DATA valueTuReturn = storage[top];
            storage[top] = null;
            top = -1;
            bottom = -1;
            return valueTuReturn;
        } else {
            final DATA valueTuReturn = storage[top];
            storage[top] = null;
            top = (top + 1) % storage.length;
            return valueTuReturn;
        }
    }

    /**
     * Checks if the queue is empty.
     *
     * @return TRUE if the queue is empty, FALSE otherwise
     */
    public boolean isEmpty() {
        return top == bottom;
    }
}
```

### Reference

- https://www.cs.bu.edu/teaching/c/queue/linked-list/types.html
- https://www.cs.bu.edu/teaching/c/queue/array/types.html