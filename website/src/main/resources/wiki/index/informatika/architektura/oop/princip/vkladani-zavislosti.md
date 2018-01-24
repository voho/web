## Vkládání závislostí (dependency injection)

> The basic idea of the Dependency Injection is to have a separate object, an assembler, that populates a field in a class with an appropriate implementation for the interface. *Martin Fowler*

V [objektově orientovaných](wiki/oop) programovacích [jazycích](wiki/jazyk) se program skládá ze vzájemně propojených objektů. Pokud objekt *A* vyžaduje pro svou činnost objekt *B*, říkáme, že je objekt *A* **závislý** na objektu *B*. Při pohledu z druhé strany tvoří objekt *B* **závislost** objektu *A*.

Z toho vyplývá, že si program složený z objektů můžeme představit jako [orientovaný graf](wiki/graf), ve kterém objekty představují uzly a hrany jsou závislosti mezi nimi. Program může vypadat třeba takto:

```dot:digraph
node[shape=circle,margin=0]
C1->C2->C3
C4->C3
C5
C6
C6->C2
C7->C2
C8->C9
C10->C9
C9->C3
```

Kdo rozhoduje o tom, jaké konkrétní instance závislosti se použijí a kde je zodpovědný za jejich vytváření?

První možností je, že si o svých závislostech rozhoduje sám objekt. V konstruktoru tedy vytvoří všechny své závislosti sám. Výhodou tohoto přístupu je, že objekt lze ihned snadno použít jako samostatnou komponentu. Nevýhodou je, že jeho závislosti nemůžeme přepoužít jinde a nemůžeme je nijak změnit.

Druhou možností je, že objekt závislosti dostane zvnějšku, například pomocí konstruktoru. V tomto případě objekt spoléhá pouze na to, že závislosti budou implementovat určité rozhraní a nezajímá ho, jaké instance to přesně budou. Tím pádem ho lze například při testování "podvést" a místo skutečných instancí mu dát pouze jejich mocky (simulace). 

Druhému způsobu se říká **vkládání závislostí**, protože jsou závislosti vkládány do objektu zvnějšku.

### Mechanismy vkládání

#### Vkládání konstruktorem (constructor injection)

Výhodou tohoto způsobu je, že všechny závislosti jsou definovány najednou a objekt je tak po svém vytvoření plně funkční. Zároveň jsou všechny závislosti konstantní (proto je u proměnných klíčové slovo *final*).

Nesporným plusem je i to, že na závislosti nelze zapomenout, protože chybějící nebo nesprávné parametry konstruktoru ověří již kompilátor.

Nevýhodou je nepřehledný zápis v případě většího počtu závislostí - pokud má konstruktor příliš mnoho parametrů, stává se nepřehledný a objekt obtížně použitelný. Ale lze to řešit tak, že se více závislostí vnoří do jedné a to tak dlouho, dokud se počet neustálí na rozumném počtu (zhruba do počtu 4).

```java
class Dependant {
	private final Dependency a;
	
	public Dependant(Dependency a) {
		this.a = a;
	}
	
	// ...
}
```

#### Vkládání setterem (setter injection)

Vkládání setterem zpopularizoval [Spring Framework](http://projects.spring.io/spring-framework/). Ke každé závislosti je vytvořen odpovídající setter a okolí tyto settery zavolá před prvním použitím objektu.

Nevýhodou je, že není zaručeno, že budou tyto settery skutečně před použitím objektu zavolány. Proto může dojít během vykonávání programu k chybě (typicky *NullPointerException*), jakmile chybí potřebná závislost. U frameworků to typicky nevadí, protože vložení všech potřebných závislostí zaručuje framework.

Nicméně, na druhou stranu je zápis přehlednější a vytvoření objektu snadnější. Konstruktor je typicky bez parametrů. A závislosti lze teoreticky i za běhu vyměnit, což lze chápat jako výhodu i nevýhodu zároveň.

```java
class Dependant {
	private Dependency a;
	
	// ...
	
	public void setDependency(Dependency a) {
		this.a = a;
	}
}
```

## Reference

- http://www.martinfowler.com/articles/injection.html
- http://picocontainer.codehaus.org/introduction.html
- http://picocontainer.codehaus.org/constructor-injection.html
- http://picocontainer.codehaus.org/setter-injection.html
