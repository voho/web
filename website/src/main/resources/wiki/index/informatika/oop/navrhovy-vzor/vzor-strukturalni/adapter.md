## GoF: Adapter (adaptér)

### Situace

V systému existuje nevyhovující třída se zastaralým, nekompatibilním, nebo jinak neuspokojivým rozhraním. Funkcionalita existující třídy má být zachována a pouze s minimálním úsilím převedena pod rozhraní nové. Může se objevit i požadavek, aby se stávající třída již neměnila (a někdy to ani není možné, například při použití knihovny).

### Problém

Použití nevyhovující třídy ovlivňuje ta místa v systému, kde je využívána její funkcionalita. Nevyhovující ůže do zbytku systému zanést zbytečný zmatek, který bude navíc umocněn tím, že se zastaralá třída časem odstraní úplně.  a navíc se spolu s ním může šířit nedokonalosti, které byly již identifikovány a odstraněny. 

Přímá úprava zastaralé třídy by zabrala mnoho času a pokud je již důkladně otestována, není k tomu zpravidla ani vůle. Přesto je ale nutné, aby zbytek systému využíval již rozhraní nové.

### Řešení

Nevyhovující třídu lze od zbytku systému odstínit tak, že se celá zapouzdří do třídy nové. Tato třída již implementuje nové rozhraní a poskytuje infrastrukturu pro transformaci požadavků od klienta na příslušné metody zapouzdřené třídy.
 
Návrhový vzor Adapter se často používá jen jako dočasné řešení do doby, než je nevyhovující třída nahrazena. Toto nahrazení je navíc snadnější, protože zbytek systému je na nové rozhraní připraven. Další častou aplikací tohoto návrhového vzoru je zajištění zpětné kompatibility.

#### Varianty

- **třídní adaptér** - dědí od nevyhovující třídy
- **objektový adaptér** - využívá delegaci

#### UML diagramy

```uml:class
class Target <<interface>> {
  newRequest()
}

class Adaptee {
  oldRequest()
}

Client ..> Target
Client -> Adapter
Adapter ..|> Target
Adapter -> Adaptee
note top on link
  delegate call by translation
end note
```

#### Související vzory

- [Facade](wiki/facade) - vytváří nové rozhraní pro více tříd
- [Iterator](wiki/iterator) - adaptér specializovaný na sekvenční procházení
- [Proxy](wiki/proxy) - adaptér má stejné rozhraní jako zapouzdřená třída

#### Implementace

##### Požadovaná funkcionalita

```include:java
gof/adapter/Target.java
```

##### Nevyhovující třída

```include:java
gof/adapter/Adaptee.java
```

##### Adaptér nevyhovující třídy

```include:java
gof/adapter/Adapter.java
```

##### Použití

```include:java
gof/adapter/Example.java
```

### Reference

- předmět X36ASS na FEL ČVUT
- http://objekty.vse.cz/Objekty/Vzory-Adapter
