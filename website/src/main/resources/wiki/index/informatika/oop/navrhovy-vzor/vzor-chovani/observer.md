## GoF: Observer (pozorovatel)

Návrhový vzor Observer (pozorovatel) se používá v situacích, kdy chceme, aby nějaké objekty dostávali informaci o změně stavu jiného objektu a zároveň mít možnost za běhu ovlivňovat, které objekty to budou. 

Klíčovou součástí řešení budou tyto objekty:

- **pozorovaný** (observable, subject, publisher) - Objekt, jehož změny pozorujeme. Obsahuje množinu pozorovatelů a umožňuje jejich správu - tedy přidávání (*attach*) a odebírání (*detach*). Objekt upozorní všechny pozorovatele na každou zásadní změnu svého stavu.
- **pozorovatel** (observer, listener) - Objekt, který dostává informaci o změně stavu pozorovaného objektu.

Granularita a charakter sledovaných změn může být libovolná - pozorovatel dokonce může obsahovat více metod, které jsou volány při různých událostech nebo v určitém pořadí (např. jedna před změnou, druhá po změně).

### Řešení

```uml:class
class Observable <<interface>> {
  -observers: Collection<Observer>
  attach(o: Observer)
  detach(o: Observer)
  #fireEvent1()
  #fireEvent2()
  #fireEvent3()
}

note top: calls fire...() on events

class ConcreteObservable1 {
}

class ConcreteObservable2 {
}

ConcreteObservable1 .up.|> Observable
ConcreteObservable2 .up.|> Observable

class Observer <<interface>> {
  onEvent1()
  onEvent2()
  onEvent3()
}

ConcreteObserver1 .up.|> Observer
ConcreteObserver2 .up.|> Observer

Observable -right-> " * " Observer

class Client {
}

Client -up-> ConcreteObservable1
Client -up-> ConcreteObservable2
```

```uml:seq
Observable <- Observer : attach()
activate Observable
Client -> Observable : doSomething()
Observable -> Observable : fireEvent()
activate Observable
Observable -> Observer : onEvent()
activate Observer
Observer --> Observable
deactivate Observer
deactivate Observable
```

### Příklad

#### Pozorovatel

Nejprve vytvoříme rozhraní pozorovatelů a posléze i dvě jeho konkrétní implementace.

```include:java
gof/observer/Observer.java
```

Jedna implementace bude vypisovat událost do konzole:

```include:java
gof/observer/VisualObserver.java
```

Druhá implementace bude přehrávat zvuky:

```include:java
gof/observer/AcousticObserver.java
```

Nyní vytvoříme pozorovaný objekt, který bude udržovat seznam zaregistrovaných pozorovatelů a umožní uživatelům snadné odesílání událostí.
Pozorovatele je doporučeno volat v deterministickém pořadí.
Typicky se setkáme s tím, že budou pozorovatelské objekty voláni v tom pořadí, v jakém byly jako pozorovatelé zaregistrovány.

```include:java
gof/observer/Observable.java
```

### Reference

- http://gameprogrammingpatterns.com/observer.html
- http://www.oodesign.com/observer-pattern.html
- http://www.sitepoint.com/understanding-the-observer-pattern/