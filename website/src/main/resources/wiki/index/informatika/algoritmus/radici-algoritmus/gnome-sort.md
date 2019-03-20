## Gnome Sort (řazení trpaslíkem)

Gnome sort je jednoduchý řadící algoritmus podobný [insertion sort](wiki/insertion-sort), avšak navržený spíše pro pobavení.

Algoritmus gnome sort je **přirozený**, **stabilní** a jeho asymptotická složitost je € O(n^2) €.

Princip jeho fungování vychází z představy, jak by zahradní trpaslíci mohli řadil květináče. 

Trpaslík se postaví ke dvěma květináčům a ověří, zda jsou správně seřazené, tedy zda menší květináč leží před tím větším.
Pokud ano, posune se o jednu pozici kupředu. 
Pokud ne, květináče prohodí, a o jednu pozici se vrátí zpět.
Poté se celý postup opakuje od začátku.
Pokud se trpaslík dostane před první květináč, musí se logicky posunout o jedno pole kupředu, aby měl co porovnávat.
A pokud dorazí za poslední květináč a žádné další květináče již nevidí, řazení je dokončeno.

> Gnome Sort is based on the technique used by the standard Dutch Garden Gnome (tuinkabouter). 
> Here is how a garden gnome sorts a line of flower pots. 
> Basically, he looks at the flower pot next to him and the previous one; if they are in the right order he steps one pot forward, otherwise, he swaps them and steps one pot backward. 
> Boundary conditions: if there is no previous pot, he steps forwards; if there is no pot next to him, he is done.
> *Dick Grune*

### Implementace (Java)

```include:java
GnomeSort.java
```

### Ukázkový běh

| Pole | Pozice trpaslíka | Operace
|---|---|---
| *🤠 5* 3 2 4 | 0 | počátek pole - posun vpřed
| *5 🤠 3*, 2, 4 | 1 | špatné pořadí - prohodit, posun vzad
| *🤠 3* 5 2 4 | 0 | počátek pole - posun vpřed
| *3 🤠 5* 2 4 | 1 | správné pořadí, posun vpřed
| 3 *5 🤠 2* 4 | 2 | špatné pořadí - prohodit, posun vzad
| *3 🤠 2* 5 4 | 1 | špatné pořadí - prohodit, posun vzad
| *🤠 2* 3 5 4 | 0 | počátek pole - posun vpřed
| *2 🤠 3* 5 4 | 1 | správné pořadí, posun vpřed
| 2 *3 🤠 5* 4 | 2 | správné pořadí, posun vpřed
| 2 3 *5 🤠 4* | 3 | špatné pořadí - prohodit, posun vzad
| 2 *3 🤠 4* 5 | 2 | správné pořadí, posun vpřed
| 2 3 *4 🤠 5* | 3 | správné pořadí, posun vpřed
| 2 3 4 *5 🤠* | 4 | konec pole - řazení dokončeno