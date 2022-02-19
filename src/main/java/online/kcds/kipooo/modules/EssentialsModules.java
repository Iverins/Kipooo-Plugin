package online.kcds.kipooo.modules;

import online.kcds.kipooo.Kipooo;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public interface EssentialsModules {

    static List<Listener> modules = new ArrayList<>();

    String getModuleName();

    /**
     * 是否启用该模块
     * @return 模块是否启用
     */
    default boolean isEnable() {
        return Kipooo.INSTANCE.config.getBoolean("modules." + getModuleName() + ".enable");
    }

    /**
     * 返回模块描述
     * @return 模块描述
     */
    default String description() {
        return Kipooo.toColor(Kipooo.INSTANCE.config.getString("modules." + getModuleName() + ".description"));
    }

}
