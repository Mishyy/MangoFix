package me.idarkyy.mangofix.commands;

import me.idarkyy.mangofix.MangoFix;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zencode.mango.Mango;
import org.zencode.mango.commands.faction.ChatCommand;
import org.zencode.mango.config.LanguageFile;

public class TellCoordsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Player only");
            return false;
        }

        Player player = (Player) sender;
        LanguageFile lf = Mango.getInstance().getLanguageFile();

        if (Mango.getInstance().getFactionManager().getFaction(player) == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', lf.getString("FACTION_NOT_IN")));
            return false;
        }

        Mango.getInstance().getFactionManager().getFaction((Player) sender).sendMessage(lf.getString("FACTION_CHAT_FORMAT.FACTION")
                .replace("{player}", player.getName())
                .replace("{message}", MangoFix.getInstance().getConfig().getString("tellcoords.format")
                        .replace("%x%", String.valueOf(player.getLocation().getBlockX()))
                        .replace("%y%", String.valueOf(player.getLocation().getBlockY()))
                        .replace("%z%", String.valueOf(player.getLocation().getBlockZ()))
                        .replace("%world%", player.getLocation().getWorld().getName()))
                .replace("{faction}", Mango.getInstance().getFactionManager().getFaction(player).getName()));

        return true;
    }
}
