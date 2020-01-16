## Bloomův filtr (Bloom filter)

Bloomův filtr (Burton Howard Bloom, 1970) je datová struktura podobná [množině](wiki/mnozina). Umožňuje přidávat prvky a ověřovat přítomnost prvků v množině, to vše v konstantním čase a bez nutnosti uchovávat hodnoty prvků. To naprosto zásadním způsobem redukuje množství paměti potřebné pro uložení množiny. Nevýhodou je však možnost výskytu tzv. **falešných pozitiv** při dotazování, což znamená, že struktura občas považuje nějaký prvek za součást množiny i v případě, že do ní nepatří. Na druhou stranu, filtr má vždy pravdu ohledně nepřítomnosti prvku v množině. Dalším omezením je nemožnost prvky odebírat.

Navzdory zmíněným omezením je tato datová struktura velmi výhodná pro určitá použití:

- databázový index: pro indikaci toho, že se záznam nachází v určitém bloku na disku, ještě před tím, než se celý blok začne načítat
- torrent: pro zjištění, který peer má nebo nemá určitou část souboru
- kontrola pravopisu (spell-check): pro vyhledávání slov, které nejsou ve slovníku
- vyhodnocování pravidel (rule engine): pro vyhledávání pravidel, která nejsou splněna

Bloomův filtr je založen na bitovém poli a [hashovacích funkcích](wiki/java-hash), které vrací indexy do tohoto pole. Protože hashovací funkce nejsou dokonalé a velikost bitového pole je omezená, mohou odlišné prvky odkazovat na stejná místa v bitovém poli. V tom případě nejsme schopni s určitostí rozlišit, který z prvků v množině je, a který není. V případě podobné nejistoty se musí oba prvky považovat za potenciálně přítomné.

```dot:digraph
ratio=0.3;
table [shape=record,label="<b0>1|<b1>0|<b2>0|<b3>0|<b4>0|<b5>1|<b6>1|<b7>0"]  
"hash1(hello)=0"-> table:b0 
"hash2(hello)=3"-> table:b3 
"hash3(hello)=7"-> table:b7
"hash1(hello)=0" [shape=none,fillcolor=transparent]
"hash2(hello)=3" [shape=none,fillcolor=transparent]
"hash3(hello)=7" [shape=none,fillcolor=transparent]
```

```dot:digraph
ratio=0.3;
table2 [shape=record,label="<b0>1|<b1>0|<b2>0|<b3>0|<b4>0|<b5>1|<b6>1|<b7>0"]  
"hash1(world)=1"-> table2:b1
"hash2(world)=5"-> table2:b5
"hash3(world)=3"-> table2:b3
"hash1(world)=1" [shape=none,fillcolor=transparent]
"hash2(world)=5" [shape=none,fillcolor=transparent]
"hash3(world)=3" [shape=none,fillcolor=transparent]
```

### Operace

#### Přidání prvku

Pro každý prvek, který má být do množiny přidán, se nejprve vypočítají hodnoty všech hashovacích funkcí. Výstupem z těchto funkcí jsou indexy do bitového pole. Bity na všech těchto indexech se nastaví na hodnotu *1*. 

#### Odebrání prvku

Odebrání prvku není obecně možné - pokud bychom vynulovali bity nastavené jednotlivými hashovacími funkcemi, s jedním prvkem by mohlo dojít ke "smazání" i dalších prvků, jejichž hashe shodou okolností odkazují na stejná místa. Jednorázové odebrání sice lze implementovat pomocí druhého Bloomova filtru, který představuje množinu odebraných prvků, ale pokud prvek znovu přidáme, narazíme na stejný problém jako na začátku - z množiny "odebraných prvků" není možné odebírat prvky.

Existuje modifikace Bloomova filtru, která místo jednoho bitu používá počítadlo s omezeným rozsahem. Počítadla na daných pozicích se při přidání prvku inkrementují, při odebrání dekrementují. Z kapacitních důvodů mají tato počítadla typicky velmi omezený rozsah.

#### Vyhledání prvku

Operace pro vyhledání prvku má dva možné výsledky:

* prvek je **pravděpodobně** v množině 
* prvek není v množině 

Při vyhledávání prvku se pro daný prvek opět nejprve vypočítají hodnoty všech hashovacích funkcí. Výstupem z funkcí jsou indexy do bitového pole. Pokud jsou všechny bity na zvolených indexech rovny *1*, je prvek pravděpodobně v množině. Pokud je však alespoň jeden z těchto bitů roven *0*, prvek v množině zcela jistě není.

### Parametry

Pro dosažení maximální efektivity Bloomova filtru je důležité nastavit správnou velikost bitového pole, počet hashovacích funkcí a tyto funkce definovat.

#### Velikost bitového pole

Odhad optimální velikosti bitového pole na základě počtu vložených prvků (*n*) a požadované pravděpodobnosti falešného pozitiva (*p*):

€€ m \approx \frac{n \cdot \log\frac{1}{p}}{\log^22} €€

#### Hashovací funkce

Počet hashovacích funkcí má vliv na několik věcí:

- rychlost výpočtu (čím více funkcí, tím více se jich logicky musí spočítat)
- rychlost zaplnění (čím více funkcí, tím rychleji se bitové pole zaplní)
- pravděpodobnost falešného pozitiva (čím více funkcí, tím je pravděpodobnost nižší)

Odhad optimálního počtu hashovacích funkcí na základě velikosti bitového pole (*m*) a počtu vložených prvků (*n*):

€€ k \approx \frac{m}{n} \log 2 €€

Nejčastěji používané hashovací funkce:

- [MurMur](https://sites.google.com/site/murmurhash/)
- [FNV](http://isthe.com/chongo/tech/comp/fnv/) 
- [Jenkins](http://www.burtleburtle.net/bob/c/lookup3.c)

#### Pravděpodobnost falešného pozivita

Odhad pravděpodobnosti falešného pozitiva na základě počtu hashovacích funkcí (*k*), velikosti bitového pole (*m*) a počtu vložených prvků (*n*):

€€ P(E) \approx \left( 1-e^{-\frac{kn}{m}} \right)^k €€

### Implementace

#### Rozhraní filtru

```include:java
bloom/BloomFilter.java
```

#### Implementace filtru

```include:java
bloom/DefaultBloomFilter.java
```

#### Použití

```include:java
bloom/Example.java
```

### Reference

- https://cdn.dal.ca/content/dam/dalhousie/pdf/faculty/computerscience/technical-reports/CS-2002-10.pdf
- https://dl.acm.org/doi/10.1145/362686.362692
- http://billmill.org/bloomfilter-tutorial/
- http://hur.st/bloomfilter?n=1000000&p=0.1
- http://corte.si/posts/code/bloom-filter-rules-of-thumb/
