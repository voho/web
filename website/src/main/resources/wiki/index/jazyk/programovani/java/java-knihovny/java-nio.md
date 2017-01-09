## NIO

### Třídy pro práci se soubory

Základní třídou pro práci se soubory je *java.nio.file.Path*. Tato třída představuje jeden soubor nebo adresář, tedy jeden prvek souborového systému. Její instanci lze vytvořit například tovární metodou na třídě *java.nio.file.Paths*:

```java
final Path file = Paths.get("c:/sandbox/data.txt");
final Path file = Paths.get("c:","sandbox","data.txt");
final Path file = Paths.get("var", "output", "log", "2001-02-03.log");
```

Na základě existující instance třídy *Path* lze vytvářet další instance a pohybovat se tak v adresářovém stromě:

```java
final Path dir = Paths.get("c:/sandbox/");
final Path file = dir.resolve("data.txt");
// file = c:/sandbox/data.txt
final Path anotherFile = file.resolveSibling("meta.txt");
// anotherFile = c:/sandbox/meta.txt
```

#### Základní operace

Pro základní operace se používá knihovní třída *java.nio.file.Files*. Soubory a složky lze přesouvat, kopírovat, mazat, vytvářet, atd. Třída dále obsahuje metody pro proudové i neproudové načítání obsahu souboru, procházení obsahu adresáře, a podobně. Ke většině operací lze přidat speciální příznaky (flagy), které upřesňují chování operace v různých situacích.

```java
// copying
Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
```

```java
// moving
Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
```

```java
// deleting
Files.delete(target);
Files.deleteIfExists(target);
```

```java
// new directory
final Path newDirectory = Paths.get("c:", "sandbox", "some", "new", "directory");
Files.createDirectories(newDirectory);
```

### Reference

- http://docs.oracle.com/javase/tutorial/essential/io/notification.html#process