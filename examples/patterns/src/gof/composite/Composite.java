package gof.composite;

import java.util.Collection;

/**
 * Složený objekt.
 * @author Vojtěch Hordějču
 */
public class Composite implements Component {
    /**
     * vnořené objekty
     */
    private Collection<Component> components;

    // zde může být kód pro správu vnořených objektů
    // add(Component c)
    // remove(Component c)

    @Override
    public void doSomething() {
        // projít vnořené objekty a něco udělat

        for (final Component component : this.components) {
            component.doSomething();
        }
    }
}