## Turingův stroj

![Alan Turing (1951)](photo_alan_turing.jpg) {.right}

Turingův stroj je jednoduché abstraktní výpočetní zařízení, které se používá ke studiu **vypočitatelnosti** - tedy ke zjištění, které problémy s jeho použitím vyřešit lze a které ne. V roce 1937 jej definoval původem anglický filozof, matematik a kryptograf **Alan Mathison Turing**.

Motivací k jeho vytvoření se stal tzv. **Entscheidungsproblem** (rozhodovací problém), který zformuloval v roce 1900 matematik **David Hilbert**. V něm se ptal, zda existuje mechanický proces, kterým je možné rozhodnout o pravdivosti libovolného matematického výroku. Pomocí Turingova stroje bylo později dokázáno, že to možné není.

Ještě před sestrojením prvního počítače se Turing zajímal o to, co je to přesně ona "vypočitatelnost". Intuitivně tušil, že souvisí s existencí nějaké konečné sekvence akcí, která povede k vyřešení daného problému. Takovou sekvenci můžeme nazvat [algoritmem](wiki/algoritmus). Jedna z interpretací tzv. **Church-Turingovy teze** říká, že ke každému algoritmu lze sestrojit ekvivalentní Turingův stroj.

### Deterministický Turingův stroj

#### Formální definice

Mějme nekonečné jednorozměrné pole **buněk** (cells), které nazveme **páskou** (tape). Každá buňka má kapacitu pro uložení právě jednoho symbolu z páskové abecedy. Ze začátku jsou na celé pásce uloženy pouze **prázdné symboly**.

S páskou pracuje čtecí a zapisovací **hlava** (head), která se vždy nachází právě nad jednou buňkou, se kterou pracuje. Hlava je schopná z aktuální buňky symbol přečíst, přepsat jej symbolem jiným a pohybovat se o krokově o jednu buňku vlevo či vpravo.

Turingův stroj je uspořádaná sedmice € \{ Q,q_0,F,A,\epsilon,B,\delta \} €, kde € Q : |Q| < \infty € je konečná množina vnitřních stavů (states), € q_0 \in Q € je výchozí stav (initial state), € F \subseteq Q € je množina konečných stavů (finite states), € A : |A| < \infty € je konečná pásková abeceda (tape alphabet), € \epsilon : \epsilon \in A € je prázdný znak (blank symbol), € B : B = A \setminus \{ \epsilon \} € je konečná vstupní abeceda (input alphabet) a € \delta : (Q \setminus F) \times A \rightarrow Q \times A \times \{ L,R \} € je částečná přechodová funkce, kde L = posun hlavy vlevo, R = posun hlavy vpravo.

**Vysvětlení přechodové funkce:** Turingův stroj se nachází v určitém stavu (*Q*) a přečetl symbol z pásky (*A*). Symbol na pásce nahradí jiným symbolem (*A*), přejde do jiného stavu (*Q*) a hlava se pohne vlevo (*L*) či vpravo (*R*). Přechodová funkce se nazývá "částečná" proto, že nemusí být definovaná pro všechny možné kombinace stavů a vstupních symbolů. Konečné stavy nemají přechody vůbec definované.

#### Výpočetní proces

1. Turingův stroj se nachází ve výchozím stavu.
1. Je-li aktuální stav stavem koncovým, výpočet skončí.
1. Čtecí hlava přečte z pásky symbol, nad kterým se nachází.
1. Je-li v přechodové funkci definován přechod pro aktuální stav a přečtený symbol, provede se takto:
  1. Změní se vnitřní stav.
  1. Hlava na pásce může přepsat symbol, nad kterým se nachází.
  1. Hlava se pohne vlevo či vpravo.
1. Není-li v přechodové funkci definován přechod pro aktuální stav a přečtený symbol, výpočet skončí.
1. Proces se opakuje od bodu 2.

#### Analogie s počítačem

Podobně jako počítač, i Turingův stroj má omezenou vnitřní paměť (konečná množina vnitřních stavů). Na rozdíl od něj však disponuje nekonečně dlouhou páskou, kterou používá jako paměť pro mezivýpočty. Jeho chování je řízeno přechodovou funkcí, kterou je možné chápat jako analogii k procesoru, řízeným programem. Výpočetní proces je pak analogií k řadiči mikroprocesoru.

Instrukční sada Turingova stroje je následující:

- přečíst symbol
- přepsat symbol
- posunout čtecí hlavu o jednu buňku vlevo
- posunout čtecí hlavu o jednu buňku vpravo

### Reference

- http://www.seas.upenn.edu/~cit596/notes/dave/turing2.html
- http://plato.stanford.edu/entries/turing-machine/
- Peter Linz - An Introduction to Formal Languages and Automata