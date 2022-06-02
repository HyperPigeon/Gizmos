package net.hyper_pigeon.Gizmos.client.render;

import net.hyper_pigeon.Gizmos.client.model.projectile.EyeOfEnderArrowEntityModel;
import net.hyper_pigeon.Gizmos.entities.EyeOfEnderArrowEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class EyeOfEnderArrowEntityRenderer extends GeoProjectilesRenderer<EyeOfEnderArrowEntity> {

    public static final Identifier TEXTURE = new Identifier("gizmos","textures/entity/projectiles/eye_of_ender_arrow.png");

    public EyeOfEnderArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new EyeOfEnderArrowEntityModel());
    }

    @Override
    public Identifier getTexture(EyeOfEnderArrowEntity entity) {
        return TEXTURE;
    }
}
