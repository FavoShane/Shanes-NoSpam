package net.gamingwithshane.fantasymc.plugins.nospam;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CommandsCooldownManager {

    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public static final long DEFAULT_COOLDOWN = NoSpam.getPlugin(NoSpam.class).getConfig().getLong("CommandCooldown");

    public void setCooldown(UUID player, long time){
        if(time < 1) {
            cooldowns.remove(player);
        } else {
            cooldowns.put(player, time);
        }
    }

    public long getCooldown(UUID player){
        return cooldowns.getOrDefault(player, Long.valueOf(0));
    }
}
