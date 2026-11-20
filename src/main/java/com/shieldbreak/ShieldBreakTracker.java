package com.shieldbreak;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class ShieldBreakTracker {
    // Timeline in ticks (20 ticks = 1 second): 0-2s red, 2-4s yellow, 4-5s blue, then green.
    private static final Map<UUID, Long> BREAKS = new ConcurrentHashMap<>();

    public static void mark(Player player) {
        BREAKS.put(player.getUUID(), player.level().getGameTime());
    }

    public static ItemStack swap(ItemStack stack, LivingEntity entity) {
        if (!(entity instanceof Player player) || !stack.is(Items.SHIELD)) return stack;
        Long start = BREAKS.get(player.getUUID());
        if (start == null) return stack;

        long elapsed = player.level().getGameTime() - start;
        String color;
        if (elapsed < 0) { BREAKS.remove(player.getUUID()); return stack; }
        else if (elapsed < 40) color = "red";
        else if (elapsed < 80) color = "yellow";
        else if (elapsed < 100) color = "blue";
        else { BREAKS.remove(player.getUUID()); return stack; } // back to default green

        ItemStack copy = stack.copy();
        copy.set(DataComponents.ITEM_MODEL, Identifier.fromNamespaceAndPath("shieldbreak", "shield_" + color));
        return copy;
    }
}
