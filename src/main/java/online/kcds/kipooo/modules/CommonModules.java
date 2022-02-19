package online.kcds.kipooo.modules;

import online.kcds.kipooo.Kipooo;

public interface CommonModules extends EssentialsModules{

    default String getDefaultMessage() {
        return Kipooo.toColor(Kipooo.INSTANCE.config.getString("modules." + getModuleName() + ".message"));
    }

    default String getUsage() {
        return Kipooo.INSTANCE.config.getString("modules." + getModuleName() + ".usage");
    }

}
