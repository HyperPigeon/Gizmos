package net.hyper_pigeon.Gizmos.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.hyper_pigeon.Gizmos.entities.EyeOfEnderArrowEntity;
import net.hyper_pigeon.Gizmos.registry.GizmoEntities;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Shadow
    private ClientWorld world;

    @Inject(method = "onEntitySpawn", at = @At(value = "RETURN"))
    private void spawnEyeOfEnderArrow(EntitySpawnS2CPacket packet, CallbackInfo info){

        double d = packet.getX();
        double e = packet.getY();
        double f = packet.getZ();
        EntityType<?> entityType = packet.getEntityTypeId();
        Entity entity = null;

        if (entityType == GizmoEntities.EYE_OF_ENDER_ARROW_ENTITY) {
            entity = new EyeOfEnderArrowEntity(d,e,f,world);
        }
        if (entity != null) {
            Entity owner = this.world.getEntityById(packet.getEntityData());
            if(owner != null) {
                ((PersistentProjectileEntity) entity).setOwner(owner);
            }

            int i = packet.getId();
            entity.updateTrackedPosition(d, e, f);
            entity.refreshPositionAfterTeleport(d, e, f);
            entity.pitch = (float)(packet.getPitch() * 360) / 256.0F;
            entity.yaw = (float)(packet.getYaw() * 360) / 256.0F;
            entity.setEntityId(i);
            entity.setUuid(packet.getUuid());
            this.world.addEntity(i, entity);
        }

    }
}
