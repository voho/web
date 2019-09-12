package gof.decorator;

public class StringPrinter implements Printer {
    private final Printer printer;

    public StringPrinter(final Printer printer) {
        this.printer = printer;
    }

    @Override
    public void printCharacter(final char c) {
        printer.printCharacter(c);
    }

    @Override
    public void goToNextLine() {
        printer.goToNextLine();
    }

    public void printString(final String s) {
        for (int i = 0; i < s.length(); i++) {
            printCharacter(s.charAt(i));
        }

        goToNextLine();
    }
}