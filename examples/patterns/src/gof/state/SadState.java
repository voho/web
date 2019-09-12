package gof.state;

/**
 * Pocit smutku (konkrétní vnitřní stav).
 * @author Vojtěch Hordějčuk
 */
public class SadState implements StateOfMind {
    @Override
    public void express() {
        System.out.println("Jsem smutný :(");
    }
}
