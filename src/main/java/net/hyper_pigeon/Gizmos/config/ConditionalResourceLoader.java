package net.hyper_pigeon.Gizmos.config;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.tags.JTag;
import net.hyper_pigeon.Gizmos.Gizmos;
import net.minecraft.util.Identifier;

import static net.devtech.arrp.json.tags.JTag.*;
import static net.hyper_pigeon.Gizmos.Gizmos.CONFIG;

public interface ConditionalResourceLoader {
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(Gizmos.MOD_ID + ":test");

    static void load() {
        if (CONFIG.seeker_arrows) {
            final JTag seekerArrowTag = tag().add(new Identifier(Gizmos.MOD_ID + ":eye_of_ender_arrow"));
            RESOURCE_PACK.addTag(new Identifier("minecraft:items/arrows"),
                    seekerArrowTag);
            RESOURCE_PACK.addTag(new Identifier("minecraft:entities/arrows"),
                    seekerArrowTag);
        }

        RRPCallback.EVENT.register(a -> a.add(RESOURCE_PACK));
    }
}
