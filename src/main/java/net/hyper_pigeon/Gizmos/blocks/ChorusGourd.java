package net.hyper_pigeon.Gizmos.blocks;

import net.hyper_pigeon.Gizmos.entities.CultivatedShulkerEntity;
import net.hyper_pigeon.Gizmos.registry.GizmoEntities;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class ChorusGourd extends GourdBlock {

    public ChorusGourd(Settings settings) {
        super(settings);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() == Items.SHEARS) {
            if (!world.isClient) {

                world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockState(pos, (BlockState)Blocks.AIR.getDefaultState(), 11);

                CultivatedShulkerEntity cultivatedShulkerEntity = new CultivatedShulkerEntity(GizmoEntities.CULTIVATED_SHULKER_ENTITY
                ,player.getEntityWorld());

                cultivatedShulkerEntity.refreshPositionAndAngles(pos.getX(),pos.getY(),pos.getZ(),0,0);

                world.spawnEntity(cultivatedShulkerEntity);

            }

            return super.onUse(state, world, pos, player, hand, hit);
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }

    @Override
    public StemBlock getStem() {
        return (StemBlock) Blocks.PUMPKIN_STEM;
    }

    @Override
    public AttachedStemBlock getAttachedStem() {
        return (AttachedStemBlock)Blocks.ATTACHED_PUMPKIN_STEM;
    }
}
