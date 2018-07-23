import java.awt.*;

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
