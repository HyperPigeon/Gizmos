package net.hyper_pigeon.Gizmos.entities;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class EyeOfEnderArrowEntityRenderer extends ProjectileEntityRenderer<EyeOfEnderArrowEntity> {

    public static final Identifier TEXTURE = new Identifier("textures/entity/projectiles/arrow.png");

    public EyeOfEnderArrowEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher);
    }

    @Override
    public Identifier getTexture(EyeOfEnderArrowEntity entity) {
        return TEXTURE;
    }
}
