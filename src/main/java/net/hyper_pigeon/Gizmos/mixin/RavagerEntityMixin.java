package net.hyper_pigeon.Gizmos.mixin;

import net.hyper_pigeon.Gizmos.Gizmos;
import net.hyper_pigeon.Gizmos.entities.TamedRavagerEntity;
import net.hyper_pigeon.Gizmos.registry.GizmoEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RavagerEntity.class)
public abstract class RavagerEntityMixin extends RaiderEntity {


    protected RavagerEntityMixin(EntityType<? extends RaiderEntity> entityType, World world) {
        super(entityType, world);
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if(item == Items.GOLDEN_APPLE){
            if(this.getHealth() >= this.getMaxHealth()*Gizmos.CONFIG.ravagerTamingHealthProportion){
                return ActionResult.PASS;
            }
            else {
                itemStack.decrement(1);
                TamedRavagerEntity tamedRavagerEntity = this.convertTo(GizmoEntities.TAMED_RAVAGER_ENTITY,false);
                tamedRavagerEntity.setOwnerUUID(player.getUuid());
                tamedRavagerEntity.setPersistent();

                for(int i = 0; i < 8; i++){
                    getEntityWorld().addParticle(ParticleTypes.HEART,this.getX()+(this.random.nextFloat() - this.random.nextFloat()),this.getY()+3,
                            this.getZ()+(this.random.nextFloat() - this.random.nextFloat()),
                            0,0.01,0);
                }


                float g = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEvents.ENTITY_RAVAGER_CELEBRATE, 1.0F, g);



                return ActionResult.success(this.world.isClient);
            }
        }
        else {
            return super.interactMob(player,hand);
        }
    }


}
