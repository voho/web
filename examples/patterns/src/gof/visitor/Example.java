package gof.visitor;

import org.junit.Test;

public class Example {
    @Test
    public void test() {
        // vytvořit návštěvníky

        final Visitor good = new GoodVisitor();
        final Visitor evil = new EvilVisitor();

        // vytvořit cíle

        final Place cinema = new Cinema();
        final Place museum = new Museum();

        // hodný návštěvník

        cinema.accept(good);
        museum.accept(good);

        // zlý návštěvník

        cinema.accept(evil);
        museum.accept(evil);
    }
}
