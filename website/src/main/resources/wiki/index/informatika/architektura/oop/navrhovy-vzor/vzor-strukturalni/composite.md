## GoF: Composite (kompozit)

### Situace

Do systému je třeba zabudovat hierarchickou strukturu, která se skládá ze dvou typů objektu: primitivní (leaf) a složené (composite), které obsahují další vnořené objekty. Oba typy objektů mají společné rozhraní, ale každý z nich implementuje požadované funkce jinak. Oba typy jsou tedy vzájemně zaměnitelné.

Příklady: Adresáře, ve kterých mohou být soubory i další adresáře. Výrobky, které se mohou skládat z materiálu i z dalších výrobků (polotovarů). Menu, jehož položky mohou být příkazy i další menu.

### Řešení

Každý typ objektu bude reprezentován jednou třídou. Jedna třída (Leaf) bude představovat primitivní objekt, druhá třída (Composite) bude představovat objekt složený. Obě třídy budou implementovat společné rozhraní (Component). Třída reprezentující složený objekt bude obsahovat kód, který využije vnořených objektů při realizaci společné funkcionality. Může jimi například procházet či požadavek přeposlat na každý z nich.

```uml:class
class Component <<interface>> {
  operation()
}

class Composite {
  add(child: Component)
  remove(child: Component)
}

note top of Composite
  operation := foreach child { child.operation() }
end note

Composite ..|> Component
Composite "1" *-- "*" Component : children
Leaf ..|> Component
Client -> Component
```

### Implementace

#### Společné rozhraní

```include:java
gof/composite/Component.java
```

#### Objekty

```include:java
gof/composite/Leaf.java
```

```include:java
gof/composite/Composite.java
```

### Reference

http://sourcemaking.com/design_patterns/composite