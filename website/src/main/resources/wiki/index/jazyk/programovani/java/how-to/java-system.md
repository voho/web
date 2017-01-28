## Systémové třídy

### java.lang.System

#### Standardní výstup

Standardní výstup *System.out* se nejčastěji používá v konzolových aplikací k poskytování informací o stavu programu či výsledcích výpočtu. Důležité jsou především metody *print()* a *println()*. Tyto metody jsou schopné vypsat jakýkoliv objekt, který je na řetězec převeden voláním statické metody *String.valueOf()* s daným objektem jako parametrem. Proto lze bezpečně vypisovat i hodnoty *null*.

```java
System.out.println("Výsledek je " + vysledek + ".");
```

#### Standardní vstup

Pro jednoduché načítání dat ze standardního vstupu *System.in* lze využít třídu *Scanner*, která v konstruktoru obdrží libovolný [vstupní proud](wiki/java-stream), v tomto případě standardní vstup. Potom je schopná z tohoto proudu načítat tokeny zadaného typu. V případě, že se *Scanner* nachází na konci proudu, aktuální vlákno se zablokuje a *Scanner* čeká na další data. V případě chybných dat nebo uzavření toku dojde k výjimce.

```java
Scanner scanner = new Scanner(System.in);
// zde se vlákno zablokuje dokud není zadáno číslo na vstup
int number = scanner.nextInt();
// existují další podobné metody, např. nextBoolean, nextLine, atd.
```

### java.awt.Desktop

Užitečnou třídou pro vyvolávání různých uživatelských akcí je třída *Desktop* ze standardní knihovny AWT. Poměrně zajímavou metodou je otevření zadané adresy URL ve výchozím systémovém prohlížeči a otevření souboru výchozím programem. Obě tyto operace jsou demonstrovány v následujícím kódu:

#### Otevření zadané adresy v prohlížeči

Otevření adresy URL ve výchozím webovém prohlížeči:

```java
URI uri = new URI("http://google.cz/");
Desktop.getDesktop().browse(uri);
```

#### Otevření souboru

Otevření souboru výchozím přiřazeným programem:

```java
File file = new File("image.png");
Desktop.getDesktop().open(file);
// existuje i podobná funkce edit(file)
```

### java.lang.Runtime

#### Spuštění příkazu

Před použitím metody *exec()* je třeba důkladně prostudovat dokumentaci. Díky ní a praktickým zkušenostem uživatelů se ukázalo, že je na některých platformách nezbytné přesměrovat standardní a chybový výstup spuštěného procesu (nejlépe v samostatném vlákně). Bez tohoto opatření může spuštěný proces "zamrznout".

Pozor: metoda *exec()* NENÍ příkazový řádek. Její chování je třeba důkladně otestovat na všech cílových platformách a verzích JVM.

```java
private static int execute(String command) throws IOException {
  Process p = Runtime.getRuntime().exec(command);

  // spustit zachytávání chybového výstupu (nutné!)
  new StreamGobbler("ERR: ", p.getErrorStream()).start();
  // spustit zachytávání standardního výstupu (nutné!)
  new StreamGobbler("OUT: ", p.getInputStream()).start();

  return p.waitFor();
}

private static class StreamGobbler extends Thread {
  private final String prefix;
  private final InputStream is;

  private StreamGobbler(String prefix, InputStream is) {
    super();
    this.prefix = prefix;
    this.is = is;
  }

  @Override
  public void run() {
    try {
      BufferedReader dis = new BufferedReader(new InputStreamReader(is));
      String line = null;

      while ((line = dis.readLine()) != null) {
        System.out.print(prefix);
        System.out.print(line);
        System.out.println();
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
```

### Reference

- http://www.javaworld.com/javaworld/jw-12-2000/jw-1229-traps.html?page=1
- předmět X36PJV na FEL ČVUT
- předmět X36ALG na FEL ČVUT