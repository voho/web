## Lambda kalkulus

Lambda kalkulus je **formální systém** pro deklaraci **funkcí** s pravidly pro jejich **vyhodnocování**. Jeho první verze pochází ze třicátých let 20. století. Popsal jej **Alonzo Church**, který se snažil vytvořit nový funkcionální základ pro [matematiku](wiki/matematika). K tomuto účelu sice jeho systém použit zatím nebyl, ale našel využití jinde.

Lambda kalkulus lze chápat jako jednoduchý, univerzální, netypovaný a striktně formální programovací jazyk. Je výpočetně ekvivalentní s [Turingovým strojem](wiki/turinguv-stroj). V šedesátých letech 20. století se začal používat v [informatice](wiki/informatika), především pro studium **vyčíslitelnosti** a **formálních jazyků**. Lambda kalkulus se také stal inspirací a teoretickým základem mnoha funkcionálních programovacích jazyků. Je totiž výsledným programům a lidskému myšlení bližší, než klasické výpočetní modely (např. Turingův stroj), které příliš připomínají hardware.

### Syntaxe

Základní stavební jednotkou lambda kalkulu je tzv. **lambda výraz**. Množinu lambda výrazů lze chápat jako soubor pravidel pro výpočet.

Formálně lze každý **lambda výraz** vyjádřit v Backus-Naurově formě takto:

```bnf
<výraz> ::= <proměnná>
<výraz> ::= ( λ <identifikátor> . <výraz> )
<výraz> ::= ( <výraz> <výraz> )
```

Závorky nejsou povinné, slouží pouze ke zvýšení čitelnosti. Lambda výrazy se vyhodnocují jako **asociativní zleva**.

€€ (f\;(a\;b)) \sim_{\lambda} ((f\;a)\;b) €€

Funkce s více parametry sice v lambda kalkulu neexistují, ale každou takovou funkci lze převést na funkci s parametrem jedním tak, že vnější funkce vrací vnitřní funkci, které předá parametr. Tento převodní proces se nazývá **Currying** a používá se pro něj následující syntaktická zkratka:

€€ (\lambda\;x_1\;x_2\;x_3\;x_{\ldots}\;.\;E) \sim_{\lambda} (\lambda\;x_1\;.\;(\lambda\;x_2\;.\;(\lambda\;x_3\;.\;(\lambda\;x_{\ldots}\;.\;E)))) €€

Lambda výrazy jsou striktně **prefixové**, jak ukazují následující příklady:

€€
\begin{align*}
x+3 &\sim_{\lambda} (+\;x\;3) \\
x^2 &\sim_{\lambda} (*\;x\;x) \\
\sin (x) &\sim_{\lambda} (\sin\;x) \\
\sin (x) - 1 &\sim_{\lambda} (-\;(\sin\;x)\;1) \\
\end{align*}
€€

#### Pojmenování funkcí

Přestože v čistém lambda kalkulu nemají funkce žádný název, lze pro vyšší přehlednost a zkrácení zápisu použít mechanizmus podobný matematické substituci. Pro vkládání zkrácených názvů funkci do lambda výrazů pak platí stejná syntaktická pravidla jako pro proměnné.

€€ \mathrm{2X} ::= (\lambda\;x\;.\;+\;x\;x) €€

### Sémantika

Lambda výraz obsahující symbol *lambda* představuje funkci s parametrem a tělem.

€€ f(x) = 1-x \sim_{\lambda} (\lambda\;x\;.\;(-\;1\;x)) €€

Výše uvedený zápis představuje funkci s jedním parametrem *x* a tělem *1-x* (v prefixovém zápisu).

#### Volné a vázané proměnné

Rozlišují se dvě kategorie proměnných: **volné** a **vázané**. 

- **volné** jsou takové proměnné, které před sebou nemají symbol *lambda* a své jméno
- **vázané** jsou takové proměnné, které před sebou mají symbol *lambda* a své jméno

#### Operace s výrazy

- *alfa* konverze
- *beta* redukce
- *theta* konverze

#### Vyjádření základních konceptů

##### Logické hodnoty

€€
\begin{align*}
\mathrm{true} &::= (\lambda\;xy\;.\;x) \\
\mathrm{false} &::= (\lambda\;xy\;.\;y) \\
\end{align*}
€€

```text
true 1 0 = 1
false 1 0 = 0
```

##### Logické operátory

€€
\begin{align*}
\mathrm{not} &::= (a\;\mathrm{false}\;\mathrm{true}) \\
\mathrm{and} &::= (a\;b\;\mathrm{false}) \\
\mathrm{or} &::= (a\;\mathrm{true}\;b) \\
\mathrm{xor} &::= a\;(b\;\mathrm{false}\;\mathrm{true})\;b \\
\end{align*}
€€

##### Uspořádaná dvojice

K definici uspořádaných dvojic lze využít výše definovaných logických hodnot.

€€
\begin{align*}
\mathrm{pair} &::= (\lambda\;x\;y\;f\;.\;f\;x\;y) \\
\mathrm{first} &::= (\lambda\;p\;.\;p\;\mathrm{true}) \\
\mathrm{second} &::= (\lambda\;p\;.\;p\;\mathrm{false}) \\
\end{align*}
€€

```text
first pair 1 2 = 1
second pair 1 2 = 2
```

### Reference

- předmět X36OBP na FEL ČVUT
- http://worrydream.com/AlligatorEggs/
- http://safalra.com/science/lambda-calculus/
- http://homepages.nyu.edu/~cb125/Lambda/
- http://thyer.name/lambda-animator/