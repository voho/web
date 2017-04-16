## Identity map (mapa identit)

### Situace

Trvalé úložiště dat obsahuje entity, jejichž obrazy se vytváří v aplikaci. Tyto entity jsou rozlišeny klíčem. Pod trvalým úložištěm si lze představit například databázi, pod entitami zákazníky a pod klíčem jejich ID.
Problém

Nic nebrání tomu, aby aplikace volně vytvářela instance entit a ty rozesílala do různých částí programu. Případná změna jedné takové instance se neprojeví v ostatních instancích a dojde k porušení [principu SSOT](wiki/princip-ssot). Také je nutné při každém požadavku na entitu načítat aktuální data z trvalého úložiště, což je zbytečné, pokud se uložená instance mezitím nezměnila.

```java
public Customer getCustomer(long id)
{
  // ...
  // načíst data z databáze
  // SELECT * FROM customer WHERE id = ?
  // ...

  Customer entity = new Customer();
  // ...
  entity.setName(result.getString("name"));
  entity.setSurname(result.getString("surname"));
  // ...
  return entity;
}

public void problem()
{
  Customer c1_firstInstance = getCustomer(1);
  Customer c1_secondInstance = getCustomer(1);

  c1_secondInstance.setName("John");

  // problém: která instance je teď ta správná?
  // instance c1_firstInstance: původní jméno
  // instance c1_secondInstance: změněné jméno (John)
}
```

### Řešení

Řešení tohoto problému spočívá v odstínění programu od vytváření instancí. Vznikne nová třída, která bude instance entit spravovat a vytvářet je pouze tehdy, když instance entity s požadovaným klíčem neexistuje. V podstatě se jedná o prostou vyrovnávací paměť.

#### Varianty

- **implicitní** - jedna mapa pro každý typ entity
- **explicitní** - pouze jedna univerzální mapa, typ entity se specifikuje později

```uml:class
class IdentityMap<KEY,VALUE> <<interface>> {
  +getInstance(key: KEY): VALUE
  -createInstance(key: KEY): VALUE
}

class DAO<KEY,VALUE> <<interface>> {
  +load(key: KEY): VALUE
}

Client -> IdentityMap
IdentityMap -> DAO
note right on link
  map uses DAO to load missing objects
  (used only if no instance exists yet)
end note
```

#### Implementace

```java
import java.util.HashMap;
import java.util.Map;

/**
 * Identity Map.
 *
 * @author Vojtěch Hordějčuk
 * @param <K>
 * třída klíče (např. Long)
 * @param <V>
 * třída entity (např. Customer)
 */
public class IdentityMap<K, V>
{
  /**
   * cache obsahující jedinečné instance entit
   */
  private final Map<K, V> cache;
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
  public IdentityMap(final DAO<V> dao)
  {
    this.cache = new HashMap<K, V>();
    this.dao = dao;
  }

  /**
   * Vrátí jedinečnou instanci pro zadaný klíč. Pokud instance neexistuje, bude
   * vytvořena a uložena do cache.
   *
   * @param key
   * klíč
   * @return jedinečná instance pro zadaný klíč
   */
  public V getInstance(final K key)
  {
    if (!this.cache.containsKey(key))
    {
      // hodnota s tímto klíčem není v cache
      // bude vytvořena nová instance

      final V fresh = dao.load(key);
      this.cache.put(key, fresh);
      return fresh;
    }

    // vrátit již existující hodnotu z cache

    return this.cache.get(key);
  }
}
```

### Reference

- předmět X36ASS na FEL ČVUT
- http://martinfowler.com/eaaCatalog/identityMap.html
