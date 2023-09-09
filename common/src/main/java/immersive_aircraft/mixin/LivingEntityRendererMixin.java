package immersive_aircraft.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import immersive_aircraft.entity.AircraftEntity;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity> {
    @Inject(method = "setupTransforms", at = @At("TAIL"))
    public <E extends Entity> void render(T entity, PoseStack matrices, float animationProgress, float bodyYaw, float tickDelta, CallbackInfo ci) {
        if (entity.getRootVehicle() != entity && entity.getRootVehicle() instanceof AircraftEntity aircraft) {
            matrices.mulPose(Axis.XP.rotationDegrees(-aircraft.getViewXRot(tickDelta)));
            matrices.mulPose(Axis.ZP.rotationDegrees(-aircraft.getRoll(tickDelta)));
        }
    }
}
