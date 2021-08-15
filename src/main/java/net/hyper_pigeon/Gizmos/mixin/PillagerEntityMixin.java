package net.hyper_pigeon.Gizmos.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.CrossbowUser;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(PillagerEntity.class)
public abstract class PillagerEntityMixin extends IllagerEntity implements CrossbowUser {

    Random random = new Random();

    protected PillagerEntityMixin(EntityType<? extends IllagerEntity> entityType, World world) {
        super(entityType, world);
    }


    //gives 5% of pillagers a rocket in their offhand
    @Inject(at = @At("HEAD"), method = "initEquipment")
    protected void giveFireworkOffhand(LocalDifficulty difficulty, CallbackInfo callback) {

        int i = random.nextInt(100)+1;

        if(i <= 5) {
//            ListTag listTag = new ListTag();
//
//            ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET);
//            itemStack.setCount(64);
//
//            itemStack.getSubTag("Fireworks").put("Explosions", listTag);

            //item stack of fireworks
//            ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET);
//            //sets # of fireworks in stack to 64
//            itemStack.setCount(64);
//
//            CompoundTag ct = new CompoundTag();
//            ListTag lt = new ListTag();
//
//            lt.add(new CompoundTag());
//
//            ct.put("Explosions", lt);
//
//            itemStack.putSubTag("Fireworks", ct);

            ItemStack fireworkStack = new ItemStack(Items.FIREWORK_ROCKET);
            fireworkStack.setCount(64);
            try {
                fireworkStack.setSubNbt("Fireworks", StringNbtReader.parse("{Explosions:[{Type:1b,Flicker:1,Colors:[I;3887386,14602026],FadeColors:[I;5320730]}],Flight:3b}"));
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
            }

            this.equipStack(EquipmentSlot.OFFHAND, fireworkStack);
        }
    }


}
