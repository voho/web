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

- https://github.com/voho/examples/tree/master/lz77

#### Kódové slovo

```java
public class LZ77Codeword {
    private final int i;
    private final int j;
    private final char x;

    public LZ77Codeword(final int i, final int j, final char x) {
        this.i = i;
        this.j = j;
        this.x = x;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public char getX() {
        return x;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d,%s)", i, j, x == 0 ? "<eof>" : x);
    }
}
```

#### Komprese

```java
public static List<LZ77Codeword> encode(final char[] input, final int backBufferSize, final int frontBufferSize) {
    final List<LZ77Codeword> result = new LinkedList<>();

    int middle = 0;
    int start = middle - backBufferSize;
    int end = middle + frontBufferSize;

    while (middle < input.length) {
        // prepare necessary structures to perform lookup
        final String backBuffer = safeSubString(input, start, middle);
        final String frontBuffer = safeSubString(input, middle, end);
        final int maxPrefixLength = frontBuffer.length();
        final String buffer = safeSubString(input, start, end);

        // initialize the encoded word
        int prefixIndex = 0;
        int prefixLength = 0;
        char prefixFollow = safeSubChar(input, middle);

        for (int i = 1; i <= maxPrefixLength; i++) {
            // create prefix of length "i"
            final String newPrefix = safeSubString(input, middle, middle + i);
            // find prefix in the whole window
            final int newPrefixIndex = buffer.indexOf(newPrefix);
            // check we have found it
            final boolean prefixFoundInWindow = newPrefixIndex != -1;
            // check it starts in the back buffer
            final boolean prefixStartsInBackBuffer = newPrefixIndex < backBuffer.length();

            if (prefixFoundInWindow && prefixStartsInBackBuffer) {
                // replace the best found prefix with the new longer one
                prefixIndex = backBuffer.length() - newPrefixIndex;
                prefixLength = i;
                prefixFollow = safeSubChar(input, middle + i);
            }
        }

        final int skip = prefixLength + 1;
        start += skip;
        middle += skip;
        end += skip;

        result.add(new LZ77Codeword(prefixIndex, prefixLength, prefixFollow));
    }

    return result;
}
```

#### Dekomprese

```java
public static char[] decode(final List<LZ77Codeword> input) {
    final StringBuilder buffer = new StringBuilder();

    for (final LZ77Codeword word : input) {
        final int numCharsToGoBack = word.getI();
        final int numCharsToCopy = word.getJ();
        final char characterToAppend = word.getX();

        if (numCharsToCopy > 0) {
            final int firstCopyIndex = buffer.length() - numCharsToGoBack;

            for (int i = 0; i < numCharsToCopy; i++) {
                final char charToCopy = buffer.charAt(firstCopyIndex + i);
                buffer.append(charToCopy);
            }
        }

        if (characterToAppend != 0) {
            buffer.append(characterToAppend);
        }
    }

    return buffer.toString().toCharArray();
}
```

#### Pomocné metody

```java
private static char safeSubChar(final char[] input, final int index) {
    if (index >= 0 && index <= input.length - 1) {
        return input[index];
    }

    return 0;
}
```

```java
private static String safeSubString(final char[] input, final int start, final int end) {
    final StringBuilder buffer = new StringBuilder();

    for (int i = start; i < end; i++) {
        if (i >= 0 && i <= input.length - 1) {
            buffer.append(input[i]);
        }
    }

    return buffer.toString();
}
```

### Reference

- předmět X36KOD na FEL ČVUT
- http://www.stringology.org/DataCompression/lz77/index_cs.html
- http://ksvi.mff.cuni.cz/~dvorak/vyuka/NSWI072/LZ77.pdf