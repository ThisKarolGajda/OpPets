package me.opkarol.oppets.listeners;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.interfaces.IHolder;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.OpUtils;
import me.opkarol.oppets.utils.PetsUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayerListeners implements Listener {
    private final Database database = Database.getInstance();

    @EventHandler
    public void playerConsume(PlayerItemConsumeEvent event) {
        InventoryHolder holder = event.getPlayer().getOpenInventory().getTopInventory().getHolder();
        event.setCancelled(holder instanceof IHolder);
    }

    @EventHandler
    public void playerQuit(@NotNull PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        database.getDatabase().databaseUUIDSaver(playerUUID, true);
        OpUtils.killPetFromPlayerUUID(playerUUID);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Pet currentPet = database.getDatabase().getCurrentPet(uuid);
        PetsUtils.summonPet(currentPet, player, true);
        database.hideEntity(player);
    }

    @EventHandler
    public void serverLoad(@NotNull ServerLoadEvent event) {
        if (event.getType().equals(ServerLoadEvent.LoadType.RELOAD)) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                PetsUtils.summonPet(database.getDatabase().getCurrentPet(player.getUniqueId()), player, true);
            }
        }
    }
}
