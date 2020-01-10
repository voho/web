## Original (originál)

Návrhový vzor Original se používá všude tam, kde je pro jedinečnou sadu parametrů výhodné vytvořit jedinečnou instanci a zajistit, aby se pro stejnou sadu nedala vytvořit instance jiná. Počet instancí dané třídy je tedy shora omezen počtem všech možných kombinací parametrů.

Vzor se nejsnadněji implementuje pomocí vyrovnávací paměti (cache). Nejprve je nutné navrhnout [injektivní zobrazení](wiki/zobrazeni) ze sady parametrů do [množiny](wiki/mnozina), jejíž prvky budou sloužit jako klíče do vyrovnávací paměti instancí. Toto zobrazení spolu s vyrovnávací pamětí by mělo být efektivní, aby režie při vytváření nových instancí nebyla příliš vysoká. 

Instance dané třídy lze získávat pouze pomocí [tovární metody](wiki/factory-method), která přijímá sadu parametrů a vrací jedinečnou instanci pro každou takovou sadu. Jiné způsoby vytváření instancí by měly být zakázány na úrovni syntaxe (např. privátní konstruktor).

### Příklad

#### Barva

Níže deklarovaná třída **Color** má několik charakteristických rysů, společných pro všechny třídy navržené podle vzoru Original. Má privátní konstruktor, a proto je zajištěn jiný způsob, jak její instance získávat. K tomu je určena veřejná tovární metoda **get()**. 

Tato metoda vrací jedinečnou instanci třídy **Color** pro každou jedinečnou kombinaci vstupních parametrů. Jinými slovy, každé dvě instance touto metodou získané jsou totožné právě tehdy, když byly vytvořeny na základě stejné kombinace parametrů.

Z každé jedinečné kombinace parametrů je vytvořen jedinečný klíč, který je následně vyhledán v mapě. Jestliže tento klíč v mapě existuje, je vrácena odpovídající instance třídy **Color**. Jestliže klíč v mapě neexistuje, je vytvořena nová instance třídy, tato je uložena do mapy a vrácena.

```include:java
gof/original/Color.java
```

#### Použití

```include:java
gof/original/Example.java
```

### Reference

- Rudolf Pecinovský: Návrhové vzory