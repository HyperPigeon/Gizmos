package net.hyper_pigeon.Gizmos.config;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;

import java.util.Optional;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class GizmosModMenuIntegration implements ModMenuApi {
//    @Override
//    public String getModId() {
//        return "gizmos";
//    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> AutoConfig.getConfigScreen(GizmosConfig.class, screen).get();
    }

//    public Optional<Supplier<Screen>> getConfigScreen(Screen screen) {
//        return Optional.of(AutoConfig.getConfigScreen(GizmosConfig.class, screen));
//    }
}
