## Plant UML

[Plant UML](http://plantuml.sourceforge.net/) je nástroj pro vizualizaci diagramů UML zapsaných pomocí speciálního textového formátu.

### Komponenty

Jednoduchá třída:

```
class User {
  - name: String
  - age: int
  + getName(): String
  + getAge(): int
  + setName(value: String)
  + setAge(value: int)
}
```

```uml:class
class User {
  - name: String
  - age: int
  + getName(): String
  + getAge(): int
  + setName(value: String)
  + setAge(value: int)
}
```

Komplexní třída:

```
class User {
  .. queries ..
  + getResult(): Result
  + getResultSize(): int
  .. mutators ..
  + calculate(): void
}
```

```uml:class
class User {
  .. queries ..
  + getResult(): Result
  + getResultSize(): int
  .. mutators ..
  + calculate(): void
}
```

Rozhraní (speciální formát):

```
interface Iterator<T> {
  + next(): T
  + hasNext(): boolean
}
```

```uml:class
interface Iterator<T> {
  + next(): T
  + hasNext(): boolean
}
```

Rozhraní (klasický formát):

```
class Iterator<T> <<interface>> {
  + next(): T
  + hasNext(): boolean
}
```

```uml:class
class Iterator<T> <<interface>> {
  + next(): T
  + hasNext(): boolean
}
```

### Konektory

Asociace, poznámka:

```
one -- two
element .. note
```

```uml:class
one -- two
element .. note
```

Generalizace, implementace:

```
generic <|-- specific
interface <|.. implementation
```

```uml:class
generic <|-- specific
interface <|.. implementation
```

Agregace, kompozice:

```
tree o-- leaf
human *-- brain
```

```uml:class
tree o-- leaf
human *-- brain
```

Popisky konektorů:

```
pond "1" *-- "many" fish : contains
```

```uml:class
pond "1" *-- "many" fish : contains
```

Poznámky u konektorů:

```
pond "1" *-- "many" fish : contains
note right on link
	many fishes can swim around in a single pond
end note
```

```uml:class
pond "1" *-- "many" fish : contains
note right on link
	many fishes can swim around in a single pond
end note
```

### Reference

- http://plantuml.sourceforge.net/classes.html