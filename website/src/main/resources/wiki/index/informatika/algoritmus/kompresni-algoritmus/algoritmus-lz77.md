## Kompresní algoritmus LZ77

Kompresní algoritmus LZ77 (autoři Abraham Lempel, Jacob Ziv, 1977) je slovníková kompresní metoda založena na tzv. **klouzavém okénku**. Okénko je rozděleno na dvě části - **prohlížecí okno** (search buffer, back buffer) a **aktuální okno** (look-ahead buffer, front buffer). Jejich velikost je konstantní a vzhledem ke vstupním datům malá. Délka aktuálního okna by měla být vždy menší nebo rovna délce prohlížecího okna. Algoritmus pracuje pouze s daty v těchto okéncích, a tak paměťová náročnost algoritmu neroste s délkou vstupního řetězce. 

Algoritmus je inicializován tak, že je prohlížecí okno prázdné a do aktuálního okna se načte začátek vstupu. Potom se v každém kroku vyhledá nejdelší slovo, které začíná v prohlížecím okně a je shodné s nějakou předponou (prefixem) slova v aktuálním okně. Po nalezení je slovo zakódováno trojicí €(i,j,z)€, kde €i€ je vzdálenost začátku slova od začátku aktuálního okna (směrem k začátku okna), €j€ je délka slova a €z€ je první znak následující po slově. Celé okno je následně posunuto o €j+1€ znaků doprava. Toto se opakuje, dokud existuje nezakódovaný vstup (tzn. aktuální okno tedy není prázdné).

Slovník je v každém okamžiku tvořen teoreticky všemi řetězci, které začínají v prohlížecím okně. Pokud je například v prohlížecím okně obsažen text *AB* a v aktuálním okně text *CD*, slovník obsahuje slova *A* (2, 1), *B* (1,1), *AB* (2,2), *BC* (1,2), *ABC* (2,3), *BCD* (1,3) a *ABCD* (2, 4).

Kompresní metoda LZ77 je slovníková, jednoprůchodová, adaptivní a asymetrická - komprese je mnohem náročnější než dekomprese.

### Příklad

#### Komprese řetězce ABRAKADABRA

Velikost prohlížecího okna nechť jsou 4 znaky a aktuálního okna 3 znaky.

| Krok | Prohlížecí okno | Aktuální okno | Zbytek vstupu | Výstup | Posun
|---|---|---|---|---|---
| Inicializace | ____ | *ABR* | AKADABRA | - | -
| 1 | ___A | *BRA* | KADABRA | (0, 0, A) | 1
| 2 | __AB | *RAK* | ADABRA | (0, 0, B) | 1
| 3 | _ABR | *AKA* | DABRA | (0, 0, R) | 1
| 4 | BRAK | *ADA* | BRA | (3, 1, K) | 2
| 5 | AKAD | *ABR* | A | (2, 1, D) | 2
| 6 | ADAB | *RA_* |  | (4, 1, B) | 2
| 7 | DABR | *A__* |  | (0, 0, R) | 1
| 8 | ABRA | *___* | | (3, 1, konec) | 2

#### Zpětná dekomprese

| Krok | Vstup | Buffer | Přidáno | Výstup
|---|---|---|---|---
| 1 | (0, 0, A) | | *A* | A
| 2 | (0, 0, B) | A | *B* | AB
| 3 | (0, 0, R) | AB | *R* | ABR
| 4 | (3, 1, K) | ABR | *AK* | ABRAK
| 5 | (2, 1, D) | ABRAK | *AD* | ABRAKAD
| 6 | (4, 1, B) | ABRAKAD | *AB* | ABRAKADAB
| 7 | (0, 0, R) | ABRAKADAB | *R* | ABRAKADABR
| 8 | (3, 1, konec) | ABRAKADABR | *A*, konec | ABRAKADABRA

### Implementace (Java)

#### Kódové slovo

```include:java
LZ77Codeword.java
```

#### Komprese a dekomprese

```include:java
LZ77.java
```

### Reference

- předmět X36KOD na FEL ČVUT
- http://www.stringology.org/DataCompression/lz77/index_cs.html
- http://ksvi.mff.cuni.cz/~dvorak/vyuka/NSWI072/LZ77.pdf