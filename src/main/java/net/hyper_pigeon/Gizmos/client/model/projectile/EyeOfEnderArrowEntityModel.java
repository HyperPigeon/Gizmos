// Made with Blockbench 4.2.4
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports

package net.hyper_pigeon.Gizmos.client.model.projectile;

import net.hyper_pigeon.Gizmos.entities.EyeOfEnderArrowEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EyeOfEnderArrowEntityModel extends AnimatedGeoModel<EyeOfEnderArrowEntity> {

	@Override
	public Identifier getModelResource(EyeOfEnderArrowEntity object) {
		return new Identifier("gizmos", "geo/eye_of_ender_arrow.geo.json");
	}

	@Override
	public Identifier getTextureResource(EyeOfEnderArrowEntity object) {
		return new Identifier("gizmos", "textures/items/eye_of_ender_arrow.png");
	}

	@Override
	public Identifier getAnimationResource(EyeOfEnderArrowEntity animatable) {
		return null;
	}
}