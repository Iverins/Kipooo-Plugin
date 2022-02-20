package online.kcds.kipooo.modules;

import online.kcds.kipooo.Kipooo;

import java.util.List;

public interface MultiModules extends EssentialsModules{

    default List<String> getCmds() {
        return Kipooo.INSTANCE.config.getStringList(
                "modules." + getModuleName() + ".commands"
        );
    }

    default String getCmd(String command) {
        return Kipooo.INSTANCE.config.getString(
                "modules." + getModuleName() + ".commands." + command
        );
    }

}
