## Konečný automat typu Mealy

Konečný automat typu Mealy je zobecněním [typu Moore](wiki/moore). Liší se od něj pouze tím, že výstup nezávisí jen na vnitřním stavu, ale i na vstupu. Ve formální definici se tato odlišnost projevuje jiným definičním oborem výstupní funkce. U typu Mealy totiž do výstupní funkce vstupuje jako parametr i aktuální prvek vstupní abecedy.

```dot:digraph
rankdir=LR
ratio=0.8
"transition function" [shape=none,fillcolor=transparent]
"output function" [shape=none,fillcolor=transparent]
"transition function" -> state [dir=both]
input -> "transition function"
state -> "output function"
input -> "output function"
"output function" -> output
state [shape=box3d,fillcolor=beige]
{rank=same;"input";"output";"output function";}
{rank=same;"state";"transition function";}
```

### Formální definice

Sekvenční automat typu Mealy je uspořádaná šestice € (X,Y,S,S_0,\delta,\omega) €, kde € X : \left| X \right| < \infty € je konečná vstupní abeceda (množina vstupních symbolů), € Y : \left| Y \right| < \infty € je konečná výstupní abeceda (množina výstupních symbolů), € S : \left| S \right| < \infty € je konečná množina vnitřních stavů, € S_0 : S_0 \in S € je výchozí vnitřní stav, € \delta : S \times X \to S € je přechodová funkce a € \omega : S \times X \to Y € je výstupní funkce.

### Kdo byl "George H. Mealy"

George H. Mealy patřil k průkopníkům stavových automatů. Jeho koncept se poprvé objevil v díle "A Method for Synthesizing Sequential Circuits", které publikoval v roce 1955.
