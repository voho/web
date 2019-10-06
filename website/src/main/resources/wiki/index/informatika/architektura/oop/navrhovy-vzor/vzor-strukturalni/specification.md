## Specifikace (Specification)

### Problém

Je potřeba vybírat objekty na základě určitých kritérií. Tato kritéria chceme modelovat pomocí bojektů, abychom je mohli například předávat v programu nebo uchovat pro opakované použití. Příkladem může být databázový dotaz, uživatelský filtr nebo pravidla validace.

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

```include:java
gof/specification/Specification.java
```

```include:java
gof/specification/AndSpecification.java
```

```include:java
gof/specification/OrSpecification.java
```

```include:java
gof/specification/NotSpecification.java
```

```include:java
gof/specification/HasName.java
```

```include:java
gof/specification/HasAge.java
```

```include:java
gof/specification/Example.java
```

### Reference

- http://martinfowler.com/apsupp/spec.pdf
- http://culttt.com/2014/08/25/implementing-specification-pattern/
- http://enterprisecraftsmanship.com/2016/02/08/specification-pattern-c-implementation/
