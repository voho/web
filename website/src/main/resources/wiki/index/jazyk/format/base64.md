## Base 64

Base 64 je [kódování](wiki/kodovani), které umožňuje reprezentovat libovolnou posloupnost bajtů (bajt = 8 bitů) jako [ASCII](wiki/ascii) řetězec.
Používá se tam, kde je potřeba přenést binární data po kanálu, který podporuje pouze ASCII nebo jiné osmibitové kódování.

Každých 24 bitů (3 bajty) na vstupu je rozděleno na 4 části po 6 bitech (€24 / 4 = 6€). 
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

- zbývající délka je 8 bitů: zakódují se dva znaky a čtveřice se doplní dvěmi rovnítky: *==*
- zbývající délka je 16 bitů: zakódují se tři znaky a čtveřice se doplní jedním rovnítkem: *=* 

Pokud jsou výsledné znaky zakódovány jedním bajtem, ve výsledku kódování převádí trojici bajtů na čtveřici.
Proto bude poměr délky výstupu k délce vstupu 4:3 (délka v bitech se tedy zvětří o třetinu).

### Příklady

- `Hello world!` = `SGVsbG8gd29ybGQh` (base64)
- `This is me` = `VGhpcyBpcyBtZS4=` (base64)
- `Brno` = `QnJubw==` (base64)

### Varianty

Existuje varianta Base 64 URL, která je shodná s Base 64 až na dva poslední znaky v tabulce:

| Bity | Symbol
|---|---
| `111110` | - 
| `111111` | _ 

Tato varianta se používá primárně pro URL a názvy souborů, protože neobsahuje žádné problematické znaky.

### Reference

- https://tools.ietf.org/html/rfc4648
- http://b64.io/
- https://www.base64encode.org/
- https://www.base64decode.org/