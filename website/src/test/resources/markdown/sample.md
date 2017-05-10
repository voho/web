## Abstract Factory (abstraktní továrna)

Návrhový vzor abstract factory je návrhovým vzorem pro strukturu objektů. Používá se v situacích, kdy je z nějakých dobrých důvodů vhodné delegovat vytváření nové instance třídy (tzv. produktu) na dedikovanou třídu (tzv. tovární třídu). Důvody mohou být různé - například je logika pro volbu konkrétní vytvářené třídy složitá, opakuje se na více místech, nebo ji chce továrna zkrátka "skrýt" a odstínit tak klienta od nepodstatných detailů. Tovární třída obsahuje jednu nebo více [továrních metod](wiki/factory-method).

Obecně lze mít rozsáhlé hierarchie produktů i továrních tříd, přičemž klient nemůže očekávat žádnou konkrétní implementaci továrny či produktu, musí mu stačit jen to nejobecnější rozhraní od každého. Všechny možné produkty tovární třídy mají společného předka a klient spoléhá pouze na to, že mu bude vrácen jeden z potomků této třídy. Dopředu neví, který to bude - logiku pro jeho výběr zná pouze tovární třída. To umožňuje klientovi se všemi možnými produkty pracovat stejně. Klient má k dispozici pouze rozhraní jejich abstraktního předka a neměl by mít žádná očekávání ohledně tovární třídy, která bude pro vytvoření instancí použita.

### Emoji

:bowtie: :blush: :stuck_out_tongue_winking_eye:

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

### Graph 1

```dot:graph
rankdir=LR
A--F [label=" 1"]
{rank=same; A;F;E;}
{rank=same; B;D;}
```

## Graph 2

```dot:digraph
rankdir=LR
A->F [label=" 1"]
{rank=same; A;F;E;}
{rank=same; B;D;}
```

### Kód bez ničeho 1

Každou šestici teď zakódujeme pomocí kódovací tabulky a poslední dvě chybějící šestice nahradíme rovnítky podle standardu:

    QnJubw==

A ještě.

    hello <world>
    
### Kód bez ničeho 2

```
hello <world>
```

### Příklad

#### Rozhraní produktů

```java
/**
 * Obecný dopravní prostředek.
 *
 * @author Vojtěch Hordějčuk
 */
public interface Vehicle<T>
{
  // ...
  List<Integer> getCosts();
}
```

#### Konkrétní produkty

```java
/**
 * Osobní automobil.
 *
 * @author Vojtěch Hordějčuk
 */
public class Car<T> implements Vehicle<T>
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
```

### Definition lists

Something
: def1
: def2

### Images

Hello, some paragraph.
![image floating left](https://placekitten.com/g/200/300){.left}
Hello, some paragraph.
![image floating right](https://placekitten.com/g/200/300){.right}

### Just figures

![This is a simple figure.](https://placekitten.com/g/200/300)

### Links

Hello, go to [this wiki](wiki/something).
Normal link [[TEXT|url]].
And [this is normal link](something).

### Tables

| hello | world
|---|---
| hello | world
| hello | world
| hello | world

### Quotes

blablabla

> This is the dummies code ever. *Wojtek*

blablabla

### Reference

- http://sourcemaking.com/design_patterns/abstract_factory
