package gof.adapter;

/**
 * Vnořená třída, která má zpravidla zastaralé, nekompatibilní, nebo jinak
 * nevyhovujícím rozhraní. Proto k ní bude vytvořen odpovídající adaptér.
 * @author Vojtěch Hordějčuk
 */
public class Adaptee {
    /**
     * Provede požadavek.
     */
    public void oldRequest() {
        // ...
    }
}