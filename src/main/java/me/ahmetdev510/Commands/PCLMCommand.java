package me.ahmetdev510.Commands;

import me.ahmetdev510.PCustomLoginMessages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import static me.ahmetdev510.PCustomLoginMessages.config;
import static me.ahmetdev510.PCustomLoginMessages.instance;
import static me.ahmetdev510.Util.MySQL.*;

public class PCLMCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pclm")) {
            if (args.length == 0) {
                for (String message : config.getStringList(instance.getName() + ".Messages.Help")) {
                    sender.sendMessage(message.replace("&","§").replace("{prefix}", config.getString(instance.getName() + ".prefix").replace("&", "§")));
                }
            } else if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (sender.hasPermission(config.getString(instance.getName()+".Permissions.reload"))) {
                        instance.reloadConfig();
                        config = instance.getConfig();
                        sender.sendMessage(config.getString(instance.getName()+".Messages.reload").replace("&", "§").replace("{prefix}", config.getString(instance.getName()+".prefix").replace("&", "§")));
                    } else {
                        sender.sendMessage(config.getString(instance.getName()+".Messages.noPermission").replace("&", "§").replace("{prefix}", config.getString(instance.getName()+".prefix").replace("&", "§")));
                    }
                }else if (args[0].equalsIgnoreCase("loginmessage")) {
                    if(args.length == 1){
                        sender.sendMessage("§c/pclm loginmessage give/take <player> <message>");
                    }else if(args.length >= 2){
                        if(args[1].equalsIgnoreCase("give")) {
                            if (args.length == 2) {
                                sender.sendMessage("§c/pclm loginmessage give <player> <message>");
                            } else if (args.length >= 3) {
                                if (sender.hasPermission(config.getString(instance.getName()+".Permissions.give"))) {
                                    if (instance.getServer().getPlayer(args[2]) != null) {
                                        if(args.length == 3){
                                            sender.sendMessage("§c/pclm loginmessage give <player> <message>");
                                        }else if(args.length >= 4){
                                            String message = args[3];
                                            ConfigurationSection sec = config.getConfigurationSection(instance.getName()+".GUI.items");
                                            if(sec != null) {
                                                for (String key : sec.getKeys(false)) {
                                                    if(key.equalsIgnoreCase(message)){
                                                        String[] spl = getPlayerAccessMessages(instance.getServer().getPlayer(args[2]).getUniqueId().toString()).split(",");
                                                        String newMessage = "";
                                                        for (String s1 : spl) {
                                                            if(!s1.equalsIgnoreCase(message)){
                                                                newMessage = newMessage + s1 + ",";
                                                            }
                                                        }
                                                        newMessage = newMessage + message;
                                                        setPlayerAccessMessages(instance.getServer().getPlayer(args[2]).getUniqueId().toString(), newMessage);
                                                        sender.sendMessage(config.getString(instance.getName()+".Messages.Gived").replace("&", "§").replace("{prefix}", config.getString(instance.getName()+".prefix").replace("&","§")).replace("{player}", args[2]).replace("{message}", message));
                                                        return true;
                                                    }
                                                }
                                                sender.sendMessage(config.getString(instance.getName()+".Messages.NotGived").replace("&", "§").replace("{prefix}", config.getString(instance.getName()+".prefix").replace("&","§")).replace("{player}", args[2]).replace("{message}", message));
                                            }
                                        }
                                    } else {
                                        sender.sendMessage(config.getString(instance.getName() + ".Messages.PlayerNotFound").replace("&", "§").replace("{prefix}", config.getString(instance.getName() + ".prefix").replace("&", "§")));
                                    }
                                } else {
                                    sender.sendMessage(config.getString(instance.getName() + ".Messages.noPermission").replace("&", "§").replace("{prefix}", config.getString(instance.getName() + ".prefix").replace("&", "§")));
                                }
                            }
                        }else if(args[1].equalsIgnoreCase("take")) {
                            if (args.length == 2) {
                                sender.sendMessage("§c/pclm loginmessage take <player> <message>");
                            } else if (args.length >= 3) {
                                if (sender.hasPermission(config.getString(instance.getName()+".Permissions.take"))) {
                                    if (instance.getServer().getPlayer(args[2]) != null) {
                                        if(args.length == 3){
                                            sender.sendMessage("§c/pclm loginmessage take <player> <message>");
                                        }else if(args.length >= 4){
                                            String message = args[3];
                                            ConfigurationSection sec = config.getConfigurationSection(instance.getName()+".GUI.items");
                                            if(sec != null) {
                                                for (String key : sec.getKeys(false)) {
                                                    if(key.equalsIgnoreCase(message)){
                                                        String[] spl = getPlayerAccessMessages(instance.getServer().getPlayer(args[2]).getUniqueId().toString()).split(",");
                                                        String newMessage = "";
                                                        for (String s1 : spl) {
                                                            if(!s1.equalsIgnoreCase(message)){
                                                                newMessage = newMessage + s1 + ",";
                                                            }
                                                        }
                                                        newMessage = newMessage.substring(0, newMessage.length() - 1);
                                                        setPlayerAccessMessages(instance.getServer().getPlayer(args[2]).getUniqueId().toString(), newMessage);
                                                        setPlayerMessage(instance.getServer().getPlayer(args[2]).getUniqueId().toString(), "default");
                                                        sender.sendMessage(config.getString(instance.getName()+".Messages.Taked").replace("&", "§").replace("{prefix}", config.getString(instance.getName()+".prefix").replace("&","§")).replace("{player}", args[2]).replace("{message}", message));
                                                        return true;
                                                    }
                                                }
                                                sender.sendMessage(config.getString(instance.getName()+".Messages.NotTaked").replace("&", "§").replace("{prefix}", config.getString(instance.getName()+".prefix").replace("&","§")).replace("{player}", args[2]).replace("{message}", message));
                                            }
                                        }
                                    } else {
                                        sender.sendMessage(config.getString(instance.getName() + ".Messages.PlayerNotFound").replace("&", "§").replace("{prefix}", config.getString(instance.getName() + ".prefix").replace("&", "§")));
                                    }
                                } else {
                                    sender.sendMessage(config.getString(instance.getName() + ".Messages.noPermission").replace("&", "§").replace("{prefix}", config.getString(instance.getName() + ".prefix").replace("&", "§")));
                                }
                            }
                        }
                    }
                }else if(args[0].equalsIgnoreCase("reset")) {
                    if (args.length == 1) {
                        sender.sendMessage("§c/pclm reset <player>");
                    } else if (args.length >= 2) {
                        if (sender.hasPermission(config.getString(instance.getName()+".Permissions.reset"))) {
                            if (instance.getServer().getPlayer(args[1]) != null) {
                                deletePlayer(instance.getServer().getPlayer(args[1]).getUniqueId());
                                sender.sendMessage(config.getString(instance.getName()+".Messages.Reset").replace("&", "§").replace("{prefix}", config.getString(instance.getName()+".prefix").replace("&","§")).replace("{player}", args[1]));
                            } else {
                                sender.sendMessage(config.getString(instance.getName() + ".Messages.PlayerNotFound").replace("&", "§").replace("{prefix}", config.getString(instance.getName() + ".prefix").replace("&", "§")));
                            }
                        } else {
                            sender.sendMessage(config.getString(instance.getName() + ".Messages.noPermission").replace("&", "§").replace("{prefix}", config.getString(instance.getName() + ".prefix").replace("&", "§")));
                        }
                    }
                }
            }
        }
        return true;
    }
}
