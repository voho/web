## Base 64

Base 64 je [kódování](wiki/kodovani), které reprezentuje libovolnou posloupnost bajtů (bajt = 8 bitů) jako [ASCII](wiki/ascii) řetězec.

Kódování Base 64 se používá tam, kde je potřeba přenést binární data po kanálu, který je schopen přenášet ASCII, ale nikoliv libovolná binární data.
Může to být například proto, že některé posloupnosti bitů mohou být přenosovými komponentami (např. dříve modemy) interpretovány jako speciální instrukce.

Každých 24 bitů (tři bajty) na vstupu je rozděleno na čtyři části po šesti bitech (€24 / 4 = 6€). 
Každé toto šestibitové číslo v rozsahu 0 (*000000*) až 63 (*111111*) slouží jako index do této tabulky znaků:

| Bity | Symbol | Bity | Symbol | Bity | Symbol | Bity | Symbol
|---|---|---|---|---|---|---|---
| `000000` | A | `010000` | Q | `100000` | g | `110000` | w
| `000001` | B | `010001` | R | `100001` | h | `110001` | x
| `000010` | C | `010010` | S | `100010` | i | `110010` | y
| `000011` | D | `010011` | T | `100011` | j | `110011` | z
| `000100` | E | `010100` | U | `100100` | k | `110100` | 0
| `000101` | F | `010101` | V | `100101` | l | `110101` | 1
| `000110` | G | `010110` | W | `100110` | m | `110110` | 2
| `000111` | H | `010111` | X | `100111` | n | `110111` | 3
| `001000` | I | `011000` | Y | `101000` | o | `111000` | 4
| `001001` | J | `011001` | Z | `101001` | p | `111001` | 5
| `001010` | K | `011010` | a | `101010` | q | `111010` | 6
| `001011` | L | `011011` | b | `101011` | r | `111011` | 7
| `001100` | M | `011100` | c | `101100` | s | `111100` | 8
| `001101` | N | `011101` | d | `101101` | t | `111101` | 9
| `001110` | O | `011110` | e | `101110` | u | `111110` | +
| `001111` | P | `011111` | f | `101111` | v | `111111` | /

Pokud délka vstupu není dělitelná číslem 24, mohou nastat tyto možnosti:

- zbývající délka je 8 bitů:
  - binární řetězec se doplní čtyřmi nulami, aby jeho délka byla 12
  - podle tabulky se zakódují dva znaky: €(8+4) / 6 = 2€
  - zakódovaná čtveřice znaků se doplní dvěmi rovnítky: *==*
- zbývající délka je 16 bitů:
  - binární řetězec se doplní dvěma nulami, aby jeho délka byla 18
  - podle tabulky se zakódují tři znaky: €(16+2) / 6 = 3€
  - zakódovaná čtveřice znaků se doplní jedním rovnítkem: *=* 

Těmto úpravám se říká "padding" a některé implementace kódování si umí poradit i bez nich, avšak standard je předepisuje.

Pokud výsledný řetězec uložíme jako ASCII, kódování ve výsledku převede každou trojici bajtů na čtveřici, takže bude poměr délky výstupu k délce vstupu 4:3. 
Délka v bitech se tedy zvětší zhruba o třetinu.

### Příklady

#### Kódování "01000010011100100110111001101111"

Nejprve každých 24 bitů na vstupu rozdělíme na čtyři části po šesti bitech.
Protože je řetězec dlouhý 32 bitů a není dělitelný číslem 24, musíme provést padding.
To znamená, že zbývajících osm bitů doplníme čtyřmi nulami, abychom mohli použít kódovací tabulku, a rezervujeme místo pro dvě chybějící šestice.


```
010000 100111 001001 101110, 011011 11[0000] [??????] [??????]
```

Každou šestici teď zakódujeme pomocí kódovací tabulky a poslední dvě chybějící šestice nahradíme rovnítky podle standardu:

```
QnJubw==
```

#### Dekódování "QnJubw=="

Pomocí kódovací tabulky zapíšeme binární hodnoty všech znaků. Rovnítka přeskočíme, protože představují chybějící šestice.

```
010000 100111 001001 101110 011011 110000
```

Jelikož zakódovaný řetězec končil dvěma rovnítky (*==*), lze jednoznačně odvodit, že do původního binárního řetězce byly doplněny čtyři nuly (viz zdůvodnění výše). 
Tyto nuly proto odebereme a získáme původní binární řetězec:

```
01000010011100100110111001101111
```

### Varianty

Existuje varianta Base 64 URL, která je shodná s Base 64 až na dva poslední znaky v tabulce:

| Bity | Symbol
|---|---
| `111110` | - (mínus)
| `111111` | _ (podtržítko)

Tato varianta se používá primárně pro URL a názvy souborů, protože neobsahuje žádné problematické znaky.

### Reference

- https://tools.ietf.org/html/rfc4648
- http://b64.io/
- https://www.base64encode.org/
- https://www.base64decode.org/