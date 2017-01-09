## Specifikace (Specification)

### Problém

Je potřeba vybírat objekty na základě určitých kritérií a tato kritéria uchovat pro opakované použití (například jako databázový dotaz, filtr nebo validace).

### Řešení

Řešením je oddělit logiku vlastního výběru od objektů, které budou touto logikou vybírány. To lze udělat například tak, že se vytvoří základní třída představující predikát (logika, zda má objekt určitou vlastnost) a její podtřídy pro všechna potřebná výběrová kritéria a jejich logické kombinace: 

- a zároveň (*AND*, *All*)
- nebo (*OR* / *Any*),
- inverze (*NOT* / *Negate*)

```uml:class
interface Specification<T> {
  isSatisfiedFor(T object): boolean
  and(Specification<T> other): Specification<T>
  or(Specification<T> other): Specification<T>
  not(): Specification<T>
}
class AndSpecification<T> {
}
class OrSpecification<T> {
}
class NotSpecification <T> {
}
AndSpecification ..|> Specification
OrSpecification ..|> Specification
NotSpecification ..|> Specification
```

```java
public interface Specification<T> {
    boolean isSatisfiedFor(T object);

    default Specification<T> and(Specification<T> other) {
        return new AndSpecification<>(Specification.this, other);
    }

    default Specification<T> or(Specification<T> other) {
        return new OrSpecification<>(Specification.this, other);
    }

    default Specification<T> not() {
        return new NotSpecification<>(Specification.this);
    }
}
```

```java
public class AndSpecification<T> implements Specification<T> {
    private final Iterable<Specification<T>> clauses;

    public AndSpecification(Specification<T>... clauses) {
        this(Arrays.asList(clauses));
    }

    public AndSpecification(Iterable<Specification<T>> clauses) {
        this.clauses = clauses;
    }

    @Override
    public boolean isSatisfiedFor(T object) {
        for (Specification<T> clause : clauses) {
            if (!clause.isSatisfiedFor(object)) {
                return false;
            }
        }

        return true;
    }
}
```

```java
public class OrSpecification<T> implements Specification<T> {
    private final Iterable<Specification<T>> clauses;

    public OrSpecification(Specification<T>... clauses) {
        this(Arrays.asList(clauses));
    }

    public OrSpecification(Iterable<Specification<T>> clauses) {
        this.clauses = clauses;
    }

    @Override
    public boolean isSatisfiedFor(T object) {
        for (Specification<T> clause : clauses) {
            if (clause.isSatisfiedFor(object)) {
                return true;
            }
        }

        return false;
    }
}
```

```java
public class NotSpecification<T> implements Specification<T> {
    private final Specification<T> clause;

    public NotSpecification(Specification<T> clause) {
        this.clause = clause;
    }

    @Override
    public boolean isSatisfiedFor(T object) {
        return !this.clause.isSatisfiedFor(object);
    }
}
```

```java
public class HasName implements Specification<Person> {
    private final String desiredName;

    public HasName(String desiredName) {
        this.desiredName = desiredName;
    }

    @Override
    public boolean isSatisfiedFor(Person object) {
        return desiredName.equals(object.getName());
    }
}
```

```java
public class HasAge implements Specification<Person> {
    private final int minAge;
    private final int maxAge;

    public HasAge(int minAge, int maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }
    
    @Override
    public boolean isSatisfiedFor(Person object) {
        return minAge <= object.getAge() && maxAge >= object.getAge();
    }
}
```

```java
// první způsob
boolean satisfied = new HasName("John Doe").and(new HasAge(18, 20)).isSatisfiedFor(somePerson);

// druhý způsob
boolean satisfied = new AndSpecification<>(new HasName("John Doe"), new HasAge(18, 20)).isSatisfiedFor(somePerson);
```

### Reference

- http://martinfowler.com/apsupp/spec.pdf
- http://culttt.com/2014/08/25/implementing-specification-pattern/
- http://enterprisecraftsmanship.com/2016/02/08/specification-pattern-c-implementation/