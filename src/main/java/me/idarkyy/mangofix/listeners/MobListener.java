package me.idarkyy.mangofix.listeners;

import me.idarkyy.mangofix.MangoFix;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTeleportEvent;

public class MobListener implements Listener {
    @EventHandler
    public void onEndermanTeleport(EntityTeleportEvent event) {
        if(event.getEntityType() == EntityType.ENDERMAN) {
            if (MangoFix.getInstance().getConfig().getBoolean("mobs.disable-enderman-teleport", true)) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(MangoFix.getInstance(), () -> event.getEntity().teleport(event.getFrom()), 1);
            }
        }
    }
}
