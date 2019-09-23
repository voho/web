package gof.command;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class Example {
    @Test
    public void test() {
        // vytvořit frontu a naplnit ji příkazy

        final List<Command> commands = new LinkedList<>();

        commands.add(new PrintCommand("First line."));
        commands.add(new NewLineCommand());
        commands.add(new PrintCommand("Second line."));
        commands.add(new NewLineCommand());
        commands.add(new PrintCommand("Third line."));

        // příkazy jsou připraveny, nyní je možné dělat něco jiného

        // ...

        // spustit příkazy

        for (final Command command : commands) {
            command.execute();
        }
    }
}
