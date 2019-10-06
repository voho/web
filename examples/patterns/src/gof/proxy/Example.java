package gof.proxy;

import org.junit.Test;

public class Example {
    @Test
    public void test() {
        final Database<String, String> db = new SimpleDatabase<>();
        // dbSecure is a proxy of db
        final Database<String, String> dbSecure = new LoggingDatabase<>(db);

        // no output
        db.write("A", "value of A");

        // prints "Writing to key: A"
        dbSecure.write("A", "value of A");
    }
}
