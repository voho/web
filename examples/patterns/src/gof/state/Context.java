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
     * Nastaví náhodný pocit.
     */
    public void changeYourMood() {
        if (Math.random() < 0.7) {
            System.out.println("Teď budu veselý.");
            this.state = new HappyState();
        } else {
            System.out.println("Teď budu smutný.");
            this.state = new SadState();
        }
    }
}
