## Unit of Work (jednotka práce)

### Situace

V trvalém úložišti dat jsou uloženy entity. Program může tyto entity načítat, vytvářet, upravovat či mazat v paměti. Provedené změny se ukládají zpět do trvalého úložiště.

### Problém

Množina entit v paměti se může během spuštění programu měnit. Některé entity jsou upraveny, jiné smazány a mohou přibýt i entity nové. Pokud odpovídající změny nebudou ihned provedeny i v trvalém úložišti, budou po odstranění entit z paměti ztraceny.

Někdy je také žádoucí, aby změny v trvalém úložišti proběhly najednou, například v jedné transakci. Toto se špatně zajišťuje především tehdy, když jsou změny roztroušeny v různých částech kódu nebo v čase. Je-li totiž každá malá změna ihned propagována až do trvalého úložiště, snižuje se výkonnost programu.

### Řešení

Bude vytvořena třída, která bude zaznamenávat provedené změny a teprve na požádání tyto změny najednou provede i v trvalém úložišti. Změny nemusí provádět přímo, ale delegovat je na jiné třídy, které mají změny daného typu entity na starosti. Po provedení změn se stav třídy vrátí zpět do výchozího stavu, beze změn. Do této třídy je někdy výhodné přidat i metodu pro návrat do výchozího stavu bez uložení změn, což je praktické v případě, že se uživatel či program rozhodne změny vzít zpět a v trvalém úložišti je neprovádět.

Tato nová třída v sobě nějaký způsobem bude ukládat informaci o stavu každé změněné entity. V typické aplikaci se jedná o tři základní stavy: nová (*NEW*), změněná (*DIRTY*) a odstraněná (*REMOVED*). Změnu je nutné třídě oznámit zvenku, a to jednou z metod *markAsNew* (označí danou entitu jako nově vytvořenou), *markAsDirty* (označí entitu jako změněnou) a *markAsRemoved*. Pokud změny na nějaké entitě nechceme provádět, hodí se i metoda *removeFromChanges*, která odstraní všechny vedené záznamy o změně entity. K potvrzení změn a jejich provedení v trvalém úložišti slouží metoda *commit*, která způsobí postupné provedení všech dílčích změn, zpravidla v jedné transakci. Ke smazání všech záznamů o změnách entit je určena metoda *rollback*.

Pro jednoduchost se jako změněná označuje celá entita - ve většině případů užití totiž není nutné zacházet do podrobností a rozlišovat, jakého atributu entity se změna týká. V podobných případech se ani nemusí sledovat, je-li entita následnou změnou opět vrácena do původního stavu (shodného s trvalým úložištěm) - jako změněná zkrátka zůstane označena i nadále. To však nevadí.

#### Diagramy

```uml:class
class UnitOfWork {
  markAsNew(Entity e)
  markAsDirty(Entity e)
  markAsRemoved(Entity e)
  removeMarks(Entity e)
  commit()
  rollback()
}

class DAO <<interface>> {
  insert(Entity: e)
  update(Entity: e)
  delete(Entity: e)
  startTransaction()
  endTransaction()
}

Client -right-> UnitOfWork
UnitOfWork --> DAO
```

#### Implementace

Implementace se mohou lišit v pravidlech, jakými se řídí označování instancí. Někdy je například nepřípustné, aby byla nová instance označena jako změněná, jindy to přípustné je. Proto je nutné tato pravidla vždy pečlivě uvážit a implementovat.

Třídu Unit of Work je možné implementovat například pomocí disjunktních množin nebo jako mapu, která každé instanci přiřazuje nejvýše jednu hodnotu z množiny *NEW*, *DIRTY*, *REMOVED*.

```java
import java.util.HashSet;
import java.util.Set;

/**
 * Unit of Work.
 *
 * @author Vojtěch Hordějčuk
 * @param <V>
 * třída entity
 */
public class UnitOfWork<V>
{
  /**
   * množina nových instancí
   */
  private final Set<V> setOfNew;
  /**
   * množina změněných instancí
   */
  private final Set<V> setOfDirty;
  /**
   * množina odstraněných instancí
   */
  private final Set<V> setOfRemoved;
  /**
   * DAO pro načítání entit z trvalého úložiště
   */
  private final DAO<V> dao;

  /**
   * Vytvoří novou instanci.
   *
   * @param dao
   * DAO pro zadaný typ entit
   */
  public UnitOfWork(final DAO<V> dao)
  {
    this.setOfNew = new HashSet<V>();
    this.setOfDirty = new HashSet<V>();
    this.setOfRemoved = new HashSet<V>();

    this.dao = dao;
  }

  /**
   * Označí instanci jako novou.
   *
   * @param entity
   * instance entity
   */
  public void markAsNew(final V entity)
  {
    if (this.setOfDirty.contains(entity))
    {
      // nová instance nemohla být změněna, pokud neexistovala

      throw new IllegalStateException("Already registered as dirty.");
    }

    if (this.setOfRemoved.contains(entity))
    {
      // nová instance nemohla být odstraněna, pokud neexistovala

      throw new IllegalStateException("Already registered as removed.");
    }

    this.setOfNew.add(entity);
  }

  /**
   * Označí instanci jako změněnou.
   *
   * @param entity
   * instance entity
   */
  public void markAsDirty(final V entity)
  {
    if (this.setOfNew.contains(entity))
    {
      // pokud je instance entity nová, není ještě v úložišti
      // proto by nebylo co upravovat

      return;
    }

    this.setOfDirty.add(entity);
  }

  /**
   * Označí instanci jako smazanou.
   *
   * @param entity
   * instance entity
   */
  public void markAsRemoved(final V entity)
  {
    if (!this.setOfNew.contains(entity))
    {
      if (this.setOfDirty.contains(entity))
      {
        // instance byla dřív označena jako změněná
        // tato informace již není platná a musí se smazat

        this.setOfDirty.remove(entity);
      }

      this.setOfRemoved.add(entity);
    }
    else
    {
      // instance byla nová, v úložišti tedy není
      // proto ji lze odebrat jen tak

      this.setOfNew.remove(entity);
    }
  }

  /**
   * Vymaže záznamy o změnách zadané entity.
   *
   * @param entity
   * instance entity
   */
  public void removeFromChanges(final V entity)
  {
    this.setOfNew.remove(entity);
    this.setOfDirty.remove(entity);
    this.setOfRemoved.remove(entity);
  }

  /**
   * Provede změny v trvalém úložišti.
   */
  public void commit()
  {
    // vytvořit nové

    for (final V newEntity : this.setOfNew)
    {
      this.dao.insert(newEntity);
    }

    this.setOfNew.clear();

    // uložit změněné

    for (final V dirtyEntity : this.setOfDirty)
    {
      this.dao.update(dirtyEntity);
    }

    this.setOfDirty.clear();

    // smazat odstraněné

    for (final V removedEntity : this.setOfRemoved)
    {
      this.dao.remove(this.setOfRemoved);
    }

    this.setOfRemoved.clear();
  }

  /**
   * Vymaže všechny záznamy o změnách.
   */
  public void rollback()
  {
    this.setOfNew.clear();
    this.setOfDirty.clear();
    this.setOfRemoved.clear();
  }
}
```

### Reference

- předmět X36ASS na FEL ČVUT
- http://martinfowler.com/eaaCatalog/unitOfWork.html