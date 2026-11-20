package com.shieldbreak.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.shieldbreak.ShieldBreakTracker;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemModelResolver.class)
public abstract class ItemModelResolverMixin {
    // Swap the shield's model while it is drawn on a player (hand / third person).
    @ModifyVariable(method = "updateForLiving", at = @At("HEAD"), argsOnly = true)
    private ItemStack shieldbreak$swap(ItemStack stack, @Local(argsOnly = true) LivingEntity entity) {
        return ShieldBreakTracker.swap(stack, entity);
    }
}
