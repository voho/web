## Swing Layout manager

Nedílnou součástí uživatelských rozhraní (GUI) jsou okna, obsahující jednotlivé komponenty a ovládací prvky. Rozmístění těchto komponent je z uživatelského hlediska velmi důležité a proto by nad ním měl programátor grafických aplikací mít co největší kontrolu. Úkolem knihoven pro tvorbu uživatelského rozhraní je citlivě vyvažovat flexibilitu a snadnost použití, což jsou často protichůdné požadavky. Ideální je, když si může programátor sám volit mezi různými způsoby definice uživatelského rozhraní a postupy pro jeho tvorbu.

Ačkoliv už všechna vývojová prostřední nabízejí možnost automatizované a bezstarostné tvorby uživatelského rozhraní grafickými nástroji, někteří lidé stále preferují ruční definici uživatelského rozhraní a tyto nástroje zatracují - často se kritizuje nepřehlednost a malá strukturovanost výsledného kódu, poměrně zdlouhavá implementace změny a vyšší pravděpodobnost tvorby překomplikovaných struktur v uživatelském rozhraní. 

Hlavním frameworkem pro tvorbu a obsluhu uživatelského rozhraní v jazyce Java je knihovna Swing, která je založena na **komponentách**. Komponenty, které v sobě mohou obsahovat jiné komponenty, se nazývají **kontejnery**. Nejčastěji používaným kontejnerem je třída *JPanel* a *JFrame*. 

Každý kontejner má k dispozici právě jednu instanci speciální třídy, která se označuje jako **správce rozvržení** (layout manager). Tato třída v sobě obsahuje logiku pro rozmisťování komponent v kontejneru a ovlivňuje i jejich ukotvení a způsob změny velikostí jednotlivých komponent v závislosti na změně velikosti nadřazeného kontejneru. 

Těchto správců je ve standardní knihovně definováno několik a liší se především poměrem mezi flexibilitou a složitostí použití. Vhodnou volbou správce rozvržení a vnořováním jednotlivých komponent do sebe lze docílit takřka libovolného rozmístění prvků uživatelského rozhraní bez nutnosti používat grafický nástroj vývojového prostředí.

Správce rozvržení především zvyšuje sémantiku definic uživatelského rozhraní a činí je pružnějšími vůči změnám. 

### Obecné použití

1. Vytvořit novou instanci správce rozvržení.
1. Přiřadit tuto instanci metodou *setLayout()* jako **LayoutManager** kontejneru.
1. Přidávat komponenty do kontejneru metodou *add()*, jejíž druhý parametr umožňuje správci rozvržení předat další dodatečné informace vázané ke komponentě (typ i význam se liší dle typu layout manageru).

### Základní layout managery

#### FlowLayout

FlowLayout je základní správce rozvržení, který umisťuje komponenty do kontejneru postupně za sebou tak, jak jsou do něj přidány. Nastavit je možné zarovnání komponent (nalevo, na střed, napravo) a mezery mezi nimi. Při změně velikosti kontejneru nedochází ke změně velikosti komponent.

```java
public class FlowLayoutDemo extends JFrame {
    public FlowLayoutDemo() {
        super("FlowLayout");

        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));

        add(new JButton("First"));
        add(new JButton("Second"));
        add(new JButton("Third"));

        pack();
    }
}
```

![FlowLayout](https://dl.dropboxusercontent.com/u/5942837/voho.cz/image-wiki/FlowLayout.png)

#### BorderLayout

BorderLayout se řadí mezi jednoduché a přesto velmi užitečné správce rozvržení. Kontejner je rozdělen do pěti oblastí: sever (north), jih (south), východ (east), západ (west) a střed (center). Hlavní komponenta by měla být umístěna ve středu. Při změně velikosti kontejneru se mění i velikost komponent a největší prostor je vždy poskytnutý právě hlavní středové komponentě.

```java
public class BorderLayoutDemo extends JFrame {
    public BorderLayoutDemo() {
        super("BorderLayout");

        setLayout(new BorderLayout(5, 20));

        add(new JButton("North"), BorderLayout.NORTH);
        add(new JButton("South"), BorderLayout.SOUTH);
        add(new JButton("Center"), BorderLayout.CENTER);
        add(new JButton("East"), BorderLayout.EAST);
        add(new JButton("West"), BorderLayout.WEST);

        pack();
    }
}
```

![BorderLayout](https://dl.dropboxusercontent.com/u/5942837/voho.cz/image-wiki/BorderLayout.png)

#### GridLayout

GridLayout rozmisťuje komponenty do pravidelné mřížky, jejíž rozměry je možné specifikovat v konstruktoru. Komponenty lze pak jednoduše do kontejneru přidat bez parametrů. Jejich rozměry jsou při změně velikosti kontejneru měněny tak, aby všechny buňky mřížky byly stejně velké.

```java
public class GridLayoutDemo extends JFrame {
    public GridLayoutDemo() {
        super("GridLayout");

        setLayout(new GridLayout(3, 2, 5, 20));

        add(new JButton("A"));
        add(new JButton("B"));
        add(new JButton("C"));
        add(new JButton("D"));
        add(new JButton("E"));
        add(new JButton("F"));

        pack();
    }
}
```

![GridLayout](https://dl.dropboxusercontent.com/u/5942837/voho.cz/image-wiki/GridLayout.png)

#### GridBagLayout

GridBagLayout je tabulkově založený správce rozvržení. Každý element má svou přesnou polohu a rozlohu v mřížce, prostor zevnitř (inset) a zvenku (pad), zarovnání (anchor) a způsob vyplnění prostoru buňky (fill). Práce s tímto správcem není příliš příjemná, a proto je lepší si vytvořit nějaký pomocný objekt či metodu, která zápis rozvržení značně zkrátí.

```java
public class GridBagLayoutDemo extends JFrame {
    public GridBagLayoutDemo() {
        super("GridBagLayout");

        setLayout(new GridBagLayout());

        add(new JLabel("Login:"), new GBCBuilder(0, 0, 1, 1).inset(10, 5).alignRight().build());
        add(new JTextField(), new GBCBuilder(1, 0, 2, 1).fillX().build());
        add(new JLabel("Password:"), new GBCBuilder(0, 1, 1, 1).inset(10, 5).alignRight().build());
        add(new JTextField(), new GBCBuilder(1, 1, 2, 1).fillX().build());

        add(new JButton("Login"), new GBCBuilder(1, 2, 1, 1).pad(5, 20).build());
        add(new JButton("Exit"), new GBCBuilder(2, 2, 1, 1).pad(5, 5).build());

        add(new JLabel("Are you not a member?"), new GBCBuilder(1, 3, 2, 1).inset(0, 20).alignCenter().build());
        add(new JButton("Register here..."), new GBCBuilder(1, 4, 2, 1).fill().alignCenter().build());

        pack();
    }

    private static class GBCBuilder {
        private final GridBagConstraints gbc;

        public GBCBuilder(final int x, final int y, final int w, final int h) {
            gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.BASELINE_LEADING;
            gbc.gridx = x;
            gbc.gridy = y;
            gbc.gridwidth = w;
            gbc.gridheight = h;
        }

        public GBCBuilder weight(final double wx, final double wy) {
            gbc.weightx = wx;
            gbc.weighty = wy;
            return this;
        }

        public GBCBuilder alignLeft() {
            gbc.anchor = GridBagConstraints.WEST;
            return this;
        }

        public GBCBuilder alignCenter() {
            gbc.anchor = GridBagConstraints.CENTER;
            return this;
        }

        public GBCBuilder alignRight() {
            gbc.anchor = GridBagConstraints.EAST;
            return this;
        }

        public GBCBuilder fill() {
            gbc.fill = GridBagConstraints.BOTH;
            return this;
        }

        public GBCBuilder fillX() {
            gbc.fill = GridBagConstraints.HORIZONTAL;
            return this;
        }

        public GBCBuilder fillY() {
            gbc.fill = GridBagConstraints.VERTICAL;
            return this;
        }

        public GBCBuilder pad(final int padx, final int pady) {
            gbc.ipadx = padx;
            gbc.ipadx = pady;
            return this;
        }

        public GBCBuilder inset(final int insetx, final int insety) {
            gbc.insets = new Insets(insety, insetx, insety, insetx);
            return this;
        }

        public GridBagConstraints build() {
            return gbc;
        }
    }
}
```

![GridBagLayout](https://dl.dropboxusercontent.com/u/5942837/voho.cz/image-wiki/GridBagLayout.png)

### Reference

- http://docs.oracle.com/javase/tutorial/uiswing/layout/using.html