package gof.nullobject;

public class NullLogger implements Logger {
    @Override
    public void info(final String message) {
        // NOP
    }

    @Override
    public void error(final String message) {
        // NOP
    }
}
