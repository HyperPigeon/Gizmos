package net.hyper_pigeon.Gizmos;


import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.hyper_pigeon.Gizmos.config.GizmosConfig;
import net.hyper_pigeon.Gizmos.mixin.MixinConditions;
import net.hyper_pigeon.Gizmos.registry.GizmoBlocks;
import net.hyper_pigeon.Gizmos.registry.GizmoEnchantments;
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
		FabricModelPredicateProviderRegistry.register(GizmoItems.SOUL_FIRE_SPITTER, new Identifier("tank_amount"),
				(itemStack, clientWorld, livingEntity, i) -> {
					if(itemStack.getDamage() >= itemStack.getMaxDamage()) {
						System.out.println("check0");
						return 0.0F;
					}
					else if (itemStack.getDamage() >= 3*(itemStack.getMaxDamage()/4)){
						System.out.println("check1");
						return 1.0F;
					}
					else if (itemStack.getDamage() >= itemStack.getMaxDamage()/2){
						System.out.println("check2");
						return 2.0F;
					}
					else if (itemStack.getDamage() >= itemStack.getMaxDamage()/4){
						System.out.println("check3");
						return 3.0F;
					}
					else {
						System.out.println("check4");
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

		GizmoEnchantments.init();


	}
}
