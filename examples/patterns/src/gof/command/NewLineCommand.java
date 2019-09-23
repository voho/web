package gof.command;

/**
 * Příkaz, který vypíše konec řádku na standardní výstup.
 * @author Vojtěch Hordějčuk
 */
public class NewLineCommand implements Command {
    @Override
    public void execute() {
        System.out.println();
    }
}
