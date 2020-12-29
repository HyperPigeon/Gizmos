package net.hyper_pigeon.Gizmos.entities;

import net.hyper_pigeon.Gizmos.registry.GizmoItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public class TamedRavagerEntity extends RavagerEntity implements TamedMonster {


    private UUID ownerUUID;

    public TamedRavagerEntity(EntityType<? extends RavagerEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(4, new TamedRavagerEntity.AttackGoal());
        this.targetSelector.add(4, new FollowTargetGoal(this, LivingEntity.class, 10, true, false,
                livingEntity -> livingEntity == getTarget()));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.4D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.targetSelector.add(6, new RevengeGoal(this));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
    }

    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putUuid("OwnerUUID", ownerUUID);
    }

    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        setOwnerUUID(tag.getUuid("OwnerUUID"));
    }

    public void setOwnerUUID(UUID ownerUUID){
        this.ownerUUID = ownerUUID;
    }

    @Nullable
    public LivingEntity getOwner() {
        try {
            UUID uUID = this.ownerUUID;
            return uUID == null ? null : this.world.getPlayerByUuid(uUID);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    public boolean hasHorseshoes(){
        if(this.hasStackEquipped(EquipmentSlot.FEET)){
            return this.getEquippedStack(EquipmentSlot.FEET).getItem() == GizmoItems.HORSESHOES;
        }
        return false;
    }


    public void tickMovement(){
        super.tickMovement();

//        if(this.getPrimaryPassenger() != null && this.getPrimaryPassenger() instanceof PlayerEntity){
//            PlayerEntity playerEntity = (PlayerEntity) this.getPrimaryPassenger();
//            if(playerEntity.getAttacking() != null){
//                super.setTarget(playerEntity.getAttacking());
//            }
//            else if(playerEntity.getAttacker() != null){
//                super.setTarget(playerEntity.getAttacker());
//            }
//        }

        if(this.getOwner() != null){
            if(getOwner().getAttacking() != null && getOwner().getAttacking() != this){
                super.setTarget(getOwner().getAttacking());
            }
            else if(getOwner().getAttacker() != null && getOwner().getAttacker() != this){
                super.setTarget(getOwner().getAttacker());
            }
        }
    }

    public void setTarget(@Nullable LivingEntity target) {
        if(getOwner() != target){
            super.setTarget(target);
        }
    }


    public ActionResult interactMob(PlayerEntity player, Hand hand) {
            ItemStack itemStack = player.getStackInHand(hand);
            Item item = itemStack.getItem();
            if(item == Items.APPLE){
                float f = this.getHealth();
                this.heal(10.0F);
                if (this.getHealth() == f) {
                    return ActionResult.PASS;
                } else {
                    float g = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                    this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1.0F, g);
                    if (!player.abilities.creativeMode) {
                        itemStack.decrement(1);
                    }

                    return ActionResult.success(this.world.isClient);
                }
            }
            else if(item == Items.GOLDEN_APPLE){
                float f = this.getHealth();
                this.heal(20.0F);
                if (this.getHealth() == f) {
                    return ActionResult.PASS;
                } else {
                    float g = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                    this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1.0F, g);
                    if (!player.abilities.creativeMode) {
                        itemStack.decrement(1);
                    }

                    return ActionResult.success(this.world.isClient);
                }
            }
            else if(item == Items.NETHERITE_PICKAXE || item == Items.DIAMOND_PICKAXE || item == Items.IRON_PICKAXE){
                if(hasStackEquipped(EquipmentSlot.FEET) && (getEquippedStack(EquipmentSlot.FEET).getItem() == GizmoItems.HORSESHOES)){
                    if (player == getOwner()) {
                        this.getEquippedStack(EquipmentSlot.FEET).setCount(0);
                        this.playSound(SoundEvents.BLOCK_CHAIN_BREAK, 4.0F, 1);
                        EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
                        entityAttributeInstance.removeModifier(GizmoItems.HORSESHOE_BOOST);

                        return ActionResult.success(this.world.isClient);
                    }
                }
            }
            else if(!this.hasPassengers() && !player.shouldCancelInteraction()){
                if (!this.world.isClient && (player == getOwner())) {
                    player.startRiding(this);
                    return ActionResult.success(this.world.isClient);
                }
            }

            if(!this.world.isClient && player != getOwner()){
                tryAttack(player);
            }

            return ActionResult.PASS;
    }

    public boolean canBeControlledByRider() {
        return this.getPrimaryPassenger() instanceof PlayerEntity;
    }

    public void travel(Vec3d movementInput) {
        if (this.isAlive()) {
            if (this.hasPassengers() && this.canBeControlledByRider() ) {
                LivingEntity livingEntity = (LivingEntity)this.getPrimaryPassenger();
                this.yaw = livingEntity.yaw;
                this.prevYaw = this.yaw;
                this.pitch = livingEntity.pitch * 0.5F;
                this.setRotation(this.yaw, this.pitch);
                this.bodyYaw = this.yaw;
                this.headYaw = this.bodyYaw;
                
                float f = livingEntity.sidewaysSpeed * 0.35F;
                float g = livingEntity.forwardSpeed;

                if (g <= 0.0F) {
                    g *= 0.25F;
                }

                this.flyingSpeed = this.getMovementSpeed() * 0.1F;
                if (this.isLogicalSideForUpdatingMovement()) {
                    this.setMovementSpeed((float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
                    super.travel(new Vec3d(f, movementInput.y, g));
                } else if (livingEntity instanceof PlayerEntity) {
                    this.setVelocity(Vec3d.ZERO);
                }

                this.method_29242(this, false);
            } else {
                this.flyingSpeed = 0.02F;
                super.travel(movementInput);
            }
        }
    }

    class AttackGoal extends MeleeAttackGoal {
        public AttackGoal() {
            super(TamedRavagerEntity.this, 1.0D, true);
        }

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            float f = TamedRavagerEntity.this.getWidth() - 0.1F;
            return (double)(f * 2.0F * f * 2.0F + entity.getWidth());
        }
    }
}
