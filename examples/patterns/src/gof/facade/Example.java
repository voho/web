package gof.facade;

import org.junit.Test;

public class Example {
    @Test
    public void test() {
        final Facade facade = new Facade();

        // = 5
        System.out.println(facade.negative(-5));
        // = 15
        System.out.println(facade.mean(10, 20));
        // = 25
        System.out.println(facade.square(5));
    }
}
