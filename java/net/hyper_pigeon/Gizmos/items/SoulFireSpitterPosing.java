package net.hyper_pigeon.Gizmos.items;

import net.minecraft.client.model.ModelPart;
import net.minecraft.util.math.MathHelper;

public class SoulFireSpitterPosing {
    public static void hold(ModelPart holdingArm, ModelPart otherArm, ModelPart head, boolean rightArmed) {
        //ModelPart modelPart = rightArmed ? holdingArm : otherArm;
        ModelPart modelPart2 = rightArmed ? otherArm : holdingArm;

        //modelPart.yaw = (-0.3F) + head.yaw;
        modelPart2.yaw = (rightArmed ? 0.6F : -0.6F) + head.yaw;
        //modelPart.pitch = -1.5707964F + head.pitch + 0.1F;
        modelPart2.pitch = -1.9F + head.pitch;
    }

    public static void method_29350(ModelPart modelPart, ModelPart modelPart2, float f) {
        modelPart.roll += MathHelper.cos(f * 0.09F) * 0.05F + 0.05F;
        //modelPart2.roll -= MathHelper.cos(f * 0.09F) * 0.05F + 0.05F;
        modelPart.pitch += MathHelper.sin(f * 0.067F) * 0.05F;
        //modelPart2.pitch -= MathHelper.sin(f * 0.067F) * 0.05F;
    }
}
