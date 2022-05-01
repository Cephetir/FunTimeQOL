package me.cephetir.funtimeqol.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;

public class AutoAHRefresh {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static boolean enabled = true;
    private static int ticks = 0;
    public static int wait = 20;

    public static void onTick() {
        if (!enabled || mc.player == null || mc.world == null || mc.currentScreen == null) return;
        Screen screen = mc.currentScreen;
        if (!(screen instanceof GenericContainerScreen chest)) return;
        String name = chest.getNarratedTitle().getString();
        if (!name.startsWith("[☃] Аукционы ")) return;
        if (ticks++ < wait) return;
        ticks = 0;

        for (Slot slot : chest.getScreenHandler().slots) {
            if (!slot.hasStack()) continue;
            boolean has = false;
            for (Text text : slot.getStack().getTooltip(mc.player, TooltipContext.Default.NORMAL))
                if (text.getString().contains("Обновить")) {
                    has = true;
                    break;
                }
            if (has && mc.interactionManager != null) {
                mc.interactionManager.clickSlot(chest.getScreenHandler().syncId, slot.id, 0, SlotActionType.THROW, mc.player);
                break;
            }
        }
    }
}
