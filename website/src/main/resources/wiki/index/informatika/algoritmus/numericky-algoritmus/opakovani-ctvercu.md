## Metoda opakování čtverců

Metoda opakování čtverců je algoritmus urychlující umocňování celých čísel v nějakém **okruhu modulo n**. Je založen na rozkladu mocnitele na mocniny dvojky.

### Postup při výpočtu

Výpočet se provádí následujícím postupem:

1. Exponent se převede do binární soustavy.
1. Z tohoto binárního zápisu se vytvoří tzv. **řídící řetězec** složený ze znaků *S* (square) a *X* (times).
1. Na základě řídícího řetězce se provede výpočet, který se dá přirovnat ke spuštění programu na střadačovém procesoru.

### Tvorba řídícího řetězce

Řídící řetězec lze připodobnit k programu jednoduchého **střadačového procesoru** s celočíselným střadačem *W*. Na počátku je ve střadači hodnota 1. Procesor má dvě instrukce *S* (square) a *X* (times), které mají následující sémantiku:

| Instrukce | Sémantika | Popis
|---|---|---
| **S** | W = (W * W) % N | do střadače ulož druhou mocninu střadače modulo *N*
| **X** | W = (W * A) % N | do střadače ulož násobek střadače a základu *A*, to celé modulo *N*

Řídící řetězec se vytváří na základě binárního zápisu exponentu. Ten se čte po znacích zleva doprava a řídící řetězec vzniká postupně dle následujících pravidel:

- *0* se přeloží na *S*
- *1* se přeloží na *XS*
- poslední znak řídícího řetězce (vždy *S*) se odebere

Několik příkladů exponentů a jejich řídících řetězců:

| Exponent | Binární zápis | Řídící řetězec
|---|---|---
| 1 | 1 | X
| 2 | 10 | XS
| 5 | 101 | XSSX
| 15 | 1111 | XSXSXSX
| 28 | 11100 | XSXSXSS
| 44 | 101100 | XSSXSXSS
| 72 | 1001000 | XSSSXSSS
| 89 | 1011001 | XSSXSXSSSX
| 153 | 10011001 | XSSSXSXSSSX

Také je možné jednotlivé číslice binárního zápisu proložit znaky *S* a na pozice, kde je *1*, zapsat *X*. Oba uvedené způsoby jsou ekvivalentní.

| Číslo | Binárně | Vložení znaku S | Vložení znaku X | Výsledek
|---|---|---|---|---
| 5 | 101 | 1*S*0*S*1 | *X*S0S*X* | XSSX
| 44 | 101100 | 1*S*0*S*1*S*1*S*0*S*0 | *X*S0S*X*S*X*S0S0 | XSSXSXSS
| 89 | 1011001 | 1*S*0*S*1*S*1*S*0*S*0*S*1 | *X*S0S*X*S*X*S0S0S*X* | XSSXSXSSSX

### Příklady

#### Zadání

Umocněte 17 na 51 v okruhu modulo 312.

€€ 17^{51} \;\;\mathrm{v}\; \mathbb{Z}_{312} €€

#### Řešení

Nejprve převedeme číslo **51** do binární soustavy. Výsledkem je číslo **110011**, ze kterého vytvoříme řídící řetězec *XSXSSSXSX*. Znaky řídícího řetězce napíšeme pod sebe a zahájíme výpočet.

| Instrukce | Význam | Výsledek (akumulátor)
|---|---|---
| - | inicializace | 1
| X | vynásob 17 | 17
| S | umocni | 17 * 17 = 289
| X | vynásob 17 | 289 * 17 = 4913 = 233
| S | umocni | 233 * 233 = 1
| S | umocni | 1 * 1 = 1
| S | umocni | 1 * 1 = 1
| X | vynásob 17 | 1 * 17 = 17
| S | umocni | 17 * 17 = 289
| X | vynásob 17 | 289 * 17 = 4913 = 233

Výsledek je tedy:

€€ 17^{51} = 233 \;\;\mathrm{v}\; \mathbb{Z}_{312} €€

#### Zadání

Umocněte 571 na 2691 v okruhu modulo 1469.

€€ 571^{2691} \;\;\mathrm{v}\; \mathbb{Z}_{1469} €€

#### Řešení

Nejprve převedeme číslo **2691** do binární soustavy. Výsledkem je číslo **100001101**, ze kterého vytvoříme řídící řetězec *XSSSSSXSXSSX*. Znaky řídícího řetězce napíšeme pod sebe a zahájíme výpočet.

| Instrukce | Význam | Výsledek (akumulátor)
|---|---|---
| - | inicializace | 1
| X | vynásob 571 | 571
| S | umocni | 571 * 571 = 326041 = 1392
| S | umocni | 1392 * 1392 = 1937664 = 53
| S | umocni | 53 * 53 = 1340
| S | umocni | 1340 * 1340 = 1795600 = 482
| S | umocni | 482 * 482 = 232324 = 222
| X | vynásob 571 | 222 * 571 = 126762 = 428
| S | umocni | 428 * 428 = 183184 = 1028
| X | vynásob 571 | 1028 * 571 = 586988 = 857
| S | umocni | 857 * 857 = 734449 = 1418
| S | umocni | 1418 * 1418 = 2010724 = 1132
| X | vynásob 571 | 1132 * 571 = 646372 = 12

Výsledek je tedy:

€€ 571^{2691} = 12 \;\;\mathrm{v}\; \mathbb{Z}_{1469} €€

### Princip

€€ a^{11} = a^{2^3 + 2^1 + 2^0} = ((a^2)^2 \cdot a)^2 \cdot a \rightarrow \mathrm{XSSXSX} €€

### Reference

- předmět X01AVT na FEL ČVUT