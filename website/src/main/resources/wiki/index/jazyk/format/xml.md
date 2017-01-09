## Jazyk XML

Jazyk XML je univerzální textový formát pro výměnu informací. Používá se především k reprezentaci strukturovaných dat. Za jeho specifikací stojí konsorcium W3C a jeho první verze byla uvolněna v roce 1996. 

### Syntaxe

#### Hlavička

Každý XML dokument začíná povinnou **hlavičkou**.

```xml
<?xml version="1.0" encoding="utf-8"?>
```

#### Tagy

Dále se skládá z rekurzivně vnořených značek, tzv. **tagů**. Tagy jsou **párové** a **nepárové**. Párové tagy se vyznačují tím, že se do nich mohou vpisovat další tagy nebo text.

```xml
<neparovy />
<parovy>obsah tagu</parovy>
```

Každý tag může mít tzv. **atributy**. Uvozovky jsou povinné a lze místo nich použít i apostrofy.

```xml
<neparovy atribut1="hodnota1" atribut2="hodnota2" />
<parovy atribut1="hodnota1" atribut2="hodnota2">obsah tagu</parovy>
```

#### Komentáře

V dokumentu XML mohou být i komentáře.

```xml
<!-- komentář -->
```

Každý XML dokument musí mít tzv. **kořenový prvek**. Všechny tagy musí být správně vnořeny a ukončeny. Jazyk XML rozlišuje velikost písmen.

#### Speciální znaky

| Speciální znak | Symbol | Náhrada
|---|---|---
| je menší (levá špičatá závorka) | < | &amp;lt; 
| je větší (pravá špičatá závorka) | > | &amp;gt;  
| amperstand | & | &amp;amp;
| apostrof | ' | &amp;apos;
| uvozovky | &quot; | &amp;quot;

### Příklady

```xml
<?xml version="1.0" encoding="utf-8"?>
<kontakt pridano="1.3.2003">
  <jmeno>Karel</jmeno>
  <prijmeni>Novák</prijmeni>
  <vek>30</vek>
  <adresa>
    <ulice>Blanická</ulice>
    <cp>42</cp>
    <mesto>Liptákov</mesto>
    <psc>12331</psc>
    <zeme>Česká republika</zeme>
  </adresa>
</kontakt>
```

### Reference

- http://www.w3.org/XML/
- http://www.w3schools.com/xml/xml_whatis.asphttp://voho.cz/wiki/poznamky-xml/