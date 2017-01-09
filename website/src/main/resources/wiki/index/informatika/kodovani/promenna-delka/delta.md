## Delta kód

Delta kód je jednoduchý algoritmus pro kódování přirozených čísel. V tomto kódování se využívá gama kód, beta kód a beta kód s čárkou. Delta kód je permutací delta kódu s čárkou (viz níže). Funguje podobně jako gama kód s čárkou, ale místo kódování alfa používá kódování gama.

Delta kód přirozeného čísla *N* je zřetězením těchto komponent:

- gama kód délky beta kódu *N* (slovo *A*)
- beta s čárkou *N* (slovo *B*)

€€
\delta (N) = \gamma (|\beta(N)|) \dotplus \beta' (N)
€€

| Číslo | Kód
|---|---
| 1 | 1
| 2 | 0010
| 3 | 0011
| 4 | 01100
| 5 | 01101
| 10 | 00001010
| 100 | 01011100100

### Příklad

#### Kódování čísla 42

Beta kód (binární zápis) čísla 42 je **101010**. Délka tohoto řetězce je 6. Gama kód čísla 6 je **00110**. Tím máme hotovou první část (slovo *A*). Nyní znovu vezmeme beta kód čísla 42 a odebereme z něj první jedničku, čímž získáme jeho beta kód s čárkou, což je **01010**. Toto je druhá část (slovo *B*). Nyní obě části zřetězíme a získáme výsledný delta kód: *0011001010*.

### Delta kód s čárkou

Delta kód s čárkou přirozeného čísla *N* se vytváří podobně jako delta kód, ale místo gama kódu je použit gama kód s čárkou. Delta kód s čárkou přirozeného čísla *N* je zřetězením těchto komponent:

- gama kód s čárkou délky beta kódu *N* (slovo *A*)
- beta s čárkou *N* (slovo *B*)

€€ 
\delta' (n) = \gamma' (|\beta(n)|) \dotplus \beta' (n)
€€

### Reference

- předmět X36KOD na ČVUT