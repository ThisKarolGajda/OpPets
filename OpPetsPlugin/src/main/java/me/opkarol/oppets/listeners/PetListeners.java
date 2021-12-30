package me.opkarol.oppets.listeners;

import dir.pets.Pet;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.events.PetLevelupEvent;
import me.opkarol.oppets.particles.ParticlesManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class PetListeners implements Listener {

    @EventHandler
    public void petLevelup(@NotNull PetLevelupEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Pet pet = event.getPet();

        pet.setLevel(pet.getLevel() + 1);
        pet.setPetExperience(0);
        OpPets.getSkillDatabase().changeActionValueToPet(pet.getOwnUUID(), 0);

        ParticlesManager.spawnLevelUpPetEffect(event.getPlayer(), OpPets.getUtils().getEntityByUniqueId(event.getPet().getOwnUUID()));
    }
}
