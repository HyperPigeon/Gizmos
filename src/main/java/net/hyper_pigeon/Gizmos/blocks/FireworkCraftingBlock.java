package net.hyper_pigeon.Gizmos.blocks;

import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class FireworkCraftingBlock extends BlockWithEntity {

    public FireworkCraftingBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return null;
    }
}
