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

```include:java
gof/proxy/Database.java
```

#### Jednoduchá databáze

Nyní vytvoříme základní implementaci této databáze:

```include:java
gof/proxy/SimpleDatabase.java
```

#### Zabezpečená databáze

Prvním příkladem proxy objektu zastupujícího naší databázi může být její zabezpečená verze. Ta umožňuje povolit či zakázat veškeré přístupy k databázi:

```include:java
gof/proxy/SecureDatabase.java
```

#### Logující databáze

Dalším příkladem proxy objektu zastupujícího naší jednoduchou databází může být třeba logující databáze, která všechny přístupy do databáze loguje:

```include:java
gof/proxy/LoggingDatabase.java
```

Krásné na tomto přístupu je to, že tyto zástupné objekty lze vnořovat do sebe (implementují stejné rozhraní).

### Reference

- http://www.oodesign.com/proxy-pattern.html
