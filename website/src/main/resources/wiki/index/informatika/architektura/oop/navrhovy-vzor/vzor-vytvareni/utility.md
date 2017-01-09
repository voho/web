## Utility (knihovna)

Jakmile se v systému objeví skupina souvisejících statických metod, u kterých existuje možnost, že by mohly být užitečné i v jiných částech systému, je vhodné uvažovat o jejich přesunu na nějaké lepší místo. Návrhový vzor Utility říká, že takovým místem má být nová třída. Třída plná statických metod má několik charakteristických vlastností, které je dobré si uvědomit a zvýšit kvalitu její deklarace.

K volání statických metod a čtení statických konstant především není třeba vytvářet instanci třídy a dokonce se to považuje za nežádoucí – instance by například zbytečně zabírala místo v paměti. Proto je lepší vytváření instancí pro jistotu úplně zakázat. Ideální je maximálně využít vyjadřovacích schopností daného jazyka, aby dodržování pravidla hlídal již kompilátor (např. v jazyce Java stačí deklarovat jediný prázdný privátní konstruktor bez parametrů).

Dále se doporučuje zamezit možnosti od takové třídy dědit, protože to nedává smysl. Hlavním přínosem dědičnosti je totiž sdílení kódu a ten lze jednodušeji sdílet voláním metod knihovní třídy. I zde je nejlepší využít vyjadřovacích prostředků daného jazyka (např. v jazyce Java stačí k deklaraci třídy přidat modifikátor **final**).

Výše popsaná třída se nazývá **knihovna**, anglicky **utility class** či **library class**.

První výhodou knihovny je její dobrá testovatelnost – statické metody se totiž obecně velmi dobře jednotkově testují. Další výhodou je zvýšení přehlednosti sdružováním souvisejících metod na jedno jasně určené místo. Jakmile se metoda dostane do knihovny, je pohodlně dostupná celému systému (pokud není její viditelnost omezena) a je-li dostatečně obecná, může snižovat počet duplicit podobného kódu. Toto vše platí ale jen za předpokladu, že všichni programátoři systému znají obsah knihoven a knihovny také používají.

Nevýhodou je možná až příliš nestydaté vystavování metod vnějšímu světu, což může signalizovat nedokonalý objektový návrh. Velké množství knihoven pracujících s rozličnými objekty jde proti základní myšlence objektového programování, a to umisťování dat a funkcí s nimi souvisejících blízko sebe.

### Postup

1. vytipovat související statické metody, které by mohly být užitečné i jiným částem systému
1. vytipované metody přesunout do nově vytvořené knihovní třídy
1. zamezit možnosti vytvořit instanci knihovní třídy (např. jediný privátní konstruktor)
1. zamezit možnosti vytvořit potomka knihovní třídy (např. klíčové slovo **final**)
1. původní volání přesunutých metod nahradit voláním metod v knihovně

### Příklad

#### Knihovní třída

```java
/**
 * Ukázková knihovní třída pro práci s polem.
 * @author Vojtěch Hordějčuk
 */
public final class ArrayUtility {
  /**
   * Není možné vytvářet instance knihovní třídy.
   */
  private ArrayUtility() {
    throw new UnsupportedOperationException();
  }

  /**
   * Prohodí dvě čísla v poli.
   * @param array pole
   * @param i1 první index
   * @param i2 druhý index
   */
  public static void swap(final int[] array, final int i1, final int i2) {
    final int temp = array[i1];
    array[i1] = array[i2];
    array[i2] = temp;
  }

  /**
   * Převede pole na řetězec, např. "<b>1, 2, 3, 4</b>".
   * @param array pole
   * @return řetězcová reprezentace pole
   */
  public static String toString(final int[] array) {
    final StringBuilder buffer = new StringBuilder(32);

    for (final int element : array) {
      if (buffer.length() != 0) {
        buffer.append(", ");
      }

      buffer.append(element);
    }

    return buffer.toString();
  }
}
```

#### Použití

```java
// ukázkové pole
final int[] array = { 1, 4, -3, 7 };

final String s1 = ArrayUtility.toString(array);
// vypíše "1, 4, -3, 7"
System.out.println(s1);

// prohodit první a třetí prvek
ArrayUtility.swap(array, 0, 2);

// vypíše "-3, 4, 1, 7"
final String s2 = ArrayUtility.toString(array);
System.out.println(s2);
```

### Reference

- Rudolf Pecinovský: Návrhové vzory