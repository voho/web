## Spojový seznam (linked list)

Většina programovacích jazyků nabízí možnost, jak získat referenci či adresu nějakého objektu a tuto adresu v programu používat a předávat jako běžný datový typ, například celé číslo. Tento referenční datový typ zde budeme nazýva třeba **ukazatel** (někde se také můžete setkat s označením reference, odkaz, adresa, atd.). Ukazatel lze dále rozšířit tak, že umožníme, aby mohl nabývat i speciální hodnoty *NULL* (prázdno), která indikuje, že tato reference na žádný objekt neukazuje.

Spojové seznamy jsou dynamické datové struktury, které se skládají z lineárně uspořádaných prvků vzájemně propojených ukazateli. Každý prvek v sobě nese **datovou hodnotu** (například číslo, znak, datovou strukturu nebo objekt) a **ukazatel** na následující prvek (*next*), přičemž poslední prvek ukazuje do prázdna (na hodnotu *NULL*). 

Prvek spojového seznamu tedy "obaluje" datovou hodnotu informacemi o jejím relativním umístění vůči ostatním prvkům a bez datové hodnoty by asi nemělo vůbec smysl nějaký seznam dělat. Při práci se strukturou seznamu (přidávání a odebírání prvků) se však datovými hodnotami nemusíme vůbec zabývat.

Vstupním bodem do spojového seznamu je ukazatel na jeho první prvek, který se také nazývá **hlavička** (*head*). Prázdný spojový seznam poznáme tak, že do prázdna ukazuje už přímo jeho hlavička.

Za cenu další obsazené paměti a složitějších operací lze jednoduché spojové seznamy ještě vylepšit a umožnit tak efektivnější běh algoritmům, které s nimi pracuji. Nejčastější modifikace jsou tyto:

- Pro snadné přidávání prvků na konec seznamu je vhodné zavést ukazatel na konec seznamu, tzv. **patičku** (*tail*).
- Pro efektivní obousměrný průchod lze prvky zřetězit i zpětně, takže kromě ukazatele na následující prvek (*next*) bude každý prvek ukazovat i na prvek předchozí (*prev*).

### Jednosměrně zřetězené seznamy

V jednosměrně zřetězených seznamech mají prvky pouze jeden ukazatel, a to na následující prvek.

```dot:digraph
ratio=0.3
head -> "item 1" [label="  head"]
tail -> "item 3" [label="  tail"]
head [shape=none,label="",style=invisible]
tail [shape=none,label="",style=invisible]
null [shape=none,fillcolor=transparent]
"item 1" -> "item 2" -> "item 3" -> null [label=" next"]
{rank=same;head;tail;}
{rank=same;"item 1";"item 2";"item 3";null}
```

### Obousměrně zřetězené seznamy

V obousměrně zřetězených seznamech mají prvky dva ukazatele: jeden na prvek následující a druhý na prvek předchozí.

```dot:digraph
ratio=0.5
head -> "item 1" [label=" head"]
tail -> "item 3" [label=" tail"]
head [shape=none,label="",style=invisible]
tail [shape=none,label="",style=invisible]
null1 [shape=plaintext,label="null",fillcolor=transparent]
null2 [shape=plaintext,label="null",fillcolor=transparent]
"item 1" -> "item 2" -> "item 3" -> null1 [label=" next"]
"item 3" -> "item 2" -> "item 1" -> null2 [label=" prev"]
{rank=same;head;tail;}
{rank=same;null1;null2;}
{rank=same;"item 1";"item 2";"item 3";}
```

### Cyklicky zřetězený seznam

Pokud poslední prvek seznamu neukazuje do prázdna, ale je napojený opět na začátek seznamu, jedná se o tzv. **cyklicky zřetězený seznam**.

```dot:digraph
{rank=same;head;tail;}
{rank=same;"item 1";"item 2";"item 3"}
ratio=0.3
head -> "item 1" [label="  head"]
tail -> "item 3" [label="  tail"]
head [shape=none,label="",style=invisible]
tail [shape=none,label="",style=invisible]
"item 1" -> "item 2" -> "item 3" [label=" next"]
"item 3" -> "item 1"
```

### Asymptotická složitost

| Typ seznamu | Jednosměrně zřetězený | Obousměrně zřetězený |
|---|---|---
| přidání prvku | € O(1) € | € O(1) € |
| mazání prvku | € O(1) € | € O(1) € |
| indexace (náhodný přístup k prvku č. *i*) | € O(n) € | € O(n) € |
| vyhledávání | € O(n) € | € O(n) € |

### Výhody a nevýhody

#### Výhody

- kapacita je teoreticky neomezená
- velikost obsazené paměti je přímo závislá jen na počtu prvků, není zde žádné plýtvání
- rychlost přidávání i odebírání prvků je vždy stejně vysoká

#### Nevýhody

- pomalý přístup k prvkům na zadaném indexu *i* (random access)
- uložené hodnoty nejsou v paměti uspořádány za sebou a změna struktury seznamu může způsobit fragmentaci volného místa v paměti
- pomalejší procházení (při každém posunu je nutná dereference ukazatele a skok na místo v paměti)
- stejné množství dat zabírá více paměti než stejné prvky uložené v poli (kvůli ukazatelům navíc)

### Implementace

#### Rozhraní

```include:java
List.java
```

#### Jednosměrně zřetězený seznam

Nejjednodušší jednosměrně zřetězený seznam s hlavičkou:

```include:java
SinglyLinkedList.java
```

#### Obousměrně zřetězený seznam

Obousměrně zřetězený seznam s hlavičkou i patičkou (všimněte si, že je implementace celkem jednoduchá, protože se tu objevuje symetrie - hlavička/patička, předchozí/následující prvek):

```include:java
DoublyLinkedList.java
```

### Reference

- http://cslibrary.stanford.edu/103/LinkedListBasics.pdf
