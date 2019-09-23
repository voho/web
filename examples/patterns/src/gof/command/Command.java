package gof.command;

/**
 * Rozhraní příkazu.
 * @author Vojtěch hordějčuk
 */
public interface Command {
    /**
     * Spustí vykonávání tohoto příkazu.
     */
    void execute();
}