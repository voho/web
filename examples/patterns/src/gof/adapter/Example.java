package gof.adapter;

import org.junit.Test;

public class Example {
    @Test
    public void test() {
        // vytvořit instanci třídy s požadovanou funkcionalitou

        final Target adapter = new Adapter();

        // provést požadavek

        adapter.newRequest();
    }
}
