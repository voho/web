## Gnome Sort (řazení trpaslíkem)

Gnome sort je řadící algoritmus, který si zakládá na své jednoduchosti. Základní princip algoritmu je podobný [insertion sort](wiki/insertion-sort), ale gnome sort je navržený spíše pro pobavení.

Algoritmus je **přirozený**, **stabilní** a jeho [asymptotická složitost](wiki/asymptoticka-slozitost) je € O(n^2) €.

Nyní si zkusme představit, jak zahradní trpaslíci řadí květináče podle velikosti. 

Trpaslík stojí mezi dvěma květináči a kontroluje, zda jsou správně seřazeny. 
Pokud ano, posune se o jednu pozici dopředu.
Pokud ne, květináče prohodí, a vrátí se o jednu pozici zpět.
Poté se celý postup opakuje od začátku.

Pokud se trpaslík dostane před první květináč, musí se logicky posunout o jednu pozici dopředu, aby vůbec měl co porovnávat.
A jestliže dorazí za květináč poslední, řazení je dokončeno.

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
| (*🤠 5*) 3 2 4 | 0 | počátek pole - posun vpřed
| (*5 🤠 3*), 2, 4 | 1 | špatné pořadí - prohodit, posun vzad
| (*🤠 3*) 5 2 4 | 0 | počátek pole - posun vpřed
| (*3 🤠 5*) 2 4 | 1 | správné pořadí, posun vpřed
| 3 (*5 🤠 2*) 4 | 2 | špatné pořadí - prohodit, posun vzad
| (*3 🤠 2*) 5 4 | 1 | špatné pořadí - prohodit, posun vzad
| (*🤠 2*) 3 5 4 | 0 | počátek pole - posun vpřed
| (*2 🤠 3*) 5 4 | 1 | správné pořadí, posun vpřed
| 2 (*3 🤠 5*) 4 | 2 | správné pořadí, posun vpřed
| 2 3 (*5 🤠 4*) | 3 | špatné pořadí - prohodit, posun vzad
| 2 (*3 🤠 4*) 5 | 2 | správné pořadí, posun vpřed
| 2 3 (*4 🤠 5*) | 3 | správné pořadí, posun vpřed
| 2 3 4 (*5 🤠)* | 4 | konec pole - řazení dokončeno