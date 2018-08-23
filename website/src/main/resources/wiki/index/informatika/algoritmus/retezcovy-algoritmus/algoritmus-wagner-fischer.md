## Algoritmus Wagner-Fischer

Algoritmus Wagner-Fischer je řetězcový algoritmus pro výpočet **Levenshteinovy vzdálenosti** mezi dvěma řetězci.
Je založený na dynamickém programování.

1. €n€ = délka řetězce €s€
1. €m€ = délka řetězce €t€
1. Vytvoř matici s €m+1€ řádky a €n+1€ sloupci.
1. Do prvního řádku matice vlož posloupnost 0 až €n+1€.
1. Do prvního sloupce matice vlož posloupnost 0 až €m+1€.
1. Pro každý znak řetězce €s€ (€i \in \langle1,n\rangle€):
    1. Pro každý znak řetězce €t€ (€j \in \langle1,m\rangle€):
        1. Pokud jsou znaky na těchto pozicích shodné, pak €cost = 0€, jinak €cost = 1€.
            1. Do matice na pozici €[i,j]€ vlož nejmenší z těchto hodnot:
                1. Hodnotu políčka o jednu pozici výše + 1.
                1. Hodnotu políčka o jednu pozici vlevo + 1.
                1. Hodnotu políčka diagonály (vlevo nahoře) + €cost€.
1. Výsledek je v matici na pozici €(n,m)€.

### Levenshteinova vzdálenost

![Vladimir Levenshtein](levenshtein.jpg){.right}

Levenshteinova vzdálenost (Vladimir Levenshtein, publikováno v roce 1965) mezi dvěma řetězci €s, t€ vyjadřuje míru jejich odlišnosti. 
Čím je levenshteinova vzdálenost větší, tím jsou řetězce odlišnější.

Tato vzdálenost je definovaná jako nejmenší počet operací, které je nutné provést s řetězcem €s€, abychom dostali řetězec €t€.
Povolené operace jsou vložení znaku, odebrání znaku a nahrazení znaku. 
Levenstheinova vzdálenost je symetrická pouze v případě, že všechny tyto operace mají stejnou váhu.

Existují i varianty, které přikládají odlišné váhy různým operacím nebo kombinacím znaků při nahrazení.
Tímto způsobem je možné lépe pracovat například s překlepy na klávesnici: nahrazení znaků *P* a *O* dáme menší váhu, než nahrazení znaků *P* a *X* a tím pádem se vzájemná vzdálenost takových slov sníží.

Levenstheinova vzdálenost se využívá například při implementaci kontroly pravopisu, analýze DNA, detekce plagiarismu nebo "fuzzy" vyhledávání podobných slov (např. s překlepy).

#### Příklad: DOG &rarr; BUGGY

Levenshteinova vzdálenost mezi slovy *DOG* a *BUGGY* je 4.
Můžeme například nahradit *D*->*B*, nahradit *O*->*U*, ponechat *G* a přidat *GY*.
To jsou dva znaky nahrazené a dva znaky přidané, výsledný počet operací je tedy 4. 

| | |B|U|G|G|Y|
|---|---|---|---|---|---|---
| |*0*|*1*| 2 | 3 | 4 | 5 |
|D| 1 | 1 |*2*| 3 | 4 | 5 |
|O| 2 | 2 | 2 |*3*| 4 | 5 |
|G| 3 | 3 | 3 | 2 |*3*|*=4*|

#### Příklad: NANNY &rarr; MAN

Levenshteinova vzdálenost mezi slovy *NANNY* a *MAN* je 3.
Můžeme například nahradit *N*->*M*, ponechat *AN* a smazat *NY*.
To je jeden znak nahrazený a dva smazané, výsledný počet operací je tedy 3.

| | |M|A|N|
|---|---|---|---|---|
| |*0*| 1 | 2 | 3 |
|N| 1 |*1*| 2 | 2 |
|A| 2 | 2 |*1*| 2 |
|N| 3 | 3 |*2*| 1 |
|N| 4 | 4 | 3 |*2*|
|Y| 5 | 5 | 4 |*=3*|

### Implementace

Pro snadnější pochopení je lepší začít původní verzí algoritmu s maticí.

```include:java
levenshtein/LevenshteinWagnerFischer.java
```

Protože jsou vždy k výpočtu potřeba jen dva poslední řádky matice, není nutné ukládat celou matici a ušetřit nějakou pameť:

```include:java
levenshtein/LevenshteinWagnerFischerOptimized.java
```

### Reference

- https://people.cs.pitt.edu/~kirk/cs1501/Pruhs/Spring2006/assignments/editdistance/Levenshtein%20Distance.htm
- http://odur.let.rug.nl/~kleiweg/lev/