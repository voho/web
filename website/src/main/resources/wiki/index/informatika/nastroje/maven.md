## Maven

Maven je rozšířený systém pro správu a sestavování aplikací postavených nad platformou Java. Jeho využitím odpadá závislost na konkrétním IDE, protože všechny informace potřebné ke kompilaci a sestavení programu jsou přímo obsaženy ve speciálních souborech *pom.xml* (POM = project object model). Systém Maven je již plně integrován do všech velkých IDE (Eclipse, Netbeans, IDEA) a tak je práce s ním opravdu velmi snadná i přes uživatelské rozhraní.

### Koncept

Principem systému Maven je vytvoření objektového modelu nad zdrojovým kódem, se kterým lze provádět různé operace. Nejčastěji se jedná o kompilaci, kontrolu, vytvoření balíků, atd. Model projektu je definován v souborech *pom.xml*, které se nachází v kořenovém adresáři každého projektu. V těchto adresářích lze spouštět příkazy *mvn* s parametry, které načtou model z odpovídajícího souboru *pom.xml* a provedou zadanou akci.

Klíčovou funkcí systému Maven je řešení závislostí. To znamená, že není nutné ručně kopírovat knihovny (JAR, WAR, EAR) a umisťovat je na classpath. Zvláště u velkých projektů složených z mnoha podprojektů je to neocenitelné zpřehlednění a zjednodušení vývoje.

#### Projekt

Každý soubor *pom.xml* představuje jeden **projekt** (v hantýrce Maven **artefakt**). Artefakt je jednoznačně identifikován skupinou (groupId), názvem (artifactId) a verzí (version). 

#### Repositář

Repositář systému Maven obsahuje katalog artefaktů a systém pro jejich vyhledávání a stahování. Repositáře jsou **lokální** a **vzdálené**. Lokální repositář je vždy přítomen na vašem lokálním počítači a cachují se v něm použité závislosti. Není tedy nutné při každé kompilaci stahovat balíčky ze vzdáleného repositáře. Výchozím centrálním repositáře je http://search.maven.org/, ale konfigurací Mavenu či POM lze nastavit jiný (například firemní či týmový).

#### Závislosti (Dependencies)

Závislostmi jsou myšlené jiné artefakty spravované systémem Maven, které nějaký artefakt vyžaduje ke své kompilaci či funkci. Závislosti mají tzv. *scope*, který specifikuje míru a okamžik potřeby dané závislosti. Scope může nabývat následujících hodnot:

- *compile* (výchozí) = artefakt je vyžadován pro kompilaci i běh aplikace (artefakt bude dostupný na všech classpath)
- *test* = artefakt je vyžadován pouze pro kompilaci a spuštění unit testů
- *runtime* = artefakt není vyžadován při komplikaci, ale je vyžadován za běhu aplikace (nejčastěji implementace různých API)
- *provided* = artefakt je vyžadován pro komplikaci i běh, ale bude poskytnut JVM za běhu (nejčastěji různé knihovny přibalené v aplikačním serveru)
- *system* = podobný jako *provided*, ale je nutné ručně uvést cestu k zadanému artefaktu
- *import*

##### Příklad

```xml
<dependency>
  <groupId>somegroup</groupId>
  <artifactId>someartifact</artifactId>
  <version>1.0</version>
  <scope>provided</scope>
</dependency>
```

#### Spravované závislosti (Managed Dependencies)

Správa závislostí se používá ve vícemodulových projektech a slouží ke sjednocení použitých verzí. Nadřazený projekt například může specifikovat verzi určité závislosti a podřazené projekty tuto verzi již uvádět nemusí (avšak mohou ji přebít, pokud chtějí). 

##### Příklad

Nadřazený projekt:

```xml
<dependencyManagement>
  <dependency>
    <groupId>somegroup</groupId>
    <artifactId>someartifact</artifactId>
    <version>1.0</version>
    <scope>provided</scope>
  </dependency>
</dependencyManagement>
```

Podřazený projekt:

```xml
<dependency>
  <groupId>somegroup</groupId>
  <artifactId>someartifact</artifactId>
  <!-- není nutné specifikovat verzi -->
</dependency>
```

#### Výjimky ze závislostí (Dependency Exclusions)

Pokud projekt obsahuje závislost na dalším artefaktu, může explicitně zakázat některé jeho tranzitivní závislosti.

### Soubor pom.xml

Soubor *pom.xml* (POM = project object model) je XML soubor obsahující model jednoho artefaktu (projektu), který obsahuje například závislosti artefaktu a jeho návaznosti na jiné artefakty.

#### Příklady

##### Jednoduchý projekt se závislostí

```xml
<project 
  xmlns="http://maven.apache.org/POM/4.0.0" 
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  
  <modelVersion>4.0.0</modelVersion>  
  <groupId>cz.voho</groupId>
  <artifactId>myproject</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <packaging>jar</packaging>
  <name>My Project</name>
  <description>Sample project with a dependency</description>
  <url>http://someproject.com/</url>
  <inceptionYear>2000</inceptionYear>
  
  <dependencies>
    <dependency>
      <groupId>com.company</groupId>
      <artifactId>somelibrary</artifactId>
      <version>1.2.3</version>
    </dependency>
  </dependencies>
  
</project>
```

##### Hlavní projekt s moduly

```xml
<project 
  xmlns="http://maven.apache.org/POM/4.0.0" 
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

  <modelVersion>4.0.0</modelVersion>
  <groupId>cz.voho</groupId>
  <artifactId>myproject</artifactId>
  <version>1.0.0-SNAPSHOT</version>
  <packaging>pom</packaging>
  <name>My Project</name>
  <description>Sample parent project with modules</description>
  <url>http://someproject.com/</url>    
  <inceptionYear>2000</inceptionYear>
  
  <modules>
    <module>module1</module>
    <module>module2</module>
    <module>module3</module>
  </modules>
  
</project>
```

### Spuštění kompilace

Maven se spouští příkazem *mvn* v adresáři, který obsahuje soubor *pom.xml*. Za příkazem *mvn* následují tzv. **goaly** a **parametry**.

```bash
mvn [goaly] [parametry]
```

Nejčastěji používané parametry:

- *-U* = aktualizovat snapshoty (nahraje z repositáře jejich aktuální verze)
- *-O* = offline režim (používá pouze artefakty z lokálního repositáře)
- *-DskipTests=true* = přeskočí unit testy
- *-T4* = použít 4 vlákna

#### Příklady

Smazat všechny výstupní soubory (clean), zkompilovat a instalovat projekt do lokálního repositáře (install) a přitom aktualizovat všechny závislosti, jejichž verze jsou snapshoty (-U). To všechno provést ve čtyřech vláknech (-T4):

```bash
mvn clean install -U -T4
```

Příklad použití:

```bash
mvn clean install -UOT4
```

Vypsat seznam závislostí, které mají v repositáři novější verzi:

```bash
mvn versions:display-dependency-updates
```

Vypsat seznam Maven pluginů, které mají v repositáři novější verzi:

```bash
mvn versions:display-plugin-updates 
```

Aktualizovat všechny závislosti na další (next) či poslední verze (latest):

```bash
mvn versions:use-next-releases
mvn versions:use-latest-releases
mvn versions:use-next-snapshots
mvn versions:use-latest-snapshots
```

Vypsat nepoužívané a tranzitivní závislosti:

```bash
mvn dependency:analyze
```

### Běžné případy užití

#### Webové služby JAX-WS

Plugin JAX-WS se používá pro vytváření klientů webových služeb a generování souvisejícího zdrojového kódu dle souboru WSDL. Nejprve je třeba přidat závislost na Java EE web API:

```java
<dependencies>
  <dependency>
    <groupId>javax</groupId>
    <artifactId>javaee-web-api</artifactId>
    <version>XXX</version>
    <scope>provided</scope>
  </dependency>
</dependencies>
```

Poté je již možné konfigurovat plugin a jeho spuštění při kompilaci. Následující příklad připraví kód pro provolání vzdálené webové služby.

```xml
<plugin>
  <groupId>org.jvnet.jax-ws-commons</groupId>
  <artifactId>jaxws-maven-plugin</artifactId>
  <version>2.2</version>
  <executions>
    <execution>
      <goals>
        <goal>wsimport</goal>
      </goals>
      <configuration>
        <wsdlFiles>
          <wsdlFile>www.webservicex.net/EMBLNucleotideSequenceWebService.asmx.wsdl</wsdlFile>
        </wsdlFiles>
        <wsdlLocation>http://www.webservicex.net/EMBLNucleotideSequenceWebService.asmx?WSDL</wsdlLocation>
      </configuration>
      <id>wsimport-generate-EMBLNucleotideSequenceWebService.asmx</id>
      <phase>generate-sources</phase>
    </execution>
  </executions>
  <dependencies>
    <dependency>
      <groupId>javax.xml</groupId>
      <artifactId>webservices-api</artifactId>
      <version>1.4</version>
      </dependency>
    </dependencies>
  <configuration>
    <xnocompile>true</xnocompile>
    <verbose>true</verbose>
    <extension>true</extension>
  </configuration>
</plugin>
```

Do sekce *configuration* je dále možné přidat tyto tagy:

- **packageName** - obsahuje název balíčku, ve kterém budou vygenerované soubory umístěny
- **encoding** - název kódování generovaných souborů (např. utf-8)
- **verbose** - zapne (true) či vypne (false) podrobné logování generátoru

#### Plugin pro spouštění skriptů

```xml
<plugin>
  <artifactId>exec-maven-plugin</artifactId>
  <groupId>org.codehaus.mojo</groupId>
  <executions>
    <execution>
      <id>my script execution</id>
      <phase>generate-sources</phase>
      <goals>
        <goal>exec</goal>
      </goals>
      <configuration>
        <executable>${basedir}/scripts/script.sh</executable>
      </configuration>
    </execution>
  </executions>
</plugin>
```

#### Spustitelný JAR

Toto musí být v *pom.xml*. Potom lze vytvořit spustitelný balíček se závislostmi příkazem *mvn package assembly:single*.

```xml
<build>
  <plugins>
    <plugin>
      <artifactId>maven-assembly-plugin</artifactId>
      <configuration>
        <archive>
          <manifest>
            <mainClass>cz.voho.example.YourMainClass</mainClass>
          </manifest>
        </archive>
        <descriptorRefs>
          <descriptorRef>jar-with-dependencies</descriptorRef>
        </descriptorRefs>
      </configuration>
    </plugin>
  </plugins>
</build>
```

### Reference

- http://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html
- http://mojo.codehaus.org/versions-maven-plugin/