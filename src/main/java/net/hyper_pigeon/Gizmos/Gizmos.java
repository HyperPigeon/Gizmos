package net.hyper_pigeon.Gizmos;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.hyper_pigeon.Gizmos.config.GizmosConfig;
import net.hyper_pigeon.Gizmos.mixin.MixinConditions;
import net.hyper_pigeon.Gizmos.registry.GizmoBlocks;
import net.hyper_pigeon.Gizmos.registry.GizmoEntities;
import net.hyper_pigeon.Gizmos.registry.GizmoItems;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Gizmos implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Gizmos");

	public static final GizmosConfig CONFIG = MixinConditions.CONFIG;

	@Override
	public void onInitialize() {
		GizmoItems.init();
		GizmoEntities.init();
		GizmoBlocks.init();
	}
}
