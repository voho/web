## Halda (heap)

Halda je speciální druh [binárního stromu](wiki/datova-struktura-binarni-strom), která se používá primárně pro efektivní nalezení minimálního (či maximálního) prvku v konstantním čase. Proto je to častá implementace prioritních front.

Aby byl nějaký binární strom haldou, musí každý jeho uzel splňovat tzv. **vlastnost haldy**, která říká, že klíč každého uzlu musí být větší nebo rovný klíčům oběma jeho potomkům (nebo menší nebo rovný v závislosti na směru řazení).

Základní operace haldy:

- *dequeueMinimum()* = vrátí hodnotu prvku s minimální m(nebo maximálním) klíčem a odebere je
- *enqueue(key, value)* = přidá prvek se zadaným klíčem 

### Halda v poli

Haldu s *n* uzly lze uložit do pole o velikosti *n* tím způsobem, že kořen je uložen v jeho prvním prvku a pak pro něj a pro každý další uzel na indexu € i € platí, že jeho potomci se nachází na indexu € 2i+1 € a € 2i+2 €.

![halda v poli](http://algs4.cs.princeton.edu/24pq/images/heap-representations.png)

#### Oprava haldy

Operace pro opravu haldy se nezývá *maxHeapify* nebo *minHeapify* podle toho, zda má na vrcholu haldy ležet maximum či minimum. Z hlediska implementace se jedná pouze o prohození operátorů porovnání, takže uvedeme pouze *maxHeapify*. Tato operace se spouští pro podstrom na určitém indexu.

```java
private static void maxHeapify(final int[] array, final int rootIndex, final int heapSize) {
    // left child of node N is at N*2 + 1
    final int leftChildIndex = rootIndex * 2 + 1;
    // right child of node N is at N*2 + 2
    final int rightChildIndex = rootIndex * 2 + 2;
    // index of the largest subtree found (initialized to root) 
    int largestIndex = rootIndex;

    if (leftChildIndex < heapSize) {
        // left child exists: update largest subtree index if necessary
        if (array[leftChildIndex] > array[largestIndex]) {
            largestIndex = leftChildIndex;
        }
    }

    if (rightChildIndex < heapSize) {
        // right child exists: update largest subtree index if necessary
        if (array[rightChildIndex] > array[largestIndex]) {
            largestIndex = rightChildIndex;
        }
    }

    if (rootIndex != largestIndex) {
        // we found that heap property is broken here:
        // - swap the largest child with the parent
        // - repair the heap starting from the changed children (recursivelly)
        swap(array, rootIndex, largestIndex);
        maxHeapify(array, largestIndex, heapSize);
    }
}
```

#### Vytvoření haldy v poli

K vytvoření haldy v poli potřebujeme mít definovanou funkci pro opravu haldy (viz výše), kterou jen zavoláme pro všechny vnitřní uzly. Pro listy ji volat nemusíme, protože listy jsou samy o sobě triviálně haldami. Proto je možné začít opravovat haldu až od posledního vnitřního uzlu. Před opravou větších hald již musí být opraveny haldy menší, postupuje se tedy směrem od potomků k rodičům, což také udává směr iterace od konce pole směrem k jeho začátku.

Výsledný kód tedy vypadá nějak takto:

```java
private static void buildMaxHeap(final int[] array) {
    final int firstLeafIndex = array.length / 2;

    for (int i = firstLeafIndex - 1; i >= 0; i--) {
        maxHeapify(array, i, array.length);
    }
}
```

#### Řazení

Výše uvedené metody lze využít k vytvoření řadícího algoritmu, který se nazývá [heap sort](wiki/heap-sort). Funguje na principu postupného odebírání maximálních prvků z haldy. Zde je jeho kostra:

```java
private static void heapsort(final int[] array) {
    // first build the initial heap 
    buildMaxHeap(array);
    // start with heap spanning the whole array
    int heapSize = array.length;

    while (heapSize > 1) {
        // move the greatest element to the end of array
        // bring forward the wrong element instead
        swap(array, 0, heapSize - 1);
        // fix the heap property so the maximum sifts up again
        // also decrement heap size to not touch the array end
        heapSize--;
        maxHeapify(array, 0, heapSize);
    }
}
```

### Reference

- http://www.cs.cmu.edu/~adamchik/15-121/lectures/Trees/trees.html
- http://cslibrary.stanford.edu/110/BinaryTrees.html
- http://algs4.cs.princeton.edu/24pq/