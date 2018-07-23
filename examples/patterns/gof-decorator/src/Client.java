import org.junit.Test;

public class Client {
    @Test
    public void testSimplePrinter() {
        final Printer printer = new SimplePrinter();
        printer.printCharacter('H');
        printer.printCharacter('E');
        printer.printCharacter('L');
        printer.printCharacter('L');
        printer.printCharacter('O');
        printer.goToNextLine();
        printer.printCharacter('W');
        printer.printCharacter('O');
        printer.printCharacter('R');
        printer.printCharacter('L');
        printer.printCharacter('D');
        printer.goToNextLine();
    }

    @Test
    public void testWrappingPrinter() {
        final Printer printer = new WrappingPrinter(new SimplePrinter(), 3);
        printer.printCharacter('H');
        printer.printCharacter('E');
        printer.printCharacter('L');
        printer.printCharacter('L');
        printer.printCharacter('O');
        printer.printCharacter('W');
        printer.printCharacter('O');
        printer.printCharacter('R');
        printer.printCharacter('L');
        printer.printCharacter('D');
    }

    @Test
    public void testStringPrinter() {
        final StringPrinter printer = new StringPrinter(new SimplePrinter());
        printer.printString("Hello");
        printer.printString("World");
    }
}
