## Princip GRASP

GRASP (general responsibility assignment software patterns) je sada doporučení, principů a vodítek, sloužících k vytvoření kvalitnějšího objektového návrhu. Princip GRASP se zaměřuje se na rozdělení zodpovědností jednotlivým třídám a objektům.

Zodpovědností se rozumí podúloha, kterou má třída nebo skupina tříd řešit. Může to být například načítání zakázek, výpočet daně nebo kopírování souborů. Každá taková zodpovědnost lze realizovat různými třídami a různými metodami. GRASP radí, jak jednotlivé podúlohy mezi třídy rozdělit a jak tyto součásti spolu budou spolupracovat. Výstupem jsou nejčastěji **diagramy spolupráce** v jazyce UML.

### Rychlý přehled

| Název | Popis
|---
| **Information Expert** | rozdělení zodpovědnosti na základě přístupu k informací
| **Creator** | nalezení vhodného místa k vytváření instancí 
| **Controller** | realizace uživatelské akce
| **Low Coupling** | minimální závislosti mezi třídami
| **High Cohesion** | maximální soustředěnost souvisejících dat a funkcí
| **Polymorphism** | odlišné chování podle konkrétního typu objektu
| **Protected Variations** | identifikace a stabilizace společného rozhraní několika tříd
| **Pure Fabrication** | návrh univerzálních tříd, které nejsou závislé na řešeném problému
| **Indirection** | komunikace mezi dvěma třídami zajištěná třetím prostředníkem

### Podrobnější popis

#### Information expert

Princip **information expert** (informační expert) říká, že třídou zodpovědnou za nějakou akci má být právě ta třída, která má k provedení této akce nejvíce informací. Taková třída se nazývá **informační expert**. Podle tohoto pravidla by se měly služby umisťovat k souvisejícím atributům a třída by měla se svými vlastními daty pracovat pokud možno sama.

#### Creator

Princip **creator** (tvůrce) pomáhá najít vhodné místo, kde vytvářet nové instance nějaké třídy. Je to princip důležitý, protože vytváření nových objektů patří mezi nejčastější operace v objektově orientovaném programování. Vytváření nových instancí třídy *A* by mělo být zajištěno třídou *B*, právě když platí alespoň jeden z těchto výroků:

- třída *B* obsahuje třídu *A*
- třída *B* se skládá z třídy *A*
- třída *B* obsahuje všechny informace nutné k vytvoření třídy *A*
- třída *B* velmi úzce spolupracuje s třídou *A*

#### Controller

Třída označovaná jako **controller** (ovladač) je taková třída, která jako první zpracovává systémovou událost (například uživatelskou akci vyvolanou kliknutím na tlačítko) a zajišťuje její provedení. Controller by měl akci delegovat specializovanému podsystému nižší úrovně.

#### Low coupling

Princip **low coupling** (nízká závislost) si vynucuje dodržování těchto zásad:

- nízká závislost mezi třídami
- žádný nebo minimální dopad změny jedné třídy na třídy ostatní
- vysoký potenciál znovupoužití 

#### High cohesion

Princip **high cohesion** (vysoká soudržnost) je založen na těchto principech:

- funkce každé třídy je snadno pochopitelná a popsatelná
- každá třída do sebe soustředí data a s nimi související funkce
- existence každé třídy je objektivně odůvodnitelná

#### Polymorphism

Chování se mění na základě typu objektu. 

#### Protected Variations

Princip **protected variations** (chráněné variace) ochraňuje prvky systému před změnami jiných prvků tak, že zavede **rozhraní**. Konkrétní chování se pak zvolí na základě konkrétní instance objektu.

#### Pure Fabrication

Třída označovaná jako **pure fabrication** v systému existuje jen a pouze proto, aby vylepšila systémovou architekturu (zmenšila závislost a/nebo zvýšila soustředěnost). Taková třída je velmi vhodným kandidátem pro znovupoužití, tedy pro přenos mezi projekty se stejnou či podobnou architekturou.

#### Indirection

Princip zvaný **indirection** (nepřímost) snižuje závislost mezi dvěma třídami tak, že jejich vazby přetrhne a místo nich nechá obě třídy komunikovat přes prostředníka. Tím se mimo jiné zvyšuje i znovupoužitelnost obou tříd.

Příkladem dobrého použití tohoto principu je vytvoření controlleru jako prostředníka pro přenos dat mezi uživatelským rozhraním (view) a datovým modelem (model) v architektuře MVC (model-view-controller).

### Reference

- předmět X36ASS na FEL ČVUT
- http://en.academic.ru/dic.nsf/enwiki/2418108