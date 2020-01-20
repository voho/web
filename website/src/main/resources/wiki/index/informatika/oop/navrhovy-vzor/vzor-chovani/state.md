## GoF: State (stav)

### Situace

V systému se nachází třída, jejíž chování je velmi silně závislé na jejím vnitřním stavu. Zbytek systému má být od tohoto stavu odstíněn. 
V podstatě se jedná o objektově-orientovanou implementaci [konečného automatu](wiki/konecny-automat).

### Problém

Typická špatná implementace tohoto chování se opírá o množství stavových proměnných a podmínek, na jejichž základě se provádí změna chování třídy nebo změna výstupů. 
Údržba takového kódu je obtížná. 
Všechny stavy třídy jsou vlastně smíchané dohromady v jedné instanci, i když by správně měly být odděleny stav po stavu.
Z množství stavových proměnných nemusí být jasné, které z nich spolu souvisí, a co jejich vzájemné kombinace znamenají.

### Řešení

Na základě vnitřních stavů se vytvoří hierarchie tříd, které implementují společné rozhraní. 
Třídy, která je tímto vnitřním stavem řízena, bude obsahovat právě jednu instanci třídy z této hierarchie, tedy jeden z vnitřních stavů. 
Všechny metody, které závisí na vnitřním stavu, by měly být na tento stav delegovány. 

Změna vnitřního stavu se provádí výměnou objektu, který představuje vnitřní stav.
Za tuto výměnu je zodpovědna třída, které je stavem řízena.

#### Varianty

- vnitřní stav mění třída sama
- změnu stavu provádí sám stav (například tak, že vrátí instanci jiného stavu a třída jej převezme)

#### UML diagramy

```uml:class
class State <<interface>> {
}

Client -> Context
Context -> State
ConcreteState1 .up-.|> State
ConcreteState2 .up-.|> State
```

#### Související vzory

- [Strategy](wiki/strategy) - konkrétní strategii určuje klient
- [Adapter](wiki/adapter) - určitá podobnost v delegaci operace na vnitřní objekt

### Příklad

#### Vnitřní stavy

```include:java
gof/state/StateOfMind.java
```

```include:java
gof/state/HappyState.java
```

```include:java
gof/state/SadState.java
```

#### Kontext

Kontext je třída, jejíž stav je reprezentován instancí nějakého vnitřního stavu.

```include:java
gof/state/Context.java
```

#### Příklad použití

```include:java
gof/state/Example.java
```

### Reference

- předmět X36ASS na FEL ČVUT
- předmět X36OBP na FEL ČVUT
- http://www.vico.org/pages/PatronsDisseny/Pattern%20State/index.html
