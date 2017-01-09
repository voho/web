## Pole (array)

Pole je nejjednodušší, nejstarší a nejčastěji používaná datová struktura v informatice. V paměti je uloženo jako lineárně uspořádaná posloupnost prvků stejného typu, ke kterým lze přistupovat pomocí tzv. **indexu**. Ten lze chápat jako **vzdálenost** požadovaného prvku od počátku (prvního prvku) pole - tedy jako souřadnici v jednorozměrném prostoru.

Pokud z důvodu přehlednosti či intuitivnosti používáme k adresaci prvku indexů více (třeba *N*), mluvíme o poli N-rozměrném. Počet rozměrů pole odpovídá počtu souřadnic (indexů), kterými je popsáno umístění každého jednotlivého prvku v paměti. V tom případě bývá pole v paměti uloženo jako "pole polí", nebo jako pole jednorozměrné, jehož index je přepočítán ze všech indexů pomocí tzv. **mapovací funkce** (viz níže).

### Jednorozměrná pole

V jednorozměrných polích je každý prvek určen právě jedním indexem (souřadnicí). Jednorozměrná pole se nejčastěji používají pro ukládání vektorů (pole čísel) a řetězců (pole znaků).

```dot:digraph
table [shape=record,label="<i>5|-24|88|3|922"]
array -> table:i 
array [shape=none]
```

#### Situace v paměti

| Adresa paměťové buňky | Index hodnoty | Hodnota
|---
| proměnná POLE || ukazatel na 0x01A2
| ... | ... | ... |
| 0x01A2 | POLE+0 | 5
| 0x01A3 | POLE+1 | -24
| 0x01A4 | POLE+2 | 88
| 0x01A5 | POLE+3 | 3
| 0x01A6 | POLE+4 | 922
| ... | ... | ... |

Pole v dnešních programovacích jazycích mají zpravidla pevně danou velikost (**počet prvků** x **velikost prvku**), ale některé interpretované jazyky jejich velikost dokáží měnit automaticky (podle potřeby).

Při nedostatečné kapacitě pole požádá program operační systém o větší (resp. menší) volný prostor (který nemusí být k dispozici!), do kterého překopíruje stávající data. Původní pole následně uvolní. Větší pohodlí programátora je vykoupeno větší režií. Menší dynamická pole se však většinou lépe realizují pomocí dynamických seznamů.

### Vícerozměrná pole

Matice a tabulky jsou příkladem polí dvojrozměrných. Každý prvek určují dvě souřadnice - index řádku a index sloupce. Trojrozměrné pole je zase podobné Rubikově kostce. Pole vyšších rozměrů si již představit neumíme, ale přesto se hojně v programech používají a jsou velmi užitečná.

Ukázka jednotkové matice 3x4, uložené v "poli polí":

```dot:digraph
ratio=0.5
p [shape=record,label="<a>|<b>|<c>"]
a [shape=record,label="<a>1|4|2|0"]
b [shape=record,label="<a>3|44|-2|1"]
c [shape=record,label="<a>0|2|44|-9"]
p:a->a:a
p:b->b:a
p:c->c:a
```

#### Situace v paměti

| Adresa paměťové buňky | Index hodnoty | Hodnota
|---
| proměnná POLE || ukazatel na 0x5A01
| ... | ... | ... |
| 0x5A01 | POLE+0 | ukazatel na 0x6122
| 0x5A02 | POLE+1 | ukazatel na 0x6132
| 0x5A03 | POLE+2 | ukazatel na 0x610A
| ... | ... | ... |
| 0x610A | POLE[2]+0 | 0
| 0x610B | POLE[2]+1 | 0
| 0x610C | POLE[2]+2 | 1
| ... | ... | ... |
| 0x6122 | POLE[0]+0 | 1
| 0x6123 | POLE[0]+1 | 0
| 0x6124 | POLE[0]+2 | 0
| ... | ... | ... |
| 0x6132 | POLE[1]+0 | 0
| 0x6133 | POLE[1]+1 | 1
| 0x6134 | POLE[1]+2 | 0
| ... | ... | ... |

#### Mapovací funkce

Použitím mapovací funkce lze ušetřit nějakou tu paměť za cenu dodatečných výpočtů při indexaci. Jak na to? Prvky vícerozměrného pole postupně (po řádcích či po sloupcích) uložíme do pole jednorozměrného. Požadovaný prvek pak nalezneme tak, že všechny indexy nejprve předložíme tzv. **mapovací funkci**, které je převede na jediný index v tomto jednorozměrném poli. K její sestavení však musíme znát maximální počet prvků v každém rozměru pole.

Mapovací funkce naleznou uplatnění i tam, kde s jiným než jednorozměrným polem pracovat nemůžeme (jednoduché procesory, staré programovací jazyky).

##### Dvojrozměrné pole na jednorozměrné

Takto vypadá mapovací funkce, která mapuje dvojrozměrné pole o velikosti € M \times N € do pole jednorozměrného (*M* je počet prvků v jednom řádku):

€€ f(x,y) = x + M \cdot y €€

##### N-rozměrné pole na jednorozměrné

Zobecněním mapovací funkce z předchozího výkladu získáme návod na sestavování mapovacích funkcí i pro pole vyšších rozměrů (*Ni* je maximální počet prvků v rozměru *i*).

€€ f(x_1,...,x_n) = x_1 + \sum_{i=2}^{n} N_i \cdot x_i €€

##### Situace v paměti - mapovací funkce

Pro převod indexů bude použita tato mapovací funkce (viz níže):

€€ i = x + 3 \cdot y €€

| Adresa paměťové buňky | Index hodnoty | Hodnota
|---
| proměnná POLE || ukazatel na 0x5A01
| ... | ... | ... |
| 0x5A01 | POLE+0 | 1
| 0x5A02 | POLE+1 | 0
| 0x5A03 | POLE+2 | 0
| 0x5A04 | POLE+3 | 0
| 0x5A05 | POLE+4 | 1
| 0x5A06 | POLE+5 | 0
| 0x5A07 | POLE+6 | 0
| 0x5A08 | POLE+7 | 0
| 0x5A09 | POLE+8 | 1
| ... | ... | ... |

### Příklad v jazyce Java

Takto se v Javě pracuje s jednorozměrným polem:

```java
// deklarace s výchozími hodnotami

int pole1 [] = {1, 0, 5, -4, 55};

// univerzálnější deklarace

int pole2 [];
pole2 = new int [5];
pole2 [0] = 1;
pole2 [1] = 0;
pole2 [2] = 5;
pole2 [3] = -4;
pole2 [4] = 55;

// výpis jednoho prvku

System.out.println (pole1 [3]);

// výpis celého pole

for (int i=0; i < pole1.length; i++)
{
  System.out.println (pole1 [i]);
}
```

Takto se v Javě pracuje s dvojrozměrným polem:

```java
// deklarace s výchozími hodnotami

int matice1 [][] = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};

// univerzálnější deklarace

int matice2 [][];
matice2 = new int [3][3];
matice2[0] = new int [3];
matice2[0][0] = 1;
matice2[0][1] = 0;
matice2[0][2] = 0;
matice2[1] = new int [3];
matice2[1][0] = 0;
matice2[1][1] = 1;
matice2[1][2] = 0;
matice2[2] = new int [3];
matice2[2][0] = 0;
matice2[2][1] = 0;
matice2[2][2] = 1;

// výpis jednoho prvku

System.out.println (matice1 [0][2]);

// výpis celého pole

for (int r=0; r < matice1.length; r++) {
  for (int s=0; s < matice1[r].length; s++) {
    System.out.print(matice1[r][s] + " ");
  }
  System.out.println();
}
```
