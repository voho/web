import org.junit.Test;

public class Client {
    @Test
    public void testSimplePrinter() {
        final LoggerMediator mediator = new LoggerMediator(
                new StdOutLogger(),
                new StdErrLogger()
        );

        mediator.logInfo("Application started.");
        mediator.logError("Application error.");
        mediator.logInfo("Application terminated.");
    }
}
