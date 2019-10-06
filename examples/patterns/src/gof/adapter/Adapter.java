package gof.adapter;

/**
 * Adaptér, který implementuje funkcionalitu požadovanou rozhraním "Target" a to
 * tak, že přeposílá (deleguje) požadavky třídě vnořené. K tomu může přidat
 * nějakou řídící logiku. Okolí nemusí vědět ani o instanci vnořené třídy, ani o
 * způsobu, jakým se požadavky převádí.
 * @author Vojtěch Hordějčuk
 */
public class Adapter implements Target {
    /**
     * vnořená třída
     */
    private final Adaptee adaptee;

    /**
     * Vytvoří novou instanci.
     */
    public Adapter() {
        // vytvořit instanci adaptované třídy
        // (instance může být předána i jinak, například parametrem)

        this.adaptee = new Adaptee();
    }

    @Override
    public void newRequest() {
        // v této ukázce se volání pouze jednoduše deleguje
        // (adaptér ale může přidat nějakou řídící logiku navíc)

        this.adaptee.oldRequest();
    }
}