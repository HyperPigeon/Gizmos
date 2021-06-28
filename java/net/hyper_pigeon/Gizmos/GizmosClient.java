package net.hyper_pigeon.Gizmos;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.hyper_pigeon.Gizmos.blocks.FireworkStarBlockScreen;
import net.hyper_pigeon.Gizmos.entities.EyeOfEnderArrowEntityRenderer;
import net.hyper_pigeon.Gizmos.registry.GizmoBlocks;
import net.hyper_pigeon.Gizmos.registry.GizmoEntities;
import net.minecraft.client.render.entity.RavagerEntityRenderer;
import net.minecraft.client.render.entity.ShulkerEntityRenderer;

@Environment(EnvType.CLIENT)
public class GizmosClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(GizmoEntities.CULTIVATED_SHULKER_ENTITY, ShulkerEntityRenderer::new);

        EntityRendererRegistry.INSTANCE.register(GizmoEntities.TAMED_RAVAGER_ENTITY, RavagerEntityRenderer::new);

        EntityRendererRegistry.INSTANCE.register(GizmoEntities.EYE_OF_ENDER_ARROW_ENTITY, EyeOfEnderArrowEntityRenderer::new);

        ScreenRegistry.register(GizmoBlocks.FIREWORK_STAR_BLOCK_CRAFTING_SCREEN_HANDLER_SCREEN_HANDLER_TYPE, FireworkStarBlockScreen::new);



    }

}
