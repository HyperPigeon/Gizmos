package net.hyper_pigeon.Gizmos.blocks;

import net.hyper_pigeon.Gizmos.registry.GizmoBlocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

public class FireworkCraftingBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {


    public FireworkCraftingBlockEntity(BlockEntityType<?> type) {
        super(type);
    }

    @Override
    public Text getDisplayName() {
        return null;
    }


    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return null;
    }
}
