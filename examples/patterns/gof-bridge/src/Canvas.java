public class Canvas {
    private final Drawer drawer;

    public Canvas(final Drawer drawer) {
        this.drawer = drawer;
    }

    public void drawLine(final int x1, final int y1, final int x2, final int y2) {
        this.drawer.drawLine(x1, y1, x2, y2);
    }

    public void drawCircle(final int cx, final int cy, final int r) {
        this.drawer.drawCircle(cx, cy, r);
    }

    public void drawRectangle(final int x, final int y, final int w, final int h) {
        this.drawer.drawLine(x, y, x + w, y); // top 
        this.drawer.drawLine(x, y, x, y + h); // left 
        this.drawer.drawLine(x, y + h, x + w, y + h); // bottom 
        this.drawer.drawLine(x + w, y, x + w, y + h); // right 
    }
}
