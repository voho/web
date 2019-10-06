package gof.adapter;

/**
 * Rozhraní specifikující požadovanou funkcionalitu.
 * @author Vojtěch Hordějčuk
 */
public interface Target {
    /**
     * Provede požadavek.
     */
    void newRequest();
}
