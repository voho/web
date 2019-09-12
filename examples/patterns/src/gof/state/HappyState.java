package gof.state;

/**
 * Pocit štěstí (konkrétní vnitřní stav).
 * @author Vojtěch Hordějčuk
 */
public class HappyState implements StateOfMind {
    @Override
    public void express() {
        System.out.println("Jsem veselý :)");
    }
}
