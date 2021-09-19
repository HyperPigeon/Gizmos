package net.hyper_pigeon.Gizmos.mixin;

import net.hyper_pigeon.Gizmos.entities.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.potion.PotionUtil;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Mixin(SnowGolemEntity.class)
public abstract class SnowGolemEntityMixin extends GolemEntity implements Shearable, RangedAttackMob {

    private static final int permanentDuration = 32767; // from EntityStatusEffectS2CPacket#isPermanent()
    
    private StatusEffectInstance statusEffectInstance_one;

    private static final ArrayList<StatusEffect> applicableStatusEffects = new ArrayList<>(Arrays.asList(
            StatusEffects.FIRE_RESISTANCE,
            StatusEffects.REGENERATION,
            StatusEffects.INVISIBILITY,
            StatusEffects.WATER_BREATHING,
            StatusEffects.SPEED,
            StatusEffects.REGENERATION,
            StatusEffects.SLOW_FALLING,
            StatusEffects.RESISTANCE,
            StatusEffects.STRENGTH));

    protected SnowGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "method_18443", cancellable = true)
    private static boolean doNotAutoAggroOnTamedMonster(LivingEntity livingEntity,
                                                        CallbackInfoReturnable<Boolean> callbackInfoReturnable){
        return livingEntity instanceof Monster && !(livingEntity instanceof TamedMonster);
    }

    @Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
    public void giveStatusEffect(PlayerEntity player, Hand hand,
                                 CallbackInfoReturnable<ActionResult> callbackInfoReturnable){
        if(player.getMainHandStack().getItem() instanceof PotionItem){
            if (!player.getEntityWorld().isClient) {
                List<StatusEffectInstance> list = PotionUtil.getPotionEffects(player.getMainHandStack());
                Iterator var6 = list.iterator();

                while(var6.hasNext()) {
                    StatusEffectInstance statusEffectInstance = (StatusEffectInstance)var6.next();

                    if(statusEffectInstance_one != null){
                        //do nothing
                    }
                    else {
                        if(!player.isCreative()) {
                            player.getMainHandStack().decrement(1);
                            player.giveItemStack(new ItemStack(Items.GLASS_BOTTLE));
                        }
                        statusEffectInstance_one = statusEffectInstance;
                    }

                }
            }

            if(statusEffectInstance_one != null) {
                if (statusEffectInstance_one.getEffectType().equals(StatusEffects.FIRE_RESISTANCE)) {
                    makePermanent();
                    this.addStatusEffect(statusEffectInstance_one);
                }
                else if (statusEffectInstance_one.getEffectType().equals(StatusEffects.INVISIBILITY)) {
                    makePermanent();
                    this.addStatusEffect(statusEffectInstance_one);
                }
                else if (statusEffectInstance_one.getEffectType().equals(StatusEffects.WATER_BREATHING)) {
                    makePermanent();
                    this.addStatusEffect(statusEffectInstance_one);
                }
                else if (statusEffectInstance_one.getEffectType().equals(StatusEffects.SPEED)) {
                    makePermanent();
                    this.addStatusEffect(statusEffectInstance_one);
                }
                else if (statusEffectInstance_one.getEffectType().equals(StatusEffects.REGENERATION)) {
                    makePermanent();
                    this.addStatusEffect(statusEffectInstance_one);
                }
                else if (statusEffectInstance_one.getEffectType().equals(StatusEffects.SLOW_FALLING)) {
                    makePermanent();
                    this.addStatusEffect(statusEffectInstance_one);
                }
                else if (statusEffectInstance_one.getEffectType().equals(StatusEffects.RESISTANCE)) {
                    makePermanent();
                    this.addStatusEffect(statusEffectInstance_one);
                }
                else if (statusEffectInstance_one.getEffectType().equals(StatusEffects.STRENGTH)) {
                    makePermanent();
                    this.addStatusEffect(statusEffectInstance_one);
                }
            }

        }
    }

    private void makePermanent() {
        ((StatusEffectInstanceAccessor)statusEffectInstance_one).setDuration(permanentDuration);
        if (this.world.isClient)
            statusEffectInstance_one.setPermanent(true);
    }

    @Inject(at = @At("RETURN"), method = "hurtByWater", cancellable = true)
    public void doNotTakeWaterDamage(CallbackInfoReturnable<Boolean> callbackInfoReturnable){
        if(statusEffectInstance_one != null){
            if(statusEffectInstance_one.getEffectType().equals(StatusEffects.WATER_BREATHING)){
                callbackInfoReturnable.setReturnValue(false);
            }
        }
    }

    public StatusEffectInstance getStatusEffectInstance_one(){
        return statusEffectInstance_one;
    }

    @Inject(at = @At("TAIL"), method = "writeCustomDataToTag")
    public void writeStatusEffectInstanceToTag(CompoundTag tag, CallbackInfo callbackInfo){
        if(statusEffectInstance_one != null){
            statusEffectInstance_one.toTag(tag);
        }
    }

    @Inject(at = @At("TAIL"), method = "readCustomDataFromTag")
    public void readStatusEffectInstanceFromTag(CompoundTag tag, CallbackInfo callbackInfo){
        statusEffectInstance_one = StatusEffectInstance.fromTag(tag);
    }

    @Inject(at = @At("HEAD"), method = "attack", cancellable = true)
    public void throwPotionEffectSnowballs(LivingEntity target, float pullProgress, CallbackInfo callbackInfo){

        if(statusEffectInstance_one != null) {
            if (statusEffectInstance_one.getEffectType() == StatusEffects.INSTANT_DAMAGE) {
                DamageSnowballEntity snowballEntity = new DamageSnowballEntity(this.world, this);
                if (statusEffectInstance_one.getAmplifier() > 0) {
                    snowballEntity.setIsAmplified(true);
                }
                double d = target.getEyeY() - 1.100000023841858D;
                double e = target.getX() - this.getX();
                double f = d - snowballEntity.getY();
                double g = target.getZ() - this.getZ();
                float h = MathHelper.sqrt(e * e + g * g) * 0.2F;
                snowballEntity.setVelocity(e, f + (double) h, g, 1.6F, 12.0F);
                this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
                this.world.spawnEntity(snowballEntity);
                callbackInfo.cancel();
            } else if (statusEffectInstance_one.getEffectType() == StatusEffects.POISON) {
                PoisonSnowballEntity snowballEntity = new PoisonSnowballEntity(this.world, this);
                if (statusEffectInstance_one.getAmplifier() > 0) {
                    snowballEntity.setIsAmplified(true);
                }
                double d = target.getEyeY() - 1.100000023841858D;
                double e = target.getX() - this.getX();
                double f = d - snowballEntity.getY();
                double g = target.getZ() - this.getZ();
                float h = MathHelper.sqrt(e * e + g * g) * 0.2F;
                snowballEntity.setVelocity(e, f + (double) h, g, 1.6F, 12.0F);
                this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
                this.world.spawnEntity(snowballEntity);
                callbackInfo.cancel();
            } else if (statusEffectInstance_one.getEffectType() == StatusEffects.INSTANT_HEALTH) {
                HealthSnowballEntity snowballEntity = new HealthSnowballEntity(this.world, this);
                if (statusEffectInstance_one.getAmplifier() > 0) {
                    snowballEntity.setIsAmplified(true);
                }
                double d = target.getEyeY() - 1.100000023841858D;
                double e = target.getX() - this.getX();
                double f = d - snowballEntity.getY();
                double g = target.getZ() - this.getZ();
                float h = MathHelper.sqrt(e * e + g * g) * 0.2F;
                snowballEntity.setVelocity(e, f + (double) h, g, 1.6F, 12.0F);
                this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
                this.world.spawnEntity(snowballEntity);
                callbackInfo.cancel();
            } else if (statusEffectInstance_one.getEffectType() == StatusEffects.REGENERATION) {
                RegenerationSnowballEntity snowballEntity = new RegenerationSnowballEntity(this.world, this);
                if (statusEffectInstance_one.getAmplifier() > 0) {
                    snowballEntity.setIsAmplified(true);
                }
                double d = target.getEyeY() - 1.100000023841858D;
                double e = target.getX() - this.getX();
                double f = d - snowballEntity.getY();
                double g = target.getZ() - this.getZ();
                float h = MathHelper.sqrt(e * e + g * g) * 0.2F;
                snowballEntity.setVelocity(e, f + (double) h, g, 1.6F, 12.0F);
                this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
                this.world.spawnEntity(snowballEntity);
                callbackInfo.cancel();
            }
            else if (statusEffectInstance_one.getEffectType() == StatusEffects.SLOWNESS) {
                SlownessSnowballEntity snowballEntity = new SlownessSnowballEntity(this.world, this);
                if (statusEffectInstance_one.getAmplifier() > 0) {
                    snowballEntity.setIsAmplified(true);
                }
                double d = target.getEyeY() - 1.100000023841858D;
                double e = target.getX() - this.getX();
                double f = d - snowballEntity.getY();
                double g = target.getZ() - this.getZ();
                float h = MathHelper.sqrt(e * e + g * g) * 0.2F;
                snowballEntity.setVelocity(e, f + (double) h, g, 1.6F, 12.0F);
                this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
                this.world.spawnEntity(snowballEntity);
                callbackInfo.cancel();
            }
            else if (statusEffectInstance_one.getEffectType() == StatusEffects.STRENGTH) {
                StrengthSnowballEntity snowballEntity = new StrengthSnowballEntity(this.world, this);
                if (statusEffectInstance_one.getAmplifier() > 0) {
                    snowballEntity.setIsAmplified(true);
                }
                double d = target.getEyeY() - 1.100000023841858D;
                double e = target.getX() - this.getX();
                double f = d - snowballEntity.getY();
                double g = target.getZ() - this.getZ();
                float h = MathHelper.sqrt(e * e + g * g) * 0.2F;
                snowballEntity.setVelocity(e, f + (double) h, g, 1.6F, 12.0F);
                this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
                this.world.spawnEntity(snowballEntity);
                callbackInfo.cancel();
            }
            else if (statusEffectInstance_one.getEffectType() == StatusEffects.NIGHT_VISION){
                SnowballEntity snowballEntity = new SnowballEntity(this.world, this);
                double d = target.getEyeY() - 1.100000023841858D;
                double e = target.getX() - this.getX();
                double f = d - snowballEntity.getY();
                double g = target.getZ() - this.getZ();
                float h = MathHelper.sqrt(e * e + g * g) * 0.2F;
                snowballEntity.setVelocity(e, f + (double)h, g, 1.6F, 1.0F);
                this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
                this.world.spawnEntity(snowballEntity);
            }
            else if (statusEffectInstance_one.getEffectType() == StatusEffects.SPEED){
                SnowballEntity snowballEntity = new SnowballEntity(this.world, this);
                double d = target.getEyeY() - 1.100000023841858D;
                double e = target.getX() - this.getX();
                double f = d - snowballEntity.getY();
                double g = target.getZ() - this.getZ();
                float h = MathHelper.sqrt(e * e + g * g) * 0.2F;
                snowballEntity.setVelocity(e, f + (double)h, g, 3.2F, 1.0F);
                this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
                this.world.spawnEntity(snowballEntity);
            }
            else if (statusEffectInstance_one.getEffectType() == StatusEffects.WEAKNESS){
                WeaknessSnowballEntity snowballEntity = new WeaknessSnowballEntity(this.world, this);
                if (statusEffectInstance_one.getAmplifier() > 0) {
                    snowballEntity.setIsAmplified(true);
                }
                double d = target.getEyeY() - 1.100000023841858D;
                double e = target.getX() - this.getX();
                double f = d - snowballEntity.getY();
                double g = target.getZ() - this.getZ();
                float h = MathHelper.sqrt((float) (e * e + g * g)) * 0.2F;
                snowballEntity.setVelocity(e, f + (double) h, g, 1.6F, 12.0F);
                this.playSound(SoundEvents.ENTITY_SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
                this.world.spawnEntity(snowballEntity);
                callbackInfo.cancel();
            }

        }

    }

    @Override
    public void tick(){
        if(getStatusEffectInstance_one() != null && getStatusEffectInstance_one().getDuration() < 20 && applicableStatusEffects.contains(getStatusEffectInstance_one().getEffectType())){
            statusEffectInstance_one = new StatusEffectInstance(getStatusEffectInstance_one().getEffectType(), permanentDuration, getStatusEffectInstance_one().getAmplifier());
            this.addStatusEffect(statusEffectInstance_one);
        }
        super.tick();
    }




}
