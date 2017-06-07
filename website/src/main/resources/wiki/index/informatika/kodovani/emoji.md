## Emoji

Emoji je výraz pro sadu ikon, která se stala součástí standardu Unicode.
Tato sada obsahuje abstraktní symboly i konkrétní předměty, zvířata, lidi, situace, a podobně.
Název "emoji" je původně složenina z japonských slov **e** (絵, "obrázek") a **moji** (文字, "znak"), podobnost se slovem "emotikona" nebo "emoce" je tedy čistě náhodná.
Původní emoji se začaly objevovat na japonských mobilních telefonech v devadesátých letech dvacátého století a nyní jich standard obsahuje již několik stovek.
Standard nepředepisuje, jak přesně by měly být emoji znázorněny, každý výrobce má volnou ruku. 
Proto stejný symbol emoji může vypadat rozdílně na různých platformách.

### Příklady

| Kód | Symbol | Název Unicode | Název
|---|---|---|---
|26FD|⛽|FUEL PUMP|benzínová stanice
|2747|❇|SPARKLE|třpytka
|1F308|🌈|RAINBOW|duha
|1F31D|🌝|FULL MOON WITH FACE|měsíc v úplňku s tváří
|1F401|🐁|MOUSE|myš

Seznam všech e-moji najdete například na [oficiálních stránkách konsorcia Unicode](http://unicode.org/emoji/charts/full-emoji-list.html) nebo zde na jednotlivých podstránkách.

### Kombinace emoji

Znaky e-moji lze vzájemně kombinovat a standard předepisuje i několik modifikátorů, které ovlivňují výsledné zobrazení.

#### Prezentace

Některé symboly lze vyjádřit pomocí textu i obrázku, proto existuje modifikátor, kterým lze prezentaci vybrat.
Například znak srdce (U+2764) lze vyjádřit oběma způsoby: textově jako ❤ (U+2764 U+FE0E) nebo graficky jako ❤️ (U+2764 U+FE0F).
Emoji podporující tento modifikátor mají ve standardu definován i výchozí styl zobrazení, který je použitý v případě, že není použit žádný modifikátor.

| Kód | Název Unicode | Popis
|---|---|---
|U+FE0E|VARIATION SELECTOR-15 (text variation selector)|přepnutí na textové zobrazení
|U+FE0F|VARIATION SELECTOR-16 (emoji variation selector)|přepnutí na grafické znázornění

#### Regionální identifikátory

Z regionálních identifikátorů lze skládat například vlajky různých států. 
Například dvoupísmenný ISO kód Lucemburska je *LU*, za sebe tedy vložíme dva regionální identifikátory *L* + *U*:
🇱+🇺 = 🇱🇺

| Kód | Symbol | Název Unicode
|---|---|---
| U+1F1E6 |🇦|REGIONAL INDICATOR SYMBOL LETTER A
| U+1F1E7 |🇧|REGIONAL INDICATOR SYMBOL LETTER B
| U+1F1E8 |🇨|REGIONAL INDICATOR SYMBOL LETTER C
| U+1F1E9 |🇩|REGIONAL INDICATOR SYMBOL LETTER D
| U+1F1EA |🇪|REGIONAL INDICATOR SYMBOL LETTER E
| U+1F1EB |🇫|REGIONAL INDICATOR SYMBOL LETTER F
| U+1F1EC |🇬|REGIONAL INDICATOR SYMBOL LETTER G
| U+1F1ED |🇭|REGIONAL INDICATOR SYMBOL LETTER H
| U+1F1EE |🇮|REGIONAL INDICATOR SYMBOL LETTER I
| U+1F1EF |🇯|REGIONAL INDICATOR SYMBOL LETTER J
| U+1F1F0 |🇰|REGIONAL INDICATOR SYMBOL LETTER K
| U+1F1F1 |🇱|REGIONAL INDICATOR SYMBOL LETTER L
| U+1F1F2 |🇲|REGIONAL INDICATOR SYMBOL LETTER M
| U+1F1F3 |🇳|REGIONAL INDICATOR SYMBOL LETTER N
| U+1F1F4 |🇴|REGIONAL INDICATOR SYMBOL LETTER O
| U+1F1F5 |🇵|REGIONAL INDICATOR SYMBOL LETTER P
| U+1F1F6 |🇶|REGIONAL INDICATOR SYMBOL LETTER Q
| U+1F1F7 |🇷|REGIONAL INDICATOR SYMBOL LETTER R
| U+1F1F8 |🇸|REGIONAL INDICATOR SYMBOL LETTER S
| U+1F1F9 |🇹|REGIONAL INDICATOR SYMBOL LETTER T
| U+1F1FA |🇺|REGIONAL INDICATOR SYMBOL LETTER U
| U+1F1FB |🇻|REGIONAL INDICATOR SYMBOL LETTER V
| U+1F1FC |🇼|REGIONAL INDICATOR SYMBOL LETTER W
| U+1F1FD |🇽|REGIONAL INDICATOR SYMBOL LETTER X
| U+1F1FE |🇾|REGIONAL INDICATOR SYMBOL LETTER Y
| U+1F1FF |🇿|REGIONAL INDICATOR SYMBOL LETTER Z

#### Barva pleti

Škála barev pleti je založena na tzv. [Fitzpatrickově škále](https://en.wikipedia.org/wiki/Fitzpatrick_scale), která byla publikovaná v roce 1975 a obsahuje šest fototypů.
Kvůli zjednodušení byly první dvě skupiny (I + II) sloučeny.
Preferovaná barva vlasů je tmavá, protože je u lidí častější.

| Kód | Symbol | Název Unicode | Příklad
|---|---|---|---
| U+1F3FB | 🏻 | light skin tone: EMOJI MODIFIER FITZPATRICK TYPE-1-2 | 🤷+🏻 = 🤷🏻
| U+1F3FC | 🏼 | medium-light skin tone: EMOJI MODIFIER FITZPATRICK TYPE-3 | 🤷+🏼 = 🤷🏼		
| U+1F3FD | 🏽 | medium skin tone: EMOJI MODIFIER FITZPATRICK TYPE-4	| 🤷+🏻 = 🤷🏽
| U+1F3FE | 🏾 | medium-dark skin tone: EMOJI MODIFIER FITZPATRICK TYPE-5| 🤷+🏾 = 🤷🏾
| U+1F3FF | 🏿 | dark skin tone: EMOJI MODIFIER FITZPATRICK TYPE-6| 🤷+🏿 = 🤷🏿

#### Pohlaví

| Kód | Symbol | Název Unicode | Příklad
|---|---|---
| U+2642 | ♂️ | Male Sign | 🤷+♂ = 🤷♂
| U+2640 | ♀️ | Female Sign | 🤷+♀️ = 🤷♀️

### Volné kombinace se spojovacím znakem nulové délky (ZWJ)

Existuje speciální znak, který se nazývá **spojovací znak nulové délky** (zero-width joiner, zkráceně ZWJ).
Tento znak vložíme mezi emoji, které chceme spojit v jeden znak.
Pokud výrobce takovou kombinaci emoji podporuje, zobrazí je jako jeden znak.
Pokud ne, pak prostě spojovací znak ignoruje.

Příklad některých kombinací (kombinace by se měly zobrazit jako jeden znak - pokud ne, váš font je nepodporuje).

| Posloupnost znaků | Kombinace (jako jeden znak) | Popis
|---|---|---
| 👨+🏻+‍⚖ | 👨🏻‍⚖️ | Soudce (muž + bílá pleť + spravedlnost) 
| 👨+👩+👦+👶 | 👨‍👩‍👦‍👶 | Rodina (muž + žena + dítě žena + dítě muž)

!TODO!

### Reference

- http://unicode.org/Public/emoji/5.0/emoji-data.txt
- http://unicode.org/charts/PDF/Unicode-7.0/U70-1F300.pdf
- http://unicode.org/emoji/charts/full-emoji-list.html
- http://unicode.org/emoji/charts/emoji-list.html
- https://github.com/muan/emojilib/blob/master/simplemap.json
- https://gist.github.com/rxaviers/7360908
- https://github.com/StylishThemes/GitHub-Dark/wiki/Emoji
- https://www.emoji.codes/
- http://getemoji.com/
- https://www.webpagefx.com/tools/emoji-cheat-sheet/
- http://mts.io/2015/04/21/unicode-symbol-render-text-emoji/
- https://codepoints.net/U+1F3FF?lang=en
