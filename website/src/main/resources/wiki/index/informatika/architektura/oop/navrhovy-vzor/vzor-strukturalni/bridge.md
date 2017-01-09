## GoF: Bridge (most)

Bridge je návrhový vzor pro strukturu objektů. Používá se, když chceme oddělit abstrakci od její implementace tak, aby se obě mohly měnit nezávisle. Klient posléze využije některou z implementací nepřímo prostřednictvím abstrakce. Protože je "oddělení abstrakce od implementace" velmi obecný pojem, je vhodné uvést několik příkladů, jak řez provést:

- platformově nezávislý kód vs. platformově závislý kód
- kód nezávislý na formátu dat vs. kód závislý na formátu dat
- (obecně jakýkoliv specifický kód vs. společný kód)
- doménový kód vs. infrastrukturní kód
- front-end vs. back-end

### Řešení

Prvním krokem je návrh **rozhraní abstrakce** (*AbstractionApi*), které bude využívat klient. Tento návrh by tedy měl být klientem specifikován. Druhým krokem je návrh **rozhraní implementace** (*ImplementationApi*), které bude abstrakce využívat pro přístup k implementaci. Návrh bude omezený tím, jaké funkce jsou implementace schopny v průniku poskytnout. Třetím krokem je vytvoření vlastního "mostu", tedy kódu, který bude realizovat abstrakci pomocí implementace. Tento kód nesmí být závislý na žádné konkrétní implementaci, pouze na jejich rozhraní (*ImplementationApi*). Lze jej realizovat například jako abstraktní třídu, která v konstruktoru obdrží některou z konkrétních implementací (*Implementation1* nebo *Implementation2*). Pokud jiní klienti vyžadují odlišné ozhraní, lze vytvořit potomka *AbstractionApi* a rozdíly implementovat v něm (*RefinedAbstractionApi*).

```uml:class
AbstractionApi -right-> ImplementationApi
RefinedAbstractionApi-up-|> AbstractionApi
Implementation1 .up.|> ImplementationApi
Implementation2 .up.|> ImplementationApi
```

### Související vzory

- [Adapter](wiki/adapter) - vzor Adapter se využívá pro přizpůsobení již existující třídy pro nové rozhraní a oba objekty (existující i nový) mají stejnou podstatu
- [State](wiki/state) - u vzoru State je vytvořená třída pouze externalizovaným vnitřním stavem původního objektu a je s ním tedy těsně svázaná
- [Strategy](wiki/strategy) - vzor Strategy ukazuje, jak externalizovat určitý postup či chování objektu, proto je vztah mezi objektem a strategií silnější a koncept strategie je přesněji definovaný než koncept závislosti

### Příklad

Následující příklad bude představovat jednoduchý vykreslovací program a řez bude proveden mezi klientem (kreslící program) a jeho závislostí (grafická knihovna AWT pro vykreslování UI v Javě). Cílem kreslícího programu je vystavit klientům jednoduché rozhraní pro vykreslování komplexních objektů, jako je čára, kružnice, obdélník (a různé jejich kombinace) a zároveň jim umožnit volbu kvality, v jaké se bude grafika vykreslovat.

```uml:class
Canvas -right-> Drawer
ExtendedCanvas -up-|> Canvas
LowQualityDrawer .up.|> Drawer
HighQualityDrawer .up.|> Drawer
```

#### Grafická knihovna

Závislostí kreslícího programu je grafická knihovna. Po té se vyžaduje, aby uměla nakreslit kružnici a čáru. Toto rozhraní je kořenem první hierarchie.

```java
public interface Drawer {
    void drawCircle(int cx, int cy, int r);

    void drawLine(int x1, int y1, int x2, int y2);
}
```

První implementace kreslí vše černobíle a v nízké kvalitě.

```java
public class LowQualityDrawer implements Drawer {
    private final Graphics2D graphics;

    public LowQualityDrawer(final Graphics2D graphics) {
        this.graphics = graphics;
        this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    @Override
    public void drawCircle(final int cx, final int cy, final int r) {
        this.graphics.setColor(Color.BLACK);
        final int d = r + r;
        this.graphics.drawOval(cx - r, cy - r, d, d);
    }

    @Override
    public void drawLine(final int x1, final int y1, final int x2, final int y2) {
        this.graphics.setColor(Color.BLACK);
        this.graphics.drawLine(x1, y1, x2, y2);
    }
}
```

Druhá implementace kreslí barevně a ve vysoké kvalitě (s vyhlazováním).

```java
public class HighQualityDrawer implements Drawer {
    private static final Stroke CIRCLE_STROKE = new BasicStroke(5f);
    private static final Stroke LINE_STROKE = new BasicStroke(1f);
    private final Graphics2D graphics;

    public HighQualityDrawer(final Graphics2D graphics) {
        this.graphics = graphics;
        this.graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    public void drawCircle(final int cx, final int cy, final int r) {
        final int d = r + r;
        this.graphics.setColor(Color.RED);
        this.graphics.setStroke(CIRCLE_STROKE);
        this.graphics.drawOval(cx - r, cy - r, d, d);
    }

    @Override
    public void drawLine(final int x1, final int y1, final int x2, final int y2) {
        this.graphics.setColor(Color.BLUE);
        this.graphics.setStroke(LINE_STROKE);
        this.graphics.drawLine(x1, y1, x2, y2);
    }
}
```

#### Hierarchie kreslícího programu

```java
public class Canvas {
    private final Drawer drawer;

    public Canvas(final Drawer drawer) {
        this.drawer = drawer;
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        this.drawer.drawLine(x1, y1, x2, y2);
    }

    public void drawCircle(int cx, int cy, int r) {
        this.drawer.drawCircle(cx, cy, r);
    }

    public void drawRectangle(int x, int y, int w, int h) {
        this.drawer.drawLine(x, y, x + w, y); // top 
        this.drawer.drawLine(x, y, x, y + h); // left 
        this.drawer.drawLine(x, y + h, x + w, y + h); // bottom 
        this.drawer.drawLine(x + w, y, x + w, y + h); // right 
    }
}
```

```java
public class ExtendedCanvas extends Canvas {
    public ExtendedCanvas(final Drawer drawer) {
        super(drawer);
    }

    public void drawDoubleCircle(int cx, int cy, int r1, int r2) {
        drawCircle(cx, cy, r1);
        drawCircle(cx, cy, r2);
    }

    public void drawCircledSquare(int cx, int cy, int r) {
        final int a = Math.round(2.0f * (float) (r / Math.sqrt(2.0)));
        final int px = cx - a / 2;
        final int py = cy - a / 2;
        drawRectangle(px, py, a, a);
        drawCircle(cx, cy, r);
    }

    public void drawSquaredCircle(int cx, int cy, int r) {
        final int d = r + r;
        final int px = cx - r;
        final int py = cy - r;
        drawRectangle(px, py, d, d);
        drawCircle(cx, cy, r);
    }
}
```

### Reference

- https://sourcemaking.com/design_patterns/bridge
- http://c2.com/cgi/wiki?BridgePattern
- https://dzone.com/articles/design-patterns-bridge