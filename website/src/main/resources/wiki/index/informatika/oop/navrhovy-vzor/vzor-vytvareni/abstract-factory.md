## GoF: Abstract Factory (abstraktní továrna)

Návrhový vzor abstract factory je návrhovým vzorem pro strukturu objektů. Používá se v situacích, kdy je z nějakých dobrých důvodů vhodné delegovat vytváření nové instance třídy (tzv. produktu) na dedikovanou třídu (tzv. tovární třídu). Důvody mohou být různé - například je logika pro volbu konkrétní vytvářené třídy složitá, opakuje se na více místech, nebo ji chce továrna zkrátka "skrýt" a odstínit tak klienta od nepodstatných detailů. Tovární třída obsahuje jednu nebo více [továrních metod](wiki/factory-method).

Obecně lze mít rozsáhlé hierarchie produktů i továrních tříd, přičemž klient nemůže očekávat žádnou konkrétní implementaci továrny či produktu, musí mu stačit jen to nejobecnější rozhraní od každého. Všechny možné produkty tovární třídy mají společného předka a klient spoléhá pouze na to, že mu bude vrácen jeden z potomků této třídy. Dopředu neví, který to bude - logiku pro jeho výběr zná pouze tovární třída. To umožňuje klientovi se všemi možnými produkty pracovat stejně. Klient má k dispozici pouze rozhraní jejich abstraktního předka a neměl by mít žádná očekávání ohledně tovární třídy, která bude pro vytvoření instancí použita.

### UML diagramy

```uml:class
class Factory <<interface>> {
  createProduct(): Product
}

class Product <<interface>> {
}

Client -> Factory
Client ..> Product
note left on link: client demands products
ConcreteFactory1 .up.|> Factory
ConcreteProduct1 .up.|> Product
ConcreteFactory2 .up.|> Factory
ConcreteProduct2 .up.|> Product
```

### Příklad

#### Rozhraní produktů

```java
/**
 * Obecný dopravní prostředek.
 *
 * @author Vojtěch Hordějčuk
 */
public interface Vehicle
{
  // ...
}
```

#### Konkrétní produkty

```java
/**
 * Osobní automobil.
 *
 * @author Vojtěch Hordějčuk
 */
public class Car implements Vehicle
{
  // ...
}

/**
 * Nákladní vůz.
 *
 * @author Vojtěch Hordějčuk
 */
public class Truck implements Vehicle
{
  // ...
}

/**
 * Malá loďka.
 *
 * @author Vojtěch Hordějčuk
 */
public class Boat implements Vehicle
{
  // ...
}

/**
 * Trajekt.
 *
 * @author Vojtěch Hordějčuk
 */
public class Ferry implements Vehicle
{
  // ...
}
```

#### Rozhraní továrních tříd

```java
/**
 * Rozhraní tovární třídy pro vytváření vozidel.
 *
 * @author Vojtěch Hordějčuk
 */
public interface VehicleFactory
{
  /**
   * Vytvoří malé vozidlo.
   *
   * @return malé vozidlo
   */
  public Vehicle createSmallVehicle();

  /**
   * Vytvoří velké vozidlo.
   *
   * @return velké vozidlo
   */
  public Vehicle createBigVehicle();
}
```

#### Tovární třídy

```java
/**
 * Tovární třída, která vytváří instance automobilů.
 *
 * @author Vojtěch Hordějčuk
 */
public class CarFactory implements VehicleFactory
{
  @Override
  public Vehicle createBigVehicle()
  {
    return new Truck();
  }

  @Override
  public Vehicle createSmallVehicle()
  {
    return new Car();
  }
}

/**
 * Tovární třída, která vytváří instance lodí.
 *
 * @author Vojtěch Hordějčuk
 */
public class ShipFactory implements VehicleFactory
{
  @Override
  public Vehicle createBigVehicle()
  {
    return new Ferry();
  }

  @Override
  public Vehicle createSmallVehicle()
  {
    return new Boat();
  }
}
```

#### Použití

```java
public static void main(String[] args)
{
  // vytvořit tovární třídy

  VehicleFactory carFactory = new CarFactory();
  VehicleFactory shipFactory = new ShipFactory();

  // požádat tovární třídu o vytvoření produktů

  Vehicle smallCar = carFactory.createSmallVehicle();
  Vehicle bigCar = carFactory.createBigVehicle();
  Vehicle smallShip = shipFactory.createSmallVehicle();
  Vehicle bigShip = shipFactory.createBigVehicle();
}
```

### Reference

- http://sourcemaking.com/design_patterns/abstract_factory
