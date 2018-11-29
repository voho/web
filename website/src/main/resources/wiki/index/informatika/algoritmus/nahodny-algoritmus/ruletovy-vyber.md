## Ruletový výběr

Ruletový výběr je [algoritmus](wiki/algoritmus), který provádí výběr náhodnéhu prvku s [pravděpodobností](wiki/pravdepodobnost) odpovídající jeho váze, což je libovolné nezáporné číslo. 
Čím vyšší váha, tím vyšší pravděpodobnost, že bude daný prvek vybrán.

Obecně je tedy vstupem algoritmu [množina](wiki/mnozina) prvků a [zobrazení](wiki/zobrazeni), které pro každý prvek určí jeho váhu.
Váhy nemusí být celočíselné - jediný rozdíl je v tom, že celočíselné váhy se lépe ukazují na příkladu.
Hodnoty váhy mohou být teoreticky libovolně veliké, protože se porovnávají pouze samy mezi sebou, avšak implementace mohou nějaká omezení klást - například rozsahem datových typů.

Jak ruletový výběr funguje? Představme si, že máme prvky (*A*, *B*, *C*, *D*, *E*, *F*) s celočíselnými vahami (4, 1, 3, 8, 1, 1).
Z těchto údajů můžeme vytvořit následující ruletu, ve které každý prvek zopakujeme právě tolikrát, kolik udává jeho váha.
Na pořadí prvků vůbec nezáleží, protože předpokládáme, že každé políčko rulety může být vybráno se stejnou pravděpodobností.

|0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---
|B|A|D|D|A|D|C|D|D|A|E|D|C|C|F|D|A|D

Prvek nyní zvolíme tak, že vybereme náhodné políčko (0 až 17) a vrátíme odpovídající prvek (např. pro náhodné číslo 5 vrátíme prvek *D*).

Aby se však tento algoritmus lépe implementoval, zkusme ruletu trochu upravit.
V nové ruletě políčka uspořádáme tak, aby všechna políčka představující stejné prvky následovala za sebou:

|0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---
|A|A|A|A|B|C|C|C|D|D|D|D|D|D|D|D|E|F

Z rulety nyní můžeme vypsat rozsahy políček, které jednotlivé prvky zabírají (první políčko číslujeme jako *0*):

| Prvek | Počet políček (váha) | Počáteční políčko | Koncové políčko | Rozsah políček
|---|---|---|---|---
| A | 4 | 0 | 3 | (0 až 3)
| B | 1 | 4 | 4 | (4 až 4)
| C | 3 | 5 | 7 | (5 až 7)
| D | 8 | 8 | 15 | (8 až 15)
| E | 1 | 16 | 16 | (16 až 16)
| F | 1 | 17 | 17 | (17 až 17)

Prvek vybereme opět tak, že zvolíme náhodné políčko (0 až 17) a vrátíme prvek, který se na něm nachází (např. pro náhodné číslo 7 vrátíme prvek *C*).
Nyní však nemusíme znát všechna políčka, stačí nám projít tabulku a najít rozsah, do kterého toto náhodně vybrané políčko patří.
Jelikož má tabulka pro každý prvek jen jeden řádek, je průchod tabulkou daleko rychlejší než roztáčení rulety, která musí mít právě tolik políček, kolik udává celková váha.

Druhou výhodou tabulky je, že váhy nyní nemusí být celočíselné. Podobného výběru bychom mohli dosáhnout například s touto tabulkou:

| Prvek | Váha | Rozsah políček
|---|---|---
| A | 0,4 | <0 až 0.4)
| B | 0,1 | <0.4 až 0.5)
| C | 0,3 | <0.5 až 0.8)
| D | 0,8 | <0.8 až 1.6)
| E | 0,1 | <1.6 až 1.7)
| F | 0,1 | <1.7 až 1.8)

Nyní však musíme vygenerovat náhodné číslo v rozsahu *0* (včetně) až *1,8*.
Výběr probíhá stejně - opět najdeme odpovídající rozsah a vrátíme prvek, který do něj patří (např. pro náhodné číslo 0,2442 vrátíme prvek *A*).

Jednoduchá implementace této techniky v [jazyce Java](wiki/java) se linární asymptotickou složitostí €O(n)€:

```include:java
random/RouletteWheelSelection.java
```

Algoritmus lze vylepšit až na logaritmickou složitost €O(\log(n))€ použitím binárního vyhledávání:

```include:java
random/RouletteWheelSelectionWithBinarySearch.java
```

### Reference

- https://www.youtube.com/watch?v=9JzFcGdpT8E