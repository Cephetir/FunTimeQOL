package me.cephetir.funtimeqol.mixin;

import me.cephetir.funtimeqol.features.HighlightingItems;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public class MixinHandledScreen {
	@Inject(method = "drawSlot", at = @At("HEAD"))
	private void drawSlot(MatrixStack matrices, Slot slot, CallbackInfo info) {
		HighlightingItems.onDraw(matrices, slot);
	}
}
