package me.cephetir.funtimeqol.features;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;

import java.awt.*;

public class HighlightingItems {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static boolean enabled = true;
    private static double balance = 0d;

    public static void onDraw(MatrixStack matrices, Slot slot) {
        if (!enabled || !slot.hasStack()) return;
        Screen screen = mc.currentScreen;
        if (!(screen instanceof GenericContainerScreen chest)) return;
        String name = chest.getNarratedTitle().getString();
        if (!name.startsWith("[☃] Аукционы ")) return;

        String s = "NONE";
        for (Text text : slot.getStack().getTooltip(mc.player, TooltipContext.Default.NORMAL))
            if (text.getString().contains("Цена: ")) {
                s = text.getString();
                break;
            }
        if (s.equals("NONE")) return;
        int price = Integer.parseInt(s.split("Цена: ")[1].replace(" ", "").replace("$", ""));

        if (price >= balance)
            Screen.fill(matrices, slot.x, slot.y, slot.x + 16, slot.y + 16, Color.RED.getRGB());
        else if (price >= balance / 3)
            Screen.fill(matrices, slot.x, slot.y, slot.x + 16, slot.y + 16, Color.YELLOW.getRGB());
        else
            Screen.fill(matrices, slot.x, slot.y, slot.x + 16, slot.y + 16, Color.GREEN.getRGB());
    }

    public static void onChat(Text message) {
        String text = message.getString();
        if(!text.contains("[$] Ваш баланс: ")) return;
        balance = Double.parseDouble(text.split("баланс: ")[1].replace("$", "").replace(",", ""));
    }
}
