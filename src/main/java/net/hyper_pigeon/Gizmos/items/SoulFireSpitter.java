package net.hyper_pigeon.Gizmos.items;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class SoulFireSpitter extends ToolItem {

    private static final Predicate<LivingEntity> LIVING_ENTITY_FILTER = Objects::nonNull;

    public SoulFireSpitter(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return (ingredient.getItem().equals(Items.BLAZE_POWDER));
    }

    public boolean isDamageable(){
        return true;
    }


//    public void createFlames(Entity user, float pitch, float yaw, float roll, float modifierZ, float modifierXYZ){
//        float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
//        float g = -MathHelper.sin((pitch + roll) * 0.017453292F);
//        float h = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
//        Vec3d vec3d =  user.getCameraPosVec(1F);
//        user.getEntityWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, vec3d.x, vec3d.y, vec3d.z, 10*f,10*g,
//                10*h);
//    }
//    public SpriteProvider spriteProvider = new SpriteProvider() {
//        @Override
//        public Sprite getSprite(int i, int j) {
//            return (Sprite)this.sprites.get(i * (this.sprites.size() - 1) / j);
//        }
//
//        @Override
//        public Sprite getSprite(Random random) {
//            return null;
//        }
//    };


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getStackInHand(hand);
        Vec3d rotationVec = playerEntity.getRotationVec(1F);
        Vec3d camera = playerEntity.getCameraPosVec(1F);
        double rotationVec_magnitude = MathHelper.sqrt((rotationVec.x* rotationVec.x)+
                (rotationVec.z*rotationVec.z));
        float angle = (float) Math.atan2(rotationVec.z,rotationVec.x);
        double z_increase = 3*rotationVec_magnitude*(MathHelper.sin(angle));
        double x_increase = 3*rotationVec_magnitude*(MathHelper.cos(angle));



        Box basicBox = playerEntity.getBoundingBox().expand(1);

        Box flameBox = basicBox.offset(x_increase,0,z_increase);
        List<LivingEntity> list = playerEntity.world.getEntitiesByClass(LivingEntity.class, flameBox,
                LIVING_ENTITY_FILTER);




        float f = -MathHelper.sin(playerEntity.yaw * 0.017453292F) * MathHelper.cos(playerEntity.pitch * 0.017453292F);
        float g = -MathHelper.sin((playerEntity.pitch +
                (playerEntity.world.isClient ? playerEntity.getRoll() : 0)) *
                0.017453292F);
        float h = MathHelper.cos(playerEntity.yaw * 0.017453292F) * MathHelper.cos(playerEntity.pitch* 0.017453292F);

        if(itemStack.getDamage() < itemStack.getMaxDamage()) {

            for (double x = (camera.x - 1); x <= camera.x + 1; x = x+0.2) {
                for (double y = (camera.y - 1); y <= camera.y + 1; y = y+0.2) {
                    for (double z = (camera.z - 1); z <= camera.z + 1; z = z+0.2) {
                        playerEntity.getEntityWorld().addParticle
                                (ParticleTypes.SOUL_FIRE_FLAME, x + x_increase, y, z + z_increase, 0.01 * f, g,
                                        0.01 * h);
                    }
                }
            }


//        MinecraftClient.getInstance().particleManager
//                .addParticle(new SoulFireSpitterParticle(MinecraftClient.getInstance().world, camera.x, camera.y, camera.z, 0.1*f,g,
//                        0.1*h));


            for (LivingEntity livingEntity : list) {
                if (!livingEntity.equals(playerEntity)) {
                    livingEntity.setAttacker(playerEntity);
                    livingEntity.setOnFireFor(5);
                    livingEntity.damage(DamageSource.ON_FIRE, 5);
                }

            }

            Iterator iterator = BlockPos.iterate
                    (MathHelper.floor(flameBox.minX),
                            MathHelper.floor(flameBox.minY),
                            MathHelper.floor(flameBox.minZ),
                            MathHelper.floor(flameBox.maxX),
                            MathHelper.floor(flameBox.maxY),
                            MathHelper.floor(flameBox.maxZ)).iterator();

            while (iterator.hasNext()) {
                BlockPos blockPos = (BlockPos) iterator.next();
                BlockState blockState = world.getBlockState(blockPos);

                if (CampfireBlock.method_30035(blockState)) {
                    world.playSound(playerEntity, blockPos, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.BLOCKS, 1.0F, RANDOM.nextFloat() * 0.4F + 0.8F);
                    world.setBlockState(blockPos, blockState.with(Properties.LIT, true), 11);
                } else {
                    BlockPos blockPos2 = blockPos.offset(playerEntity.getMovementDirection());
                    if (AbstractFireBlock.method_30032(world, blockPos2, playerEntity.getMovementDirection())) {
                        world.playSound(playerEntity, blockPos2, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.BLOCKS, 1.0F, RANDOM.nextFloat() * 0.4F + 0.8F);
                        BlockState blockState2 = AbstractFireBlock.getState(world, blockPos2);
                        world.setBlockState(blockPos2, blockState2, 11);
                    }
                }
            }

            //Criteria.ITEM_DURABILITY_CHANGED.trigger((ServerPlayerEntity) playerEntity, itemStack, 1);

//        itemStack.damage(1, playerEntity, ((e) -> {
//            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
//        }));

        }

        itemStack.setDamage(itemStack.getDamage()+1);



        return TypedActionResult.success(itemStack);
        //return new TypedActionResult<>(ActionResult.SUCCESS, playerEntity.getStackInHand(hand));
    }
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }


}
