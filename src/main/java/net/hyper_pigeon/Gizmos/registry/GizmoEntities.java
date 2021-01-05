package net.hyper_pigeon.Gizmos.registry;

import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.hyper_pigeon.Gizmos.Gizmos;
import net.hyper_pigeon.Gizmos.entities.CultivatedShulkerEntity;
import net.hyper_pigeon.Gizmos.entities.TamedRavagerEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GizmoEntities {

    public static final EntityType<CultivatedShulkerEntity> CULTIVATED_SHULKER_ENTITY = Registry.register(
            Registry.ENTITY_TYPE, new Identifier("gizmos", "cultivated_shulker"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CultivatedShulkerEntity::new).setImmuneToFire().size(EntityDimensions.fixed(1, 1)).build()
    );

    public static final EntityType<TamedRavagerEntity> TAMED_RAVAGER_ENTITY = Registry.register(
            Registry.ENTITY_TYPE, new Identifier("gizmos","tamed_ravager"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, TamedRavagerEntity::new).size(EntityDimensions.fixed(1.95F, 2.2F)).build());


    public static void init(){
        if (Gizmos.CONFIG.chorusGourdAndCultivatedShulkers)
            FabricDefaultAttributeRegistry.register(CULTIVATED_SHULKER_ENTITY, ShulkerEntity.createShulkerAttributes());

        if (Gizmos.CONFIG.rideableRavagers)
            FabricDefaultAttributeRegistry.register(TAMED_RAVAGER_ENTITY, RavagerEntity.createRavagerAttributes());
    }
}
