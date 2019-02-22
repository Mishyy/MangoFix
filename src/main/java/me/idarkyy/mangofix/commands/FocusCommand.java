package me.idarkyy.mangofix.commands;

import me.idarkyy.mangofix.MangoFix;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FocusCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This is for players only.");
            return true;
        }

        if(args.length == 0) {
            sender.sendMessage(c(MangoFix.getInstance().getConfig().getString("focus.focus-usage")));
            return true;
        }

        Player player = Bukkit.getPlayer(args[0]);

        if(player == null) {
            sender.sendMessage(c(MangoFix.getInstance().getConfig().getString("focus.player-not-found").replace("%player%", args[0])));
            return true;
        }



        return true;
    }

    private String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
