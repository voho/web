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
root [shape=none;fillcolor=transparent];
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

add(*key*)
: přidá klíč *key*
 
containsPrefix(*key*)
: ověří, zda se mezi klíči nachází nějaký, který má zadaný klíč jako prefix (například klíč *he* je prefixem klíče *hello*).

### Implementace

#### Deklarace uzlu

```include:java
ds/trie/Trie.java
```

#### Datová struktura

```include:java
ds/trie/GenericTrie.java
```

Tato implementace umožňuje vnitřní strukturu řešit různým způsobem.

První řešení využívá [hash tabulku](wiki/datova-struktura-hash-tabulka) a je univerzální co do datových typů, zabírá však mnoho místa v paměti navíc.

```include:java
ds/trie/CharacterToNodeHashMap.java
```

Druhé řešení je mnohem úspornější, ale funguje pouze pro malá písmena latinky.
Podobná řešení lze připravit i pro jiné množiny znaků s podobnou kardinalitou ([ASCII](wiki/ascii) a podobně).

```include:java
ds/trie/CharacterToNodeArrayMap.java
```

### Reference

* https://www.cs.bu.edu/teaching/c/tree/trie/
* https://www.topcoder.com/community/data-science/data-science-tutorials/using-tries/