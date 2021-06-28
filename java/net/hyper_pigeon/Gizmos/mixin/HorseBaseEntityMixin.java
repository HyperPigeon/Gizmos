package net.hyper_pigeon.Gizmos.mixin;

import net.hyper_pigeon.Gizmos.registry.GizmoItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HorseBaseEntity.class)
public abstract class HorseBaseEntityMixin extends AnimalEntity implements InventoryChangedListener, JumpingMount, Saddleable {
    protected HorseBaseEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(at = @At("HEAD"), method = "openInventory", cancellable = true)
    public void doNotOpenInventoryWhenHoldingHorseshoes(PlayerEntity player, CallbackInfo ci){
        if(!this.world.isClient && (player.getMainHandStack().getItem() == GizmoItems.HORSESHOES)){
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "openInventory", cancellable = true)
    public void doNotOpenInventoryWhenHoldingPickaxes(PlayerEntity player, CallbackInfo ci){
        if(!this.world.isClient && ((player.getMainHandStack().getItem() == Items.NETHERITE_PICKAXE)
        || (player.getMainHandStack().getItem() == Items.DIAMOND_PICKAXE) || (player.getMainHandStack().getItem() == Items.IRON_PICKAXE))){
            ci.cancel();
        }
    }


}
