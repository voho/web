## Hashovací tabulka (hash table)

Hashovací tabulka (popř. hašovací či hešovací tabulka) je [datová struktura](wiki/datova-struktura) pro ukládání dvojic **(klíč, hodnota)** nabízející dobrý kompromis mezi rychlostí vyhledávání a paměťovou náročností. Princip vyhledávání v hashovací tabulce je podobné vyhledávání dokumentů v uklizené kanceláři: pokud chci například najít určitou fakturu, klíčem bude její číslo 20150715. Z klíče odvodím, že je to faktura z roku 2015, určitě jí tedy najdu v zásuvce nadepsané "faktury 2015". Hashovací tabulka dělá něco podobného - automaticky pro každý klíč určí jeho kategorii a hledá v přihrádkách, ve kterých by se daný klíč mohl nacházet. Tímto způsobem lze ušetřit čas za podmínky, že je kategorizace záznamu dostatečně rychlá a přehledná.

Obecně řečeno, hashovací tabulka je datová struktura se schopností efektivně vkládat, mazat a hledat datové záznamy **podle klíče**.

Pokud bychom měli celočíselné klíče dlouhé 32 bitů a chtěli bychom maximalizovat rychlost vyhledávání, ukládali bychom všechny záznamy do [pole](wiki/datova-struktura-pole), ve kterém by klíč byl zároveň indexem záznamu. Takto by však docházelo k obrovskému plýtvání pamětí v případě, že by bylo obsazeno jen několik klíčů.

Alternativou by bylo možné použít [spojový seznam](wiki/datova-struktura-seznam) a uložit všechny záznamy do něho. Takto by se pamětí neplýtvalo, ale každé vyhledávání by znamenalo projít celý seznam.

Bylo by možné použít i [binární vyhledávací strom](wiki/datova-struktura-binarni-strom) organizovaný podle klíče. U něj je výkon závislý na jeho hloubce a pro dosažení optimální efektivity se musí vyvažovat.

Hashovací tabulka za cenu určitého kompromisu spojuje výhody pole a spojového seznamu a nevýhody minimalizuje. Kromě toho je v obecném případě možné rekurzivně vnořovat hashovací tabulky do sebe a vybudovat hierarchické struktury podobné paměti v počítači.

### Princip fungování

![hashovací tabulka](http://upload.wikimedia.org/wikipedia/commons/7/7d/Hash_table_3_1_1_0_1_0_0_SP.svg)

Hashovací tabulka uvnitř obsahuje pole tzv. **slotů**, do kterých lze ukládat záznamy. Hashovací tabulka v podstatě dělá jen to, že na základě klíče vybere vhodný slot a operaci provede v něm. Abychom docílili efektivity, snažíme se, aby všechny sloty byly využity rovnoměrně, tedy aby různé klíče ideálně padaly do různých slotů. Samozřejmě to není zcela možné, protože množina klíčů je mnohem větší než počet slotů. Takové situaci říkáme **kolize** a existují různé metody, jak tyto kolize řešit:

- **zřetězení záznamů** (separate chaining) - každý slot obsahuje spojový seznam, do kterého se postupně řetězí prvky patřící do stejného slotu
- **otevřená adresace** (open addressing) - obsah všech slotů je umístěn v jednom poli a tak mohou data z jednoho slotu "přetékat" i do jiných slotů a tím zabírat volné místo pro jejich budoucí prvky, což se minimalizuje různými dalšími technikami (linear probing, double hashing...)

Převod klíče na index slotu realizuje tzv. **hashovací funkce**. Toto [zobrazení](wiki/zobrazeni) nemusí být injektivní, ale mělo by mít následující vlastnosti:

- ideálně by mělo vracet různé sloty s rovnoměrnou [pravděpodobností](wiki/pravdepodobnost)
- mělo by být velmi rychle vypočitatelné

Mezi nejčastěji používané hashovací funkce patří **modulo** (zbytek po dělení) nebo násobení klíče prvočíslem a následná normalizace na počet slotů, tedy € h(k) = p \cdot k \bmod m €, kde €k€ je celočíselný klíč, €p€ je prvočíslo a €m€ je počet slotů (velikost pole se sloty).

### Asymptotická složitost

| Operace | Typický případ | Nejhorší případ
|---|---|---
| vyhledávání podle klíče | € O(1) € | € O(n) € 
| vkládání záznamu | € O(1) € | € O(n) € 
| mazání záznamu | € O(1) € | € O(n) € 

### Implementace

#### Pomocí pole

![hashovací tabulka realizovaná jedním polem](http://upload.wikimedia.org/wikipedia/commons/b/bf/Hash_table_5_0_1_1_1_1_0_SP.svg)

```java
/**
 * Simple array-based hash table.
 *
 * @author Vojtěch Hordějčuk
 */
public class MyArrayBasedHashTable<DATA> {
    private static class HashEntry<DATA> {
        private int key;
        private DATA value;
    }

    private final HashEntry<DATA>[] table;

    /**
     * Creates a new instance.
     *
     * @param tableSize table size
     */
    public MyArrayBasedHashTable(int tableSize) {
        table = new HashEntry[tableSize];
    }

    /**
     * Adds a value into the table. Does not overwrite any existing value.
     * Throws a runtime exception if no free space is left in the table.
     *
     * @param key   key
     * @param value value
     */
    public void add(int key, DATA value) {
        Integer firstExpectedIndexForKey = scanForNextFreeIndexByKey(key);

        if (firstExpectedIndexForKey == null) {
            throw new IllegalStateException("No free space in table.");
        }

        HashEntry<DATA> newEntry = new HashEntry<DATA>();
        newEntry.key = key;
        newEntry.value = value;
        table[firstExpectedIndexForKey] = newEntry;
    }

    /**
     * Finds first value with the given key.
     *
     * @param key key to find
     * @return first value with this key or NULL
     */
    public DATA findByKey(int key) {
        Integer index = scanForIndexByKey(key);

        if (index != null) {
            return table[index].value;
        }

        return null;
    }

    /**
     * Removes the first occerrence of a value with the given key.
     *
     * @param key key to remove
     * @return TRUE if the value was found and removed, FALSE otherwise
     */
    public boolean removeByKey(int key) {
        Integer index = scanForIndexByKey(key);

        if (index != null) {
            if (table[index] != null) {
                table[index] = null;
            }

            return true;
        }

        return false;
    }

    private Integer scanForNextFreeIndexByKey(int key) {
        int index = getKeyHash(key);

        for (int i = 0; i < table.length; i++) {
            if (table[index] == null) {
                // empty cell found
                return index;
            }

            // continue and wrap around
            index = (index + 1) % table.length;
        }

        return null;
    }

    private Integer scanForIndexByKey(int key) {
        int index = getKeyHash(key);

        for (int i = 0; i < table.length; i++) {
            if (table[index] != null && table[index].key == key) {
                // non-empty cell with correct key found
                return index;
            }

            // continue and wrap around
            index = (index + 1) % table.length;
        }

        return null;
    }

    private int getKeyHash(int key) {
        return (key % table.length);
    }
}

```

#### Pomocí pole a spojového seznamu

![hashovací tabulka realizovaná polem a spojovými seznamy](http://sydney.edu.au/engineering/it/~scilect/tpop/handouts/hashlist.gif)

```java
/**
 * Array-to-linked list based hash table.
 *
 * @author Vojtěch Hordějčuk
 */
public class MyHashTableWithLinkedListSlots<VALUE> {
    private static class Entry<VALUE> {
        private int innerKey;
        private VALUE innerValue;
        private Entry<VALUE> next = null;
    }

    private static class Slot<VALUE> {
        private Entry<VALUE> head = null;

        private void add(int key, VALUE value) {
            Entry<VALUE> newEntry = new Entry<VALUE>();
            newEntry.innerKey = key;
            newEntry.innerValue = value;

            if (head == null) {
                // empty list
                head = newEntry;
            } else {
                Entry<VALUE> temp = head;

                while (temp.next != null) {
                    temp = temp.next;
                }

                temp.next = newEntry;
            }
        }

        private VALUE findByKey(int key) {
            Entry<VALUE> temp = head;

            while (temp != null) {
                if (temp.innerKey == key) {
                    // value found
                    return temp.innerValue;
                }

                temp = temp.next;
            }

            return null;
        }

        private boolean removeByKey(int key) {
            Entry<VALUE> temp = head;
            Entry<VALUE> prevOfTemp = null;

            while (temp != null) {
                if (temp.innerKey == key) {
                    if (prevOfTemp == null) {
                        // removing head
                        head = head.next;
                    } else {
                        // general case
                        prevOfTemp.next = temp.next;
                    }
                    return true;
                }

                prevOfTemp = temp;
                temp = temp.next;
            }

            return false;
        }
    }

    private final Slot<VALUE>[] slots;

    /**
     * Creates a new instance.
     *
     * @param numberOfSlots number of slots
     */
    public MyHashTableWithLinkedListSlots(int numberOfSlots) {
        slots = new Slot[numberOfSlots];
    }

    /**
     * Adds a value with the given key. Allows more values with the same key.
     *
     * @param key   key
     * @param value value
     */
    public void add(int key, VALUE value) {
        Slot<VALUE> slot = getSlotForKey(key, true);
        slot.add(key, value);
    }

    /**
     * Finds a first value by the given key.
     *
     * @param key key to find
     * @return first value found with this key or NULL
     */
    public VALUE findByKey(int key) {
        Slot<VALUE> slot = getSlotForKey(key, false);
        if (slot != null) {
            return slot.findByKey(key);
        }
        return null;
    }

    /**
     * Removes a first occurrence of a value with the given key.
     *
     * @param key key to remove
     * @return TRUE if a value was found and removed, FALSE otherwise
     */
    public boolean removeByKey(int key) {
        Slot<VALUE> slot = getSlotForKey(key, false);
        if (slot != null) {
            return slot.removeByKey(key);
        }
        return false;
    }

    private Slot<VALUE> getSlotForKey(int key, boolean createIfMissing) {
        int bucketIndex = getHash(key);
        Slot<VALUE> slot = slots[bucketIndex];
        if (slot == null && createIfMissing) {
            slot = new Slot<VALUE>();
            slots[bucketIndex] = slot;
        }
        return slot;
    }

    private int getHash(int key) {
        // simple hash function
        return key % slots.length;
    }
}

```

### Reference

- http://en.wikipedia.org/wiki/Hash_table
- http://www.algolist.net/Data_structures/Hash_table/Simple_example
- http://bigocheatsheet.com/
- http://primes.utm.edu/lists/small/10000.txt
