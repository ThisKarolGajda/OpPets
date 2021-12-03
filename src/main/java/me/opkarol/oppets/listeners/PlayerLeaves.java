package me.opkarol.oppets.listeners;

import me.opkarol.oppets.OpPets;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerLeaves implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerLeaves(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        OpPets.getUtils().killPetFromPlayerUUID(playerUUID);
    }
}
