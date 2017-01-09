## CRON výraz

CRON výraz je speciální textový formát určený pro definici množiny časových okamžiků. Formát je to sice stručný, ale poměrně univerzální. Protože je velmi rozšířený, dávno se již nepoužívá pouze v Unixových nástrojích, ale lze na něj narazit takřka všude, kde se specifikuje čas spuštění nějaké úlohy.

Původ slova *CRON* je nejasný, ale zdá se, že je to zkratka slov "commands run over night", která se ujala i díky své podobnosti s řeckým slovem pro čas, což je **χρόνος** (chronos).

### Standardní formát

Standardní CRON výraz se skládá z pěti částí, oddělených prázdným znakem (např. mezerou):

| Název | Povolené hodnoty | Povolené speciální znaky
|---|---|---
| minuty | 0-59 | , - \*
| hodiny | 0-23 | , - \*
| den v měsíci | 1-31 | , - \* L W
| měsíc | 1-12 | , - \*
| den v týdnu | 0-7 (0, 7 = neděle) | , - \* ? L #

Význam speciálních znaků:

| Znak | Popis | Příklad
|---|---|---
| *,* | více hodnot | 1,2,3 = 1 nebo 2 nebo 3
| *-* | rozsah hodnot | 1-5 pro dny v týdnu = pondělí až pátek
| <em>*</em> | zástupný symbol pro celý rozsah hodnot | \* pro minuty = 0-59
| *L* pro den v měsíci | poslední den v měsíci (28, 29, 30 nebo 31) | L pro leden = 31
| *L* pro den v týdnu | poslední daný den v měsíci | 5L = poslední pátek v měsíci
| *W* | nejbližší pracovní den | 3W = nejbližší pracovní den po třetím dni v měsíci
| *#* | X#Y znamená každé Y-té X, kde X je číslo dne v týdnu | 1#2 = každé druhé pondělí

#### Příklady

| Výraz | Popis
|---|---
| `0 0 * * *` | každý den o půlnoci
| `0 9-18 * * *` | každý den každou hodinu od 9 do 18
| `30 22 * * Mon,Tue,Wed,Thu,Fri` | každý pracovní den v 22:30
| `15 6 2 1 *` | 2. ledna v 6:15

### Rozšíření

#### Názvy dnů a měsíců

V některých implementacích lze místo čísla měsíce používat zkratky JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC a místo čísla dne v týdnu zkratky SUN, MON, TUE, WED, THU, FRI, SAT.

#### FreeBSD zkratky

FreeBSD systémy [podporují](https://www.freebsd.org/cgi/man.cgi?crontab%285%29) další zkratky:

| Zkratka | Význam | Překlad
|---|---|---
| *@reboot* | spustit při startu CRON démona | (nelze zapsat pomocí CRON výrazu)
| *@yearly* nebo *@annually* | jednou ročně (1. ledna) | `0 0 1 1 *`
| *@monthly* | jednou měsíčně (první den v daném měsíci) | `0 0 1 * *`
| *@weekly* | jednou týdně (v neděli) | `0 0 * * 0`
| *@daily* nebo *@daily* | jednou denně (o půlnoci) | `0 0* * *`
| *@hourly* | jednou za hodinu | `0 * * * *`
| *@every_minute* | jednou za minutu | `*/1 * * * *`
| *@every_second* | jednou za sekundu | (nelze zapsat pomocí CRON výrazu)

#### Rok

Některé implementace umožňují přidat rok na konec výrazu jako jeho šestou část:

| Název | Povolené hodnoty | Povolené speciální znaky
|---|---|---
| rok | 1970-2099 | , - \* 

#### Inkrementy

Některé implementace umožňují definovat hodnoty jako inkrementy pomocí lomítka (*/*). Zápis ve tvaru *x/y* znamená €x, x+y, x+2y, x+3y, \ldots€ až do maximálního rozsahu daného pole. Například *0/15* znamená *0,15,30,45*, *4/20* znamená *4,24,44*. Hvězdička před lomítkem se chová jako nula.

#### Quartz Scheduler

[Quartz Scheduler](http://www.quartz-scheduler.org/) je velmi oblíbenou [knihovnou](wiki/quartz-scheduler) pro plánování úloh, například v [jazyce Java](wiki/java). Specifikuje však vlastní formát CRON výrazů, který jde nad rámec standardního formátu. 

Quartz CRON výraz se skládá ze šesti až sedmi částí:

| Název | Povinné | Povolené hodnoty | Povolené speciální znaky
|---|---|---|---
| sekundy | ano | 0-59 | , - \* /
| minuty | ano | 0-59 | , - \* /
| hodiny | ano | 0-23 | , - \* /
| den v měsíci | ano | 1-31 | , - \* / L W
| měsíc | ano | 1-12 nebo JAN-DEC | , - \* /
| den v týdnu | ano | 1-7 nebo SUN-SAT | , - \* / L #
| rok | ne | 1970-2099 | , - \* /

Stejné speciální znaky mají stejný význam, jako ve standardních výrazech.

#### Příklady

| Výraz | Popis
|---|---
| `0 0 0 * * ?` | každý den o půlnoci
| `0 15 10 * * ? 2001` | každý den roku 2001 v 10:15
| `0 30 8 ? * 6#3` | každý třetí pátek v měsíci v 8:30

### Reference

- http://www.cronmaker.com/
- http://www.quartz-scheduler.org/documentation/quartz-1.x/tutorials/crontrigger
- http://pubs.opengroup.org/onlinepubs/7908799/xcu/crontab.html