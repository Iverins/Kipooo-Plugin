package online.kcds.kipooo.event;

import online.kcds.kipooo.Kipooo;
import online.kcds.kipooo.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

public class PlayerEvents implements Listener {

    @EventHandler
    public void kick(AsyncPlayerPreLoginEvent event) {
        if (event.getLoginResult().equals(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST)) {
            event.setKickMessage(Kipooo.toColor(Kipooo.INSTANCE.config.getString("langs.kick-whitelist")));
        }
        if (event.getLoginResult().equals(AsyncPlayerPreLoginEvent.Result.KICK_BANNED)) {
            event.setKickMessage(Kipooo.toColor(Kipooo.INSTANCE.config.getString("langs.kick-banned")));
            Bukkit.broadcastMessage(Kipooo.toColor(Kipooo.INSTANCE.config.getString("banned-login").replaceAll("%player%" , event.getName())));
        }
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        if (event.getPlayer().getPersistentDataContainer().get(
                PlayerData.pd ,
                PlayerData.INSTANCE
        ).getPlayer() == null) {
            event.getPlayer().getPersistentDataContainer().set(
                    PlayerData.pd,
                    PlayerData.INSTANCE,
                    new PlayerData(
                            event.getPlayer(),
                            event.getPlayer().getLocation().getWorld().getSpawnLocation(),
                            event.getPlayer().getLocation().getWorld().getSpawnLocation(),
                            true , false
                    )
            );
        }
        if (event.getPlayer().getPersistentDataContainer().get(
                PlayerData.pd,
                PlayerData.INSTANCE
        ).isScoreBoard()) {
            // TODO 记分板显示
        }
        event.setJoinMessage(Kipooo.toColor("&7+ &f" + event.getPlayer().getName() + "进入KIVO COMMUNITY SERVER."));
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        event.setQuitMessage(Kipooo.toColor("&7- &f" + event.getPlayer().getName() + "断开连接."));
    }

    @EventHandler
    public void enterBed(PlayerBedEnterEvent event) {

    }


}
