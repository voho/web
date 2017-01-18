## Kódování reálných čísel v řádové mřížce

!TODO!

### IEEE 754

Hodnota čísla je rovna součinu znaménka (*s*), základu soustavy (*2*) umocněného na exponent (*e*) a mantisy (*m*).

€€ 
n = s \cdot 2^e \cdot m 
€€

| Reprezentace | Znaménko | Exponent | Mantisa
|---|---|---|---
| Jednoduchá přesnost (single precision) | 1 bit | 8 bitů (-126 až 127) | 23 bitů
| Dvojitá přesnost (double precision) | 1 bit | 11 bitů (-1022 až 1023) | 52 bitů

### Reference

- https://www.h-schmidt.net/FloatConverter/IEEE754.html