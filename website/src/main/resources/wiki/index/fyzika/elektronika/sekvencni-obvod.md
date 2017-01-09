## Sekvenční obvod

Vedle kombinačních obvodů, jejichž výstup je závislý pouze na aktuálním vstupu, existují i obvody **sekvenční**. U těch je výstup dán nejen současným vstupem, ale i vstupy minulými (historií).

Základními stavebními bloky sekvenčních obvodů jsou logická hradla a klopné obvody, které slouží k uchovávání tzv. **vnitřního stavu**. Výstup pak závisí na kombinaci logických hodnot na vstupu a aktuálním vnitřním stavu.

```dot:digraph
node [shape = rect];
nodesep = 1;
I -> C -> O;
C -> M -> C;
{rank = same; I; C; O;}
I [label = "vstup", shape=none];
O [label = "výstup", shape=none];
C [label = "kombinační logika", style=filled, fillcolor=beige];
M [label = "paměť", shape="folder"];
```

### Popis součástí

#### Přechodová funkce

Přechodová funkce je realizována kombinačním obvodem, který generuje budoucí vnitřní stav na základě vstupu a současném vnitřním stavu.

#### Výstupní funkce

Výstupní funkce je realizována kombinačním obvodem, který se stará o správnou hodnotu na výstupu, a to buď v závislosti na vnitřním stavu (viz typ Moore), nebo na vnitřním stavu a vstupu zároveň (viz typ Mealy).

#### Paměťová část

Paměťová část je realizována klopnými obvody, nejčastěji synchronními klopnými obvody typu D. V paměťové části je uložen zakódovaný **vnitřní stav**.

### Postup při návrhu

1. Specifikace
1. Formalizace zadání
1. Zakódování vnitřních stavů
1. Graf přechodů
1. Tabulka přechodů a výstupů
1. Zakódování tabulky přechodů a výstupů
1. Vytvoření map
1. Minimalizace map
1. Odvození budících funkcí pro vstupy a výstupy
1. Realizace na úrovni hradel
1. Výpočet maximální hodinové frekvence
1. Testování, verifikace

### Reference

- Alan Clemens: The Principles of Computer Hardware, Second Edition
