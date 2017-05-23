## Změny v JDK 9

Tento seznam není úplný, protože [změn je opět mnoho](https://www.jcp.org/en/jsr/detail?id=379), ale zachycuje to nejdůležitější z hlediska běžného vývojáře.

### Vylepšení API

#### Stream, Optional, Collectors

Nejprve několik nových metod pro [proudy](wiki/java-stream). 
Pozor na metody *takeWhile* a *dropWhile*, které se chovají odlišně pro proudy [s definovaným pořadím](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html#Ordering) (ordered) a bez tohoto pořadí (unordered).
Pro proudy bez pořadí není jejich chování definováno.

```java
// takeWhile (vypíše 1 2 3)
Stream.of(1, 2, 3, 4, 10, 1).takeWhile(i -> i < 4).forEach(System.out::println);

// dropWhile (vypíše 1 2 10 1)
Stream.of(1, 2, 3, 4, 10, 1).dropWhile(i -> i > 2 && i < 5).forEach(System.out::println);

// Optional.stream (vypíše 1)
Optional.of(1).stream().forEach(System.out::println);

// Optional.stream (nevypíše nic)
Optional.empty().stream().forEach(System.out::println);

// empty stream for NULL (nevypíše nic)
Stream.ofNullable(null).forEach(System.out::println);

// extended iterate on stream (vypíše 0 1 2 3 4)
Stream.iterate(0, i -> i < 5, i -> i + 1).forEach(System.out::println);

// filtering collector
// (seznam transakcí přes 1,000 pro každý rok)
Map<Integer, List<Expense>> yearToExpensiveExpenses = purchases.stream()
        .collect(groupingBy(Expense::getYear, filtering(expense -> expense.getAmount() > 1_000, toList())));

// flat mapping collector
// (množina štítků pro každý rok)
Map<Integer, Set<String>> yearToExpenseTags = purchases.stream()
        .collect(groupingBy(Expense::getYear, flatMapping(expense -> expense.getTags().stream(), toSet())));
```

Vylepšení [vstupně-výstupních proudů](wiki/java-io-stream):

```java
ByteArrayInputStream bais = new ByteArrayInputStream(buffer);

// přečíst všechny bajty
byte[] result = bais.readAllBytes();

// kopírovat celý proud
bais.transferTo(someOutputStream);
```

#### Nové tovární metody pro kolekce

Pro množinu (*javadoc:java.util.Set*), seznam (*javadoc:java.util.List) a mapu (*javadoc:java.util.Map*) byly přidány statické [tovární metody](wiki/factory-method) pro vytváření nemodifikovatelných instancí těchto objektů.

```java
// množina
Set<String> set = Set.of("a", "b", "c");

// seznam
List<String> list = List.of("a", "b", "c");

// mapa (až pro 10 záznamů)
Map<String, String> map = Map.of("key1", "value1", "key2", "value2");

// mapa (pro větší počet záznamů)
Map<String, String> map = Map.ofEntries(
        entry("key1", "value1"),
        entry("key2", "value2")
);
```

#### Rozšíření knihovny nástrojů pro paralelizaci (JEP 266)

Několik vylepšení doznala i knihovna nástrojů pro implementaci paralelních aplikací.

První změnou je přidání podpory pro reaktivní proudy (reactive streams), což umožní snadnější implementaci aplikací typu publisher-subscriber.
K tomuto účelu byly přidány třídy *javadoc:java.util.concurrent.Flow*, *javadoc:java.util.concurrent.Flow.Publisher*, *javadoc:java.util.concurrent.Flow.Subscriber*, *javadoc:java.util.concurrent.Flow.Processor*.

Další změny můžeme najít například v *CompletableFuture* a dalších často používaných třídách.

#### API pro měny a finanční částky (JSR 354)

Nové API pro měny a finanční částky se skrývá v balíčku *javax.money*.
Například třída *javax.money.MonetaryAmount* má podobné API jako *javadoc:java.math.BigDecimal*.
API dále nabízí generické metody pro zaokrouhlování, konverzi měn a formátování.

```java
CurrencyUnit dollar = Monetary.getCurrency(Locale.US);
MonetaryAmount moneyDollars = Money.of(120, dollar);
ExchangeRateProvider ecbRateProvider = MonetaryConversions.getExchangeRateProvider("ECB");
CurrencyConversion ecbDollarConvertion = ecbRateProvider.getCurrencyConversion(dollar);
MonetaryAmount moneyReals = moneyDollars.with(ecbDollarConvertion);
```

#### Vylepšené Process API (JEP 102)

Pomocí Process API je možné spouštět a spravovat ostatní procesy, které běží mimo JVM.
V nové verzi bylo představeno několik výrazných vylepšení, například tato:

```java
// ID aktuálního procesu
int currentProcessId = ProcessHandle.current().getPid();

// ID jiného procesu
Process otherProcess = new ProcessBuilder(javaCmd, "-version").inheritIO().start();
int otherProcessId = otherProcess.toHandle().getPid();

// seznam všech procesů
ProcessHandle.allProcesses().forEach(/*...*/);

// pipeline
List<Process> oneThenAnother = ProcessBuilder.startPipeline(Arrays.asList(one, another));
```

#### Nové JSON API (JEP 198)

Bylo přidáno jednoduché API pro práci s [formátem JSON](wiki/json) podle specifikace [RFC 7150](http://tools.ietf.org/html/rfc7159). 
Toto API se nachází v balíčku *javax.json*. Objekt je reprezentovaný třídou *javadoc:javax.json.JsonObject*, pro čtení a zápis byly přidány třídy *javadoc:javax.json.JsonReader* a  *javadoc:javax.json.JsonWriter*.
Objekty lze jednoduše vytvářet pomocí [tovární třídy](wiki/abstract-factory) *javadoc:javax.json.Json*.

#### Nový klient pro HTTP 2.0 (JEP 110)

Původní HTTP klient integrovaný ve standardní knihovně byl již poněkud zastaralý a měl několik velkých nedostatků, například neumožňoval asynchronní volání.
Nový klient tyto nedostatky řeší a nabízí lepší API.

```java
HttpRequest req = HttpRequest
   .create(new URI("http://www.helloworld.com"))
   .body(noBody())
   .GET();

// asynchronní volání
CompletableFuture<HttpResponse> responseFuture = req.sendAsync();
HttpResponse response = responseFuture.get();

// synchronní volání
HttpResponse response = req.send();
```

#### Reflexe

Nyní je možné snadno zjistit, v jakém balíčku se daná třída nachází:

```java
String thisPackageName = this.getClass().getPackageName();
```

### Změny jazyka

Povolení privátních metod v rozhraních jen jen logickým následkem přidáním výchozích metod (default methods) v rozhraních v [minulé verzi jazyka](wiki/java-zmeny-jdk8).
Tento mechanismus umožní přepoužití kódu a lepší zapouzdření - v případě rozdělení kódu do několika metod.

```java
interface CharacterMapper {
    char map(char c);
    
    default char[] mapArray(char[] array) {
        char[] cc = Arrays.copyOf(array, array.length);
        mapArray(array);
        return new String(cc);
    }
    
    default String mapString(String s) {
        char[] cc = s.toCharArray();
        mapArray(cc);
        return new String(cc);
    }
    
    private void mapArray(char[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = map(array[i]);
        }
    }
}
```

Inicialize instancí typu *javadoc:java.lang.AutoCloseable* už nemusí být uzavřené v bloku *try*. 
Jediná podmínka kladaná na tyto instance je, že musí být "efektivně finální" (effective final). 

```java
public void tryWithResourcesOnVariable() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader("file.txt"));
    
    try (reader) {
        // ...
    }
}

public void tryWithResourcesOnArgument(BufferedReader reader) throws IOException {
    try (reader) {
        // ...
    }
}
```

A pozor, podtržítko již není platnou součástí identifikátorů.

### Změny vnitřní architektury a nástrojů

#### Java + REPL = jshell (dříve: projekt Kulla)

Java 9 obsahuje REPL (Read-Eval-Print-Loop), což je interaktivní prostředí s příkazovou řádkou, ve kterém lze vyhodnocovat a spouštět různé příkazy.

```
vojta$ jshell
|  Welcome to JShell -- Version 9-ea
|  For an introduction type: /help intro


jshell> int a = 10
a ==> 10

jshell> System.out.println("a value = " + a )
a value = 10

jshell> /exit
|  Goodbye
```

#### UTF-8 v property souborech (JEP 226)

V property souborech bude konečně podporována znaková sada UTF-8 jako výchozí (nahrazuje ISO-8859-1).

#### Změna výchozího Garbage Collectoru (JEP 248)

Výchozí garbage collector v Java 8 (Parallel GC) je nahrazen G1.

#### Modularizace (dříve: projekt Jigsaw)

Kód JDK byl reorganizován do několika desítek modulů, aby ho bylo možné různými způsoby kombinovat a do projektu zahrnout jen ty, které jsou skutečně potřebné.
V projektu můžete vytvořit soubor *module-info.java*. Pokud bude prázdný, váš kód nebude mít přístup k žádným knihovnám jazyka (např. *java.util.logging.*).

```java
module example {
    requires java.logging;
}
```

Existující moduly lze zobrazit příkazem *java -listmods*.

!TODO! 

#### Vylepšené kódování řetězců (JEP 254)

Řetězce jsou nyní uloženy ve dvou formách: pole typu *byte[]* (řetězce, které obsahují pouze znaky z ISO-8859-1) a pole typu *char[]* (ostatní řetězce).
Tato čistě vnitřní optimalizace redukuje potřebu spouštění garbage collectoru a také požadavky na paměť (v řádu procent).
Řetězce jsou také nově sdíleny mezi různými JVM.

### Reference

- http://blog.codefx.org/java/java-9-stream/
- http://blog.takipi.com/5-features-in-java-9-that-will-change-how-you-develop-software-and-2-that-wont/
- http://blog.takipi.com/java-9-the-ultimate-feature-list/
- http://iteratrlearning.com/java9/2016/08/16/java9-collectors.html
- http://openjdk.java.net/projects/jdk9/-C777-460B-8ACE-58BE5EA681F6
- http://www.baeldung.com/java-9-process-api
- http://www.journaldev.com/13121/java-9-features-with-examples
- https://bentolor.github.io/java9-in-action/#/1
- https://blogs.oracle.com/java/features-in-java-8-and-9develop-software-and-2-that-wont/
- https://docs.oracle.com/javase/9/whatsnew/toc.htm#JSNEW-GUID-C23AFD78-C777-460B-8ACE-58BE5EA681F6
- https://dzone.com/articles/a-look-at-intellij-ideas-support-for-java-9-modules9-modules
- https://dzone.com/articles/jdk-9-is-feature-complete/
- https://github.com/honzapaces/java9-examples/wiki
- https://www.slideshare.net/SimonRitter/55-new-features-in-jdk-9
- https://www.voxxed.com/blog/2016/10/java-9-series-concurrency-updates/
