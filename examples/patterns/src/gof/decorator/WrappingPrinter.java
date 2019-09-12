package gof.decorator;

public class WrappingPrinter implements Printer {
    private final Printer printer;
    private final int maxCharsPerLine;
    private int charsPerCurrentLine;

    public WrappingPrinter(final Printer printer, final int maxCharsPerLine) {
        this.printer = printer;
        this.maxCharsPerLine = maxCharsPerLine;
        this.charsPerCurrentLine = 0;
    }

    @Override
    public void printCharacter(final char c) {
        if (charsPerCurrentLine == maxCharsPerLine) {
            goToNextLine();
        }

        printer.printCharacter(c);
        charsPerCurrentLine++;
    }

    @Override
    public void goToNextLine() {
        printer.goToNextLine();
        charsPerCurrentLine = 0;
    }
}
