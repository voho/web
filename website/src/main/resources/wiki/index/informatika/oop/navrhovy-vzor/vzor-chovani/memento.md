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

##### Memento

```include:java
gof/memento/Memento.java
```

##### Originator

```include:java
gof/memento/Originator.java
```

##### Caretaker

Následující objekt lze implementovat různými způsoby. Uvedený příklad ukazuje, jak by se dalo implementovat jednoduché "undo". Externalizované stavy jsou uloženy na zásobníku a po každém zavolání metody *saveState* je stav objektu **originator** externalizován do **mementa**, které je uloženo na zásobník. Po zavolání metody *undoState* je stav objektu **originator** obnoven z **mementa**, odebraného z vrcholu zásobníku.

```include:java
gof/memento/Caretaker.java
```

##### Příklad použití

```include:java
gof/memento/Example.java
```

### Reference

- https://dzone.com/articles/design-patterns-memento
- https://sourcemaking.com/design_patterns/memento/java/1