package gof.bridge;

public class ExtendedCanvas extends Canvas {
    public ExtendedCanvas(final Drawer drawer) {
        super(drawer);
    }

    public void drawDoubleCircle(final int cx, final int cy, final int r1, final int r2) {
        drawCircle(cx, cy, r1);
        drawCircle(cx, cy, r2);
    }

    public void drawCircledSquare(final int cx, final int cy, final int r) {
        final int a = Math.round(2.0f * (float) (r / Math.sqrt(2.0)));
        final int px = cx - a / 2;
        final int py = cy - a / 2;
        drawRectangle(px, py, a, a);
        drawCircle(cx, cy, r);
    }

    public void drawSquaredCircle(final int cx, final int cy, final int r) {
        final int d = r + r;
        final int px = cx - r;
        final int py = cy - r;
        drawRectangle(px, py, d, d);
        drawCircle(cx, cy, r);
    }
}
