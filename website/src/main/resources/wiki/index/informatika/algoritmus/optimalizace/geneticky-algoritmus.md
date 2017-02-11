## Genetický algoritmus

Genetický algoritmus patří mezi tzv. **evoluční algoritmy**, které jsou inspirovány přirozenými procesy v přírodě. Jako první jej popsal **John Holland** ve svém článku "Adaptation in Natural and Artificial Systems" (Michigan Press, 1975). Je založen na Darwinově teorii o vývoji druhů a simulují boj jednotlivých organizmů (jedinců) o přežití. Každý jedinec je kandidátním řešením daného problému a jeho kvalita je kvantitativně vyjádřitelná pomocí tzv. **ohodnocovací funkce** (fitness funkce). Úkolem genetického algoritmu je "vyšlechtit" takového jedince (řešení), pro kterého bude tato ohodnocovací funkce vycházet nejlépe.

## Pseudokód

Genetický algoritmus má dvě hlavní varianty. Ta první pracuje s jednou populací, která se každou generací snaží nahradit nejhorší jedince nějakými novými, zatímco ta druhá každou generaci vytváří populaci novou.

### Varianta 1 (Steady-State)

1. Vytvoř **počáteční populaci** *P* (obvykle náhodnou).
1. Vyhodnoť kvalitu každého jedince v populaci *P* pomocí **ohodnocovací funkce**.
1. Použitím **operátoru výběru** vyber jedince z populace *P*, aplikuj na ně **operátor křížení** a/nebo **operátor mutace** a vytvoř tak nového jedince.
1. Vyhodnoť kvalitu výsledného jedince a nahrad jím toho nejhoršího jedince v populaci *P*.
1. Opakuj *N*-krát od bodu 3.
1. Jedinec z populace *P* s nejvyšší kvalitou je nejlepší nalezené řešení.

### Varianta 2 (Generational)

1. Vytvoř **počáteční populaci** *P(0)* (obvykle náhodnou).
1. Vyhodnoť kvalitu každého jedince v populaci *P* pomocí **ohodnocovací funkce**.
1. Vytvoř novou prázdnou populaci *P(T)*.
1. Použitím **operátoru výběru** vyber jedince z předchozí populace *P(T-1)*, aplikuj na ně **operátor křížení** a/nebo **operátor mutace** a vytvoř tak nového jedince.
1. Vyhodnoť kvalitu výsledného jedince a vlož jej do nové populace *P(T)*.
1. Nahraď starou populaci *P(T-1)* novou populací *P(T)*.
1. Opakuj *N*-krát od bodu 3.
1. Jedinec z poslední populace *P(T)* s nejvyšší kvalitou je nejlepší nalezené řešení.

### Podrobný popis součástí

Před použitím genetického algoritmu je nutné definovat následující:

- způsob vytvoření počáteční populace
- kódování řešení
- operátor(y) výběru
- operátor(y) křížení
- operátor(y) mutace
- ohodnocovací funkci

#### Vytvoření počáteční populace (initial population)

Počáteční populace se zpravidla vytváří náhodně, aby algoritmus pokryl co možná největší část stavového prostoru. Při generování jedinců lze využít i nějaké znalosti o problému a vygenerovat některé jedince tak, aby byly co "nejblíže" očekávanému optimálnímu řešení.

#### Kodování řešení (coding)

Kódování určuje, jak je potenciální řešení v jedinci vyjádřeno. Dříve se pro jednoduchost často používalo kódování do binárních řetězců (např. 011101110), ale to je pro mnoho problémů až příliš obecné. Řešení může být reprezentováno prakticky jakoukoliv [datovou strukturou](wiki/datova-struktura), jakmile jsou pro ni definovány další operátory genetického algoritmu.

Kódování je klíčové pro kvalitu a rychlost výpočtu. Pokud genetický algoritmus navzdory všem optimalizacím nenalézá dobrá řešení, bývá na vině právě nevhodně zvolené kódování.

##### Příklady

| Kandidátní řešení | Kódování
|---|---
| Cork - Dublin - Kinsale - Limerick | 01, 00, 11, 10
| Dublin - Kinsale - Cork - Limerick | 00, 11, 01, 10
| Cork - Kinsale - Limerick - Dublin | 01, 11, 10, 00

| Kandidátní řešení | Kódování
|---|---
| (1+2) | (1, 2, +)
| ((5+3) / 2) | (5, 3, +, 2, /)
| ((1000*4)+22) | (1000, 4, *, 22, +)

#### Operátor výběru (selection operator)

Operátor výběru vybírá jedince, kteří "mohou přežít" do další generace. Aby byl genetický algoritmus podobný evoluci, musí upřednostňovat řešení "kvalitní" na úkor řešení "nekvalitních". Kvalita řešení je vyjádřena **ohodnocovací funkcí**. Operátor výběru má velký vliv na konvergenci kvality populace v čase a jeho nesprávným použitím se zvyšuje riziko, že genetický algoritmus "uvázne" v lokálním optimu a nenajde optimum globální.

Nejčastěji se používají tyto operátory výběru:

- **ruleta** (vrátí každého jedince s pravděpodobností odpovídající jeho poměrné kvalitě)
- **ruleta založená na pořadí** (vrátí jedince s pravděpodobností odpovídající jeho pořadí v populaci podle kvality)
- **turnaj** (náhodně vybere *N* jedinců a vrátí toho nejlepšího z nich)

#### Operátor křížení (crossover operator)

Křížení je proces, který z několika (nejméně dvou) jedinců (rodičů) vytvoří jedince nového (potomka). Tento jedinec pak obsahuje "smíšené" charakteristiky všech svých rodičů. Operátor křížení velmi jednoduše modeluje křížení ze skutečného života - např. dítě má obličej po matce, ale barvu vlasů a očí po otci. Ve skutečnosti je křížení daleko složitější, ale pro genetický algoritmus toto zjednodušení zpravidla bohatě stačí.

Pro kódování založená na posloupnosti symbolů se používají tyto operátory výběru:

- **jednobodové** (0011 + ABBA = 00.BA)
- **vícebodové** (01101101 + ABBBAABA = 01.BBA.10.A)
- **uniformní** (01101101 + ABBBAABA = 0.B.1.0.1.A.0.1)

![operátor křížení](ga_crossover.png)

#### Operátor mutace (mutation operator)

Mutace obecně je (obvykle malá) změna v genetickém kódu jedince, která zapříčíni viditelnou nebo neviditelnou změnu v jeho struktuře. Mutace někdy přinese nečekané zlepšení, ale také může jedince poškodit. Většina biologů věří, že právě mutace je oním hnacím motorem evoluce, v níž nové vlastnosti jinak než mutací vzniknout nemohou.

![operátor mutace](ga_mutation.png)

#### Ohodnocovací funkce (fitness function)

Ohodnocovací funkce (také fitness funkce) kvantitativně vyjadřuje kvalitu každého řešení. Obvykle je touto hodnotou kladné reálné číslo a platí, že čím vyšší toto číslo je, tím je řešení kvalitnější.

### Implementace

Bude představena jednoduchá implementace algoritmu v jazyce Java. Úkolem algoritmu je najít sekvenci znaků (*plus* = 1 bod, *mínus* = -1 bod, *nula* = 0 bodů) s nejvyšším bodovým ohodnocením. Optimálním řešením je sekvence *plusů* a algoritmus se jej pokusí najít pomocí simulované evoluce.

#### Jedinec

Implementaci genetického algoritmu začneme implementací jedince. Tento jedinec bude obsahovat ohodnocovací funkci pro zjištění jeho kvality a také operátory mutace a křížení.

```java
/**
 * Jedinec - kandidátní řešení.
 * @author Vojtěch Hordějčuk
 */
public class Individual
{
  /**
   * sekvence znaků
   */
  private char sequence[];

  /**
   * Konstruktor bez parametrů vytvoří náhodného jedince.
   */
  public Individual ()
  {
    this.sequence = new char[Algorithm.LENGTH];

    for (int i = 0; i < this.sequence.length; i ++)
    {
      this.sequence[i] = this.getRandomChar ();
    }
  }

  /**
   * Konstruktor s dvěma parametry vytvoří nového jedince křížením.
   * Je použito uniformní křížení.
   * @param p1 první rodič
   * @param p2 druhý rodič
   */
  public Individual (Individual p1, Individual p2)
  {
    this.sequence = new char[Algorithm.LENGTH];

    for (int i = 0; i < this.sequence.length; i ++)
    {
      if (Math.random () < 0.5)
      {
        this.sequence[i] = p1.sequence[i];
      }
      else

      {
        this.sequence[i] = p2.sequence[i];
      }
    }
  }

  /**
   * Provést mutaci (náhodně změnit jeden znak).
   */
  public void mutate ()
  {
    final int a = (int) (Math.random () * this.sequence.length);

    this.sequence[a] = this.getRandomChar ();
  }

  /**
   * Vrátit hodnotu fitness (kvalitu řešení)
   * @return fitness
   */
  public int getFitness ()
  {
    int score = 0;

    for (char c: this.sequence)
    {
      switch (c)
      {
        // plus jeden bod přidává
        case '+':
          score ++;
          break;

        // mínus jeden bod odebírá
        case '-':
          score --;
          break;
      }
    }

    return score;
  }

  /**
   * Vygenerovat náhodný znak (plus, mínus nebo nula).
   * @return náhodný znak (plus, mínus nebo nula)
   */
  private char getRandomChar ()
  {
    final int a = (int) (Math.random () * 3.0);

    switch (a)
    {
      case 1:
        return '+';
      case 2:
        return '-';
      default:
        return '0';
    }
  }

  @Override
  public String toString ()
  {
    return String.valueOf (this.sequence) + " (fitness = " + this.getFitness () + ")";
  }
}
```

### Populace

Budeme pokračovat vytvořením populace těchto jedinců, která obsahuje proceduru pro své zlepšení.

```java
import java.util.ArrayList;
import java.util.List;

/**
 * Populace jedinců.
 * @author Vojtěch Hordějčuk
 */
public class Population
{
  /**
   * aktuální generace
   */
  private List<Individual> pCurrent;
  /**
   * příští generace
   */
  private List<Individual> pNext;
  /**
   * nejlepší dosud nalezené řešení
   */
  private Individual best;

  /**
   * Konstruktor vytvoří náhodnou populaci jedinců.
   */
  public Population ()
  {
    this.pCurrent = new ArrayList<Individual> ();
    this.pNext = new ArrayList<Individual> ();
    this.best = null;

    for (int i = 0; i < Algorithm.POPULATION_SIZE; i ++)
    {
      this.pCurrent.add (new Individual ());
    }

    this.updateBest ();
  }

  /**
   * Vylepší populaci pomocí genetických operátorů.
   */
  public void improve ()
  {
    this.pNext.clear ();

    for (final Individual iTemp: this.pCurrent)
    {
      final Individual iNew;

      if (Math.random () < Algorithm.P_CROSSOVER)
      {
        // provést křížení
        iNew = new Individual (this.select (), this.select ());
      }
      else
      {
        // provést výběr
        iNew = this.select ();
      }

      if (Math.random () < Algorithm.P_MUTATION)
      {
        // provést mutaci
        iNew.mutate ();
      }

      // přidat jedince do nové populace
      this.pNext.add (iNew);
    }

    // nahradit aktuální populaci novou populací

    this.pCurrent.clear ();
    this.pCurrent.addAll (pNext);

    // obnovit nejlepší řešení

    this.updateBest ();
  }

  /**
   * Výběrový mechanismus, který vybere "dobrého" jedince z populace.
   * Turnaj mezi dvěma jedinci.
   * @return "dobrý" jedinec
   */
  private Individual select ()
  {
    final int i1 = (int) (Math.random () * (double) this.pCurrent.size ());
    final int i2 = (int) (Math.random () * (double) this.pCurrent.size ());

    final Individual a = this.pCurrent.get (i1);
    final Individual b = this.pCurrent.get (i2);

    if (a.getFitness () > b.getFitness ())
    {
      return a;
    }
    else
    {
      return b;
    }
  }

  /**
   * Nalezne v populaci nejlepšího jedince a uloží jej.
   */
  private void updateBest ()
  {
    this.best = null;

    for (final Individual iTemp: this.pCurrent)
    {
      if (this.best == null)
      {
        this.best = iTemp;
      }
      else
      {
        if (iTemp.getFitness () > this.best.getFitness ())
        {
          this.best = iTemp;
        }
      }
    }
  }

  /**
   * Vrátí zatím nejlepší nalezené řešení.
   * @return nejlepší nalezené řešení
   */
  public Individual getBest ()
  {
    return this.best;
  }

  @Override
  public String toString ()
  {
    String temp = "\nPOPULATION:\n";

    for (final Individual iTemp: this.pCurrent)
    {
      temp += iTemp.toString () + "\n";
    }

    return temp;
  }
}
```

### Algoritmus

Poslední chybějící částí je hlavní tělo algoritmu.

```java
/**
 * Genetický algoritmus.
 * @author Vojtěch Hordějčuk
 */
public class Algorithm
{
  /**
   * velikost populace
   */
  public static final int POPULATION_SIZE = 15;
  /**
   * počet generací
   */
  public static final int GENERATION_COUNT = 50;
  /**
   * pravděpodobnost křížení
   */
  public static final double P_CROSSOVER = 0.8;
  /**
   * pravděpodobnost mutace
   */
  public static final double P_MUTATION = 0.2;
  /**
   * délka sekvence
   */
  public static final int LENGTH = 40;

  /**
   * Spustí genetický algoritmus.
   * @return nejlepší nalezené řešení
   */
  public static Individual start ()
  {
    Population population = new Population ();

    for (int i = 0; i < Algorithm.GENERATION_COUNT; i ++)
    {
      population.improve ();

      System.out.println ("Generation = " + i);
      System.out.println (population);
    }

    return population.getBest ();
  }
}
```

#### Spuštění algoritmu

```java
public static void main (String[] args)
{
  final Individual result = Algorithm.start ();
  System.out.println ("Result: " + result.toString ());
}
```

### Hybridní genetické algoritmy

Využívá-li genetický algoritmus znalosti problému a je-li spojený s jiným specifickým algoritmem, nazývá se **hybridním**. Tato kombinace umožňuje zmírnit nevýhody genetického algoritmu (přílišná univerzálnost, neznalost problému a z toho vyplývající neoptimální a polovičatá řešení, přílišná citlivost na volbu kódování, dlouhá doba výpočtu, atd.) a zvýšit kvalitu výsledků. V poslední době se ukazuje, že se tento přístup velmi vyplácí.
