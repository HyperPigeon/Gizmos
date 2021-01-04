package net.hyper_pigeon.Gizmos.config;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.hyper_pigeon.Gizmos.config.GizmosConfig;
import net.minecraft.client.gui.screen.Screen;

import java.util.Optional;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class GizmosModMenuIntegration implements ModMenuApi {
    @Override
    public String getModId() {
        return "gizmos";
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            Optional<Supplier<Screen>> optionalScreen = getConfigScreen(parent);
            if(optionalScreen.isPresent()) {
                return optionalScreen.get().get();
            } else {
                return parent;
            }
        };
    }

    public Optional<Supplier<Screen>> getConfigScreen(Screen screen) {
        return Optional.of(AutoConfig.getConfigScreen(GizmosConfig.class, screen));
    }
}
