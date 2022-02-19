package online.kcds.kipooo.player;

import online.kcds.kipooo.Kipooo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;

import static org.bukkit.persistence.PersistentDataType.*;

public class PlayerData implements PersistentDataType<PersistentDataContainer , PlayerData> {

    public static PlayerData INSTANCE = new PlayerData();

    public static final NamespacedKey pd = new NamespacedKey(
            Kipooo.INSTANCE , "playerData"
    );

    private Player player;
    private Location playerDeathLoc;
    private Location homeLoc;
    private File playerLangFile;
    private boolean scoreBoard;
    private boolean dnd;

    public PlayerData() {}

    public PlayerData(Player player , Location playerDeathLoc , Location homeLoc , File playerLangFile , boolean scoreBoard , boolean dnd) {
        this.player = player;
        this.playerDeathLoc = playerDeathLoc;
        this.homeLoc = homeLoc;
        this.playerLangFile = playerLangFile;
        this.scoreBoard = scoreBoard;
        this.dnd = dnd;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPlayer(String name) {
        this.player = Bukkit.getPlayer(name);
    }

    public void setPlayerDeathLoc(Location playerDeathLoc) {
        this.playerDeathLoc = playerDeathLoc;
    }

    public void setHomeLoc(Location homeLoc) {
        this.homeLoc = homeLoc;
    }

    public void setPlayerLangFile(File playerLangFile) {
        this.playerLangFile = playerLangFile;
    }

    public void setScoreBoard(boolean scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public void setDnd(boolean dnd) {
        this.dnd = dnd;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getPlayerDeathLoc() {
        return playerDeathLoc;
    }

    public Location getHomeLoc() {
        return homeLoc;
    }

    public File getPlayerLangFile() {
        return playerLangFile;
    }

    public boolean isScoreBoard() {
        return scoreBoard;
    }

    public boolean isDnd() {
        return dnd;
    }

    @Override
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @Override
    public Class<PlayerData> getComplexType() {
        return PlayerData.class;
    }

    @Override
    public PersistentDataContainer toPrimitive(PlayerData complex, PersistentDataAdapterContext context) {
        PersistentDataContainer container = context.newPersistentDataContainer();
        container.set(
                pd,
                PlayerData.INSTANCE,
                new PlayerData(
                        complex.getPlayer(), complex.getPlayerDeathLoc(), complex.getHomeLoc(), complex.getPlayerLangFile(), complex.isScoreBoard(), complex.isDnd()
                )
        );
        return container;
    }

    @Override
    public PlayerData fromPrimitive(PersistentDataContainer primitive, PersistentDataAdapterContext context) {
        PlayerData data;
        data = primitive.get(
                pd,
                PlayerData.INSTANCE
        );
        return data;
    }
}
