## Formální jazyk

Před definicí formálního jazyka jako takového je třeba definovat některé potřebné pojmy.

Nechť je definovaná libovolná konečná [množina](wiki/mnozina) €\Sigma€. Tuto množinu pojmenujeme **abeceda** a její prvky budeme nazývat **symboly** (znaky, písmena). Abecedou mohou být například znaky latinky (A až Z), dekadické číslice (0 až 9), binární číslice (0, 1), grafické symboly (Braillovo písmo) nebo vizuální symboly (barvy semaforu).

Každá konečná posloupnost €w€ (uspořádaná množina) těchto symbolů z abecedy €\Sigma€ se nazývá **slovo** (řetězec) nad abecedou €\Sigma€. Posloupnost symbolů o nulové délce (prázdné slovo) je také validní a značí se jako €\epsilon€. Množina všech slov nad touto abecedou včetně prázdného slova se označuje jako €\Sigma^*€.

U slov lze měřit délku, případně je spojovat a jejich řetězením vytvářet nová slova. Operace řetězení je asociativní (nezáleží na pořadí, v jakém slova spojujeme).

A konečně, formální jazyk €L \subseteq \Sigma^*€ je podmnožinou všech slov nad abecedou €\Sigma€, vytvořenou dle určitých pravidel. Formální jazyk tedy vždy musí obsahovat nějaký rozhodovací mechanismus, který o každém slově nad danou abecedou jednoznačně prohlásí, zda do jazyka patří, či nikoliv. 

Tímto rozhodovacím mechanismem může být například [konečný automat](wiki/konecny-automat), [Turingův stroj](wiki/turinguv-stroj), [formální gramatika](wiki/formalni-gramatika), prostý výčet přijatelných slov, a tak dále. Tyto mechanismy lze mezi sebou různě převádět a pro každé použití je vhodnější jiný z nich.

### Příklady

#### Binární řetězce liché délky

Nechť je definována abeceda se symboly *0*, *1*. Jazyk €L€ je definován jako množina všech slov s délkou 4. 

- € A=\\{0,1\\}, L=\\{w \; | \; w \in A^*, |w| = 4\\} €
- příklady slov patřících do jazyka: 0000, 0011, 1000
- příklady slov mimo jazyk: 00, 01110, 0110011001100110

#### Slova s určitou přeponou a příponou

Nechť je definována abeceda s anglickými znaky *A* až *Z*. Jazyk €L€ je definován jako množina všech slov, které začínají předponou *AKA* a končí příponou *LEL*.

- příklady slov patřících do jazyka: AKABEFCLEL, AKALEL, AKAEEFLEL
- příklady slov mimo jazyk: AKA, ALEL, HELLO

### Notace

- € \\{a\\}^* = \\{\epsilon,a,aa,aaa,\ldots\\} € = množina všech podmnožin vytvořených opakováním určitého symbolu
- € \\{a\\}^+ = \\{a\\}^* \setminus \\{\epsilon\\} € = množina všech podmnožin vytvořených alespoň jedním opakováním určitého symbolu
- € \\{0,1\\}^* = \\{\epsilon,0,1,00,01,10,11,000,001,010,\ldots\\} € = množina všech slov nad danou abecedou 0, 1
