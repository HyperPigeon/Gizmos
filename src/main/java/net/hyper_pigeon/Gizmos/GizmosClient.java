package net.hyper_pigeon.Gizmos;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.hyper_pigeon.Gizmos.blocks.FireworkStarBlockScreen;
import net.hyper_pigeon.Gizmos.entities.CultivatedShulkerEntityRenderer;
import net.hyper_pigeon.Gizmos.entities.TamedRavagerEntityRenderer;
import net.hyper_pigeon.Gizmos.registry.GizmoBlocks;
import net.hyper_pigeon.Gizmos.registry.GizmoEntities;
import net.minecraft.client.render.entity.RavagerEntityRenderer;

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

        ScreenRegistry.register(GizmoBlocks.FIREWORK_STAR_BLOCK_CRAFTING_SCREEN_HANDLER_SCREEN_HANDLER_TYPE, FireworkStarBlockScreen::new);


    }

}
