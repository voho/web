## Klonování objektů v jazyce Java

Nejprve je nutné vysvětlit dva základní pojmy týkající se klonování objektů. Existují dva způsoby kopírování objektů: **mělká kopie** a **hluboká kopie**. Zatímco mělká kopie zkopíruje pouze hodnoty atributů nějakého objektu a s těmito atributy dále nepracuje, hluboká kopie rekurzivně zkopíruje všechny atributy, atributy atributů a tak dále až skončí u primitivních datových typů, které se kopírují triviálně. Běžné situace jsou pak vždy určitou kombinací těchto dvou způsobů.

Každá třída v jazyce Java dědí od základní třídy *Object*. Ta obsahuje výchozí nativní metodu pro vytvoření **mělké kopie** objektu a je deklarována s následující signaturou:

```java
protected native Object clone() throws CloneNotSupportedException;
```

Ta se chová následovně: pokud daná instance není typu *Cloneable* (tzn. neimplementuje toto rozhraní), dojde k výjimce *CloneNotSupportedException*. Pokud ano, je vytvořena nová instance stejné třídy a její atributy jsou inicializovány hodnotami z původního objektu. Pro primitivní datové typy je tedy vše v pořádku, protože jsou jejich hodnoty na sobě nezávislé. Co se týče referencí, ukazují na stále stejné objekty, a proto je nutné s tím buď počítat při návrhu nebo je naklonovat také, čili vytvořit **hlubokou kopii**.

### Implementace klonování

- implementovat rozhraní *Cloneable*
- překrýt metodu *clone* a implementovat specifický způsob klonování objektu, je-li to nutné
- v implementaci metody *clone* nejprve provést klonování předka voláním *super.clone()* a poté změnit hodnoty atributů, u kterých je to potřeba (zejména u referencí)

### Příklady

#### Pouze primitivní typy

```java
public class Point implements Cloneable {
  private int x;
  private int y;
        
  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
```

#### Kombinace primitivních typů a referencí

```java
public class BinaryTreeNode implements Cloneable {
  private BinaryTreeNode left;
  private BinaryTreeNode right;
        
  @Override
  public Object clone() throws CloneNotSupportedException {
    final BinaryTreeNode clone = (BinaryTreeNode) super.clone();
    // nyní: clone.left == this.left
    // nyní: clone.right == this.right
    clone.left = this.left == null ? null : (BinaryTreeNode) this.left.clone();
    clone.right = this.right == null ? null : (BinaryTreeNode) this.right.clone();
    // nyní: clone.left != this.left
    // nyní: clone.right != this.right
    return clone;
  }
}
```

### Reference

- http://docs.oracle.com/javase/6/docs/api/java/lang/Object.html
- http://www.docjar.com/html/api/java/lang/Object.java.html
