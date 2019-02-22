package me.idarkyy.mangofix.commands;

import me.idarkyy.mangofix.MangoFix;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.zencode.mango.Mango;
import org.zencode.mango.config.ConfigFile;
import org.zencode.mango.config.LanguageFile;
import org.zencode.mango.factions.Faction;
import org.zencode.mango.factions.types.PlayerFaction;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class MangoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("mangofix.admin")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
            return true;
        }

        if (args.length == 0) {
            sendAll(sender,
                    "&8&m-----------------------------------------------------",
                    "&3&lMango &7(with MangoFix v" + MangoFix.getInstance().getDescription().getVersion() + ")",
                    "",
                    " &7/&bmango &7reload &bReloads Mango and MangoFix",
                    " &7/&bmango &7forcesave &bForcefully saves all factions",
                    "",
                    " &7/&bmango &7setleader &7<&bplayer&7> <&bfaction&7> &7Forcefully set the leader",
                    "&8&m-----------------------------------------------------"
            );

            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            saveConfig();
            saveLang();
            MangoFix.getInstance().saveConfig();

            sender.sendMessage(ChatColor.GREEN + "Reloaded all configuration files!");

        } else if (args[0].equalsIgnoreCase("forcesave")) {

            for (Faction faction : Mango.getInstance().getFactionManager().getFactions()) {
                try {
                    faction.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            sender.sendMessage(ChatColor.GREEN + "Saved all factions!");
        } else if (args[0].equalsIgnoreCase("setleader")) {
            if (args.length < 3) {
                sendAll(sender, "&7Correct usage: &7/&bmango &7setleader &7<&bplayer&7> <&bfaction&7>");
                return false;
            }

            Player player = Bukkit.getPlayer(args[1]);
            Faction faction = player != null ? Mango.getInstance().getFactionManager().getFactionByName(args[2]) : null;

            if (player == null) {
                sender.sendMessage(ChatColor.RED + args[1] + " is offline.");
                return false;
            }

            if (faction == null) {
                sender.sendMessage(ChatColor.RED + "Faction \"" + args[2] + "\" does not exist.");
                return false;
            }

            if (!(faction instanceof PlayerFaction)) {
                sender.sendMessage(ChatColor.RED + "That is not a player faction");
                return false;
            }

            if (!((PlayerFaction) faction).getMembers().contains(player.getUniqueId())) {
                sender.sendMessage(ChatColor.RED + player.getName() + " is must be in the specified faction.");
                return false;
            }

            ((PlayerFaction) faction).setLeader(player.getUniqueId());
            sender.sendMessage(ChatColor.GREEN + "Updated the faction leader of faction &7" + faction.getName() + " &ato &7" + player.getName());
        }

        return true;
    }

    private void sendAll(CommandSender sender, String... messages) {
        for (String s : messages) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
        }
    }

    private boolean saveConfig() {
        try {
            Field configFileField = ConfigFile.class.getDeclaredField("file");
            Field configYamlField = ConfigFile.class.getDeclaredField("configuration");

            File file;
            YamlConfiguration config;

            configFileField.setAccessible(true);
            configYamlField.setAccessible(true);

            file = (File) configFileField.get(Mango.getInstance().getConfigFile());
            config = (YamlConfiguration) configYamlField.get(Mango.getInstance().getConfigFile());

            config.save(file);

            configFileField.setAccessible(false);
            configYamlField.setAccessible(false);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean saveLang() {
        try {
            Field configFileField = LanguageFile.class.getDeclaredField("file");
            Field configYamlField = LanguageFile.class.getDeclaredField("configuration");

            File file;
            YamlConfiguration config;

            configFileField.setAccessible(true);
            configYamlField.setAccessible(true);

            file = (File) configFileField.get(Mango.getInstance().getLanguageFile());
            config = (YamlConfiguration) configYamlField.get(Mango.getInstance().getLanguageFile());

            config.save(file);

            configFileField.setAccessible(false);
            configYamlField.setAccessible(false);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
