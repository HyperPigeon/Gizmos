package net.hyper_pigeon.Gizmos.mixin;

import net.hyper_pigeon.Gizmos.entities.EyeOfEnderArrowEntity;
import net.hyper_pigeon.Gizmos.registry.GizmoEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndermanEntity.class)
public abstract class EndermanEntityMixin extends HostileEntity implements Angerable {
    protected EndermanEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void takeEnderEyeArrowDamage(DamageSource source, float amount, CallbackInfoReturnable callbackInfoReturnable){
        if(source instanceof ProjectileDamageSource &&
                source.getSource() instanceof EyeOfEnderArrowEntity){
           callbackInfoReturnable.setReturnValue(super.damage(source,amount));
           callbackInfoReturnable.cancel();
        }


    }
}
