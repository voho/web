## Algoritmus

> An algorithm must be seen to be believed. *Donald Knuth*

Slovo "algoritmus" pochází ze zkomoleného jména perského matematika jménem **Abú Abd Alláh Muhammad ibn Músá al-Chwárizmí** (otec Abdulláha, Mohameda, syn Mojžíšův, pocházející z města Chórézm). Od počátku 20. století se termín "algoritmus" používá ve smyslu univerzálního **návodu** či **postupu** pro řešení určité třídy úloh, který se skládá z konečné posloupnosti jednoznačně definovaných kroků.

### Vlastnosti

- **konečnost** - Každý algoritmus musí pro přípustná vstupní data skončit po provedení konečného množství kroků.
- **determinovanost** - Každý krok je jednoznačně definován. Tato vlastnost algoritmů si v podstatě vynutila vznik formálních [programovacích jazyků](wiki/jazyk), protože přirozený jazyk je pro zápis algoritmů příliš obecný a nepřesný.
- **hromadnost** - Vstupní data lze měnit a existuje celá množina možných vstupů. Tato vlastnost zapříčinila vznik polí a podobných lineárních [datových struktur](wiki/datova-struktura).
- **rezultativnost** - Algoritmus musí mít alespoň jeden měřitelný výstup, který je řešením zadaného problému. Bez splnění této podmínky by vlastně vůbec nemělo smysl jakékoliv algoritmy vymýšlet.

Efektivitu algoritmů pro řešení stejné třídy úloh na stejném výpočetním modelu můžeme vzájemně porovnávat pomocí matematického aparátu, který se nazývá [asymptotická složitost](wiki/asymptoticka-slozitost). Tento aparát umožňuje u některých algoritmů aproximovat funkce popisující závislosti mezi velikostí vstupu a potřebnými nároky algoritmu na časové (výpočetní čas) či systémové zdroje (náročnost na paměť). Tyto funkce jsou konstruovány na základě znalosti vnitřní struktury algoritmu.

### Základní koncepty

U všech algoritmů lze vysledovat základní koncept, který realizují.

- **jednoduchá rekurze** (simple recursive)
- **hledání s návratem** (backtracking) = rekurzivní prohledávání do hloubky
- **rozděl a panuj** (divide-and-conquer) = vícenásobná rekurze
- **dynamické programování** (dynamic programming) = řešení se skládá z řešení podproblémů, přičemž dílčí řešení se ukládají do paměti
- **hladové algoritmy** (greedy) = v každém kroku vždy zvolí lokální optimum v naději, že tak dojde k optimu globálnímu
- **branch-and-bound** = za běhu dynamicky vytváří strom podproblémů
- **hrubá síla** (brute force) = vyzkouší všechny možné varianty a z nich vybere řešení

### Reference

- http://www.mi.fu-berlin.de/wiki/pub/ABI/DiscretMathWS10/runtime.pdf