package me.opkarol.oppets.listeners;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.pets.BabyEntityCreator;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.EntityUtils;
import net.minecraft.world.entity.EntityAgeable;
import net.minecraft.world.entity.animal.EntityAnimal;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerJoin implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoin(@NotNull PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (OpPets.getDatabase().getPetList(uuid) == null) {
            OpPets.getDatabase().setPets(uuid, new ArrayList<>());
            return;
        }
        Pet currentPet = OpPets.getDatabase().getCurrentPet(uuid);
        if (currentPet != null){
            OpPets.getCreator().spawnMiniPet(currentPet, player);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoin2(@NotNull PlayerJoinEvent event){
        OpPets.getDatabase().getActivePetMap().keySet().forEach(uuid -> {
            if (Bukkit.getPlayer(uuid) == null) return;
            if (OpPets.getDatabase().getCurrentPet(uuid) == null) return;
            if (!OpPets.getDatabase().getCurrentPet(uuid).isVisibleToOthers()){
                if (OpPets.getDatabase().getCurrentPet(uuid).getOwnUUID() == null) return;
                EntityUtils.hideEntityFromPlayer(event.getPlayer(), OpPets.getDatabase().getIdPet(OpPets.getDatabase().getCurrentPet(uuid).getOwnUUID()));
                Bukkit.broadcastMessage(OpPets.getDatabase().getIdPet(OpPets.getDatabase().getCurrentPet(uuid).getOwnUUID()) + " " + OpPets.getDatabase().getCurrentPet(uuid).getOwnUUID());
            }
        });
    }
}
