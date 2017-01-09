## Změny v JDK 8

### Lambda výrazy

Lambda výrazy jsou kusy kódu, které lze předávat v programu předávat podobně jako proměnné bez nutnosti je obalovat jako v předchozích verzí Javy do rozhraní. Jejich typ je dynamicky určen podle kontextu, kde jsou použity. Lambda výrazy lze zaměňovat s odpovídajícími instancemi **funkčních rozhraními**, což jsou speciální rozhraní s jedinou abstraktní metodou (např. Runnable, Callable, Predicate, Function...).

```java
// varianta na jeden řádek pouze s lambda výrazy

Collection<String> names = Arrays.asList("John", "Mary", "Margot");
names.stream().filter(a -> a.length() < 5).sorted().forEach(a -> System.out.println(a));
```

```java
// varianta s funkčním rozhraním

Predicate<String> isShort = a -> a.length() < 5;
Consumer<String> printer = a -> System.out.println(a);
names.stream().filter(isShort).sorted().forEach(printer);
```

```java
// varianta s funkčním rozhraním (bez lambda výrazů)

Predicate<String> isShort = new Predicate<String>() {
	@Override
	public boolean test(String a) {
		return a.length() < 5;
	}
};

Consumer<String> printer = new Consumer<String>() {
	@Override
	public void accept(String t) {
		System.out.println(t);
	}
};

names.stream().filter(isShort).sorted().forEach(printer);
```

### Odkaz na metodu

Metody je možné referencovat operátorem *::*. Získané reference lze použít místo lambda výrazů. Podporovány jsou tyto reference:

| Referenční metoda | Získání reference
|---|---
| statická metoda *M* třídy *C* | C::M
| instanční metoda *M* instance *I* | I::M
| konstruktor třídy *C* | C::new

```java
Iterable data = Arrays.asList(1, 2, 5, 4, 8, 8, 55, 4);

// varianta 1
data.forEach((a) -> System.out.println(a));

// varianta 2
data.forEach(System.out::println);
```

### Default metody v rozhraních

V rozhraních je nově možné specifikovat i metody s těly, které se nazývají **default metody**. Tyto metody není nutné znovu implementovat, ale implementující třída ji může překrýt. Pokud jedna třída implementuje více rozhraní se stejně pojmenovanými default metodami, ohlásí kompilátor chybu a metodu je nutné překrýt a v těle případně provolat metodu některého z implementovaných rozhraní.

```java
public interface Greeter {
    default String greet(String name) {
        return String.format("Hello, %s!", name);
    }
}
```

```java
public class Person implements Greeter {
    // nothing here
}
```

```java
System.out.println(new Person().greet("John"));
```

### Statické metody v rozhraních

Statické metody nově lze specifikovat i v rozhraních. Každá taková metoda musí mít tělo, protože se dědičností či implementací nepřenáší do implementujících tříd. Proto lze statickou metodu rozhraní provolat pouze na tom rozhraní, které ji implementuje. Tím se předchází mnohým problémům.

```java
public interface Thanker {
	static String thank() {
		return "Thank you.";
	}
}
```

### Vylepšená reprezentace data a času

Nově jsou k dispozici tyto hlavní třídy:

- *java.time.LocalDate* pro reprezentaci data (bez času)
- *java.time.LocalTime* pro reprezentaci času (bez data)
- *java.time.LocalDateTime* pro reprezentaci data a času (kompozice výše zmíněných tříd)

Další hromada tříd umožňuje práci s časovými zónami, okamžiky, intervaly, změnami, atd. Java se zde velmi inspirovala od populární knihovny [JodaTime](http://www.joda.org/joda-time/).

```java
// get the current date
final LocalDate now = LocalDate.now();
// get the end of the current month (several ways to do it)
final LocalDate endOfMonth = LocalDate.of(now.getYear(), now.getMonth(), now.lengthOfMonth());
// get period between current date and end of the current month
final Period durationTillEndOfMonth = now.until(endOfMonth);
// get period in days
final int daysTillEndOfMonth = durationTillEndOfMonth.getDays();
```
#### Převod Date &rarr; LocalDateTime

```java
private Date convertLocalDateTimeToLegacyDate(final LocalDateTime date) {
  return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
}
``` 

#### Převod LocalDateTime &rarr; Date

```java
private LocalDateTime convertLegacyDateToLocalDateTime(final Date legacyDate) {
  return LocalDateTime.ofInstant(legacyDate.toInstant(), ZoneId.systemDefault());
}
``` 

### Reference

- http://www.oracle.com/technetwork/articles/java/jf14-date-time-2125367.html
- http://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html
- http://zeroturnaround.com/rebellabs/java-8-explained-default-methods/
- http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/index.html
- http://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html