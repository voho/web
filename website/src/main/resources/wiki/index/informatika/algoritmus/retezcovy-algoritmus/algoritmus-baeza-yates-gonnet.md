## Algoritmus Baeza-Yates-Gonnet (Bitap)

Tento algoritmus pro vyhledávání řetězce je velmi podobný triviálnímu vyhledávacímu algoritmu až na to, že se nesnaží minimalizovat počet porovnávání, ale jejich efektivitu.
Proto v hojné míře používá [bitové operace](wiki/bit): logický součin, součet, porovnávání a posuvy.
Z toho důvodu má algoritmus určitá omezení, která plynou z velikosti celočíselných typů podporovaných danou platformou a [programovacím jazykem](wiki/jazyk).

Tento algoritmus je vhodný zejména v situacích, kdy je délka vyhledáváného řetězce a velikost abecedy relativně malá k textu, například se jedná o [kódování ASCII](wiki/ascii) a vyhledávání slov v běžném textu.
Jeho výhodou je především zcela předvídatelná rychlost, která není závislá na struktuře textu.

Vyhledávaný řetězec se pro zjednodušení nazývá jako **vzor** (pattern).

### Kroky algoritmu

#### Předzpracování

Abychom mohli algoritmus spustit, musíme připravit tabulku bitových masek pro všechny znaky vstupní abecedy.
Tabulka znaků bude obsahovat řádek pro každý znak a jeden řádek navíc. Počet sloupců bude odpovídat délce vzoru.
V této tabulce se nejprve všechny řádky vyplní jedničkami (`111...111`).
Poté se umístí nuly na řádky znaků, které se nachází ve vzoru, a to na pozice, na kterých se znaky ve vzoru vyskytují - avšak obráceně (znaky čteme odzadu).

Například pro vyhledávání řetězce *LLO* s použitím sedmibitového kódování ASCII (0 - 127) bude mít tabulka 128 řádků tabulka vypadat takto:
 
| Řádek tabulky | Odpovídající znak | Bitové pole
|---|---|---
| 0-75 | null - K | `1111111`
| 76 | **L** (ve vzoru na dvou prvních pozicích) | `1111100` 
| 77-78 | M, N | `1111111`
| 79 | **O** (ve vzoru na třetí pozici) | `1111011`
| 80-127 | P - del | `1111111` 
| 128 | (prázdný) | `1111111`

Význam těchto bitů je zhruba následující: nula reprezentuje shodu, jednička neshodu.

#### Vyhledávání

Nejprve připravíme pomocné bitové pole €R€, které inicializujeme na bitovou inverzi jedničky (€\lnot 1 = 111\ldots110€).

Pro každý znak textu provedeme následující operace:

- €s€ = řádek masky odpovídající aktuálnímu znaku
- €R = R \lor s€
- bitový posuv €R€ o jeden bit vlevo (vpravo se objeví nula)

Nalezení či nenalezení se vyhodnotí následovně:

- €m€ = délka vzoru
- €z€ = 1
- bitový posuv €z€ o €m€ bitů vlevo
- pokud €R \land z = 0€, tak byl vzor nalezen na pozici  €i - m + 1€, kde *i* je aktuální pozice v textu

### Implementace

První implementace využívá nízkoúrovňové binární operace a proto je omezena délka vyhledávaného řetězce na délku celočíselného typu *long*.

```java
public int find(char[] text, char[] pattern) {
    int m = pattern.length;

    if (m < 1 || m > 63) {
        throw new IllegalArgumentException("Pattern has to be 1-63 characters long.");
    }

    long mask[] = new long[Character.MAX_VALUE + 1];

    for (int iMask = 0; iMask < mask.length; iMask++) {
        // ~0 = 111...111
        mask[iMask] = ~0;
    }

    for (int iPattern = 0; iPattern < m; iPattern++) {
        int iMask = (int) pattern[iPattern];
        // ~(1 << 0) = 1...11111110
        // ~(1 << 1) = 1...11111101
        // ~(1 << 2) = 1...11111011
        // ~(1 << 3) = 1...11110111
        mask[iMask] &= ~(1L << iPattern);
    }

    // = 1...1110
    long R = ~1;
    // = 1...0000
    long lastPatternBit = 1L << m;

    for (int iText = 0; iText < text.length; ++iText) {
        // logický součet (OR)
        R |= mask[text[iText]];
        // bitový posun vlevo
        R <<= 1;

        if ((R & lastPatternBit) == 0) {
            // nalezeno
            return iText - m + 1;
        }
    }

    // nenalezeno
    return -1;
}
```

### Složitost

[Asymptotická složitost](wiki/asymptoticka-slozitost) algoritmu je € O(t + a) €, kde €t€ je délka textu a €a€ počet znaků ve vstupní abecedě.
  
### Reference

- https://fr.wikipedia.org/wiki/Algorithme_de_Baeza-Yates-Gonnet
- http://www.akira.ruc.dk/~keld/teaching/algoritmedesign_f08/Artikler/09/Baeza92.pdf
- https://www.dcc.uchile.cl/~gnavarro/ps/cpm96.pdf
- http://www.cs.au.dk/~cstorm/courses/StrAlg_f13/slides/Shift-and-OR.pdf
