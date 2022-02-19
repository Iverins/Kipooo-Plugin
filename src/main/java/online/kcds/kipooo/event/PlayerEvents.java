package online.kcds.kipooo.event;

import online.kcds.kipooo.Kipooo;
import online.kcds.kipooo.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitTask;

public class PlayerEvents implements Listener {

    public BukkitTask sleepTask = Bukkit.getScheduler().runTaskTimerAsynchronously(
            Kipooo.INSTANCE , () -> {
                Kipooo.sendActionBarOnline("今晚将在睡眠中度过");
            } , 0 , 1
    );

    @EventHandler
    public void join(PlayerJoinEvent event) {
        if (event.getPlayer().getPersistentDataContainer().get(
                PlayerData.pd,
                PlayerData.INSTANCE
        ).isScoreBoard()) {

        }
    }


}
