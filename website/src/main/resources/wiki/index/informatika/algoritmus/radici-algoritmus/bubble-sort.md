## Bubble Sort (bublinkové řazení)

Bubble sort je jednoduchý řadící algoritmus s asymptotickou složitostí € O(n^2) €. Traduje se, že kdysi vznikl proto, že byly čtecí hlavy páskových mechanik pomalé a bylo tedy žádoucí, aby se hlava pohybovala po pásce po co možná nejkratších možných trajektoriích.

Algoritmus prochází pole a najde-li dva prvky ve špatném pořadí, prohodí je. To opakuje tak dlouho, dokud není pole seřazeno. Po skončení každé *n*-té iterace vnějšího cyklu je správně seřazeno *n* prvků na konci pole. Algoritmus bubble sort je **přirozený** a **stabilní**.

### Implementace (Java)

```java
/**
* Implementace algoritmu bubble sort.
* @param input vstupní pole
* @author Vojtěch Hordějčuk
*/
public static <T extends Comparable<? super T>> void bubbleSort(final T[] input)
{
 // průchod skončí na předposledním prvku (index 'i - 2')
 // (prvek se vždy porovnává se svým následníkem)
 
 for (int i = 0; i < input.length - 1; i++)
 {
   // už jsme seřadili 'i' prvků
   // (na konci pole je tedy nemusíme kontrolovat)
   
   for (int j = 0; j < input.length - 1 - i; j++)
   {
     // prohoď sousední prvky, pokud jsou ve špatném pořadí
     
     if (input[j].compareTo(input[j + 1]) == 1)
     {
       final T temp = input[j];
       input[j] = input[j + 1];
       input[j + 1] = temp;
     }
   }
 }
}
```

### Ukázkový běh

| Pole | Operace
|---|---
| 5 2 3 9 1 | Toto pole chceme seřadit bubble sortem.
| (2 5) 3 9 1 | Čísla 5, 2 jsou ve špatném pořadí, prohodit.
| 2 (3 5) 9 1 | Čísla 5, 3 jsou ve špatném pořadí, prohodit.
| 2 3 (5 9) 1 | Čísla 5, 9 jsou ve správném pořadí.
| 2 3 5 (1 9) | Čísla 9, 1 jsou ve špatném pořadí, prohodit.
| 2 3 5 1 **9** | Na konci máme správně seřazen jeden prvek.
| (2 3) 5 1 **9** | Čísla 2, 3 jsou ve správném pořadí.
| 2 (3 5) 1 **9** | Čísla 3, 5 jsou ve správném pořadí.
| 2 3 (1 5) **9** | Čísla 5, 1 jsou ve špatném pořadí, prohodit.
| 2 3 1 **5 9** | Na konci máme správně seřazeny dva prvky.
| (2 3) 1 **5 9** | Čísla 2, 3 jsou ve správném pořadí.
| 2 (1 3) **5 9** | Čísla 3, 1 jsou ve špatném pořadí, prohodit.
| 2 1 **3 5 9** | Na konci máme správně seřazeny tři prvky.
| (1 2) **3 5 9** | Čísla 2, 1 jsou ve špatném pořadí, prohodit.
| 1 **2 3 5 9** | Na konci máme správně seřazeny čtyři prvky.
| **1 2 3 5 9** | Pole je seřazeno.