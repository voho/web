## Formát SVG

Jazyk SVG (Scalable Vector Graphics) je značkovací jazyk, který vychází z jazyka XML a popisuje vektorovou grafiku. Jedná se o otevřený grafický formát. Nejznámějším grafickým editorem, který umožňuje grafiku ve formátu SVG vytvářet a editovat, je [Inkscape](http://inkscape.org/). Výhodou SVG je i to, že se velmi snadno generuje strojově.

### Kostra dokumentu

```xml
<?xml version="1.0" standalone="no"?>
<!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" 
"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">
<svg width="100%" height="100%" version="1.1" xmlns="http://www.w3.org/2000/svg">

<!-- tělo dokumentu -->

</svg>
```

### Základní útvary

#### Úsečka

```xml
<line x1="178" y1="102" x2="236" y2="37" style="stroke:rgb(255,20,20);stroke-width:1;stroke-opacity:0.8;" />
```

#### Obdélník

```xml
<rect x="0" y="0" width="419" height="430" style="fill:rgb(30,30,30);" /><rect x="84" y="0" width="100" height="52" style="fill:rgb(255,255,255);stroke-width:1;stroke:rgb(120,120,120);" />
```

#### Kruh

```xml
<circle cx="314" cy="106" r="5" style="fill:rgb(200,200,200);" /><circle cx="286" cy="136" r="5" style="fill:rgb(200,200,200);" />
```

### Reference

- http://www.w3.org/Graphics/SVG/
- http://inkscape.org/