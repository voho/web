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

```include:java
gof/command/Command.java
```

#### Konkrétní příkazy

```include:java
gof/command/NewLineCommand.java
```

```include:java
gof/command/PrintCommand.java
```

#### Použití

```include:java
gof/command/Example.java
```


### Reference

- http://sourcemaking.com/design_patterns/command
- http://www.oodesign.com/command-pattern.html
