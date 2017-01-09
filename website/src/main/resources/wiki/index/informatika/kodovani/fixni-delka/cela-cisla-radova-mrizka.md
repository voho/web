## Kódování celých čísel v řádové mřížce

Zjednodušeně řečeno, v počítači pracuje s binárními čísly procesor. Ten má pevně danou a hlavně omezenou velikost registrů (velmi malá pracovní paměť určená hlavně pro operandy - pro čísla). Tím pádem je omezený i číselný rozsah, který je do každého z nich možné uložit. Abychom mohli toto přesně zdokumentovat, je třeba pro procesor definovat tzv. **binární řádovou mřížku**.

Binární řádová mřížka přesně popisuje **formát** čísel, se kterými dokáže daný procesor pracovat. Mezi její parametry patří:

* délka *l* - počet bitů mřížky, který určuje počet rozlišitelných hodnot
* jednotka *e* - nejmenší číslo, které LZE v mřížce uložit
* modul *Z* - nejmenší číslo, které již NELZE v mřížce uložit
* pozice desetinné čárky - může být pevná či plovoucí (v tomto článku se pracuje pouze s celými čísly, takže je desetinná čárka napevno vždy za posledním bitem vpravo)

Příklad binární mřížky s délkou 8 bitů:

| Řád || 7. | 6. | 5. | 4. | 3. | 2. | 1. | 0. ||
|---|---|---|---|---|---|---|---|---|---|---
| Ukázkové číslo || 0 | 1 | 1 | 0 | 1 | 0 | 0 | 1 | =105
| Jednotka || 0 | 0 | 0 | 0 | 0 | 0 | 0 | 1 | =1
| Modul | 1 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | =256

### Zápis čísel bez znaménka

Chceme-li zapsat kladné dekadické číslo v binární řádové mřížce, provedeme standardní převod mezi číselnými soustavami a vznikne nám posloupnost jedniček a nul. Pokud je tato posloupnost delší, než délka řádové mřížky, číslo v dané mřížce není zobrazitelné. Zobrazitelná čísla zapisujeme od nejnižšího řádu, zbytek vyplníme nulami.

| Binárně (délka mřížky = 4) | Dekadicky
|---|---
| 0000 | 0
| 0001 | 1
| 0010 | 2
| 0011 | 3
| 0100 | 4
| 0101 | 5
| 0110 | 6
| 0111 | 7
| 1000 | 8
| ... | ...

### Zápis čísel se znaménkem

Při zápisu záporných čísel je třeba do binární řádové mřížky nějakým způsobem přidat informaci o znaménku a umět z výsledného zápisu rychle zjistit absolutní hodnotu i znaménko zakódovaného čísla.

#### Přímé kódování

To první, co člověka napadne, je rozdělit mřížku na dvě podmřížky. První bude tvořena jedním bitem, který určí znaménko a zbylá část se použije k zápisu absolutní hodnoty. Tento způsob je velmi jednoduchý a intuitivní, ale ukrývá v sobě zradu v podobě záporné nuly a sníženého rozsahu. Také obvod pro sčítání dvou čísel v přímém kódu bude složitější, protože bude muset rozlišovat jednotlivé případy (+ +, + -, - +, - -).

| Binárně (délka mřížky = 1+4) | Dekadicky | Dekódované číslo
|---|---|---
| 0,0000 | +,0 | 0
| 0,0001 | +,1 | 1
| 0,0010 | +,2 | 2
| 0,0011 | +,3 | 3
| 0,... | +,x | x
| 1,0000 | -,0 | -0 (zbytečné)
| 1,0001 | -,1 | -1
| 1,0010 | -,2 | -2
| 1,0011 | -,3 | -3
| 1,... | -,x | -x

€€
\begin{align*} 
s &= \{\begin{matrix} 1; \; \; n < 0 \\ 
0; \; \; n \geq 0 \end{matrix} \\ 
A(n) &= s\|(|n|)_{2} \\ 
A^{-1}(s\|n) &= ((-1)^s \cdot n)_{10} 
\end{align*} 
€€

#### Aditivní kódování

Aditivní kód již nerozděluje mřížku na podmřížky, ale mění způsob interpretace dat. Hodnotu dekadického čísla před zápisem zvýšíme o pevně zvolenou hodnotu c (většinou polovina modulu, aby byl rozsah kladných i záporných čísel zhruba vyrovnaný). To nás zbaví záporných čísel a kladná čísla umíme již bez problémů do mřížky zapsat. Při dekódování čísla naopak hodnotu c odečteme. Aditivní kód se také někdy označuje jako kód s posunutou nulou. Zajímavé na něm je to, že umožňuje do mřížky zapsat více záporných čísel než kladných.

Příklad ukazuje aplikaci aditivního kódu na mřížku s délkou l=3, modulem Z=8 a hodnotou c=4.

| Binárně (délka mřížky = 3) | Dekadicky | Dekódované číslo
|---|---|---
| 000 | 0 | -4
| 001 | 1 | -3
| 010 | 2 | -2
| 011 | 3 | -1
| 100 | 4 | 0
| 101 | 5 | 1
| 110 | 6 | 2
| 111 | 7 | 3

€€
\begin{align*} 
c &= \frac{Z}{2} = \frac{2^l}{2} = 2^{l-1} \\ 
A(n) &= (n+c)_{2} \\ 
A^{-1}(n) &= (n-c)_{10} 
\end{align*} 
€€

#### Doplňkové kódování

Doplňkové kódování je podobné přímému kódování s tím rozdílem, že je znaménkový bit přímou a organickou součástí hodnoty čísla. Tím pádem neplýtváme rozsahem mřížky. Podobně jako aditivní kód i doplňkový kód mění interpretaci čísel, avšak jen pro čísla záporná. Jeho výhodou je okamžité zjištění znaménka, triviální kódování kladných čísel a rychlý převod čísel záporných.

Příklad ukazuje aplikaci doplňkového kódu na mřížku s délkou l=3 a modulem Z=16.

| Binárně (délka mřížky = 3) | Dekadicky | Dekódované číslo
|---|---|---
| 000 | 0 | 0
| 001 | 1 | 1
| 010 | 2 | 2
| 011 | 3 | 3
| 100 | 4 | -4
| 101 | 5 | -3
| 110 | 6 | -2
| 111 | 7 | -1

€€
\begin{align*} 
A(n) &= \{\begin{matrix} (n)_{2}; \; \; n \geq 0 \\ 
(n+M)_{2}; \; \; n < 0 \end{matrix} \\ 
A^{-1}(s\|n) &= \{\begin{matrix} (s\|n)_{10}; \; \; s = 0 \\
(s\|n-M)_{10}; \; \; s = 1 \end{matrix} \\ 
\end{align*}
€€

Existuje taková pomůcka, která může pomoci při zápisu záporného čísla v doplňkovém kódu:

1. Zapište do mřížky absolutní hodnotu záporného čísla ve dvojkové soustavě, zbytek vyplňte nulami.
1. Invertujte všechny bity.
1. Přičtěte jedničku a ignorujte případný přenos v nejvyšším řádu.

### Reference

- předmět X36SKD na FEL ČVUT