package dev.jayjoke;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

public class TotemCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(
            ClientCommandManager.literal("totemindicator")
                .then(ClientCommandManager.literal("toggle")
                    .executes(ctx -> {
                        ModConfig config = ModConfig.get();
                        config.enabled = !config.enabled;
                        ModConfig.save();

                        String state = config.enabled ? "§aenabled" : "§cdisabled";
                        ctx.getSource().sendFeedback(
                            Text.literal("§6[Totem Indicator] §rMod " + state + "§r.")
                        );
                        return 1;
                    })
                )
                .then(ClientCommandManager.literal("status")
                    .executes(ctx -> {
                        String state = ModConfig.get().enabled ? "§aenabled" : "§cdisabled";
                        ctx.getSource().sendFeedback(
                            Text.literal("§6[Totem Indicator] §rCurrently " + state + "§r.")
                        );
                        return 1;
                    })
                )
        );
    }
}
