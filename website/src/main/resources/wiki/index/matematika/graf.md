## Teorie grafů

Teorie grafů je [matematická](wiki/matematika) disciplína, která zkoumá vlastnosti struktur zvaných **grafy**. Graf je definován velmi obecně, a to jako dvojice disjunktních [množin](wiki/mnozina) **uzlů** (vrcholů, vertex-vertices) a **hran** (edge-edges), přičemž hrana vždy spojuje právě dva uzly (které nemusí být různé). Teorie neříká vůbec nic o tom, co přesně uzly a hrany jsou - jejich interpretace je určena až problémem, který se pomocí teorie grafů řeší. To umožňuje práci ve velmi obecné rovině a možnost využití obecných poznatků získaných při řešení jednoho problému v úloze jiné.

Grafy se široce uplatňují při modelování a studiu různých problémů, například ve výpočetní technice či sociologii. V [informatice](wiki/informatika) se používají jako [datové struktury](wiki/datova-struktura) modelující entity a vztahy mezi nimi. [Algoritmy](wiki/algoritmus), které s nimi pracují, se nazývají [grafové algoritmy](wiki/grafovy-algoritmus).

### Historie

První úlohou, která se zapsala do historie teorie grafů, bylo **sedm mostů města Královce** (Königsberg). Otázkou bylo, zda je možné projít každým mostem ve městě právě jednou a vrátit se zpět do původního místa. Úspěšným řešitelem byl až **Leonhard Euler** (1707-1783), který v roce 1736 [matematicky dokázal](http://eulerarchive.maa.org//docs/originals/E053.pdf), že to možné není.

![sedm mostů města Královce jako mapa](konigsberg.gif)

```dot:graph
{rank=same;1;2;3;}
1--2
2--1
2--3
3--2
4--1
4--2
4--3
```

Poté se grafy na dlouhou dobu ocitly mimo zájem matematiků. Vrátili se k nim až **Gustav Kirchhoff** (1824-1887), **Arthur Cayley** (1821-1895) a **sir William Hamilton** (1805-1865). Nejznámějším problémem té doby byla **úloha čtyř barev**- otázka, zda lze každou mapu obarvit pomocí čtyř barev tak, aby žádné dva sousední státy neměly stejnou barvu. Tento problém byl však kompletně vyřešen až v roce 1976.

### Definice 1

Graf je dvojice:

€€ G = (V, E); \forall e \in E: e = (a,b); a,b \in V €€

- *V* - neprázdná množina uzlů (disjunktní s množinou *E*)
- *E* - množina hran (disjunktní s množinou *V*)

### Definice 2

Graf je trojice:

€€ G = (V, E, R); R: E \to V \times V €€

- *V* - neprázdná množina uzlů (disjunktní s množinou *E*)
- *E* - množina hran (disjunktní s množinou *V*)
- *R* - incidenční relace (zobrazení z množiny hran do množiny uzlů)

### Základní pojmy

- Jakékoliv dva uzly, které jsou spojeny hranou, nazýváme **sousední uzly**.
- Hrana, která vede z uzlu *u* do téhož uzlu *u*, se nazývá **smyčka**.
- Hrany, které spojují stejnou dvojici uzlů, se nazývají **rovnoběžné hrany**.
- Zobrazení z množiny hran do množiny uzlů se nazývá **incidenční relace**.
- Je-li hrana *h* v incidenční relaci s uzlem *u*, říká se o uzlu *u*, že s hranou *h* **inciduje**.
- Uzly, které neincidují se žádnou hranou, se nazývají **izolované uzly**.

#### Podgraf (subgraph)

Podgraf *P* grafu *G* je takový graf, jehož množina uzlů je podmnožinou množiny uzlů grafu *G* a zároveň množina jeho hran je podmnožinou množiny hran grafu *G*. Musí však platit, že se v podgrafu *P* nachází jen ty hrany, jejichž krajní uzly jsou obsaženy v množině uzlů podgrafu *P* (tzn. žádné hrany "nevisí ve vzduchu").

#### Faktor (factor)

Faktor grafu *G* je takový jeho podgraf, který obsahuje všechny jeho uzly (tzn. množiny jejich uzlů se rovnají a množina hran faktoru je podmnožinou hran grafu *G*).

#### Stupeň uzlu (degree)

Stupeň uzlu *d(u)* je počet hran, které s uzlem *u* incidují. Smyčka zvyšuje stupeň uzlu o dvě.

U orientovaných grafů lze rozlišovat stupeň vstupní *d-* (indegree) a stupeň výstupní *d+* (outdegree). Vstupní stupeň u orientovaného grafu udává počet hran, které jsou orientovány směrem **do uzlu**, zatímco výstupní stupeň udává, kolik hran je orientováno směrem **z uzlu ven**.

€€ d(v) = d^{+}(v) + d^{-}(v) €€

Součet stupňů všech uzlů v grafu je roven dvojnásobku počtu hran (handshaking lemma). Důsledkem toho je, že v každém grafu se nachází sudý počet uzlů lichého stupně.

€€ \sum_{\forall v \in V} d(v) = 2 |E| €€

#### Sled (walk)

Sled mezi uzly *u1* a *un* je posloupnost uzlů a hran:

€€ S = (u_1, h_1, u_2, h_2, ..., u_n) €€

Uzel *u1* se nazývá **počáteční uzel** a uzel *un* je **uzel koncový**. Pokud je počáteční uzel shodný s koncovým uzlem, nazývá se sled **uzavřeným**. V ostatních případech jde o sled **otevřený**. Sled má i orientovanou variantu, která respektuje orientaci hran.

#### Tah (trail)

Tah grafu je takový jeho **sled**, ve kterém se neopakují žádné hrany. Tah má i orientovanou variantu, která respektuje orientaci hran.

#### Eulerův tah (Eulerian trail)

Eulerovým tahem v souvislém grafu *G* se nazývá **tah** obsahující všechny hrany grafu *G*. Z definice vyplývá, že budou všechny hrany grafu navštíveny právě jednou. Eulerův tah má i orientovanou variantu, která respektuje orientaci hran.

- Souvislý graf obsahuje neorientovaný Eulerův tah právě tehdy, když tento graf obsahuje dva uzly lichého stupně a zbytek uzlů má stupeň sudý.
- Souvislý graf obsahuje orientovaný Eulerův tah právě tehdy, když tento graf obsahuje jeden uzel, jehož výstupní stupeň je o jedna vyšší než jeho vstupní stupeň, dále jeden uzel, jehož výstupní stupeň je o jedna nižší než jeho vstupní stupeň a zbývající uzly mají svůj vstupní a výstupní stupeň shodný.

Graf, který obsahuje Eulerův tah, se nazývá **Eulerův graf**.

#### Eulerův okruh (Eulerian circuit)

Eulerův okruh je uzavřený **Eulerův tah** - skončí v uzlu, ve kterém začal.

- Graf obsahuje neorientovaný Eulerův okruh právě tehdy, když mají všechny uzly grafu sudý stupeň.
- Graf obsahuje orientovaný Eulerův okruh právě tehdy, když mají všechny uzly shodný svůj vstupní a výstupní stupeň.

#### Cesta (path)

Cestou v grafu je takový jeho **tah**, ve kterém se neopakují žádné uzly. Každý uzel tedy inciduje nejvýše se dvěma hranami tohoto **tahu**. Cesta má i orientovanou variantu, která respektuje orientaci hran.

#### Hamiltonova cesta (Hamiltonian path)

Hamiltonova cesta v grafu *G* je **cesta** obsahující každý uzel grafu *G*.

#### Kružnice (circle)

Kružnicí se nazývá každá uzavřená **cesta**. Kružnice má i orientovanou variantu, která respektuje orientaci hran a nazývá se **cyklus**.

#### Hamiltonovská kružnice (Hamiltonian circle)

Hamiltonovská kružnice v grafu je taková **kružnice**, která projde všemi jeho uzly právě jednou. Graf, který obsahuje Hamiltonskou kružnici, se nazývá **Hamiltonovský graf**.

#### Souvislost (connection)

Souvislým grafem se nazývá takový graf, mezi jehož libovolnými dvěma uzly existuje **sled**. Silně souvislý je graf právě tehdy, když v něm pro každou dvojici uzlů *a*,*b* existuje **orientovaná cesta** z *a* do *b* a nazpět z uzlu *b* do *a*.

#### Komponenta (component)

Komponentou grafu se nazývá každý jeho maximální **souvislý podgraf**. Silnou komponentou orientovaného grafu se nazývá každý jeho maximální **silně souvislý podgraf**.

#### Strom (tree)

Stromem se nazývají **souvislé grafy**, které neobsahují **kružnice**. Odebráním jedné hrany je porušena **souvislost**, přidáním jedné hrany vznikne **kružnice**. Strom je minimální souvislý graf na daných vrcholech. Pro stromy platí vztah, který udává, že počet hran je o jedna menší než počtu uzlů.

€€ |E| = |V| - 1 €€

Existují i **orientované stromy**, jejichž hrany mají přiřazenou orientaci.

Graf, jehož komponenty jsou stromy, nazýváme **les**.

#### Kořenový strom (rooted tree)

Kořenový strom je takový **orientovaný strom**, jehož uzly mají vstupní stupeň roven jedné až na jeden uzel, který má vstupní stupeň roven nule. Tento speciální uzel se nazývá kořen (root).

€€ \exists r \in V: d^{-}(r) = 0; \forall v \in V \setminus \{r\}: d^{-}(v) = 1 €€

#### Kostra (spanning tree)

Kostrou grafu se rozumí takový jeho **faktor**, který je **stromem**.

#### Úplný graf (complete graph)

Úplný graf je obyčejný graf, ve kterém jsou všechny možné dvojice uzlů spojeny hranou. Počet hran *E* v úplném grafu je roven kombinačnímu číslu *V* nad *2*, kde *V* je počet uzlů.

€€ |E| = \binom{|V|}{2} = \frac{|V|(|V|-1)}{2} €€

### Klasifikace grafů

#### Podle smyček a rovnoběžných hran

- **multigrafy** (multigraph) - mohou obsahovat smyčky i rovnoběžné hrany
- **prosté grafy** - mohou obsahovat smyčky, neobsahují rovnoběžné hrany
- **obyčejné grafy** (simple graph) - neobsahují smyčky ani rovnoběžné hrany

#### Podle orientace hran

Každá hrana grafu je určena dvojicí uzlů. Pokud je tato dvojice **neuspořádaná**, nazývá se graf **neorientovaným**. U těchto grafů není pro definici hran důležité pořadí incidujících uzlů. Jestliže se však hrana zavede jako **uspořádaná** dvojice uzlů, nazývá se výsledný graf **orientovaným**. Zakresluje-li se orientovaný graf, pro názornost se u hran kreslí šipky, aby bylo na první pohled zřejmé, jak jsou uzly ve dvojici uspořádány.

#### Podle množství hran a uzlů

- **řídké grafy** - obsahují "relativně" málo hran vzhledem k počtu uzlů (vágní pojem)
- **husté grafy**  - obsahují "relativně" mnoho hran vzhledem k počtu uzlů (vágní pojem)

#### Podle uspořádání uzlů a hran

**Bipartitní graf** je takový graf, jehož množinu vrcholů je možné rozdělit na dvě disjunktní množiny (tzv. **parity**) tak, že žádné dva vrcholy ze stejné množiny nejsou spojeny hranou. Všechny bipartitní grafy mají chromatické číslo rovno 2. Bipartitní je každý strom a grafy, které neobsahují kružnici liché délky. Analogicky lze definovat **tripartitní graf** a obecně i **k-partitní grafy**.

Obsahuje-li k-partitní graf všechny možné hrany, které neporušují výše uvedené podmínky, označuje se jako **úplný k-partitní graf**. Úplné k-partitní grafy se zkráceně zapisují jako *K m1,m2,...,mn*, kde *m1*, *m2*, ..., *mn* jsou mohutnosti jednotlivých disjunktních množin.

#### Podle planarity

**Planární graf** je takový graf, který lze v rovině nakreslit bez křížení hran (tzn. hrany se protínají pouze ve společných uzlech). Teorém Kuratowského říká, že konečný graf je planární právě tehdy, když nemá žádný podgraf homeomorfní s úplným grafem *K5* nebo úplným bipartitním grafem *K3,3*.

### Izomorfizmus

Dva grafy jsou izomorfní právě tehdy, když existuje vzájemně jednoznačné zobrazení (bijekce) *F* takové, že platí:

€€
\begin{align*}
\exists F: V(G) &\to V(G'): \\
\{a,b\} \in E(G) &\leftrightarrow \{F(a),F(b)\} \in E(G')
\end{align*}
€€

Lepšímu pochopení izomorfizmu by mohl pomoci následující výklad: Představte si, že jsou hrany nepřetrhnutelné gumičky a je povoleno je neomezeně natahovat, natáčet a přesouvat uzly. Lze-li tímto způsobem vytvořit jeden graf z druhého, jsou izomorfní.

Nutné (nikoliv postačující) podmínky pro dva izomorfní grafy:

- mají stejný počet uzlů
- mají stejný počet hran
- jejich stupně uzlů seřazené do neklesající posloupnosti jsou shodné
- každý uzel grafu má sousedy stejného stupně jako odpovídající uzel v grafu izomorfním

### Homeomorfizmus

Dva grafy jsou homeomorfní právě tehdy, když je možné jeden z druhého vytvořit postupným přidáváním uzlů stupně 2 (subdivisions). Tato operace zachovává planaritu.

### Příklady grafů

Neorientovaný graf:

```dot:graph
rankdir=LR
A -- B -- C
```

Neorientovaný multigraf:

```dot:graph
rankdir=LR
A -- B -- C
B -- C
A -- C
```

Orientovaný graf:

```dot:digraph
rankdir=LR
A -> B -> C
D -> A
E -> A
```

### Reference

- Dr. Jeanne Stynes, Computer Science
- http://uai.fme.vutbr.cz/seda/teorie-grafu/
- http://www.cam.zcu.cz/~ryjacek/students/DMA/skripta/
- http://mathworld.wolfram.com/Graph.html
- http://teorie-grafu.elfineer.cz/
- http://eulerarchive.maa.org//docs/originals/E053.pdf
