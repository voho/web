## Základní kombinatorické vzorce

Základními pojmy kombinatoriky jsou **variace** (výběr uspořádané k-tice z [množiny](wiki/mnozina)) a **kombinace** (výběr podmožiny). Speciální případ variace se nazývá **permutace**. Kombinace se od variací liší tím, že nezáleží na pořadí vybraných prvků.

### Přehled

| Výběr | Bez opakování | S opakováním
|---
| uspořádaný | variace bez opakování, permutace<br/> €€ V(k,n) = \frac{n!}{(n-k)!} €€ | variace s opakováním<br/> €€ V'(k,n) = n^k €€
| neuspořádaný | kombinace bez opakování<br/> €€ K(k,n) = \binom{n}{k} €€ | kombinace s opakováním<br/> €€ K'(k,n) = \binom{n+k-1}{k} €€

### Variace (uspořádaný výběr) bez opakování

Variace *k*-té třídy z *n* prvků bez opakování je uspořádaná *k*-tice sestavená z těchto prvků tak, že každý se v ní vyskytuje nejvýše jednou. Počet všech různých *k*-tic, které lze takto utvořit, je:

€€ V(k,n) = \frac{n!}{(n-k)!} €€

Na první pozici k-tice je možné dát *n* prvků, na druhou pozici již pouze *n-1* prvků (jeden prvek je již obsazený) a tak dále. Vychází faktoriál, kterému je nutné useknout přebytečný "ocas", pokud počet vybíraných prvků *k* je menší než *n*).

€€ V(k,n) = n \cdot V(k-1,n-1) €€
€€ V(k,n+1) = V(k,n) + k \cdot V(k-1,n) €€

#### Příklady

1. Kolik tříbarevných vlajek lze vyrobit ze šesti různobarevných pruhů látek tak, aby se žádná barva neopakovala? 
1. Kuchař umí uvařit 15 druhů jídla. Kolika způsoby lze vytvořit jídelníček na týden tak, aby se žádné jídlo celý týden neopakovalo?

### Permutace bez opakování

Permutace je speciální případ variace bez opakování, kde *n* = *k*. Počet permutací odpovídá počtu všech možných uspořádání všech prvků zadané množiny.

€€ P(k) = V(k,k) = \frac{k!}{(k-k)!} = k! €€

#### Příklady

1. Kolik různých výsledků může mít běžecký závod s 20 účastníky? 
1. Kolika možnými způsoby je možné seřadit 10 knih na polici?

### Variace (uspořádaný výběr) s opakováním

Variace *k*-té třídy z *n* prvků s opakováním je uspořádaná *k*-tice sestavená z těchto prvků a každý se v ní může vyskytovat i vícenásobně. Počet všech různých *k*-tic, které lze takto utvořit, je:

€€ V'(k,n) = n^k €€

Na první pozici k-tice je možné dát *n* prvků, na druhou pozici opět *n* prvků a tak dále. 

#### Příklady

1. Kolik trojciferných čísel lze vytvořit z čísel 1, 2, 3? 
1. Kolik existuje binárních čísel délky 10?

### Kombinace (neuspořádaný výběr) bez opakování

Kombinace *k*-té třídy z *n* prvků bez opakování je neuspořádaná *k*-tice sestavená z těchto prvků tak, že každý se v ní vyskytuje nejvýše jednou. Počet všech různých *k*-tic, které lze takto utvořit, je:

€€ K(k,n) = \binom{n}{k} = \binom{n}{n-k} = \frac{n!}{k!(n-k)!} €€

Počet kombinací je počet variací snížený o k-tice, které obsahují tytéž prvky, jen v jiném pořadí. Tyto k-tice se sjednotí do jedné množiny. Těchto k-tic je *k!* a proto je k-prvkových podmnožin *k!*-krát méně než variací.

€€ K(k,n) = \frac{V(k,n)}{k!} = \frac{n!}{(n-k)!} \cdot \frac{1}{k!} €€

#### Příklady

1. Ve třídě je 28 žáků. Kolika způsoby z nich lze vybrat dva zástupce třídy? 
1. Kolik dvouprvkových podmnožin má množina {1, 2, 3, 4}?

### Kombinace (neuspořádaný výběr) s opakováním

Kombinace *k*-té třídy z *n* prvků s opakováním je neuspořádaná *k*-tice sestavená z těchto prvků tak a každý se v ní může vyskytovat i vícenásobně. Počet všech různých *k*-tic, které lze takto utvořit, je:

€€ K'(k,n) = \binom{n+k-1}{k} = \frac{(n+k-1)!}{k!(n-1)!} €€

#### Příklady

1. Kolika způsoby si mohu objednat 5 kusů zboží z e-shopu, který má v nabídce 10 položek a mohu si objednat více kusů od jedné položky?

### Reference

- předmět X01MVT na FEL ČVUT
- M. Navara: Pravděpodobnost a matematická statistika
- V. Rogalewicz: Pravděpodobnost a statistika pro inženýry
- http://carolina.mff.cuni.cz/~jana/kombinatorika/
