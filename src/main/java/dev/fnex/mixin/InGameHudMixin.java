package dev.fnex.mixin;

import dev.fnex.module.Modules;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    /** Replaces the vanilla crosshair with the FNEX crosshair when that module is enabled. */
    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void fnex$crosshair(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (Modules.crosshair != null && Modules.crosshair.enabled) {
            Modules.crosshair.render(context);
            ci.cancel();
        }
    }
}
