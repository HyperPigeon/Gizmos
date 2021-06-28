package net.hyper_pigeon.Gizmos.mixin;

import net.hyper_pigeon.Gizmos.items.SoulFireSpitterPosing;
import net.hyper_pigeon.Gizmos.registry.GizmoItems;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin {

    @Shadow
    public ModelPart head;
    @Shadow
    public ModelPart rightArm;

    @Shadow
    public ModelPart leftArm;

    @Shadow
    public ModelPart rightLeg;

    @Shadow
    public ModelPart leftLeg;


    /*Changes player model so SoulFireSpitter and other Gizmos are held in a certain way*/
    @Inject(at = @At("HEAD"), method = "positionLeftArm")
    private void GizmoPoses(LivingEntity livingEntity, CallbackInfo callbackInfo) {

        ItemStack mainhandStack = livingEntity.getMainHandStack();

        if(mainhandStack.getItem().equals(GizmoItems.SOUL_FIRE_SPITTER)){
            SoulFireSpitterPosing.hold(this.rightArm, this.leftArm, this.head, false);
        }
    }


    /*sets angle for hands when holding gizmos*/
    @Inject(at = @At("HEAD"), method = "setAngles")
    private void setGizmoAngles(LivingEntity livingEntity, float f, float g, float h, float i, float j,
                                CallbackInfo callbackInfo){
        ItemStack mainhandStack = livingEntity.getMainHandStack();

        if(mainhandStack.getItem().equals(GizmoItems.SOUL_FIRE_SPITTER)){
            SoulFireSpitterPosing.method_29350(this.rightArm, this.leftArm, h);
        }
    }

}
