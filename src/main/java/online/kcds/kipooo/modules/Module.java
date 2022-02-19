package online.kcds.kipooo.modules;

import online.kcds.kipooo.Kipooo;
import org.bukkit.configuration.file.FileConfiguration;

public interface Module {

    FileConfiguration config = Kipooo.INSTANCE.config;

    String moduleName();

    default boolean isEnable() {
        return config.getBoolean(moduleName() + ".enable");
    }

    default String moduleUsage() {
        return config.getString(moduleName() + ".usage");
    }

    default String message() {
        return Kipooo.toColor(config.getString(moduleName() + ".message"));
    }

    default String description() {
        return Kipooo.toColor(config.getString(moduleName() + ".description"));
    }
}
