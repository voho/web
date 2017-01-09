## Burrows-Wheelerova transformace

Burrows-Wheelerova transformace je [kódování](wiki/kodovani), které se používá před kompresí, aby zvýšilo její efektivitu. Tato transformace způsobí, že se frekventované symboly a jejich skupiny přesunou tak, že jsou více pohromadě. 

Burrows-Wheelerovat transformace se používá jako pre-processing před použitím [kompresního algoritmu](wiki/kompresni-algoritmus). Po dekompresi se opět musí provést zpětná transformace (podobně jako u každého jiného skládání [zobrazení](wiki/zobrazeni).

### Transformace

Nejprve se vytvoří všechny cyklické rotace zadaného řetězce.

| Index řádku | Posunutý řetězec
|---|---|---
| 0 | ABRABABRA *(eof)*
| 1 | *(eof)* ABRABABRA
| 2 | A *(eof)* ABRABABR
| 3 | RA *(eof)* ABRABAB
| 4 | BRA *(eof)* ABRABA
| 5 | ABRA *(eof)* ABRAB
| 6 | BABRA *(eof)* ABRA
| 7 | ABABRA *(eof)* ABR
| 8 | RABABRA *(eof)* AB
| 9 | BRABABRA *(eof)* A

Potom se všechny řetězce vzniklé rotací lexikograficky seřadí.

| Index řádku | Posunutý řetězec
|---|---|---
| 7 | ABABRA *(eof)* ABR
| 0 | ABRABABRA *(eof)*
| 5 | ABRA *(eof)* ABRAB
| 2 | A *(eof)* ABRABABR
| 6 | BABRA *(eof)* ABRA
| 9 | BRABABRA *(eof)* A
| 4 | BRA *(eof)* ABRABA
| 8 | RABABRA *(eof)* AB
| 3 | RA *(eof)* ABRABAB
| 1 | *(eof)* ABRABABRA

Z této seřazené posloupnosti řetězců se jako výstup použije poslední sloupec:

R *(eof)* BRAAABBA

Ve výsledném řetězci je například vidět trojnásobné opakování symbolu *A* a dvojnásobné opakování symbolu *B*. To je pro kompresní algoritmy většinou výhodnější, než původní řetězec.

### Inverzní transformace

Vstupem pro inverzní transformaci bude řetězec R*(eof)*BRAAABBA. Tento řetězec se vloží jako poslední sloupec prázdné tabulky.

| Index řádku | Posunutý řetězec
|---|---|---
| 0 | R
| 1 | *(eof)*
| 2 | B
| 3 | R
| 4 | A
| 5 | A
| 6 | A
| 7 | B
| 8 | B
| 9 | A

Řádky se po každém kroku seřadí lexikograficky:

| Index řádku | Posunutý řetězec
|---|---|---
| 0 | A
| 1 | A
| 2 | A
| 3 | A
| 4 | B
| 5 | B
| 6 | B
| 7 | R
| 8 | R
| 9 | *(eof)*

Opět vložíme vstupní řetězec R *(eof)* BRAAABBA:

| Index řádku | Posunutý řetězec
|---|---|---
| 0 | RA
| 1 | *(eof)* A
| 2 | BA
| 3 | RA
| 4 | AB
| 5 | AB
| 6 | AB
| 7 | BR
| 8 | BR
| 9 | A *(eof)*

A řádky opět seřadíme:

| Index řádku | Posunutý řetězec
|---|---|---
| 0 | AB
| 1 | AB
| 2 | AB
| 3 | A *(eof)*
| 4 | BA
| 5 | BR
| 6 | BR
| 7 | RA
| 8 | RA
| 9 | *(eof)* A

Takto se postupuje stále dál, dokud není tabulka zaplněna.

| Index řádku | Posunutý řetězec
|---|---|---
| 0 | ABABRA *(eof)* ABR
| 1 | ABRABABRA *(eof)* 
| 2 | ABRA *(eof)* ABRAB
| 3 | A *(eof)* ABRABABR
| 4 | BABRA *(eof)* ABRA
| 5 | BRABABRA *(eof)* A
| 6 | BRA *(eof)* ABRABA
| 7 | RABABRA *(eof)* AB
| 8 | RA *(eof)* ABRABAB
| 9 |  *(eof)* ABRABABRA

Výsledek zpětné transformace se nachází na řádku, který končí symbolem pro konec vstupu *(eof)*. V tomto případě je to řádek s indexem 1.

ABRABABRA *(eof)* 

### Asymptotická složitost

Při implementaci se samozřejmě nemusí vytvářet žádné tabulky v paměti, které by zabíraly mnoho místa, pracuje se s ukazateli. V případě použití efektivních metod tvorby tabulky a řazení řetězců je asymptotická časová složitost €O(n\cdot\log(n))€. Při použití [datové struktury](wiki/datova-struktura) Suffix Array může klesnout až na €O(n)€.

### Reference

- http://www.stringology.org/DataCompression/bwt/index_cs.html