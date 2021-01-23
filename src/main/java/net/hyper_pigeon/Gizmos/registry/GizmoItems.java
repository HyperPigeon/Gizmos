package net.hyper_pigeon.Gizmos.registry;

import net.hyper_pigeon.Gizmos.Gizmos;
import net.hyper_pigeon.Gizmos.entities.EyeOfEnderArrowEntity;
import net.hyper_pigeon.Gizmos.items.EyeOfEnderArrowItem;
import net.hyper_pigeon.Gizmos.items.Horseshoes;
import net.hyper_pigeon.Gizmos.items.Slingshot;
import net.hyper_pigeon.Gizmos.items.SoulFireSpitter;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Position;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class GizmoItems {

    public static final SoulFireSpitter SOUL_FIRE_SPITTER =
            new SoulFireSpitter(ToolMaterials.IRON, new Item.Settings().maxDamage(251).group(ItemGroup.COMBAT));

    public static final Slingshot SLINGSHOT =
            new Slingshot((new Item.Settings()).maxDamage(100).group(ItemGroup.COMBAT));

    public static final Horseshoes HORSESHOES =
            new Horseshoes(ArmorMaterials.IRON, EquipmentSlot.FEET,new Item.Settings().group(ItemGroup.COMBAT));

    public static final EntityAttributeModifier HORSESHOE_BOOST = new EntityAttributeModifier("HORSESHOE_BOOST",0.1,
            EntityAttributeModifier.Operation.fromId(0));

    public static final EyeOfEnderArrowItem EYE_OF_ENDER_ARROW =
            new EyeOfEnderArrowItem((new Item.Settings()).group(ItemGroup.COMBAT));

    public static void init() {
        if (Gizmos.CONFIG.soulFireSpitter)
            Registry.register(Registry.ITEM,new Identifier("gizmos","soul_fire_spitter"), SOUL_FIRE_SPITTER);

        if (Gizmos.CONFIG.slingShot)
            Registry.register(Registry.ITEM, new Identifier("gizmos","slingshot"), SLINGSHOT);

        if (Gizmos.CONFIG.horseshoes)
            Registry.register(Registry.ITEM, new Identifier("gizmos","horseshoes"), HORSESHOES);


        if(Gizmos.CONFIG.seeker_arrows) {
            Registry.register(Registry.ITEM, new Identifier("gizmos", "eye_of_ender_arrow"), EYE_OF_ENDER_ARROW);

            DispenserBlock.registerBehavior(EYE_OF_ENDER_ARROW, new ProjectileDispenserBehavior() {
                protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                    PersistentProjectileEntity persistentProjectileEntity = new EyeOfEnderArrowEntity(position.getX(), position.getY(), position.getZ(), world);
                    persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
                    return persistentProjectileEntity;
                }
            });
        }
    }

}