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

```java
interface Observer {
  void onEvent1();
  void onEvent2();
  void onEvent3();
}
```

Jedna implementace bude vypisovat událost do konzole:

```java
class VisualObserver implements Observer {
  private final Display display = new Display();

  @Override
  public void onEvent1() {
    display.message("Event 1 detected!");
  }

  @Override
  public void onEvent2() {
    display.message("Event 2 detected!");
  }

  @Override
  public void onEvent3() {
    display.message("Event 3 detected!");
  }
}
```

Druhá implementace bude přehrávat zvuky:

```java
class AcousticObserver implements Observer {
  private final SoundPlayer play = new SoundPlayer();

  @Override
  public void onEvent1() {
    play.soundForEvent1();
  }

  @Override
  public void onEvent2() {
    play.soundForEvent2();
  }

  @Override
  public void onEvent3() {
    play.soundForEvent3();
  }
}
```

Nyní vytvoříme pozorovaný objekt, který bude udržovat seznam zaregistrovaných pozorovatelů a umožní uživatelům snadné odesílání událostí.
Pozorovatele je doporučeno volat v deterministickém pořadí.
Typicky se setkáme s tím, že budou pozorovatelské objekty voláni v tom pořadí, v jakém byly jako pozorovatelé zaregistrovány.

```java
class Observable {
  private final Collection<Observer> observers = new LinkedHashSet<>();

  public void attach(Observer o) {
    observers.add(o);
  }

  public void detach(Observer o) {
    observers.remove(o);
  }

  public void doOperation1() {
    // ... EXECUTE OPERATION 1
    // notify all listeners about event 1
    observers.forEach(o -> o.onEvent1());
  }

  public void doOperation2() {
    // ... EXECUTE OPERATION 2
    // notify all listeners about event 2
    observers.forEach(o -> o.onEvent2());
  }

  public void doOperation3() {
    // ... EXECUTE OPERATION 3
    // notify all listeners about event 3
    observers.forEach(o -> o.onEvent3());
  }
}
```

### Reference

- http://gameprogrammingpatterns.com/observer.html
- http://www.oodesign.com/observer-pattern.html
- http://www.sitepoint.com/understanding-the-observer-pattern/