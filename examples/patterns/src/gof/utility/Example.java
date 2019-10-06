package gof.utility;

import org.junit.Test;

public class Example {
    @Test
    public void test() {
        // ukázkové pole
        final int[] array = {1, 4, -3, 7};

        final String s1 = ArrayUtility.toString(array);
        // vypíše "1, 4, -3, 7"
        System.out.println(s1);

        // prohodit první a třetí prvek
        ArrayUtility.swap(array, 0, 2);

        // vypíše "-3, 4, 1, 7"
        final String s2 = ArrayUtility.toString(array);
        System.out.println(s2);
    }
}
