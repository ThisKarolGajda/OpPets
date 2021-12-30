package me.opkarol.oppets.listeners;

import dir.databases.Database;
import dir.pets.Pet;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.skills.SkillEnums;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

public class SkillsListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerBreakEvent(@NotNull BlockBreakEvent event) {
        if (!event.getBlock().getType().equals(Material.STONE)){
            return;
        }

        Player player = event.getPlayer();

        if (Database.getDatabase().getCurrentPet(player.getUniqueId()) == null) {
            return;
        }

        Pet pet = Database.getDatabase().getCurrentPet(player.getUniqueId());

        OpPets.getSkillDatabase().addPoint(SkillEnums.SkillsAdders.MINING, pet, player);
}
}
