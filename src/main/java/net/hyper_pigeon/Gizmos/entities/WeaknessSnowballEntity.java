package net.hyper_pigeon.Gizmos.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class WeaknessSnowballEntity extends SnowballEntity {
    public boolean isAmplified = false;

    public WeaknessSnowballEntity(World world, LivingEntity owner) {
        super(world, owner);
    }

    public void setIsAmplified(boolean true_or_false){
        isAmplified= true_or_false;
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        if(entity instanceof LivingEntity){
            if(isAmplified){
                StatusEffectInstance statusEffectInstance = new StatusEffectInstance(StatusEffects.WEAKNESS, 15,1);
                ((LivingEntity) entity).setStatusEffect(statusEffectInstance, this.getOwner());
            }
            else {
                StatusEffectInstance statusEffectInstance = new StatusEffectInstance(StatusEffects.WEAKNESS, 10,0);
                ((LivingEntity) entity).setStatusEffect(statusEffectInstance, this.getOwner());
            }
        }
    }
}
