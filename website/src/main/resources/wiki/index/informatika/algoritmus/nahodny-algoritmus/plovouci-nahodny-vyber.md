## Plovoucí náhodný výběr

Výběr jednoho náhodného prvku z pole je docela jednoduchý. 
Vygenerujeme náhodné číslo v rozmezí 0 až *n-1*, kde *n* je délka [pole](wiki/datova-struktura-pole), a vrátíme prvek na dané pozici.
Pokud takových prvků chceme více, proceduru opakujeme, případně si pamatujeme prvky, které již byly vybrány, aby nebyly vybrány znovu.

Jak to ale uděláme, pokud je výčet prvků předem neznámý nebo příliš velký na to, aby se vešel do paměti?
Lze prvky vybírat takovým způsobem, který zajistí, že každý prvek má stejnou šanci se do výběru dostat?
Řešení nabízí tzv. **plovoucí náhodný výběr** (reservoir sampling).

### Výběr několika prvků

Pokud chceme vybrat *s* náhodných prvků z předem neznámého výčtu prvků, můžeme postupovat následovně:

1. Inicializujeme pole €R€ o velikosti €s€.
1. Prvních €s€ přijatých prvků postupně uložíme do pole €R€.
1. Pro každý další přijatý prvek na pozici €k = s+1 € až €n€:
    1. S pravděpodobností €s/k€ prvek přijmeme a nahradíme jím náhodně vybraný prvek v poli €R€.
1. Předchozí bod se opakuje, dokud nejsou vyčerpány všechny prvky.

Jak a proč tato technika funguje? 

Každý prvek €I_k€ je nahrazen následujícím prvkem s pravděpodobností €1/k€ a zachován v ostatních případech, což znamená €1-(1/k)=(k-1)/k€.

Aby byl prvek €I_k€ nakonec vybrán, musí nastat současně tyto jevy:
- předchozí prvek €I_{k-1}€ je nahrazen, což nastane s pravděpodobností €1/(k-1)€ (viz definice algoritmu)
- aktuální €I_k€ prvek není přeskočen, což nastane s pravděpodobností €1-(1/k)=(k-1)/k€ (viz definice algoritmu)

Pravděpodobnost toho, že tyto dva jevy nastanou současně, je tedy €(1/(k-1))\cdot((k-1)/k)=1/k€, což odpovídá předpokladu.

Jednoduchá implementace této techniky v [jazyce Java](wiki/java) (pozor, pro velké objemy dat musíme počítat se zaokrouhlovací chybou a omezeným rozsahem typu *javadoc:java.lang.Integer*):

```include:java
random/ReservoirSamplingMultipleItems.java
```

### Výběr jednoho prvku

Pokud potřebujeme vybrat jen jeden prvek, jedná se o speciální případ výše uvedeného algoritmu pro €k=1€.
Pro tento případ lze algoritmus trochu zjednodušit.

```include:java
random/ReservoirSamplingSingleItem.java
```

### Reference

- http://blog.cloudera.com/blog/2013/04/hadoop-stratified-randosampling-algorithm/
- https://gregable.com/2007/10/reservoir-sampling.html
- http://www.cs.umd.edu/~samir/498/vitter.pdf
- https://xlinux.nist.gov/dads/HTML/reservoirSampling.html