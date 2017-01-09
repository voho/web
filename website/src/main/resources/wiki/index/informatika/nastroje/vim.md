## Textový editor VIM

### Spuštění

```bash
vim SOUBOR
```

### Ukončení

Napište *:q!* + *enter* pro ukončení editoru bez uložení změn, *:wq* + *enter* pro uložení změn a ukončení.

### Tutorial

Pro spuštění hezkého integrovaného tutorialu:

```bash
vimtutor
```

### Režimy

Textový editor VIM je vždy v nějakém režimu, ze kterých je nejdůležitější tzv. **normální režim** a **režim vkládání**. Normální režim slouží primárně k rychlému pohybu po souboru a vykonávání hromadných či opravných operací, režim vkládání slouží primárně k psaní textu. Dále je v editoru interní příkazová řádka.

- normální režim: *:* = otevře interní příkazovou řádku (pro potvrzení příkazu stiskněte *enter*)
- normální režim: *i* = přejde do režimu vkládání
- režim vkládání: *Esc* = přejde zpět do normálního režimu

### Klávesové zkratky

Tyto klávesové zkratky fungují v **normálním režimu** (nikoliv v režimu vkládání).

#### Pohyb po souboru

- *h* / šipka vlevo = o znak vlevo
- *l* / šipka vpravo = o znak vpravo
- *j* / šipka dolů = o řádek níž
- *k* / šipka nahoru = o řádek výš
- *Ctrl+G* = zobrazí dole informaci o pozici v souboru
- *Shift+G* = skočí na konec souboru
- *číslo Shift+G* = skočí na zadané číslo řádku (číslo se nezobrazuje během psaní)
- *%* = skok na odpovídající proti-závorku

#### Vyhledávání

- */slovo* + *enter* = po zadání tohoto příkazu se vyhledá zadané slovo od kurzoru dále až do konce souboru a dále od začátku až do výchozí pozice - po nalezení výsledků lze použít tyto příkazy pro skok mezi jednotlivými výskyty:
  - *n* = skok na další výskyt
  - *Shift+n* = skok na předchozí výskyt
- *?slovo* + *enter* = stejné jako */*, ale vyhledávání funguje opačným směrem

#### Mazání 

- *x* = mazání jednoho znaku pod kurzorem
- *dw* = mazání slova, které začíná pod kurzorem
- *dd* = mazání řádku pod kurzorem
- *d$* = mazání všech znaků od kurzoru až do konce řádku

#### Vkládání

- *i* = aktivace vkládacího režimu na místo kurzoru
- *a* = aktivace vkládacího režimu za aktuálním znakem
- *A* = aktivace vkládacího režimu na konci aktuálního řádku 
- *p* = vloží poslední smazaný text za kurzor (v případě řádku vloží řádek ve schránce za aktuální řádek, v případě znaku za aktuální znak)

#### Nahrazení

- *r* = nahrazení znaku na místě kurzoru (například *rh* zamění aktuální znak za **h**)
- *cw* = nahrazení slova začínajícího na místě kurzoru (smazání slova + přepnutí na insert režim)
- *cc* = nahrazení řádku pod kurzorem (smazání obsahu na řádku + přepnutí na insert)
- *c$* = nahrazení všech znaků od kurzoru až do konce řádku (smazání znaků + přepnutí na insert)
- *:s/XXX/YYY* + *enter* = nahrazení prvního výskytu znaků **XXX** znaky **YYY**
- *:s/XXX/YYY/g* + *enter* = nahrazení všech výskytu znaků **XXX** znaky **YYY** v aktuálním řádku
- *:5,9s/XXX/YYY/g* + *enter* = nahrazení všech výskytu znaků **XXX** znaky **YYY** v řádcích 5 až 9
- *:%s/XXX/YYY/g* + *enter* = nahrazení všech výskytu znaků **XXX** znaky **YYY** v celém souboru

#### Ostatní

- *u* = funkce "zpět" (undo) vrátí zpět poslední provedenou operaci
- *U* = vrátí celý řádek do původního stavu
- *Ctrl-R* = funkce "opakovat" (redo) provede poslední vrácenou operaci
- *:!XXX* + *enter* = spustí externí příkaz **XXX**

#### Obecně

- číslo před nebo po příkazu označuje počet jeho opakování (například *5dd* nebo *d5d* opakuje pětkrát příkaz *dd* a smaže pět řádků)