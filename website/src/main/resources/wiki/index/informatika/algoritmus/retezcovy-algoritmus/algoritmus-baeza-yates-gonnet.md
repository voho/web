## Algoritmus Baeza-Yates-Gonnet (Bitap)

!TODO!

Tento algoritmus pro vyhledávání řetězců je velmi podobný triviálnímu vyhledávacímu algoritmu až na to, že se nesnaží minimalizovat počet porovnávání, ale jejich efektivitu.
Mezi nejefektivnější operace na počítačích patří [bitové operace](wiki/bit) - logický součin, součet, porovnávání a posuvy.
Proto se zde pro maximální efektivitu používají nativní celočíselné typy podporované danou platformou a [programovacím jazykem](wiki/jazyk).
Například pro [jazyk C](wiki/c) nebo [Java](wiki/java) to může být typ *long* o velikosti (délce) 64 bitů.

Algoritmus je vhodný zejména pro situace, kdy je délka vyhledáváného řetězce a velikost abecedy relativně malá k textu, například se jedná o [kódování ASCII](wiki/ascii) a vyhledávání slov v běžném textu.

### Kroky algoritmu

1. **Předzpracování**
    1. Každému znaku vstupní abecedy přiřaď jedno celé číslo od *0* do *a-1*, kde *a* je počet znaků vstupní abecedy (např. *a* = 255 pro ASCII).
    1. Připrav matici bitů *t* o velikosti *a*, *p*, kde *a* je počet znaků vstupní abecedy a *p* je délka vyhledávaného řetězce.
    1. Pro *c* = 0 až *a-1* (včetně) a *j* = 1 až *p* (včetně): *t*(c, j) = 1
    1. Pro *j* = 1 až *p* (včetně): *t*(číslo přiřazené znaku vzoru na pozici j, j) = 0
1. **Hlavní cyklus**
    1. Připrav bitové pole *s* délky *p* + 1, kde *p* je délka vyhledávaného řetězce
    1. Toto bitové pole *s* vynuluj a na poslední pozici vlož 1.
    1. Pro *i* = 1 až *x*, kde *x* je délka řetězce, ve kterém se vyhledává
        1. bitové pole *s* posunout bitově doprava
        1. na bitovém poli *s* provést operaci OR s řádkem *t(x(i))*
        1. Pokud *s(p)* je nula, pak byl řetězec nalezen na pozici *i-p+1*

### Příklad

Budeme vyhledávat znaky *LLO* ve textu *HELLO WORLD* s použitím kódování ASCII.

Vyhledávání zahájíme inicializací tabulky. 
Je třeba si uvědomit, že binární řetězce obvykle píšeme od nejvyššího bitu po nejnižší, a tak je bit s indexem 0 napravo.

| Znak vzoru | Bitové pole | Poznámka
|---|---|---
| L | `100` | (*LLO* obráceně = O*LL*)
| O | `011` | (*LLO* obráceně = *O*LL)

| Znaky textu |h|e|l|l|o| |w|o|r|l|d|
|---|---|---|---|---|---|---|---|---|---|---|---
|Kód znaku|111|111|100|100|011|111|111|011|111|100|111

### Implementace

První implementace využívá nízkoúrovňové binární operace a tak je omezena délka slova.

```java
private static int bitap(char[] text, char[] pattern) {
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
            /** Update the bit array **/
            patternDiffRev |= pattern_mask[text[iText]];
            // bitový posuv vlevo o jeden bit
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
public int bitap(char[] text, char[] pattern) {
    int m = pattern.length;
    BitSet R = new BitSet(m + 1);
    // R[0] = 1: prázdný řetězec triviálně nalezen
    R.set(0);
    for (int i = 0; i < text.length; i++) {
        for (int k = m; k >= 1; k--) {
            // bitový posuv + AND
            R.set(k, R.get(k - 1) && (text[i] == pattern[k - 1]));
        }
        if (R.get(m)) {
            // nalezeno
            return i - m + 1;
        }
    }
    // nenalezeno
    return -1;
}
```
    
### Reference

- http://www.akira.ruc.dk/~keld/teaching/algoritmedesign_f08/Artikler/09/Baeza92.pdf
- http://www.cs.au.dk/~cstorm/courses/StrAlg_f13/slides/Shift-and-OR.pdf