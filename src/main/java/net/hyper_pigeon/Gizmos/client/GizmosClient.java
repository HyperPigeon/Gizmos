package net.hyper_pigeon.Gizmos.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.hyper_pigeon.Gizmos.blocks.FireworkStarBlockScreen;
import net.hyper_pigeon.Gizmos.client.render.EyeOfEnderArrowEntityRenderer;
import net.hyper_pigeon.Gizmos.registry.GizmoBlocks;
import net.hyper_pigeon.Gizmos.registry.GizmoEntities;
import net.hyper_pigeon.Gizmos.registry.GizmoItems;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.RavagerEntityRenderer;
import net.minecraft.client.render.entity.ShulkerEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GizmosClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.INSTANCE.register(GizmoEntities.COBBLESTONE_PROJECTILE_ENTITY, FlyingItemEntityRenderer::new);

        EntityRendererRegistry.INSTANCE.register(GizmoEntities.CULTIVATED_SHULKER_ENTITY, ShulkerEntityRenderer::new);

        EntityRendererRegistry.INSTANCE.register(GizmoEntities.TAMED_RAVAGER_ENTITY, RavagerEntityRenderer::new);

        EntityRendererRegistry.INSTANCE.register(GizmoEntities.EYE_OF_ENDER_ARROW_ENTITY, EyeOfEnderArrowEntityRenderer::new);

        ScreenRegistry.register(GizmoBlocks.FIREWORK_STAR_BLOCK_CRAFTING_SCREEN_HANDLER_SCREEN_HANDLER_TYPE, FireworkStarBlockScreen::new);


        FabricModelPredicateProviderRegistry.register(GizmoItems.SOUL_FIRE_SPITTER, new Identifier("tank_amount"),
                (itemStack, clientWorld, livingEntity, i) -> {
                    if(itemStack.getDamage() >= itemStack.getMaxDamage()) {
                        return 0.0F;
                    }
                    else if (itemStack.getDamage() >= 3*(itemStack.getMaxDamage()/4)){
                        return 1.0F;
                    }
                    else if (itemStack.getDamage() >= itemStack.getMaxDamage()/2){
                        return 2.0F;
                    }
                    else if (itemStack.getDamage() >= itemStack.getMaxDamage()/4){
                        return 3.0F;
                    }
                    else {
                        return 4.0F;
                    }
                });

        FabricModelPredicateProviderRegistry.register(GizmoItems.SLINGSHOT, new Identifier("slingshot_pull"),
                (itemStack, clientWorld, livingEntity, i) -> {
                    if (livingEntity == null) {
                        return 0.0F;
                    }
                    else {
                        return livingEntity.getActiveItem() != itemStack ? 0.0F : (float)(itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0F;
                    }
                });

        FabricModelPredicateProviderRegistry.register(GizmoItems.SLINGSHOT, new Identifier("slingshot_pulling"),
                (itemStack, clientWorld, livingEntity, i) -> {
                    return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
                });
    }

}
