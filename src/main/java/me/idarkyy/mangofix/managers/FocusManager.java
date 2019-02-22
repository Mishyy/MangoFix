package me.idarkyy.mangofix.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class FocusManager {
    private JavaPlugin plugin;

    HashMap<Player, Player> focus = new HashMap<>();

    public FocusManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void focus(Player player, Player target) {
        focus.put(player, target);
    }

    public void unfocus(Player player) {
        if(!focus.containsKey(player)) {
            return;
        }

        Player target = focus.get(player);
    }
}
