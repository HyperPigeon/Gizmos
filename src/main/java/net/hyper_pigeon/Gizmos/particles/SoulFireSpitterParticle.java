package net.hyper_pigeon.Gizmos.particles;

import net.minecraft.client.particle.AbstractSlowingParticle;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.world.ClientWorld;

public class SoulFireSpitterParticle extends AbstractSlowingParticle {

    public SoulFireSpitterParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
    }



    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public void move(double dx, double dy, double dz) {
        this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
        this.repositionFromBoundingBox();
    }

    public float getSize(float tickDelta) {
        float f = ((float)this.age + tickDelta) / (float)this.maxAge;
        return this.scale * (1.0F - f * f * 0.5F);
    }

//    public int getColorMultiplier(float tint) {
//        float f = ((float)this.age + tint) / (float)this.maxAge;
//        f = MathHelper.clamp(f, 0.0F, 1.0F);
//        int i = super.getColorMultiplier(tint);
//        int j = i & 255;
//        int k = i >> 16 & 255;
//        j += (int)(f * 15.0F * 16.0F);
//        if (j > 240) {
//            j = 240;
//        }
//
//        return j | k << 16;
//    }

//    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
//        Vec3d vec3d = camera.getPos();
//        float f = (float)(MathHelper.lerp((double)tickDelta, this.prevPosX, this.x) - vec3d.getX());
//        float g = (float)(MathHelper.lerp((double)tickDelta, this.prevPosY, this.y) - vec3d.getY());
//        float h = (float)(MathHelper.lerp((double)tickDelta, this.prevPosZ, this.z) - vec3d.getZ());
//        Quaternion quaternion2;
//        if (this.angle == 0.0F) {
//            quaternion2 = camera.getRotation();
//        } else {
//            quaternion2 = new Quaternion(camera.getRotation());
//            float i = MathHelper.lerp(tickDelta, this.prevAngle, this.angle);
//            quaternion2.hamiltonProduct(Vector3f.POSITIVE_Z.getRadialQuaternion(i));
//        }
//
//        Vector3f vector3f = new Vector3f(-1.0F, -1.0F, 0.0F);
//        vector3f.rotate(quaternion2);
//        Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
//        float j = this.getSize(tickDelta);
//
//        for(int k = 0; k < 4; ++k) {
//            Vector3f vector3f2 = vector3fs[k];
//            vector3f2.rotate(quaternion2);
//            vector3f2.scale(j);
//            vector3f2.add(f, g, h);
//        }
//
//        float l = 50;
//        float m = 50;
//        float n = 50;
//        float o = 50;
//        int p = this.getColorMultiplier(tickDelta);
//        vertexConsumer.vertex(vector3fs[0].getX(), (double)vector3fs[0].getY(), (double)vector3fs[0].getZ()).texture(m, o).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(p).next();
//        vertexConsumer.vertex(vector3fs[1].getX(), (double)vector3fs[1].getY(), (double)vector3fs[1].getZ()).texture(m, n).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(p).next();
//        vertexConsumer.vertex((double)vector3fs[2].getX(), vector3fs[2].getY(), (double)vector3fs[2].getZ()).texture(l, n).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(p).next();
//        vertexConsumer.vertex((double)vector3fs[3].getX(), vector3fs[3].getY(), (double)vector3fs[3].getZ()).texture(l, o).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(p).next();
//    }

//    @Environment(EnvType.CLIENT)
//    public static class Factory implements ParticleFactory<DefaultParticleType> {
//        private final SpriteProvider spriteProvider;
//
//        public Factory(SpriteProvider spriteProvider) {
//            this.spriteProvider = spriteProvider;
//        }
//
//        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
//            SoulFireSpitterParticle soulFireSpitterParticle = new SoulFireSpitterParticle(clientWorld, d, e, f, g, h, i);
//            soulFireSpitterParticle.setSprite(this.spriteProvider);
//            return soulFireSpitterParticle;
//        }
//    }
}
