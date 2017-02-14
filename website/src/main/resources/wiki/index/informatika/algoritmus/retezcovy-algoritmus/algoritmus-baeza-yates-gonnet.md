## Algoritmus Baeza-Yates-Gonnet (Bitap)

!TODO!

Tento algoritmus pro vyhledávání řetězců je velmi podobný triviálnímu vyhledávacímu algoritmu až na to, že se nesnaží minimalizovat počet porovnávání, ale jejich efektivitu.
Mezi nejefektivnější operace na počítačích patří [bitové operace](wiki/bit) - logický součin, součet, porovnávání a posuvy.
Proto algoritmus pro maximální efektivitu používá nativní celočíselné typy podporované danou platformou a [programovacím jazykem](wiki/jazyk).
Například pro [jazyk C](wiki/c) nebo [Java](wiki/java) to může být typ *long* o velikosti (délce) 64 bitů.

Tento algoritmus je vhodný zejména v situacích, kdy je délka vyhledáváného řetězce a velikost abecedy relativně malá k textu, například se jedná o [kódování ASCII](wiki/ascii) a vyhledávání slov v běžném textu.

Jeho výhodou je především zcela předvídatelná rychlost, protože ta není vůbec závislá na struktuře textu.

### Kroky algoritmu

#### Předzpracování

Abychom mohli využít bitové operace, musíme připravit maskovací tabulku s daty pro všechny znaky vstupní abecedy.
Tabulka znaků bude obsahovat řádek pro každý znak. 
V této tabulce se nejprve všechny řádky vyresetují na jedničky (`111...111`).
Pro všechny ostatní řádky je nutné znát, 

Například pro vyhledávání řetězce *LLO* s použitím kódování ASCII a osmi bitů bude tabulka vypadat takto:
 
| Řádek tabulky | Znak vzoru | Bitové pole | Poznámka
|---|---|---|---
| 0-75 | |   `11111111` | (znak se nenachází ve vyhledávaném řetězci)
| 76 | L |   `00001000` | (*LLO* obráceně = O*LL*)
| 77-78 | |  `11111111` | (znak se nenachází ve vyhledávaném řetězci)
| 79 | O |   `00000011` | (*LLO* obráceně = *O*LL)
| 80-255 | | `11111111` | (znak se nenachází ve vyhledávaném řetězci)

### Příklad

Budeme vyhledávat řetězec *LLO* v textu *HELLO WORLD* s použitím kódování ASCII.

Vyhledávání zahájíme inicializací tabulky. 
Tabulka bude obsahovat jeden řádek pro každý znak, bude tedy mít 255 řádků a 4 sloupce (délka vyhledávaného řetězce + 1).
Řádky znaků ve vzoru dostanou XXX.
Ostatní řádky, které se vzoru nenachází, dostanou kód 1111.

| Řádek tabulky | Znak vzoru | Bitové pole | Poznámka
|---|---|---|---
| 0-75 | | `1111` | (znak se nenachází ve vyhledávaném řetězci)
| 76 | L | `1000` | (*LLO* obráceně = O*LL*)
| 77-78 | | `1111` | (znak se nenachází ve vyhledávaném řetězci)
| 79 | O | `011` | (*LLO* obráceně = *O*LL)
| 80-255 | | `1111` | (znak se nenachází ve vyhledávaném řetězci)



| Znaky textu |h|e|l|l|o| |w|o|r|l|d|
|---|---|---|---|---|---|---|---|---|---|---|---
|Kód znaku|1111|1111|100|100|011|1111|1111|011|1111|100|1111

### Implementace

První implementace využívá nízkoúrovňové binární operace a tak je omezena délka slova.

```java
private int find(char[] text, char[] pattern) {
        int m = pattern.length;
        if (m == 0 || m > 63) {
            throw new IllegalArgumentException("Pattern has to be 1-63 characters long.");
        }
        long pattern_mask[] = new long[Character.MAX_VALUE + 1];
        // = 1...1110
        long patternDiffRev = ~1;
        // = 0...010...0
        long lastPatternBit = 1L << m;
        // inicializace bitových masek masky pro všechny znaky
        for (int iChar = 0; iChar <= Character.MAX_VALUE; ++iChar)
            pattern_mask[iChar] = ~0;
        for (int iPattern = 0; iPattern < m; ++iPattern) {
            // = 0...010...0
            char patternChar = pattern[iPattern];
            pattern_mask[patternChar] &= ~(1L << iPattern);
        }
        for (int iText = 0; iText < text.length; ++iText) {
            // logický součet (OR)
            patternDiffRev |= pattern_mask[text[iText]];
            // bitový posun vlevo
            patternDiffRev <<= 1;
            if ((patternDiffRev & lastPatternBit) == 0) {
                // nalezeno
                return iText - m + 1;
            }
        }
        // nenalezeno
        return -1;
    }
```

S pomocí třídy *javadoc:java.util.BitSet* je možné odstranit omezení na délku vzoru za cenu drobného zpomalení.

```java
public int find(char[] text, char[] pattern) {
    // 0...0001
    BitSet patternDiffRev = new BitSet(pattern.length + 1);
    patternDiffRev.set(0);

    for (int iText = 0; iText < text.length; iText++) {
        // pro všechny znaky textu:
        for (int iPattern = pattern.length; iPattern >= 1; iPattern--) {
            // pro všechny znaky vyhledávaného řetězce zprava doleva:
            int iPatternNextChar = iPattern - 1;
            boolean nextCharDiff = patternDiffRev.get(iPatternNextChar);
            boolean currentCharDiff = text[iText] == pattern[iPatternNextChar];
            patternDiffRev.set(iPattern, nextCharDiff && currentCharDiff);
        }
        if (patternDiffRev.get(pattern.length)) {
            // nalezeno
            return iText - pattern.length + 1;
        }
    }
    // nenalezeno
    return -1;
}
```

### Složitost

[Asymptotická složitost](wiki/asymptoticka-slozitost) algoritmu je € O(m + n) €, kde €m€ je délka vyhledávaného řetězce a €n€ počet znaků ve vstupní abecedě.
  
### Reference

- https://fr.wikipedia.org/wiki/Algorithme_de_Baeza-Yates-Gonnet
- http://www.akira.ruc.dk/~keld/teaching/algoritmedesign_f08/Artikler/09/Baeza92.pdf
- http://www.cs.au.dk/~cstorm/courses/StrAlg_f13/slides/Shift-and-OR.pdf