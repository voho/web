package gof.immutableobject;

import org.junit.Test;

import static org.junit.Assert.assertNotSame;

public class Example {
    @Test
    public void test() {
        final ComplexNumber a = new ComplexNumber(1, 4);
        final ComplexNumber b = new ComplexNumber(2, 3);
        final ComplexNumber sum = a.plus(b);
        assertNotSame(sum, a);
        assertNotSame(sum, b);
    }
}
