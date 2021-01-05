package net.hyper_pigeon.Gizmos.registry;

import net.hyper_pigeon.Gizmos.Gizmos;
import net.hyper_pigeon.Gizmos.items.Horseshoes;
import net.hyper_pigeon.Gizmos.items.Slingshot;
import net.hyper_pigeon.Gizmos.items.SoulFireSpitter;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GizmoItems {

    public static final SoulFireSpitter SOUL_FIRE_SPITTER =
            new SoulFireSpitter(ToolMaterials.IRON, new Item.Settings().group(ItemGroup.COMBAT));

    public static final Slingshot SLINGSHOT =
            new Slingshot((new Item.Settings()).maxDamage(100).group(ItemGroup.COMBAT));

    public static final Horseshoes HORSESHOES =
            new Horseshoes(ArmorMaterials.IRON, EquipmentSlot.FEET,new Item.Settings().group(ItemGroup.COMBAT));

    public static final EntityAttributeModifier HORSESHOE_BOOST = new EntityAttributeModifier("HORSESHOE_BOOST",0.1,
            EntityAttributeModifier.Operation.fromId(0));

    public static void init(){
        if (Gizmos.CONFIG.soulFireSpitter)
            Registry.register(Registry.ITEM,new Identifier("gizmos","soul_fire_spitter"), SOUL_FIRE_SPITTER);

        if (Gizmos.CONFIG.slingShot)
            Registry.register(Registry.ITEM, new Identifier("gizmos","slingshot"), SLINGSHOT);

        if (Gizmos.CONFIG.horseshoes)
            Registry.register(Registry.ITEM, new Identifier("gizmos","horseshoes"), HORSESHOES);
    }

}
