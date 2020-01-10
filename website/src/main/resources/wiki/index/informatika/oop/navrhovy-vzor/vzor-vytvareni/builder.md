## GoF: Builder (stavitel)

Builder (stavitel) ja návrhový vzor popisující způsob jak přistupovat k tvorbě různých instancí s podobným konstrukčním procesem. Ve skutečném světě by se to dalo přirovnat například k přípravě pizzy - všechny druhy mají stejný základ, liší se jen na povrchu.

Obecně je v podobných případech vhodné společnou kostru postupu zachytit a jeho zobecněním snížit duplicitu kódu. Je-li navíc kostra soustředěna na jednom místě, je možné se na toto místo podrobněji zaměřit a vylepšit jej například důkladnější validací.

### Řešení

Návrhový vzor Builder je založen na interakci následujících objektů:

- **produkt** (product) - konkrétní výsledná instance, kterou řídící objekt vytvoří; produkty mohou být uspořádány v hierarchii, ale není to pravidlem
- **řídící objekt** (director) - řídí výkonné objekty při vytváření produktu; stará se o vytváření jednotlivých částí produktu ve správném pořadí, validaci jeho stavu a hlášení případných chyb
- **výkonný objekt** (builder) - realizuje vytváření konkrétních částí produktu; neměl by však předpokládat žádnou konkrétní posloupnost volání svých jednotlivých metod

Charakteristické chování této struktury je následující:

1. Klient požádá řídící objekt o vytvoření nového produktu a předá mu výkonný objekt, který má pro tvorbu tohoto produktu použít.
1. Řídící objekt zahájí proces vytváření produktu pomocí daného výkonného objektu a během něj zpravidla reflektuje svůj stav (nastavení) či výsledky dílčích operací.
1. Po vytvoření produktu je tento předán klientovi.

#### Diagramy

```uml:class
class Director {
  build(): Product
}

class Builder <<interface>> {
  buildPart1()
  buildPart2()
  buildPart3()
}

class Product {
  part1
  part2
  part3
}

Client ..> Product
note top on link: client needs product
Builder ..> Product
note top on link: builder creates and prepares product
Client -> Director
Director -> Builder
ConcreteBuilder1 ..|> Builder
ConcreteBuilder2 ..|> Builder
```

### Příklad

#### Stavba domu

##### Budova

Začneme jednoduchou třídou představující budovu. Bude mít tři atributy: podlahu (floor), zdi (walls) a střechu (roof). Budova bude naším produktem. Různé budovy se od sebe budou lišit podlahou, zdmi a střechou. Důležité je, že klient o postavení budovy požádá řídící objekt (stavbyvedoucího) a o proces jejího vystavění se nestará.

```java
public class Building {
  private String floor;
  private String walls;
  private String roof;

  public void setFloor(final String pFloor) {
    this.floor = pFloor;
  }

  public void setWalls(final String pWall) {
    this.walls = pWall;
  }

  public void setRoof(final String pRoof) {
    this.roof = pRoof;
  }

  public void print() {
    System.out.println(String.format(
            "%s, %s, %s", 
            this.floor, 
            this.walls, 
            this.roof));
  }
}
```

##### Rozhraní stavitelů

Stavitelé v našem příkladu budou mít několik společných metod, a to na zahájení nové stavby, položení podlahy, stavbu zdí, stavbu střechy a získání výsledné budovy. Proto vytvoříme rozhraní, které budou všichni stavitelé implementovat.

```java
public interface Builder {
  /**
   * Zahájí novou stavbu.
   */
  void startNew();

  /**
   * Položí podlahu.
   */
  void buildFloor();

  /**
   * Postaví zdi.
   */
  void buildWalls();

  /**
   * Postaví střechu.
   */
  void buildRoof();

  /**
   * Vrátí výslednou budovu.
   * @return výsledná budova
   */
  Building getResult();
}
```

##### Stavitelé

Pro náš příklad vytvoříme dvě různé implementace stavitelů: jeden bude stavě budovy levné, druhý luxusní. Klient si pak jednoho z nich vybere a tuto volbu sdělí stavbyvedoucímu. Oba stavitelé budou v našem případě výkonnými objekty, protože zajišťují konkrétní úkony při stavbě.

```java
public class CheapBuilder implements Builder {
  private Building result;

  @Override
  public void startNew() {
    this.result = new Building();
  }

  @Override
  public void buildFloor() {
    this.result.setFloor("laminate floor");
  }

  @Override
  public void buildWalls() {
    this.result.setWalls("panel walls");
  }

  @Override
  public void buildRoof() {
    this.result.setRoof("wooden roof");
  }

  @Override
  public Building getResult() {
    return this.result;
  }
}
```

```java
public class LuxuryBuilder implements Builder {
  private Building result;

  @Override
  public void startNew() {
    this.result = new Building();
  }

  @Override
  public void buildFloor() {
    this.result.setFloor("wooden floor");
  }

  @Override
  public void buildWalls() {
    this.result.setWalls("brick walls");
  }

  @Override
  public void buildRoof() {
    this.result.setRoof("shindel roof");
  }

  @Override
  public Building getResult() {
    return this.result;
  }
}
```

##### Stavbyvedoucí

V roli řídícího objektu zde bude vystupovat stavbyvedoucí. Ten dostane k dispozici stavitele, s jehož pomocí budovu postupně vystaví. Začne podlahou, pokračuje stěnami a nakonec postaví střechu. Po dokončení všech operací vrátí klientovi výslednou budovu.

```java
public class Director {
  /**
   * Postaví budovu s použitím zadaného stavitele.
   * @param builder stavitel
   * @return výsledná budova
   */
  public Building build(final Builder builder) {
    // zahájit stavbu nové budovy
    builder.startNew();
	// položit podlahu
    builder.buildFloor();
	// postavit zdi
    builder.buildWalls();
	// postavit střech
    builder.buildRoof();
	// vrátit výsledek
    return builder.getResult();
  }
}
```

##### Použití

```java
final Director director = new Director();

// levná budova
final Building cheapBuilding = director.build(new CheapBuilder());
cheapBuilding.print();

// luxusní budova
final Building luxuryBuilding = director.build(new LuxuryBuilder());
luxuryBuilding.print();
```

#### Tvorba složitých objektů

Pokud má nějaký objekt mnoho proměnných, existuje několik způsobů, jak se s tím vypořádat. S úspěchem lze použít návrhový vzor Builder. Builder se často vnořuje do vytvářené třídy, aby měl exkluzivní přístup k jejím vnitřním proměnným. Také je zvykem v každé metodě builderu vracet opět jeho instanci, aby bylo možné volání metod řetězit. Použití návrhového vzoru v tomto případě zpřehlední zdrojový kód a v případě rozumného pojmenování metod dává celý kód mnohem větší smysl.

```java
public class Person {
    private String firstName;
    private String lastName;
    private int age;
    
    public static Builder builder() {
        return new Person.Builder();
    }
    
    private static class Builder {
        private final Person result = new Person();
        
        public Builder withName(String firstName, String lastName) {
            result.firstName = firstName;
            result.lastName = lastName;
            return this;
        }
        
        public Builder ofAge(int age) {
            result.age = age;
            return this;
        }
        
        public Person build() {
            return result;
        }
    }
}
```

Použití vypadá takto:

```java
Person person = Person.builder()
    .withName("John Doe")
    .ofAge(value)
    .build();
```

### Reference

- Rudolf Pecinovský: Návrhové vzory