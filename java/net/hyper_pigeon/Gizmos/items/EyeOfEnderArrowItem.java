package net.hyper_pigeon.Gizmos.items;

import net.hyper_pigeon.Gizmos.entities.EyeOfEnderArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.ItemTags;
import net.minecraft.world.World;

public class EyeOfEnderArrowItem extends ArrowItem {

    public EyeOfEnderArrowItem(Settings settings) {
        super(settings);
    }

    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        return new EyeOfEnderArrowEntity(world, shooter);
    }
}
