package me.ahmetdev510;


import com.samjakob.spigui.SpiGUI;
import me.ahmetdev510.Commands.PCLMCommand;
import me.ahmetdev510.Listeners.CommandListener;
import me.ahmetdev510.Listeners.PlayerJoinListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;
import static me.ahmetdev510.Util.MySQL.*;

public class PCustomLoginMessages extends JavaPlugin {
    public static PCustomLoginMessages instance;

    public static SpiGUI spiGUI;

    public static FileConfiguration config;
    public static FileConfiguration data;

    @Override
    public void onEnable() {
        instance = this;
        config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();

        spiGUI = new SpiGUI(this);
        getServer().getPluginManager().registerEvents(new CommandListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getCommand("pclm").setExecutor(new PCLMCommand());
        getLogger().info("Plugin enabled!");
        if(config.getBoolean(instance.getName()+".mysql.enabled")) {
            try {
                openConnection();
                if(connection!=null) {
                    PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `"+config.getString(instance.getName()+".mysql.database")+"`.`"+config.getString(instance.getName()+".mysql.table")+"` (`uuid` VARCHAR(36) NOT NULL, `message` VARCHAR(100) NOT NULL , `access_messages` VARCHAR(1000) NOT NULL , PRIMARY KEY (`uuid`)) ENGINE = InnoDB;");
                    statement.executeUpdate();
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onDisable() {
        closeConnection();
        getLogger().info("Plugin disabled!");
    }
    public static SpiGUI getSpiGUI() {
        return spiGUI;
    }



}