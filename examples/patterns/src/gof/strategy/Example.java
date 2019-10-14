package gof.strategy;

import org.junit.Test;

public class Example {
    @Test
    public void test() {
        // test první strategie

        final Context context1 = new Context(new TimesStrategy());
        final int r1 = context1.multiply(3, 5);
        System.out.println(r1); // 3*5 = 15

        // test druhé strategie

        final Context context2 = new Context(new PlusStrategy());
        final int r2 = context2.multiply(3, 5);
        System.out.println(r2); // 3*5 = 3+3+3+3+3 = 15
    }
}
