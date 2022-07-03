package net.hyper_pigeon.Gizmos.items;

import net.hyper_pigeon.Gizmos.entities.TamedRavagerEntity;
import net.hyper_pigeon.Gizmos.mixin.AbstractHorseEntityMixin;
import net.hyper_pigeon.Gizmos.registry.GizmoItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class Horseshoes extends ArmorItem {
    public Horseshoes(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        return TypedActionResult.fail(itemStack);
    }

    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(!user.world.isClient && (entity instanceof AbstractHorseEntity || entity instanceof TamedRavagerEntity)){
            EquipmentSlot equipmentSlot = EquipmentSlot.FEET;
            ItemStack itemStack2 = entity.getEquippedStack(equipmentSlot);
            if(itemStack2.isEmpty()){
                entity.equipStack(equipmentSlot, stack.copy());
                stack.setCount(0);

                EntityAttributeInstance entityAttributeInstance = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
                entityAttributeInstance.addTemporaryModifier(GizmoItems.HORSESHOE_BOOST);

                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.FAIL;
    }

//    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
//        if(entity instanceof HorseBaseEntity || entity instanceof TamedRavagerEntity){
//            System.out.println(!user.world.isClient);
//            System.out.println(entity.getEquippedStack(EquipmentSlot.FEET).getItem() != GizmoItems.HORSESHOES);
//            if (!user.world.isClient && (entity.getEquippedStack(EquipmentSlot.FEET).getItem() != GizmoItems.HORSESHOES)) {
//                entity.equipStack(EquipmentSlot.FEET, stack);
//                EntityAttributeInstance entityAttributeInstance = entity.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
//                entityAttributeInstance.addPersistentModifier(new EntityAttributeModifier("HORSESHOE_BOOST",0.1,
//                        EntityAttributeModifier.Operation.fromId(0)));
//
//                if(!user.isCreative()) {
//                    stack.decrement(1);
//                }
//                return ActionResult.success(user.world.isClient);
//            }
//        }
//        return ActionResult.PASS;
//    }
}
