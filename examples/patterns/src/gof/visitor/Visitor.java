package gof.visitor;

/**
 * Návštěvník.
 */
public interface Visitor {
    /**
     * Navštíví zadané museum.
     * @param museum cílové museum
     */
    void visit(Museum museum);

    /**
     * Navštíví zadané kino.
     * @param cinema cílové kino
     */
    void visit(Cinema cinema);
}
