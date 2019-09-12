package gof.memento;

import org.junit.Assert;
import org.junit.Test;

public class Example {
    @Test
    public void test() {
        final Originator<State> originator = new Originator<>();
        final Caretaker<State> caretaker = new Caretaker<>(originator);

        // initialize state
        originator.setCurrentStateTestOnly(State.STATE_1);
        Assert.assertEquals(State.STATE_1, originator.getCurrentStateTestOnly());

        // save current state and change it (1 -> 2)
        caretaker.saveState();
        originator.setCurrentStateTestOnly(State.STATE_2);
        Assert.assertEquals(State.STATE_2, originator.getCurrentStateTestOnly());

        // save current state and change it (2 -> 3)
        caretaker.saveState();
        originator.setCurrentStateTestOnly(State.STATE_3);
        Assert.assertEquals(State.STATE_3, originator.getCurrentStateTestOnly());

        // test first undo (3 -> 2)
        caretaker.undoState();
        Assert.assertEquals(State.STATE_2, originator.getCurrentStateTestOnly());

        // test second undo (2 -> 1)
        caretaker.undoState();
        Assert.assertEquals(State.STATE_1, originator.getCurrentStateTestOnly());
    }

    private enum State {
        STATE_1, STATE_2, STATE_3
    }
}
