package me.opkarol.oppets.listeners;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.files.MessagesHolder;
import me.opkarol.oppets.api.map.OpMap;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.events.PetLevelupEvent;
import me.opkarol.oppets.events.PrestigeChangeEvent;
import me.opkarol.oppets.inventory.OpInventories;
import me.opkarol.oppets.particles.ParticlesManager;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.prestiges.PrestigeManager;
import me.opkarol.oppets.skills.Skill;
import me.opkarol.oppets.utils.external.FormatUtils;
import me.opkarol.oppets.utils.external.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static me.opkarol.oppets.utils.Utils.equalsComponentAndPetName;

public class PetListeners implements Listener {
    private final Database database = Database.getInstance();
    private final MessagesHolder messages = MessagesHolder.getInstance();
    private final ParticlesManager particlesManager = new ParticlesManager();

    @EventHandler
    public void petLevelUp(@NotNull PetLevelupEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Pet pet = event.getPet();
        pet.setLevel(pet.getLevel() + 1);
        pet.setPetExperience(0);
        Player player = event.getPlayer();
        if (pet.getPetName() == null) {
            return;
        }
        player.sendMessage(messages.getString("Messages.petLevelUpMessage").replace("%newline%", "\n").replace("%pet_name%", FormatUtils.formatMessage(pet.getPetName())).replace("%current_level%", String.valueOf(pet.getLevel())).replace("%max_level%", String.valueOf(MathUtils.getPrestigeLevel(pet))).replace("%experience_level%", String.valueOf(MathUtils.getPetLevelExperience(pet))));
        if (pet.settings.areParticlesEnabled()) {
            particlesManager.spawnLevelUpPetEffect(player, database.getOpPets().getUtils().getEntityByUniqueId(event.getPet().petUUID.getOwnUUID()));
        }

        database.getOpPets().getSkillDatabase().getSkills().stream()
                .map(Skill::getAbilities)
                .map(OpMap::getValues)
                .forEach(lists -> lists.forEach(list -> list.forEach(iAbility -> iAbility.use(player))));
    }

    @EventHandler
    public void prestigeChange(@NotNull PrestigeChangeEvent event) {
        PrestigeManager pm = database.getOpPets().getPrestigeManager();
        if (event.isCancelled() || pm.format == null) {
            return;
        }
        Pet pet = event.getPet();
        pet.setLevel(0);
        pet.setPetExperience(0);
        pet.setPrestige(pm.getFormatForNumber(pm.getPrestigeLevel(pet.getPrestige()) + 1));
        Player player = event.getPlayer();
        player.sendMessage(messages.getString("Messages.prestigeUpMessage").replace("%newline%", "\n").replace("%pet_name%", FormatUtils.formatMessage(pet.getPetName())).replace("%current_prestige%", pm.getFilledPrestige(pet.getPrestige())).replace("%max_level%", String.valueOf(MathUtils.getPrestigeLevel(pet))));
        database.getOpPets().getUtils().respawnPet(pet, player);
        if (pet.settings.areParticlesEnabled()) {
            particlesManager.prestigeChangeEffect(player, database.getOpPets().getUtils().getEntityByUniqueId(event.getPet().petUUID.getOwnUUID()));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerInteract(@NotNull PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking()) {
            return;
        }
        Pet currentPet = database.getDatabase().getCurrentPet(player.getUniqueId());
        String name = event.getRightClicked().getName();
        if (currentPet != null && equalsComponentAndPetName(currentPet, name)) {
            event.setCancelled(true);
            player.openInventory(new OpInventories.PetMainInventory().buildInventory());
            return;
        }
        database.getDatabase().getActivePetMap().keySet().stream()
                    .map(uuid -> database.getDatabase().getCurrentPet(uuid))
                    .filter(pet -> pet.getPetName().equals(name)).findAny().ifPresent(pet -> {
                        event.setCancelled(true);
                        player.openInventory(new OpInventories.GuestInventory(pet).buildInventory());
                    });
    }

    @EventHandler
    public void onChunkUnload(@NotNull ChunkUnloadEvent event) {
        checkForEntitiesByPlayer(event.getChunk().getEntities(), false);
    }

    @EventHandler
    public void onWorldChange(@NotNull PlayerChangedWorldEvent event) {
        checkForEntitiesByPlayer(event.getPlayer().getLocation().getChunk().getEntities(), true);
    }

    private void checkForEntitiesByPlayer(Object @NotNull [] entities, boolean portalCooldown) {
        for (Object entityObject : entities) {
            if (!(entityObject instanceof Entity entity)) {
                continue;
            }
            if (!entity.getType().equals(EntityType.PLAYER)) {
                continue;
            }
            Player player = (Player) entity;
            UUID uuid = player.getUniqueId();
            Pet pet = database.getDatabase().getCurrentPet(uuid);
            if (pet == null) {
                continue;
            }
            Entity entityPet = Bukkit.getEntity(pet.petUUID.getOwnUUID());
            if (entityPet == null) {
                continue;
            }
            entityPet.teleport(player.getLocation());
            if (portalCooldown) {
                entityPet.setPortalCooldown(300);
            }
        }
    }

}
