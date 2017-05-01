## Golombovy kódy

Golombovy kódy jsou rodinou parametrizovatelných kódů. Při konstrukci konkrétního kódu je zvolena konstanta *M* z oboru přirozených čísel (pro *M* = 1 je Golombův kód shodný s unárním kódem), která ovlivňuje délku kódových slov. Golombovy kódy využívají [kódování alfa](wiki/kodovani-alfa) a redukovaný binární kód.

Při kódování přirozeného čísla *N* se nejprve vypočítá **kvocient** *Q* a **zbytek** *R*:

€€ Q = \lfloor N / M \rfloor, R = N - Q \cdot M €€

Golombův kód čísla *N* je pak tvořen zřetězením **negace alfa kódu** čísla *Q + 1* a **redukovaným binárním kódem** čísla *R*.

€€ \mathrm{golomb}(N) = \overline{\alpha(Q + 1)} \dotplus \beta'' (R) €€

Redukovaný binární kód čísla pro Golombův kód s parametrem *M* se vytvoří takto:

1. Vypočítá se hodnota *C*, kde *C* je rozdíl nejnižší mocniny 2 větší než *M*.
1. Čísla menší než *C* se zakódují binárně bez prvního bitu.
1. Zbývající čísla (větší nebo rovna *C*) se zakódují binárně zvětšená o hodnotu *C*.

### Riceovy kódy

Riceovy kódy jsou podmožinou Golombových kódů, ve kterých je volitelná konstanta *M* nějakou mocninou dvojky. Jsou tedy vhodnější pro použití ve výpočetní technice založené na [bitech](wiki/bit).

### Reference

- předmět X36KOD na ČVUT FEL