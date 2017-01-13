## Zásobník (stack)

Zásobník je lineární datová struktura určená pro ukládání prvků a jejich opětovný výběr v opačném pořadí - poslední přidaný prvek je vybrán jako první (**LIFO** = last in first out). Typickým zásobníkem je například hromada talířů nebo knih poskládaných na sebe.

Zásobník je jedna z nejdůležitějších datových struktur v informatice. Používá se pro rekurzi, volání podprogramů, výpočty matematických výrazů, překlad formálních jazyků, zpracovávání procesorových instrukcí... setkáte se s ním tedy téměř na všech možných úrovních abstrakce - od fyzické implementace na úrovni hardware až po vysoce abstraktní implementace v aplikačních [programovacích jazycích](wiki/jazyk).

Zásobník v základní variantě poskytuje tyto funkce:

push(*a*)
: vloží prvek *a* na vrchol zásobníku

pop()
: vrátí vrchol zásobníku a vrchol odebere

peek()
: vrátí vrchol zásobníku (beze změny)

```dot:digraph
splines=ortho
table [shape=record,label="<n>_ _ _|<h>hello|world|this|is|...|stack"]
head [shape=none]
head->table:h
push->table:n
table:h->pop
{rank=same;push;pop;}
```

### Implementace

#### Pomocí spojového seznamu

Implementace využívající [spojový seznam](wiki/datova-struktura-seznam) má teoreticky neomezenou kapacitu, ale prvky zabírají více paměti, než by mohly (ke každému prvku totiž náleží ukazatel na prvek následující).

```java
/**
 * Stack implemented using linked list.
 *
 * @author Vojtěch Hordějčuk
 */
public class MyLinkedStack<DATA> {
    private static class Node<DATA> {
        private DATA innerValue;
        private Node<DATA> next;
    }

    private Node<DATA> top;

    /**
     * Adds an element on the top of the stack.
     *
     * @param data data to push
     */
    public void push(final DATA data) {
        final Node<DATA> newTop = new Node<DATA>();
        newTop.innerValue = data;
        newTop.next = top;
        top = newTop;
    }

    /**
     * Pops an element from the stack top.
     * Throws exception if no element is present.
     *
     * @return popped element (former stack top)
     */
    public DATA pop() {
        if (top != null) {
            final DATA topValue = top.innerValue;
            top = top.next;
            return topValue;
        } else {
            throw new IllegalStateException("Stack underflow.");
        }
    }

    /**
     * Checks if the stack is empty.
     *
     * @return TRUE if the stack is empty, FALSE otherwise
     */
    public boolean isEmpty() {
        return top == null;
    }
}
```

#### Pomocí pole

```java
/**
 * Array based stack with maximum capacity.
 *
 * @author Vojtěch Hordějčuk
 */
public class MyArrayStack<DATA> {
    private final DATA[] storage;
    private int top;

    /**
     * Creates a new instance.
     *
     * @param capacity stack capacity (maximum number of elements to store)
     */
    public MyArrayStack(final int capacity) {
        this.storage = (DATA[]) new Object[capacity];
        this.top = -1;
    }

    /**
     * Adds an element on the top of the stack.
     *
     * @param data data to push
     */
    public void push(final DATA data) {
        if (top == storage.length - 1) {
            throw new IllegalStateException("Stack overflow.");
        }

        storage[++top] = data;
    }

    /**
     * Pops an element from the stack top.
     * Throws exception if no element is present.
     *
     * @return popped element (former stack top)
     */
    public DATA pop() {
        if (top == -1) {
            throw new IllegalStateException("Stack underflow.");
        }

        return storage[top--];
    }

    /**
     * Checks if the stack is empty.
     *
     * @return TRUE if the stack is empty, FALSE otherwise
     */
    public boolean isEmpty() {
        return top == -1;
    }
}
```

### Reference

- http://www.algolist.net/Data_structures/Stack