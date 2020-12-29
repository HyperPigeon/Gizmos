package net.hyper_pigeon.Gizmos.entities;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.ShulkerHeadFeatureRenderer;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.Identifier;

public class CultivatedShulkerEntityRenderer extends
        MobEntityRenderer<ShulkerEntity, ShulkerEntityModel<ShulkerEntity>> {


    public CultivatedShulkerEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new ShulkerEntityModel<>(), 1);
        this.addFeature(new ShulkerHeadFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(ShulkerEntity entity) {
        return new Identifier("textures/entity/shulker/shulker.png");
    }


//    @Override
//    public Identifier getTexture(CultivatedShulkerEntity entity) {
//        return new Identifier("textures/entity/shulker/shulker.png");
//    }
}
