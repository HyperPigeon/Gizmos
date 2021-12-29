package net.hyper_pigeon.Gizmos.mixin;

import net.hyper_pigeon.Gizmos.Gizmos;
import net.hyper_pigeon.Gizmos.entities.TamedRavagerEntity;
import net.hyper_pigeon.Gizmos.registry.GizmoEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(RavagerEntity.class)
public abstract class RavagerEntityMixin extends RaiderEntity {

    @Shadow
    private int roarTick;

    @Shadow
    private int stunTick;

    @Shadow
    private int attackTick;

    @Shadow
    private native void roar();

    @Shadow
    private native void spawnStunnedParticles();


    protected RavagerEntityMixin(EntityType<? extends RaiderEntity> entityType, World world) {
        super(entityType, world);
    }

    public boolean isBlockWood(Block block){
        if(block.getDefaultState().getMaterial().equals(Material.WOOD)){
            return true;
        }
        return false;
    }


    @Inject(at =  @At("TAIL"), method = "tickMovement")
    public void breakWoodBlock(CallbackInfo ci){
        Box box = this.getBoundingBox().expand(0.5D);
        Iterator blockPosIterator = BlockPos.iterate(MathHelper.floor(box.minX), MathHelper.floor(box.minY), MathHelper.floor(box.minZ), MathHelper.floor(box.maxX), MathHelper.floor(box.maxY), MathHelper.floor(box.maxZ)).iterator();

        while(blockPosIterator.hasNext()){
            BlockPos blockPos = (BlockPos) blockPosIterator.next();
            BlockState blockState = this.world.getBlockState(blockPos);
            Block block = blockState.getBlock();

            if (isBlockWood(block)){
                this.world.breakBlock(blockPos,true);
            }
        }
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

                for(int i = 0; i < 32; i++){
                    getEntityWorld().addParticle(ParticleTypes.HEART,this.getX()+(this.random.nextFloat() - this.random.nextFloat()),this.getY()+3,
                            this.getZ()+(this.random.nextFloat() - this.random.nextFloat()),
                            0,0.01,0);
                }


                float g = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEvents.ENTITY_RAVAGER_CELEBRATE, 3.0F, g);



                return ActionResult.success(this.world.isClient);
            }
        }
        else {
            return super.interactMob(player,hand);
        }
    }

//    public void tickMovement() {
//        super.tickMovement();
////        if (this.isAlive()) {
////            if (this.isImmobile()) {
////                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.0D);
////            } else {
////                double d = this.getTarget() != null ? 0.35D : 0.3D;
////                double e = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).getBaseValue();
////                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(MathHelper.lerp(0.1D, e, d));
////            }
////
////            if (this.horizontalCollision && this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
////                boolean bl = false;
////                Box box = this.getBoundingBox().expand(0.2D);
////                Iterator var8 = BlockPos.iterate(MathHelper.floor(box.minX), MathHelper.floor(box.minY), MathHelper.floor(box.minZ), MathHelper.floor(box.maxX), MathHelper.floor(box.maxY), MathHelper.floor(box.maxZ)).iterator();
////
////                label60:
////                while(true) {
////                    BlockPos blockPos;
////                    Block block;
////                    do {
////                        if (!var8.hasNext()) {
////                            if (!bl && this.onGround) {
////                                this.jump();
////                            }
////                            break label60;
////                        }
////
////                        blockPos = (BlockPos)var8.next();
////                        BlockState blockState = this.world.getBlockState(blockPos);
////                        block = blockState.getBlock();
////                    } while(!(block instanceof LeavesBlock) && !(isBlockWood(block) &&
////                            this.hasStatusEffect(StatusEffects.STRENGTH)));
////
////                    bl = this.world.breakBlock(blockPos, true, this) || bl;
////
////                }
////            }
////
////            if (this.roarTick > 0) {
////                --this.roarTick;
////                if (this.roarTick == 10) {
////                    this.roar();
////                }
////            }
////
////            if (this.attackTick > 0) {
////                --this.attackTick;
////            }
////
////            if (this.stunTick > 0) {
////                --this.stunTick;
////                this.spawnStunnedParticles();
////                if (this.stunTick == 0) {
////                    this.playSound(SoundEvents.ENTITY_RAVAGER_ROAR, 1.0F, 1.0F);
////                    this.roarTick = 20;
////                }
////            }
////
////        }
//    }


}
