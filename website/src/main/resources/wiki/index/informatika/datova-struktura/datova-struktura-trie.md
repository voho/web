## Trie

Datová struktura **trie** svůj název získala nejspíše kombinací slov **retrieval** a **tree**. Jedná se o [strom](wiki/datova-struktura-strom), který umožňuje uchovávat páry klíč - hodnota a získávat hodnoty podle klíče. Klíčem však musí být řetězec. Nejprve je třeba definovat, co tvoří jeho uzly a hrany. Každý uzel reprezentuje jeden znak klíče a každý hrana jejich zřetězení. Klíč získáme jako cestu od kořene k zadanému uzlu.

### Příklad

Dejme tomu, že chceme uložit hodnoty s klíčem *baby*, *ban*, *damn*, *do*.


```dot:digraph
nodesep=1;
splines=false;
A1 [label="A"];
A2 [label="A"];
B1 [label="B"];
B2 [label="B"];
D1 [label="D"];
N1 [label="N"];
N2 [label="N"];
M1 [label="M"];
Y1 [label="Y"];
O1 [label="O"];
root [shape=none];
root -> B1;
root -> D1;
B1->A1->B2->Y1;
A1->N1;
D1->A2->M1->N2;
D1->O1;
B2->N1 [style=dotted,dir=none,len=0];
A2->O1 [style=dotted,dir=none,len=0];
```

### Operace

* **add(key)** - vloží zadaný klíč 
* **containsPrefix(key)** - ověří, zda se mezi klíči nachází nějaký, který má zadaný klíč jako prefix (například klíč *he* je prefixem klíče *hello*).

### Implementace

#### Deklarace uzlu

```java
private static class Node {
    private char keyContribution;
    private Node nextSibling;
    private Node nextChild;
}
```

#### Vyhledávací algoritmus

```java
public class ValuelessTrie {
    private final Node root = new Node();

    // ...

    private Optional<Node> findNode(char[] key, boolean createMissingNodes) {
        Node temp = root;
        int level = 0;

        while (true) {
            if (temp.keyContribution == key[level]) {
                if (level == key.length - 1) {
                    // FOUND
                    return Optional.of(temp);
                } else {
                    // need to go further: deeper level
                    if (temp.nextChild == null) {
                        if (createMissingNodes) {
                            // need to create child
                            temp.nextChild = new Node();
                            temp.nextChild.keyContribution = key[level];
                        } else {
                            return Optional.empty();
                        }
                    }
                    level++;
                    temp = temp.nextChild;
                }
            } else {
                // need to go further: current level
                if (temp.nextSibling == null) {
                    if (createMissingNodes) {
                        // need to create sibling
                        temp.nextSibling = new Node();
                        temp.nextSibling.keyContribution = key[level];
                    } else {
                        return Optional.empty();
                    }
                }
                temp = temp.nextSibling;
            }
        }
    }
}
```

#### Vyhledávání prefixu

```java
public boolean containsPrefix(char[] key) {
    return findNode(key, false).isPresent();
}
```

#### Uložení slova

```java
public void add(char[] key) {
    findNode(key, true);
}
```

### Reference

* https://www.cs.bu.edu/teaching/c/tree/trie/
* https://www.topcoder.com/community/data-science/data-science-tutorials/using-tries/