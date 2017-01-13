## Binární strom (binary tree)

Binární stromy jsou dynamické [datové struktury](wiki/datova-struktura), ve kterých jsou prvky hierarchicky uspořádány pomocí ukazatelů tak, že každý prvek ukazuje nejvýše na dva následující prvky a je určen jeden počáteční prvek, ze kterého všechny ukazatele vychází (tzv. **kořen**). Jedná se o strukturu podobnou [spojovému seznamu](wiki/datova-struktura-seznam), ale narozdíl od něho umožňuje binární strom větvení, i když omezené. Formálně se jedná o souvislý acyklický graf. 

Binární stromy jsou pro informatiku velmi důležité, protože se jimi modelují například matematické výrazy (většina operátorů je totiž unárních a binárních) či binární kódy. Při práci se stromem se hojně využívá rekurze.

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

Počáteční uzel stromu označujeme jako **kořen** (root). Z něho vedou hrany přes **vnitřní uzly** (inner nodes) až do **listů** (leaf nodes), kde cesta končí. Uzel, ze kterého vychází ukazatel na uzel *U*, se nazývá **rodič** (parent) uzlu *U*. Obecně můžeme na cestě od listu ke kořeni mluvit o **předchůdcích** (predecessors, jsou blíže kořeni) a **následnících** (successors, jsou dále od kořene). Prázdný strom je ukazatel se speciální hodnotou *NULL* (prázdno).

Každý uzel může mít nejvýše dva potomky, které se označují mnemotechnicky jako **levý** podstrom (*left*) a **pravý podstrom** (*right*). Vede-li ukazatel z potomka na rodiče, jedná se o ukazatel *parent*. 

### Binární vyhledávací strom

Binární vyhledávací strom je takový binární strom, ve kterém jsou uzly uspořádány podle nějakého kritéria tak, že v každém uzlu *V* platí, že všechny uzly menší než *V* jsou v jeho levém podstromě a naopak uzly větší než *V* v jeho pravém podstromě. Operace porovnání pak může být definována libovolně, nemusí jít jen o porovnávání dvou čísel.

Binární vyhledávací strom, jak již název napovídá, může za určitých podmínek zajistit velmi rychlé vyhledávání hodnot. Jeho efektivita se však odvíjí od jeho struktury, a to konkrétně od průměrné hloubky stromu, kterou je nutné pro vyhledání hodnoty projít. Strom například může zdegenerovat až na [spojový seznam](wiki/datova-struktura-seznam), ve kterém je hloubka rovna počtu prvků. Proto zde žádného zlepšení oproti spojovému seznamu nedocílíme. 

Aby byl binární strom při vyhledávání vždy efektivní, je nutné se o něj během změn starat a jeho podstromy **vyvažovat**. Algoritmy pro vyvažování stromu existují různé, ale zde se jimi zabývat nebudeme. 

### Asymptotická složitost

| Operace | Typický případ | Nejhorší případ 
|---|---|---
| indexace (náhodný přístup k prvku *i*) | € O(\log(n)) € | € O(n) €
| vyhledávání prvku | € O(\log(n)) € | € O(n) €
| vkládání prvku | € O(\log(n)) € | € O(n) €
| mazání prku | € O(\log(n)) € | € O(n) €

### Implementace

Tato implementace využívá rekurze. Výsledný kód je pak trochu jednodušší za cenu drobného snížení výkonu a rizika přetečení zásobníků u obrovských stromů.

```java
/**
 * Binary tree example.
 *
 * @author Vojtěch Hordějčuk
 */
public class MyBinaryTree<VALUE extends Comparable<? super VALUE>> {
    private static class Node<VALUE> {
        private VALUE innerValue;
        private Node<VALUE> left;
        private Node<VALUE> right;
    }

    private Node<VALUE> root;

    /**
     * Puts the value into the tree.
     *
     * @param value value to put
     */
    public void put(final VALUE value) {
        assert value != null;
        root = putRecursive(value, root);
    }

    /**
     * Removes the value from the tree.
     *
     * @param value value to be removed
     */
    public void remove(final VALUE value) {
        assert value != null;
        root = removeRecursive(value, root);
    }

    /**
     * Returns the minimum value.
     *
     * @return the minimum value or NULL if the tree is empty
     */
    public VALUE getMinimum() {
        final Node<VALUE> nodeWithMinimum = findLeftmost(root);
        return nodeWithMinimum != null ? nodeWithMinimum.innerValue : null;
    }

    /**
     * Returns the maximum value.
     *
     * @return the maximum value or NULL if the tree is empty
     */
    public VALUE getMaximum() {
        final Node<VALUE> nodeWithMaximum = findRightmost(root);
        return nodeWithMaximum != null ? nodeWithMaximum.innerValue : null;
    }

    private Node<VALUE> putRecursive(final VALUE value, final Node<VALUE> root) {
        if (root == null) {
            final Node<VALUE> newRoot = new Node<VALUE>();
            newRoot.innerValue = value;
            return newRoot;
        }

        final int comparison = value.compareTo(root.innerValue);

        if (comparison > 0) {
            // greater value = put in the right subtree
            root.right = putRecursive(value, root.right);
            return root;
        } else if (comparison < 0) {
            // lower value = put in the left subtree
            root.left = putRecursive(value, root.left);
            return root;
        } else {
            // same value = put here
            root.innerValue = value;
            return root;
        }
    }

    private Node<VALUE> removeRecursive(final VALUE value, final Node<VALUE> root) {
        if (root == null) {
            return null;
        }

        final int comparison = value.compareTo(root.innerValue);

        if (comparison > 0) {
            // greater value = remove from the right subtree
            root.right = removeRecursive(value, root.right);
            return root;
        } else if (comparison < 0) {
            // lower value = remove from the left subtree
            root.left = removeRecursive(value, root.left);
            return root;
        } else {
            // same value = remove this node

            if (root.left == null && root.right == null) {
                // removing leaf: no changes needed
                return null;
            } else if (root.left == null) {
                // removing node with right child only: replace by right child
                return root.right;
            } else if (root.right == null) {
                // removing node with left child only: replace by left child
                return root.left;
            } else {
                // removing node with two children:
                // 1) choose left or right subtree (does not matter - we choose right)
                // 1) find in-order successor in the right subtree
                // 2) copy value from successor to this node
                // 3) remove value from the right subtree to remove duplicate
                final Node<VALUE> inOrderSuccessor = findLeftmost(root.right);
                root.innerValue = inOrderSuccessor.innerValue;
                root.right = removeRecursive(root.innerValue, root.right);
                return root;
            }
        }
    }

    private Node<VALUE> findLeftmost(final Node<VALUE> root) {
        return root == null ? null : root.left == null ? root : findLeftmost(root.left);
    }

    private Node<VALUE> findRightmost(final Node<VALUE> root) {
        return root == null ? null : root.right == null ? root : findRightmost(root.right);
    }

    // TREE WALK-THROUGH
    // =================

    public List<VALUE> listValuesPreOrder() {
        final List<VALUE> accumulator = new ArrayList<VALUE>();
        collectValuesPreOrder(root, accumulator);
        return accumulator;
    }

    public List<VALUE> listValuesInOrder() {
        final List<VALUE> accumulator = new ArrayList<VALUE>();
        collectValuesInOrder(root, accumulator);
        return accumulator;
    }

    public List<VALUE> listValuesPostOrder() {
        final List<VALUE> accumulator = new ArrayList<VALUE>();
        collectValuesPostOrder(root, accumulator);
        return accumulator;
    }

    private void collectValuesPreOrder(final Node<VALUE> root, final List<VALUE> accumulator) {
        if (root != null) {
            accumulator.add(root.innerValue);
            collectValuesPreOrder(root.left, accumulator);
            collectValuesPreOrder(root.right, accumulator);
        }
    }

    private void collectValuesInOrder(final Node<VALUE> root, final List<VALUE> accumulator) {
        if (root != null) {
            collectValuesInOrder(root.left, accumulator);
            accumulator.add(root.innerValue);
            collectValuesInOrder(root.right, accumulator);
        }
    }

    private void collectValuesPostOrder(final Node<VALUE> root, final List<VALUE> accumulator) {
        if (root != null) {
            collectValuesPostOrder(root.left, accumulator);
            collectValuesPostOrder(root.right, accumulator);
            accumulator.add(root.innerValue);
        }
    }
}
```

### Reference

- http://www.cs.cmu.edu/~adamchik/15-121/lectures/Trees/trees.html
- http://cslibrary.stanford.edu/110/BinaryTrees.html