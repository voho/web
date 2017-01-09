## GoF: Memento (památka)

Návrhový vzor memento ukazuje, jak řešit problém externalizace, uchování a obnovení vnitřního stavu objektu bez porušení **principu zapouzdření**.  

### Řešení

Princip zapouzdření říká, že přímý přístup k vnitřnímu stavu nějakého objektu může mít jen dotyčný objekt sám. Pokud se budeme při řešení problému tímto principem řídit, musí to být tedy objekt sám, který svůj vnitřní stav externalizuje do určité formy a z tého formy ho opět obnovuje. Dotyčný objekt označíme jako **originator**, externalizovanou formu jeho vnitřního stavu jako **memento** a k jejich uchování můžeme zavést další objekt, tzv. **caretaker**.

Při externalizaci požádá **caretaker** objekt **originator**, aby externalizoval svůj stav. Ten to provede a vrátí novou instanci **mementa**. Objekt **caretaker** jej uchová pro pozdější použití. Jakmile je potřeba stav **originatoru** obnovit, objekt **caretaker** vyhledá správné **memento** a předá jej objektu **originator**, který pomocí něj obnoví svůj vnitřní stav.

#### Diagramy

##### Diagram tříd

```uml:class
class Originator {
  - State internalState;
  + saveStateToMemento(): Memento
  + restoreStateFromMemento(Memento): void
}
note top
originator:
- creates new memento(s) using its state
- restores its state from a memento
end note
class Memento {
  - State storedState;
}
class Caretaker {
}
note bottom: stores memento(s)
class Client {
}
Originator -down-> Memento
Caretaker o-left-> Memento
Client -left-> Originator
Client --> Caretaker
```

##### Sekvenční diagram

```uml:seq
== Save state ==
activate Caretaker
activate Originator
Caretaker -> Originator: saveStateToMemento()
Originator -> Memento: new(internalState)
activate Memento
Memento --> Originator: new memento
deactivate Memento
Originator --> Caretaker: memento
... ...
== Restore state ==
Caretaker -> Originator: restoreFromMemento(memento)
Originator -> Memento: getStoredState()
activate Memento
Memento --> Originator: storedState
destroy Memento
Originator -> Originator: update internalState
Originator --> Caretaker
deactivate Caretaker
deactivate Originator
```

#### Implementace

* https://github.com/voho/examples/tree/master/patterns/gof-memento

##### Memento

```java
public class Memento<S> {
    private final S storedState;

    public Memento(final S storedState) {
        this.storedState = storedState;
    }

    public S getStoredState() {
        return storedState;
    }
}
```

##### Originator

```java
public class Originator<S> {
    private S internalState;

    public Memento<S> saveStateToMemento() {
        return new Memento<>(this.internalState);
    }

    public void restoreStateFromMemento(final Memento<S> memento) {
        this.internalState = memento.getStoredState();
    }
}

```

##### Caretaker

Následující objekt lze implementovat různými způsoby. Uvedený příklad ukazuje, jak by se dalo implementovat jednoduché "undo". Externalizované stavy jsou uloženy na zásobníku a po každém zavolání metody *saveState* je stav objektu **originator** externalizován do **mementa**, které je uloženo na zásobník. Po zavolání metody *undoState* je stav objektu **originator** obnoven z **mementa**, odebraného z vrcholu zásobníku.

```java
public class Caretaker<S> {
    private final Originator<S> originator;
    private final Stack<Memento<S>> history;

    public Caretaker(final Originator<S> originator) {
        this.originator = originator;
        this.history = new Stack<>();
    }

    public void saveState() {
        history.push(originator.saveStateToMemento());
    }

    public void undoState() {
        originator.restoreStateFromMemento(history.pop());
    }
}
```

##### Použití

```java
final Originator<State> originator = new Originator<>();
final Caretaker<State> caretaker = new Caretaker<>(originator);

// initialize state
originator.setCurrentStateTestOnly(State.STATE_1);
assertEquals(State.STATE_1, originator.getCurrentStateTestOnly());

// save current state and change it (1 -> 2)
caretaker.saveState();
originator.setCurrentStateTestOnly(State.STATE_2);
assertEquals(State.STATE_2, originator.getCurrentStateTestOnly());

// save current state and change it (2 -> 3)
caretaker.saveState();
originator.setCurrentStateTestOnly(State.STATE_3);
assertEquals(State.STATE_3, originator.getCurrentStateTestOnly());

// test first undo (3 -> 2)
caretaker.undoState();
assertEquals(State.STATE_2, originator.getCurrentStateTestOnly());

// test second undo (2 -> 1)
caretaker.undoState();
assertEquals(State.STATE_1, originator.getCurrentStateTestOnly());
```

### Reference

- https://dzone.com/articles/design-patterns-memento
- https://sourcemaking.com/design_patterns/memento/java/1