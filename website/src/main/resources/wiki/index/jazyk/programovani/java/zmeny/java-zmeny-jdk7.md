## Změny v JDK 7

Tento seznam nepokrývá všechny změny, neboť jich je zhruba dva tisíce. Vybrány byly jen ty nejdůležitější, se kterými se může běžný programátor setkat.

### Multi-catch

Nově je možné odchytávat více typů výjimek najednou (ve skutečnosti se jedná o odchytnutí typu vytvořeného sjednocením několika typů výjimek). Nějaká typová omezení tam jsou, ale s tím pomůže překladač a pokud máte rozumně navrženou hierarchii, není problém.

```java
try {
  // do something hard
} catch (MyException1 | MyException2 | MyException3 e) {
  // resolve specific exception
} catch (Exception e) {
  // resolve generic exception
}
```

### Switch na řetězcích

Switch na řetězcích je **case-sensitive** a výsledný bytekód je optimalizovaný (rychlejší než dřívější způsob if-else).

```java
String name= "John";

switch (monthName) {
  case "John":
  case "Jack":
  // ...
    return Gender.MALE;
  case "Mary":
  case "Sharon":
  // ...
    return Gender.FEMALE;
  default:
    return Gender.UNKNOWN;
}
```

### Numerické literály

#### Binární čísla

```java
int flags = 0b00011011;
```

#### Podtržítka

Podtržítka jsou ignorována, slouží pouze pro oddělení řádů a tedy větší srozumitelnost konstant.

```java
long bytes = 0xFE_DC_A5_18;
```

### Java NIO 2

Nové API pro práci se soubory řeší nedostatky dřívějšího řešení. Umí nově pracovat i se symlinky, tvořit vlastní souborové systémy (vhodné například pro unit testy, apod.) a operace byly přesunuty do helperů. Nově se vše točí kolem třídy *java.nio.file.Path*. Pomocné třídy s operacemi jsou *java.nio.file.Paths* (získávání cest) a *java.nio.file.Files* (souborové operace). Mezi nové funkce patří například korektní přesun a kopírování souborů, tvorbu symlinků a práci se symlinky, procházení souborového systému, sledování změn a další dříve problematické operace.

### Automatická správa zdrojů

Přibylo nové rozhraní *AutoCloseable*, které implementují například proudy (streamy). Nově lze v bloku *try* vytvářet potřebné instance typu *AutoCloseable*, které jsou automaticky na konci uzavřeny - podobně, jako by bylo jejich uzavření provedeno v bloku *finally*.

```java
try (
  InputStream is = new FileInputStream(new File("in")); 
  OutputStream os = new FileOutputStream(new File("out"));
) {
  // work with stream
} catch (Exception e) {
  // resolve exception (stream will be closed afterwards)
}
```

### Potlačení výjimek

!TODO!

### Diamantový operátor

Diamantový operátor se kompilátor snaží nahradit tím správným typem a tak na některých místech není nutné výraz znovu typovat.

```java
List<String> list = new ArrayList<>();
```

### Fork-Join pool

Fork-Join pool patří mezi třídy pro paralelní spouštění úloh složených z podúloh (např. různé rekurzivní výpočty). Úlohy dědí od abstraktní třídy *ForkJoinTask* a od jejich potomků *RecursiveTask* a *RecursiveAction*. Podúlohy lze asynchronně spouštět metodou *fork* a na jejich výsledky čekat voláním metody *join*.

```java
public static class Fibonnaci extends RecursiveTask<Long> {
    private final long n;

    public Fibonnaci(long n) {
        this.n = n;
    }

    @Override
    protected Long compute() {
        if (n <= 1L) {
            return 1L;
        }

        final Fibonnaci f1 = new Fibonnaci(n - 1);
        final Fibonnaci f2 = new Fibonnaci(n - 2);
        // submits the work and continues
        f1.fork();
        f2.fork();
        // executes both sub-tasks in parallel and waits for their finish
        return f1.join() + f2.join();
    }
}

public static void main(String[] args) throws InterruptedException, ExecutionException {
    final ForkJoinPool executor = new ForkJoinPool();
    final ForkJoinTask<Long> result = executor.submit(new Fibonnaci(15L));
    System.out.println("f(15)=" + result.get());
}
```

### Nimbus look&feel

O něco krásnější skin pro Swing aplikace. Aktivuje se takto:

```java
try {
    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
            UIManager.setLookAndFeel(info.getClassName());
            break;
        }
    }
} catch (Exception e) {
    // nimbus is not available on your system
}
```

### Reference

- http://www.ntu.edu.sg/home/ehchua/programming/java/JDK7_NewFeatures.html
- http://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/nimbus.html