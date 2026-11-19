package dev.fnex;

import dev.fnex.config.ConfigManager;
import dev.fnex.module.Modules;
import dev.fnex.util.UpdateChecker;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FnexClient implements ClientModInitializer {
    public static final String ID = "fnex";
    public static final Logger LOG = LoggerFactory.getLogger("FNEX");
    public static final String VERSION = FabricLoader.getInstance().getModContainer(ID)
            .map(c -> c.getMetadata().getVersion().getFriendlyString()).orElse("1.0.0");

    @Override public void onInitializeClient() {
        Modules.init();
        ConfigManager.init();
        ClientTickEvents.END_CLIENT_TICK.register(Modules::tick);
        HudRenderCallback.EVENT.register((ctx, tickCounter) -> Modules.renderHud(ctx));
        ClientLifecycleEvents.CLIENT_STOPPING.register(c -> { Modules.shutdown(); ConfigManager.save(); });
        UpdateChecker.start();
        LOG.info("FNEX CLIENT {} loaded", VERSION);
    }
}
