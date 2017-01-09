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