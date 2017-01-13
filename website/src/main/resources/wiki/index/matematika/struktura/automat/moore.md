## Konečný automat typu Moore

Konečný automat typu Moore si lze představit jako jednoduché zařízení s konečným počtem vnitřních stavů, mezi kterými se přechází na základě vstupních symbolů. Každý vnitřní stav má definovaný právě jednu hodnotu na výstupu. Automat musí mít dále definovaný výchozí vnitřní stav, ve kterém se nachází před zadáním prvního vstupního symbolu a pravidla pro přechody mezi jednotlivými stavy.

```dot:digraph
rankdir=LR
ratio=1
"transition function" [shape=none,fillcolor=transparent]
"output function" [shape=none,fillcolor=transparent]
input -> "transition function"
"transition function" -> state
state -> "transition function"
state -> "output function"
"output function" -> output
input [shape=oval]
output [shape=oval]
state [shape=box3d,fillcolor=khaki]
```

### Formální definice

Konečný automat typu Moore je uspořádaná šestice € (X,Y,S,S_0,\delta,\omega) €, kde € X : \left| X \right| < \infty € je konečná vstupní abeceda (množina vstupních symbolů), € Y : \left| Y \right| < \infty € je konečná výstupní abeceda (množina výstupních symbolů), € S : \left| S \right| < \infty € je konečná množina vnitřních stavů, € S_0 : S_0 \in S € je výchozí vnitřní stav, € \delta : S \times X \to S € je přechodová funkce a € \omega : S \to Y € je výstupní funkce.

Pokud by výstup závisel i na vstupu, jednalo by se o [konečný automat typu Mealy](wiki/mealy).

### Kdo byl "Gordon Moore"

Gordon Moore se narodil v roce 1929 v Kalifornii. Známý je především jako spoluzakladatel megakorporace Intel (1968). Velmi proslulý je i jeho postulát (tzv. Mooreův zákon), který říká, že integrované obvody zdvojnásobí svou složitost každých 18 měsíců.

![Gordon Moore (1965)](http://www.computerhistory.org/semiconductor/assets/img/400x400/1965_1_1.jpg)