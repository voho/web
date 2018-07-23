public class SimplePrinter implements Printer {
    @Override
    public void printCharacter(final char c) {
        System.out.print(c);
    }

    @Override
    public void goToNextLine() {
        System.out.println();
    }
}
