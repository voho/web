package gof.nullobject;

import org.junit.Test;

public class ExampleAfter {
    @Test
    public void test() {
        // nechci logovat - použiju Null Object
        // dále už nemusím počítat s hodnotou NULL

        final Logger logger = new NullLogger();

        logger.info("Starting...");

        // ...

        logger.info("Finished.");
    }
}
