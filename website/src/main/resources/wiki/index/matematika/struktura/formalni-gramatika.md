## Formální gramatika

Jednou z možností, jak popsat [formální jazyk](wiki/formalni-jazyk), je použití formální gramatiky. Jedná se v podstatě o [množinu](wiki/mnozina) syntaktických pravidel, kterými lze ověřit, zda nějaké slovo do jazyka popsaného danou gramatikou patří, či nikoliv, a teoreticky také všechna slova vygenerovat, pokud je jejich množina konečná.

Formální gramatika €G€ je čtveřice €(N, T, P, S)€, kde €N€ je konečná [množina](wiki/mnozina) **neterminálních symbolů** (neterminálů), €T: T \cap N = \varnothing€ je konečná množina **terminálních symbolů** (terminálů), €P€ je konečná množina odvozovacích pravidel ve tvaru €(T \cup N)^\* N (T \cup N)^\* \longrightarrow (T \cup N)^\* € a €S \in N€ je počáteční symbol.

Pro zpřehlednění a zkrácení zápisu se pravidla často zapisují v tzv. [Backus-Naurově formě](wiki/backus-naur), která umožňuje příbuzná pravidla sloučit do jednoho řádku.

### Příklady

**Terminály** budou pro přehlednost psány malými písmeny, **neterminály** velkými.

#### Jednoduché výroky

Definujme gramatiku, která generuje jednoduché výroky. Množina terminálů je **malý, velký, pes, kocour, běží, leží, spí**, množina neterminálů **VÝROK, PŘÍVLASTEK, PODMĚT, PŘÍSUDEK** a počáteční symbol je **VÝROK**. Pravidla budou následující:

1. **VÝROK** &rarr; **PŘÍVLASTEK** **PODMĚT** **PŘÍSUDEK**
1. **PŘÍVLASTEK** &rarr; *malý*
1. **PŘÍVLASTEK** &rarr; *velký*
1. **PODMĚT** &rarr; *pes*
1. **PODMĚT** &rarr; *kocour*
1. **PŘÍSUDEK** &rarr; *běží*
1. **PŘÍSUDEK** &rarr; *leží*
1. **PŘÍSUDEK** &rarr; *spí*

Nyní zkusíme vygenerovat nějaké výroky. Začneme u počátečního symbolu a postupně aplikejeme některé z možných pravidel:

1. **VÝROK**
1. **PŘÍVLASTEK PODMĚT PŘÍSUDEK** (použité pravidlo: 1)
1. *malý* **PODMĚT PŘÍSUDEK** (použité pravidlo: 2)
1. *malý* *pes* **PŘÍSUDEK** (použité pravidlo: 4)
1. *malý* *pes* *spí* (použité pravidlo: 8)

### Aritmetické výrazy

Definujme gramatiku, která generuje aritmetické výrazy. Množina terminálů je (, ), 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, +, -, množina neterminálů VÝRAZ, ČÍSLO, ČÍSLICE, OPERÁTOR a počáteční symbol bude VÝRAZ. Pravidla budou následující:

1. **VÝRAZ** &rarr; **VÝRAZ** **OPERÁTOR** **VÝRAZ**
1. **VÝRAZ** &rarr; **ČÍSLO**
1. **VÝRAZ** &rarr; *(* **VÝRAZ** *)*
1. **ČÍSLO** &rarr; **ČÍSLICE** **ČÍSLO**
1. **ČÍSLO** &rarr; **ČÍSLICE**
1. **OPERÁTOR** &rarr; *+*
1. **OPERÁTOR** &rarr; *-*
1. **ČÍSLICE** &rarr; *0*
1. **ČÍSLICE** &rarr; *1*
1. **ČÍSLICE** &rarr; *2*
1. **ČÍSLICE** &rarr; *3*
1. **ČÍSLICE** &rarr; *4*
1. **ČÍSLICE** &rarr; *5*
1. **ČÍSLICE** &rarr; *6*
1. **ČÍSLICE** &rarr; *7*
1. **ČÍSLICE** &rarr; *8*
1. **ČÍSLICE** &rarr; *9*

Opět zkusíme vygenerovat nějaké výroky. Začneme u počátečního symbolu a postupně aplikujeme některé z možných pravidel:

1. **VÝRAZ**
1. **VÝRAZ** **OPERÁTOR** **VÝRAZ** (použité pravidlo: 1)
1. *(* **VÝRAZ** *)* **OPERÁTOR** **VÝRAZ** (použité pravidlo: 3)
1. *(* **VÝRAZ** **OPERÁTOR** **VÝRAZ** *)* **OPERÁTOR** **VÝRAZ** (použité pravidlo: 1)
1. *(* **ČÍSLO** **OPERÁTOR** **VÝRAZ** *)* **OPERÁTOR** **VÝRAZ** (použité pravidlo: 2)
1. *(* **ČÍSLO** **OPERÁTOR** **ČÍSLO** *)* **OPERÁTOR** **VÝRAZ** (použité pravidlo: 2)
1. *(* **ČÍSLO** **OPERÁTOR** **ČÍSLO** *)* **OPERÁTOR** **ČÍSLO** (použité pravidlo: 2)
1. *(* *5* **OPERÁTOR** **ČÍSLO** *)* **OPERÁTOR** **ČÍSLO** (použité pravidlo: 5)
1. *(* *5* **OPERÁTOR** **ČÍSLICE** **ČÍSLO** *)* **OPERÁTOR** **ČÍSLO** (použité pravidlo: 4)
1. *(* *5* **OPERÁTOR** *1* **ČÍSLO** *)* **OPERÁTOR** **ČÍSLO** (použité pravidlo: 9)
1. *(* *5* **OPERÁTOR** *1* **ČÍSLICE** *)* **OPERÁTOR** **ČÍSLO** (použité pravidlo: 5)
1. *(* *5* **OPERÁTOR** *14* *)* **OPERÁTOR** **ČÍSLO** (použité pravidlo: 12)
1. *(* *5* **OPERÁTOR** *14* *)* **OPERÁTOR** **ČÍSLICE** (použité pravidlo: 5)
1. *(* *5* **OPERÁTOR** *14* *)* **OPERÁTOR** *5* (použité pravidlo: 13)
1. *(* *5* *+* *14* *)* **OPERÁTOR** *5* (použité pravidlo: 6)
1. *(* *5* *+* *14* *)* *-* *5* (použité pravidlo: 7