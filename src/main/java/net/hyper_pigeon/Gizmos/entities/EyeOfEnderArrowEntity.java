package net.hyper_pigeon.Gizmos.entities;

import net.hyper_pigeon.Gizmos.registry.GizmoEntities;
import net.hyper_pigeon.Gizmos.registry.GizmoItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.function.Predicate;

public class EyeOfEnderArrowEntity extends PersistentProjectileEntity implements IAnimatable {

    private Entity target;
    private boolean targetDNE = false;

    private AnimationFactory factory = new AnimationFactory(this);

    public final Predicate<LivingEntity> NON_ENDER_ENTITY_AND_GLOWING = (entity) -> {
        return entity.getType() != EntityType.ENDER_DRAGON && entity.getType() != EntityType.ENDERMAN &&
                entity != getOwner() && entity.hasStatusEffect(StatusEffects.GLOWING);
    };



    public EyeOfEnderArrowEntity(EntityType<EyeOfEnderArrowEntity> entityType, World world) {
        super(entityType, world);
    }


    public EyeOfEnderArrowEntity(World world, LivingEntity owner) {
        super(GizmoEntities.EYE_OF_ENDER_ARROW_ENTITY, owner, world);
    }

    public EyeOfEnderArrowEntity(double d, double e, double f, World world) {
        super(GizmoEntities.EYE_OF_ENDER_ARROW_ENTITY,d,e,f,world);
    }


    public void setTarget(Entity target){
        this.target = target;
    }


    public void tick() {
        super.tick();
        Vec3d vec3d;

        if (!this.world.isClient) {
            if(!targetDNE){
                if(target != null && target.isAlive()){
                    //this.setNoGravity(true)
                    //Vec3d vec3d1 = new Vec3d(this.getX() - this.targetX,this.getY() - this.targetY,this.getZ() - this.targetZ);

//                    Vec3d vec3d1 = this.getVelocity();
//                    Vec3d vec3d2 = (new Vec3d(target.getX() - this.getX(), target.getY() - this.getY(), target.getZ() - this.getZ()))
//                            .normalize().add(this.random.nextGaussian() * 0.007499999832361937D * (double)0,
//                                    this.random.nextGaussian() * 0.007499999832361937D * (double)0,
//                                    this.random.nextGaussian() * 0.007499999832361937D * (double)0).multiply((double)1.0);

                    this.setVelocity(target.getX() - this.getX(),target.getY() - this.getY() + 0.5,target.getZ() - this.getZ(),2.1f,0f);
                    //this.setVelocity(vec3d1.add(vec3d2));

                }
                else {
                    Box box = (new Box(this.getBlockPos())).expand(48.0D);

                    List<EnderDragonEntity> enderDragonList = this.world.getNonSpectatingEntities(EnderDragonEntity.class, box);
                    List<ShulkerEntity> shulkerEntityList = this.world.getNonSpectatingEntities(ShulkerEntity.class, box);
                    List<EndermanEntity> endermanList = this.world.getNonSpectatingEntities(EndermanEntity.class, box);
                    List<LivingEntity> livingEntities = this.world.getEntitiesByClass(LivingEntity.class, box,  NON_ENDER_ENTITY_AND_GLOWING);


                    if(!enderDragonList.isEmpty()){
                        setTarget(enderDragonList.get(0));
                    }
                    else if(!shulkerEntityList.isEmpty()){
                        setTarget(shulkerEntityList.get(0));
                    }
                    else if(!endermanList.isEmpty()){
                        //Random rand = new Random();
                        setTarget(endermanList.get(0));
    //                    EndermanEntity randomEnderman =  endermanList.get(rand.nextInt(endermanList.size()));
    //                    target = randomEnderman;
                    }
                    else if(!livingEntities.isEmpty()){
                        setTarget(livingEntities.get(0));
                    }
                    else {
                        targetDNE = true;
                    }

                }
            }

        }

//        this.checkBlockCollision();
          vec3d = this.getVelocity();
        double d = this.getX() + vec3d.x;
        double e = this.getY() + vec3d.y;
        double f = this.getZ() + vec3d.z;
//        this.updatePosition(this.getX() + vec3d.x, this.getY() + vec3d.y, this.getZ() + vec3d.z);
//        ProjectileUtil.method_7484(this, 0.5F);

        if (this.world.isClient) {
            this.world.addParticle
                    (ParticleTypes.PORTAL,
                            d - vec3d.x * 0.25D + this.random.nextDouble() * 0.6D - 0.3D,
                            e - vec3d.y * 0.25D - 0.5D,
                            f - vec3d.z * 0.25D + this.random.nextDouble() * 0.6D - 0.3D, vec3d.x, vec3d.y, vec3d.z);
        }



    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(GizmoItems.EYE_OF_ENDER_ARROW);
    }



    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<EyeOfEnderArrowEntity>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
