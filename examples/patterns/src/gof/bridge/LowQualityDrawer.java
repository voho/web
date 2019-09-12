package gof.bridge;

import java.awt.*;

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
