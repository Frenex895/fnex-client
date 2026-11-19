package dev.fnex.module;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayDeque;

/** Per-frame mouse polling so that CPS is accurate (not limited to 20 ticks/s). */
public final class Cps {
    private static final ArrayDeque<Long> L = new ArrayDeque<>(), R = new ArrayDeque<>();
    private static boolean ld, rd;

    public static void update() {
        long h = MinecraftClient.getInstance().getWindow().getHandle(), now = System.currentTimeMillis();
        boolean l = GLFW.glfwGetMouseButton(h, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
        boolean r = GLFW.glfwGetMouseButton(h, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
        if (l && !ld) L.add(now);
        if (r && !rd) R.add(now);
        ld = l; rd = r;
        while (!L.isEmpty() && now - L.peekFirst() > 1000) L.pollFirst();
        while (!R.isEmpty() && now - R.peekFirst() > 1000) R.pollFirst();
    }
    public static int left() { return L.size(); }
    public static int right() { return R.size(); }
    public static boolean leftDown() { return ld; }
    public static boolean rightDown() { return rd; }
}
