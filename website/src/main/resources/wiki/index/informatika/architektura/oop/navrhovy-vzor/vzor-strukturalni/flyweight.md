## GoF: Flyweight (muší váha)

Návrhový vzor Flyweight (muší váha) umožňuje v některých případech ušetřit paměť zabranou velkým počtem podobných objektů. Principem je sdílení co největšího množství dat s ostatními podobnými objekty. Každý z mnoha objektů tak může obsahovat pouze odkaz na data sdílená mezi příbuznými objekty a navíc je jen o to, v čem se skutečně liší.

Pokud máme například mapu skládající se z různých druhů políček, lze každý druh reprezentovat jednou instancí obsahující společné vlastnosti celého druhu.

![příklad použití ve hře](flyweight.png)

Dalším příkladem může být reprezentace dokumentu v textovém editoru. Pokud například dokument obsahuje tisíc znaků *A*, není nutné uchovávat tisíc obrázků písmena *A* pro jeho vykreslení. Obrázek stačí načíst pouze jednou a potom se něj odkazovat.

```dot:digraph
rankdir=LR
nodesep=0.1
edgesep=0
edge[style=invis]
ratio=0.5

subgraph pool {
  rankdir=LR
  node[shape=doublecircle,margin=0,fillcolor=khaki]
  B->A B->N
}

subgraph cluster_0 {
  style="rounded,filled"
  fillcolor=gray
  color=gray
  node[shape=circle,margin=0]
  B1->A1->N1->A2->N2->A3
  B1[label="B"]
  A1[label="A"]
  N1[label="N"]
  A2[label="A"]
  N2[label="N"]
  A3[label="A"]
}

A1->A:n [style=normal]
A2->A:nw [style=normal]
A3->A:w [style=normal]
B1->B [style=normal]
N1->N:w [style=normal]
N2->N:nw [style=normal]
```

### Příklad

Náš příklad bude představovat jednoduchou herní mapu (dvojrozměrné pole). Každé políčko je buď louka, kopec nebo řeka. Pro vykreslení pole potřebujeme znát jeho texturu (*imageName*) a pro vyhodnocení pohybu herních postav musíme vědět, jak rychle se lze po daném políčku pohybovat (*moveSpeed*) a zda se jedná o vodní plochu (*isWater*). Dále chceme vědět, zda se na aktuálním políčku zrovna nějaká postava nenachází (*isOccupied*).

#### Před použitím vzoru

První verze třídy pro reprezentaci políček bude všechny tyto hodnoty obsahovat přímo jako své proměnné. Již teď je jasné, že v případě malého množství druhů políček bude v paměti neefektivně uloženo příliš mnoho duplicitních informací. Například všechna políčka typu řeka budou mít nastavený příznak vodní plochy a stejnou rychlost pohybu.

```include:java
gof/flyweight/Tile.java
```

```include:java
gof/flyweight/Map.java
```

#### Po použití vzoru

Klíčem k redukci velikosti políček v paměti je identifikace vlastností, které se vážou k druhu políčka, a tyto vlastnosti extrahovat do samostatné třídy. Políčko pak dostane na tuto třídu odkaz. Každý druh políčka bude reprezentován pouze jedinou její instancí a tak se zabrání duplicitě.

V našem případě lze extrahovat všechny proměnné až na příznak *isOccupied*, protože ten není dán druhem políčka a opravdu je nutné, aby si tento příznak drželo každé jedno políčko zvlášť.

```include:java
gof/flyweight/FlyweightTileType.java
```

Informace vázané ke druhu políčka lze z políčka vyjmout a nahradit je pouze odkazem na druh políčka. Množství informací v políčku se tedy nezmění, pouze budou lépe distribuované a nebudou se zbytečně duplikovat.

```include:java
gof/flyweight/FlyweightTile.java
```

Předem si připravíme tři instance typů políček: louku, řeku a kopec. Tyto tři instance budou obsahovat údaje společné pro všechna políčka daných typů. Každé políčko potom dostane referenci na nějaký typ.

```include:java
gof/flyweight/FlyweightMap.java
```

### Reference

- https://sourcemaking.com/design_patterns/flyweight
- http://gameprogrammingpatterns.com/flyweight.html
- http://www.tutorialspoint.com/design_pattern/flyweight_pattern.htm
- http://www.oodesign.com/flyweight-pattern-wargame-example-java-sourcecode.html
