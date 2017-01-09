## JPA (Java Persistence API)

JPA (Java Persistence API) je standard popisující programátorské rozhraní (API) a chování knihoven pro objektově-relační mapování. V současné době existují tyto jeho nejznámnější implementace:

- [Hibernate](http://www.hibernate.org/)
- [EclipseLink](http://www.eclipse.org/eclipselink/)
- [OpenJPA](http://openjpa.apache.org/)
- [DataNucleus](http://www.datanucleus.org/)
- [ObjectDB](http://www.objectdb.com/)

### Entita

Entitou může být každá třída, splňující předepsané podmínky:

- anotace *@Entity* (javax.persistence.Entity)
- alespoň jeden veřejný či chráněný konstruktor bez parametrů
- třída ani její metody nesmí být *final*
- její atributy nejsou veřejné a ke každému existuje **setter** a **getter**

Tato třída se zpravidla ukládá jako jeden řádek v nějaké databázové tabulce (pokud je jako úložiště použita relační databáze).

#### Entita s minimem anotací

```java
@Entity
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    private int age;

    // ...
    // gettery
    // settery
    // ...
}
```

#### Entita s podrobnými anotacemi

```java
@Entity(name = "person")
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name", length = 30, nullable = false)
    private String name;
    @Column(name = "surname", length = 50, nullable = false)
    private String surname;
    @Column(name = "age", precision = 3, scale = 0, nullable = false)
    private int age;
    
    // ...
    // gettery
    // settery
    // ...
}
```

#### Entita s definicí ekvivalence

```java
@Entity
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    
    @Override
    public int hashCode()
    {
        return (id == null) ? 0 : id.hashCode();
    }
    
    @Override
    public boolean equals(Object other) 
    {
      if (other == this) 
      {
        // dvě stejné instance jsou si vždy rovny
        return true;
      }
      
      if (!(other instanceof Person))
      {
        // druhá instance není správného typu nebo je NULL
        // (operátor 'instanceof' povolí i podtřídy)
        return false; 
      }
      
      // pro další zjednodušení se druhá instance přetypuje
      Person otherPerson = (Person) other;
      
      if (this.id == null || otherPerson.id == null)
      {
        // bez obou klíčů nelze rozhodnout o rovnosti instancí
        return false;
      }
      
      if (this.id != otherPerson.id) 
      {
        // primární klíče obou instancí se liší
        return false;
      }
      
      // obě instance jsou osoby se stejným klíčem
      return true;
    }
    
    // ...
    // gettery
    // settery
    // ...
}
```

### Persistenční kontext

Jako persistenční kontext (persistence context) se označuje množina instancí entit, které jsou spravovány jedním správcem entit. Životní cyklus persitenčního kontextu je zpravidla svázán s transakcí (srovnej s Unit of Work). Pochopení smyslu persistenčního kontextu je důležité pro správný vývoj aplikací a korektní řízení transakcí.

V jednom persistenčním kontextu vždy existuje nejvýše jedna instance entity daného typu se stejným primárním klíčem (srovnej s Identity Map). Proto na něj lze pohlížet i jako na určitou formu vyrovnávací paměti (cache), ve které jsou jednotlivé instance entit adresovány svými typy a primárními klíči.

Stav entit v persistenčním kontextu nemusí odpovídat stavu v úložišti. Aby došlo k trvalému uložení všech lokálně provedených změn, musí dojít k tzv. **synchronizaci**, která je vyvolána na konci transakce (při zavolání metody *commit()* na transakci) nebo ručně zavoláním metody *flush()* na správci entit. Během synchronizace může dojít k chybám, které jsou způsobeny například porušením integritních omezení nebo neschopností získat potřebný zámek.

V případě, že potvrzování transakce selže, je sice automaticky provedeno zrušení transakce v databázi (rollback), ale entity se do původního stavu před transakcí nevrací. Je proto nutné je znovu načíst z databáze a provést celou transakci znovu.

Instance entit v persistenčním kontextu se označují jako **spravované** (managed), instance mimo persistenční kontext se označují jako **odpojené** (detached). Odpojené entity nemá správce entit pod kontrolou a neprovádí s nimi žádné operace. Uchovávají si ale svou identitu a je proto jednoduché je znovu načíst (například dle primárního klíče).

### Správa entit

O správu entit a jejich životní cyklus (tedy o načítání, ukládání, mazání a obnovování) se stará třída implementující rozhraní *EntityManager*. Toto rozhraní předepisuje následující klíčové metody:

- *find* - načte entitu se zadaným klíčem z úložiště do kontextu
- *refresh* - obnoví entitu v kontextu dle úložiště
- *persist* - přidá entitu do kontextu
- *merge* - upraví entitu v úložišti dle kontextu
- *remove* - odebere entitu z úložiště
- *detach* - odebere entitu z kontextu

Libovolná implementace tohoto rozhraní bude v textu dále označována jako **správce entit**.

#### Životní cyklus entity

Po vytvoření nové instance správce entit je persistenční kontext prázdný. Pomocí dotazů (viz dále) a metodou *find* je možné do kontextu načíst aktuální entity z databáze, metodou *persist* je do kontextu vložena nová (lokálně vytvořená) instance entity. Poté je možné entity libovolně modifikovat pomocí setterů. Metodou *refresh* je možné aktualizovat instanci entity v kontextu a zrušit tak její případné neuložené změny. Metoda *remove* označí entitu v kontextu jako určenou ke smazání, metoda *detach* ji odstraní z persistenčního kontextu (nikoliv z databáze). 

```dot:digraph
ratio = 0.5;
rankdir = LR;
New;
Managed [style = filled, color = beige, fillcolor = beige];
Removed;
Detached;
Database [width=1.5, height=2, color = forestgreen];
nodesep = 1;
new [style = invis, label = ""];
Database [shape = box3d];
new -> New [label = "new"];
New -> Managed [label = "persist"];
Managed -> Detached [label = "clear/close"];
Managed -> Removed [label = "remove"];
Removed -> Managed [label = "persist"];
Removed -> Detached [label = "clear/close"];
Removed -> Database [label = "flush/commit", color = forestgreen, style = dashed];
Managed -> Database [label = "flush/commit", color = forestgreen, style = dashed];
Database -> Managed [label = "find/query", color = forestgreen, style = dashed];
```

#### Transakce

```java
// vytvořit několik instancí entit

Company company = new Company();
company.setName("SuperTech a.s.");

Employee employee = new Employee();
employee.setName("Jan");
employee.setSurname("Novák");

// vytvořit relaci (nutno svázat obě strany)

company.addEmployee(employee);

// TRANSAKCE
// =========

EntityManager em = emf.createEntityManager();

em.getTransaction().begin();

// kontext je prázdný

em.persist(company);

// kontext: {company}

em.persist(employee);

// kontext: {company, employee}

em.getTransaction().commit();

// kontext byl vyprázdněn, 
// instance "company" a "employee" jsou odpojené (detached)
```

#### Doporučení

Na každou transakci se doporučuje vytvořit nového správce entit, aby se v něm při chybách nehromadily nepotřebné entity a nezpůsobovaly tak zbytečné memory leaky. Ihned po vytvoření správce entit je nanejvýš vhodné zahájit transakcí a po jejím potvrzení či zrušení správce entit uzavřít příkazem *close()*. Po uzavření již není možné správce entit používat (dojde k výjimce) a tak je při ladění programu nalezeno místo, kde se se správcem pracuje mimo povolenou transakci.

### Vazby

#### 1:1 (one-to-one)

Příklady:

- **občan - pas**: jeden občan má právě jeden pas

```java
/**
 * Občan s pasem.
 * @author Vojtěch Hordějčuk
 */
@Entity(name = "citizen")
@Table(name = "citizen")
public class Citizen {
  /**
   * ID občana
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "citizen_id", nullable = false)
  private Long id;
  /**
   * občanův pas
   */
  @OneToOne
  @JoinColumn(name="passport_number")
  private Passport passport;
}
```

#### 1:N (one-to-many)

Příklady:

- **ruka - prst**: jedna ruka má pět prstů

```java
/**
 * Krabice, která může obsahovat předměty.
 * @author Vojtěch Hordějčuk
 */
@Entity(name = "box")
@Table(name = "box")
public class Box implements Serializable
{
  /**
   * ID krabice
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "box_id", nullable = false)
  private Long id;
  /**
   * obsah krabice = položky
   */
  @OneToMany(mappedBy = "location")
  @JoinColumn(name = "contents", nullable = false)
  private List<Item> contents;

  // ...
}
```

#### N:1 (many-to-one)

Příklady:

- **adresa - osoba**: na jedné adrese může žít více osob

```java
/**
 * Předmět, který může být umístěn v krabici.
 * @author Vojtěch Hordějčuk
 */
@Entity(name = "item")
@Table(name = "item")
public class Item implements Serializable
{
  /**
   * ID předmětu
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "item_id", nullable = false)
  private Long id;
  /**
   * krabice, ve které se předmět nachází
   */
  @ManyToOne(optional = true)
  @JoinColumn(name = "box_ref", nullable = true)
  private Box location;

  // ...
}
```

#### N:N (many-to-many)

Příklady:

- **kniha - autor**: kniha může mít více autorů a každý autor mohl napsat více knih

```java
/**
 * Projekt. Na projektu může pracovat více zaměstnanců.
 * @author Vojtěch Hordějčuk
 */
@Entity(name = "project")
@Table(name = "project")
public class Project implements Serializable
{
  /**
   * ID projektu
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "project_id", nullable = false)
  private Long id;
  /**
   * přiřazení zaměstnanci
   */
  @ManyToMany(mappedBy = "projects")
  private List<Employee> employees;
}
```

```java
/**
 * Zaměstnanec. Může pracovat na několika projektech.
 * @author Vojtěch Hordějčuk
 */
@Entity(name = "employee")
@Table(name = "employee")
public class Employee implements Serializable
{
  /**
   * jméno zaměstnance
   */
  @Id
  @Column(name = "employee_name", nullable = false)
  private String name;
  /**
   * seznam projektů
   */
  @ManyToMany
  @JoinTable(name = "assignment",
  joinColumns =
  {
    @JoinColumn(name = "employee_ref", referencedColumnName = "employee_name", nullable = false)
  },
  inverseJoinColumns =
  {
    @JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false)
  })
  private List<Project> projects;
}
```

### Konfigurace persistenční jednotky

Persistenční jednotka obsahuje konfiguraci připojení k databázi, jeho parametry a konkrétní knihovnu, která bude k tomuto účelu bude využita jako implementace JPA. Jednotka se konfiguruje ve speciálním XML souboru, který je umístěn v adresáři **.../src/META-INF/persistence.xml**. Následuje ukázkový konfigurační soubor pro databázi MySQL a knihovnu EclipseLink:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.0" xmlns="http://java.sun.com/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd">
  <persistence-unit name="voho" transaction-type="RESOURCE_LOCAL">
    <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
    <shared-cache-mode>NONE</shared-cache-mode>
    <properties>
      <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/DBNAME"/>
      <property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver"/>
      <property name="javax.persistence.jdbc.user" value="USER"/>
      <property name="javax.persistence.jdbc.password" value="PASSWORD"/>
      <property name="eclipselink.ddl-generation" value="create-tables"/>
    </properties>
  </persistence-unit>
</persistence>
```

### Správce entit

Správce entit lze získat různými způsoby. Aplikace postavené na technologii Java EE umožňují například tzv. **dependency injection**. Pokud tento postup není dostupný či vítaný, je tu ještě druhá možnost:

```java
String pu = "název persistenční jednotky";
EntityManagerFactory emf = Persistence.createEntityManagerFactory(pu);
EntityManager em = emf.createEntityManager();
```

Persistenční jednotka je definovaná v souboru **persistence.xml**.

### Reference

- https://en.wikibooks.org/wiki/Java_Persistence/OneToOne
- http://docs.oracle.com/javaee/5/tutorial/doc/bnbpy.html
- http://openjpa.apache.org/builds/1.0.4/apache-openjpa-1.0.4/docs/manual/jpa_overview_emfactory_perscontext.html
- http://docs.jboss.org/hibernate/core/4.0/hem/en-US/html/architecture.html#architecture-ejb-persistctxscope
- http://jcp.org/aboutJava/communityprocess/final/jsr220/index.html
- http://openjpa.apache.org/builds/1.0.4/apache-openjpa-1.0.4/docs/manual/jpa_overview_em_lifecycle.html
- http://www.objectdb.com/java/jpa/persistence/managed
- http://java.boot.by/scbcd5-guide/ch06.html