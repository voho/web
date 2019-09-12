package gof.memento;

public class Originator<S> {
    private S internalState;

    public Memento<S> saveStateToMemento() {
        return new Memento<>(this.internalState);
    }

    public void restoreStateFromMemento(final Memento<S> memento) {
        this.internalState = memento.getStoredState();
    }

    // JUST FOR TESTING - class state should not be ever public (due encapsulation)

    S getCurrentStateTestOnly() {
        return internalState;
    }

    void setCurrentStateTestOnly(final S currentState) {
        this.internalState = currentState;
    }
}
