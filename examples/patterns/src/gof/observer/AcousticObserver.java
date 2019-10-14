package gof.observer;

public class AcousticObserver implements Observer {
    private final SoundPlayer play = new SoundPlayer();

    @Override
    public void onEvent1() {
        play.sound(1);
    }

    @Override
    public void onEvent2() {
        play.sound(2);
    }

    @Override
    public void onEvent3() {
        play.sound(3);
    }
}