package dev.jayjoke;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main implements ClientModInitializer {

    public static final String MOD_ID = "totemindicator";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Totem Indicator initialising...");

        ModConfig.load();
        TotemOverlayRenderer.register();

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            TotemCommand.register(dispatcher)
        );

        LOGGER.info("Totem Indicator ready. Use /totemindicator toggle to enable/disable.");
    }
}
