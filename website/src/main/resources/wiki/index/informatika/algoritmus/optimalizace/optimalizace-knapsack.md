## Problém batohu (knapsack)

Problém batohu (knapsack problem) je optimalizační úloha, která trochu připomíná dilema zloděje s malým batohem, do kterého se snaží vměstnat věci s nejvyšší možnou hodnotou. Logicky se tedy bude snažit svůj zlodějský batoh primárně naplnit nějakými drobnými zlatými cennostmi či drahými kameny a pečivo nebo klavír raději na místě nechá.

Mějme tedy množinu nějakých věcí. Každá tato věc má určitou váhu a hodnotu. Které z těchto věcí musíme vzít, chceme-li maximalizovat jejich celkovou hodnotu a přitom nepřekročit maximální dovolenou váhu?

Zde se budeme zabývat asi nejznámnější variantou tohoto problému, která nedovoluje věci dělit na menší (tzv. 0-1 knapsack). Každou věc tedy musíme buď celou vzít, nebo celou nevzít.

### Formalizace

Zadáno je celé číslo € W > 0 € a dvě uspořádané n-tice celých kladných čísel:

- € (v_1, \ldots, v_n) €, kde € v_i € je hodnota položky číslo *i*
- € (w_1, \ldots, w_n) €, kde € w_i € je váha položky číslo *i*

Úkolem je nalézt takovou množinu *T*, která splňuje následující podmínky:

- € T \subseteq \left\\{1 \ldots n \right\\} € (řešením je podmnožina zadaných věcí)
- € \sum_{i \in T} v_i € je maximální ze všech možných (hledá se maximální hodnota podmnožiny věcí)
- € \sum_{i \in T} w_i \leq W € (celková váha věcí musí být zároveň menší nebo rovna maximální přípustné váze)

### Řešení

Nejjednodušším řešením, avšak neoptimálním, může být jeden z následujících hladových algoritmů:

- seřadit věci podle hodnoty sestupně a postupně je přidávat do batohu, dokud se vejdou
- seřadit věci podle hmotnosti a postupně je přidávat do batohu, dokud se vejdou
- seřadit věci podle poměru ceny a hmotnosti (měrné ceny) a postupně je přidávat do batohu, dokud se vejdou

Tyto postupy však obecně nevedou k optimálnímu řešení.

Obecné optimální řešení lze najít postupným rozkládáním problému na menší podproblémy a jejich postupným skládáním, což se podobá matematické indukci. Triviální případ je takový, že nemáme k dispozici žádnou věc a hodnota batohu je tedy nulová. Nyní využijeme matematickou indukci. Předpokládejme, že již v batohu nějaké věci jsou, batoh má určitou hodnotu a zbývá určitá váha. Pokud v tomto obecném případě přidáváme další věc, máme na výběr nanejvýš ze dvou možností:

- **věc přidáme do batohu** - pokud v batohu zbývá dostatečná kapacita pro danou věc, přidáme ji, což zvýší hodnotu batohu o hodnotu vybrané věci, ale zároveň zmenší zbývající váhu pro ostatní věci, které bychom chtěli přidávat v budoucnu
- **věc do batohu nepřidáme** - množina věcí v batohu i jeho hodnota zůstane stejná, zbyde však více váhy pro další věci, které bychom chtěli přidávat v budoucnu

Z těchto možností vybereme takovou, která maximalizuje hodnotu batohu a zapamatujeme si, zda jsme danou věci vybrali, nebo ne. Řešení pro problém batohu s *n* věcmi a maximální váhou *w* je tedy obecně:

€€ k(n,w) = \begin{cases} n=0: 0 \\ n>0: \mathrm{max}(v_i+k(n-1,w-w_i),k(n-1,w)) \end{cases} €€

### Implementace

#### Rekurzivní řešení

Toto řešení je neefektivní, protože každý - a tedy již vyřešený - podproblém řeší znovu. Nicméně, je vhodné si jej uvést, protože je z něj patrnější způsob dělení problému na podproblémy.

Začneme s datovým modelem, který představuje jednu položku a jedno řešení:

```java
/**
 * Represents a single item which can be taken in a backpack.
 */
public static class Item {
    /**
     * weight indicates backpack capacity decrease when taking this item
     */
    private final int weight;
    /**
     * cost indicates the benefit increase when taking this item
     */
    private final int cost;

    public Item(final int weight, final int cost) {
        this.weight = weight;
        this.cost = cost;
    }

    public int getWeight() {
        return weight;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return String.format("(%d kg, $%d)", weight, cost);
    }
}
```

```java
/**
 * Represents a single knapsack solution.
 */
public static class Solution {
    /**
     * smaller sub-problem (might be NULL)
     */
    private final Solution subProblem;
    /**
     * related item (might be both taken or skipped)
     */
    private final Item relatedItem;
    /**
     * flag indicating if the item is taken
     */
    private final boolean takeItem;

    public Solution(final Solution subProblem, final Item relatedItem, final boolean takeItem) {
        this.subProblem = subProblem;
        this.relatedItem = relatedItem;
        this.takeItem = takeItem;
    }

    public Solution getSubProblem() {
        return subProblem;
    }

    public Item getRelatedItem() {
        return relatedItem;
    }

    public boolean isItemIncluded() {
        return takeItem;
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();

        if (subProblem != null) {
            buffer.append(subProblem);
            buffer.append(" --- ");
        }

        if (relatedItem != null) {
            if (takeItem) {
                buffer.append("take ");
            } else {
                buffer.append("skip ");
            }

            buffer.append(relatedItem);
        }

        return buffer.toString();
    }
}
```

Nyní přidáme vlastní řešení:

```java
/**
 * Solves the knapsack problem.
 *
 * @param items list of all possible items
 * @param maxWeight maximum weight that a backpack can take
 * @return problem solution
 */
public static Solution knapsack(final List<Item> items, final int maxWeight) {
    return knapsack(items, items.size() - 1, maxWeight);
}

/**
 * Solves the knapsack sub-problem.
 *
 * @param items list of all possible items
 * @param itemIndex maximum index of items considered for addition (start with count - 1 and decrease)
 * @param maxWeight remaining capacity in a backpack (consumed by item weight)
 * @return sub-problem solution
 */
private static Solution knapsack(final List<Item> items, final int itemIndex, final int maxWeight) {
    if (itemIndex < 0) {
        return null;
    }

    final Item itemToAdd = items.get(itemIndex);

    // first, find solution for skipping the item

    final Solution bestSolutionSkippingItem = new Solution(
            knapsack(
                    items,
                    itemIndex - 1,
                    maxWeight
            ),
            itemToAdd,
            false
    );

    if (itemToAdd.getWeight() <= maxWeight) {
        // if possible to take the item, find solution with taking it

        final Solution bestSolutionTakingItem = new Solution(
                knapsack(
                        items,
                        itemIndex - 1,
                        maxWeight - itemToAdd.getWeight()
                ),
                itemToAdd,
                true
        );

        if (getTotalCost(bestSolutionTakingItem) > getTotalCost(bestSolutionSkippingItem)) {
            // it is better to take the item than skip it

            return bestSolutionTakingItem;
        }
    }

    return bestSolutionSkippingItem;
}

/**
 * Calculates the solution cost (total benefit). The cost is composed of the following:
 * <ul>
 * <li>sub-problem cost</li>
 * <li>cost of item being taken</li>
 * </ul>
 *
 * @param solution solution to evaluate (or NULL)
 * @return total solution cost
 */
private static int getTotalCost(final Solution solution) {
    int totalCost = 0;

    if (solution != null) {
        // add cost of the sub-problem (if any)
        totalCost += getTotalCost(solution.getSubProblem());

        if (solution.isItemIncluded()) {
            // add cost of item being taken
            totalCost += solution.getRelatedItem().getCost();
        }
    }

    return totalCost;
}
```

#### Dynamické programování

Protože lze problém batohu rozložit na dva menší podproblémy a každý problém má dva parametry (počet sebraných věcí a maximální váha), pro zachování řešení podproblémů vytvoříme dvojrozměrné pole *V* o velikosti € n \times W €. Každý jeho prvek na pozici *(i, w)* bude obsahovat maximální hodnotu, které jsme schopni dosáhnout pro jakoukoliv podmnožinu věcí číslo 1 až *i* a maximální váhu *w*. Optimální řešení tedy bude na pozici € V[n, W] €. 

Pravidla pro výpočet pomocného pole budou tedy formálně tato:

- € V[0, w] = 0 € pro € 0 \leq w \leq W € (krajní případ)
- € V[i, w] = \mathrm{max}(v_i+V[i-1,w-w_i], V[i-1,w]) € (obecný případ, který volí ze dvou možností, a to zda vybrat či nevybrat položku číslo *i*)

Následující implementace předpokládá existenci stejných pomocných datových struktur a metod (*Item*, *Solution*, *getTotalCost*) jako implementace předchozí. Pro zjednodušení je zde uvedena pouze hlavní část algoritmu.

```java
/**
 * Solves the knapsack problem using dynamic programming.
 *
 * @param items list of all possible items
 * @param maxWeight maximum weight that a backpack can take
 * @return problem solution
 */
public static Solution knapsack(final List<Item> items, final int maxWeight) {
    final Solution[][] v = new Solution[items.size() + 1][maxWeight + 1];

    for (int n = 0; n <= items.size(); n++) {
        for (int w = 0; w <= maxWeight; w++) {
            if (n == 0) {
                // solve: no items to take
                v[n][w] = new Solution(null, null, false);
            } else {
                // solve: take N items
                final Item currentItem = items.get(n - 1);
                final Solution bestSolutionWithoutItem = v[n - 1][w];
                final int costWithoutItem = getTotalCost(bestSolutionWithoutItem);

                // we can always fallback to not taking the item 
                v[n][w] = new Solution(bestSolutionWithoutItem, currentItem, false);

                if (w >= currentItem.getWeight()) {
                    // we have enough weight take item N
                    final Solution bestSolutionWithoutItemReservedWeight = v[n - 1][w - currentItem.getWeight()];
                    final int costWithItem = getTotalCost(bestSolutionWithoutItemReservedWeight) + currentItem.getCost();

                    if (costWithItem > costWithoutItem) {
                        // it is actually better to take the item = update solution
                        v[n][w] = new Solution(bestSolutionWithoutItemReservedWeight, currentItem, true);
                    }
                }
            }
        }
    }

    return v[items.size()][maxWeight];
}
```

### Reference

- http://www.es.ele.tue.nl/education/5MC10/Solutions/knapsack.pdf
- http://www.diku.dk/~pisinger/95-1.pdf