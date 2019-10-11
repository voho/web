package gof.nullobject;

import org.junit.Test;

public class ExampleBefore {
    @Test
    public void test() {
        // nechci logovat - nastavím NULL
        // poté musím všude počítat s hodnotou NULL

        final Logger logger = null;

        if (logger != null) {
            logger.info("Starting...");
        }

        // ...

        if (logger != null) {
            logger.info("Finished.");
        }
    }
}
