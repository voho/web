## Logback

Logback je implementace logovacího rozhraní *SLF4J* pro platformu [Java](wiki/java), která se konfiguruje pomocí XML souborů. Umožňuje vytvářet tzv. **loggery**, což jsou objekty, do kterých se posílají vlastní logované zprávy. Úroveň a argumenty zprávy jsou pak vyhodnoceny a následně je zpráva podle konfigurace rozeslána do tzv. **appenderů**, které zajistí zpracování zprávy, nejčastěji provedou její výpis na standardní výstup nebo do zadaného souboru. 

Každý logger má unikátní jednoznačný název a je podle něho zařazen do hierarchie, jejímž kořenem je speciální předpřipravený logger, tzv. **root logger**. Jako oddělovač úrovní hierarchie slouží tečka v názvu loggeru, podobně jako to mají balíčky v Javě. Pokud se logger nazývá například *a.b.c.x*, je na stejné úrovni jako *a.b.c.y*. Nadřazeným loggerem těchto dvou jsou loggery  *a.b.c*, dále *a.b*, *a*, **root logger**.

### Závislost (Maven)

```xml
<dependency>
  <groupId>ch.qos.logback</groupId>
  <artifactId>logback-classic</artifactId>
  <version>X.X.X</version>
</dependency>
```

### Vytvoření loggeru

Logback se používá zpravidla tak, že se ve třídě pomocí [tovární třídy](wiki/abstract-factory) *javadoc:org.slf4j.LoggerFactory* vytvoří privátní statická konstanta typu *javadoc:org.slf4j.Logger*, pomocí které se pak do loggeru přidávají zprávy pocházející z dané třídy. Název loggeru může být libovolný, ovlivňuje však zásadně postavení loggeru v hierarchii a tudíž i jeho konfiguraci.

### Použití

Na rozhraní *javadoc:org.slf4j.Logger* se nachází metody pojmenované podle úrovní logování. Úrovní logování je myšlena závažnost či důležitost dané zprávy. Pokud například dojde k chybě, je to zásadnější než informativní hláška o úspěšně provedené operaci. Úrovně logování od nejméně zásadní až po tu nejdůležitější jsou tyto: *trace*, *debug*, *info*, *warn*, *error*.

```java
public class LoggingClass {
    private static final Logger LOG = LoggerFactory.getLogger(LoggingClass.class);
    
    public void test() {
        LOG.debug("we will tell you the current time");
        String name = "John";
        String time = new Date().toString();
        LOG.info("{} says: the current time is {}", name, time);
    }
}
```

### Konfigurace

Logback hledá konfigurace v následujícím pořadí: *logback-test.xml*, *logback.xml*. To umožňuje v unit testech používat jiné nastavení logování než při běžném provozu aplikace (pouze je třeba zajistit, aby byl soubor *logback-test.xml* přítomný na classpath pouze v době testů, například [Maven](wiki/maven) toto zajišťuje automaticky).

#### Jednoduchá konfigurace (konzole)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%date %level %logger{10} %msg%n</pattern>
        </encoder>
    </appender>

    <root level="info">
        <appender-ref ref="console"/>
    </root>
</configuration>
```

#### Složitější konfigurace (konzole + soubor)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

    <!-- name of a directory where the application logs are stored including history -->
    <property name="log_app_dir" value="my_application"/>

    <!-- must contain the log_base_dir property -->
    <property file="setup.properties"/>

    <appender name="file" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${log_base_dir}${log_app_dir}/log.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.FixedWindowRollingPolicy">
            <FileNamePattern>${log_base_dir}${log_app_dir}/log_%i.log</FileNamePattern>
            <MinIndex>1</MinIndex>
            <MaxIndex>9</MaxIndex>
        </rollingPolicy>
        <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
            <MaxFileSize>10MB</MaxFileSize>
        </triggeringPolicy>
        <append>true</append>
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <pattern>%d %p [%c] - %m%n</pattern>
        </encoder>
    </appender>
	
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%date %level %logger{10} %msg%n</pattern>
        </encoder>
    </appender>

    <!-- default root level is debug, but we override it to info -->
    <root level="info">
        <appender-ref ref="file"/>
	<appender-ref ref="console"/>
    </root>

    <!-- I want to override logging of my package to debug -->
    <logger name="com.mycompany.myapp" level="debug"/>

</configuration>
```

### Názvosloví chyb

* **Failure** použijte, pokud selhala externí závislost, například databáze nebo služba
* **Fault** použijte, nastala-li neočekávaná chyba v kódu, například se program dostal do neplatného stavu
* **Error** použijte, pokud chybu udělal například člověk a způsobil tím chybu v kódu (fault)

### Úroveň logování

- **ERROR** použijte, pokud nastal nebo brzy nastane vážný problém, který vyžaduje rychlý lidský zásah
- **WARN** použijte, pokud nastala nějaká neočekávaná situace, ale není vyžadován rychlý lidský zásah
- **INFO** použijte, pokud daná informace bude užitečná například při ladění problémů - typicky se logují méně časté situace, jako například životní cykly aplikací a komponent
- **DEBUG** použijte pro zbytek situací, například pro oznámení vstupu/výstupu z netriviálních veřejných metod
- **TRACE** se prakticky nepoužívá, ale lze využít pro detailní ladění, například pro výpis rozsáhlého objektu nebo každé iterace v dlouhém cyklu

### Reference

- http://logback.qos.ch/manual/index.html
