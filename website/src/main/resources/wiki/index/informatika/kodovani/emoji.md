## Emoji

Emoji je výraz pro sadu ikon, která se stala součástí standardu Unicode.
Tato sada obsahuje abstraktní symboly i konkrétní předměty, zvířata, lidi, situace, a podobně.
Název "emoji" je původně složenina z japonských slov **e** (絵, "obrázek") a **moji** (文字, "znak"), podobnost se slovem "emotikona" nebo "emoce" je tedy čistě náhodná.
Původní emoji se začaly objevovat na japonských mobilních telefonech v devadesátých letech dvacátého století a nyní jich standard obsahuje již několik stovek.
Standard nepředepisuje, jak přesně by měly emoji vypadat, výrobci proto mají volnou ruku a emoji vypadají rozdílně na různých platformách.

Zajímavostí je, že se emoji vyvíjí v samostatný jazyk se svými dialekty. 
Interpretace a četnost použití jednotlivých symbolů se také liší podle kultury, ve které je použit.
Některé kultury například preferují pozitivní emoji, jiné zase naopak.

### Příklady

Seznam všech e-moji najdete například na [oficiálních stránkách Unicode](http://unicode.org/emoji/charts/full-emoji-list.html) nebo na příslušných podstránkách této wiki.

| Kód | Symbol | Název Unicode | Název
|---|---|---|---
|U+26FD|⛽|FUEL PUMP|benzínová stanice
|U+2747|❇|SPARKLE|třpytka
|U+1F308|🌈|RAINBOW|duha
|U+1F31D|🌝|FULL MOON WITH FACE|měsíc v úplňku s tváří
|U+1F401|🐁|MOUSE|myš

### Kombinace emoji

Některé kombinace znaků emoji mají speciální význam a standard předepisuje i několik modifikátorů, které ovlivňují výsledné zobrazení.

#### Prezentace

Některé symboly lze vyjádřit pomocí textu i obrázku, proto existuje modifikátor, kterým lze prezentaci vybrat.
Například znak srdce (U+2764) lze vyjádřit oběma způsoby: textově jako ❤ (U+2764 U+FE0E) nebo graficky jako ❤️ (U+2764 U+FE0F).
Emoji podporující tento modifikátor mají ve standardu definován i výchozí styl zobrazení, který je použitý v případě, že není použit žádný modifikátor.

| Kód | Název Unicode | Popis
|---|---|---
|U+FE0E|VARIATION SELECTOR-15 (text variation selector)|přepnutí na textové zobrazení
|U+FE0F|VARIATION SELECTOR-16 (emoji variation selector)|přepnutí na grafické znázornění

Několik příkladů:

| Kód | Textová verze (+FE0E) | Grafická verze (+FE0F) | Název Unicode
|---|---|---|---
|00A9|©|©|COPYRIGHT SIGN
|00AE|®|®|REGISTERED SIGN
|203C|‼|‼|DOUBLE EXCLAMATION MARK
|2049|⁉|⁉|EXCLAMATION QUESTION MARK

#### Regionální identifikátory

Pro každé písmeno je definován i regionální identifikátor, ze kterých lze skládat vlajky států.
Vlajky státu se skládají ze dvojice regionálních identifikátorů podle standardu [ISO 3166-1](http://data.okfn.org/data/core/country-list).  

Příklad: kód Lucemburska je *LU*, za sebe tedy vložíme regionální identifikátory *L* a *U*: 🇱 + 🇺 = 🇱🇺

| Kód | Symbol | Název Unicode
|---|---|---
| U+1F1E6|🇦|REGIONAL INDICATOR SYMBOL LETTER A
| U+1F1E7|🇧|REGIONAL INDICATOR SYMBOL LETTER B
| U+1F1E8|🇨|REGIONAL INDICATOR SYMBOL LETTER C
| U+1F1E9|🇩|REGIONAL INDICATOR SYMBOL LETTER D
| U+1F1EA|🇪|REGIONAL INDICATOR SYMBOL LETTER E
| U+1F1EB|🇫|REGIONAL INDICATOR SYMBOL LETTER F
| U+1F1EC|🇬|REGIONAL INDICATOR SYMBOL LETTER G
| U+1F1ED|🇭|REGIONAL INDICATOR SYMBOL LETTER H
| U+1F1EE|🇮|REGIONAL INDICATOR SYMBOL LETTER I
| U+1F1EF|🇯|REGIONAL INDICATOR SYMBOL LETTER J
| U+1F1F0|🇰|REGIONAL INDICATOR SYMBOL LETTER K
| U+1F1F1|🇱|REGIONAL INDICATOR SYMBOL LETTER L
| U+1F1F2|🇲|REGIONAL INDICATOR SYMBOL LETTER M
| U+1F1F3|🇳|REGIONAL INDICATOR SYMBOL LETTER N
| U+1F1F4|🇴|REGIONAL INDICATOR SYMBOL LETTER O
| U+1F1F5|🇵|REGIONAL INDICATOR SYMBOL LETTER P
| U+1F1F6|🇶|REGIONAL INDICATOR SYMBOL LETTER Q
| U+1F1F7|🇷|REGIONAL INDICATOR SYMBOL LETTER R
| U+1F1F8|🇸|REGIONAL INDICATOR SYMBOL LETTER S
| U+1F1F9|🇹|REGIONAL INDICATOR SYMBOL LETTER T
| U+1F1FA|🇺|REGIONAL INDICATOR SYMBOL LETTER U
| U+1F1FB|🇻|REGIONAL INDICATOR SYMBOL LETTER V
| U+1F1FC|🇼|REGIONAL INDICATOR SYMBOL LETTER W
| U+1F1FD|🇽|REGIONAL INDICATOR SYMBOL LETTER X
| U+1F1FE|🇾|REGIONAL INDICATOR SYMBOL LETTER Y
| U+1F1FF|🇿|REGIONAL INDICATOR SYMBOL LETTER Z

#### Barva pleti

Škála barev pleti je založena na tzv. [Fitzpatrickově škále](https://en.wikipedia.org/wiki/Fitzpatrick_scale), která byla publikovaná v roce 1975 a obsahuje šest fototypů.
Kvůli zjednodušení byly první dvě skupiny (I + II) sloučeny.
Preferovaná barva vlasů je tmavá, protože je u lidí častější.

| Kód | Symbol | Název Unicode | Příklad
|---|---|---|---
| U+1F3FB | 🏻 | light skin tone: EMOJI MODIFIER FITZPATRICK TYPE-1-2 | 🤷 + 🏻 = 🤷🏻
| U+1F3FC | 🏼 | medium-light skin tone: EMOJI MODIFIER FITZPATRICK TYPE-3 | 🤷 + 🏼 = 🤷🏼		
| U+1F3FD | 🏽 | medium skin tone: EMOJI MODIFIER FITZPATRICK TYPE-4	| 🤷 + 🏻 = 🤷🏽
| U+1F3FE | 🏾 | medium-dark skin tone: EMOJI MODIFIER FITZPATRICK TYPE-5| 🤷 + 🏾 = 🤷🏾
| U+1F3FF | 🏿 | dark skin tone: EMOJI MODIFIER FITZPATRICK TYPE-6| 🤷 + 🏿 = 🤷🏿

#### Pohlaví

| Kód | Symbol | Název Unicode | Příklad
|---|---|---
| U+2642 | ♂️ | Male Sign | 🤷+♂ = 🤷♂
| U+2640 | ♀️ | Female Sign | 🤷+♀️ = 🤷♀️

### Volné kombinace se spojovacím znakem nulové délky (ZWJ)

Existuje speciální znak (U+200D), který se nazývá **spojovací znak nulové délky** (zero-width joiner, zkráceně ZWJ).
Tento znak vložíme mezi emoji, které chceme spojit v jeden znak.
Pokud výrobce takovou kombinaci emoji podporuje, jednotlivé znaky se vykreslí normálně a spojovací znak se přeskočí.

Několik příkladů:

| Posloupnost znaků | Kombinace (jako jeden znak) | Popis
|---|---|---
| 👨+🏻+ZWJ+‍⚖ | 👨🏻‍⚖️ | Soudce (muž + bílá pleť + ZWJ + spravedlnost)
| 👨+ZWJ+👩+ZWJ+👦 | 👨‍👩‍👦 | Rodina (muž + ZWJ + žena + ZWJ + chlapec)
| 👩🏽+ZWJ+🚒 | 👩🏽‍🚒 | Černá hasička (žena + tmavá pleť + ZWJ + hasičské auto)

### Reference

- http://unicode.org/Public/emoji/5.0/emoji-data.txt
- http://unicode.org/emoji/charts/full-emoji-list.html
- http://unicode.org/emoji/charts/emoji-list.html
- https://github.com/muan/emojilib/blob/master/simplemap.json
- https://www.emoji.codes/
- http://getemoji.com/
- https://www.webpagefx.com/tools/emoji-cheat-sheet/
- http://mts.io/2015/04/21/unicode-symbol-render-text-emoji/
- https://codepoints.net/U+1F3FF?lang=en
