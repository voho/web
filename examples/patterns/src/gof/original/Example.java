package gof.original;

import org.junit.Test;

public class Example {
    @Test
    public void test() {
        final Color blue1 = Color.get(0, 0, 255);
        final Color red1 = Color.get(255, 0, 0);
        final Color blue2 = Color.get(0, 0, 255);

        // platí:
        // blue1 == blue2
        // blue1 != red1
        // blue2 != red1
    }
}
