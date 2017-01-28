## Classloader

Classloader je komponenta JRE (runtime prostředí Javy), která se stará o načítání tříd a dalších potřebných zdrojů za běhu JVM. Není těžké implementovat si svůj vlastní. Jediné, co je potřeba udělat, je vytvořit nového potomka *java.lang.Classloader*,překrýt metodu *findClass(String)* a samozřejmě nový classloader korektně použít pro načítání tříd. 

Classloader má následující úkoly:

- kontrola, zda je třída načtena
- kontrola, zda byla třída načtena systémovým classloaderem
- načtení třídy
- definování třídy ("vložení" jejího zdroje do JVM)
- předání požadované třídy klientovi

### Hierarchie classloaderů

Důležité je vědět, že classloadery mohou být a také často jsou uspořádány hierarchicky. Při použití classloaderu je nejprve o třídu požádán jeho rodič (pokud existuje) a až poté on sám. V praxi to znamená, že se váš vlastní classloader ke slovu nemusí dostat, pokud je požadovaná třída již načtená. Toto chování lze samozřejmě obejít správným překrytím metody *loadClass()*.

Přímo v JVM existuje tato hierarchie vestavěných classloaderů:

- **bootstrap classloader** - napsaný v nativním kódu, načítá základní třídy jazyka (jre/lib)
- **extension classloader** - načítá třídy které patří do různých rozšíření Javy (jre/lib/ext)
- **system classloader** - načítá třídy umístěné na systémové classpath
- **vlastní classloadery** - vlastní classloadery

### Mechanismus načítání tříd

Výchozí mechanismus načítání tříd je dobře vidět na kódu rodičovské třídy *java.lang.ClassLoader* (kód byl pro snadnější pochopení zkrácen pouze na ty nejpodstatnější části, pro kompletní zdrojový kód si projděte své JDK):

```java
protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
  Class c = findLoadedClass(name);

  if (c == null) {
    // třída ještě není načtena
    try {
      if (parent != null) {
        // o načtení požádáme rodičovský classloader
        c = parent.loadClass(name, false);
      } else {
        // není rodičovský classloader - jako fallback se použije bootstrap
        c = findBootstrapClassOrNull(name);
      }
    } catch (ClassNotFoundException e) {
      // rodič třídu nenalezl - chyba se ignoruje
    }
    
    if (c == null) {
      // třída není dostupná, ke slovu se konečně dostává tento classloader
      c = findClass(name);
    }
  }
  
  return c;
}
```

### Vlastní classloader

#### Classloader

Pozornost věnujte zejména metodě *findClass* a konstruktorům. Pokud žádné nejsou definovány, zůstává výchozí konstruktor z předka, který je definován tak, že se jako rodičovský classloader použije **systémový classloader**. Pokud tomu chcete předejít, je nutné konstruktor překrýt a jako rodičovský classloader předkovi poslat prázdnou hodnotu *null*. I pak je ale třeba dávat pozor na různé fallbacky, které jsou zabudovány v metodě *loadClass* (viz výchozí mechanismus popsaný výše).

```java
public class MyClassLoader extends ClassLoader {
    public MyClassLoader() {
        // žádný rodičovský classloader (pozor, existují fallbacky)
        super(null);
    }

    public MyClassLoader(ClassLoader parent) {
        // poskytujeme rodičovský classloader
        super(parent);
    }
    
    @Override
    protected Class<?> findClass(final String name) throws ClassNotFoundException {
        System.out.println("Finding class: " + name);

        // zde se má načíst bytekód třídy nebo nastat výjimka ClassNotFoundException
        final byte[] classBinaryCode = this.loadClassDataOrThrowException(name);
        
        // defineClass() je magie, která definuje třídu v JVM na základě jejího bytekódu
        return this.defineClass(name, classBinaryCode, 0, classBinaryCode.length);
    }
}
```

#### Použití

```java
// CHYBA: náš classloader třídu nezná a neposkytli jsme rodičovský systémový classloader
Class<?> something = Class.forName("cz.voho.classloader.Main", 
                                   true, new MyClassLoader());

// OK: náš classloader třídu nezná, ale poskytli jsme rodičovský classloader, který ano
Class<?> something = Class.forName("cz.voho.classloader.Main", 
                                   true, new MyClassLoader(ClassLoader.getSystemClassLoader()));

// další způsoby načtení třídy pomocí vlastního classloaderu
ClassLoader classloader = new MyClassLoader();
ClassLoader classloader = new MyClassLoader(ClassLoader.getSystemClassLoader());
ClassLoader classloader = new MyClassLoader(this.getClass().getClassLoader());
Class<?> something = classloader.loadClass("org.w3c.dom.Element");
```