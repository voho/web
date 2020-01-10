## GoF: Chain of Responsibility (řetez odpovědnosti)

Tento návrhový vzor slouží k realizaci předávání požadavku v řetězci vzájemně propojených instanci. Ta instance, která požadavek nemůže zpracovat, jej přepošle následujícímu článku. Připomíná to předávání "horké brambory".

Popisovaný návrhový vzor se využívá proto, aby klient nemusel znát reference na všechny články řetězce, ani pořadí, ve kterém se mají tyto články provolat. O dalším zpracování požadavku totiž mohou články rozhodovat samy.

Tento návrhový vzor se často objevuje například v různých hierarchiích, kde je požadavek postupně předáván směrem k nadřazenému prvku, až ke kořeni stromu, ve kterém je prvek umístěn. Nebo v lineárně uspořádaném procesu, kde dochází k postupnému zpracovávání požadavku.

### Řešení

Klient bude mít referenci pouze na začátek řetezce - instanci rozhraní *Handler* a té odešle svůj požadavek. O nic víc se nestará - zbytek zařídí článek, který požadavek zpracuje nebo jej odešle dalším článkům, ke kterým má přístup.

Samotné pořadí článků může být realizováno několika způsoby. Články mohou být uspořádány [lineárně](wiki/datova-struktura-seznam), nebo ve [stromové struktuře](wiki/datova-struktura-strom). Znalost o pořadí článků v řetězci může být buď přímou součástí článků, nebo jej články neznají a k předávání požadavku využívají pomocný objekt, který k tomuto účelu spolu s požadavkem dostanou.

#### Varianty

První z variant počítá s tím, že každý článek řetězce má k dispozici referenci na článek následující a tomuto článku požadavek v případě potřeby předá.

```uml:class
class Responsible <<interface>> {
  handle(request: Request)
}
Client -right-> Responsible : " first"
Responsible --> Responsible : next
```

Druhá varianta zapouzdřuje celý řetězec do samostatného objektu, který obsahuje jednotlivé články a definuje jejich pořadí. Tento objekt potom zpracovává požadavek, aniž by jednotlivé články měly přehled nad tím, v jakém pořadí jsou uspořádány.

```uml:class
interface Chain {
  handle(request: Request)
}
class DefaultChain implements Chain {
}
class Responsible <<interface>> {
  handle(request: Request, restOfTheChain: Chain)
}
note right
  any responsible can call "restOfTheChain.handle"
  to continue request handling
  or not to end the handling
  (responsibles do not know their order)
end note
DefaultChain " 1 " o-down-> " * " Responsible : " sequence"
Client -right-> Chain
```

#### Implementace

Tato implementace počítá s tím, že jednotlivé články mají reference na ty následující a zpracování je tedy v rukou každého článku.

```java
interface Handler<T> {
    void handle(T request);
}
```

```java
public abstract class AbstractHandler<T> implements Handler<T> {
    private Handler<T> next;
    
    public void setNext(Handler<T> next) {
        this.next = next;
    }
    
    protected void handleByNext(T request) {
        if (this.next != null) {
            // předává řízení dalšímu článku v řetězci
            this.next.handle(request);
        }
    }
}
```

```java
public class LoginHandler extends AbstractHandler<Request> {
    @Override
    public void handle(Request request);
        if (containsLoginHeaders(request)) {
            if (tryLogin(request)) {
                request.setAuthorized(true);
            }
        }
        
        handleByNext(request);
    }
}
```

```java
public class AuthorizingHandler extends AbstractHandler<Request> {
    @Override
    public void handle(Request request);
        if (request.isAuthorized()) {
            handleByNext(request);
        } else {
            request.setResponseCode(403);
            request.setResponseBody("You are not authorized to do this!");
        }
    }
}
```

```java
public class PerformingHandler extends AbstractHandler<Request> {
    @Override
    public void handle(Request request);
        try {
            performAction(request);
            request.setResponseCode(200);
            request.setResponseBody("Action was performed!");
        } catch (Exception e) {
            request.setResponseCode(500);
            request.setResponseBody("Action failed: " + e.toString());
        }
    }
}
```

### Reference

- http://www.oodesign.com/chain-of-responsibility-pattern.html
- http://userpages.umbc.edu/~tarr/dp/lectures/Chain-2pp.pdf
