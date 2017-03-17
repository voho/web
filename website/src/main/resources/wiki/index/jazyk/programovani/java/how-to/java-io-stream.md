## Vstupně-výstupní proudy (I/O stream)

Datový proud lze chápat jako teoreticky nekonečnou posloupnost dat, která je poskytována na vyžádání (on-demand), a to pouze jedním směrem. Podle směru, jakým se na datový proud nahlíží, se jedná buď o proud **vstupní** (teče směrem z vnějšího světa do objektu, slouží pro příjem či načítání vnějších dat), nebo o proud **výstupní** (teče z objektu do vnějšího světa, slouží k odesílání či k zápisu dat). Výhodou datových proudů je jejich intuitivnost a možnost jejich paralelního i sériového skládání.

Ve standardní knihovně jazyka Java lze nalézt několik tříd představujících rozličné datové proudy, které poskytují slušné zázemí pro nejrůznější druhy komunikace. S jejich pomocí lze přenášet jak holá binární data bez sémantiky (bajty), tak i znaky, řetězce či celé  serializované objekty.

### Příklady proudů

Příkladem proudů může být uživatelský vstup o neznámé velikosti, data z měřících zařízení v reálném čase a obecně všechny datové toky, které svým objemem mohou překroči velikost volné operační paměti nebo se požaduje jednoprůchodové zpracování "kousek po kousku" bez nutnosti načítání celého vstupu.

### Korektní zavírání proudů

U proudů v jazyce Java platí pravidlo, že stačí zavřít vždy jen ten "nejvyšší" proud, tedy ten, který již není vstupem jiného proudu. V něm vnořené proudy jsou uzavřeny automaticky, protože "vyšší" proud vlastní referenci na proud "nižší" (aby z něj mohl načítat data), ale ne naopak. Výstupní proudy, které obsahují nějaký druh vyrovnávací paměti při uzavření provedou automatickou synchronizaci a vyrovnávací paměť vyprázdní, není tedy třeba před jejich uzavřením explicitně volat metodu *flush()*.

#### JDK 7 a novější

V jazyce Java 7 a novějších existuje tzv. konsturkce **try-with-resources**, která umožňuje přehlednější zápis algoritmů pracujících s proudy. Blok *try* má nově volitelné tělo (uzavřené v závorkách), které může obsahovat deklaraci a inicializaci proudů. Tyto proudy jsou po skončení bloku *try* uzavřeny, a to jak v běžné situaci, tak při vyjímce. Jediným požadavkem na inicializaci proudu v bloku *try* je, aby tento proud implementoval rozhraní *AutoCloseable**.

```java
try (
  InputStream stream1 = openSourceStream();
  OutputStream stream2 = openTargetStream();
) {
  // ...
  // (do something with streams)
  // ...
} catch (Exception x) {
  // shit happens
}

// stream1+stream2 are always closed at this point
```

#### JDK 6 a starší

V následujícím příkladu způsobí uzavření "vyššího" proudu *BufferedInputStream* uzavření "nižšího" vnořeného proudu *FileInputStream*.

```java
InputStream is = null;

try {
  is = new BufferedInputStream(new FileInputStream(file));
  // ...
  // (práce s proudem)
  // ...
} finally {
  if (is != null) {
    is.close();
  }
}
```

### Reference

- http://www.javamex.com/tutorials/io/input_stream.shtml