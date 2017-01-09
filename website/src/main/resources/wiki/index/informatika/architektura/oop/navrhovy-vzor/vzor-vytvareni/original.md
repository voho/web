## Original (originál)

Návrhový vzor Original se používá všude tam, kde je pro jedinečnou sadu parametrů výhodné vytvořit jedinečnou instanci a zajistit, aby se pro stejnou sadu nedala vytvořit instance jiná. Počet instancí dané třídy je tedy shora omezen počtem všech možných kombinací parametrů.

Vzor se nejsnadněji implementuje pomocí vyrovnávací paměti (cache). Nejprve je nutné navrhnout [injektivní zobrazení](wiki/zobrazeni) ze sady parametrů do [množiny](wiki/mnozina), jejíž prvky budou sloužit jako klíče do vyrovnávací paměti instancí. Toto zobrazení spolu s vyrovnávací pamětí by mělo být efektivní, aby režie při vytváření nových instancí nebyla příliš vysoká. 

Instance dané třídy lze získávat pouze pomocí [tovární metody](wiki/factory-method), která přijímá sadu parametrů a vrací jedinečnou instanci pro každou takovou sadu. Jiné způsoby vytváření instancí by měly být zakázány na úrovni syntaxe (např. privátní konstruktor).

### Příklad

#### Barva

Níže deklarovaná třída **Color** má několik charakteristických rysů, společných pro všechny třídy navržené podle vzoru Original. Má privátní konstruktor, a proto je zajištěn jiný způsob, jak její instance získávat. K tomu je určena veřejná tovární metoda **get()**. 

Tato metoda vrací jedinečnou instanci třídy **Color** pro každou jedinečnou kombinaci vstupních parametrů. Jinými slovy, každé dvě instance touto metodou získané jsou totožné právě tehdy, když byly vytvořeny na základě stejné kombinace parametrů.

Z každé jedinečné kombinace parametrů je vytvořen jedinečný klíč, který je následně vyhledán v mapě. Jestliže tento klíč v mapě existuje, je vrácena odpovídající instance třídy **Color**. Jestliže klíč v mapě neexistuje, je vytvořena nová instance třídy, tato je uložena do mapy a vrácena.

```java
package original;

import java.util.HashMap;
import java.util.Map;

/**
 * Třída reprezentující barvu. Využívá návrhový vzor "Original".
 * @author Vojtěch Hordějčuk
 */
public class Color {
  /**
   * instance cache
   */
  private final static Map<Integer, Color> cache = new HashMap<Integer, Color>();
  /**
   * červená složka (0 - 255)
   */
  private final int r;
  /**
   * zelená složka (0 - 255)
   */
  private final int g;
  /**
   * modrá složka (0 - 255)
   */
  private final int b;
  
  /**
   * Vytvoří novou instanci.
   * @param pR červená složka (0 - 255)
   * @param pG zelená složka (0 - 255)
   * @param pB modrá složka (0 - 255)
   */
  private Color(final int pR, final int pG, final int pB) {
    this.r = pR;
    this.g = pG;
    this.b = pB;
  }
  
  /**
   * Vrátí jedinečnou instanci pro zadanou kombinaci barevných složek. Pokud
   * taková instance neexistuje, bude vytvořena a uložena pro pozdější použití.
   * @param pR červená složka (0 - 255)
   * @param pG zelená složka (0 - 255)
   * @param pB modrá složka (0 - 255)
   * @return jedinečná instance barvy
   */
  public static Color get(final int pR, final int pG, final int pB) {
    if ((pR < 0) || (pR > 255) || (pG < 0) || (pG > 255) || (pB < 0) || (pB > 255)) {
      throw new IllegalArgumentException();
    }
    
    // tvorba klíče do cache, například:
    // - 0, 0, 0 ::= 0 + 0 + 0 = 0
    // - 10, 135, 8 ::= 10 + 135000 + 8000000 = 8135010
    // - 255, 255, 255 ::= 255 + 255000 + 255000000 = 255255255
    
    final int key = pR + (pG * 1000) + (pB * 1000000);
    
    synchronized (Color.cache) {
      if (!Color.cache.containsKey(key)) {
        // unikátní instance ještě neexistuje
        // vytvoříme ji a vložíme do cache
        
        Color.cache.put(key, new Color(pR, pG, pB));
      }
      
      return Color.cache.get(key);
    }
  }
  
  // ...
  // getR()
  // getG()
  // getB()
  // ...
}
```

#### Použití

```java
Color blue1 = Color.get(0, 0, 255);
Color red1 = Color.get(255, 0, 0);
Color blue2 = Color.get(0, 0, 255);

// platí:
// blue1 == blue2
// blue1 != red1
// blue2 != red1
```

### Reference

- Rudolf Pecinovský: Návrhové vzory