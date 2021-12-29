package net.hyper_pigeon.Gizmos.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

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
