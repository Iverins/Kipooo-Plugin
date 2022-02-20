package online.kcds.kipooo;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import online.kcds.kipooo.event.PlayerEvents;
import online.kcds.kipooo.modules.EssentialsModules;
import online.kcds.kipooo.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Kipooo extends JavaPlugin {

    public static Kipooo INSTANCE;
    {
        INSTANCE = this;
    }

    public static boolean customLang = true;

    public File langDataFolder;
    public File configFile;
    public File saveFile;

    public FileConfiguration config;
    public FileConfiguration save;

    @Override
    public void onLoad() {
        langDataFolder = new File(this.getDataFolder() , "lang-data");
        if (!langDataFolder.exists()) {
            Kipooo.sendConsole("正在创建玩家自定义语言文件.");
            if (langDataFolder.mkdirs()) {
                Kipooo.sendConsole("&c玩家自定义语言文件创建失败，已关闭自定义语言系统.");
                customLang = false;
            }
        }

        configFile = new File(this.getDataFolder() , "config.yml");
        saveFile = new File(this.getDataFolder() , "save.yml");
        if (!configFile.exists()) {
            Kipooo.sendConsole("正在创建配置文件.");
            this.saveResource("config.yml" , true);
        }
        if (!saveFile.exists()) {
            Kipooo.sendConsole("正在创建保存文件.");
            this.saveResource("save.yml" , true);
        }
    }

    @Override
    public void onEnable() {
        config = YamlConfiguration.loadConfiguration(configFile);
        Kipooo.sendConsole("已加载完毕配置文件.");
        save = YamlConfiguration.loadConfiguration(saveFile);
        Kipooo.sendConsole("已加载完毕保存文件.");
        Bukkit.getPluginManager().registerEvents(new PlayerEvents() , Kipooo.INSTANCE);
        for (Listener module : EssentialsModules.modules) {
            Bukkit.getPluginManager().registerEvents(module , Kipooo.INSTANCE);
        }
        Kipooo.sendConsole("已加载监听器.");
    }

    @Override
    public void onDisable() {
        Kipooo.sendConsole("插件已卸载.");
        Bukkit.clearRecipes();
        Kipooo.sendConsole("已清空合成表.");
    }

    /**
     * 给所有在线玩家发送TITLE
     * @param title 主标题
     * @param subTitle 子标题
     * @param fadeIn 淡入
     * @param stay 保留
     * @param fadeOut 淡出
     */
    public static void sendTitleOnline(String title , String subTitle , int fadeIn , int stay , int fadeOut) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.sendTitle(Kipooo.toColor(title) ,
                    Kipooo.toColor(subTitle) ,
                    fadeIn , stay , fadeOut);
        }
    }

    /**
     * 给所有未开启免打扰的在线玩家发送TITLE
     * @param title 主标题
     * @param subTitle 子标题
     * @param fadeIn 淡入
     * @param stay 保留
     * @param fadeOut 淡出
     */
    public static void sendTitleOnlineAsDND(String title , String subTitle , int fadeIn , int stay , int fadeOut) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!online.getPersistentDataContainer().get(
                    PlayerData.pd ,
                    PlayerData.INSTANCE
            ).isDnd()) {
                online.sendTitle(Kipooo.toColor(title) ,
                        Kipooo.toColor(subTitle) ,
                        fadeIn , stay , fadeOut);
            }
        }
    }

    /**
     * 给所有玩家发送ACTIONBAR
     * @param text 内容
     */
    public static void sendActionBarOnline(String text) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.spigot().sendMessage(ChatMessageType.ACTION_BAR ,
                    new TextComponent(Kipooo.toColor(text)));
        }
    }

    /**
     * 替换玩家变量
     * @param player 玩家
     * @param text 文本
     * @return 替换后的文本
     */
    public static String replacePlayer(Player player , String text) {
        String worldName = Kipooo.INSTANCE.config.getStringList("options.world-alias").contains(
                player.getLocation().getWorld().getName()
        ) ? player.getLocation().getWorld().getName() : Kipooo.toColor(
                Kipooo.INSTANCE.config.getString("options.world-alias." + player.getLocation().getWorld().getName())
        );
        return text.replaceAll("%player%" , player.getName())
                .replaceAll("%player_X%" , String.valueOf(player.getLocation().getBlockX()))
                .replaceAll("%player_Y" , String.valueOf(player.getLocation().getBlockY()))
                .replaceAll("%player_Z" , String.valueOf(player.getLocation().getBlockZ()))
                .replaceAll("%player_World" , worldName);
    }

    /**
     * 以后台的身份发送消息
     * @param text 消息
     */
    public static void sendConsole(String text) {
        Bukkit.getConsoleSender().sendMessage(Kipooo.toColor("&d&lKipooo &7| &f" + text));
    }

    /**
     * 转换为带有颜色的文本
     * @param text 文本
     * @return 带有颜色的文本
     */
    public static String toColor(String text) {
        return ChatColor.translateAlternateColorCodes('&' , text);
    }
}
