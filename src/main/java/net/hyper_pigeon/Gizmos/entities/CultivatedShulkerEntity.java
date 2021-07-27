package net.hyper_pigeon.Gizmos.entities;

import net.hyper_pigeon.Gizmos.registry.GizmoEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import java.util.EnumSet;

public class CultivatedShulkerEntity extends ShulkerEntity implements TamedMonster{

    public CultivatedShulkerEntity(EntityType<? extends ShulkerEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new CultivatedShulkerEntity.ShootBulletGoal());
        this.goalSelector.add(3, new CultivatedShulkerEntity.PeekGoal());
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.targetSelector.add(3, new CultivatedShulkerEntity.SearchForTargetGoal(this));
    }


    class ShootBulletGoal extends Goal {
        private int counter;

        public ShootBulletGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        public boolean canStart() {
            LivingEntity livingEntity = CultivatedShulkerEntity.this.getTarget();
            if (livingEntity != null && livingEntity.isAlive()) {
                return CultivatedShulkerEntity.this.world.getDifficulty() != Difficulty.PEACEFUL;
            } else {
                return false;
            }
        }

        public void start() {
            this.counter = 20;
            CultivatedShulkerEntity.this.setPeekAmount(100);
        }

        public void stop() {
            CultivatedShulkerEntity.this.setPeekAmount(0);
        }

        public void tick() {
            if (CultivatedShulkerEntity.this.world.getDifficulty() != Difficulty.PEACEFUL) {
                --this.counter;
                LivingEntity livingEntity = CultivatedShulkerEntity.this.getTarget();
                CultivatedShulkerEntity.this.getLookControl().lookAt(livingEntity, 180.0F, 180.0F);
                double d = CultivatedShulkerEntity.this.squaredDistanceTo(livingEntity);
                if (d < 400.0D) {
                    if (this.counter <= 0) {
                        this.counter = 20 + CultivatedShulkerEntity.this.random.nextInt(10) * 20 / 2;
                        CultivatedShulkerEntity.this.world.spawnEntity(new ShulkerBulletEntity(CultivatedShulkerEntity.this.world, CultivatedShulkerEntity.this, livingEntity, CultivatedShulkerEntity.this.getAttachedFace().getAxis()));
                        CultivatedShulkerEntity.this.playSound(SoundEvents.ENTITY_SHULKER_SHOOT, 2.0F, (CultivatedShulkerEntity.this.random.nextFloat() - CultivatedShulkerEntity.this.random.nextFloat()) * 0.2F + 1.0F);
                    }
                } else {
                    CultivatedShulkerEntity.this.setTarget((LivingEntity)null);
                }

                super.tick();
            }
        }
    }


    class PeekGoal extends Goal {
        private int counter;

        private PeekGoal() {
        }

        public boolean canStart() {
            return CultivatedShulkerEntity.this.getTarget() == null && CultivatedShulkerEntity.this.random.nextInt(40) == 0;
        }

        public boolean shouldContinue() {
            return CultivatedShulkerEntity.this.getTarget() == null && this.counter > 0;
        }

        public void start() {
            this.counter = 20 * (1 + CultivatedShulkerEntity.this.random.nextInt(3));
            CultivatedShulkerEntity.this.setPeekAmount(30);
        }

        public void stop() {
            if (CultivatedShulkerEntity.this.getTarget() == null) {
                CultivatedShulkerEntity.this.setPeekAmount(0);
            }

        }

        public void tick() {
            --this.counter;
        }
    }


    static class SearchForTargetGoal extends FollowTargetGoal<LivingEntity> {
        public SearchForTargetGoal(CultivatedShulkerEntity shulker) {
            super(shulker, LivingEntity.class, 10, true, false, (entity) -> {
                return !(entity instanceof TamedMonster) && entity instanceof Monster;
            });
        }

        public boolean canStart() {
            return super.canStart();
        }

        protected Box getSearchBox(double distance) {
            Direction direction = ((ShulkerEntity)this.mob).getAttachedFace();
            if (direction.getAxis() == Direction.Axis.X) {
                return this.mob.getBoundingBox().expand(4.0D, distance, distance);
            } else {
                return direction.getAxis() == Direction.Axis.Z ? this.mob.getBoundingBox().expand(distance, distance, 4.0D) : this.mob.getBoundingBox().expand(distance, 4.0D, distance);
            }
        }
    }
}
