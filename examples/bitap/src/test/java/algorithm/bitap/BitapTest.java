package algorithm.bitap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BitapTest {
    private final Bitap toTest = new Bitap();

    @Test
    public void test() {
        assertEquals(-1, toTest.find("hello world".toCharArray(), "wox".toCharArray()));
        assertEquals(0, toTest.find("hello world".toCharArray(), "h".toCharArray()));
        assertEquals(0, toTest.find("hello world".toCharArray(), "hello".toCharArray()));
        assertEquals(0, toTest.find("hello world".toCharArray(), "hello world".toCharArray()));
        assertEquals(10, toTest.find("hello world".toCharArray(), "d".toCharArray()));
        assertEquals(6, toTest.find("hello world".toCharArray(), "world".toCharArray()));
        assertEquals(6, toTest.find("hello world".toCharArray(), "w".toCharArray()));
    }
}