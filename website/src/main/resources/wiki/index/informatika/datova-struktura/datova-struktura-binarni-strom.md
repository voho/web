## Binární strom (binary tree)

Binární stromy jsou dynamické [datové struktury](wiki/datova-struktura), ve kterých jsou prvky hierarchicky uspořádány pomocí ukazatelů tak, že každý prvek ukazuje nejvýše na dva následující prvky a je určen jeden počáteční prvek, ze kterého všechny ukazatele vychází (tzv. **kořen**). 
Jedná se o strukturu podobnou [spojovému seznamu](wiki/datova-struktura-seznam), ale narozdíl od něho umožňuje binární strom větvení, i když omezené. 
Formálně se jedná o souvislý acyklický [graf](wiki/graf). 

Binární stromy jsou pro informatiku velmi důležité, protože se jimi modelují například [matematické výrazy](wiki/matematika) (většina operátorů je totiž unárních a binárních) či [binární kódy](wiki/bit). 
Při práci se stromem se hojně využívá rekurze.

```dot:digraph
nodesep=1
root [shape=none]
root -> 19
19 -> 15 [label=" L (<)"]
19 -> 23 [label=" R (>=)"]
15 -> 10 [label=" L (<)"]
15 -> 17 [label=" R (>=)"]
23 -> 27 [label=" R (>=)"]
```

Nyní trochu názvosloví, které vychází z [teorie grafů](wiki/graf).

Počáteční uzel stromu označujeme jako **kořen** (root). Z něho vedou hrany přes **vnitřní uzly** (inner nodes) až do **listů** (leaf nodes), kde cesta končí. Uzel, ze kterého vychází ukazatel na uzel *U*, se nazývá **rodič** (parent) uzlu *U*. 
Obecně můžeme na cestě od listu ke kořeni mluvit o **předchůdcích** (predecessors, jsou blíže kořeni) a **následnících** (successors, jsou dále od kořene). 
Prázdný strom je ukazatel se speciální hodnotou *NULL* (prázdno).

Každý uzel může mít nejvýše dva potomky, které se označují mnemotechnicky jako **levý** podstrom (*left*) a **pravý podstrom** (*right*). 
Vede-li ukazatel z potomka na rodiče, jedná se o ukazatel *parent*. 

### Binární vyhledávací strom

Binární vyhledávací strom je takový binární strom, ve kterém jsou uzly uspořádány podle nějakého kritéria tak, aby v každém uzlu *V* platilo, že všechny uzly menší než *V* jsou v jeho levém podstromě a naopak uzly větší než *V* v jeho pravém podstromě. 
Operace porovnání pak může být definována libovolně, nemusí jít jen o porovnávání dvou čísel.

Binární vyhledávací strom, jak již název napovídá, může za určitých podmínek zajistit velmi rychlé vyhledávání hodnot. 
Jeho efektivita se však odvíjí od jeho struktury, a to konkrétně od průměrné hloubky stromu, kterou je nutné pro vyhledání hodnoty projít. 
Strom například může zdegenerovat až na [spojový seznam](wiki/datova-struktura-seznam), ve kterém je hloubka rovna počtu prvků. 
Proto zde žádného zlepšení oproti spojovému seznamu nedocílíme. 

Aby byl binární strom při vyhledávání vždy efektivní, je nutné se o něj během změn starat a jeho podstromy **vyvažovat**. 
Algoritmy pro vyvažování stromu existují různé, ale zde se jimi zabývat nebudeme. 

### Asymptotická složitost

| Operace | Typický případ | Nejhorší případ 
|---|---|---
| indexace (náhodný přístup k prvku *i*) | € O(\log(n)) € | € O(n) €
| vyhledávání prvku | € O(\log(n)) € | € O(n) €
| vkládání prvku | € O(\log(n)) € | € O(n) €
| mazání prku | € O(\log(n)) € | € O(n) €

### Implementace

Tato implementace využívá rekurze. 
Výsledný kód je pak trochu jednodušší za cenu drobného snížení výkonu a rizika přetečení [zásobníků](wiki/datova-struktura-zasobnik) u obrovských stromů.

```include:java
BinaryTree.java
```

### Reference

- http://www.cs.cmu.edu/~adamchik/15-121/lectures/Trees/trees.html
- http://cslibrary.stanford.edu/110/BinaryTrees.html