package me.opkarol.oppets.listeners;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.events.PetLevelupEvent;
import me.opkarol.oppets.events.PrestigeChangeEvent;
import me.opkarol.oppets.inventories.GuestInventory;
import me.opkarol.oppets.inventories.PetMainInventory;
import me.opkarol.oppets.particles.ParticlesManager;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.prestiges.PrestigeManager;
import me.opkarol.oppets.skills.Ability;
import me.opkarol.oppets.utils.FormatUtils;
import me.opkarol.oppets.utils.OpUtils;
import net.milkbowl.vault.economy.Economy;
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
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The type Pet listeners.
 */
public class PetListeners implements Listener {
    /**
     * The Database.
     */
    private final Database database = Database.getInstance(OpPets.getInstance().getSessionIdentifier().getSession());
    /**
     * The Particles manager.
     */
    private final ParticlesManager particlesManager = new ParticlesManager();

    /**
     * Pet levelup.
     *
     * @param event the event
     */
    @EventHandler
    public void petLevelup(@NotNull PetLevelupEvent event) {
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
        player.sendMessage(database.getOpPets().getMessages().getMessagesAccess().stringMessage("petLevelUpMessage").replace("%newline%", "\n").replace("%pet_name%", FormatUtils.formatMessage(pet.getPetName())).replace("%current_level%", String.valueOf(OpUtils.getLevel(pet))).replace("%max_level%", String.valueOf(OpUtils.getMaxLevel(pet))).replace("%experience_level%", String.valueOf(OpUtils.getPetLevelExperience(pet))));
        if (pet.areParticlesEnabled()) {
            particlesManager.spawnLevelUpPetEffect(player, database.getOpPets().getUtils().getEntityByUniqueId(event.getPet().getOwnUUID()));
        }
        List<Ability> abilities = database.getOpPets().getSkillDatabase().getSkillFromMap(pet.getSkillName()).getAbilityList();
        for (Ability ability : abilities) {
            switch (ability.getAbility()) {
                case PLUGIN_CONNECTION -> {
                    if (ability.getPLUGIN_CONNECTION().equalsIgnoreCase("Vault")) {
                        Object economy = database.getOpPets().getEconomy();
                        if (economy != null) {
                            Economy economy1 = (Economy) economy;
                            economy1.depositPlayer(player, Double.parseDouble(ability.getPluginAction()));
                        }
                    }
                }
                case CUSTOM_COMMAND -> {
                    String command = ability.getPluginAction();
                    if (command != null) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
                    }
                }
                case VANILLA_EFFECT -> {
                    String[] strings = ability.getVANILLA_EFFECT().split(";");
                    if (strings.length != 3) return;
                    PotionEffectType potion = PotionEffectType.getByName(strings[0]);
                    if (potion == null) return;
                    event.getPlayer().addPotionEffect(potion.createEffect(Integer.parseInt(strings[1]), Integer.parseInt(strings[2])));
                }
                case TREASURE -> {

                }
                case CUSTOM_MESSAGE -> player.sendMessage(ability.getMESSAGE());
            }
        }
    }

    /**
     * Prestige change.
     *
     * @param event the event
     */
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
        player.sendMessage(database.getOpPets().getMessages().getMessagesAccess().stringMessage("prestigeUpMessage").replace("%newline%", "\n").replace("%pet_name%", FormatUtils.formatMessage(pet.getPetName())).replace("%current_prestige%", pm.getFilledPrestige(pet.getPrestige())).replace("%max_level%", String.valueOf(OpUtils.getMaxLevel(pet))));
        database.getOpPets().getUtils().respawnPet(pet, player);
        if (pet.areParticlesEnabled()) {
            particlesManager.prestigeChangeEffect(player, database.getOpPets().getUtils().getEntityByUniqueId(event.getPet().getOwnUUID()));
        }
    }

    /**
     * Player interact.
     *
     * @param event the event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerInteract(@NotNull PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (player.isSneaking()) {
            return;
        }
        if (database.getDatabase().getCurrentPet(player.getUniqueId()) != null) {
            if (database.getDatabase().getCurrentPet(player.getUniqueId()).getOwnUUID() == event.getRightClicked().getUniqueId()) {
                event.setCancelled(true);
                player.openInventory(new PetMainInventory().getInventory());
                event.getPlayer().updateInventory();
                return;
            }
        }
        for (UUID uuid : database.getDatabase().getActivePetMap().keySet()) {
            Pet pet = database.getDatabase().getCurrentPet(uuid);
            if (pet == null || pet.getOwnUUID() == null) {
                return;
            }
            if (!pet.getOwnUUID().equals(event.getRightClicked().getUniqueId())) {
                return;
            }
            event.setCancelled(true);
            Objects.requireNonNull(Bukkit.getPlayer(player.getUniqueId())).openInventory(new GuestInventory(pet).getInventory());
            event.getPlayer().updateInventory();
        }

    }

    /**
     * On chunk unload.
     *
     * @param event the event
     */
    @EventHandler
    public void onChunkUnload(@NotNull ChunkUnloadEvent event) {
        checkForEntitiesByPlayer(event.getChunk().getEntities(), false);
    }

    /**
     * On world change.
     *
     * @param event the event
     */
    @EventHandler
    public void onWorldChange(@NotNull PlayerChangedWorldEvent event) {
        checkForEntitiesByPlayer(event.getPlayer().getLocation().getChunk().getEntities(), true);
    }

    /**
     * Check for entities by player.
     *
     * @param entities       the entities
     * @param portalCooldown the portal cooldown
     */
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
            Entity entityPet = Bukkit.getEntity(pet.getOwnUUID());
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
