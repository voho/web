## GoF: Proxy (zástupce)

V určitých situacích potřebujeme nějaký objekt vzít a nahradit jiným zástupným objektem, který bude navenek stejný jako objekt původní (aby se jeho současné okolí nemuselo měnit), ale zároveň jsme mohli zasahovat do chování tohoto objektu. Můžeme tak zachytit všechna volání, která jeho uživatel provádí a tato volání převádět na jiná, ignorovat je, a tak dále.

Uvedeme několik příkladů, kdy by se taková věc mohla hodit.

- zabezpečení původního objektu (vyhodnocování uživatelských oprávnění, atd.)
- logování přístupu k objektu
- zastupování objektů náročných na paměť méně náročnými objekty
- zastupování vzdálených objektů (provádění operací přes síť)

### Podobné vzory

- [adaptér](wiki/adapter) - adaptér implementuje jiné rozhraní, než měl původní objekt, proxy implementuje to stejné
- [dekorátor](wiki/decorator) - dekorátor přidává k původnímu objektu další funkce, zatímco proxy k nim pouze řídí přístup

### Řešení

```uml:class
class Subject <<interface>> {
}

RealSubject -.-|> Subject
SubjectProxy -.-|> Subject
SubjectProxy -> RealSubject
note right on link
  proxy may use the real subject
end note
Client -> Subject
```

### Implementace

#### Rozhraní databáze

Nejprve vytvoříme rozhraní jednoduché databáze pro čtení a zápis dvojic klíč-hodnota:

```java
public interface Database<K, V> {
    V read(K key);

    void write(K key, V value);
}
```

#### Jednoduchá databáze

Nyní vytvoříme základní implementaci této databáze:

```java
public class SimpleDatabase<K, V> implements Database<K, V> {
    private final Map<K, V> storage = new HashMap<>();

    @Override
    public V read(K key) {
        return storage.get(key);
    }

    @Override
    public void write(K key, V value) {
        storage.put(key, value);
    }
}
```

#### Zabezpečená databáze

Prvním příkladem proxy objektu zastupujícího naší databázi může být její zabezpečená verze. Ta umožňuje povolit či zakázat veškeré přístupy k databázi:

```java
public class SecureDatabase<K, V> implements Database<K, V> {
    private final Database<K, V> delegate;

    public SecureDatabase(final Database<K, V> delegate) {
        this.delegate = delegate;
    }

    @Override
    public V read(final K key) {
        if (canRead()) {
            return delegate.read(key);
        } else {
            throw new IllegalAccessError("Read access denied.");
        }
    }

    @Override
    public void write(final K key, final V value) {
        if (canWrite()) {
            delegate.write(key, value);
        } else {
            throw new IllegalAccessError("Write access denied.");
        }
    }

    private boolean canRead() {
        // add security logic here
        return false;
    }

    private boolean canWrite() {
        // add security logic here
        return false;
    }
}
```

#### Logující databáze

Dalším příkladem proxy objektu zastupujícího naší jednoduchou databází může být třeba logující databáze, která všechny přístupy do databáze loguje:

```java
public class LoggingDatabase<K, V> implements Database<K, V> {
    private final Database<K, V> delegate;

    public LoggingDatabase(final Database<K, V> delegate) {
        this.delegate = delegate;
    }

    @Override
    public V read(final K key) {
        log("Reading from key: " + key);
        return delegate.read(key);
    }

    @Override
    public void write(final K key, final V value) {
        log("Writing to key: " + key);
        delegate.write(key, value);
    }

    private void log(final String message) {
        // (implement logging here)
    }
}
```

Krásné na tomto přístupu je to, že tyto zástupné objekty lze vnořovat do sebe (implementují stejné rozhraní).

### Reference

- http://www.oodesign.com/proxy-pattern.html
