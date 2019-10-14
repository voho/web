package gof.observer;

public class VisualObserver implements Observer {
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
