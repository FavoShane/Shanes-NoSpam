package net.gamingwithshane.fantasymc.plugins.nospam;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class CommandEvent implements Listener {



    static Logger logger = Logger.getLogger(CommandEvent.class.getName());
    private final CommandsCooldownManager cooldownManager = new CommandsCooldownManager();
    private final CommandManager commandmanager = new CommandManager();




    @EventHandler
    void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(player.getUniqueId());
        if(TimeUnit.MILLISECONDS.toSeconds(timeLeft) >= cooldownManager.DEFAULT_COOLDOWN){
            if (commandmanager.HANDLERS.containsKey(player)) {
                commandmanager.HANDLERS.remove(player);
            }
            commandmanager.HANDLERS.put(player, event.getMessage());
            cooldownManager.setCooldown(player.getUniqueId(), System.currentTimeMillis());
        }
        else if (commandmanager.HANDLERS.containsKey(player)) {
            if (commandmanager.HANDLERS.containsValue(event.getMessage())) {
                player.sendMessage(ChatColor.WHITE + "[ " + ChatColor.translateAlternateColorCodes('&', NoSpam.getPlugin(NoSpam.class).getConfig().getString("Prefix")) + ChatColor.WHITE + " ] " + ChatColor.translateAlternateColorCodes('&', NoSpam.getPlugin(NoSpam.class).getConfig().getString("CommandCooldown-Message")));
                event.setCancelled(true);
            } else {
                commandmanager.HANDLERS.remove(player);
                commandmanager.HANDLERS.put(player, event.getMessage());
                cooldownManager.setCooldown(player.getUniqueId(), System.currentTimeMillis());
            }

        }


    }
}
