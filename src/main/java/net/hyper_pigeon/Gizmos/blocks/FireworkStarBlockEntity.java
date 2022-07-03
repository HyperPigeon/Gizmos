package net.hyper_pigeon.Gizmos.blocks;

import net.hyper_pigeon.Gizmos.registry.GizmoBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.math.BlockPos;

public class FireworkStarBlockEntity extends BlockEntity implements NamedScreenHandlerFactory{


    public FireworkStarBlockEntity(BlockPos pos, BlockState state) {
        super(GizmoBlocks.FIREWORK_STAR_BLOCK_ENTITY, pos, state);
    }

    @Override
    public Text getDisplayName() {
        //return new TranslatableText(getCachedState().getBlock().getTranslationKey());
        return (Text) new TranslatableTextContent("Firework Star Crafting");
    }


    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new FireworkStarBlockCraftingScreenHandler(syncId, inv, ScreenHandlerContext.create(world, pos));
    }
}
