package dev.fnex.gui;

import dev.fnex.module.Modules;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;

public final class Sounds {
    public static void click() {
        if (Modules.sounds == null || !Modules.sounds.enabled) return;
        MinecraftClient.getInstance().getSoundManager().play(
                PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK.value(), 1.2f, Modules.sounds.volume.f()));
    }
}
