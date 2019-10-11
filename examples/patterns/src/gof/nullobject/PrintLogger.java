package gof.nullobject;

public class PrintLogger implements Logger {
    @Override
    public void info(final String message) {
        System.out.println("INFO: " + message);
    }

    @Override
    public void error(final String message) {
        System.out.println("ERROR: " + message);
    }
}
