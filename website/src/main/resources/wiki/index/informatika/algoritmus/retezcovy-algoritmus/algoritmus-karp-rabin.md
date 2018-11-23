## Algoritmus Karp-Rabin

Algoritmus Karp-Rabin pro vyhledávání řetězců je založený na porovnávání **otisků** ([hashů](wiki/java-hash)) dvou řetězců: otisku řetězce hledaného a otisku části řetězce prohledávaného.
Tento algoritmus je v praxi efektivnější než triviální algoritmus založený na porovnávání jednotlivých znaků za předpokladu, že výpočet otisku probíhá v konstantním čase, a to nezávisle na délce hledaného řetězce.
Algoritmus pro výpočet otisku je tedy nutné vybírat tak, aby otisk byl schopný přepočítat v konstantním čase při každém přesunu vyhledávané části (tzv. rolling hash).

Další výhodou algoritmu je schopnost efektivně vyhledávat větší množství řetězců najednou, proto se skvěle hodí například ke hledání plagiátů nebo detekci různých virů v binárních souborech.

Algoritmus byl představen v roce 1987 (Michael O. Rabin, Richard M. Karp).

### Postup

- Algoritmus nejprve vypočítá otisk vyhledávaného řetězce a stejně dlouhého výřezu řetězce prohledávaného. 
- Pokud se otisky shodují, je nutné provést kontrolu pro případ kolize (dva různé řetězce totiž mohou mít stejný otisk). V případě absolutní shody jsme řetězec nalezli a výpočet končí.
- Pokud se otisky neshodují nebo předchozí kontrola selže (v případě kolize), výřez prohledávaného řetězce se posune o jeden znak doprava, jeho otisk se přepočítá a výpočet se vrací zpět na první krok.

### Příklad

Úkolem je vyhledat pozici řetězce `CDA` v řetězci `AABCDAA`. 
Pro výpočet otisku budeme používat nejjednodušší metodu, a sice součet pořadových čísel písmen v abecedě (A = 1, B = 2, C = 3, atd).

Nejprve tedy vypočítáme otisk vyhledávaného řetězce `CDA` (€3+4+1=8€) a prvních tří znaků řetězce `AABCDAA`, tedy `AAB` (€1+1+2=4€).

Otisky se neshodují (€8 \neq 4€), pokračujeme tedy dál.

Další tři vstupní znaky výřezu, které musíme vyhodnotit, jsou znaky `ABC`. 
Je to druhý, třetí a čtvrtý znak prohledávaného řetězce.
Každý posun výřezu způsobí, že první znak z výřezu zmizí a na konec výřezu se dostane znak nový.

Další otisk můžeme spočítat buď klasickým způsobem (€1+2+3=6€) nebo využít předchozího výsledku a ten přepočítat odečtením prvního znaku `A` a přičtením následujícího znaku `C`.
Obecně je tento způsob rychlejší, a to především v případě, kdy je vyhledávaný řetězec dlouhý.

V našem případě můžeme otisk `ABC` také vypočítat jako předchozí otisk `AAB` po odečtení `A` a přičtení `C`, tedy €(1+1+2)-1+3=6€.

Otisky se opět neshodují (€8 \neq 6€), pokračujeme tedy dál.

Další výřez je `BCD` s otiskem €6-1+4=9€, který se neshoduje (€8 \neq 9€).

Další výřez je `CDA` s otiskem €9-2+1=8€, který se shoduje s otiskem vyhledávaného řetězce.

Nyní musíme už jen provést kontrolu, zda se znaky skutečně shodují. 
V tomto případě ano, takže hledání je u konce a řetězec byl nalezen.

![vyhledávání CDA v řetězci AABCDAA](algorithm-karp.png)

### Asymptotická složitst

Asymptotická složitost algoritmu je teoreticky stejná jako u triviálního algoritmu, a sice €O(m + n \cdot m)€, kde €n€ je délka řetězce a €m€ je délka vzoru.
Při vhodné volbě hashovacího algoritmu ale klesá až na €O(m + n \cdot c)€, kde €c€ je malá konstanta reprezentující čas nutný pro přepočet otisku během každého posunu.

### Implementace

```include:java
search/KarpRabin.java
```

### Reference

- https://dzone.com/articles/algorithm-week-rabin-karp