## Binární čísla a bitové operace

Binární číslo je reprezentováno posloupností binárních hodnot (např. zapisovaných jako *0* a *1*) a takto reprezentovaná čísla se používají v digitální technice založené na [logických hradlech](wiki/logicke-hradlo).

### Nejpoužívanější mocniny 2

| mocnina | hodnota | poznámka
|---|---|---
| 5 | 32 | single precision
| 6 | 64 | double precision
| 7 | 128 | |
| 8 | 256 | byte
| 9 | 512 | |
| 10 | 1,024 | 1 kiB
| 20 | 1,048,576 | 1 MiB
| 30 | 1,073,741,824 | 1 GiB
| 32 | 4,294,967,296 | 4 GiB
| 40 | 1,099,511,627,776 | 1 TiB

### Hexadecimální čísla

| hexadecimálně | binárně | hexadecimálně | binárně
|---|---|---|---
| `0` | `0000` | `8` | `1000`
| `1` | `0001` | `9` | `1001`
| `2` | `0010` | `A` | `1010`
| `3` | `0011` | `B` | `1011`
| `4` | `0100` | `C` | `1100`
| `5` | `0101` | `D` | `1101`
| `6` | `0110` | `E` | `1110`
| `7` | `0111` | `F` | `1111`

### Bitové operace

#### Logický součin

Logický součin vrací 1 v případě, že jsou oba činitelé 1, v ostatních případech 0. Také se značí jako *AND*, *&&*, *&*.

| a | b | a AND b 
|---|---|---
| 0 | 0 | 0
| 0 | 1 | 0
| 1 | 0 | 0
| 1 | 1 | 1

Příklad:

```
      a = 11000001010100000000000100110111
      b = 10000100000100000001000101010111
a AND b = 10000000000100000000000100010111
```

V jazyce Java:

```java
int a = 0xC1500137;
int b = 0x84101157;
int r = a & b; // = 0x80100117
```

#### Logický součet

Logický součet vrací 1 v případě, že je alespoň jeden sčítanec 1, v ostatních případech 0. Také se značí jako *OR*, *||*, *|*.

| a | b | a OR b 
|---|---|---
| 0 | 0 | 0
| 0 | 1 | 1
| 1 | 0 | 1
| 1 | 1 | 1

Příklad:

```
     a = 11000001010100000000000100110111
     b = 10000100000100000001000101010111
a OR b = 11000101010100000001000101110111
```

V jazyce Java:

```java
int a = 0xC1500137;
int b = 0x84101157;
int r = a | b; // = 0xC5501177
```

#### Exkluzivní součet

Exkluzivní součet vrací 1 v případě, že se sčítance liší, v ostatních případech 0. Také se značí jako *XOR*.

| a | b | a XOR b 
|---|---|---
| 0 | 0 | 0
| 0 | 1 | 1
| 1 | 0 | 1
| 1 | 1 | 0

Příklad:

```
      a = 11000001010100000000000100110111
      b = 10000100000100000001000101010111
a XOR b = 01000101010000000001000001100000
```

V jazyce Java:

```java
int a = 0xC1500137;
int b = 0x84101157;
int r = a ^ b; // = 0x45401060
```

#### Bitový posuv

Bitový posuv bity v daném čísle posune o zadaný počet míst vlevo či vpravo. Existují však různé druhy posuvu podle toho, jakými hodnotami vyplňuje krajní pozice.

##### Aritmetický posuv

Aritmetický posuv respektuje kódování čísla, které je posouváno, a proto má i aritmetický význam - posuv o jednu pozici vlevo odpovídá násobení dvěma a posuv vpravo dělení. Opakovaným aritmetickým posuvem lze tedy velmi efektivně násobit a dělit mocninami čísla 2. V případě doplňkového kódu a záporného čísla (tzn. nejvýznamnější bit je 1) jsou směrem od nejvýznamnějšího nasouvány hodnoty 1, jinak 0.

```
     a = 11000001010100000000000100110111
a >> 1 = 11100000101010000000000010011011
a >> 5 = 11111110000010101000000000001001
a << 3 = 00001010100000000000100110111000
```

```
     b = 00010100101010110110111101101111
b >> 1 = 00001010010101011011011110110111
b >> 5 = 00000000101001010101101101111011
b << 3 = 10100101010110110111101101111000
```

##### Logický posuv

Logický posuv nemá žádný aritmetický význam a z obou krajních pozic generuje za všech okolností nuly.

```
      a = 11000001010100000000000100110111
a >>> 1 = 01100000101010000000000010011011
a >>> 5 = 00000110000010101000000000001001
a <<< 3 = 00001010100000000000100110111000
```

### Binární řádová mřížka

Binární řádová mřížka přesně popisuje **formát** čísel, se kterými dokáže daný procesor pracovat. Mezi její parametry patří:

délka *l*
: počet bitů mřížky, který určuje počet rozlišitelných hodnot

jednotka *e*
: nejmenší číslo, které LZE v mřížce uložit

modul *Z*
: nejmenší číslo, které NELZE v mřížce uložit

pozice desetinné čárky
: může být pevná či plovoucí (v tomto článku se pracuje pouze s celými čísly, takže je desetinná čárka napevno vždy za posledním bitem vpravo)

Příklad binární mřížky s délkou 8 bitů:

| Řád | | 7. | 6. | 5. | 4. | 3. | 2. | 1. | 0. | |
|---|---|---|---|---|---|---|---|---|---|---
| Příklad | | 0 | 1 | 1 | 0 | 1 | 0 | 0 | 1 | =105
| Jednotka | | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 1 | =1
| Modul | 1 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | =256

### Zápis čísel bez znaménka

Chceme-li zapsat kladné dekadické číslo v binární řádové mřížce, provedeme standardní převod mezi číselnými soustavami a vznikne nám posloupnost jedniček a nul. Pokud je tato posloupnost delší, než délka řádové mřížky, číslo v dané mřížce není zobrazitelné. Zobrazitelná čísla zapisujeme od nejnižšího řádu, zbytek vyplníme nulami.

| Binárně (délka mřížky = 4) | Dekadicky
|---|---
| 0000 | 0
| 0001 | 1
| 0010 | 2
| 0011 | 3
| 0100 | 4
| 0101 | 5
| 0110 | 6
| 0111 | 7
| 1000 | 8
| ... | ...

### Zápis čísel se znaménkem

Při zápisu záporných čísel je třeba do binární řádové mřížky nějakým způsobem přidat informaci o znaménku a umět z výsledného zápisu rychle zjistit absolutní hodnotu i znaménko zakódovaného čísla.

#### Přímé kódování

To první, co člověka napadne, je rozdělit mřížku na dvě podmřížky. První bude tvořena jedním bitem, který určí znaménko a zbylá část se použije k zápisu absolutní hodnoty. Tento způsob je velmi jednoduchý a intuitivní, ale ukrývá v sobě zradu v podobě záporné nuly a sníženého rozsahu. Také obvod pro sčítání dvou čísel v přímém kódu bude složitější, protože bude muset rozlišovat jednotlivé případy (+ +, + -, - +, - -).

| Binárně (délka mřížky = 1+4) | Dekadicky | Dekódované číslo
|---|---|---
| 0,0000 | +,0 | 0
| 0,0001 | +,1 | 1
| 0,0010 | +,2 | 2
| 0,0011 | +,3 | 3
| 0,... | +,x | x
| 1,0000 | -,0 | -0 (zbytečné)
| 1,0001 | -,1 | -1
| 1,0010 | -,2 | -2
| 1,0011 | -,3 | -3
| 1,... | -,x | -x

€€
\begin{align*} 
s &= \{\begin{matrix} 1; \; \; n < 0 \\ 
0; \; \; n \geq 0 \end{matrix} \\ 
A(n) &= s\|(|n|)_{2} \\ 
A^{-1}(s\|n) &= ((-1)^s \cdot n)_{10} 
\end{align*} 
€€

#### Aditivní kódování

Aditivní kód již nerozděluje mřížku na podmřížky, ale mění způsob interpretace dat. Hodnotu dekadického čísla před zápisem zvýšíme o pevně zvolenou hodnotu c (většinou polovina modulu, aby byl rozsah kladných i záporných čísel zhruba vyrovnaný). To nás zbaví záporných čísel a kladná čísla umíme již bez problémů do mřížky zapsat. Při dekódování čísla naopak hodnotu c odečteme. Aditivní kód se také někdy označuje jako kód s posunutou nulou. Zajímavé na něm je to, že umožňuje do mřížky zapsat více záporných čísel než kladných.

Příklad ukazuje aplikaci aditivního kódu na mřížku s délkou l=3, modulem Z=8 a hodnotou c=4.

| Binárně (délka mřížky = 3) | Dekadicky | Dekódované číslo
|---|---|---
| 000 | 0 | -4
| 001 | 1 | -3
| 010 | 2 | -2
| 011 | 3 | -1
| 100 | 4 | 0
| 101 | 5 | 1
| 110 | 6 | 2
| 111 | 7 | 3

€€
\begin{align*} 
c &= \frac{Z}{2} = \frac{2^l}{2} = 2^{l-1} \\ 
A(n) &= (n+c)_{2} \\ 
A^{-1}(n) &= (n-c)_{10} 
\end{align*} 
€€

#### Doplňkové kódování

Doplňkové kódování je podobné přímému kódování s tím rozdílem, že je znaménkový bit přímou a organickou součástí hodnoty čísla. Tím pádem neplýtváme rozsahem mřížky. Podobně jako aditivní kód i doplňkový kód mění interpretaci čísel, avšak jen pro čísla záporná. Jeho výhodou je okamžité zjištění znaménka, triviální kódování kladných čísel a rychlý převod čísel záporných.

Příklad ukazuje aplikaci doplňkového kódu na mřížku s délkou l=3 a modulem Z=16.

| Binárně (délka mřížky = 3) | Dekadicky | Dekódované číslo
|---|---|---
| 000 | 0 | 0
| 001 | 1 | 1
| 010 | 2 | 2
| 011 | 3 | 3
| 100 | 4 | -4
| 101 | 5 | -3
| 110 | 6 | -2
| 111 | 7 | -1

€€
\begin{align*} 
A(n) &= \{\begin{matrix} (n)_{2}; \; \; n \geq 0 \\ 
(n+M)_{2}; \; \; n < 0 \end{matrix} \\ 
A^{-1}(s\|n) &= \{\begin{matrix} (s\|n)_{10}; \; \; s = 0 \\
(s\|n-M)_{10}; \; \; s = 1 \end{matrix} \\ 
\end{align*}
€€

Existuje taková pomůcka, která může pomoci při zápisu záporného čísla v doplňkovém kódu:

1. Zapište do mřížky absolutní hodnotu záporného čísla ve dvojkové soustavě, zbytek vyplňte nulami.
1. Invertujte všechny bity.
1. Přičtěte jedničku a ignorujte případný přenos v nejvyšším řádu.

### Reference

- předmět X36SKD na FEL ČVUT