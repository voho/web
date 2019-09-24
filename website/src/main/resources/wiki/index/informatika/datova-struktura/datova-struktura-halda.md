## Halda (heap)

Halda je speciální druh [binárního stromu](wiki/datova-struktura-binarni-strom), která se používá primárně pro efektivní nalezení minimálního (či maximálního) prvku v konstantním čase. 
Proto je to častá implementace prioritních front.

Aby byl nějaký binární strom haldou, musí každý jeho uzel splňovat tzv. **vlastnost haldy**, která říká, že klíč každého uzlu musí být větší nebo rovný klíčům oběma jeho potomkům (nebo menší nebo rovný v závislosti na směru řazení).

Základní operace haldy:

dequeueMinimum()
: vrátí hodnotu prvku s minimálním klíčem a odebere jej

enqueue(*key*, *value*)
: přidá prvek *value* s klíčem *key* 

```include:java
ds/heap/Heap.java
```

### Halda v poli

Haldu s *n* uzly lze uložit do pole o velikosti *n* tím způsobem, že kořen je uložen v jeho prvním prvku a pak pro něj a pro každý další uzel na indexu € i € platí, že jeho potomci se nachází na indexu € 2i+1 € a € 2i+2 €.

```dot:digraph
ratio=0.5;
T -> S [label=" 2*0+1=1"];
T -> R [label=" 2*0+2=2"];
S -> P [label=" 2*1+1=3"];
S -> N [label=" 2*1+2=4"];
R -> O [label=" 2*2+1=5"];
R -> A [label=" 2*2+2=6"];
T[label="T (0)"];
S[label="S (1)"];
R[label="R (2)"];
P[label="P (3)"];
N[label="N (4)"];
O[label="O (5)"];
A[label="A (6)"];
{rank=same;T}
{rank=same;S;R;}
{rank=same;P;N;O;A;}
```

#### Oprava haldy

Operace pro opravu haldy zajišťuje, že vlastnost haldy platí pro všechny její prvky, a tedy na vrcholu zaručeně leží nejmenší (resp. největší) prvek. 
Oprava haldy se spouští ve dvou situacích: pokud odebíráme nejmenší (největší) prvek, a pokud vkládáme prvek nový.

- při vkládání prvku se začne halda opravovat zdola nahoru a oprava začíná vloženým prvkem
- při odebírání nejmenšího (největšího) prvku se halda začne opravovat shora dolu a oprava začíná kořenem celého stromu 

#### Vytvoření haldy v poli

K vytvoření haldy v poli potřebujeme mít definovanou funkci pro opravu haldy (viz výše), kterou jen zavoláme pro všechny vnitřní uzly. 
Pro listy ji volat nemusíme, protože listy jsou samy o sobě triviálně haldami. 
Před opravou větších hald již musí být opraveny haldy menší, postupuje se tedy směrem od potomků k rodičům, tj. iterace přes pole začíná od konce pole a pokračuje směrem k jeho začátku.

#### Implementace

```include:java
ds/heap/ArrayHeap.java
```

#### Řazení

Výše uvedené metody lze využít k vytvoření řadícího algoritmu, který se nazývá [heap sort](wiki/heap-sort). 
Funguje na principu postupného odebírání maximálních prvků z haldy.

```include:java
HeapSort.java
```

### Reference

- http://www.cs.cmu.edu/~adamchik/15-121/lectures/Trees/trees.html
- http://cslibrary.stanford.edu/110/BinaryTrees.html
- http://algs4.cs.princeton.edu/24pq/