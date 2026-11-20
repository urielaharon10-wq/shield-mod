package com.shieldbreak.mixin;

import com.shieldbreak.ShieldBreakTracker;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    // Entity event 30 = "shield broke". The server sends it to everyone nearby.
    @Inject(method = "handleEntityEvent", at = @At("HEAD"))
    private void shieldbreak$onEvent(byte id, CallbackInfo ci) {
        if (id == 30 && (Object) this instanceof Player player) {
            ShieldBreakTracker.mark(player);
        }
    }
}
