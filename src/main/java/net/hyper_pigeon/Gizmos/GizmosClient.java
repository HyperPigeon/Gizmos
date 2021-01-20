package net.hyper_pigeon.Gizmos;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.hyper_pigeon.Gizmos.blocks.FireworkStarBlockScreen;
import net.hyper_pigeon.Gizmos.entities.CultivatedShulkerEntityRenderer;
import net.hyper_pigeon.Gizmos.entities.EyeOfEnderArrowEntityRenderer;
import net.hyper_pigeon.Gizmos.entities.TamedRavagerEntityRenderer;
import net.hyper_pigeon.Gizmos.registry.GizmoBlocks;
import net.hyper_pigeon.Gizmos.registry.GizmoEntities;
import net.hyper_pigeon.Gizmos.registry.GizmoItems;
import net.minecraft.client.render.entity.RavagerEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GizmosClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(GizmoEntities.CULTIVATED_SHULKER_ENTITY, (dispatcher, context) -> {
            return new CultivatedShulkerEntityRenderer(dispatcher);
        });

        EntityRendererRegistry.INSTANCE.register(GizmoEntities.TAMED_RAVAGER_ENTITY, (dispatcher, context) -> {
            return new RavagerEntityRenderer(dispatcher);
        });

        EntityRendererRegistry.INSTANCE.register(GizmoEntities.EYE_OF_ENDER_ARROW_ENTITY, (dispatcher, context) -> {
            return new EyeOfEnderArrowEntityRenderer(dispatcher);
        });

        FabricModelPredicateProviderRegistry.register(GizmoItems.SOUL_FIRE_SPITTER, new Identifier("tank_amount"),
                (itemStack, clientWorld, livingEntity) -> {
                    if(itemStack.getDamage() >= itemStack.getMaxDamage()) {
                        //System.out.println("check0");
                        return 0.0F;
                    }
                    else if (itemStack.getDamage() >= 3*itemStack.getMaxDamage()/4){
                        //System.out.println("check1");
                        return 1.0F;
                    }
                    else if (itemStack.getDamage() >= itemStack.getMaxDamage()/2){
                        //System.out.println("check2");
                        return 2.0F;
                    }
                    else if (itemStack.getDamage() >= itemStack.getMaxDamage()/4){
                        //System.out.println("check3");
                        return 3.0F;
                    }
                    else {
                        //System.out.println("check4");
                        return 4.0F;
                    }
                });

        FabricModelPredicateProviderRegistry.register(GizmoItems.SLINGSHOT, new Identifier("slingshot_pull"),
                (itemStack, clientWorld, livingEntity) -> {
                    if (livingEntity == null) {
                        return 0.0F;
                    }
                    else {
                        return livingEntity.getActiveItem() != itemStack ? 0.0F : (float)(itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0F;
                    }
                });

        FabricModelPredicateProviderRegistry.register(GizmoItems.SLINGSHOT, new Identifier("slingshot_pulling"),
                (itemStack, clientWorld, livingEntity) -> {
                    return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
                });


        ScreenRegistry.register(GizmoBlocks.FIREWORK_STAR_BLOCK_CRAFTING_SCREEN_HANDLER_SCREEN_HANDLER_TYPE, FireworkStarBlockScreen::new);



    }

}
