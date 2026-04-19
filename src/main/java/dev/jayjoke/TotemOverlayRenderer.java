package dev.jayjoke;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class TotemOverlayRenderer {

    private static final long START_TIME = System.currentTimeMillis();

    // Border settings
    private static final int THICKNESS = 8;
    private static final int BASE_ALPHA = 160;

    public static void register() {
        HudRenderCallback.EVENT.register(TotemOverlayRenderer::onHudRender);
    }

    private static void onHudRender(DrawContext context, net.minecraft.client.render.RenderTickCounter tickCounter) {
        if (!ModConfig.get().enabled) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;
        if (client.currentScreen != null) return;

        ItemStack offhand = client.player.getStackInHand(Hand.OFF_HAND);
        if (offhand.isOf(Items.TOTEM_OF_UNDYING)) return;

        int w = client.getWindow().getScaledWidth();
        int h = client.getWindow().getScaledHeight();

        // Pulsing alpha via sine wave (~0.83 Hz)
        long elapsed = System.currentTimeMillis() - START_TIME;
        double pulse = Math.sin((elapsed / 600.0) * Math.PI);
        int alpha = (int) (BASE_ALPHA * (0.5 + 0.5 * pulse));
        alpha = Math.max(0, Math.min(255, alpha));

        int color = (alpha << 24) | 0x00FF0000;

        context.fill(0,          0,          w,          THICKNESS,  color); // top
        context.fill(0,          h - THICKNESS, w,        h,          color); // bottom
        context.fill(0,          THICKNESS,  THICKNESS,  h - THICKNESS, color); // left
        context.fill(w - THICKNESS, THICKNESS, w,        h - THICKNESS, color); // right
    }
}
