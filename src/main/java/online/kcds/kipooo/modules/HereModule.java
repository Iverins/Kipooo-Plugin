package online.kcds.kipooo.modules;

import online.kcds.kipooo.Kipooo;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class HereModule implements CommonModules , Listener {

    @Override
    public String getModuleName() {
        modules.add(this);
        return "here";
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        if (event.getMessage().equalsIgnoreCase(getUsage()) && isEnable()) {
            Bukkit.broadcastMessage(Kipooo.replacePlayer(event.getPlayer() , getDefaultMessage()));
        }
    }
}
