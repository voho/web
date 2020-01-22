package gof.visitor;

/**
 * Nějaké zajímavé místo.
 */
public interface Place {
    /**
     * Přišel návštěvník.
     * @param visitor návštěvník
     */
    void accept(Visitor visitor);
}
