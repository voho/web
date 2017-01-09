## Jazyk Java 8

Java je objektově orientovaný jazyk, takže se program skládá ze vzájemně komunikujících objektů. Objekty spolu komunikují pouze přes své veřejné rozhraní - jeden objekt může využívat veřejného rozhraní objektu druhého. Programátor v jazyce Java definuje strukturu a chování těchto objektů. 

Základní úlohou při programování v jazyku Java je tedy definice **struktury** a **chování** objektů. Struktura objektu je dána strukturou jeho **vnitřního stavu**, tedy množinou proměnných, jejichž životní cyklus je svázaný s životním cyklem **třídy** či její **instance**. Tyto proměnné se označují jako **atributy**. Chování objektu je dáno posloupnostmi příkazů, které jsou v něm obsaženy. Objekt v sobě obsahuje pojmenované posloupnosti příkazů (bloky) s parametry, které se označují jako **metody**. Každá metoda vykonává posloupnost příkazů a k dispozici má všechny **atributy** i **metody** daného objektu (včetně metod objektů vnořených v atributech) a své **parametry**, což jsou proměnné dodané externě.

### Základní rysy jazyka

Protože byl jazyk Java od počátku zamýšlen jako multiplatformní, je to jazyk interpretovaný. Zdrojový kód se tedy nekompiluje do strojového kódu procesoru počítače, ale do instrukcí virtuálního zásobníkového procesoru JVM (Java virtual machine), tzv. **bytekódu**. Implementace JVM mohou být různé a existují pro všechny rozšířené platformy (Windows, Linux, Mac OS, FreeBSD, atd.). Stejný kód lze tedy spustit na všech JVM stejně, a to nezávisle na platformě. V některých speciálních případech samozřejmě lze narazit na drobné rozdíly, ale podpora Javy je vesměs velmi dobrá. V jazyce Java se programují i televize, set-top bosy, ledničky, mobilní zařízení (Android) a tak dále.

### Struktura programu

Program v jazyce Java se skládá ze **tříd**. Třída představuje schéma, podle kterého se vytváří její **instance**. Je to podobné, jako když se razítko otiskne na papír. Razítko je třída, zatímco jeho otisk na papíře je instance této třídy. Změnou razítka se změní i jeho další otisky, ale 

změnou otisků není nijak ovlivněno razítko. Z otisku také lze zjistit, jakým razítkem byl natištěn. Podobně lze i u instance zjistit, z jaké třídy pochází.

Spuštění celého programu začíná tak, že je na předem určeném objektu (tzv. *main class*) zavolána metoda s názvem *main*, které jsou předány parametry z příkazového řádku. Co objekt vykoná potom už je zcela v jeho režii.

### Komentáře

```java
// jednořádkový komentář
/* víceřádkový komentář */
/** Javadoc */
```

### Proměnné

Každá proměnná je dána svým **názvem** a **typem**. Název proměnné musí být v daném kontextu unikátní. K deklaraci proměnné lze přidat inicializaci na výchozí hodnotu a různé modifikátory. Proměnné se obecně deklarují podle následujícího schématu:

```plain
[modifikátory] typ název [= hodnota]
```

Několik příkladů:

```java
// proměnná typu 'int' s názvem 'counter' inicializovaná na hodnotu '0'
int counter = 0;
```

```java
// proměnná typu 'String' s názvem 'text' s modifikátorem 'final' inicializovaná na hodnotu 'Hello'
final String text = "Hello";
```

```java
// proměnná typu 'List<String>' s názvem 'data' s modifikátory 'private' a 'final'
private final List<String> data;
```

#### Modifikátory

| Modifikátor | Význam
|---|---
| private | atribut je přístupný pouze z instance dané třídy
| protected | atribut je přístupný pouze z instance dané třídy nebo jejího potomka
| public | atribut je přístupný ze všech objektů
| volatile | zákaz uchovávání proměnné v registrech - bezpečnější při současném přístupu z více vláken
| transient | atribut nemá být serializován (při přenosu po síti, atd.)
| final | hodnotu proměnné je možné nastavit jen jednou (pak ji již nelze měnit)
| static | proměnná není vázaná na instanci, ale na třídu, takže všechny instance dané třídy sdílí jedinou hodnotu

#### Primitivní datové typy

Ačkoliv je jazyk Java objektově orientovaný, jsou v něm z výkonnostních důvodů k dispozici i "holé" neobjektové datové typy (nejčastěji číselné), tzv. **primitivní datové typy**. Tyto typy nejsou objekty samy o sobě, ale ke každému z nich existuje i tzv. **obalová třída**, která prostou hodnotu "obalí" metodami pro práci s ní, jako jsou různé konverze a podobně. Aby bylo využití primitivních datových typů jednoduché, jsou v kódu primitivní typy zaměnitelné s jejich obalovými třídami. Převod z obalové třídy na primitivní typ se nazývá **unboxing**, opačný proces jako **boxing**. Tyto operace může automaticky provádět kompilátor.

| Datový typ | Druh | Velikost | Kódování | Obalová třída | Výchozí hodnota
|---|---|---|---|---|---
| byte | celočíselný | 8 bitů | dvojkový doplněk, se znaménkem | Byte | 0
| short | celočíselný | 16 bitů | dvojkový doplněk, se znaménkem | Short | 0
| int | celočíselný | 32 bitů | dvojkový doplněk, se znaménkem | Integer | 0
| long | celočíselný | 64 bitů | dvojkový doplněk, se znaménkem | Long | 0
| float | reálný | 32 bitů | IEEE 754 | Float | 0.0
| double | reálný | 64 bitů | IEEE 754 | Double | 0.0
| boolean | logický | N/A, počítá se jako 1 bit | N/A | Boolean | false
| char | znakový | 16 bitů | N/A | Character | 0

#### Pole

Z každého datového typu lze jednoduše vytvořit i pole, a to tak, že se za jeho název přidají hranaté závorky *[]*. Pole je zpočátku nastaveno na hodnotu *null*. Před jeho použitím je jej nutné inicializovat a rezervovat potřebnou paměť. Potom je k jeho prvkům možné přistupovat přes platný index (0 až jeho velikost - 1). Oboje ukazuje následující zdrojový kód:

```java
// array with size of 10 (ten int values next to each other)
int[] array = new int[10];

// accessing array elements by index
array[0] = 42;
array[5] = 87;

// error - index out of range
array[-5] = 11;
array[555] = 7;
```

Po inicializaci pole mají všechny jeho elementy iniciální hodnotu.

### Metody

Metoda je dána svou **signaturou**, což je kombinace **názvu**, **návratové hodnoty** a **typů parametrů**. K deklaraci metody lze přidat seznam výjimek které mohou při provádění metody vzniknout a různé modifikátory. Metody se obecně deklarují podle následujícího schématu:

```plain
[modifikátory] typ název ([parametry]) [výjimky] {
	tělo metody
}
```

Metoda se může v libovolném okamžiku ukončit příkazem *return* a vrácením návratové hodnoty nebo vytvořením výjimky příkazem *throw*.

Několik příkladů:

```java
/*
metoda s názvem 'sum'
	- se dvěma parametry 'a', 'b' typu 'int'
	- s návratovým typem 'int'
	- modifikátory: 'private'
	- výjimky: žádné
*/
private int sum(int a, int b) {
	return a + b;
}
```

```java
/*
metoda s názvem 'getMessage'
	- bez parametrů
	- s návratovým typem 'String'
	- modifikátory: 'public', 'static'
	- výjimky: žádné
*/
public static final String getMessage() {
	return "Hello world!";
}
```

```java
/* 
metoda s názvem 'printShortMessage'
	- bez parametrů
	- bez návratové hodnoty (typ void)
	- modifikátory: 'public', 'static'
	- výjimky: možný výskyt výjimky MessageTooLongException
*/
public static void printShortMessage(String message) throws MessageTooLongException {
	if (message.length > 160) {
		throw new MessageTooLongException();
	}
	
	System.out.println(message);
}
```

#### Modifikátory

| Modifikátor | Význam
|---|---
| private | metoda je přístupná pouze z instance dané třídy
| protected | metoda je přístupná pouze z instance dané třídy nebo jejího potomka
| public | metoda je přístupná ze všech objektů
| final | metodu není možné překrýt v potomcích dané třídy
| static | metoda není vázaná na instanci, ale na třídu, takže je stejná pro všechny instance, nelze ji v podtřídě změnit a nelze v ní použít klíčové slovo *this*
| abstract | abstraktní metoda neobsahuje tělo, pouze svou hlavičku - abstraktní metoda se může vyskytovat jen v abstraktní třídě a proto první ne-abstraktní podtřída musí tělo doplnit
| synchronized | do metody může pro každou instanci třídy vstoupit pouze jedno vlákno
| native | metoda je implementována v jiném jazyce (např. C, C++), například systémová volání

### Třídy

Třída je schématem pro vytváření instancí. Třída se skládá z **dat** (atributy) a **operací** (metody). Třída se deklaruje podle následujícího schématu:

```plain
[modifikátory] class NÁZEV [extends NÁZEV] [implements NÁZEV] {
	[atributy]
	[metody]
}
```

Několik příkladů:

```java
// abstract class with one abstract method

public abstract class Animal {
	abstract public String sound();
}

// concrete sub-class implementing that method

public class Dog extends Animal {
	@Override
	public String sound() {
		return "bark";
	}
}
```

```java
// abstract class with unimplemented abstract methods specified by interface

public abstract class Calculator implements Adding, Subtracting {
	// empty
}
```

```java
// concrete class implementing two interfaces

public class Calculator implements Adding, Subtracting {
	@Override
	public int plus(int a, int b) {
		return a + b;
	}
	
	@Override
	public int minus(int a, int b) {
		return a - b;
	}
}
```

#### Modifikátory

| Modifikátor | Význam
|---|---
| private | třída je přístupná pouze z instance dané třídy
| protected | tířda je přístupná pouze z instance dané třídy nebo jejího potomka
| public | třída je přístupná ze všech objektů
| final | od třídy není možné dědit
| static | v případě deklarace třídy ve třídě tato vnitřní třída není vázaná na instanci vnější třídy
| abstract | třída označená tímto klíčovým slovem je tzv. **abstraktní** a není možné vytvářet její instance - nemusí to však platit pro její potomky, kteří abstraktní být nemusí (opakem abstraktní třídy je **konkrétní** třída)

### Rozhraní

Rozhraní se dá chápat jako specifikace funkčnosti nějaké třídy. Deklarace rozhraní je podobná jako deklarace třídy, ale z principu zde mohou být definovány jen operace, rozhraní nemůže mít žádný vnitřní stav. Vztah mezi třídou a rozhraním je takový, že jedna třída může **implementovat** více různých rozhraní. Pokud nějaká třída **implementuje** nějaké rozhraní, znamená to, že obsahuje všechny operace specifikované v rozhraní a zároveň je **podtypem** daného rozhraní. Pokud například třída *T* implementuje rozhraní *A*, *B* a *C*, obsahuje všechny operace specifikované v rozhraní *A*, *B*, *C* a zároveň je podtypem *A*, *B* i *C*. Lze ji tedy použít (na základě polymorfismu) místo všech těchto rozhraní. V rozhraní mohou být dále přítomny konstanty.

Prvním druhem operací, které lze v rozhraní specifikovat, jsou **hlavičky metod**. Ty pouze určují název metody, její parametry, návratovou hodnotu a výjimky. Implementující třída potom musí přidat tělo této metody. Příklad ukazuje jedno rozhraní a dvě jeho možné implementace:

```java
public interface Operation {
    int calculate(int a, int b);
}
```

```java
public class Adding implements Operation {
    @Override
    public int calculate(int a, int b) {
        return a + b;
    }
}
```

```java
public class Subtracting implements Operation {
    @Override
    public int calculate(int a, int b) {
        return a - b;
    }
}
```

Pokud třída neimplementuje všechny metody deklarovaných rozhraní, musí být abstraktní. Další její potomci musí implementaci doplnit.

```java
public abstract class AbstractPrintingOperation implements Operation {
    public void calculateAndPrint(int a, int b) {
        final int result = calculate(a, b);
        System.out.println("result: " + result);
    }

    // children must implement: int calculate(int, int)
}
```

V rozhraní se dále mohou nacházet tzv. **default metody**, což jsou metody včetně těla. Default metody slouží hlavně k tomu, aby bylo možné rozhraní rozšiřovat bez nutnosti zásahů do jejich implementujících tříd. Jakmile nějaká default metoda vznikne v rozhraní, automaticky se dostane i do všech tříd, které toho rozhraní implementují. 

```java
public interface OperationWithDisplay extends Operation {
    default void calculateAndPrint(int a, int b) {
        final int result = calculate(a, b);
        display(result);
    }

    void display(int a);
}
```

Vzhledem k tomu, že jedna třída může implementovat více rozhraní, může docházet k jmenným konfliktům. Implementující třída si pak musí vybrat, kterou implementaci použije.

```java
public interface GroupFromWest {
    default void shout() {
        System.out.println("West side is da best!");
    }
}
```

```java
public interface GroupFromEast {
    default void shout() {
        System.out.println("East side is da best!");
    }
}
```

```java
public class Everybody implements GroupFromEast, GroupFromWest {
    @Override
    public void shout() {
        GroupFromEast.super.shout();
        GroupFromWest.super.shout();
        System.out.println("Silence!");
    }
}
```

Dalším typem metod, které mohou v rozhraních být, jsou **statické metody**. Fungují podobně jako u tříd, ale nepřenášejí se do podtypů. Takže se dají volat pouze na tom rozhraní, kde jsou definovány. Z toho stejného důvodu musí statické metody v rozhraních obsahovat i tělo, protože nikde jinde nemůže být definováno.

```java
public static interface Thanker {
    static String thank() {
        return "Thank you.";
    }
}
```

### Funkční rozhraní

Funkční rozhraní je speciální typ rozhraní obsahující jen jednu abstraktní metodu. Používá se ve spojení s **lambda výrazy**, ale může sloužit i jako klasické obyčejné rozhraní.

Každý lambda výraz může vystupovat v roli libovolného funkčního rozhraní, jehož abstraktní metoda typově odpovídá danému lambda výrazu. Pokud tedy má metoda funkčního rozhraní například dva celočíselné parametry a vrací řetězec, lambda výraz také musí přijímat dva celočíselné parametry a vracet řetězec (existuje tam i možnost použití podtypů, podobně jako při překrývání metod). Funkční rozhraní se od obyčejného rozhraní kromě již zmíněného omezení na jedinou abstraktní metodu liší i (nepovinnou) anotací *@FunctionalInterface*, která je informací pro dodatečné validace při kompilaci.

```java
@FunctionalInterface
public static interface Operation {
	int apply(int a, int b);
}
```

Nyní lze deklarovat lambda výrazy daného typu:

```java
Operation plus = (int a, int b) -> a + b;       
Operation minus = (int a, int b) -> a - b;
```

Několik základních a obecných funkčních rozhraní je již připraveno v knihovných jazyka Java:

- java.lang.Runnable
- java.util.function.BinaryOperator<T>
- java.util.function.UnaryOperator<T>
- java.util.function.Predicate<T>
- java.util.function.Function<A,B>
- java.util.function.Consumer<T>
- java.util.function.Supplier<T>
- atd.

### Lambda výrazy

Lambda výrazy jsou kusy kódu, které je možné v programu předávat podobně jako jiné druhy proměnných. Vhodným použitím lambda výrazu lze zjednodušit kód, vyjádřit nějaké konstrukce srozumitelněji a omezit počet umělých tříd a rozhraní v programu. Typ lambda výrazu je dán kontextem, kde je použit. Jeden a ten samý lambda výraz může nabývat různých typů, dokud zmíněné typy kompilátor dokáže napasovat na sebe. 

Syntaxe lambda výrazu je následující:

- otevřená kulatá závorka *(* (možno vynechat pokud je pouze jeden parametr)
- volitelné parametry s typy (typy je možné většinou vynechat, domyslí si je kompilátor)
- uzavřená kulatá závorka *)* (možno vynechat pokud je pouze jeden parametr)
- symbol šipky *->*
- tělo lambda výrazu (pokud obsahuje více příkazů, musí být uzavřeno ve složených závorkách, konstrukci *return* lze vynechat, je-li tělo výrazem)

### Výčtové typy

Výčtové typy jsou podobné třídám, ale není možné vytvářet jejich instance - všechny instance jsou totiž již předem zcela dané **hodnotami** daného výčtového typu. 

Mějme následující příklad:

```java
public enum Gender {
	MALE, FEMALE;
}
```

V tomto výčtovém typu jsou definovány dvě instance: *Gender.MALE* a *Gender.FEMALE*. Obě tyto instance jsou typu *Gender*. Výše uvedený výčtový typ zhruba odpovídá následujícímu kódu bez výčtových typů:

```java
public final class Gender {
	public static final Gender MALE = new Gender();
	public static final Gender FEMALE = new Gender();
}
```

I výčtové typy mohou mít atributy, metody a implementovat rozhraní, podobně jako třídy. Jediné, co nemohou, je využití dědičnosti.

### Výjimky

K řízení toku programu například při výjimečných situacích či chybách lze využít mechanismus tvorby a odchytávání výjimek. Výjimka v jazyce Java se deklaruje jako libovolný potomek třídy *java.lang.Throwable*, nejčastěji však jeho konkrétnějších podtypů *java.lang.Exception* a *java.lang.RuntimeException*. Rozdíl mezi nimi je v tom, že se podtypy *RuntimeException* nemusí explicitně odchytávat blokem *catch*, zatímco všechny ostatní ano. Výjimky, které se musí deklarovat a odchytávat, se označují jako **checked**, ty ostatní jako **unchecked**.

Odchytávání výjimek probíhá ve speciálním bloku *try-catch* nebo *try-catch-finally*, který má následující strukturu:

```java
try {
	// kód způsobující výjimky
} catch (Exception1 | Exception2 e) {
	// akce v případě, že dojde k chybě Exception1 nebo Exception2 (nebo jejich podtypům)
} catch (Exception3 e) {
	// akce v případě, že dojde k chybě Exception3 (nebo jejímu podtypu)
} finally {
	// (blok je nepovinný)
	// akce která se provede vždy (nezávisle na tom, zda k výjimce došlo či ne)
}
```

Pokud v bloku *catch* není některá z výjimek odchycena, je propagována do volajícího objektu.

Pro zavedení nové výjimky je třeba vykonat tyto kroky:

1. deklarovat výjimku *X*
1. do hlavičky všech metod které mohou výjimku způsobit přidat deklaraci *throws X* (není nutné v případě runtime exception)
1. na patřičných místech ve výše zmíněných metodách výjimku vytvořit konstrukcí *throw new X*
1. v kódu volajícím metodu *M* je nutné výjimku ošetřit blokem *try-catch* (není nutné v případě runtime exception)

Názorná ukázka v kódu:

```java
private void methodWithError() throws SomeException {
	throw new SomeException();
}

private void methodWithNoError() {
	try {
		this.methodWithError();
	} catch (SomeException e) {
		// resolve exception which is available in 'e' variable
	}
}
```

### Anotace

Anotace slouží k přidávání meta-informací k různým elementům zdrojového kódu. Tyto meta-informace lze následně použít při kompilaci nebo číst pomocí **introspekce** (podle toho, jak je anotace propagována). Příkladem jsou například anotace související s validací, ukládáním do databáze (JPA), Spring kontextem, atd.

Anotace se deklaruje v podobné struktuře jako třída:

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Untested {
    String severity = "low";
}
```

Anotace *@Target* specifikuje typy elementů, ke kterým je možné anotaci přiřadit:

- **TYPE** = třída, rozhraní, výčtový typ
- **FIELD** = atribut, konstanta výčtového typu
- **METHOD** = metoda
- **PARAMETER** = parametr metody
- **CONSTRUCTOR** = konstruktor
- **LOCAL_VARIABLE** = deklarace proměnné v bloku
- **ANNOTATION_TYPE** = anotace
- **PACKAGE** = balíček

Anotace *@Retention* specifikuje, kam až se bude anotace propagovat:

- **SOURCE** = anotace je pouze ve zdrojovém kódu, kompilátor anotaci odstraní
- **CLASS** = anotace je přítomná ve zkompilované třídě, ale není propagována do jejích instancí
- **RUNTIME** = anotace je přítomná ve zkompilované třída a je propagovaná i do instancí