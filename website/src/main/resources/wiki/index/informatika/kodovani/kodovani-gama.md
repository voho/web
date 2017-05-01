## Gama kód

Gama kód je jednoduchý algoritmus pro kódování přirozených čísel. V tomto kódování se využívá [kódování alfa](wiki/kodovani-alfa), [kódování beta](wiki/kodovani-beta) a kódování beta s čárkou. Gama kód je permutací gama kódu s čárkou (viz níže).

Gama kód přirozeného čísla *N* se vytváří z těchto komponent:

- alfa kód délky beta kódu *N* (slovo *A*)
- beta s čárkou *N* (slovo *B*)

Slovo *A* je vždy o jeden symbol delší, než část *B*. Proto je možné "vložit" mezi jednotlivé symboly delší část všechny symboly kratšího slova tak, že liché symboly pochází ze slova *A*, zatímco všechny sudé symboly z části *B* (například ze slov **AAAA** a **BBB** vznikne slovo **ABABABA**).

Nechť je tedy zavedena funkce *F*, která toto "vnoření" kratšího řetězce do delšího provede. Gama kód je pak definován takto:

€€ 
\gamma (N) = F(\alpha (|\beta(N)|), \beta' (N))
€€

| Číslo | Slovo A | Slovo B | Kód
|---|---|---|---
| 1 | *1* | prázdný řetězec | 1
| 2 | *01* | 0 | *0*0*1*
| 3 | *01* | 1 | *0*1*1*
| 4 | *001* | 00 | *0*0*0*0*1*
| 5 | *001* | 01 | *0*0*0*1*1*
| 10 | *0001* | 010 | *0*0*0*1*0*0*1*
| 100 | *0000001* | 100100 | *0*1*0*0*0*0*0*1*0*0*0*0*1*

### Příklad

#### Kódování čísla 42

Beta kód (binární zápis) čísla 42 je **101010**. Délka tohoto řetězce je 6. Alfa kód čísla 6 je **000001**. Tím máme hotovou první část (slovo *A*). Nyní znovu vezmeme beta kód čísla 42 a odebereme z něj první jedničku, čímž získáme jeho beta kód s čárkou, což je **01010**. Toto je druhá část (slovo *B*). Nyní "vnoříme" kratší slovo (slovo *B*) do slova delšího (slovo *A*). Získáme tak výsledný gama kód: *00010001001*.

### Gama kód s čárkou

Gama kód s čárkou přirozeného čísla *N* se vytváří ze stejných komponent jako gama kód:

- alfa kód délky beta kódu *N* (slovo *A*)
- beta s čárkou *N* (slovo *B*)

Namísto funkce *F*, která jednotlivá slova "vložila" do sebe, je použito obyčejné zřetězení:

€€ 
\gamma' (n) = \alpha (|\beta(n)|) \dotplus \beta' (n)
€€

| Číslo | Slovo A | Slovo B | Kód
|---|---|---|---
| 1 | *1* | prázdný řetězec | 1
| 2 | *01* | 0 | 010
| 3 | *01* | 1 | 011
| 4 | *001* | 00 | 00100
| 5 | *001* | 01 | 00101
| 10 | *0001* | 010 | 0001010
| 100 | *0000001* | 100100 | 0000001100100

#### Příklad

##### Kódování čísla 42

Beta kód (binární zápis) čísla 42 je **101010**. Délka tohoto řetězce je 6. Alfa kód čísla 6 je **000001**. Tím máme hotovou první část (slovo *A*). Nyní znovu vezmeme beta kód čísla 42 a odebereme z něj první jedničku, čímž získáme jeho beta kód s čárkou, což je **01010**. Toto je druhá část (slovo *B*). Nyní už jen obě části zřetězíme (000001 a 01010) a získáme výsledný gama kód s čárkou: *00000101010*.

### Reference

- předmět X36KOD na ČVUT