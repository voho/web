## Jazyk C (ANSI)

> A C program is like a fast dance on a newly waxed dance floor by people carrying razors. *Waldi Ravens*

Jazyk C je jedním z nejvýznamnějších imperativních programovacích jazyků, který zásadně ovlivnil vývoj celého odvětví IT. Jazyk C je jednoduchý, expresivní a univerzální, vyznačuje se úsporností a určitým minimalizmem. Není specializovaný pro žádnou určitou oblast a lze jej využít jak k programování mikroprocesorů, tak i k implementaci informačních systémů. Byl poprvé představen v roce 1972 a jeho autory jsou **Brian W. Kernighan** a **Dennis M. Ritchie**. Jazyk C se symbioticky vyvíjel společně s Unixem a tato skutečnost je patrná dodnes - jádro i většina programů je napsána právě v C. V průběhu času byl jazyk C několikrát vylepšen a vzniklo i několik standardů, z nichž nejznámnější je standard **ANSI 89**. Jejich cílem bylo vytvořit "bezespornou a strojově nezávislou definici jazyka C", což se splnit podařilo. Není tedy chybou považovat jazyk C za **multiplatformní**.

Jazyk C ideově vychází z jazyků *BCPL* (Martin Richards) a *B* (Ken Thompson), ke kterým přidává typy a strojově nezávislou adresovou aritmetiku, realizovanou ukazateli. Každý program v jazyce C se skládá z **proměnných** a **funkcí** (lépe řečeno podprogramů). Vstupním bodem programů je speciální funkce *main*.

> C is quirky, flawed, and an enormous success. *Dennis M. Ritchie*

### Hello world

```c
#include <stdio.h>

int main (void)
{
  puts ("Hello world!");
  return 0;
}
```

Jednoduchý program, který vypíše řetězec "Hello world!" na standardní výstup, se skládá z těchto kroků:

1. Načtení knihovny pro práci se standardním vstupem a výstupem.
1. Deklarace speciální funkce *main*, která je vstupním bodem programu.
1. Výpis řetězce "Hello world!" na standardní výstup.
1. Návrat z funkce *main* s hodnotou *0*, která oznamuje, že program skončil úspěšně.

### Proměnné

Proměnná je smysluplně pojmenované umístění v paměti počítače. Název proměnné se označuje jako **identifikátor**. Ten se může skládat z písmen, čísel a podtržítek, začínat ale může pouze podtržítkem nebo písmenem. Klíčová slova nemohou být použita jako identifikátory a délka by neměla přesáhnout "rozumné meze" (podle implementace 31 až 247 znaků).

#### Deklarace

Deklarací se rozumí zavedení nové proměnné do zdrojového kódu.

```c
int number; /* deklarace bez inicializace */
char letter = 'a'; /* deklarace s inicializací */
struct animal cat; /* deklarace strukturované proměnné "klasicky" */
Animal cat; /* deklarace strukturované proměnné "prakticky" */
```

#### Ukazatele

Ukazatel (pointer) je speciální primitivní datový typ, který si lze představit jako číslo jednoznačně určující paměťovou buňku. Ukazatel na typ *A* ukazuje na místo v paměti, kde je uložen typ *A*. Ukazatel sám o sobě nenese žádná data. Ukazatel se deklaruje pomocí hvězdičky, která se vloží za název datového typu proměnné:

```c
char * ukazatel;
```

Adresa na proměnnou se získá použitím operátoru *&*:

```c
char znak = 'A';
char * ukazatel = & znak;
```

Hodnotu v paměti, na kterou ukazuje ukazatel, lze získat tzv. **dereferencí**, která se zapisuje jako hvězdička před ukazatelem:

```c
char znak = 'A';
char * ukazatel = & znak;
char kopie = * ukazatel;
```

Pro přístup k vnitřním datovým hodnotám nějaké struktury lze použít tyto dva ekvivalentní zápisy:

```c
(* cat).legs = 4;
cat -> legs = 4;
```

#### Pole

Pole v jazyku C není nic víc, než ukazatel na svůj první prvek. Jeho velikost se nastavuje při alokaci a za běhu ji nelze měnit (nic však nebrání pole zkopírovat do většího prostoru a dále rozšiřovat).

##### Alokace pole

Rezervace souvislého paměťového prostoru na žádost programu se nazývá **alokace**. Během tohoto procesu požádá program operační systém o souvislý blok paměti o velikosti *N*, kde *N* je součin velikosti jednoho prvku (v bajtech) a požadovaného počtu prvků. Je-li takový souvislý blok k dispozici, je rezervován pro daný proces a program do něho může ukládat data. Tento prostor je pak třeba před ukončením uvolnit (i když to dnes udělá operační systém za programátora, není vhodné na to spoléhat). Alokace se provádí pomocí funkce *malloc* a velikost datového typu se zjistí použitím konstruktu *sizeof*.

Funkce *malloc* vrací beztypový ukazatel typu *void \**, takže je nutné jej před přiřazením přetypovat. Po alokaci je více než vhodné zkontrolovat výsledek této operace - může se stát, že počítači dojde paměť a program namísto elegantního ukončení spadne.

```c
/* alokace dynamické struktury (klasicky) */

struct animal * cat = (struct animal *) malloc (sizeof (struct animal));

/* alokace dynamické struktury (prakticky) */

Animal * cat = (Animal *) malloc (sizeof (Animal));

/* alokace pole typu "int" o velikosti 10 prvků */

int * arr = (int *) malloc (sizeof (int) * 10);

/* velmi doporučeno!!! */

if (arr == NULL)
{
  /* alokace neproběhla úspěšně, možná není dost paměti */
}
```

##### Dealokace pole

Dealokace paměti se provádí pomocí funkce *free*. Ihned po dealokaci se doporučuje nastavit ukazateli nějakou "neškodnou" hodnotu, což nejlépe splňuje hodnota *NULL*.

```c
free(arr);

/* velmi doporučeno!!! */

arr = NULL;
```

#### Primitivní datové typy

| Název | Typ | Rozsah | Velikost (obvyklá)
|---|---|---|---
| (unsigned) char | znak | 0 až 255 | 1 bajt
| signed char | znak | -128 až 127 | 1 bajt
| (signed) short int | celé číslo | -32768 až 32767 | 2 bajty
| unsigned short int | celé číslo | 0 až 65535 | 2 bajty
| (signed) int | celé číslo | -2147483648 až 2147483647 | 4 bajty
| unsigned int | celé číslo | 0 až 4294967295 | 4 bajty
| float | reálné číslo | +/- 3.4e +/- 38 (cca 7 míst) | 4 bajty
| double | reálné číslo | +/- 1.7e +/- 308 (cca 15 míst) | 8 bajtů
| void | obecný typ | nemá | -
| A * | ukazatel na A | nemá | 4 bajty

#### Strukturované datové typy

V jazyce C se nachází i strukturované datové typy, které se dají skládat z primitivních a dalších strukturovaných typů. Tyto datové typy se označují jako **struktury** (nebo ještě obecněji jako **záznamy** (records)).

##### Definice struktury

```c
/* první možnost (klasická) */

struct animal
{
  int eyes;
  int legs;
}

/* deklarace proměnné tohoto typu */

struct animal cat;
```

```c
/* druhá možnost (praktická) */

typedef struct animal
{
  int eyes;
  int legs;
} Animal;

/* deklarace proměnné tohoto typu */

Animal cat;
```

##### Přístup k prvkům

```c
/* zápis do struktury */

cat.eyes = 2;
cat.legs = 4;

/* čtení ze struktury */

printf ("cat has %d eyes", cat.eyes);
printf ("cat has %d legs", cat.legs);
```

### Operátory

| Operátor | Typ | Význam | Příklad
|---|---|---|---
| + | aritmetický | součet dvou čísel | a = 1 + 4
| - | aritmetický | rozdíl dvou čísel | b = 5 - 2
| * | aritmetický | součin dvou čísel | c = 5 * 6
| / | aritmetický | podíl dvou čísel | d = 10 / 3
| % | aritmetický | zbytek po dělení dvou čísel | e = 12 % 5

### Podmínky (if)

```c
int a = 42;

if (a == 0)
{
  puts ("A je nula");
}
else if (a % 2 == 0)
{
  puts ("A je sude");
}
else
{
  puts ("A je liche");
}
```

### Přepínače (switch)

```c
switch (number)
{
  case 3:
    puts ("Tri orisky pro popelku");
    break;
  case 7:
    puts ("Sedm trpasliku");
    break;
  default:
    puts ("Nic me nenapada...");
    break;
}
```

### Cykly

#### While

Cyklus "while" má tvar *WHILE (výraz) příkaz*. Nejprve se vyhodnotí *výraz*. Je-li nenulový, provede se *tělo cyklu* a výraz se znovu vyhodnotí. Takto se pokračuje, dokud je *výraz* nenulový. Cyklus *while* se používá zejména tam, kde není nutné provádět před spuštěním cyklu žádnou inicializaci.

```c
int a = 0;

while (a < 10)
{
  printf ("%d", a);
  a = a + 1;
}
```

#### Do While

Cyklus "do while" má tvar *DO příkaz WHLE výraz*. Nejprve je vykonán *příkaz*, pak je vyhodnocena *výraz*. Je-li jeho výsledek nenulový, je *příkaz* vykonán znovu. Takto se pokračuje, dokud je *výraz* nenulový.

```c
int a = 0;

do
{
  printf ("%d", a);
  a = a + 1;
}
while (a < 10);
```

#### For

Cyklus "for" má tvar *FOR (inicializace, podmínka, operace) příkaz* a je zobecněním cyklu *while*. Používá se především tam, kde je před spuštěním cyklu nutné provést nějakou inicializaci. Tím, že jsou všechny tři řídící výrazy v hlavičce, zvyšuje tento zápis přehlednost. Jeho hlavička obsahuje tři části:

- **inicializaci** - co se provede před spuštěním cyklu
- **podmínku** - podmínka, která se testuje před každým spuštěním cyklu
- **operaci** - příkaz, který se provede na konci každého cyklu

```c
#include <stdio.h>

int main (void)
{
  int f;

  for (f = 0; f <= 300; f = f + 20)
  {
    printf ("%d\t%6.1f\n", f, (5.0 / 9.0) * (f - 32));
  }
}
```

### Funkce

Funkcí se rozumí podprogram, který může vracet návratovou hodnotu. Rozdělování kódu na menší celky vede k větší přehlednosti a lepší struktuře zdrojového kódu.

#### Deklarace

Deklarací se rozumí zavedení nové funkce do zdrojového kódu. Deklarace každé funkce by měla obsahovat **návratovou hodnotu**, seznam **parametrů** a jejich **typů** a **identifikátor** představující název funkce. Pokud funkce nemá žádné parametry, jako jediný parametr se explicitně uvádí *void*.

```c
/* identifikátor této funkce je "max" */
/* návratová hodnota funkce je "int" */
/* funkce má dva parametry: "a", "b" typu "int" */

int max (int a, int b)
{
  if (a > b)
  {
    return a;
  }
  else
  {
    return b;
  }
}
```

```c
/* identifikátor této funkce je "printLine" */
/* funkce nemá návratovou hodnotu */
/* funkce má jeden parametr "s" typu "int" */

void printLine (int s)
{
  int i;

  for (i = 0; i < s; i++)
  {
    putchar ('-');
  }

  putchar ('\n');
}
```

### Rozdělení do souborů

Zdrojový kód v jazyce C lze pro větší přehlednost rozdělit do více souborů. Každý takový soubor může obsahovat související funkce a proměnné. Každý soubor je kompilován zvlášť, čímž vzniká nespustitelný binární soubor (object file). Aby bylo možné v souborech používat nelokální symboly (proměnné, struktury, funkce, ...) z jiných souborů, je nutné získat jejich deklarace. K tomuto účelu vznikají speciální soubory obsahující právě tyto deklarace. Nazývají se **hlavičkové soubory** (header files) a obvykle končí příponou **.h**. Před použitím všech symbolů musí být tyto symboly deklarovány.

#### Příklad

Následující jednoduchý příklad čítače se skládá ze tří souborů:

- **counter.h** - hlavičkový soubor obsahující rozhraní čítače
- **counter.c** - soubor s implementací čítače
- **main.c** - testovací soubor

##### counter.h

```c
/* následující makra zabraňují tomu, aby byl obsah hlavičkového souboru vložen dvakrát */

#ifndef COUNTER_H
#define COUNTER_H

/* hlavička funkce, jejíž implementace se nachází v jiném souboru */

void increment (void);
void decrement (void);
int getValue (void);

#endif
```

##### counter.c

```c
#include "counter.h"

/* statické symboly nejsou nikdy viditelné z jiných souborů */

static int counter = 0;

static void change (int delta)
{
  counter += delta;
}

/* následující symboly jsou viditelné z jiných souborů */

void increment (void)
{
  change (1);
}

void decrement (void)
{
  change (-1);
}

int getValue (void)
{
  return counter;
}
```

##### main.c

```c
/* standardní hlavičkové soubory se uvádí ve špičatých závorkách (nejsou v aktuálním adresáři) */
/* nestandardní (uživatelské) hlavičkové soubory se uvádí v uvozovkách (jsou v aktuálním adresáři) */

#include <stdio.h>
#include "counter.h"

int main (void)
{
  printf ("Hodnota citace na zacatku: %d\n", getValue ());
  increment ();
  increment ();
  decrement ();
  increment ();
  decrement ();
  increment ();
  increment ();
  decrement ();
  printf ("Hodnota citace na konci: %d\n", getValue ());

  return 0;
}
```

> In My Egotistical Opinion, most people's C programs should be indented six feet downward and covered with dirt. *Blair P. Houghton*

### Reference

- Brian W. Kernighan, Dennis M. Ritchie: Programovací jazyk C
- http://www.di-mgt.com.au/cprog.html
- http://www.cprogramming.com/tutorial.html#ctutorial
- http://cprogramminglanguage.net/c-programming-language-tutorial.aspx
- http://www.hpl.hp.com/personal/Hans_Boehm/gc/
- http://trashwiki.cz/doku.php/lang:bit