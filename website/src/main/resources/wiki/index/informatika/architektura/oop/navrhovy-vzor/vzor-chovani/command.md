## ﻿GoF: Command (příkaz)

Návrhový vzor Command se využívá v situacích, kdy je třeba vykonání nějakého příkazu zapouzdřit do třídy a tu jako proměnnou předávat v aplikaci, například z důvodu jejího zařazení do fronty k pozdějšímu vykonání nebo rekonstrukci historie příkazů. Důležité je, aby zapouzdřený příkaz obsahoval všechny potřebné informace nutné ke svému vykonání. Je-li to potřeba, může příkaz obsahovat i instrukce k provedení opačné operace, což lze ve spojení s historií příkazů použít k implementaci funkcionality undo/redo.

### UML diagramy

```uml:class
class Command <<interface>> {
  execute()
}

class UndoableCommand <<interface>> {
  undo()
}

Client -> Command
UndoableCommand --|> Command
SaveCommand ..|> Command
MakeBoldCommand ..|> UndoableCommand
MakeItalicCommand ..|> UndoableCommand
```

### Příklad

#### Rozhraní příkazu

```java
/**
 * Rozhraní příkazu.
 * @author Vojtěch hordějčuk
 */
public interface Command
{
  /**
   * Spustí vykonávání tohoto příkazu.
   */
  public void execute();
}
```

#### Konkrétní příkazy

```java
/**
 * Příkaz, který vypíše konec řádku na standardní výstup.
 * @author Vojtěch Hordějčuk
 */
public class NewLineCommand implements Command
{
  @Override
  public void execute()
  {
    System.out.println();
  }
}
```

```java
/**
 * Příkaz, který vypíše text na standardní výstup.
 * @author Vojtěch Hordějčuk
 */
public class PrintCommand implements Command
{
  /**
   * text, který se má vypsat
   */
  private final String text;

  /**
   * Vytvoří nový příkaz.
   * @param pText text
   */
  public PrintCommand(final String pText)
  {
    this.text = pText;
  }

  @Override
  public void execute()
  {
    System.out.print(this.text);
  }
}
```

#### Použití

```java
public static void main(final String[] args)
{
  // vytvořit frontu a naplnit ji příkazy

  final List<Command> commands = new LinkedList<Command>();

  commands.add(new PrintCommand("First line."));
  commands.add(new NewLineCommand());
  commands.add(new PrintCommand("Second line."));
  commands.add(new NewLineCommand());
  commands.add(new PrintCommand("Third line."));

  // příkazy jsou připraveny, nyní je možné dělat něco jiného

  // ...

  // spustit příkazy

  for (final Command command : commands)
  {
    command.execute();
  }
}
```

### Reference

- http://sourcemaking.com/design_patterns/command
- http://www.oodesign.com/command-pattern.html
