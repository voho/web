package gof.nullobject;

/**
 * Společné rozhraní loggerů.
 * @author Vojtěch Hordějčuk
 */
public interface Logger {
    /**
     * Zaloguje informaci.
     * @param message zpráva
     */
    void info(String message);

    /**
     * Zaloguje chybu.
     * @param message zpráva
     */
    void error(String message);
}
