## Řadící algoritmus

Řazení dat podle určitého kritéria je jedním z algoritmicky nejčastěji řešených problémů. Odhaduje se, že řazením stráví programy až čtvrtinu času. Řazení se totiž objevuje jako častá podúloha v mnoha dalších algoritmech. Proto byla problematika řazení zkoumána a řešena mnoha významnými informatiky.

Matematicky bylo dokázáno, že asymptotická časová složitost žádného řadícího algoritmu založeného na porovnávání dvou prvků nemůže být lepší než lineárně-logaritmická v závislosti na počtu řazených prvků. Výpočet si totiž lze představit jako průchod binárním stromem, který má € n! € listů. Každý jeden z nich představuje permutaci řazených prvků. Výška tohoto stromu je € \log{n!} €, což je asymptoticky rovno € n \cdot \log{n} €. Cesta z kořene do listu, která odpovídá seřazení vstupní posloupnosti, nemůže být nikdy kratší. 

### Formální definice

Nechť € R_1 € až € R_n € jsou prvky určené k seřazení. Aby bylo množné prvky jednoznačně seřadit, musí existovat injektivní zobrazení *f*, které každému prvku přiřadí tzv. **klíč**. Množina všech klíčů generovaných tímto zobrazením musí být **uspořádaná**. To znamená, že pro každou dvojici klíčů musí být možné jednoznačně určit, který z nich je **větší**, který **menší**, či zda se klíče sobě navzájem **rovnají**. 

Nechť € K_i € je klíč prvku € R_i €. Seřadit prvky € R_1 € až € R_n € znamená nalézt takovou jejich posloupnost, pro kterou platí:

€€ (R_{i_1}, \ldots, R_{i_n}) | K_{i_1} \leq \ldots \leq K_{i_n} €€

### Získávání klíče

Množiny přirozených, celých a reálných čísel jsou uspořádané a výpočetní stroje s nimi dokáží (ač s omezenou přesností) efektivně pracovat. Proto se čísla používají jako klíče nejčastěji. Řadí-li se například dle data, lze je převést na přirozená čísla tímto způsobem:

€€ 1.4.2008 \sim 20080401, 30.12.1987 \sim 19871230, \ldots €€

Znaky lze považovat za přirozená čísla a jejich řetězce za posloupnosti přirozených čísel. Nejjednodušší lexikografické algoritmy řadí řetězce tak, že postupují znak po znaku. Jiné algoritmy mohou nejprve řetězec převést na jiný řetězec, ve kterém jsou určité znaky či skupiny znaků nahrazeny jinými. Některé algoritmy mohou dokonce celý řetězec převést na jedno číslo a to použit jako klíč. Klíče se používají i proto, že jsou zpravidla menší než řazené prvky. 

€€ \text{B} \sim 2, \text{O} \sim 15, \text{BOB} \sim 21502 \ldots €€

### Vlastnosti řadících algoritmů

- **časová složitost** - přibližný řád růstu doby výpočtu v závislosti na počtu řazených prvků
- **paměťová složitost** - řád velikosti potřebné paměti v závislosti na počtu řazených prvků
- **stabilita** - stabilní řadící algoritmus zachovává relativní pořadí prvků se stejným klíčem, tedy nepřesouvá prvky, které nemusí
- **přirozenost** - rychlost přirozeného řadícího algoritmu roste s rostoucím podílem seřazených prvků na vstupu

### Vícenásobné řazení

Jako vícenásobné řazení se označuje opakované řazení jedné posloupnosti použitím různých zobrazení prvků na klíče. Příkladem je například řazení osob dle příjmení a poté dle jména. Použitý algoritmus musí být **stabilní**, aby řazení dle jména nenarušilo již existující pořadí osob dle příjmení. 