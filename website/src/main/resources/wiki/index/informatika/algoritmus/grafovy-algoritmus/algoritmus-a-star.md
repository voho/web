## Algoritmus A-Star

Algoritmus A-Star je grafový algoritmus sloužící k nalezení "nejkratší" cesty v ohodnoceném grafu vedoucí ze zadaného počátečního uzlu do cílového. Poprvé jej roku 1968 popsali **P. Hart**, **N. Nilsson** a **B. Raphael**. Jeho vstupem je ohodnocený graf, počáteční uzel a cílový uzel, výstupem je nejkratší cesta z počátečního uzlu do cílového, nebo zpráva o tom, že žádná taková cesta neexistuje.

Algoritmus je v principu shodný s prohledáváním do šířky s tím rozdílem, že namísto obyčejné fronty používá **frontu prioritní**, ve které jsou cesty seřazeny podle hodnoty speciální funkce *f*. Tato funkce je definována pro každou cestu *p* a je součtem tzv. **heuristické funkce** (*h*) posledního uzlu cesty *p* a její zbývající délky (*g*). Čím je hodnota funkce *f(p)* nižší, tím vyšší má daná cesta *p* prioritu. Stručně řečeno, algoritmus se dívá do "minulosti" (jak daleko musel ujít, než na konec cesty *p* došel) i do "budoucnosti" (jak daleko ještě zhruba zbývá ujít z posledního uzlu cesty *p* do cíle).

Dále se předpokládá, že cesty ve frontě neobsahují kružnice a pro každý cílový uzel se v ní nachází nanejvýš jedna cesta, a to ta nejkratší doposud nalezená.

Pořadí cest ve frontě je určeno následující funkcí:

€€ f(x) = h(x) + g(x) €€

- *f(x)* - předpokládaná délka cesty *x*
- *h(x)* - hodnota heuristické funkce pro koncový uzel cesty *x*
- *g(x)* - délka cesty *x*

```dot:graph
rankdir = LR
S [fillcolor=beige]
S--A [label=" 1"]
A--B [label=" 5"]
B [label="B, h=21"]
```

Předpokládaná délka této cesty je (1 + 5) + 21 = 27.

Heuristická funkce musí splňovat důležité podmínky - musí být větší než nula a tzv. **přípustná** (admissible). To znamená, že její hodnota pro libovolný uzel musí být **nižší nebo rovna** skutečné vzdálenosti z daného uzlu do cíle. Jinými slovy, její hodnota nikdy nemůže být větší než je skutečná vzdálenost z daného uzlu do cíle.

€€ h(x) \leq h(y) + \mathrm{cost}(x,y) €€

Heuristická funkce vzniká na základě (alespoň hrubé) znalosti struktury problému. Hledá-li se tedy například nejkratší cesta z města *S* do města *G*, lze jako heuristickou funkci města *X* použít zbývající vzdálenost z města *X* do města *G*. V tomto (idealizovaném) případě bude hodnota heuristické funkce přibližně rovna skutečné hodnotě.

### Kroky algoritmu

- Vytvoř prázdnou množinu cest *F*.
- Do množiny *F* vlož cestu nulové délky obsahující počáteční uzel *s*.
- Dokud není množina *F* prázdná, opakuj:
 - Z množiny *F* vyber nejkratší cestu *p* (s nejnižší hodnotou *f(p)*) a odeber ji.
 - Končí-li cesta v cílovém uzlu, vrať ji a ukonči výpočet.
 - Vytvoř nové cesty použitím všech možných operátorů na koncový uzel cesty *p*, které neobsahují smyčky.
 - Jestliže dvě cesty končí ve stejném uzlu, odstraň všechny kromě té nejkratší (s nejnižší hodnotou *f(x)*).
 - Přidej cestu *p* do množiny *F*.
- Je-li množina *F* prázdná, oznam, že žádná cesta z počátečního do cílového uzlu neexistuje.

### Příklad

#### Inicializace

Do množiny cest *F* je vložena cesta nulové délky, která začíná i končí počátečním uzlem *S*. U této cesty nemá smysl počítat jakoukoliv délku, protože je zaručeno, že bude v dalším kroku vybrána.

```dot:graph
S[fillcolor=beige]
G[fillcolor=beige]
S--A [label=" 5"]
S--B [label=" 1"]
S--C [label=" 10"]
B--D [label=" 5"]
A--C [label=" 6"]
C--D [label=" 1"]
C--E [label=" 1"]
C--G [label=" 5"]
E--G [label=" 2"]

B[label="B, h=3"]
A[label="A, h=2"]
C[label="C, h=1"]
D[label="D, h=2"]
E[label="E, h=1"]
G[label="G, h=0"]
{rank=same; S;B;}
{rank=same; A;C;D;}
{rank=same; E;G;}
```

V tomto ohodnoceném grafu je počátečním uzlem *S* a cílovým *G*.
U grafu je třeba zkontrolovat, zda je použitá heuristická funkce přípustná. V tomto případě to platí, protože je její hodnota vždy menší než hodnota skutečná.

| Množina cest F | (Délka cesty) + heuristika | Poznámka
|---|---|---
| S | - | -

#### Krok 1

Z množiny *F* je vybrána jediná cesta, která se v ní nachází. Nekončí v cílovém uzlu, výpočet tedy pokračuje. Z jejího posledního uzlu *S* lze pokračovat do uzlů *A*, *B*, *C*. Nové cesty budou vloženy do množiny *F*.

| Množina cest F | (Délka cesty) + heuristika | Poznámka
|---|---|---
| S - B | (1) + 3 = 4 | -
| S - A | (5) + 2 = 7 | -
| S - C | (10) + 1 = 11 | -

#### Krok 2

Z množiny *F* je vybrána nejkratší cesta *S-B*. Nekončí v cílovém uzlu, výpočet tedy pokračuje. Z jejího posledního uzlu *B* lze pokračovat do uzlu *D*. Nová cesta bude vložena do množiny *F*.

| Množina cest F | (Délka cesty) + heuristika | Poznámka
|---|---|---
| S - A | (5) + 2 = 7 | -
| S - B - D | (1 + 5) + 2 = 8 | -
| S - C | (10) + 1 = 11 | -

#### Krok 3

Z množiny *F* je vybrána nejkratší cesta *S-A*. Nekončí v cílovém uzlu, výpočet tedy pokračuje. Z jejího posledního uzlu *A* lze pokračovat do uzlu *C*. Množina *F* však již cestu končící v uzlu *C* obsahuje, a tak bude zachována jen ta nejkratší z nich. V tomto případě je již známá cesta kratší, takže nebude do množiny *F* přidána.

| Množina cest F | (Délka cesty) + heuristika | Poznámka
|---|---|---
| S - B - D | (1 + 5) + 2 = 8 | -
| S - C | (10) + 1 = 11 | -
| S - A - C | (5 + 6) + 1 = 12 | do *C* známe kratší cestu, tuto odebrat

#### Krok 4

Z množiny *F* je vybrána nejkratší cesta *S-B-D*. Nekončí v cílovém uzlu, výpočet tedy pokračuje. Z jejího posledního uzlu *D* lze pokračovat do uzlu *C*.Množina *F* však již cesty končící v uzlu *C* obsahuje, a tak bude zachována jen ta nejkratší z nich. V tomto případě je nová cesta kratší, takže bude do množiny *F* vložena, zatímco cesta *S-C* bude z množiny *F* odebrána.

| Množina cest F | (Délka cesty) + heuristika | Poznámka
|---|---|---
| S - B - D - C | (1 + 5 + 1) + 1 = 9 | -
| S - C | (10) + 1 = 11 | do *C* známe kratší cestu, tuto odebrat

#### Krok 5

Z množiny *F* je vybrána nejkratší cesta *S-B-D-C*. Nekončí v cílovém uzlu, výpočet tedy pokračuje. Z jejího posledního uzlu *C* lze pokračovat do uzlů *A*, *E*, *G*. Nové cesty budou vloženy do množiny *F*.

| Množina cest F | (Délka cesty) + heuristika | Poznámka
|---|---|---
| S - B - D - C - E | (1 + 5 + 1 + 1) + 1 = 9 | -
| S - B - D - C - G | (1 + 5 + 1 + 5) + 0 = 12 | -
| S - B - D - C - A | (1 + 5 + 1 + 6) + 2 = 15 | -

#### Krok 6

Z množiny *F* je vybrána nejkratší cesta *S-B-D-C-E*. Nekončí v cílovém uzlu, výpočet tedy pokračuje. Z jejího posledního uzlu *E* lze pokračovat do uzlů *G*. Množina *F* však již cesty končící v uzlu *G* obsahuje, a tak bude zachována jen ta nejkratší z nich. V tomto případě je nová cesta kratší, takže bude do množiny *F* vložena, zatímco cesta *S-B-D-C-G* bude z množiny *F* odebrána.

| Množina cest F | (Délka cesty) + heuristika | Poznámka
|---|---|---
| S - B - D - C - E - G | (1 + 5 + 1 + 1 + 2) + 0 = 10 | -
| S - B - D - C - G | (1 + 5 + 1 + 5) + 0 = 12 | do *C* známe kratší cestu, tuto odebrat
| S - B - D - C - A | (1 + 5 + 1 + 6) + 2 = 15 | -

#### Krok 7

Z množiny *F* je vybrána nejkratší cesta *S-B-D-C-E-G*. Ta končí v cílovém uzlu, výpočet tedy končí.

#### Výsledek

Nejkratší nalezená cesta je *S-B-D-C-E-G* s délkou 10. Tato cesta je výstupem algoritmu.

```dot:graph
S[fillcolor=beige]
G[fillcolor=beige]
B[fillcolor=seagreen1]
D[fillcolor=seagreen1]
C[fillcolor=seagreen1]
E[fillcolor=seagreen1]
S--A [label=" 5"]
S--B [label=" 1",color=seagreen,penwidth=3]
S--C [label=" 10"]
B--D [label=" 5",color=seagreen,penwidth=3]
A--C [label=" 6"]
C--D [label=" 1",color=seagreen,penwidth=3]
C--E [label=" 1",color=seagreen,penwidth=3]
C--G [label=" 5"]
E--G [label=" 2",color=seagreen,penwidth=3]

B[label="B, h=3"]
A[label="A, h=2"]
C[label="C, h=1"]
D[label="D, h=2"]
E[label="E, h=1"]
G[label="G, h=0"]
{rank=same; S;B;}
{rank=same; A;C;D;}
{rank=same; E;G;}
```

### Časová složitost

Asymptotická složitost algoritmu A-Star silně závisí na použité heuristické funkci, která ovlivňuje množství cest uvažovaných během výpočtu. Obecně se však dá říci, že jeho asymptotická složitost nikdy nebude horší než složitost prohledávání do šířky, což je € O(E + V) €, kde €V€ je počet uzlů a €E€ je počet hran.

### Podobnost s ostatními algoritmy

- **Prohledávání do šířky**
  - všechny hrany mají stejné ohodnocení, heuristická funkce je konstantní
  - *h(x)* = C
  - *cost(x,y)* = C
- **Uniform Cost Search** 
  - heuristická funkce je konstantní
  - *h(x)* = C
- **Greedy Search** 
  - všechny hrany mají stejné ohodnocení
  - *cost(x,y)* = C
  
### Implementace

```include:java
AStar.java
```

### Reference

- Dr. Jeane Stynes, Artificial Intelligence
- http://theory.stanford.edu/~amitp/GameProgramming/AStarComparison.html
- http://www.macs.hw.ac.uk/~alison/ai3notes/subsubsection2_6_2_3_2.html