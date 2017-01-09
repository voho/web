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

##### Proudové kopírování souborů

Následující metoda ukazuje jak se realizuje proudové kopírování souborů (nebo obecně dvou libovolných proudů, pokud se *FileInputStream* a *FileOutputStream* nahradí jinými proudy).

```java
InputStream is = null;
OutputStream os = null;

try {
  is = new BufferedInputStream(new FileInputStream(srcFile));
  os = new BufferedOutputStream(new FileOutputStream(destFile));

  while (true) {
    // načíst znak ze vstupu
    int data = is.read();
    // ověřit konec
    if (data != -1) {
      // konec toku
      break;
    }
    // zapsat znak na výstup
    os.write(data);
  }
} finally {
  if (os != null) {
    os.close();
  }
  if (is != null) {
    is.close();
  }
}
```

##### Komprese ZIP

Ve standardní knihovně jazyka Java se vyskytuje i třída pro zápis komprimovaných dat metodou ZIP.

```java
ZipOutputStream zos = null;

try {
  // otevřít proud
  zos = new ZipOutputStream(new FileOutputStream(file));
  
  // vložit záznam (textový soubor)
  zos.putNextEntry(new ZipEntry("file.txt"));
  zos.write("example".getBytes("UTF-8"));
  zos.closeEntry();
  
  // vložit záznam (obrázek)
  zos.putNextEntry(new ZipEntry("image.png"));
  ImageIO.write(image, "png", zos);
  zos.closeEntry();
  
  // uzavřít archiv
  zos.finish();
} finally {
  if (zos != null) {
    // uzavřít proud
    zos.close();
  }
}
```

##### Načtení souboru do pole bajtů

Uvedená metoda načte soubor do pole typu *byte[]*.

```java
private static byte[] readFileToBytes(File file) throws FileNotFoundException, IOException {
  InputStream is = null;
  ByteArrayOutputStream os = null;

  try {
    is = new BufferedInputStream(new FileInputStream(file));
    os = new ByteArrayOutputStream();

    while (true) {
      int data = is.read();

      if (data == -1) {
        break;
      }

      os.write(data);
    }

    return os.toByteArray();
  } finally {
    if (os != null) {
      os.close();
    }
    if (is != null) {
      is.close();
    }
  }
}
```

##### Stažení obsahu URL do souboru

Uvedená metoda stáhne obsah umístěný na vzdálené adrese a zapíše jej do požadovaného souboru. Zápis probíhá proudově.

```java
private static void download(String href, File target) throws IOException {
  URL url = new URL(href);
  InputStream is = null;
  OutputStream os = null;

  try {
    is = new BufferedInputStream(url.openStream(), 4096);
    os = new BufferedOutputStream(new FileOutputStream(target), 4096);

    while (true) {
     int i = is.read();

      if (i == -1) {
        break;
      }

      os.write(i);
    }
  } finally {
    if (os != null) {
      os.close();
    }
    if (is != null) {
      is.close();
    }
  }
}
```

### Reference

- http://www.javamex.com/tutorials/io/input_stream.shtml