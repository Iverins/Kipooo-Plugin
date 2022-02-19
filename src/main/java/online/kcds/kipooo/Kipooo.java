package online.kcds.kipooo;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Kipooo extends JavaPlugin {

    public static Kipooo INSTANCE;
    {
        INSTANCE = this;
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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
     * 以免打扰形式给所有在线玩家发送TITLE
     * @param title 主标题
     * @param subTitle 子标题
     * @param fadeIn 淡入
     * @param stay 保留
     * @param fadeOut 淡出
     */
    public static void sendTitleOnlineAsDND(String title , String subTitle , int fadeIn , int stay , int fadeOut) {

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
