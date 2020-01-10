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

```include:java
gof/unitofwork/UnitOfWork.java
```

### Reference

- předmět X36ASS na FEL ČVUT
- http://martinfowler.com/eaaCatalog/unitOfWork.html