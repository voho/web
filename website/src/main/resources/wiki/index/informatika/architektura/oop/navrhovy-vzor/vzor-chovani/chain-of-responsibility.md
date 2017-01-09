## GoF: Chain of Responsibility (řetez odpovědnosti)

Tento návrhový vzor slouží k realizaci předávání požadavku v řetězci vzájemně propojených instanci. Ta instance, která požadavek nemůže zpracovat, jej přepošle následujícímu článku. Připomíná to předávání "horké brambory".

Popisovaný návrhový vzor se využívá proto, aby klient nemusel znát reference na všechny články řetězce, ani pořadí, ve kterém se mají tyto články provolat. O dalším zpracování požadavku totiž mohou články rozhodovat samy.

Tento návrhový vzor se často objevuje například v různých hierarchiích, kde je požadavek postupně předáván směrem k nadřazenému prvku, až ke kořeni stromu, ve kterém je prvek umístěn. Nebo v lineárně uspořádaném procesu, kde dochází k postupnému zpracovávání požadavku.

### Řešení

Klient bude mít referenci pouze na začátek řetezce - instanci rozhraní *Handler* a té odešle svůj požadavek. O nic víc se nestará - zbytek zařídí článek, který požadavek zpracuje nebo jej odešle dalším článkům, ke kterým má přístup.

Samotné pořadí článků může být realizováno několika způsoby. Články mohou být uspořádány [lineárně](wiki/datova-struktura-seznam), nebo ve [stromové struktuře](wiki/datova-struktura-strom). Znalost o pořadí článků v řetězci může být buď přímou součástí článků, nebo jej články neznají a k předávání požadavku využívají pomocný objekt, který k tomuto účelu spolu s požadavkem dostanou.

```uml:class
class Responsible <<interface>> {
  handle(request: Request)
}
Client -right-> Responsible : " first"
Responsible --> Responsible : next
```

#### Varianty

První z variant počítá s tím, že každý článek řetězce má k dispozici referenci na článek následující a tomuto článku požadavek v případě potřeby předá.

```uml:class
class Responsible <<interface>> {
  handle(request: Request)
}
Client -right-> Responsible : " first"
Responsible --> Responsible : next
```

Druhá z variant tento následující článek při každém volání předá.

```uml:class
class Responsible {
  handle(request: Request, next: Responsible)
}
Client -> Responsible
```

Třetí varianta zapouzdřuje celý řetězec do samostatného objektu, který obsahuje jednotlivé články a definuje jejich pořadí. Tento objekt potom zpracovává požadavek, aniž by jednotlivým článkům dával tušit, v jakém pořadí jsou články uspořádány.

```uml:class
class Chain {
  handle(request: Request)
}
class Responsible <<interface>> {
  handle(request: Request, chain: Chain)
}
note right
  any responsible can call "chain.handle"
  to continue request handling
  (responsibles do not know their order)
end note
Chain " 1 " o-down-> " * " Responsible : " sequence"
Client -right-> Chain
```

#### Implementace

Příklad se bude týkat vyhledávání odpovědí na určitou otázku. Pokud člověk odpověď nezná, zeptá se kamarádů. Pokud ani kamarádi nevědí, začne hledat na internetu.

##### Rozhraní

```java
interface Answering {
    String answer(String question);
}
```

##### Handlery

```java
public class GoogleAnswering implements Answering {
    @Override
    public String answer(String question) {
        return googleForAnswer(question);
    }

    // googleForAnswer
    // ...
}
```

```java
public class FriendsAnswering implements Answering {
    private GoogleAnswering googleAnswering;

    @Override
    public String answer(String question) {
        if (somebodyKnowsAnswer(question)) {
            return answerByFriend(question);
        } else {
            return googleAnswering.answer(question);
        }
    }

    // somebodyKnowsAnswer
    // answerByFriend
    // ...
}
```

```java
public class MyselfAnswering implements Answering {
    private FriendsAnswering friendsAnswering;

    @Override
    public String answer(String question) {
        if (rememberAnswer(question)) {
            return answerByMyself(question);
        } else {
            return friendsAnswering.answer(question);
        }
    }

    // rememberAnswer
    // answerByMyself
    // ...
}
```

### Reference

- http://www.oodesign.com/chain-of-responsibility-pattern.html
- http://userpages.umbc.edu/~tarr/dp/lectures/Chain-2pp.pdf
