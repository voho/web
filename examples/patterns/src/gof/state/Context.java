package gof.state;

/**
 * Třída s vnitřním stavem.
 * @author Vojtěch Hordějčuk
 */
public class Context {
    /**
     * aktuální pocit
     */
    private StateOfMind state;

    /**
     * Vytvoří novou instanci.
     */
    public Context() {
        this.state = new HappyState();
    }

    /**
     * Vyjádří daný pocit.
     */
    public void express() {
        System.out.println("Nyní vyjádřím svůj pocit.");
        state.express();
    }

    /**
     * Nastaví první stav.
     */
    public void beHappy() {
        System.out.println("Teď budu veselý.");
        this.state = new HappyState();
    }

    /**
     * Nastaví druhý stav.
     */
    public void beSad() {
        System.out.println("Teď budu smutný.");
        this.state = new SadState();
    }
}