## Selection Sort (řazení výběrem)

Selection Sort je základní řadící algoritmus, velmi jednoduchý na pochopení i implementaci. Na začátku řazení se celé pole považuje za **neseřazenou část**. Jedním průchodem neseřazenou částí je nalezeno **minimum**, které se prohodí s prvek na začátku neseřazené části. Po této operaci je neseřazená část kratší o jeden prvek a algoritmus pokračuje stejným způsobem dál až na konec pole. Řadící algoritmus selection sort je **nepřirozený**, **nestabilní** a jeho asymptotická složitost je € O(n^2) €.

### Implementace (Java)

```java
/**
* Implementace algoritmu selection sort.
* @param input vstupní pole
* @author Vojtěch Hordějčuk
*/
public static <T extends Comparable<? super T>> void selectionSort(final T[] input)
{
 for (int i = 0; i < input.length; i++)
 {
   // 'i' je počáteční index neseřazené části pole
   // nejdřív si jako minimum vezmi to první co najdeš
   // (třeba tam nic menšího už není)
   
   int indexOfMinimum = i;
   
   // projdi zbytek pole
   
   for (int j = i + 1; j < input.length; j++)
   {
     // 'j' je počáteční index části pole, ve které hledám menší číslo
     
     if (input[j].compareTo(input[indexOfMinimum]) == -1)
     {
       // bylo nalezeno menší číslo, zapamatuj si jeho index
       
       indexOfMinimum = j;
     }
   }
   
   // pokud je třeba, prohoď začátek neseřazené části s minimem
   
   if (i != indexOfMinimum)
   {
     final T temp = input[i];
     input[i] = input[indexOfMinimum];
     input[indexOfMinimum] = temp;
   }
 }
}
```

### Ukázkový běh

| Pole | Operace
|---|---
| 5 2 3 9 1 | Toto pole chceme seřadit.
| 5 2 3 9 (1) | Nalezené minimum je 1, prohodíme s 5.
| **1** 2 3 9 5 | Jeden prvek je seřazen.
| **1** (2) 3 9 5 | Nalezené minimum je 2. Nic neprohodíme.
| **1 2** 3 9 5 | Dva prvky jsou seřazeny.
| **1 2** (3) 9 5 | Nalezené minimum je 3. Nic neprohodíme.
| **1 2 3** 9 5 | Tři prvky jsou seřazeny.
| **1 2 3** 9 (5) | Nalezené minimum je 5, prohodíme s 9.
| **1 2 3 5** 9 | Čtyři prvky jsou seřazeny.
| **1 2 3 5 9** | Dojeli jsme na poslední prvek, pole je seřazeno.