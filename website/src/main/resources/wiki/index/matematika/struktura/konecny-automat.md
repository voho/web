## Konečný automat

Konečnými automaty modelujeme chování jednoduchých objektů, které se mohou nacházet v různých stavech a přecházet mezi nimi na základě vstupu. Používá se při studiu vyčíslitelnosti a zpracování formálních jazyků. Známe konečné automaty **deterministické** a **nedeterministické**. Při konstrukci hardware se nejčastěji používají odvozené automaty typu [Mealy](wiki/mealy) a [Moore](wiki/moore).

Funkce konečného automatu by se dala definovat takto:

1. Automat se nachází v počátečním stavu.
1. Automatu je předložen konečný vstupní řetězec postavený nad jeho vstupní abecedou.
1. Automat přečte a odebere symbol ze vstupního řetězce.
1. Automat provede přechod na základě tohoto symbolu a aktuálního vnitřního stavu (je-li definován).
1. Body 2-4 se opakují tak dlouho, dokud není celý vstupní řetězec přečten.

### Deterministický konečný automat

Deterministický konečný automat je charakteristický tím, že se vždy nachází právě v jednom ze svých vnitřních stavů.

#### Formální definice

Deterministický konečný automat je uspořádaná pětice € (Q, Q_0, F, X, \delta) €, kde € Q : |Q| < \infty € je konečná množina vnitřních stavů, € Q_0 : Q_0 \in Q € je počáteční stav, € F : F \subseteq Q € je množina koncových stavů, € X : |X| < \infty € je konečná vstupní abeceda (množina vstupních symbolů) a € \delta : Q \times X \to Q € je přechodová funkce.

### Nedeterministický konečný automat

Nedeterministický konečný automat je charakteristický tím, že se může v jednom okamžiku nacházet v celé množině svých vnitřních stavů (tedy více než v jednom). Nedeterministické konečné automaty lze převést na deterministické, avšak počet stavů se zvýší.

#### Formální definice

Nedeterministický konečný automat je uspořádaná pětice € (Q, Q_0, F, X, \delta) €, kde € Q : |Q| < \infty € je konečná množina vnitřních stavů, € Q_0  : Q_0 \subseteq Q \land Q_0 \neq \varnothing € je počáteční stav, € F : F \subseteq Q € je množina koncových stavů, € X : |X| < \infty € je konečná vstupní abeceda (množina vstupních symbolů) a € \delta : Q \times X \to \mathcal{P}(Q) € je přechodová funkce.

Poznámka: € \mathcal{P}(Q) € je **potenční množina** (množina všech podmnožin) *Q*.

Existuje i varianta s tzv. **epsilon-přechody**, kde *epsilon* znamená "prázdný vstup" a v praxi to znamená, že automat provádí přechody neustále, tedy nezávisle na čtení symbolů ze vstupu. Formální rozdíl je v definičním oboru přechodové funkce, jejíž signatura se změní na € \delta : Q \times (X \cup \epsilon) \to \mathcal{P}(Q) €.

##### Přijetí / nepřijetí vstupního řetězce

Pokud se automat po přečtení celého vstupního řetězce nachází v jednom z **konečných stavů**, byl vstupní řetězec přijat. V ostatních případech přijat nebyl. Množina všech řetězců, které automat přijme, tvoří **regulární jazyk** nad jeho vstupní abecedou.

##### Konfigurace automatu

Konfigurace automatu se skládá z **aktuálního stavu automatu** a **zbylé části vstupního řetězce**, kterou automat dosud nepřečetl. Konfigurace nám umožňuje předvídat budoucí chování deterministického konečného automatu.

### Reference

- předmět X36TIN na FEL ČVUT