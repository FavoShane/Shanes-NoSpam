package net.gamingwithshane.fantasymc.plugins.nospam;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ChatEvent implements Listener {





    static Logger logger = Logger.getLogger(ChatEvent.class.getName());
    private final CooldownManager cooldownManager = new CooldownManager();
    private final ChatManager chatmanager = new ChatManager();




    @EventHandler
    void onPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(player.getUniqueId());
        if(TimeUnit.MILLISECONDS.toSeconds(timeLeft) >= CooldownManager.DEFAULT_COOLDOWN){
            if (chatmanager.HANDLERS.containsKey(player)) {
                chatmanager.HANDLERS.remove(player);
            }
            chatmanager.HANDLERS.put(player, event.getMessage());
            cooldownManager.setCooldown(player.getUniqueId(), System.currentTimeMillis());
        }
        else if (chatmanager.HANDLERS.containsKey(player)) {
            if (chatmanager.HANDLERS.containsValue(event.getMessage())) {
                player.sendMessage(ChatColor.WHITE + "[ " + ChatColor.translateAlternateColorCodes('&', NoSpam.getPlugin(NoSpam.class).getConfig().getString("Prefix")) + ChatColor.WHITE + " ] " + ChatColor.translateAlternateColorCodes('&', NoSpam.getPlugin(NoSpam.class).getConfig().getString("ChatCooldown-Message")));
                event.setCancelled(true);
            } else {
                chatmanager.HANDLERS.remove(player);
                chatmanager.HANDLERS.put(player, event.getMessage());
                cooldownManager.setCooldown(player.getUniqueId(), System.currentTimeMillis());
            }

        }


    }
}
