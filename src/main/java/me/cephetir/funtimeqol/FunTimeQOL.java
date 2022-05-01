package me.cephetir.funtimeqol;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import me.cephetir.funtimeqol.features.AutoAHRefresh;
import me.cephetir.funtimeqol.features.HighlightingItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.LiteralText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FunTimeQOL implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("FunTimeQOL");

    @Override
    public void onInitialize() {
        LOGGER.info("[FunTimeQOL] Starting...");
        ClientTickEvents.START_CLIENT_TICK.register((client) -> AutoAHRefresh.onTick());
        ClientCommandManager.DISPATCHER.register(ClientCommandManager.literal("ftq")
                .then(ClientCommandManager.literal("toggle")
                        .then(ClientCommandManager.literal("refresh")
                                .executes((context) -> {
                                    AutoAHRefresh.enabled = !AutoAHRefresh.enabled;
                                    String enabled = "§aenabled";
                                    if (!AutoAHRefresh.enabled) enabled = "§4disabled";
                                    context.getSource().sendFeedback(
                                            new LiteralText("§c[FunTimeQOL] §eAuto auction refresh is " + enabled + "§e!")
                                    );
                                    return 1;
                                })
                        )
                        .then(ClientCommandManager.literal("highlighting")
                                .executes((context) -> {
                                    HighlightingItems.enabled = !HighlightingItems.enabled;
                                    String enabled = "§aenabled";
                                    if (!HighlightingItems.enabled) enabled = "§4disabled";
                                    context.getSource().sendFeedback(
                                            new LiteralText("§c[FunTimeQOL] §eItem highlighting is " + enabled + "§e!")
                                    );
                                    return 1;
                                })
                        )
                )
                .then(ClientCommandManager.literal("changeTime")
                        .then(ClientCommandManager.argument("time", IntegerArgumentType.integer(1, 100))
                                .executes((context) -> {
                                    AutoAHRefresh.wait = context.getArgument("time", Integer.class);
                                    context.getSource().sendFeedback(
                                            new LiteralText("§c[FunTimeQOL] §eSet auction refresh delay to " + AutoAHRefresh.wait + "!")
                                    );
                                    return 1;
                                })
                        )
                )
        );
    }
}
