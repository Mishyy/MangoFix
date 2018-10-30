package me.idarkyy.mangofix.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Openable;
import org.zencode.mango.Mango;
import org.zencode.mango.factions.claims.Claim;
import org.zencode.mango.factions.types.PlayerFaction;

public class PlayerListener implements Listener {
    Mango mango = Mango.getInstance();

    @EventHandler
    @SuppressWarnings("all")
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        PlayerFaction faction = mango.getFactionManager().getFaction(event.getPlayer());
        Claim claim = mango.getClaimManager().getClaimAt(event.getBlockClicked().getLocation());

        if(claim == null) {
            return;
        }

        if(claim.getOwner() == faction) {
            return;
        }

        if(event.getPlayer().hasPermission(mango.getConfig().getString("ADMIN_NODE"))) {
            return;
        }

        event.setCancelled(true);
        event.getPlayer().sendMessage(mango.getLanguageFile().getString("FACTION_NO_INTERACT").replace("{faction}", claim.getOwner().getName()));
    }

    @EventHandler
    @SuppressWarnings("all")
    public void onBucketFill(PlayerBucketFillEvent event) {
        PlayerFaction faction = mango.getFactionManager().getFaction(event.getPlayer());
        Claim claim = mango.getClaimManager().getClaimAt(event.getBlockClicked().getLocation());

        if(claim == null) {
            return;
        }

        if(claim.getOwner() == faction) {
            return;
        }

        if(event.getPlayer().hasPermission(mango.getConfig().getString("ADMIN_NODE"))) {
            return;
        }

        event.setCancelled(true);
        event.getPlayer().sendMessage(mango.getLanguageFile().getString("FACTION_NO_INTERACT").replace("{faction}", claim.getOwner().getName()));
    }

    @EventHandler
    public void onOpen(PlayerInteractEvent event) {
        if(event.getClickedBlock() == null) {
            return;
        }

        if(!(event.getClickedBlock() instanceof Openable)) {
            return;
        }

        PlayerFaction faction = mango.getFactionManager().getFaction(event.getPlayer());
        Claim claim = mango.getClaimManager().getClaimAt(event.getClickedBlock().getLocation());

        if(claim == null) {
            return;
        }

        if(claim.getOwner() == faction) {
            return;
        }

        if(event.getPlayer().hasPermission(mango.getConfig().getString("ADMIN_NODE"))) {
            return;
        }

        event.setCancelled(true);
        event.getPlayer().sendMessage(mango.getLanguageFile().getString("FACTION_NO_INTERACT").replace("{faction}", claim.getOwner().getName()));
    }
}
