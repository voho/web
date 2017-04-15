## JUnit 4

Knihovna JUnit slouží k implementaci a exekuci jednotkových a integračních testů. Kromě podpory pro implementaci a exekuci testů obsahuje i rozličné funkce pro kontrolu a porovnávání hodnot. Celá knihovna je navržena tak, aby byl kód přehledný a obsahoval minimální množství infrastrukturního kódu.

### Filozofie

Jednotkový test je speciální druh testu, ve kterém se testuje jedna jediná komponenta a všechny její vnější závislosti jsou simulovány. Takový test má za úkol zadanou komponentu důkladně otestovat v normálních i krajních podmínek. Pokud jsou takto otestovány všechny komponenty, je celkem vysoká pravděpodobnost, že bude celý program fungovat dobře. 

Velkou výhodou při používání jednotkového testování je i fakt, že psaní testovatelného kódu zvyšuje kvalitu všech komponent, a tedy v konečném důsledku i celého programu. Komponenta, která se nedá otestovat, je pravděpodobně špatně navržená - je nedostatečně oddělená od okolního světa, což odporuje několika dobrým praktikám objektového návrhu.

V Javě je nejmenší jednotkou třída, respektive metoda třídy. Závislostmi třídy jsou myšleny jiné třídy, které jsou k jejímu fungování nutné. 

### Implementace testů

V JUnit je každý test reprezentován třídou, jejíž název je odvozen z názvu testované třídy a příponou *Test* (například **CalculatorTest**, **UserManagementServiceTest**). JUnit od verze 4 může být zcela řízen anotacemi, takže testovací třída nemusí od žádné jiné třídy dědit a ani není nutné ji přidávat do nějakých seznamů, jako tomu bylo dříve. Testovací třída bsahuje skutečně jen samotný test, případně přípravu testovacích dat. simulovaných závislostí a jejich následný úklid.

Nejjednodušší test vypadá na první pohled jako obyčejná třída. Jediným rozdílem je přítomnost anotací z knihovny JUnit. Mezi základní anotace patří *javadoc:org.junit.Test* označující veřejné metody, které mají být v rámci testu spuštěny.

```java
public class SomeTest {
  @Test
  public void testSomething() {
    // ...
  }
}
```

#### Akce před testem a po testu

Pokud je třeba před testem či po testu vykonat nějaké akce, lze využít těchto anotací:

- *javadoc:org.junit.Before* - metoda se provede před každým spuštěním testovací metody v této třídě
- *javadoc:org.junit.After* - metoda se provede po každém spuštění testovací metody v této třídě
- *javadoc:org.junit.BeforeClass* - metoda se provede před spuštěním první testovací metody v této třídě (metoda musí být statická)
- *javadoc:org.junit.AfterClass* - metoda se provede po dokončení všech metod z této testovací třídy (metoda musí být statická)

Do metod s anotací *javadoc:org.junit.Before* se často vkládá společný kód pro inicializaci komponenty a jejich závislostí. Inicializace komponenty před každým testem způsobí, že je pro každý test připravena vždy ve stejném výchozím stavu a nemá vliv, v jakém pořadí se testovací metody spouštějí. U testů není prioritou výkon, ale jejich korektnost a úplnost.

```java
public class SomeTest {
  private Calculator calculator;

  @Before
  public void turnOn() {
    calculator = new Calculator();
    calculator.enableAdding();
    calculator.enableSubtracting();
  }
  
  @After
  public void turnOff() {
    calculator.turnOff();
  }

  @Test
  public void test() {
    // ...
  }
}
```

Pro vlastní implementaci podobných funkcí lze využít [Rules](https://github.com/junit-team/junit4/wiki/rules).

#### Ověřování výsledků

Pro logické hodnoty:

```java
// hodnota musí být TRUE
Assert.assertTrue(myReturnValue);
// hodnota musí být FALSE
Assert.assertFalse(myReturnValue);
```

Pro obecné hodnoty:

```java
// objekt *A* musí být NULL
Assert.assertNull(a);
// objekt *A* nesmí být NULL
Assert.assertNotNull(a);
// objekt *A* se musí rovnat objektu *E* (equals)
Assert.assertEquals(e, a);
// objekt *A* se nesmí rovnat objektu *E* (equals)
Assert.assertNotEquals(e, a);
// instance *A* musí být shodná s instancí *E* (==)
Assert.assertSame(e, a);
// instance *A* nesmí být shodná s instancí *E* (==)
Assert.assertNotSame(e, a);
```

#### Ověřování pomocí Matchers

Knihovna [Hamcrest Matchers](http://hamcrest.org/) se stala de-facto standardem pro tvorbu validačních tříd, tzv. **matcherů**. Matcher je v podstatě jen predikát, který obdrží objekt a odpoví, zda je podle něho daný objekt validní.

Můžete využít již připravené matchery, implementovat vlastní (jako implementaci rozhraní *javadoc:org.hamcrest.Matcher* nebo *javadoc:org.hamcrest.TypeSafeMatcher*), nebo je vzájemně kombinovat pomocí již existujících logických operátorů (*allOf* = AND, *anyOf* = OR).

```java
Assert.assertThat(
  actualValue, 
  CoreMatchers.equalTo("John")
);

Assert.assertThat(
  actualValue,
  CoreMatchers.allOf(
    new StringStartsWith("prefix"),
    new StringEndsWith("suffix")
  )
);
```

#### Ověřování pomocí AssertJ

Pro verifikaci výsledků lze použít i knihovnu [AssertJ](http://joel-costigliola.github.io/assertj/).
Ta je výborná zejména pro verifikaci kolekcí - například seznamů, množin a map.

```java
// základní ověřování
assertThat(frodo.getName())
    .isEqualTo("Frodo");

assertThat(frodo)
    .isNotEqualTo(sauron);
```

```java
// ověřování výjimek
assertThatThrownBy(() -> { throw new Exception("boom!"); })
        .hasMessage("boom!");
```

```java
// ověřování prvků v seznamu
assertThat(fellowshipOfTheRing)
    .extracting(TolkienCharacter::getName)
    .contains("Sam", "Legolas")
```

```java
// ověřování prvků v mapě
assertThat(ringBearers)
    .containsValues(frodo, galadriel);

assertThat(ringBearersByName).containsOnly(
        entry(sam.getName(), sam),
        entry(frodo.getName(), frodo),
        entry(gandalf.getName(), gandalf),
        entry(galadriel.getName(), galadriel)
);
```

### Spuštění testů

#### Spuštění v rámci buildu pomocí Maven

Maven podporu pro testy má již vestavěnou, takže není nutné do *pom.xml* příliš zasahovat, pokud nevyžadujete nějaké speciální nastavení. 

Nejdříve je nutné přidat závislost na JUnit se scopem *test*. To zařídí, že se knihovna JUnit nebude vyskytovat ve výsledném buildu, použije se jen pro tvorbu a  spuštění testů.

```xml
<dependency>
  <groupId>junit</groupId>
  <artifactId>junit</artifactId>
  <version>4.XX</version>
  <scope>test</scope>
</dependency>
```

Testy se standardně umisťují do této adresářové struktury:

- **/src/test/java** = složka pro testovací třídy
- **/src/test/resources** = složka pro testovací resources

Třídy a resources z tého struktury rozšíří produkční classpath a navíc mají navíc k dispozici všechny závislosti se scopem *test*.

Testy se budou spouštět automaticky s každým buildem (pokud to nechceme, stačí přidat parametr *-DskipTests=true*).

Samostatně lze spustit testy například takto:

```bash
mvn clean test
```

#### Spuštění z kódu

Spustit testy lze pomocí metody *runClasses* třídy *javadoc:org.junit.runner.JUnitCore*. Spustit lze libovolné množství testů.

```java
org.junit.runner.JUnitCore.runClasses(MyTest1.class, MyTest2.class);
```

#### Spuštění z příkazové řádky

Předpokladem pro spuštění z příkazové řádky je přítomnost knihovny JUnit i požadovaných testů na classpath. Spustit lze libovolné množství testů.

```bash
java org.junit.runner.JUnitCore MyTest1.class MyTest2.class
```

### Tipy a triky

#### Testování se souborovým systémem

K vytvoření testovacích souborů lze využít třídu *javadoc:org.junit.rules.TemporaryFolder*. Tato proměnná musí být deklarovaná veřejně a anotovaná jako *javadoc:org.junit.Rule*. Systém JUnit vytvoří dočasný adresář před spuštěním testů a po dokončení každé testovací metody (nezávisle na jejím výsledku) smaže všechny soubory, které testovací metoda vytvořila.

```java
public class FileSystemUnitTest {
  @Rule
  public TemporaryFolder temporaryFolder = new TemporaryFolder();

  @Test
  public void testSomeFileFunction() throws IOException {
    File newFile = temporaryFolder.newFile("output.txt");
    // ...
  }
}
```

#### Testování s databází

Jako databáze vhodná pro jednotkové testy se ukázala [H2 Database](http://www.h2database.com/html/main.html). Ta nabízí embedded i in-memory režim a umožňuje snadnou inicializaci databáze ze skriptů pomocí třídy *javadoc:org.h2.tools.RunScript*:

```java
RunScript.execute(connection, new FileReader("create_db.sql"));
```

Při použití frameworku Spring se inicialize ještě trochu zjednoduší:

```xml
<jdbc:embedded-database id="dataSource" type="H2">
  <jdbc:script location="classpath:db/sql/create-db.sql" />
  <jdbc:script location="classpath:db/sql/insert-data.sql" />
</jdbc:embedded-database>
```

#### Testování se Spring kontextem

K testování se Spring kontextem je nutné do testovacích závislostí přidat *org.springframework.test*. Pomocí anotace *javadoc:org.junit.runner.RunWith* přítomné v JUnit se pak definuje, že se daný unit test bude spouštět pomocí speciální třídy, která pak podle dalších anotací provede potřebná nastavení třídy unit testu.

Tímto způsobem lze testovat různé beany přítomné v kontextu a validovat jejich vlastnosti. Obecně se však podobný test doporučuje brát spíše jako integrační testy vyšší úrovně, které testují pouze to, že je kontext validní (lze jej vytvořit) a jednotlivé závislosti si "sednou" dobře dohromady. Funkce jednotlivých bean by měly být otestovány zvlášť bez nutnosti vůbec nějaký kontext vytvářet.

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UnitTestWithSpringContext {
    @Autowired
    private MyBean myBean;

    @Test
    public void testMyBeanIsValid() {
        assertTrue(myBean.isValid());
    }
}
```

#### Tvorba mocků

K tvorbě mocků lze využít například framework [Mockito](wiki/mockito).

#### Přeskočení testů

Přeskočit testovací metodu nebo celý test lze pomocí anotace *javadoc:org.junit.Ignore*.

```java
@Ignore
@Test
public void testSomething() {
  // ...
}
```

```java
@Ignore
public class SomeTest1() {
  // ...
}
```

#### Očekávané výjimky

Chyby v programech se stávají a je dobré mít otestované jejich chování i v těchto krajních případech. A to nejen kvůli dosažení vysokého procenta pokrytí, ale hlavně kvůli zajištění rozumného zotavení programu po chybě.

```java
@Test(expected = IOException.class)
public void testWithTimeout() {
  throw new IOException("This test will pass.");
}
```

#### Časový limit

Pokud zadaný test nedoběhne do stanovené doby, označí se jako nesplněný. Osobně tuto konstrukci příliš nedoporučuji

```java
@Test(timeout = 1000)
public void testWithTimeout() {
  // ...
}
```

### Reference

- http://junit.org/
- https://github.com/junit-team/junit4/wiki/rules
- http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html
