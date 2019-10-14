package gof.observer;

import java.util.Collection;
import java.util.LinkedHashSet;

public class Observable {
    private final Collection<Observer> observers = new LinkedHashSet<>();

    public void attach(final Observer o) {
        observers.add(o);
    }

    public void detach(final Observer o) {
        observers.remove(o);
    }

    public void doOperation1() {
        // ... EXECUTE OPERATION 1
        // notify all listeners about event 1
        observers.forEach(Observer::onEvent1);
    }

    public void doOperation2() {
        // ... EXECUTE OPERATION 2
        // notify all listeners about event 2
        observers.forEach(Observer::onEvent2);
    }

    public void doOperation3() {
        // ... EXECUTE OPERATION 3
        // notify all listeners about event 3
        observers.forEach(Observer::onEvent3);
    }
}