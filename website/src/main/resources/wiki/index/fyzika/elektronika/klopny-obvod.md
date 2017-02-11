## Klopný obvod

Bistabilní klopný obvod je speciální obvod, který se vždy nachází v jednom ze dvou možných stavů, dokud není vnějším zásahem změněn. Jedná se tedy o nejmenší paměťový prvek s kapacitou **jeden bit**. Tyto obvody mají široké využití v digitální technice, která je založena na binární číselné soustavě.

Klopný obvod je základním stavebním prvkem sekvenčních obvodů. Využívá se v pamětech, registrech, čítačích a dalších obvodech, ve kterých je potřeba uchovávat informace.

V angličtině se pro klopné obvody používá termín **bistable** nebo **flip-flop**. Klopné obvody se totiž realizovaly pomocí elektromagnetických relé, které během přepínání stavů vydávaly zvuky (flip, flop).

### Realizace klopných obvodů

V digitální technice se klopné obvody realizují pomocí logických hradel. Nejjednodušším klopným obvodem je asynchronní klopný obvod typu RS, který se skládá ze dvou hradel zapojených se zpětnou vazbou (cross-coupled circuit, feedback). Ostatní typy jsou založené na něm.

### Synchronizace

V některých aplikacích je potřeba zařídit, aby klopný obvod po určitý čas nereagoval na vstup. K tomuto účelu byly navrženy tzv. **synchronní klopné obvody** vybavené speciálním vstupem, označovaným jako **hodinový vstup**. Na tento vstup je přiveden tzv. **hodinový signál**, který určuje, zda bude klopný obvod na ostatní vstupy reagovat, nebo je ignorovat.

Podle synchronizace se klopné obvody dělí do těchto skupin:

- **asynchronní** (asynchronous) - bez hodinového vstupu
- **synchronní** (synchronous) - s hodinovým vstupem
 - **řízené úrovní** (level-sensitive) - též hladinové
 - **řízené hranou** (edge-sensitive) - též hranové
 - **master-slave**

#### Klopné obvody řízené úrovní

Úrovní řízené klopné obvody reagují na vstup právě tehdy, když je hodinový signál v určité **úrovni** (*0* či *1* dle konkrétní realizace). Jeho výhodou je snadná realizace. Možnou nevýhodou je nevhodné chování v případě zpětné vazby, kdy je nový stav klopného obvodu přiveden zpět na jeho vstup, a to dříve, než je tento hodinovým signálem deaktivován. V uvedeném případě bude obvod periodicky přepínat svůj stav, což připomíná "psa honícího svůj vlastní ocas".

#### Klopné obvody řízené hranou

Hranou řízené klopné obvody reagují na **změnu** úrovně hodinového signálu a to buď na hranu náběžnou/vzestupnou (z logické *0* na *1*), nebo sestupnou (z logické *1* na *0*). Vzestupná hrana se anglicky nazývá **positive/raising edge** a hrana sestupná **negative/falling edge**.

Infinitezimálně krátká hrana teoreticky řeší problém se zpětnou vazbou, který nastává u klopných obvodů řízených úrovní - signál se během nekonečně krátkého trvání hrany nestihne vrátit zpět na vstup. V realitě však hrana hodinového signálu není absolutně rovná, vyskytuje se jev zvaný **clock skew** (šikmost hran hodinového signálu).

#### Klopné obvody typu master-slave

Klopné obvody typu master-slave se navenek jeví jako obyčejné klopné obvody řízené hranou, uvnitř se však skládají ze dvou sériově zapojených hranou řízených klopných obvodů. Vstupní se označuje jako **master**, výstupní jako **slave**. Master je aktivován hranou hodinového signálu a reaguje na vstup. Výstup zatím není ovlivněn. Při výskytu opačné orientované hrany reaguje slave a kopíruje vnitřní stav master. Změny vnitřního stavu se tedy projeví až po skončení hodinového impulzu (obdélníku).

### Kapacita

Pro uchování většího množství informace je nutné použít více klopných obvodů. Následující tabulka ukazuje kapacitu a číselný rozsah daného počtu klopných obvodů. Jeden bajt je zde obvyklých 8 bitů.

| Počet klopných obvodů | Množství informace | Číselný rozsah
|---|---|---
| 1 | 1 bit | 0 – 1
| 2 | 2 bity | 0 – 3
| 4 | 4 bity | 0 – 15
| 8 | 1 bajt (8 bitů) | 0 – 255
| 16 | 2 bajty (16 bitů) | 0 – 65,535
| 32 | 4 bajty (32 bitů) | 0 – 4,294,967,295
| 8,192 | 1 kilobajt (1,024 bajtů) | 1.09e+2,466
| 8,388,608 | 1 megabajt (1,024 kilobajtů) | 4.26e+2,525,222
| 8,589,934,592 | 1 gigabajt (1,024 megabajtů) | (příliš velký)

### Tabulka přechodů

Tabulka přechodů pro klopné obvody (také nazývaná **excitační tabulka**) udává vstupy klopných obvodů nutné k dosažení požadované změny jejich vnitřního stavu.

| Současné Q | Příští Q | R | S | D | J | K | T
|---|---|---|---|---|---|---|---
| 0 | 0 | Cokoliv | 0 | 0 | 0 | Cokoliv | 0
| 0 | 1 | 0 | 1 | 1 | 1 | Cokoliv | 1
| 1 | 0 | 1 | 0 | 0 | Cokoliv | 1 | 1
| 1 | 1 | 0 | Cokoliv | 1 | Cokoliv | 0 | 0

### Typy klopných obvodů

#### Klopný obvod typu RS (asynchronní)

Asynchronní klopný obvod typu RS je základní a nejjednodušší asynchronní klopný obvod. Skládá se ze dvou hradel zapojených se **zpětnou vazbou** (cross-coupled circuit, feedback). 

Klopný obvod obsahuje dva vstupy, mnemotechnicky nazvané **set** (*S*) a **reset** (*R*). Přivedením logické *1* na vstup *S* se vnitřní stav klopného obvodu přepne do logické *1*. Obdobně se aktivací vstupu *R* obvod přepne do logické *0*. Není-li aktivní ani jeden vstup, vnitřní stav klopného obvodu zůstává beze změny.

Zbývající kombinace vstupů (logická *1* na vstupech *R* i *S*) vede k tzv. **zakázanému stavu**, pro který není žádný vnitřní stav definován. V praxi se obvod přepne do logické *0* nebo *1* podle toho, kterým hradlem projde signál dříve (race-condition). Nedefinovaný vnitřní stav je ve většině aplikací nežádoucí a proto by se mu měl návrhář vyhnout.

##### Schéma zapojení

![schéma zapojení z hradel NOR](latch_rs.png)

Klopný obvod lze sestavit i z [logických hradel](wiki/logicke-hradlo) NAND. Prostým nahrazením hradel NOR hradly NAND vznikne varianta s negovanými vstupy a výstupy.

![schéma zapojení z hradel NAND](latch_rs_nand.png)

##### Vstupy a výstupy

- vstup *R* - reset
- vstup *S* - set
- výstup *Q* - vnitřní stav
- výstup *not Q* - negace vnitřního stavu

##### Ovládání

| R | S | Význam
|---|---
| 0 | 0 | Zachovej stav
| 0 | 1 | Nastav 1
| 1 | 0 | Nastav 0
| 1 | 1 | Zakázaný stav

##### Tabulka přechodů

| Aktuální stav (Q) | R | S | Výsledný stav (Q')
|---|---|---|---
| 0 | 0 | 0 | 0
| 0 | 0 | 1 | 1
| 0 | 1 | 0 | 0
| 0 | 1 | 1 | Zakázaný stav
| 1 | 0 | 0 | 1
| 1 | 0 | 1 | 1
| 1 | 1 | 0 | 0
| 1 | 1 | 1 | Zakázaný stav

#### Klopný obvod typu D (synchronní)

Synchronní klopný obvod typu D se vyznačuje především jednoduchým ovládáním. Je založený na synchronním klopném obvodu typu RS. Narozdíl od něj má však pouhé dva vstupy *D*, *C* a jeho ovládání je intuitivnější. Je-li na vstupu *C* logická *1*, vnitřní stav se přepne do logické hodnoty, která se nachází na vstupu *D*. Je-li na vstupu *C* logická *0*, obvod na vstup *D* nereaguje.

Vstup *D* je přiveden na vstup *S* vnořeného klopného obvodu typu RS a jeho negace na vstup *R*. Tím je také vyloučen **zakázaný stav**, protože logické hodnoty na vstupech *R* i *S* jsou vždy opačné.

##### Schéma zapojení

![schéma zapojení založené na synchronním klopném obvodu typu RS](latch_d_using_rs.png)

![rozkreslené schéma zapojení](latch_d.png)

![alternativní zapojení s hradly NAND](latch_d_nand.png)

##### Vstupy a výstupy

- vstup *D* - hodnota k uložení
- vstup *C* - hodinový (řídící) vstup
- výstup *Q* - vnitřní stav
- výstup *not Q* - negace vnitřního stavu

##### Ovládání

| C | D | Význam
|---|---|---
| 0 | Cokoliv | Zachovej stav
| 1 | 0 | Nastav 0
| 1 | 1 | Nastav 1

##### Tabulka přechodů

Podobně jako u ostatních synchronních klopných obvodů, při neaktivním hodinovém vstupu *C* je vnitřní stav zachován nezávisle na ostatních vstupech.

| C | Aktuální stav (Q) | D | Výsledný stav (Q')
|---|---|---|---
| 0 | 0 | 0 | 0
| 0 | 0 | 1 | 0
| 0 | 1 | 0 | 1
| 0 | 1 | 1 | 1
| 1 | 0 | 0 | 0
| 1 | 0 | 1 | 1
| 1 | 1 | 0 | 0
| 1 | 1 | 1 | 1

#### Klopný obvod typu JK (synchronní)

Synchronní klopný obvod typu JK je pojmenovaný po vědci jménem Jack Kilby, který v roce 1958 představil první integrovaný obvod. Je založený na synchronním klopném obvodu typu RS a má s ním shodné i ovládání, avšak nemá na rozdíl od něj žádný zakázaný stav. Pokud je na oba vstupy *J*, *K* přivedena logická *1*, hodnota uložená v klopném obvodu se invertuje (z logické *1* na logickou *0* a naopak).

Vstup *J* (jump) lze tedy do jisté míry připodobnit ke vstupu *S* (set) a vstup *K* (kill) ke vstupu *R* (reset).

##### Schéma zapojení

![schéma zapojení založené na synchronním klopném obvodu typu RS](latch_jk_using_rs.png)

##### Vstupy a výstupy

- vstup *J* - jump
- vstup *K* - kill
- vstup *C* - hodinový (řídící) vstup
- výstup *Q* - vnitřní stav
- výstup *not Q* - negace vnitřního stavu

##### Ovládání

| C | J | K | Význam
|---|---|---|---
| 0 | Cokoliv | Cokoliv | Zachovej stav
| 1 | 0 | 0 | Zachovej stav
| 1 | 0 | 1 | Nastav 0
| 1 | 1 | 0 | Nastav 1
| 1 | 1 | 1 | Invertuj stav

##### Tabulka přechodů

Podobně jako u ostatních synchronních klopných obvodů, při neaktivním hodinovém vstupu *C* je vnitřní stav zachován nezávisle na ostatních vstupech.

| C | Aktuální stav (Q) | J | K | Výsledný stav (Q')
|---|---|---|---|---
| 0 | 0 | 0 | 0 | 0
| 0 | 0 | 0 | 1 | 0
| 0 | 0 | 1 | 0 | 0
| 0 | 0 | 1 | 1 | 0
| 0 | 1 | 0 | 0 | 1
| 0 | 1 | 0 | 1 | 1
| 0 | 1 | 1 | 0 | 1
| 0 | 1 | 1 | 1 | 1
| 1 | 0 | 0 | 0 | 0
| 1 | 0 | 0 | 1 | 0
| 1 | 0 | 1 | 0 | 1
| 1 | 0 | 1 | 1 | 1
| 1 | 1 | 0 | 0 | 1
| 1 | 1 | 0 | 1 | 0
| 1 | 1 | 1 | 0 | 1
| 1 | 1 | 1 | 1 | 0

#### Klopný obvod typu RS (synchronní)

Synchronní klopný obvod typu RS je nejjednodušší synchronní klopný obvod. Je založený na asynchronním klopném obvodu typu RS, do kterého jsou přidána dvě další logická hradla určená k nulování vstupů. Jsou-li totiž oba vstupy klopného obvodu typu RS rovny logické *0*, vnitřní stav zůstává beze změny a tak obvod na vstup nereaguje a zachovává svůj vnitřní stav.

Nulování je řízeno speciálním vstupem *C* (hodinový vstup, clock input), který je přiveden do obou nulovacích hradel AND. Je-li úroveň tohoto signálu rovna logické *0*, je výstup hradel AND rovněž roven logické *0*, a to nezávisle na jejich zbývajících vstupech. Tato hradla tudíž slouží jako inhibitor, který převádí libovolné vstupy na logické *0* vždy, když je hodiný vstup na logické *0*.

##### Schéma zapojení

![schéma zapojení z hradel NOR a AND](latch_rs_sync.png)

Opět lze místo hradel AND a NOR využít hradla NAND, přičemž jedinou změnou je prohození výstupů.

![schéma zapojení z hradel NAND](latch_rs_sync_nand.png)

##### Vstupy a výstupy

- vstup *R* - reset
- vstup *S* - set
- vstup *C* - hodinový (řídící) vstup
- výstup *Q* - vnitřní stav
- výstup *not Q* - negace vnitřního stavu

##### Ovládání

| C | R | S | Význam
|---|---|---|---
| 0 | Cokoliv | Cokoliv | Zachovej stav
| 1 | 0 | 0 | Zachovej stav
| 1 | 0 | 1 | Nastav 1
| 1 | 1 | 0 | Nastav 0
| 1 | 1 | 1 | Zakázaný stav

##### Tabulka přechodů

Část tabulky přechodů při aktivním hodinovém vstupu *C* je shodná s tabulkou přechodů pro asynchronní klopný obvod typu RS. Při neaktivním hodinovém vstupu *C* je vnitřní stav zachován nezávisle na ostatních vstupech.

| C | Aktuální stav (Q) | R | S | Výsledný stav (Q')
|---|---|---|---|---
| 0 | 0 | 0 | 0 | 0
| 0 | 0 | 0 | 1 | 0
| 0 | 0 | 1 | 0 | 0
| 0 | 0 | 1 | 1 | 0
| 0 | 1 | 0 | 0 | 1
| 0 | 1 | 0 | 1 | 1
| 0 | 1 | 1 | 0 | 1
| 0 | 1 | 1 | 1 | 1
| 1 | 0 | 0 | 0 | 0
| 1 | 0 | 0 | 1 | 1
| 1 | 0 | 1 | 0 | 0
| 1 | 0 | 1 | 1 | Zakázaný stav
| 1 | 1 | 0 | 0 | 1
| 1 | 1 | 0 | 1 | 1
| 1 | 1 | 1 | 0 | 0
| 1 | 1 | 1 | 1 | Zakázaný stav

##### Master-slave

Využitím konceptu master-slave lze úrovní řízený klopný obvod předělat na klopný obvod řízený hranou. Vstupy a výstupy zůstávají stejné, mění se pouze vnitřní struktura klopného obvodu.

##### Schéma zapojení

![schéma zapojení hranou řízeného klopného obvodu typu RS (master-slave)](latch_rs_master_slave.png)

#### Klopný obvod typu T (synchronní)

Synchronní klopný obvod typu T je založený na synchronním klopném obvodu typu RS a má narozdíl od něj pouze jeden vstup *T* (toggle). Přivedeme-li na něj logickou *1*, hodnota uložená v klopném obvodu se invertuje (z logické *1* na logickou *0* a naopak).

##### Schéma zapojení

![schéma zapojení založené na synchronním klopném obvodu typu RS](latch_t_using_rs.png)

![schéma zapojení založené na synchronním klopném obvodu typu JK](latch_t_using_jk.png)

##### Vstupy a výstupy

- vstup *T* - toggle
- vstup *C* - hodinový (řídící) vstup
- výstup *Q* - vnitřní stav
- výstup *not Q* - negace vnitřního stavu

##### Ovládání

| C | T | Význam
|---|---|---
| 0 | Cokoliv | Zachovej stav
| 1 | 0 | Zachovej stav
| 1 | 1 | Invertuj stav

##### Tabulka přechodů

Podobně jako u ostatních synchronních klopných obvodů, při neaktivním hodinovém vstupu *C* je vnitřní stav zachován nezávisle na ostatních vstupech.

| C | Aktuální stav (Q) | T | Výsledný stav (Q')
|---|---|---|---
| 0 | 0 | 0 | 0
| 0 | 0 | 1 | 0
| 0 | 1 | 0 | 1
| 0 | 1 | 1 | 1
| 1 | 0 | 0 | 0
| 1 | 0 | 1 | 1
| 1 | 1 | 0 | 1
| 1 | 1 | 1 | 0

### Reference

- Alan Clemens: The Principles of Computer Hardware, Second Edition