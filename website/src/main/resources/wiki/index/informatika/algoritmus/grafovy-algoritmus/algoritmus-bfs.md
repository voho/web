## Prohledávání do šířky (Breadth-first Search)

Prohledávání do šířky (BFS) je základní grafový algoritmus pro procházení grafu. Tento algoritmus systematicky prochází graf od počátečního uzlu, pro dočasné ukládání nenavštívených uzlů používá frontu.

### Kroky algoritmu

1. Všem uzlům *x* nastav příznak **VISIT**(*x*) = **FALSE**.
1. Počátečnímu uzlu *s* nastav příznak **VISIT**(*x*) = **TRUE**.
1. Vlož počáteční uzel do fronty.
1. Vezmi první uzel z fronty a označ jej *u*.
1. Každý uzel *v*, do kterého vede hrana z uzlu *u* a jeho příznak **VISIT**(*v*) je roven **FALSE**, přidej na konec fronty a změň jeho příznak **VISIT**(*v*) = **TRUE**.
1. Krok 4 opakuj tak dlouho, dokud není fronta prázdná.

### Příklad

Mějme následující graf. Počátečním uzlem je *START*, sousední uzly budou do fronty přidávány v abecedním pořadí.

```dot:graph
rankdir=LR
{rank=same; START; G;}
{rank=same;}
START--E
START--G
E--B
E--D
E--C
D--F
C--G
```

Algoritmus bude postupovat v následujících krocích:

| Krok | Fronta (první prvek: vlevo) | Navštívené uzly
|---
| 1 | START | -
| 2 | E, G | START
| 3 | G, B, C, D | START, E
| 4 | B, C, D | START, E, G
| 5 | C, D | START, E, G, B
| 6 | D | START, E, G, B, C
| 7 | F | START, E, G, B, C, D
| 8 | - | START, E, G, B, C, D, F

Uzly byly navštíveny v tomto pořadí: *START*, *E*, *G*, *B*, *C*, *D*, *F*

```dot:graph
rankdir=LR
{rank=same; START; G;}
{rank=same;}
START--E
START--G
E--B
E--D
E--C
D--F
C--G

START[fillcolor=khaki]
```

```dot:graph
rankdir=LR
{rank=same; START; G;}
{rank=same;}
START--E
START--G
E--B
E--D
E--C
D--F
C--G

START[fillcolor=gray]
E[fillcolor=khaki]
G[fillcolor=khaki]
```

```dot:graph
rankdir=LR
{rank=same; START; G;}
{rank=same;}
START--E
START--G
E--B
E--D
E--C
D--F
C--G

START[fillcolor=gray]
E[fillcolor=gray]
G[fillcolor=gray]
D[fillcolor=khaki]
B[fillcolor=khaki]
C[fillcolor=khaki]
```

```dot:graph
rankdir=LR
{rank=same; START; G;}
{rank=same;}
START--E
START--G
E--B
E--D
E--C
D--F
C--G

START[fillcolor=gray]
E[fillcolor=gray]
G[fillcolor=gray]
D[fillcolor=gray]
B[fillcolor=gray]
C[fillcolor=gray]
F[fillcolor=khaki]
```

```dot:graph
rankdir=LR
{rank=same; START; G;}
{rank=same;}
START--E
START--G
E--B
E--D
E--C
D--F
C--G

START[fillcolor=gray]
E[fillcolor=gray]
G[fillcolor=gray]
D[fillcolor=gray]
B[fillcolor=gray]
C[fillcolor=gray]
F[fillcolor=gray]
```

### Složitost

Asymptotická složitost algoritmu je € O(V+E) €, kde €V€ je počet uzlů a €E€ je počet hran.