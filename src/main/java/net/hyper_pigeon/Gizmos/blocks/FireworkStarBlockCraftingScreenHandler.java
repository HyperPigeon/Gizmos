package net.hyper_pigeon.Gizmos.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.hyper_pigeon.Gizmos.registry.GizmoBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.Optional;

public class FireworkStarBlockCraftingScreenHandler extends AbstractRecipeScreenHandler {
    private final CraftingInventory input;
    private final CraftingResultInventory result;
    private final ScreenHandlerContext context;
    private final PlayerEntity player;

    public FireworkStarBlockCraftingScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public FireworkStarBlockCraftingScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(GizmoBlocks.FIREWORK_STAR_BLOCK_CRAFTING_SCREEN_HANDLER_SCREEN_HANDLER_TYPE, syncId);
        this.input = new CraftingInventory(this, 4, 4);
        this.result = new CraftingResultInventory();
        this.context = context;
        this.player = playerInventory.player;
        this.addSlot(new CraftingResultSlot(playerInventory.player, this.input, this.result, 0, 146, 33));

        int m;
        int l;
        int index = 0;

        for(m = 0; m < 4; ++m) {
            for(l = 0; l < 1; ++l) {

                switch(index){
                    case 0:
                        this.addSlot(new Slot(this.input, index,  14 + l * 18, 5 + m * 18){
                            public boolean canInsert(ItemStack stack) {
                                return stack.getItem().equals(Items.GUNPOWDER);
                            }
                        });
                        break;

//                    case 1:
//                        this.addSlot(new Slot(this.input, index, 30 + l * 18, 17 + m * 18){
//                            public boolean canInsert(ItemStack stack) {
//                                return stack.getItem() instanceof DyeItem;
//                            }
//                        });
//                        break;
//
//                    case 3:
//                        this.addSlot(new Slot(this.input, index, 30 + l * 18, 17 + m * 18){
//                            public boolean canInsert(ItemStack stack) {
//                                return stack.getItem() instanceof DyeItem;
//                            }
//                        });
//                        break;

                    case 2:
                        this.addSlot(new Slot(this.input, index, 14 + l * 18, 5 + m * 18){
                            public boolean canInsert(ItemStack stack) {
                                return stack.getItem().equals(Items.DIAMOND);
                            }
                        });
                        break;

                    case 3:
                        this.addSlot(new Slot(this.input, index,14 + l * 18, 5 + m * 18){
                            public boolean canInsert(ItemStack stack) {
                                return stack.getItem().equals(Items.GLOWSTONE_DUST);
                            }
                        });
                        break;
                    default:
                        this.addSlot(new Slot(this.input, index, 14 + l * 18, 5 + m * 18));
                        break;
                }

                //this.addSlot(new Slot(this.input, index, 30 + l * 18, 17 + m * 18));
                index++;
            }
        }

        for(m = 0; m < 4; ++m) {
            for(l = 1; l < 4; ++l) {
                this.addSlot(new Slot(this.input, index,  21 + l * 18, 5 + m * 18) {
                    public boolean canInsert(ItemStack stack) {
                        return stack.getItem() instanceof DyeItem;
                    }
                });
                index++;
            }
        }
    
        for(m = 0; m < 3; ++m) {
            for(l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 102 + m * 18));
            }
        }

        for(m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 160));
        }

    }

    protected static void updateResult(int syncId, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftingResultInventory resultInventory) {
        if (!world.isClient) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)player;
            ItemStack itemStack = ItemStack.EMPTY;
            Optional<CraftingRecipe> optional = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);
            if (optional.isPresent()) {
                CraftingRecipe craftingRecipe = optional.get();
                if(craftingRecipe instanceof FireworkStarRecipe) {
                    if (resultInventory.shouldCraftRecipe(world, serverPlayerEntity, craftingRecipe)) {
                        itemStack = craftingRecipe.craft(craftingInventory);
                    }
                }
            }

            resultInventory.setStack(0, itemStack);
            serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(syncId, 0, itemStack));
        }
    }

    public void onContentChanged(Inventory inventory) {
        this.context.run((world, blockPos) -> {
            updateResult(this.syncId, world, this.player, this.input, this.result);
        });
    }

    public void populateRecipeFinder(RecipeFinder finder) {
        this.input.provideRecipeInputs(finder);
    }

    public void clearCraftingSlots() {
        this.input.clear();
        this.result.clear();
    }

    public boolean matches(Recipe recipe) {
        return recipe.matches(this.input, this.player.world);
    }

    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, blockPos) -> {
            this.dropInventory(player, world, this.input);
        });
    }

    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, GizmoBlocks.FIREWORK_STAR_BLOCK);
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index == 0) {
                this.context.run((world, blockPos) -> {
                    itemStack2.getItem().onCraft(itemStack2, world, player);
                });
                if (!this.insertItem(itemStack2, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onStackChanged(itemStack2, itemStack);
            } else if (index >= 10 && index < 46) {
                if (!this.insertItem(itemStack2, 1, 10, false)) {
                    if (index < 37) {
                        if (!this.insertItem(itemStack2, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.insertItem(itemStack2, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.insertItem(itemStack2, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack itemStack3 = slot.onTakeItem(player, itemStack2);
            if (index == 0) {
                player.dropItem(itemStack3, false);
            }
        }

        return itemStack;
    }

    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.result && super.canInsertIntoSlot(stack, slot);
    }

    public int getCraftingResultSlotIndex() {
        return 0;
    }

    public int getCraftingWidth() {
        return this.input.getWidth();
    }

    public int getCraftingHeight() {
        return this.input.getHeight();
    }

    @Environment(EnvType.CLIENT)
    public int getCraftingSlotCount() {
        return 7;
    }

    @Environment(EnvType.CLIENT)
    public RecipeBookCategory getCategory() {
        return RecipeBookCategory.CRAFTING;
    }
}
