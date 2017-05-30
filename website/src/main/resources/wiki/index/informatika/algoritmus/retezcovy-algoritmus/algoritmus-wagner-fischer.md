## Algoritmus Wagner-Fischer

Algoritmus Wagner-Fischer je řetězcový algoritmus pro výpočet **Levenshteinovy vzdálenosti** mezi dvěma řetězci.
Je založený na dynamickém programování.

1. €n€ = délka řetězce €s€
1. €m€ = délka řetězce €t€
1. Vytvoř matici s €m+1€ řádky a €n+1€ sloupci.
1. Do prvního řádku matice vlož posloupnost 0, 1, 2, 3 až €n+1€.
1. Do prvního sloupce matice vlož posloupnost 0, 1, 2, 3 až €m+1€.
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

Levenshteinova vzdálenost (Vladimir Levenshtein, 1965) mezi dvěma řetězci €s,t€ vyjadřuje míru odlišnosti dvou řetězců. 
Čím je levenshteinova vzdálenost větší, tím jsou řetězce odlišnější.

Tato vzdálenost je definovaná jako nejmenší počet operací, které je nutné provést s řetězcem €s€, abychom dostali řetězec €t€.
Povolené operace jsou vložení znaku, odebrání znaku a nahrazení znaku. 
Levenstheinova vzdálenost je symetrická pouze v případě, že všechny tyto operace mají stejnou váhu.

Existují i varianty, které přikládají odlišné váhy různým operacím nebo kombinacím znaků při nahrazení.
Tímto způsobem je možné lépe pracovat například s překlepy na klávesnici: nahrazení znaků *P* a *O* dáme menší váhu, než nahrazení znaků *P* a *X* a tím pádem se vzájemná vzdálenost takových slov sníží.

Levenstheinova vzdálenost se využívá například při implementaci kontroly pravopisu, analýze DNA, detekce plagiarismu nebo "fuzzy" vyhledávání podobných slov (např. s překlepy).

#### Příklad: DOG &rarr; BUGGY

Levenshteinova vzdálenost mezi slovy *DOG* a *BUGGY* je 4.
Můžeme například nahradit *D*->*B* (+1), nahradit *O*->*U* (+1), ponechat *G* a přidat *GY* (+2).

| | |B|U|G|G|Y|
|---|---|---|---|---|---|---
| |*0*|*1*| 2 | 3 | 4 | 5 |
|D| 1 | 1 |*2*| 3 | 4 | 5 |
|O| 2 | 2 | 2 |*3*| 4 | 5 |
|G| 3 | 3 | 3 | 2 |*3*|*(4)*|

#### Příklad: NANNY &rarr; MAN

Levenshteinova vzdálenost mezi slovy *NANNY* a *MAN* je 3.
Můžeme například nahradit *N*->*M* (+1), ponechat *AN* a smazat *NY* (+2).

| | |M|A|N|
|---|---|---|---|---|
| |*0*| 1 | 2 | 3 |
|N| 1 |*1*| 2 | 2 |
|A| 2 | 2 |*1*| 2 |
|N| 3 | 3 |*2*| 1 |
|N| 4 | 4 | 3 |*2*|
|Y| 5 | 5 | 4 |*(3)*|

### Implementace

Pro snadnější pochopení je lepší začít původní verzí algoritmu s maticí.

```java
class WagnerFischer {
    public static int getLevenshteinDistance(final char[] s, final char[] t) {
        if (s.length == 0) {
            // edge case: S is empty
            return t.length;
        } else if (t.length == 0) {
            // edge case: T is empty
            return s.length;
        }

        final int[][] d = new int[s.length + 1][t.length + 1];

        for (int i = 0; i <= s.length; i++) {
            // reset first row
            d[i][0] = i;
        }

        for (int i = 0; i <= t.length; i++) {
            // reset first column
            d[0][i] = i;
        }

        for (int it = 1; it <= t.length; it++) {
            for (int is = 1; is <= s.length; is++) {
                // up = deletion
                final int costDeletion = d[is - 1][it] + 1;
                // left = insertion
                final int costInsertion = d[is][it - 1] + 1;
                // cost for replacing the character in case it is different
                final int costReplacement = (s[is - 1] == t[it - 1]) ? 0 : 1;
                // diagonal = substitution (if necessary)
                final int costSubstitution = d[is - 1][it - 1] + costReplacement;

                d[is][it] = min(costDeletion, costInsertion, costSubstitution);
            }
        }

        return d[s.length][t.length];
    }

    private static int min(final int a, final int b, final int c) {
        return Math.min(a, Math.min(b, c));
    }
}
```

Protože jsou vždy k výpočtu potřeba jen dva poslední řádky matice, není nutné ukládat celou matici a ušetřit nějakou pameť:

```java
class WagnerFischerOptimized {
    public static int getLevenshteinDistance(final char[] s, final char[] t) {
        if (t.length > s.length) {
            // to save memory, we want the shorter string to be the second parameter
            // (as long as the distance is symmetrical, we do not care about the order)
            return getLevenshteinDistance(t, s);
        }

        int[] previousRow = new int[t.length + 1];
        int[] currentRow = new int[t.length + 1];

        for (int i = 0; i < previousRow.length; i++) {
            previousRow[i] = i;
        }

        for (int is = 1; is <= s.length; is++) {
            currentRow[0] = is;

            for (int it = 1; it <= t.length; it++) {
                // up = deletion
                final int costDeletion = previousRow[it] + 1;
                // left = insertion
                final int costInsertion = currentRow[it - 1] + 1;
                // cost for replacing the character in case it is different
                final int costReplacement = (s[is - 1] == t[it - 1]) ? 0 : 1;
                // diagonal = substitution (if necessary)
                final int costSubstitution = previousRow[it - 1] + costReplacement;

                currentRow[it] = min(costDeletion, costInsertion, costSubstitution);
            }

            // swap arrays (currentRow will be re-used and overwritten)
            final int[] temp = previousRow;
            previousRow = currentRow;
            currentRow = temp;
        }

        return previousRow[t.length];
    }
}
```

### Reference

- https://people.cs.pitt.edu/~kirk/cs1501/Pruhs/Spring2006/assignments/editdistance/Levenshtein%20Distance.htm
- http://odur.let.rug.nl/~kleiweg/lev/