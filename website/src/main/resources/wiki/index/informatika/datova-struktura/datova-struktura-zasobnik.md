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

#### Rozhraní

```include:java
stack/Stack.java
```

#### Pomocí spojového seznamu

Implementace využívající [spojový seznam](wiki/datova-struktura-seznam) má teoreticky neomezenou kapacitu, ale prvky zabírají více paměti, než by mohly (ke každému prvku totiž náleží ukazatel na prvek následující).

```include:java
stack/LinkedStack.java
```

#### Pomocí pole

Implementace využívající [pole](wiki/datova-struktura-pole) má kapacitu omezenou shora velikostí pole.

```include:java
stack/ArrayStack.java
```

### Reference

- http://www.algolist.net/Data_structures/Stack
