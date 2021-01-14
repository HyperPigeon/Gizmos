package net.hyper_pigeon.Gizmos.mixin;

import net.hyper_pigeon.Gizmos.entities.TamedMonster;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronGolemEntity.class)
public abstract class IronGolemEntityMixin extends GolemEntity implements Angerable {

//    private float propIron = 100;
//    private float propGold = 0;
//    private float propNetherite = 0;


    protected IronGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);

    }


    //changes goal so IronGolem does not automatically aggro on any mobs that implement TamedMonster
    @Inject(at = @At("TAIL"), method = "method_6498", cancellable = true)
    private static boolean doNotAutoAggroOnTamedMonster(LivingEntity livingEntity,
                                                        CallbackInfoReturnable<Boolean> callbackInfoReturnable){
        return livingEntity instanceof Monster && !(livingEntity instanceof CreeperEntity) && !(livingEntity instanceof TamedMonster);
    }


//    //apply enchanted books to the iron golem
//    @Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
//    private void applyEnchantmentToGolem(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> callbackInfoReturnable){
//        ItemStack itemStack = player.getStackInHand(hand);
//        Item item = itemStack.getItem();
//        if(item == Items.ENCHANTED_BOOK){
//            float g = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
//            this.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 1.0F, g);
//            if (!player.abilities.creativeMode) {
//                itemStack.decrement(1);
//            }
//            callbackInfoReturnable.setReturnValue(ActionResult.success(this.world.isClient));
//        }
//    }

//    //adds the abilty to heal iron golems with gold ingots, giving them unique benefits.
//    @Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
//    private void healWithGold(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> callbackInfoReturnable){
//        ItemStack itemStack = player.getStackInHand(hand);
//        Item item = itemStack.getItem();
//        if(item == Items.GOLD_INGOT){
//            float f = this.getHealth();
//            this.heal(10.0F);
//            float amountHealed = this.getHealth() - f;
//            if (this.getHealth() == f) {
//                callbackInfoReturnable.setReturnValue(ActionResult.PASS);
//            } else {
//                float g = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
//                this.playSound(SoundEvents.ENTITY_IRON_GOLEM_REPAIR, 1.0F, g);
//                if (!player.abilities.creativeMode) {
//                    itemStack.decrement(1);
//                }
//
//                if(propGold < 100){
//                    this.propGold = this.propGold + amountHealed;
//
//                    if(this.propIron != 0){
//                        this.propIron = this.propIron - amountHealed;
//                        if(propIron < 0){
//                            propIron = 0;
//                        }
//                    }
//                    else if(this.propNetherite != 0){
//                        this.propNetherite = this.propNetherite - amountHealed;
//                        if(propNetherite < 0){
//                            propNetherite = 0;
//                        }
//                    }
//                }
//
//
//                callbackInfoReturnable.setReturnValue(ActionResult.success(this.world.isClient));
//            }
//        }
//    }
//
//    //adds the abilty to heal iron golems with netherite ingots, giving them unique benefits.
//    @Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
//    private void healWithNetherite(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> callbackInfoReturnable){
//        ItemStack itemStack = player.getStackInHand(hand);
//        Item item = itemStack.getItem();
//        if(item == Items.NETHERITE_INGOT){
//            float f = this.getHealth();
//            this.heal(20.0F);
//            float amountHealed = this.getHealth() - f;
//            if (this.getHealth() == f) {
//                callbackInfoReturnable.setReturnValue(ActionResult.PASS);
//            } else {
//                float g = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
//                this.playSound(SoundEvents.ENTITY_IRON_GOLEM_REPAIR, 1.0F, g);
//                if (!player.abilities.creativeMode) {
//                    itemStack.decrement(1);
//                }
//
//                if(propNetherite < 100) {
//                    this.propNetherite = this.propNetherite + amountHealed;
//                    if (this.propIron != 0) {
//                        this.propIron = this.propIron - amountHealed;
//                        if(propIron < 0){
//                            propIron = 0;
//                        }
//                    }
//                    else if (this.propGold != 0) {
//                        this.propGold = this.propGold - amountHealed;
//                        if(propGold < 0){
//                            propGold = 0;
//                        }
//                    }
//                }
//
//                callbackInfoReturnable.setReturnValue(ActionResult.success(this.world.isClient));
//            }
//        }
//    }
//
//    @Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
//    private void changeIronProp(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> callbackInfoReturnable){
//        ItemStack itemStack = player.getStackInHand(hand);
//        Item item = itemStack.getItem();
//        if(item == Items.IRON_INGOT){
//            float f = this.getHealth();
//            float difference;
//            if(f + 25 >= 100){
//                difference = 100 - f;
//            }
//            else {
//                difference = 25;
//            }
//
//            if(propIron < 100){
//                this.propIron = this.propIron + difference;
//
//                if(this.propGold != 0){
//                    this.propGold = this.propGold - difference;
//                    if(propGold < 0){
//                        propGold = 0;
//                    }
//                }
//                else if(this.propNetherite != 0){
//                    this.propNetherite = this.propNetherite - difference;
//                    if(propNetherite < 0){
//                        propNetherite = 0;
//                    }
//                }
//            }
//        }
//   }
//
//    @Inject(at = @At("TAIL"), method = "writeCustomDataToTag")
//    public void writePropData(CompoundTag tag, CallbackInfo callbackInfo){
//        tag.putFloat("propIron",propIron);
//        tag.putFloat("propGold",propGold);
//        tag.putFloat("propNetherite",propNetherite);
//
//    }
//
//    @Inject(at = @At("TAIL"), method = "readCustomDataFromTag")
//    public void readPropData(CompoundTag tag, CallbackInfo callbackInfo){
//        if(tag.contains("propIron")){
//            this.propIron = tag.getFloat("propIron");
//        }
//        if(tag.contains("propGold")){
//            this.propGold = tag.getFloat("propGold");
//        }
//        if(tag.contains("propNetherite")){
//            this.propNetherite = tag.getFloat("propNetherite");
//        }
//
//
//    }

}
