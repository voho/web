## Dynamická proxy

Od JDK verze 1.3 je možné poměrně jednoduše vytvářet za běhu dynamické proxy k rozličným objektům. Proxy je takový prostředník mezi nějakým objektem a vnějším světem. Navenek se tváří jako vnořený objekt, ale ve skutečnosti jeho chování může pozměnit, protože má možnost rozhodnutí, zda volání pouze přepošle vnořenému objektu, nebo místo něj vykoná nějakou vlastní operaci. Případně může udělat i kombinaci obého - zavolá metodu vnořeného objektu a potom s výsledkem může ještě libovolně manipulovat. Proxy se nejčastěji používají třeba pro přidání logování k vnořenému objektu, kontrole oprávnění pro přístup k němu, a podobně.

Všechny funkce kolem vytváření a správy dynamické proxy zajišťuje knihovní třída *java.lang.reflect.Proxy*. 

K vytvoření dynamické proxy jsou potřeba tři věci:

- classloader, ve kterém bude proxy třída vytvořena
- pole rozhraní specifikující metody, které bude proxy sledovat
- invocation handler, což je objekt, který se provolá při zavolání sledovaných metod

### Příklad

Nejprve vytvoříme nějaké rozhraní. Můžeme mít třeba rozhraní představující zvíře vydávající nějaký zvuk:

```java
public static interface Animal {
	String getSound();
}
```

Dále připravíme třídu, která toto rozhraní bude implementovat a doplníme implementaci metod. Může to být třeba sova, která houká:

```java
public static class Owl implements Animal {
	@Override
	public String getSound() {
		return "hooo";
	}
}
```

Potom už nic nebrání vytvoření dynamické proxy. Proxy bude mít za úkol původní zvuk zvířete třikrát zopakovat. Když tedy někdo místo původní čisté třídy dostane naší proxy, bude místo jednoho zvuku dostávat hned tři zvuky. A to všechno bez jakéhokoliv podezření, že dostává místo své požadované třídy nějakou proxy:

```java
final Animal owl = new Owl();

final Animal owlProxy = (Animal) Proxy.newProxyInstance(owl.getClass().getClassLoader(), new Class<?>[]{Animal.class}, new InvocationHandler() {
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// call the original object (we know it returns string)
		String sound = (String) method.invoke(owl, args);
		// join three returned strings together
		return sound + " " + sound + " " + sound;
	}
});

// prints "hooo"
System.out.println("normal owl: " + owl.getSound());
// prints "hooo hooo hooo"
System.out.println("proxied owl: " + owlProxy.getSound());
```

Třída *Proxy* nabízí i další metody, kterými například můžeme zjišťovat, zda je daný objekt proxy či ne:

```java
// prints false
System.out.println("is normal owl proxy? " + Proxy.isProxyClass(owl.getClass()));
// prints true
System.out.println("is proxied owl proxy? " + Proxy.isProxyClass(owlProxy.getClass()));
```

### Reference

- http://java.sun.com/j2se/1.3/docs/guide/reflection/proxy.html