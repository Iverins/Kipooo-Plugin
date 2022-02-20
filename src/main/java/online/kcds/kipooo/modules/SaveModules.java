package online.kcds.kipooo.modules;

import online.kcds.kipooo.Kipooo;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;

public class SaveModules implements MultiModules{

    @Override
    public String getModuleName() {
        return "save";
    }

    public String toString(Location location) {
        return "X:" + location.getBlockX() + ", Y:" + location.getBlockY() + ", Z:" + location.getBlockZ() +
                ", ";
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        if (isEnable()) {
            if (event.getMessage().startsWith(getCmd("save"))) {
                Kipooo.INSTANCE.save.set(
                        event.getMessage().replaceAll(getCmd("save") , ""),
                        toString(event.getPlayer().getLocation())
                );
                try {
                    Kipooo.INSTANCE.save.save(
                            Kipooo.INSTANCE.saveFile
                    );
                } catch (IOException e) {
                    Kipooo.sendConsole("保存文件失败，请将报错发送给其他人以寻求帮助.");
                    e.printStackTrace();
                }
            }
            if (event.getMessage().startsWith(getCmd("del"))) {
                Kipooo.INSTANCE.save.set(
                        event.getMessage().replaceAll(getCmd("del") , ""),
                        null
                );
                try {
                    Kipooo.INSTANCE.save.save(
                            Kipooo.INSTANCE.saveFile
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
