package me.ahmetdev510.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import static me.ahmetdev510.Util.MySQL.*;
import static me.ahmetdev510.PCustomLoginMessages.*;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if(getPlayerMessage(player.getUniqueId().toString()) == null || getPlayerMessage(player.getUniqueId().toString()).equalsIgnoreCase("default")) {
            e.setJoinMessage(config.getString(instance.getName()+".default_message").replace("&", "§").replace("%player%", player.getName()).replace("{prefix}", config.getString(instance.getName()+".prefix").replace("&", "§")));
        }else{
            e.setJoinMessage(config.getString(instance.getName()+".GUI.items."+getPlayerMessage(player.getUniqueId().toString())+".message").replace("&", "§").replace("%player%", player.getName()).replace("{prefix}", config.getString(instance.getName()+".prefix").replace("&", "§")));
        }
    }

}
