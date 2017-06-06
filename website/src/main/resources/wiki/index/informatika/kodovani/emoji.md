## Emoji

Emoji je výraz pro sadu ikon, který se stal součástí standardu Unicode. 
Název "emoji" je původně složenina z japonských slov *e* (絵, "obrázek") a *moji* (文字, "znak").
Podobnost se slovem "emotikona" nebo "emoce" je tedy čistě náhodná.

Pod pojmem emoji se obvykle rozumí jeden znak neboli jeden obrázek, ale technicky lze jeden obrázek vytvořit i kombinací několika za sebou následujících znaků.
Přímo ve standardu se nachází některé kombinace, například vlajky.

### Kombinace emoji

Některé posloupnosti znaků emoji mají speciální význam a přidání určitých znaků lze ovlivnit symboly předcházející.

#### Prezentace

Některé symboly lze vyjádřit pomocí textu i obrázku, proto existují  

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
| 👨+🏻+‍⚖ | 👨🏻‍⚖️ | Soudce (bílá pleť, muž) 

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
