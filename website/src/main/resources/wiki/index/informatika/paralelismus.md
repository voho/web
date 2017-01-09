## Paralelní programování

Paralelní programování řeší problémy vznikající při spolupráci více procesů (vláken) na jedné úloze. Obecně (i mimo programování) platí, že jakmile existuje více zájemců o jeden vzácný zdroj, dochází ke konfliktu. Konflikt lze vyřešit zákony (pravidly) nebo vzájemnou domluvou (komunikací). Ve světě programování může být vzácným zdrojem například sdílená data v paměti, procesor, přenosová linka, server, a podobně.

Paralelní programování přichází s návody, postupy a algoritmy, jak tyto konflikty řešit nebo jim úplně předcházet. Existují například určité typické úlohy, na které byla nalezena dobrá a funkční obecná řešení. Jak se počítače více a více paralelizují, roste i potřeba tyto postupy znát a ovládat.

### Proces a vlákno (process, thread)

Každý samostatně běžící program se nazývá **proces**. Součástí jednoho procesu může být obecně několik vláken. Ač je velký rozdíl mezi procesem a vláknem na úrovni operačního systému, pro účely popisu úloh se tyto pojmy často zaměňují. Zde je preferovaný pojem **vlákno**, který mi subjektivně přijde obecnější a méně závislý na architektuře operačního systému (což je podstatně například pro virtuální stroje). Vlákno je samostatnou jednotkou, která postupně vykonává určitý předem daný kód a v případě potřeby s ostatními vlákny spolupracuje nebo komunikuje.

### Kritická sekce (critical section)

Jako kritická sekce se označuje část kódu, kde může dojít ke konfliktu mezi vlákny. Je to onen "vzácný zdroj", ke kterému je nutné řídit přístup, pokud nemá dojít k žádným problémům. Typicky je to kód, ve kterém se pracuje se sdílenými datovými strukturami.

### Zámek (lock)

Zámek je mechanismus pro řízení přístupu vláken ke kritické sekci.

### Mutex

Mutex (mutual exclusion = vzájemné vyloučení) je typ zámku, který do kritické sekce vpustí jen jedno vlákno současně. Ostatní vlákna se před vstupem do kritické sekce zablokují.

### Semafor (semaphore)

Semafor je typ zámku založený na čítači. Semafor umožňuje dvě operace: 

- *down* (P, acquire): Tato operace se volá před vstupem do kritické sekce. Při zavolání operace se vyhodnocuje hodnota čítače: pokud je nenulová, vlákno dekrementuje čítač (typicky o 1) a pokračuje dál ve vykonávání kritické sekce; pokud je nulová, vlákno se zablokuje až do doby, než je čítač opět nenulový.
- *up* (V, release): Tato operace se volá ihned po vystoupení z kritické sekce. Při zavolání operace se inkrementuje čítač (typicky o 1). Inkrementování čítače o *n* způsobí probuzení *n* čekajících vláken (viz operace *down*).

Semafor lze inicializovat na libovolné číslo - toto číslo vyjadřuje, kolik vláken současně může být v kritické sekci. Mutex lze simulovat semaforem s počáteční hodnotou čítače 1.

```plain
int value

initialize(int i) {
  value = i
}

down() {
  do_atomically {
    value--
    if (value < 0) then *BLOCK THIS THREAD*
  }
}

up() {
  do_atomically {
    value++
    if (value <= 0) then *UNBLOCK ONE BLOCKED THREAD*
  }
}
```

### Monitor

Monitor je typ zámku, který spojuje koncepty **vlastníka** a **množiny čekajících vláken**, kde vlákna čekají až do splnění určité **podmínky**. Vlákna mezi sebou mohou komunikovat a sdělovat si vzájemně, že byla podmínka splněna a mohou čekání přerušit. Vlastník se také může svého vlastnictví vzdát a připojit se tak k ostatním čekajícím vláknům. Platí, že kritickou sekci může spustit pouze aktuální vlastník monitoru.

Příkladem může být implementace monitoru v jazyce Java, znázorněná následujícím obrázkem:

```uml:digraph
nodesep=1;
edgesep=5;
splines=false;
rankdir=LR;
in [shape=none,label=""];
out [shape=none,label=""];
in -> EntrySet [label="enter"];
EntrySet -> TheOwner [label="acquire"];
TheOwner -> WaitSet [headlabel="release",labeldistance=5];
WaitSet -> TheOwner [headlabel="acquire",labeldistance=5];
TheOwner -> out [label="release and exit"];
{rank=same;out;EntrySet;}
```

Na obrázku jsou znázorněny tři součásti monitoru: **vstupní množina vláken** (entry set), **vlastník** (owner) a **množina čekajících vláken** (wait set). Každé vlákno může být nejvýše v jedné z nich, přičemž mezi nimi existuje právě pět možných vyznačených přechodů:

Vlákno se chce stát vlastníkem monitoru a vstupuje do vstupní množiny (*1*). Není-li žádné vlákno vlastníkem monitoru, vlákno přejde pomocí přechodu (*2*) a stane se vlastníkem monitoru. Pokud však již nějaké jiné vlákno vlastní monitor, vlákno se zablokuje a čeká ve vstupní množině. Vlákno vlastnící monitor může o vlastnictví přijít dvěma způsoby: může se jej dobrovolně vzdát a tím přejít do množiny čekajících vláken (*3*), nebo dokončit vykonávání kritické sekce (*5*). Vlastník dále může upozornit jedno nebo všechna vlákna v čekající množině a tak jim umožnit opětovné získání vlastnictví. Jakmile je post vlastníka monitoru volný, některé vlákno ze vstupní množiny (*3*) nebo množiny upozorněných čekajících vláken (*4*) se stává vlastníkem monitoru. 

### Bariéra (barrier)

Bariéra je typ zámku s hodnotou, která udává počet vláken nutný k jejímu "překonání". Pokud má bariéra velikost *n*, zablokuje *n-1* vláken. Další vlákno způsobí, že se všechna tato na bariéře čekající vlákna uvolní a pokračují dál do kritické sekce. Bariéra tedy neudává maximální, ale minimální počet vláken, které mohou v kritické sekci současně být.

### Reference

- http://greenteapress.com/semaphores/
- http://www.artima.com/insidejvm/ed2/threadsynch.html