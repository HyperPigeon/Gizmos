package net.hyper_pigeon.Gizmos.mixin;

import net.hyper_pigeon.Gizmos.registry.GizmoItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractDonkeyEntity.class)
public abstract class AbstractDonkeyEntityMixin extends AbstractHorseEntity {
    protected AbstractDonkeyEntityMixin(EntityType<? extends AbstractHorseEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"),method = "interactMob", cancellable = true)
    public void tryRemoveHorseshoes(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir){
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if(item == Items.NETHERITE_PICKAXE || item == Items.DIAMOND_PICKAXE || item == Items.IRON_PICKAXE){
            if(hasStackEquipped(EquipmentSlot.FEET) && (getEquippedStack(EquipmentSlot.FEET).getItem() == GizmoItems.HORSESHOES)){
                this.getEquippedStack(EquipmentSlot.FEET).setCount(0);
                this.playSound(SoundEvents.BLOCK_CHAIN_BREAK, 4.0F, 1);
                EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
                entityAttributeInstance.removeModifier(GizmoItems.HORSESHOE_BOOST);

                cir.setReturnValue(ActionResult.success(this.world.isClient));
            }
        }
    }
}
