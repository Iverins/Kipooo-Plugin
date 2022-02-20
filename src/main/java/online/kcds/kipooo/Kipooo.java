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
            Kipooo.sendConsole("���ڴ�������Զ��������ļ�.");
            if (langDataFolder.mkdirs()) {
                Kipooo.sendConsole("&c����Զ��������ļ�����ʧ�ܣ��ѹر��Զ�������ϵͳ.");
                customLang = false;
            }
        }

        configFile = new File(this.getDataFolder() , "config.yml");
        saveFile = new File(this.getDataFolder() , "save.yml");
        if (!configFile.exists()) {
            Kipooo.sendConsole("���ڴ��������ļ�.");
            this.saveResource("config.yml" , true);
        }
        if (!saveFile.exists()) {
            Kipooo.sendConsole("���ڴ��������ļ�.");
            this.saveResource("save.yml" , true);
        }
    }

    @Override
    public void onEnable() {
        config = YamlConfiguration.loadConfiguration(configFile);
        Kipooo.sendConsole("�Ѽ�����������ļ�.");
        save = YamlConfiguration.loadConfiguration(saveFile);
        Kipooo.sendConsole("�Ѽ�����ϱ����ļ�.");
        Bukkit.getPluginManager().registerEvents(new PlayerEvents() , Kipooo.INSTANCE);
        for (Listener module : EssentialsModules.modules) {
            Bukkit.getPluginManager().registerEvents(module , Kipooo.INSTANCE);
        }
        Kipooo.sendConsole("�Ѽ��ؼ�����.");
    }

    @Override
    public void onDisable() {
        Kipooo.sendConsole("�����ж��.");
        Bukkit.clearRecipes();
        Kipooo.sendConsole("����պϳɱ�.");
    }

    /**
     * ������������ҷ���TITLE
     * @param title ������
     * @param subTitle �ӱ���
     * @param fadeIn ����
     * @param stay ����
     * @param fadeOut ����
     */
    public static void sendTitleOnline(String title , String subTitle , int fadeIn , int stay , int fadeOut) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.sendTitle(Kipooo.toColor(title) ,
                    Kipooo.toColor(subTitle) ,
                    fadeIn , stay , fadeOut);
        }
    }

    /**
     * ������δ��������ŵ�������ҷ���TITLE
     * @param title ������
     * @param subTitle �ӱ���
     * @param fadeIn ����
     * @param stay ����
     * @param fadeOut ����
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
     * ��������ҷ���ACTIONBAR
     * @param text ����
     */
    public static void sendActionBarOnline(String text) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.spigot().sendMessage(ChatMessageType.ACTION_BAR ,
                    new TextComponent(Kipooo.toColor(text)));
        }
    }

    /**
     * �滻��ұ���
     * @param player ���
     * @param text �ı�
     * @return �滻����ı�
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
     * �Ժ�̨����ݷ�����Ϣ
     * @param text ��Ϣ
     */
    public static void sendConsole(String text) {
        Bukkit.getConsoleSender().sendMessage(Kipooo.toColor("&d&lKipooo &7| &f" + text));
    }

    /**
     * ת��Ϊ������ɫ���ı�
     * @param text �ı�
     * @return ������ɫ���ı�
     */
    public static String toColor(String text) {
        return ChatColor.translateAlternateColorCodes('&' , text);
    }
}
