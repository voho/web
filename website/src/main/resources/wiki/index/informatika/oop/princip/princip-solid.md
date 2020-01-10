## Princip SOLID

SOLID (single responsibility, open-closed, Liskov substitution, interface segregation, dependency inversion) je sada doporučení, principů a vodítek, sloužících k vytvoření kvalitnějšího objektového návrhu. Principy SOLID shromáždil a popsal **Robert C. Martin** (strýček Bob) kolem roku 2000.

### Rychlý přehled

| Zkratka | Název | Popis
|---|---|---
| **S** | Single responsibility principle | Každá třída má právě jednu zodpovědnost.
| **O** | Open/Closed principle | Funkcionalitu třídy lze rozšířit bez nutnosti její modifikace.
| **L** | Liskov substitution principle | Třídy musí být plně nahraditelné svými potomky.
| **I** | Interface segregation principle | Používat malá a úzce zaměřená rozhraní.
| **D** | Dependency inversion principle | Závislost na abstrakcích, nikoliv na implementacích.

![meme](meme_SOLID.jpg)

### Podrobnější popis

#### Single responsibility principle

Princip jedné zodpovědnosti říká, že každá třída či modul by měl mít právě jednu zodpovědnost a tato zodpovědnost by měla být danou třídou či modulem plně pokryta. Za zodpovědnost se zpravidla považuje nějaká rozumně jednoduchá a oddělená funkcionalita. Použití tohoto principu snižuje složitost systému a zvyšuje jeho soudržnost a pochopitelnost.

Příklad porušení principu:

```java
class Book {
    private Name name;
    private Author author;
    private Content content;

    // ... getters
    // ... settters

    public void print() {
        // book printing code
    }

    public void read() {
        // book reading code
    }
}
```

Oprava podle principu:

```java
class Book {
    private Name name;
    private Author author;
    private Content content;

    // ... getters
    // ... settters
}
```

```java
class Printer {
    public void print(Book book) {
        // book printing code
    }
}
```

```java
class Reader {
    public void read(Book book) {
        // book reading code
    }
}
```

![meme](meme_SingleResponsibilityPrinciple.jpg)

#### Open/Closed principle

Princip Open/Closed říká, že by každá softwarová entita (třída, modul, metoda, kód...) měla být **otevřená** pro rozšíření, ale **uzavřená** pro modifikaci. To znamená, že by mělo být možné změnit její chování bez toho, aby se zasáhlo do jejího zdrojového kódu, například přidáním jiných entit. Takový zásah totiž přináší mnoho možných obtíží a komplikací. Většina realizací tohoto principu spočívá v použití **dědičnosti**.

Příklad porušení principu:

```java
class Payment {
    public void pay(Method method, Money amount) {
        if (method.isCash()) {
            confirmPaidUsingCash(amount);
            printReceipt(amount);
            dispatchGoods();
        } else if (method.isTransfer()) {
            confirmPaidUsingTransfer(amount);
            printReceipt(amount);
            dispatchGoods();
        } else {
            throw new IllegalArgumentException("Unknown payment option.");
        }
    }
}
```

Oprava podle principu:

```java
interface Method {
    void confirmPaymentReceived(Money amount);
}

class TransferMethod implements Method { /* ...confirmPaymentReceived... */ }
class CashMethod implements Method { /* ...confirmPaymentReceived... */ }
```

```java
class Payment {
    public void pay(Method method, Money amount) {
        method.confirmPaymentReceived(amount);
        printReceipt(amount);
        dispatchGoods();
    }
}
```

![meme](meme_OpenClosedPrinciple.jpg)

#### Liskov substitution principle

Liskové princip substituce hovoří o vzájemném nahrazování dvou tříd. Je-li třída *B* potomkem třídy *A*, pak musí být třída *B* použitelná všude, kde je vyžadována třída *A* bez toho, aniž by o tom nadřazená třída věděla. Tento princip opět implikuje použití dědičnosti a polymorfizmu.

![meme](meme_LiskovSubtitutionPrinciple.jpg)

#### Interface segregation principle

Princip oddělení rozhraní říká, že každé rozhraní by mělo být co nejmenší možné a třídy by neměly být nuceny používat rozhraní, která nepoužívají. 

Pokud tedy nějaké rozhraní přesáhne rozumnou velikost, musí se rozdělit do několika dalších a užších rozhraní. Potom se touto změnou zasažené třídy přepracují tak, aby implementovaly jen minimální potřebnou podmnožinu původních rozhraní.

Příklad porušení principu:

```java
interface Lifecycle {
    void start();
    void stop();
}
```

Oprava podle principu:

```java
interface Startable {
    void start();
}
```

```java
interface Stoppable{
    void stop();
}
```

#### Dependency inversion principle

Princip inverze závislosti říká, že moduly na vyšší úrovni by neměly záviset na modulech nízkoúrovňových. Oba by měly záviset na abstrakcích. A dále, abstrakce by neměly záviset na implementačních detailech, ale naopak - detaily by měly záviset na abstrakcích.

Pokud například vyšší úroveň provádí nějaká rozhodnutí a jejich realizací pověřuje moduly na úrovni nižší, může se po změně nižší úrovně změnit funkce i vyšší úrovně. To by se ale nemělo stát - snižuje to znovupoužitelnost vysokoúrovňových modulů, které by měly stát odděleně od modulů nízkoúrovňových.

Tento princip souvisí s [vkládáním závislostí](wiki/vkladani-zavislosti).

![meme](meme_DependencyInversionPrinciple.jpg)

### Reference

- R. C. Martin: Design Principles and Design Patterns
- http://blog.objectmentor.com/articles/2009/02/12/getting-a-solid-start
- http://www.butunclebob.com/ArticleS.UncleBob.PrinciplesOfOod
- http://goruco2009.confreaks.com/30-may-2009-15-40-solid-object-oriented-design-sandi-metz.html