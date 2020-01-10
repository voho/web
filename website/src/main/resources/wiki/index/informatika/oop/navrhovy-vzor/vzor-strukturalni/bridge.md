## GoF: Bridge (most)

Bridge je návrhový vzor pro strukturu objektů. Používá se, když chceme oddělit abstrakci od její implementace tak, aby se obě mohly měnit nezávisle. Klient posléze využije některou z implementací nepřímo prostřednictvím abstrakce. Protože je "oddělení abstrakce od implementace" velmi obecný pojem, je vhodné uvést několik příkladů, jak řez provést:

- platformově nezávislý kód vs. platformově závislý kód
- kód nezávislý na formátu dat vs. kód závislý na formátu dat
- (obecně jakýkoliv specifický kód vs. společný kód)
- doménový kód vs. infrastrukturní kód
- front-end vs. back-end

### Řešení

Prvním krokem je návrh **rozhraní abstrakce** (*AbstractionApi*), které bude využívat klient. Tento návrh by tedy měl být klientem specifikován. Druhým krokem je návrh **rozhraní implementace** (*ImplementationApi*), které bude abstrakce využívat pro přístup k implementaci. Návrh bude omezený tím, jaké funkce jsou implementace schopny v průniku poskytnout. Třetím krokem je vytvoření vlastního "mostu", tedy kódu, který bude realizovat abstrakci pomocí implementace. Tento kód nesmí být závislý na žádné konkrétní implementaci, pouze na jejich rozhraní (*ImplementationApi*). Lze jej realizovat například jako abstraktní třídu, která v konstruktoru obdrží některou z konkrétních implementací (*Implementation1* nebo *Implementation2*). Pokud jiní klienti vyžadují odlišné ozhraní, lze vytvořit potomka *AbstractionApi* a rozdíly implementovat v něm (*RefinedAbstractionApi*).

```uml:class
AbstractionApi -right-> ImplementationApi
RefinedAbstractionApi-up-|> AbstractionApi
Implementation1 .up.|> ImplementationApi
Implementation2 .up.|> ImplementationApi
```

### Související vzory

- [Adapter](wiki/adapter) - vzor Adapter se využívá pro přizpůsobení již existující třídy pro nové rozhraní a oba objekty (existující i nový) mají stejnou podstatu
- [State](wiki/state) - u vzoru State je vytvořená třída pouze externalizovaným vnitřním stavem původního objektu a je s ním tedy těsně svázaná
- [Strategy](wiki/strategy) - vzor Strategy ukazuje, jak externalizovat určitý postup či chování objektu, proto je vztah mezi objektem a strategií silnější a koncept strategie je přesněji definovaný než koncept závislosti

### Příklad

Následující příklad bude představovat jednoduchý vykreslovací program a řez bude proveden mezi klientem (kreslící program) a jeho závislostí (grafická knihovna AWT pro vykreslování UI v Javě). Cílem kreslícího programu je vystavit klientům jednoduché rozhraní pro vykreslování komplexních objektů, jako je čára, kružnice, obdélník (a různé jejich kombinace) a zároveň jim umožnit volbu kvality, v jaké se bude grafika vykreslovat.

```uml:class
Canvas -right-> Drawer
ExtendedCanvas -up-|> Canvas
LowQualityDrawer .up.|> Drawer
HighQualityDrawer .up.|> Drawer
```

#### Grafická knihovna

Závislostí kreslícího programu je grafická knihovna. Po té se vyžaduje, aby uměla nakreslit kružnici a čáru. Toto rozhraní je kořenem první hierarchie.

```include:java
gof/bridge/Drawer.java
```

První implementace kreslí vše černobíle a v nízké kvalitě.

```include:java
gof/bridge/LowQualityDrawer.java
```

Druhá implementace kreslí barevně a ve vysoké kvalitě (s vyhlazováním).

```include:java
gof/bridge/HighQualityDrawer.java
```

#### Hierarchie kreslícího programu

```include:java
gof/bridge/Canvas.java
```

```include:java
gof/bridge/ExtendedCanvas.java
```

### Reference

- https://sourcemaking.com/design_patterns/bridge
- http://c2.com/cgi/wiki?BridgePattern
- https://dzone.com/articles/design-patterns-bridge