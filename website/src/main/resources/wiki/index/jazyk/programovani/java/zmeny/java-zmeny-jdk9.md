## Změny v JDK 9

Tento seznam není úplný, protože [změn je opět mnoho](https://www.jcp.org/en/jsr/detail?id=379), ale zachycuje to nejdůležitější z hlediska běžného vývojáře.

### Statické tovární metody pro kolekce (JEP 269)

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

- Reference: http://openjdk.java.net/jeps/269

### Nové metody pro stream

dropWhile(), takeWhile()
: podobné jako *skip* a *limit*, ale používají predikát místo čísla

iterate()
: proud bude lépe použitelný jako cyklus *for*

Optional.stream()
: vytvoří prázdný proud nebo proud s jedním prvkem

### Privátní metody v rozhraních 

!TODO!

## Rozšíření knihovny nástrojů pro paralelizaci (JEP 266)

Několik vylepšení doznala i knihovna nástrojů pro implementaci paralelních aplikací.
První změnou je přidání podpory pro reaktivní proudy (reactive streams), což umožní snadnější implementaci aplikací typu publisher-subscriber.

Další drobná vylepšení jsou ve třídě *CompletableFuture*.

- Reference: http://openjdk.java.net/jeps/266

### API pro měny a finanční částky (JSR 354)

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

- Reference: https://github.com/JavaMoney/jsr354-api

### Java + REPL = jshell (dříve: projekt Kulla)

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

- Reference: http://openjdk.java.net/jeps/222

### Vylepšené Process API (JEP 102)

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

- Reference: http://openjdk.java.net/jeps/102, http://www.baeldung.com/java-9-process-api

### HTTP 2.0 klient (JEP 110)

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

- Reference: http://openjdk.java.net/jeps/110, https://http2.github.io/

### Jednoduché JSON API (JEP 198)

Bylo přidáno jednoduché API pro práci s [formátem JSON](wiki/json) podle specifikace [RFC 7150](http://tools.ietf.org/html/rfc7159). 
Toto API se nachází v balíčku *javax.json*. Objekt je reprezentovaný třídou *javadoc:javax.json.JsonObject*, pro čtení a zápis byly přidány třídy *javadoc:javax.json.JsonReader* a  *javadoc:javax.json.JsonWriter*.
Objekty lze jednoduše vytvářet pomocí [tovární třídy](wiki/abstract-factory) *javadoc:javax.json.Json*.

- Reference: http://openjdk.java.net/jeps/198, https://www.jcp.org/en/jsr/detail?id=353

### UTF-8 v property souborech (JEP 226)

V property souborech bude konečně podporována znaková sada UTF-8 jako výchozí (nahrazuje ISO-8859-1).

- Reference: http://openjdk.java.net/jeps/226

### Změna výchozího Garbage Collectoru (JEP 248)

Výchozí garbage collector v Java 8 (Parallel GC) je nahrazen G1.

- Reference: http://openjdk.java.net/jeps/248

### Modulární knihovna (dříve: projekt Jigsaw)

Kód JDK byl reorganizován do několika desítek modulů, aby ho bylo možné různými způsoby kombinovat a do projektu zahrnout jen ty, které jsou skutečně potřebné.
V projektu můžete vytvořit soubor *module-info.java*. Pokud bude prázdný, váš kód nebude mít přístup k žádným knihovnám jazyka (např. *java.util.logging.*).

```java
module example {
    requires java.logging;
}
```

Existující moduly lze zobrazit příkazem *java -listmods*.

!TODO! 

### Vylepšené kódování řetězců (JEP 254)

Řetězce jsou nyní uloženy ve dvou formách: pole typu *byte[]* (řetězce, které obsahují pouze znaky z ISO-8859-1) a pole typu *char[]* (ostatní řetězce).
Tato čistě vnitřní optimalizace redukuje potřebu spouštění garbage collectoru a také požadavky na paměť (v řádu procent).
 
 - Reference: http://openjdk.java.net/jeps/254

### Reference

- https://github.com/honzapaces/java9-examples/wiki
- http://openjdk.java.net/projects/jdk9/
- http://blog.takipi.com/java-9-the-ultimate-feature-list/
- https://blogs.oracle.com/java/features-in-java-8-and-9
- https://docs.oracle.com/javase/9/whatsnew/toc.htm#JSNEW-GUID-C23AFD78-C777-460B-8ACE-58BE5EA681F6
- https://www.slideshare.net/SimonRitter/55-new-features-in-jdk-9
- http://www.journaldev.com/13121/java-9-features-with-examples
- https://dzone.com/articles/jdk-9-is-feature-complete
- http://blog.takipi.com/5-features-in-java-9-that-will-change-how-you-develop-software-and-2-that-wont/
- http://www.baeldung.com/java-9-process-api
- https://dzone.com/articles/a-look-at-intellij-ideas-support-for-java-9-modules
- https://www.voxxed.com/blog/2016/10/java-9-series-concurrency-updates/