package dev.fnex.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

/** Base class: scaled virtual coordinate space + immediate-mode input hooks. */
public abstract class FnexScreen extends Screen {
    private long last = System.nanoTime();

    protected FnexScreen(String title) { super(Text.literal(title)); }

    protected float uiScale() { return 1f; }
    protected int vw() { return (int) (width / uiScale()); }
    protected int vh() { return (int) (height / uiScale()); }
    protected float dt() {
        long n = System.nanoTime();
        float d = (n - last) / 1e9f;
        last = n;
        return Math.min(d, 0.1f);
    }

    protected abstract void draw(DrawContext c, int mx, int my);
    protected boolean click(int mx, int my, int btn) { return false; }
    protected boolean drag(int mx, int my, int btn) { return false; }
    protected boolean release(int mx, int my, int btn) { return false; }
    protected boolean scroll(int mx, int my, double amount) { return false; }

    @Override public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        float s = uiScale();
        Draw.a = 1f;
        ctx.getMatrices().push();
        ctx.getMatrices().scale(s, s, 1f);
        draw(ctx, (int) (mouseX / s), (int) (mouseY / s));
        ctx.getMatrices().pop();
        Draw.a = 1f;
    }
    @Override public void renderBackground(DrawContext c, int mx, int my, float d) {}
    @Override public boolean shouldPause() { return false; }

    @Override public boolean mouseClicked(double x, double y, int b) { float s = uiScale(); return click((int) (x / s), (int) (y / s), b) || super.mouseClicked(x, y, b); }
    @Override public boolean mouseDragged(double x, double y, int b, double dx, double dy) { float s = uiScale(); return drag((int) (x / s), (int) (y / s), b) || super.mouseDragged(x, y, b, dx, dy); }
    @Override public boolean mouseReleased(double x, double y, int b) { float s = uiScale(); return release((int) (x / s), (int) (y / s), b) || super.mouseReleased(x, y, b); }
    @Override public boolean mouseScrolled(double x, double y, double h, double v) { float s = uiScale(); return scroll((int) (x / s), (int) (y / s), v) || super.mouseScrolled(x, y, h, v); }

    protected static boolean in(int mx, int my, int x, int y, int w, int h) { return mx >= x && mx < x + w && my >= y && my < y + h; }
}
