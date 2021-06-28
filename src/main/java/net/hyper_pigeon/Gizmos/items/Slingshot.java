package net.hyper_pigeon.Gizmos.items;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Predicate;

public class Slingshot extends RangedWeaponItem implements Vanishable {
    public static final Predicate<ItemStack> SLINGSHOT_PROJECTILES = (stack) -> {
        return (stack.getItem() == Items.ENDER_PEARL || stack.getItem() == Items.EGG
                || stack.getItem() == Items.SNOWBALL);
    };

    public Slingshot(Settings settings) {
        super(settings);
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return SLINGSHOT_PROJECTILES ;
    }

    @Override
    public int getRange() {
        return 50;
    }


    public ItemStack getSlingshotAmmoType(PlayerEntity user, ItemStack stack) {
        if (!(stack.getItem() instanceof RangedWeaponItem)) {
            return ItemStack.EMPTY;
        } else {
            Predicate<ItemStack> predicate = ((RangedWeaponItem)stack.getItem()).getHeldProjectiles();
            ItemStack itemStack = RangedWeaponItem.getHeldProjectile(user, predicate);
            if (!itemStack.isEmpty()) {
                return itemStack;
            } else {
                predicate = ((RangedWeaponItem)stack.getItem()).getProjectiles();

                for(int i = 0; i < user.getInventory().size(); ++i) {
                    ItemStack itemStack2 = user.getInventory().getStack(i);
                    if (predicate.test(itemStack2)) {
                        return itemStack2;
                    }
                }

                return user.isCreative() ? new ItemStack(Items.EGG) : ItemStack.EMPTY;
            }
        }
    }


    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)user;
            boolean bl = playerEntity.isCreative() || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemStack = getSlingshotAmmoType(playerEntity, stack);
            if (!itemStack.isEmpty() || bl) {
                if (itemStack.isEmpty()) {
                    itemStack = new ItemStack(Items.EGG);
                }

                int i = this.getMaxUseTime(stack) - remainingUseTicks;
                float f = getPullProgress(i);
                if ((double)f >= 0.1D) {
                    boolean bl2 = bl && (itemStack.getItem() == Items.ENDER_PEARL || itemStack.getItem() == Items.EGG
                            || itemStack.getItem() == Items.SNOWBALL);

                    if (!world.isClient) {
                        if(itemStack.getItem() == Items.ENDER_PEARL){
                            EnderPearlEntity persistentProjectileEntity = new EnderPearlEntity(world, playerEntity);
                            persistentProjectileEntity.setProperties(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, f * 3.0F, 1.0F);

                            world.spawnEntity(persistentProjectileEntity);
                        }
                        if(itemStack.getItem() == Items.EGG){
                            EggEntity persistentProjectileEntity = new EggEntity(world, playerEntity);
                            persistentProjectileEntity.setProperties(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, f * 3.0F, 1.0F);

                            world.spawnEntity(persistentProjectileEntity);
                        }
                        if(itemStack.getItem() == Items.SNOWBALL){
                            SnowballEntity persistentProjectileEntity = new SnowballEntity(world, playerEntity);
                            persistentProjectileEntity.setProperties(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, f * 3.0F, 1.0F);
                        }

                        stack.damage(1, playerEntity, ((e) -> {
                                e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                        }));

                        Random random = new Random();

                        world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                        if (!bl2 && !playerEntity.isCreative()) {
                            itemStack.decrement(1);
                            if (itemStack.isEmpty()) {
                                playerEntity.getInventory().removeOne(itemStack);
                            }
                        }

                    }
                }
            }

        }
    }


    public static float getPullProgress(int useTicks) {
        float f = (float)useTicks / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }


    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        boolean bl = !getSlingshotAmmoType(user,itemStack).isEmpty();
        if (!user.isCreative() && !bl) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }
}
