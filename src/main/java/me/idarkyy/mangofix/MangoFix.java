package me.idarkyy.mangofix;

import me.idarkyy.mangofix.commands.MangoCommand;
import me.idarkyy.mangofix.listeners.MobListener;
import me.idarkyy.mangofix.listeners.PlayerListener;
import me.idarkyy.mangofix.tasks.AutoSaveTask;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.zencode.mango.Mango;
import org.zencode.mango.factions.Faction;

import java.io.File;
import java.io.IOException;

public class MangoFix extends JavaPlugin {
    private static MangoFix instance;

    private YamlConfiguration config;
    private File configFile;

    public static MangoFix getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        reloadConfig();

        PluginManager pluginManager = Bukkit.getPluginManager();
        BukkitScheduler scheduler = Bukkit.getScheduler();

        pluginManager.registerEvents(new PlayerListener(), this);
        pluginManager.registerEvents(new MobListener(), this);

        getCommand("mango").setExecutor(new MangoCommand());

        AutoSaveTask autoSaveTask = new AutoSaveTask();

        if (config.getBoolean("auto-save.async", true)) {
            scheduler.scheduleAsyncRepeatingTask(this, autoSaveTask, config.getLong("auto-save.interval", 300 * 20), config.getLong("auto-save.interval", 300 * 20));
        } else {
            scheduler.scheduleAsyncRepeatingTask(this, autoSaveTask, config.getLong("auto-save.interval", 300 * 20), config.getLong("auto-save.interval", 300 * 20));
        }
    }

    @Override
    public void onDisable() {
        for(Faction faction : Mango.getInstance().getFactionManager().getFactions()) {
            try {
                faction.save();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void reloadConfig() {
        if(configFile == null) {
            configFile = new File(getDataFolder(), "config.yml");
        }

        if(!configFile.exists()) {
            saveResource("config.yml", true);
        }



        config = YamlConfiguration.loadConfiguration(configFile);
    }

    @Override
    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (Exception e) {
            getLogger().severe("Could not save the config");
            e.printStackTrace();
        }
    }

    @Override
    public void saveDefaultConfig() {
        configFile.delete();
        saveResource("config.yml", true);
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }
}
