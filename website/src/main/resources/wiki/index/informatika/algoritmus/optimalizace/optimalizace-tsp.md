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

!TODO!

#### Optimalizace

!TODO!

### Reference

- http://gebweb.net/optimap/
- http://www.math.uwaterloo.ca/tsp/world/