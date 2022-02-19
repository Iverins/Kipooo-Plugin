package online.kcds.kipooo.modules;

import online.kcds.kipooo.Kipooo;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public interface EssentialsModules {

    static List<Listener> modules = new ArrayList<>();

    String getModuleName();

    /**
     * �Ƿ����ø�ģ��
     * @return ģ���Ƿ�����
     */
    default boolean isEnable() {
        return Kipooo.INSTANCE.config.getBoolean("modules." + getModuleName() + ".enable");
    }

    /**
     * ����ģ������
     * @return ģ������
     */
    default String description() {
        return Kipooo.toColor(Kipooo.INSTANCE.config.getString("modules." + getModuleName() + ".description"));
    }

}
