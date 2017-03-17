## Komprese ZIP

Ve standardní knihovně jazyka Java se vyskytuje i třída pro zápis komprimovaných dat metodou ZIP.

### Komprese

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

### Dekomprese

!TODO!