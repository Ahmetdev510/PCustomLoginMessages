package me.ahmetdev510.Listeners;

import com.samjakob.spigui.SGMenu;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import me.ahmetdev510.PCustomLoginMessages;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import static me.ahmetdev510.PCustomLoginMessages.instance;
import static me.ahmetdev510.Util.MySQL.*;

public class CommandListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        PCustomLoginMessages.config.getList(PCustomLoginMessages.instance.getName()+".GUI.open_command").forEach(command -> {
            if (e.getMessage().equalsIgnoreCase("/"+command.toString())) {
                e.setCancelled(true);
                if(e.getPlayer().hasPermission(PCustomLoginMessages.config.getString(instance.getName()+".Permissions.open_command"))) {
                    MessagesGUI(e.getPlayer());
                } else {
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', PCustomLoginMessages.config.getString(instance.getName()+".Messages.noPermission")).replace("{prefix}", PCustomLoginMessages.config.getString(instance.getName()+".prefix").replace("&", "§")));
                }
            }
        });
    }
    public void MessagesGUI(Player player) {
        SGMenu menu = PCustomLoginMessages.getSpiGUI().create(PCustomLoginMessages.config.getString(PCustomLoginMessages.instance.getName() + ".GUI.menu_title").replaceAll("&", "§"), 5);

        ConfigurationSection sec = PCustomLoginMessages.config.getConfigurationSection(PCustomLoginMessages.instance.getName() + ".GUI.items");
        if (sec != null) {
            String activ = null;
            for (String key : sec.getKeys(false)) {
                String[] spl = getPlayerAccessMessages(player.getUniqueId().toString()).split(",");
                String act = "";
                for (String s1 : spl) {
                    if (s1.equalsIgnoreCase(key)) {
                        if (key.equalsIgnoreCase(getPlayerMessage(player.getUniqueId().toString()))) {
                            activ = "active";
                        } else {
                            activ = "passive";
                        }
                        act = act + key + ",";
                    } else {
                        for (String ac : act.split(",")) {
                            if (!ac.equalsIgnoreCase(key)) {
                                activ = "no_access";
                            }
                        }
                    }

                    String path = PCustomLoginMessages.instance.getName() + ".GUI.items." + key;
                    String name = PCustomLoginMessages.config.getString(path + "." + activ + ".display_name");
                    List lore = PCustomLoginMessages.config.getStringList(path + "." + activ + ".lore");
                    String material = PCustomLoginMessages.config.getString(path + "." + activ + ".material");
                    int slot = PCustomLoginMessages.config.getInt(path + ".slot");
                    int page = PCustomLoginMessages.config.getInt(path + ".page");
                    for (Object loreline : lore) {
                        lore.set(lore.indexOf(loreline), loreline.toString().replace("{prefix}", PCustomLoginMessages.config.getString(PCustomLoginMessages.instance.getName() + ".prefix")).replace("%player%", player.getName()));
                    }
                    String s = activ;
                    menu.setButton(page, slot, new SGButton(
                            new ItemBuilder(Material.getMaterial(material))
                                    .name(name)
                                    .lore(lore)
                                    .build()
                    ).withListener(inventoryClickEvent -> {
                        if (inventoryClickEvent.getWhoClicked() instanceof Player) {
                            Player player1 = (Player) inventoryClickEvent.getWhoClicked();
                            if (s.equalsIgnoreCase("no_access")) {
                                player1.sendMessage(ChatColor.translateAlternateColorCodes('&', PCustomLoginMessages.config.getString(PCustomLoginMessages.instance.getName() + ".Messages.noPermission")).replace("{prefix}", PCustomLoginMessages.config.getString(PCustomLoginMessages.instance.getName() + ".prefix").replace("&", "§")));
                            } else if (s.equalsIgnoreCase("passive")) {
                                setPlayerMessage(player1.getUniqueId().toString(), key);
                                player1.sendMessage(ChatColor.translateAlternateColorCodes('&', PCustomLoginMessages.config.getString(PCustomLoginMessages.instance.getName() + ".Messages.Active").replace("{message}", name)).replace("{prefix}", PCustomLoginMessages.config.getString(PCustomLoginMessages.instance.getName() + ".prefix").replace("&", "§")));
                                player1.closeInventory();
                            } else {
                                setPlayerMessage(player1.getUniqueId().toString(), "default");
                                player1.sendMessage(ChatColor.translateAlternateColorCodes('&', PCustomLoginMessages.config.getString(PCustomLoginMessages.instance.getName() + ".Messages.DisabledActive").replace("{message}", name)).replace("{prefix}", PCustomLoginMessages.config.getString(PCustomLoginMessages.instance.getName() + ".prefix").replace("&", "§")));
                                player1.closeInventory();
                            }

                        }
                    }));
                }


            }


            AtomicReference<BukkitTask> borderRunnable = new AtomicReference<>();

            menu.setOnPageChange(inventory -> {
                if (inventory.getCurrentPage() != 0) {
                    if (borderRunnable.get() != null) borderRunnable.get().cancel();
                    return;
                }
            });

            menu.setOnClose(inventory -> {
                if (borderRunnable.get() != null) borderRunnable.get().cancel();
            });

            menu.getOnPageChange().accept(menu);
            player.openInventory(menu.getInventory());
        }


    }

}
