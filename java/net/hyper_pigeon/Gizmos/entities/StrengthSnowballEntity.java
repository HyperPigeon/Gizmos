package net.hyper_pigeon.Gizmos.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class StrengthSnowballEntity extends SnowballEntity {
    public boolean isAmplified = false;

    public StrengthSnowballEntity(EntityType<? extends SnowballEntity> entityType, World world) {
        super(entityType, world);
    }

    public StrengthSnowballEntity(World world, LivingEntity owner) {
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
                ((LivingEntity) entity).damage(DamageSource.MAGIC, 4.0f);
            }
            else {
                ((LivingEntity) entity).damage(DamageSource.MAGIC, 2.0f);
            }
        }
    }
}
