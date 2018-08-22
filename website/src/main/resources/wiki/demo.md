## Testovací stránka

### Runkit (Javascript)

```runkit:js
// GeoJSON!
var getJSON = require("async-get-json");
await getJSON("https://storage.googleapis.com/maps-devrel/google.json");
```

### Zdrojový kód (Java)

```java
public static BigInteger power(final BigInteger base, final BigInteger power) {
    if (BigInteger.ZERO.compareTo(power) > 0) {
        // power < 0
        throw new IllegalArgumentException("Power must be >= 0.");
    } else if (BigInteger.ZERO.equals(power)) {
        // power = 0
        return BigInteger.ONE;
    } else {
        BigInteger result = base;
        BigInteger remains = power;
        while (remains.compareTo(BigInteger.ONE) > 0) {
            result = result.multiply(base);
            remains = remains.subtract(BigInteger.ONE);
        }
        return result;
    }
}
```

### Zdrojový kód (Java z Githubu)

```github:java
/lz77/src/main/java/LZ77Codeword.java
```

### UML

```uml:class
class Responsible <<interface>> {
  handle(request: Request)
}
Client -right-> Responsible : " first"
Responsible --> Responsible : next
```

```uml:seq
User -> View : perform action via UI
View -> Controller : invoke action
Controller -> Model : change
Model --> Controller
Model --> View : notifies about change
View -> Model : request current state
Model --> View 
View --> User : update UI
```

### Dot

```dot:digraph
splines=ortho
table [shape=record,label="<n>_ _ _|<h>hello|world|this|is|...|<t>queue"]
head [shape=none]
tail [shape=none]
head->table:h
tail->table:t
add->table:n
table:t->take
{rank=same;add;take;}
```

```dot:graph
rankdir=LR
1--2 [label=" e1"];
1--3 [label=" e2"];
1--4 [label=" e3"];
3--4 [label=" e4"];
```

### TeX

€€ \sum _{k=1}^{n}I_{k}=0, \sum _{k=1}^{n}U_{k}=0 €€

### Definice

enqueue(*a*)
: vloží prvek *a* na konec fronty

dequeue()
: vrátí první (nejstarší) prvek ze začátku fronty a odebere jej

### Tabulka

| Skupina | Frekvence
|---|---
| radiové vlny | 3 kHz až 300 MHz
| mikrovlny | 0.3 až stovky GHz
| infračervené záření | 1e11 až 4e14 Hz

### Obrázek

![hradlo NOR](gates_nor.png)

### TODO indikátor 

!TODO!

### Citát

> Good design adds value faster than it adds cost. *Thomas C. Gale*