## Hashovací tabulka (hash table)

Hashovací tabulka (popř. hašovací či hešovací tabulka) je [datová struktura](wiki/datova-struktura) pro ukládání dvojic **(klíč, hodnota)** nabízející dobrý kompromis mezi rychlostí vyhledávání a paměťovou náročností. Princip vyhledávání v hashovací tabulce je podobné vyhledávání dokumentů v uklizené kanceláři: pokud chci například najít určitou fakturu, klíčem bude její číslo 20150715. Z klíče odvodím, že je to faktura z roku 2015, určitě jí tedy najdu v zásuvce nadepsané "faktury 2015". Hashovací tabulka dělá něco podobného - automaticky pro každý klíč určí jeho kategorii a hledá v přihrádkách, ve kterých by se daný klíč mohl nacházet. Tímto způsobem lze ušetřit čas za podmínky, že je kategorizace záznamu dostatečně rychlá a přehledná.

Obecně řečeno, hashovací tabulka je datová struktura se schopností efektivně vkládat, mazat a hledat datové záznamy **podle klíče**.

Pokud bychom měli celočíselné klíče dlouhé 32 bitů a chtěli bychom maximalizovat rychlost vyhledávání, ukládali bychom všechny záznamy do [pole](wiki/datova-struktura-pole), ve kterém by klíč byl zároveň indexem záznamu. Takto by však docházelo k obrovskému plýtvání pamětí v případě, že by bylo obsazeno jen několik klíčů.

Alternativou by bylo možné použít [spojový seznam](wiki/datova-struktura-seznam) a uložit všechny záznamy do něho. Takto by se pamětí neplýtvalo, ale každé vyhledávání by znamenalo projít celý seznam.

Bylo by možné použít i [binární vyhledávací strom](wiki/datova-struktura-binarni-strom) organizovaný podle klíče. U něj je výkon závislý na jeho hloubce a pro dosažení optimální efektivity se musí vyvažovat.

Hashovací tabulka za cenu určitého kompromisu spojuje výhody pole a spojového seznamu a nevýhody minimalizuje. Kromě toho je v obecném případě možné rekurzivně vnořovat hashovací tabulky do sebe a vybudovat hierarchické struktury podobné paměti v počítači.

### Princip fungování

Hashovací tabulka uvnitř obsahuje pole tzv. **slotů**, do kterých lze ukládat záznamy. Hashovací tabulka v podstatě dělá jen to, že na základě klíče vybere vhodný slot a operaci provede v něm. Abychom docílili efektivity, snažíme se, aby všechny sloty byly využity rovnoměrně, tedy aby různé klíče ideálně padaly do různých slotů. Samozřejmě to není zcela možné, protože množina klíčů je mnohem větší než počet slotů. Takové situaci říkáme **kolize** a existují různé metody, jak tyto kolize řešit:

- **zřetězení záznamů** (separate chaining) - každý slot obsahuje spojový seznam, do kterého se postupně řetězí prvky patřící do stejného slotu
- **otevřená adresace** (open addressing) - obsah všech slotů je umístěn v jednom poli a tak mohou data z jednoho slotu "přetékat" i do jiných slotů a tím zabírat volné místo pro jejich budoucí prvky, což se minimalizuje různými dalšími technikami (linear probing, double hashing...)

```dot:digraph
splines=false
subgraph cluster0 {
  John;Adele;Eve;Adam;Jack;
  label="keys"
  style=filled
  fillcolor=lightgray
}
subgraph cluster1 {
  J;A;E; 
  label="clusters"
  style=filled
  fillcolor=yellowgreen
  J [shape=house, label="J: John, Jack"]
  A [shape=house, label="A: Adele, Adam"]
  E [shape=house, label="E: Eve"]
}
Jack->J [ arrowhead=none]
John->J [ arrowhead=none]
Adele->A [ arrowhead=none]
Adam->A [arrowhead=none]
Eve->E [ arrowhead=none]
```

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

```include:java
HashTableBackedByArray.java
```

#### Pomocí pole a spojového seznamu

![hashovací tabulka realizovaná polem a spojovými seznamy](https://he-s3.s3.amazonaws.com/media/uploads/0e2c706.png)

```include:java
HashTableWithLinkedListSlots.java
```

### Reference

- http://en.wikipedia.org/wiki/Hash_table
- http://www.algolist.net/Data_structures/Hash_table/Simple_example
- http://bigocheatsheet.com/
- http://primes.utm.edu/lists/small/10000.txt
