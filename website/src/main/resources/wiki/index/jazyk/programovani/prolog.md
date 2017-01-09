## Jazyk Prolog

Programovací jazyk Prolog pochází z Francie a poprvé se objevil kolem roku 1972. Je založený na matematické disciplíně zvané **logika** a proto je velmi abstraktní - programátor se nemusí zabývat přesným výpočetním procesem, zapisuje pouze pravidla.

- **program** = množina axiomů
- **výpočet** = důkaz platnosti výrazu na základě uvedených axiomů

Interpretery Prologu jsou většinou konzolové aplikace. Programování probíhá následovně:

- vytvoření souboru s axiomy
- spuštění konzole Prologu
- načtení souboru příkazem *[soubor].*
- pokládání dotazů

### Syntaxe

Každý axiom končí *tečkou*. Každá proměnná začíná *velkým písmenem* (nebo podtržítkem). Existuje speciální proměnná *_*, která se při výpočtu nebere v potaz (prostě se "zahodí"). Axiomy, které mají platit současně, se oddělejí *čárkou* = AND. Axiomy, ze kterých má platit alespoň jeden, se oddělují *středníkem* = OR. Operátor **if** se zapisuje jako *:-*.

```prolog
man(clark).
rich(clark).
strong(clark).
idealman(X) :- man(X),rich(X),strong(X).
```

Položení dotazu:

```prolog
?- idealman(X).
```

### Seznamy

Další běžnou strukturou v jazyce Prolog jsou seznamy. Seznam je uspořádaná množina datových struktur. Seznam je uzavřen hranatými závorkami a jednotlivé prvky jsou odděleny čárkou. Prázdný seznam se zapisuje jako *[]*.

```prolog
[a,b,c]
[concert,dream theater,[john,peter,lucia,debora]]
```

Prolog dále umožňuje oddělit první prvek (*head*) a zbytek (*tail*) seznamu pomocí speciálního symbolu - svislítka (*|*). Rozdělíme-li například seznam *[prvni,druhy,treti]* jako *[H|T]*, bude *H* = *prvni* a *T* = *[druhy,treti]*.

### Reference

- přednášky Jeanne Stynes, CIT
- http://www.doc.gold.ac.uk/~mas02gw/prolog_tutorial/prologpages/
- http://en.wikibooks.org/wiki/Prolog
- http://www.swi-prolog.org/