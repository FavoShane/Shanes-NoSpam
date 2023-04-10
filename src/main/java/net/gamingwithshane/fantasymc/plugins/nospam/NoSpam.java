package net.gamingwithshane.fantasymc.plugins.nospam;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;


public final class NoSpam extends JavaPlugin implements Listener {
    public  FileConfiguration config = getConfig();


    public static NoSpam plugin;


    @Override
    public void onEnable() {
        plugin = this;


        config.addDefault("Prefix", "&d&lFantasy&f&lMC");
        config.addDefault("ChatCooldown", 60);
        config.addDefault("CommandCooldown", 60);
        config.addDefault("CommandCooldown-Message", "Je kan geen command 2 keer achter elkaar sturen!");
        config.addDefault("ChatCooldown-Message", "Je kan geen bericht 2 keer achter elkaar sturen!");

        config.options().copyDefaults(true);
        saveConfig();

        logger.info("Plugin is enabled");
        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        getServer().getPluginManager().registerEvents(new CommandEvent(), this);


        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        logger.info("Plugin is disabled");
        // Plugin shutdown logic
    }
}
