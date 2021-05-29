## Fronta (queue)

Fronta je lineární datová struktura určená pro ukládání prvků a jejich opětovný výběr ve stejném pořadí, v jakém byly do fronty přidány - nejstarší přidaný prvek je vybrán jako první (**FIFO** = first in first out). S frontami se setkáváme v každodenním životě a vyskytují se všude tam, kde je třeba férově vyřešit rozdílné rychlosti vstupů a výstupů, nebo jak odložit zpracování úloh na později a přitom zachovat jejich pořadí.

Fronta v základní variantě poskytuje tyto funkce:

enqueue(*a*)
: vloží prvek *a* na konec fronty

dequeue()
: vrátí první (nejstarší) prvek ze začátku fronty a odebere jej

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

#### Rozhraní

```include:java
queue/Queue.java
```

#### Implementace pomocí spojového seznamu

Implementace využívající [spojový seznam](wiki/datova-struktura-seznam) má teoreticky neomezenou kapacitu, ale prvky zabírají více paměti, než by mohly (ke každému prvku totiž náleží ukazatel na prvek následující).

```include:java
queue/LinkedQueue.java
```

#### Implementace pomocí pole

Implementace fronty pomocí [pole](wiki/datova-struktura-pole) je zajímavá tím, že se pro dosažení maximálního využití paměti musí ukazatele po přejetí konce pole vrátit zpět na jeho začátek.

```include:java
queue/ArrayQueue.java
```

### Reference

- https://www.cs.bu.edu/teaching/c/queue/linked-list/types.html
- https://www.cs.bu.edu/teaching/c/queue/array/types.html
