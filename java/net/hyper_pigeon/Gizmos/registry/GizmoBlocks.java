package net.hyper_pigeon.Gizmos.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.hyper_pigeon.Gizmos.Gizmos;
import net.hyper_pigeon.Gizmos.blocks.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GizmoBlocks {
    public static final ChorusGourd CHORUS_GOURD =
            new ChorusGourd(AbstractBlock.Settings.of(Material.GOURD, MapColor.PURPLE).strength(1.0F).sounds(BlockSoundGroup.WOOD));

    public static final FireworkStarBlock FIREWORK_STAR_BLOCK =
            new FireworkStarBlock(AbstractBlock.Settings.of(Material.METAL, MapColor.GRAY).strength(1.0F).sounds(BlockSoundGroup.METAL));

//    public static final InfusedSnowBlock INFUSED_SNOW_BLOCK =
//            new InfusedSnowBlock(AbstractBlock.Settings.of(Material.SNOW_BLOCK, MaterialColor.WHITE).
//                    strength(1.0F).sounds(BlockSoundGroup.SNOW));

//    public static final FireworkCraftingBlock FIREWORK_CRAFTING_BLOCK =
//            new FireworkCraftingBlock(AbstractBlock.Settings.of(Material.NETHER_WOOD, MaterialColor.BROWN).strength(1.0F).sounds(BlockSoundGroup.WOOD));


    public static final ScreenHandlerType<FireworkStarBlockCraftingScreenHandler> FIREWORK_STAR_BLOCK_CRAFTING_SCREEN_HANDLER_SCREEN_HANDLER_TYPE =
           ScreenHandlerRegistry.registerSimple(new Identifier("gizmos","firework_star_crafting"),FireworkStarBlockCraftingScreenHandler::new);

    //The parameter of build at the very end is always null, do not worry about it
    public static final BlockEntityType<FireworkStarBlockEntity> FIREWORK_STAR_BLOCK_ENTITY =
            Registry.register(Registry.BLOCK_ENTITY_TYPE,
                    new Identifier("gizmos","firework_star_block"),
                    FabricBlockEntityTypeBuilder.create(FireworkStarBlockEntity::new, FIREWORK_STAR_BLOCK).build(null));

//    public static final BlockEntityType<InfusedSnowBlockEntity> INFUSED_SNOW_BLOCK_ENTITY =
//            Registry.register(Registry.BLOCK_ENTITY_TYPE,
//                    new Identifier("gizmos", "infused_snow_block"),
//                    BlockEntityType.Builder.create(InfusedSnowBlockEntity::new,INFUSED_SNOW_BLOCK).build(null));

//    public static final BlockEntityType<FireworkCraftingBlockEntity> FIREWORK_CRAFTING_BLOCK_ENTITY =
//            Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("gizmos","firework_crafting_block"), BlockEntityType.Builder.create(FireworkCraftingBlockEntity::new, FIREWORK_CRAFTING_BLOCK).build(null));

    public static void init(){
        if (Gizmos.CONFIG.chorusGourdAndCultivatedShulkers) {
            Registry.register(Registry.BLOCK, new Identifier("gizmos", "chorus_gourd"), CHORUS_GOURD);
            Registry.register(Registry.ITEM, new Identifier("gizmos", "chorus_gourd"),
                    new BlockItem(CHORUS_GOURD, new Item.Settings().group(ItemGroup.MISC)));
        }

        if (Gizmos.CONFIG.fireworkStarBlock) {
            Registry.register(Registry.BLOCK, new Identifier("gizmos", "firework_star_block"), FIREWORK_STAR_BLOCK);
            Registry.register(Registry.ITEM, new Identifier("gizmos", "firework_star_block"),
                    new BlockItem(FIREWORK_STAR_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
        }


//        Registry.register(Registry.BLOCK, new Identifier("gizmos", "firework_crafting_block"), FIREWORK_CRAFTING_BLOCK);
//        Registry.register(Registry.ITEM, new Identifier("gizmos", "firework_crafting_block"),
//                new BlockItem(FIREWORK_CRAFTING_BLOCK, new Item.Settings().group(ItemGroup.MISC)));

        //FIREWORK_STAR_BLOCK_CRAFTING_SCREEN_HANDLER = Registry.register("firework_star_crafting",FireworkStarBlockCraftingScreenHandler::new);
    }
}
