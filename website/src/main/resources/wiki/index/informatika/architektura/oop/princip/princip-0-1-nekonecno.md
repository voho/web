## Pravidlo Nula-Jedna-Nekonečno

> Jediná smysluplná čísla jsou nula, jedna a nekonečno. *B. MacLennan*

Pravidlo Nula-Jedna-Nekonečno (zero-one-infinity rule) je doporučení pro objektový návrh, které hovoří o počtu entit ve vztahu. Ve většině situací dává smysl odlišit "něco" (*1*) od "ničeho" (*0*) a od "libovolného počtu něčeho" (*nekonečno*). Proto nemá smysl se při návrhu vazeb mezi objekty omezovat nějakými jinými čísly.

### Příklad

Zadání: **Vytvořte model automobilu obsahující jména cestujících na jednotlivých sedadlech**

#### Řešení bez použití pravidla 

Typické osobní auto má pět míst: dvě vepředu a tři vzadu. Proto vytvořím model s pěti atributy: jméno cestujícího na místě řidiče, jméno cestujícího na místě spolujezdce a tři jména cestujících na zadních sedadlech. Toto řešení je sice jednoduché, ale je omezené pouze na pětimístné automobily.

#### Řešení s použitím pravidla

Kabriolet a nákladní automobil má typicky dvě místa, malé osobní auto čtyři místa, střední pět a velké sedm. Takže počet zobecníme na "libovolné množství". Proto bude model obsahovat model automobilu a model vazby mezi automobilem a cestujícím. Model sice bude obsahovat více entit než model z předchozího řešení, ale je obecnější a umožní modelovat více typů dopravních prostředků.

### Reference

- http://www.catb.org/~esr/jargon/html/Z/Zero-One-Infinity-Rule.html
- http://c2.com/cgi/wiki?ZeroOneInfinityRule
- http://www.cs.utk.edu/~mclennan/Classes/365/principles.html