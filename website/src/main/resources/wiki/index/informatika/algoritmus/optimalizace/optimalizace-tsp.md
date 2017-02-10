## Obchodní cestující (Travelling Salesman)

Problém je pojmenovaný podle obchodníků, kteří jezdili od města k městu a snažili se tam prodat co nejvíce vysavačů, psacích strojů, nebo třeba encyklopedií. Proto se snažili svou cestu vybranými městy naplánovat tak, aby je všechna projeli s minimálními náklady, tedy po co nejkratší trase.

Problém obchodního cestujícího je NP-těžký optimalizační problém, jehož zadání neformálně zní například takto: je dáno několik měst a vzdálenosti mezi nimi. Úkolem je najít takovou cestu, která navštíví každé město právě jednou. Formálně se jedná o nalezení nejméně ohodnocené [hamiltonovské kružnice](wiki/graf) v úplném ohodnocením grafu. Řešením úlohy je posloupnost měst, která udává, v jakém pořadí se mají města projet.

Problém je složitý především proto, že jeho složitost dramaticky narůstá s rostoucím počtem měst.

![nejkratší cesta mezi hlavními městy v USA](http://support.sas.com/documentation/cdl/en/ornoaug/65289/HTML/default/images/map002g.png)

Praktických využití v plánování či logistice je mnoho, například:

- v jakém pořadí vrtat díry skrz díl, aby výroba dílu zabrala co nejméně času (díry jsou "města")
- v jakém pořadí zkoumat hvězdy na obloze, aby se posunem a nastavováním teleskopu strávilo co nejméně času (hvězdy jsou "města")
- v jakém pořadí dodat poštovní balíky adresátům (adresáti jsou "města")

### Řešení

#### Hrubá síla

Algoritmy používající "hrubou sílu" k nalezení optimálního řešení jsou principiálně velmi jednoduché, ale často jsou použitelné jen pro malé instance problému. Algoritmus zkrátka vygeneruje všechny možné cesty (tj. permutace měst) a vybere z nich takovou, jejíž ohodnocení je nejmenší. Tyto algoritmy vždy najdou optimální řešení, ale kvůli dramaticky narůstající složitosti jsou použitelné jen pokud je počet měst v řádu desítek až stovek.

#### Heuristika

Pokud z různých důvodů nepotřebujeme najít optimální řešení, ale stačí nám nějaké "dostatečně dobré", lze použít některé heuristické metody, které jsou šak o to rychlejší.

Nejbližší soused
: Začíná se v náhodně zvoleném městě. Potom se opakovaně volí takové nenavštívené město, které je nejblíž k tomu aktuálnímu. Po navštívení všech měst se cesta uzavře návratem k prvnímu městu.

Hladový algoritmus
: Nejprve se všechny dvojice měst seřadí podle vzdálenosti od nejkratší k nejdelší. Tyto hrany se potom postupně přidávají k výsledné cestě, přičemž se přeskakují ty, které by vytvořily "cyklus" nebo "křižovatku" (v [grafové terminologii](wiki/graf) tedy kružnici nebo uzel se stupněm vyšším než dva).

Vkládání nejbližšího
: Cesta je zpočátku tvořena pouze hranou mezi dvěma uzly, které k sobě mají nejblíž. V každém dalším kroce se vybere takové nenavštívené město, které má nejmenší vzdálenost k libovolnému navštívenému městu. Toto město se potom vloží mezi dvě již navštívená města vybraná tak, aby celková délka nově vzniklé cesty byla co nejmenší.

Vkládání nejvzdálenějšího
: Podobný postup jako u "vkládání nejbližšího", ale pro maximální vzdálenosti. Cesta je zpočátku tvořena pouze hranou mezi dvěma uzly, které k sobě mají nejdál. V každém dalším kroce se vybere takové nenavštívené město, které má největší vzdálenost k libovolnému navštívenému městu. Toto město se potom vloží mezi dvě již navštívená města vybraná tak, aby celková délka nově vzniklé cesty byla co nejmenší (podobně jako u vkládání nejbližšího).

### Reference

- http://gebweb.net/optimap/
- http://www.math.uwaterloo.ca/tsp/world/
- http://faculty.washington.edu/jtenenbg/courses/342/f08/sessions/tsp.html
- https://web.tuke.sk/fei-cit/butka/hop/htsp.pdf
