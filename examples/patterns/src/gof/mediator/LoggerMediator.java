package gof.mediator;

public class LoggerMediator {
    private final StdOutLogger stdOutLogger;
    private final StdErrLogger stdErrLogger;

    public LoggerMediator(final StdOutLogger stdOutLogger, final StdErrLogger stdErrLogger) {
        this.stdOutLogger = stdOutLogger;
        this.stdErrLogger = stdErrLogger;
    }

    public void logInfo(final String message) {
        stdOutLogger.print("INFO: " + message);
    }

    public void logError(final String message) {
        stdOutLogger.print("ERROR: " + message);
        stdErrLogger.print(message);
    }
}
