package online.kcds.kipooo.event;

import online.kcds.kipooo.Kipooo;
import online.kcds.kipooo.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class PlayerEvents implements Listener {

    private static List<Player> sleep = new ArrayList<>();

    private static BukkitTask sleepTask;

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
        event.setJoinMessage(Kipooo.toColor(Kipooo.replacePlayer(event.getPlayer() , Kipooo.INSTANCE.config.getString("langs.join-message"))));
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        event.setQuitMessage(Kipooo.toColor("&7- &f" + event.getPlayer().getName() + "断开连接."));
    }

    @EventHandler
    public void enterBed(PlayerBedEnterEvent event) {
        if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            if (!sleep.contains(event.getPlayer())) {
                sleep.add(event.getPlayer());
            }
            sleepTask = Bukkit.getScheduler().runTaskTimerAsynchronously(Kipooo.INSTANCE, () -> {
                Kipooo.sendActionBarOnline("今晚将在睡眠中度过");
            }, 0, 1);
            if (event.getPlayer().getWorld().getTime() > 12541 && event.getPlayer().getWorld().getTime() < 23458) {
                Bukkit.getScheduler().runTaskLater(Kipooo.INSTANCE, () -> {
                    event.getPlayer().getWorld().setTime(0);
                    event.getPlayer().getWorld().setStorm(false);
                    event.getPlayer().getWorld().setThundering(false);
                }, 20 * 2);
            }
            if (event.getPlayer().getWorld().getThunderDuration() > 0) {
                Bukkit.getScheduler().runTaskLater(Kipooo.INSTANCE , () -> {
                    event.getPlayer().getWorld().setStorm(false);
                    event.getPlayer().getWorld().setThundering(false);
                }, 20 * 2);
            }
            sleepTask.cancel();
        }
    }

    @EventHandler
    public void leaveBed(PlayerBedLeaveEvent event) {
        sleep.clear();
        if (!sleepTask.isCancelled()) {
            sleepTask.cancel();
        }
    }


}
