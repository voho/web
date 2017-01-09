## Jazyk PHP

Jazyk PHP (Hypertext PreProcessor) je intepretovaný jazyk určený pro web. Je celkem jednoduchý, snadno přenositelný a tedy vhodný zejména pro začínající programátory. Využívá se k vytváření menších až středních internetových aplikací, například osobních webů, blogů, e-shopů, wikipedií, a podobně.

Web PHP s dokumentací se nachází na adrese [php.net](http://php.net/).

### Psaní PHP skriptů

PHP skripty se běžně píší v **textovém editoru** (např. [PSPad](http://www.pspad.com/cz/)) a ukládají na **webový server** do souborů s příponou *.php*. Pokud je PHP správně nastaveno, po zadání správné adresy požadovaného souboru je skript spuštěn a jeho výstup zobrazen v **prohlížeči**.

### Začátek a konec PHP skriptu

Při zpracování souboru zpracuje interpreter PHP jen to, co se nachází mezi speciálními značkami **<?php** a **?>**. Zbytek jednoduše ignoruje (pošle to na výstup tak, jak to je). To umožňuje jednoduše kombinovat kód PHP a HTML.

```php
...HTML tagy...
<?php
// nějaký PHP skript
?>
...HTML tagy...
<?php
// další PHP skript
?>
...HTML tagy...
```

### Hello World

```php
<?php echo "Hello World"; ?>
```

### Proměnné

Proměnná je část paměti, se kterou může program během svého spuštění pracovat. Ukládáme do nich data, jako například čísla, řetězce, pole, instance tříd a další. Práce s proměnnými patří mezi základní operace každého programu. Ani PHP skripty nejsou výjimkou.

#### Základní datové typy

Narozdíl od jazyka C, C++, Java a podobných, nemají proměnné v PHP pevně určený (nadeklarovaný) typ. Do jedné proměnné lze uložit mnoho různých typů hodnot a ty lze dokonce za běhu programu střídat. Typy se tím pádem nikde neuvádějí - ani v deklaraci, ani v parametrech funkcí.

Zde jsou základní typy hodnot, které mohou být v proměnných uloženy:

```php
<?php

$a = 1; // celé číslo
$b = 3.14159265; // desetinné číslo
$c = true; // logická hodnota
$d = "Hello World"; // řetězec
$e = array ($a, 2, 3); // pole
$f = new Trida (); // instance třídy
$g = null; // speciální hodnota "nic"

?>
```

#### Zásady

I když je PHP velmi benevolentní, doporučuji se řídit dle následujících zásad:

- proměnné vhodně pojmenujte (ani krátce, ani dlouze)
- do jedné proměnné ukládejte pokud možno jen jeden typ hodnot
- proměnné před použitím vždy inicializujte (nastavte jejich výchozí hodnotu)

Proč to vysvětluji? Je potřeba si uvědomit, jak zhruba fungují "střeva" PHP a nesnažit se o zbytečné optimalizace tam, kde jich není třeba. Uvedené poznatky také využijete při psaní funkcí a procedur, kde se parametry chovají podobně jako reference.

### Pole

Jednorozměrné pole je lineární, homogenní a sekvenční datová struktura - zjednodušeně řečeno tedy jakási kolekce prvků. Využívá se pro uložení většího množství dat stejného typu, zpravidla proto, aby je bylo možné zpracovat hromadně pomocí cyklu.

Prvky pole v PHP (podobně jako proměnné) nemusí být stejného typu. V jednom poli se tedy mohou nacházet čísla, řetězce, instance tříd a dokonce i další pole.

#### Inicializace

Pole se v jazyce PHP inicializuje takto:

```php
<?php

// inicializace prázdného pole
$pole = array ();

// inicializace pole s hodnotami
$pole = array ("Jana", 21, 182.6);

// inicializace asociativního pole s hodnotami
$pole = array ("jméno" => "Karel", "věk" => 22);

// inicializace míšeného pole s hodnotami
$pole = array ("jméno" => "Eliška", 90, 60, 90);

// inicializace vnořeného pole
$pole = array ("Thomas", array (177, 75), array ("kalhoty", "mikina", "triko"));

?>
```

Nyní je možné s polem pracovat, například do něj vkládat prvky, odebírat je a měnit. Pole v PHP nemá pevně danou velikost, je tedy možné brát jej jako "pytel", do kterého lze neomezeně sypat prakticky cokoliv. Příliš se nedoporučuje míchat asociativní a obyčejná pole, protože může vzniknout zmatek s indexy a klíči.

#### Vkládání prvků

```php
<?php

// vkládání prvků do pole
$pole = array ();
$pole [] = "Marie";
$pole [] = "Hana";
$pole [] = 15.54532;

// vkládání prvků do asociativního pole
$pole = array ();
$pole ["klíč"] = "hodnota";
$pole ["jiný klíč"] = "jiná hodnota";
$pole [155] = "hodnota s číselným klíčem";
$pole [-487.41] = "hodnota s podivným klíčem";

?>
```

#### Přístup k prvkům

Každý prvek v poli má svůj index, který jej jednoznačně určuje. Indexy začínají od nuly. Pokud používáme pole asociativní, můžeme k prvkům navíc přistupovat pomocí jejich klíče.

```php
<?php

// inicializace pole
$pole = array ();
$pole ["triko"] = "modrá";
$pole ["čepice"] = "zelená";
$pole ["mikina"] = "červená";

// vypsání modré
echo $pole [0];
echo $pole ["triko"];

// vypsání červené
echo $pole [2];
echo $pole ["mikina"];

?>
```

Nikdy moc nemíchejte způsoby přístupu k prvkům, jinak vás čeká peklo. Zde je jeden odstrašující příklad pro představu:

```php
<?php

$pole ["značka"] = "BMW";
echo $pole [0]; // vypíše BMW
$pole [0] = "Wartburg"; // nahradí BMW Wartburgem
echo $pole [0]; // vypíše Wartburg
$pole ["0"] = "Trabant"; // nahradí Wartburg Trabantem
echo $pole [0]; // vypíše Trabant

?>
```

#### Mazání prvků

V PHP obvykle není nutné cokoliv mazat, protože to za nás udělá virtuální stroj - ale někdy potřebujeme objekty odstraňovat kvůli určité funkcionalitě v aplikaci. Mazání provedeme pomocí speciálního konstruktu **unset**, který jako parametr vyžaduje prvek, který chceme odstranit.

```php
<?php

// smaže první prvek pole
unset ($pole [0]);

// smaže prvek s klíčem "admin"
unset ($pole ["admin"]);

// smaže celé pole
unset ($pole);

?>
```

### PHP - podmínky a cykly

V každém programovacím jazyce se můžeme setkat s podmínkami a cykly. Právě ty umožňují programu reagovat na dvě různé situace jinak a opakovaně provádět podobné operace nad velkým množstvím dat.

### Podmínky

Podmínka je jazykový konstrukt, který na základě výsledku daného **logického výrazu** rozhoduje o provedení či přeskočení nějakého bloku příkazů. Ukážeme si pár jednoduchých příkladů.

Podmínka začíná klíčovým slovem *if*.

```php
<?php

// jeden příkaz není nutné uzavírat do bloku

if ($a == 0) echo "A je nula";

// více příkazů se uzavře do bloku

if ($a == 0)
{
  echo "Pozor...";
  echo "A je nula";
}

?>
```

Klíčové slovo *else* umožňuje programu reagovat i na situace, kdy podmínka splněna není.

```php
<?php

if ($hodina > 6 && $hodina < 15)
{
  echo "Jsem v práci.";
  echo "Dnes toho mám opravdu hodně.";
}
else
{
  echo "Jsem nejspíš doma.";
}

?>
```

Následující příklad ukazuje vnoření podmínek do sebe a také zjednodušení pomocí speciálního klíčového slova *elseif*.

```php
<?php

$a = 42;

// první možnost (if, else)

if ($a == 0)
{
  echo "číslo A je nulové";
}
else
{
  if ($a % 2 == 1)
  {
    echo "číslo A je liché";
  }
  else
  {
    echo "číslo A je sudé";
  }
}

// druhá možnost (if, else, elseif)

if ($a == 0)
{
  echo "číslo A je nulové";
}
elseif ($a % 2 == 1)
{
  echo "číslo A je liché";
}
else
{
  echo "číslo A je sudé";
}

?>
```

### Switch

Jazykový konstrukt *switch* v určitých případech velmi zjednodušuje zápis algoritmu. Jedná se o rozhodovací mechanismus, který vyhodnotí výraz a skočí na ten řádek, který je výslednou hodnotou označen (*case*). Od tohoto řádku dál vykoná program všechny příkazy, dokud nenarazí na klíčové slovo *break* (může tedy "přejet" i na další *case*). Pokud hodnota neodpovídá ani jednomu případu, skočí se na nepovinný výchozí příkaz (*default*).

```php
<?php

$jmeno = "Iva";

switch ($jmeno)
{
  case "Julie":
  case "Iva":
  case "Pavla":
    echo "Ahoj kočko!";
    break;
  case "Petr":
  case "Mirek":
    echo "Zdar, jdeme na jedno?";
    break;
  default:
    echo "Sorry, neznám tě.";
    break;
}

?>
```

### Cyklus WHILE (s podmínkou na začátku)

Syntaxe: **while (výraz) {příkazy}**

Cyklus **while** je nejjednodušším cyklem v PHP. Na začátku každé **iterace** je nejprve vyhodnocen **výraz** v příkazu **while**. Je-li výsledek roven **TRUE**, provede se **vnořený blok příkazů** a je spuštěna další iterace. Pokud je výraz již před začátkem první iterace roven **FALSE**, vnořený blok se neprovede vůbec.

```php
<?php

$a = 10;
while ($a > 0)
{
  echo $a . "...";
  $a--;
}

?>
```

### Cyklus DO WHILE (s podmínkou na konci)

Syntaxe: **do {příkazy} while (výraz)**

Cyklus **do while** je velmi podobný cyklu **while**, ale narozdíl od něj je **výraz** v příkazu **while** vyhodnocován až na konci každé **iterace**. Je tedy zaručeno, že se **vnořený blok příkazů** provede vždy alespoň jednou.

```php
<?php

$a = 10;
do {
  echo $a . "...";
  $a--;
} while ($a > 0);

?>
```

### Cyklus FOR

Syntaxe: **for (výraz1; výraz2; výraz3) {příkazy}**

Cyklus **for** je nejsložitějším cyklem v PHP. Skládá se ze tří výrazů:

- **výraz1** se provede vždy před první iterací
- **výraz2** se vyhodnotí před každou iterací - pokud je roven **FALSE**, cyklus skončí
- **výraz3** se provede na konci každé iterace

```php
<?php

for ($i = 0; $i < 10; $i++)
{
  echo $i . '. Už tam budeme?<br />';
}

?>
```

### Příkazy BREAK a CONTINUE

V cyklech je možné použít příkazy *break* a *continue*. Jejich význam je následující:

- *break* - okamžitě ukonči iteraci a vyskoč z cyklu
- *continue* - okamžitě ukonči iteraci, vyhodnoť logický výraz a pokud je roven **TRUE**, začni novou iteraci

Následující příklad ukazuje použití příkazů *break* a *continue*.

```php
<?php

$i = 0;
while (true)
{
  // pokud je číslo moc vysoké, ukonči cyklus
  if ($i > 10000) break;

  // přeskočit všechna sudá čísla
  if ($i % 2 == 0) continue;

  // vypsat číslo
  echo $i . "...";

  // inkrementovat
  $i++;
}

?>
```

### Funkce a procedury

Funkce a procedury jsou základními stavebními bloky strukturovaných programů. Kód, který se opakuje na více místech, je dobré prohlásit za samostatný podprogram a ten na odpovídajících místech pouze spustit - **zavolat**.

Lepší struktura kódu umožňuje dosahovat více úrovní abstrakce a klade tak menší požadavky na paměť programátora. Celý tento proces by se dal přirovnat k matematické substituci.

Funkce a procedury pomáhají zpřehlednit kód, zvýšit modularitu a tak usnadnit údržbu i rozšiřování programu.

Rozdíl mezi funkcí a procedurou je pouze formální - *procedura* nevrací žádnou hodnotu, zatímco *funkce* ano. V PHP je tento rozdíl nepodstatný, funkce i procedury se deklarují i volají stejně. Proto se v dalším textu bude vyskytovat pojem **funkce**, neboť je obecnější.

Funkce mohou být volány s **parametry** - hodnotami, které ovlivňují její průběh. Počet parametrů je pro každou funkci pevně daný. Funkce je před použitím nutné **nadeklarovat** - tedy definovat již zmíněné parametry a **tělo funkce** - tedy program, který se provede po jejím zavolání.

#### Deklarace funkcí

Funkce se deklaruje takto:

```php
<?php

function pozdrav ()
{
  echo "ahoj";
}

?>
```

Funkce se dvěma parametry a návratovou hodnotu takto:

```php
<?php

function prumer ($a, $b)
{
  return ($a + $b) / 2;
}

?>
```

#### Volání funkcí

Funkce bez parametrů se volá takto:

```php
<?php pozdrav (); ?>
```

A funkce s parametry takto:

```php
<?php $c = prumer ($a, $b); ?>
```

Návratovou hodnotu funkce lze dokonce ignorovat (není to však známka špatného kódu?):

```php
<?php prumer ($a, $b); ?>
```

#### Věstavěné funkce

Jádro PHP spolu s moduly obsahuje velké množství věstavěných funkcí. Jejich seznam naleznete v dokumentaci PHP. Je lepší používat tyto funkce než si ke stejným účelům programovat vlastní, protože jsou optimalizované a podobně jako virtuální stroj zkompilované do strojového kódu.

### Výrazy

Výrazem rozumíme syntakticky správnou posloupnost *operandů* a *operátorů*, která zpravidla vrací nějaký výsledek. Příkladem výrazu může být **matematický výraz**, **logický výraz** (vrací výsledek) nebo **volání funkce** (vrací návratovou hodnotu).

Matematické výrazy se používají všude tam, kde chceme něco vypočítat. Logické výrazy využijeme především v podmínkách.

#### Matematické výrazy

```php
<?php

$a = 3.14 * 5.5 * 5.5 * 8;
$b = (150 - $a + 44) / (487 * $a);
$c = (($a * $b) - 150) % 100;
$d = ($a + $b + $c) / 3;

?>
```

#### Matematické operátory

| Příklad | Operace
|---|---
| -$a | opačná hodnota k $a
| $a + $b | součet $a a $b
| $a - $b | rozdíl $a a $b
| $a * $b | součin $a a $b
| $a / $b | podíl $a a $b
| $a % $b | zbytek z $a po dělení $b

#### Logické výrazy

```php
<?php

$yes = true;
$no = false;
$answer = ((5 < 10) && ($no || ! $yes) != $no);

?>
```

#### Logické operátory

| Příklad | Operace
|---|---
| ! $a | negace $a
| $a and $b | $a a zároveň $b
| $a or $b | $a nebo $b
| $a xor $b | $a nebo $b, ale ne zároveň
| $a && $b | $a a zároveň $b
| $a \|\| $b | $a nebo $b

Rozdíl mezi operátory **and, or** a **&&, ||** je v prioritě vyhodnocování.

```php
<?php

$a = false || true; // $a bude true, dříve se vyhodnotí pravá strana
$b = false or true; // $b bude false, dříve se vyhodnotí levá strana

?>
```

#### Porovnávací operátory

| Příklad | Operace
|---|---
| $a == $b | $a rovná se $b
| $a === $b | $a je identické s $b (stejná hodnota, stejný typ)
| $a != $b | $a je různé od $b
| $a <> $b | $a je různé od $b
| $a !== $b | $a není identické s $b (různá hodnota nebo jiný typ)
| $a < $b | $a je menší než $b
| $a <= $b | $a je menší nebo rovno $b
| $a > $b | $a je větší než $b
| $a >= $b | $a je větší nebo rovno než $b

### Reference

- http://php.net