import java.util.Stack;

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
