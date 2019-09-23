package gof.command;

/**
 * Příkaz, který vypíše text na standardní výstup.
 * @author Vojtěch Hordějčuk
 */
public class PrintCommand implements Command {
    /**
     * text, který se má vypsat
     */
    private final String text;

    /**
     * Vytvoří nový příkaz.
     * @param pText text
     */
    public PrintCommand(final String pText) {
        this.text = pText;
    }

    @Override
    public void execute() {
        System.out.print(this.text);
    }
}