package gof.original;

import java.util.HashMap;
import java.util.Map;

/**
 * Třída reprezentující barvu. Využívá návrhový vzor "Original".
 * @author Vojtěch Hordějčuk
 */
public class Color {
    /**
     * instance cache
     */
    private final static Map<Integer, Color> cache = new HashMap<Integer, Color>();
    /**
     * červená složka (0 - 255)
     */
    private final int r;
    /**
     * zelená složka (0 - 255)
     */
    private final int g;
    /**
     * modrá složka (0 - 255)
     */
    private final int b;

    /**
     * Vytvoří novou instanci.
     * @param pR červená složka (0 - 255)
     * @param pG zelená složka (0 - 255)
     * @param pB modrá složka (0 - 255)
     */
    private Color(final int pR, final int pG, final int pB) {
        this.r = pR;
        this.g = pG;
        this.b = pB;
    }

    /**
     * Vrátí jedinečnou instanci pro zadanou kombinaci barevných složek. Pokud
     * taková instance neexistuje, bude vytvořena a uložena pro pozdější použití.
     * @param pR červená složka (0 - 255)
     * @param pG zelená složka (0 - 255)
     * @param pB modrá složka (0 - 255)
     * @return jedinečná instance barvy
     */
    public static Color get(final int pR, final int pG, final int pB) {
        if ((pR < 0) || (pR > 255) || (pG < 0) || (pG > 255) || (pB < 0) || (pB > 255)) {
            throw new IllegalArgumentException();
        }

        // tvorba klíče do cache, například:
        // - 0, 0, 0 ::= 0 + 0 + 0 = 0
        // - 10, 135, 8 ::= 10 + 135000 + 8000000 = 8135010
        // - 255, 255, 255 ::= 255 + 255000 + 255000000 = 255255255

        final int key = pR + (pG * 1000) + (pB * 1000000);

        synchronized (Color.cache) {
            if (!Color.cache.containsKey(key)) {
                // unikátní instance ještě neexistuje
                // vytvoříme ji a vložíme do cache

                Color.cache.put(key, new Color(pR, pG, pB));
            }

            return Color.cache.get(key);
        }
    }

    // ...
    // getR()
    // getG()
    // getB()
    // ...
}