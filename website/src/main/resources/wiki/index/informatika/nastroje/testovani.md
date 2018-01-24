## Testování

### Testovací pyramida

Testovací pyramida je mnemotechnická pomůcka, která nám může pomoci při plánování a vytváření testů.
Vertikální osa vyznačuje úroveň abstrakce, horizontální osa množství testů.
Základnu pyramidy tvoří **jednotkové testy**. Ze všech testů by měly pokrývat co nejvíce kódu a pokud možno i všechny případy užití dané komponenty.
Jak budeme v pyramidě postupovat výše, množství klesů by mělo klesat, protože je čím dál tím obtížnější pokrýt všechny případy užití bez duplikace kódu.
Důvodem je obtížnější navození výchozího stavu před testem a verifikace 
Na vrcholu pyramidy mohou být například **uživatelské testy**.

![Testovací pyramida](http://james-willett.com/wp-content/uploads/2016/05/TestPyramid.png)

### Anatomie testu

Každý test se skládá z těchto kroků:

**Inicializace** (Setup, Arrange)
: Tento krok by měl uvést všechny komponenty do stavu, ve kterém chceme testování operace provést.

**Akce** (Exercise / Act)
: Spustíme testovanou operaci.

**Verifikace** (Verify, Assert)
: Tento krok ověří, že bylo dosaženo požadovaného výsledku. To znamená kontrolu výsledku, stavu komponent a provedených interakcí.

**Terminace** (Teardown)
: Měl by uvést všechny komponenty do stavu před spuštěním testu.

### Druhy testů

#### Blackbox testování

Toto testování probíhá bez znalosti vnitřní struktury testovaného systému.
Testuje se pouze použitím vnějšího rozhraní systému.
Příkladem může být například uživatelské testování webu.

- výhody
    - efektivní pro velké systémy
    - není vyžadován přístup ke zdrojovému kódu
    - oddělení perspektivy autora a uživatele
- nevýhody
    - hrozí omezené pokrytí funkcionality 

#### Whitebox testování

Toto testování probíhá se znalostí vnitřní struktury testovaného systému.

- výhody
    - efektivní pro vyhledávání chyb
    - dokáže odhalit i skryté chyby (takové, ke kterým nemůže dojít používáním systému uživatelem)
    - umožňuje pokrýt větší část funkcionality
- nevýhody
    - neodhalí chybějící součásti
    - vyžaduje přístup ke zdrojovému kódu a jeho porozumění

#### Jednotkové testy (unit testing)

!TODO!

Tipy:

- každý jednotkový test by měl testovat pouze jednu operace jedné jednotky
- test by neměl ověřovat stav a interakci příliš mnoha nesouvisejících součástí (to by zvyšovalo závislost testu na konkrétní implementaci) 
- testy by se neměly vzájemně překrývat v tom, co testují

#### Komponentové testy (component testing)

!TODO!

#### Integrační testy (integration testing)

!TODO!

#### Bezpečnostní testy (security testing)

!TODO!

#### Funkční testy (functional testing)

!TODO!

### Reference

- https://technologyconversations.com/2013/12/11/black-box-vs-white-box-testing/
- http://blog.stevensanderson.com/2009/08/24/writing-great-unit-tests-best-and-worst-practises/
- https://www.agile-code.com/blog/the-anatomy-of-a-unit-test/
- https://dzone.com/articles/the-anatomy-of-good-unit-testing-with-examples-in