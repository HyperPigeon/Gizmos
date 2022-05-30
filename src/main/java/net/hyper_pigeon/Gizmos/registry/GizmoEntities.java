package net.hyper_pigeon.Gizmos.registry;


import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.hyper_pigeon.Gizmos.Gizmos;
import net.hyper_pigeon.Gizmos.entities.CobblestoneProjectileEntity;
import net.hyper_pigeon.Gizmos.entities.CultivatedShulkerEntity;
import net.hyper_pigeon.Gizmos.entities.EyeOfEnderArrowEntity;
import net.hyper_pigeon.Gizmos.entities.TamedRavagerEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GizmoEntities {

    public static final EntityType<CultivatedShulkerEntity> CULTIVATED_SHULKER_ENTITY = Registry.register(
            Registry.ENTITY_TYPE, new Identifier("gizmos", "cultivated_shulker"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CultivatedShulkerEntity::new).fireImmune().dimensions(EntityDimensions.fixed(1, 1)).build()
    );

    public static final EntityType<TamedRavagerEntity> TAMED_RAVAGER_ENTITY = Registry.register(
            Registry.ENTITY_TYPE, new Identifier("gizmos","tamed_ravager"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TamedRavagerEntity::new).dimensions(EntityDimensions.fixed(1.95F, 2.2F)).build());

    public static final EntityType<EyeOfEnderArrowEntity> EYE_OF_ENDER_ARROW_ENTITY = Registry.register(
      Registry.ENTITY_TYPE, new Identifier("gizmos","eye_of_ender_arrow"),
            FabricEntityTypeBuilder.<EyeOfEnderArrowEntity>create(SpawnGroup.MISC, EyeOfEnderArrowEntity::new).dimensions(EntityDimensions.fixed(0.5F, 0.5F))
                    .trackable(4,20).build());

    public static final EntityType<CobblestoneProjectileEntity> COBBLESTONE_PROJECTILE_ENTITY = Registry.register(
            Registry.ENTITY_TYPE, new Identifier("gizmos","cobblestone_projectile"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, CobblestoneProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5F, 0.5F))
                    .trackable(4,20).build());



    public static void init(){
        if (Gizmos.CONFIG.chorusGourdAndCultivatedShulkers)
            FabricDefaultAttributeRegistry.register(CULTIVATED_SHULKER_ENTITY, ShulkerEntity.createShulkerAttributes());

        if (Gizmos.CONFIG.rideableRavagers)
            FabricDefaultAttributeRegistry.register(TAMED_RAVAGER_ENTITY, RavagerEntity.createRavagerAttributes());

    }
}
