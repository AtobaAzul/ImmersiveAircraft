package immersive_aircraft.mixin;

import immersive_aircraft.client.OverlayRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class InGameHudMixin {
    @Inject(method = "render(Lnet/minecraft/client/gui/DrawContext;F)V", at = @At("TAIL"))
    private void renderInject(GuiGraphics context, float tickDelta, CallbackInfo ci) {
        OverlayRenderer.renderOverlay(context, tickDelta);
    }
}
