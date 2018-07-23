public class Memento<S> {
    private final S storedState;

    public Memento(final S storedState) {
        this.storedState = storedState;
    }

    public S getStoredState() {
        return storedState;
    }
}
