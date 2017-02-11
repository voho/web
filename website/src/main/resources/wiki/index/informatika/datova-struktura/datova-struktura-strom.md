## Strom (tree)

Stromem se nazývají souvislé [grafy](wiki/graf), které neobsahují kružnice. Strom je tedy minimální souvislý graf na daných vrcholech. Stromy se v informatice velmi často vyskytují, protože reprezentují základní koncept, kterým je **hierarchie**. Se stromy se setkáme všude tam, kde je potřeba rychle vyhledávat, reprezentovat strukturovaná data nebo rozhodovat. Strom je i přirozeným modelem **rekurze**.

![vznik označení "strom"](ds-tree-intro.png)

### Názvosloví

- druhy uzlů
  - **kořen** (root)
  - **vnitřní uzel** (inner node) = uzel, který není kořenem, ani listem
  - **list** (leaf node, external node) = uzel, který nemá žádné potomky
  - **rodič** (parent node) = uzel, který přímo předchází daný uzel na cestě od listu ke kořeni
  - **potomek** (child node) = uzel, který přímo následuje za daným uzlem na cestě od kořene k listu
  - **sourozenec** (sibling) = jako sourozenci se označují uzly se stejným rodičem
  - **předek** (ancestor node, predecessor node) = uzel, který leží před daným uzlem na cestě ke kořeni (nejbližší předek je rodič)
  - **následník** (successor node) = uzel, který leží za daným uzlem na cestě od kořeni k libovolnému listu (nejbližší následník je potomek)
  - **hloubka** (depth) = hloubka stromu je délka nejdelší cesty od kořene k listu, přičemž prázdný strom má definovánu hloubku jako -1
  - **úroveň** (level) = většinou se používá ve významu množiny uzlů, které se nachází ve stejné vzdálenosti od kořene, počítáno dle počtu uzlů
- struktury
  - **podstrom** (subtree) = podgraf stromu, který je také stromem (obecně se nejčastěji setkáváme s podstromy tvořenými tak, že se vezme nějaký uzel stromu jako nový kořen a zbytek struktury se zachová)
  - **větev** (branch) = každá cesta od kořene k listu

```dot:digraph
"kořen" -> N1;
"kořen" -> "rodič A";
"rodič A" -> A;
"rodič A" -> N2;
"rodič A" -> N3;
A -> "1. potomek A";
A -> "2. potomek A";
"1. potomek A" -> N4;
"1. potomek A" -> N5;
N3 -> N6;
N3 -> N7;
N3 -> N8;
"rodič A" [color=forestgreen];
A [color=red];
"1. potomek A" [color=forestgreen];
"2. potomek A" [color=forestgreen];
N1 [label=""];
N2 [label=""];
N3 [label=""];
N4 [label=""];
N5 [label=""];
N6 [label=""];
N7 [label=""];
N8 [label=""];
```

```dot:digraph
"kořen (předchůdce A)" -> "předchůdce A";
"předchůdce A" -> A;
"předchůdce A" -> N1;
A -> "1. následník A";
A -> "2. následník A";
"1. následník A" -> "3. následník A";
"1. následník A" -> "4. následník A";
A [color=red];
N1 [label=""];
"1. následník A" [label="následník A"];
"2. následník A" [label="následník A"];
"3. následník A" [label="následník A"];
"4. následník A" [label="následník A"];
```

### Průchody stromem

Obecně lze stromem, stejně jako kterýmkoliv jiným [grafem](wiki/graf), procházet následujícími klasickými průchody:

- [procházení do hloubky](wiki/algoritmus-dfs)
- [procházení do šířky](wiki/algoritmus-bfs)

Další způsoby procházení jsou již specifické pro binární stromy:

- průchod **pre-order**: zpracuj uzel, zpracuj levý podstrom, zpracuj pravý podstrom
- průchod **in-order**: zpracuj levý podstrom, zpracuj uzel, zpracuj pravý podstrom
- průchod **post-order**: zpracuj levý podstrom, zpracuj pravý podstrom, zpracuj uzel 
- **eulerovský průchod**: zpracuj levý podstrom, eulerovsky projdi levý podstrom, zpracuj uzel, eulerovsky projdi pravý podstrom, zpracuj pravý podstrom (každý uzel je navštíven 3x)

### Druhy stromů

- **binární strom** (binary tree) = strom, ve kterém má každý uzel nejvýše dva potomky
- **n-ární strom** (n-ary tree) = strom, ve kterém má každý uzel nejvýše n potomků
- **úplný binární strom** (complete binary tree) = binární strom, ve kterém jsou zaplněné všechny úrovně kromě poslední, která vyplněná být nemusí
- **plný binární storm** (full binary tree) = binární strom, ve kterém všechny uzly kromě listů mají právě dva potomky

### Vlastnosti

Pro stromy platí všechny poznatky jako pro [grafy](wiki/graf), protože strom je jen specifický typ grafu. Přesto z jeho specifické struktury plynou některé další zajímavé vztahy:

- € |E|=|V|−1 € (počet hran je o jednu menší než počet uzlů)
- přidáním libovolné hrany vznikne cyklus
- odebráním libovolné hrany se strom rozdělí na dva menší stromy
- pro binární stromy platí:
  - v hloubce € d € je nanejvýš € 2^d € uzlů
  - výška stromu € h € leží mezi € \log_2(n+1)-1 € a € 2^{h+1}-1 €
- pro plné binární stromy platí:
  - výška stromu € h € s € n € uzly je € log_2(n+1)-1 €
  - počet listů je roven počtu vnitřních uzlů + 1
  - úplný binární strom o výšce € h € obsahuje € 2^h € listů, € 2^h-1 € vnitřních uzlů, celkem tedy € 2^{h+1}-1 € uzlů