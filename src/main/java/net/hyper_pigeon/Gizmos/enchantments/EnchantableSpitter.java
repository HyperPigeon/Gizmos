package net.hyper_pigeon.Gizmos.enchantments;

import net.hyper_pigeon.Gizmos.registry.GizmoItems;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

public class EnchantableSpitter extends EnchantmentTargetMixin {
//    @Override
//    public boolean isAcceptableItem(Item item) {
//        return item == GizmoItems.SOUL_FIRE_SPITTER;
//    }
}

@Mixin(EnchantmentTarget.class)
abstract class EnchantmentTargetMixin {
//    @Shadow
//    abstract boolean isAcceptableItem(Item item);
}