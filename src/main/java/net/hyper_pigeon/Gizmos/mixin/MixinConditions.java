package net.hyper_pigeon.Gizmos.mixin;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.hyper_pigeon.Gizmos.config.GizmosConfig;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinConditions implements IMixinConfigPlugin {
    static {
        // must register here, registering in Gizmos doesn't happen in time
        AutoConfig.register(GizmosConfig.class, GsonConfigSerializer::new);
    }

    public static final GizmosConfig CONFIG = AutoConfig.getConfigHolder(GizmosConfig.class).getConfig();

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() { return null; }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.endsWith("PillagerEntityMixin"))
            return CONFIG.pillagerFireworks;

        if (mixinClassName.endsWith("RavagerEntityMixin"))
            return CONFIG.rideableRavagers;

        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) { }

    @Override
    public List<String> getMixins() { return null; }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }
}
