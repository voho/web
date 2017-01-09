## Eukleidův algoritmus

Eukleidův algoritmus je algoritmus určený primárně k nalezení největšího společného dělitele a je pokládán za jeden z nejstarších algoritmů vůbec. Vstupem jsou zpravidla dvě celá čísla *a*, *b*, výstupem jejich nejvyšší společný dělitel. Eukleidův algoritmus lze zobecnit pro výpočet v jakémkoliv **okruhu**.

Pseudokód nerekurzivní varianty:

```text
funkce EUKLEID (číslo A, číslo B) vrací číslo
{
 opakuj dokud (B není nulové)
   číslo R := zbytek po dělení čísla A číslem B;
   A := B;
   B := R;
 vrať A;
}
```

Pseudokód rekurzivní varianty:

```text
funkce EUKLEID_REKURZE (číslo A, číslo B) vrací číslo
{
 pokud (B je nulové)
   vrať A;
 jinak
   číslo R := zbytek po dělení čísla A číslem B;
   vrať EUKLEID_REKURZE (B, R);
}
```

### Příklady

#### Běh pro čísla 427, 133

| A | B | Q | R | Výraz
|---|---|---|---|---
| 427 | 133 | 3 | 28 | 427 = 3 * 133 + 28
| 133 | 28 | 4 | 21 | 133 = 4 * 28 + 21
| 28 | 21 | 1 | 7 | 28 = 1 * 21 + 7
| 21 | 7 | 3 | 0 | 21 = 3 * 7 + 0

Nejvyšším společným dělitelem je číslo **7**.

#### Běh pro čísla 29311, 231

| A | B | Q | R | Výraz
|---|---|---|---|---
| 29311 | 231 | 126 | 205 | 29311 = 231 * 126 + 205
| 231 | 205 | 1 | 26 | 231 = 205 * 1 + 26
| 205 | 26 | 7 | 23 | 205 = 26 * 7 + 23
| 26 | 23 | 1 | 3 | 26 = 23 * 1 + 3
| 23 | 3 | 7 | 2 | 23 = 3 * 7 + 2
| 3 | 2 | 1 | 1 | 3 = 2 * 1 + 1
| 2 | 1 | 2 | 0 | 2 = 1 * 2 + 0

Nejvyšším společným dělitelem je číslo **1**.

#### Běh pro čísla 552314, 9332

| A | B | Q | R | Výraz
|---|---|---|---|---
| 552314 | 9332 | 59 | 1726 | 552314 = 9332 * 59 + 1726
| 9332 | 1726 | 5 | 702 | 9332 = 1726 * 5 + 702
| 1726 | 702 | 2 | 322 | 1726 = 702 * 2 + 322
| 702 | 322 | 2 | 58 | 702 = 322 * 2 + 58
| 322 | 58 | 5 | 32 | 322 = 58 * 5 + 32
| 58 | 32 | 1 | 26 | 58 = 32 * 1 + 26
| 32 | 26 | 1 | 6 | 32 = 26 * 1 + 6
| 26 | 6 | 4 | 2 | 26 = 6 * 4 + 2
| 6 | 2 | 3 | 0 | 6 = 2 * 3 + 0

Nejvyšším společným dělitelem je číslo **2**.

### Implementace (Java)

#### Nerekurzivní varianta

```java
public static int eukleid (final int a, final int b) {
 int a_temp = a;
 int b_temp = b;

 while (b_temp != 0) {
   final int r = a_temp % b_temp;
   a_temp = b_temp;
   b_temp = r;
 }

 return a_temp;
}
```

#### Rekurzivní varianta

```java
public static int eukleidRecursive (final int a, final int b) {
 if (b == 0) {
   return a;
 } else {
   return eukleidRecursive (b, a % b);
 }
}
```

### Zpětný chod a Bezoutova rovnost

Zpětným dosazováním do řádků získaných Eukleidovým algoritmem je možné získat tzv. **Bezoutovu rovnost**, ze které se dá snadno získat např. inverze prvku *a* v okruhu modulo *b*. Tento postup se také označuje jako **zpětný chod** Eukleidova algoritmu. V každém kroku se zbytky v aktuálním řádku rozepíší pomocí vyšších řádků. Poté se výrazy roznásobí a vytknou se čísla *a*, *b*. Koeficient u čísla *a* se označí jako € \alpha €, koeficient u čísla *b* jako € \beta €.

| Krok | Výpočet
|---|---
| 1 | € 7 = 1 \cdot 28 - 21 €
| 2 | € 7 = 1 \cdot (427 - 3 \cdot 133) - (133 - 4 \cdot 28) €
| 3 | € 7 = 1 \cdot (427 - 3 \cdot 133) - (133 - 4 \cdot (427 - 3 \cdot 133)) €
| 4 (po vytknutí) | € 7 = 5 \cdot 427 - 16 \cdot 133 €

Výsledná čísla se označí:

€€ \operatorname{gcd} (427,133) = 7, \alpha = 5, \beta = -16 €€

Hodnoty se dosadí do obecné Bezoutovy rovnosti:

€€ \operatorname{gcd} (a,b) = \alpha \cdot a + \beta \cdot b €€

€€ 7 = 5 \cdot 427 - 16 \cdot 133 €€

Zápis tohoto výpočtu se stává při větším rozsahu nepřehledným, proto se někdy používá tzv. **rozšířený Eukleidův algoritmus**, který se zapisuje do tabulky a koeficienty Bezoutovy rovnosti počítá postupně.

### Reference

- předmět X01DML na FEL ČVUT
- předmět X01AVT na FEL ČVUT