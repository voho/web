## Prohledávání do hloubky (Depth-first Search)

Prohledávání do hloubky (DFS) je základní grafový algoritmus pro procházení grafu. Tento algoritmus systematicky prochází graf od počátečního uzlu, pro dočasné ukládání nenavštívených uzlů používá zásobník.

### Kroky algoritmu

1. Všem uzlům *x* nastav příznak **VISIT**(*x*) = **FALSE**.
1. Počátečnímu uzlu *s* nastav příznak **VISIT**(*x*) = **TRUE**.
1. Vlož počáteční uzel na zásobník.
1. Vezmi uzel z vrcholu zásobníku a označ jej *u*.
1. Každý uzel *v*, do kterého vede hrana z uzlu *u* a jeho příznak **VISIT**(*v*) je roven **FALSE**, vlož na zásobník a změň jeho příznak **VISIT**(*v*) = **TRUE**.
1. Krok 4 opakuj tak dlouho, dokud není zásobník prázdný.

### Příklad

Mějme následující graf. Počátečním uzlem je *START*, sousední uzly budou na zásobník vkládány v abecedním pořadí.

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

| Krok | Zásobník (vrchol: vlevo) | Navštívené uzly
|---
| 1 | START | -
| 2 | G, E | START
| 3 | C, E | START, G
| 4 | E | START, G, C
| 5 | D, B | START, G, C, E
| 6 | F, B | START, G, C, E, D
| 7 | B | START, G, C, E, D, F
| 8 | - | START, G, C, E, D, F, B

Uzly byly navštíveny v tomto pořadí: *START*, *G*, *C*, *E*, *D*, *F*, *B*

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
G[fillcolor=gray]
C[fillcolor=khaki]
E[fillcolor=khaki]
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
G[fillcolor=gray]
C[fillcolor=gray]
E[fillcolor=khaki]
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
G[fillcolor=gray]
C[fillcolor=gray]
E[fillcolor=gray]
D[fillcolor=khaki]
B[fillcolor=khaki]
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
G[fillcolor=gray]
C[fillcolor=gray]
E[fillcolor=gray]
D[fillcolor=gray]
F[fillcolor=khaki]
B[fillcolor=khaki]
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
G[fillcolor=gray]
C[fillcolor=gray]
E[fillcolor=gray]
D[fillcolor=gray]
F[fillcolor=gray]
B[fillcolor=khaki]
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