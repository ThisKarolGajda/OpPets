package me.opkarol.oppets.listeners;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.databases.misc.AbilitiesEnums;
import dir.databases.misc.PetDatabaseObject;
import dir.pets.Pet;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.abilities.AbilitiesDatabase;
import me.opkarol.oppets.abilities.AbilitiesFunctions;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class PetAbilities implements Listener {
    private final AbilitiesDatabase abilitiesDatabase;
    private final AbilitiesFunctions functions;

    public PetAbilities() {
        abilitiesDatabase = OpPets.getAbilitiesDatabase();
        functions = new AbilitiesFunctions();
    }

    @EventHandler
    public void playerInteract(@NotNull PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (!player.isSneaking()) {
            return;
        }
        UUID uuid = player.getUniqueId();
        Pet pet = OpPets.getDatabase().getCurrentPet(uuid);
        if (pet == null) {
            return;
        }
        if (pet.getOwnUUID() != event.getRightClicked().getUniqueId()) {
            return;
        }
        if (abilitiesDatabase.cooldownMap.hasActiveCooldown(uuid)) {
            return;
        }
        boolean activated = false;
        PetDatabaseObject object = OpPets.getPetsDatabase().getObjectFromDatabase(pet.getPetType());
        for (String s : object.getAbilities()) {
            String[] abilityArgs = object.getAbilityArgs(s);
            if (OpPets.getAbilitiesDatabase().cooldownMap.hasActiveCooldown(uuid)) {
                return;
            }
            switch (object.getAbility(s)) {
                case ACTIONBAR -> functions.sendActionbar(player, abilityArgs[1]);
                case ADD_FOOD -> functions.addFood(player, Integer.parseInt(abilityArgs[1]));
                case ADD_HEALTH -> functions.addHealth(player, Integer.parseInt(abilityArgs[1]));
                case CONSOLE_COMMAND -> functions.consoleCommand(player, abilityArgs[1]);
                case CURE -> functions.cure(player);
                case POTION -> functions.potion(player, Objects.requireNonNull(PotionEffectType.getByName(abilityArgs[1])), Integer.parseInt(abilityArgs[2]), Integer.parseInt(abilityArgs[3]));
                case MESSAGE -> functions.message(player, abilityArgs[1]);
                case REVIVE -> functions.revive();
                case EXP -> functions.experience(player, Integer.parseInt(abilityArgs[1]));
                case PARTICLE -> functions.particle(player, Particle.valueOf(abilityArgs[1]), Integer.parseInt(abilityArgs[2]));
                case INVINCIBLE -> functions.invincible(player, Integer.parseInt(abilityArgs[1]));
                case PLAYER_COMMAND -> functions.playerCommand(player, abilityArgs[1]);
                case FIREBALL -> functions.fireball(player);
                default -> throw new IllegalStateException("Unexpected value: " + object.getAbility(s));
            }
            activated = true;
        }
        if (activated) {
            abilitiesDatabase.cooldownMap.addCooldown(uuid);
        }
    }

    @EventHandler
    public void playerFall(@NotNull EntityDamageEvent event) {
        if (!event.getEntityType().equals(EntityType.PLAYER)) {
            return;
        }
        Player player = (Player) event.getEntity();
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL){
            UUID uuid = player.getUniqueId();
            Pet pet = OpPets.getDatabase().getCurrentPet(player.getUniqueId());
            if (pet == null) {
                return;
            }
            for (String s : OpPets.getPetsDatabase().getObjectFromDatabase(pet.getPetType()).getAbilities()) {
                if (OpPets.getAbilitiesDatabase().cooldownMap.hasActiveCooldown(uuid)) {
                    return;
                }
                if (!OpPets.getPetsDatabase().getObjectFromDatabase(pet.getPetType()).getAbility(s).equals(AbilitiesEnums.AbilityType.STOP_FALL_DAMAGE)) {
                    return;
                }
                player.setFallDistance(0);
                abilitiesDatabase.cooldownMap.addCooldown(uuid);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void playerDamaged(@NotNull EntityDamageByEntityEvent event) {
        if (!event.getDamager().getType().equals(EntityType.PLAYER)) {
            return;
        }
        Player player = (Player) event.getDamager();
        Pet pet = OpPets.getDatabase().getCurrentPet(player.getUniqueId());
        if (pet == null) {
            return;
        }
        boolean activated = false;
        UUID uuid = player.getUniqueId();
        LivingEntity damaged = (LivingEntity) event.getEntity();
        PetDatabaseObject object = OpPets.getPetsDatabase().getObjectFromDatabase(pet.getPetType());
        for (String s : object.getAbilities()) {
            String[] abilityArgs = object.getAbilityArgs(s);
            if (OpPets.getAbilitiesDatabase().cooldownMap.hasActiveCooldown(uuid)) {
                return;
            }
            switch (object.getAbility(s)) {
                case LIGHTING -> damaged.getWorld().spawnEntity(damaged.getLocation(), EntityType.LIGHTNING);
                case FLAME -> damaged.setFireTicks(Integer.parseInt(abilityArgs[1]));
                case STOP_ATTACK -> event.setCancelled(true);
                case STOP_KNOCKBACK -> {
                    event.setCancelled(true);
                    player.damage(event.getDamage());
                }
                case STEAL_HEALTH -> {
                    if (damaged.isDead()) {
                        return;
                    }
                    int healthSteal = Integer.parseInt(abilityArgs[1]);
                    if (damaged.getHealth() - healthSteal < 1) {
                        return;
                    }
                    damaged.damage(healthSteal);
                    functions.addHealth(player, healthSteal);
                }
            }
            activated = true;
        }
        if (activated) {
            abilitiesDatabase.cooldownMap.addCooldown(uuid);
        }
    }
}
