package me.opkarol.oppets.listeners;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.skills.Adder;
import me.opkarol.oppets.skills.Skill;
import me.opkarol.oppets.skills.SkillDatabase;
import me.opkarol.oppets.skills.SkillEnums;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Skills listeners.
 */
public class SkillsListeners implements Listener {

    /**
     * Player mine.
     *
     * @param event the event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerMine(@NotNull BlockBreakEvent event) {
        ItemStack item = event.getPlayer().getItemInHand();
        if (item.getType() == Material.AIR || item.getItemMeta() == null || item.getItemMeta().getEnchants().containsKey(Enchantment.SILK_TOUCH)) {
            return;
        }
        Player player = event.getPlayer();
        if (Database.getDatabase().getCurrentPet(player.getUniqueId()) == null) {
            return;
        }
        Pet pet = Database.getDatabase().getCurrentPet(player.getUniqueId());
        SkillDatabase database = Database.getOpPets().getSkillDatabase();
        List<Material> materials = new ArrayList<>();
        database.getSkillFromMap(pet.getSkillName()).getAdderList().stream()
                .filter(adder1 -> adder1.getAdder().equals(SkillEnums.SkillsAdders.MINING))
                .map(Adder::getTypes)
                .forEach(materials1 -> materials.addAll(List.of(materials1)));
        if (!materials.contains(event.getBlock().getType())) {
            return;
        }
        database.addPoint(SkillEnums.SkillsAdders.MINING, pet, player);
    }

    /**
     * Player harvest.
     *
     * @param event the event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerHarvest(@NotNull PlayerHarvestBlockEvent event) {
        Player player = event.getPlayer();
        if (Database.getDatabase().getCurrentPet(player.getUniqueId()) == null) {
            return;
        }
        Pet pet = Database.getDatabase().getCurrentPet(player.getUniqueId());
        SkillDatabase database = Database.getOpPets().getSkillDatabase();
        Skill skill = database.getSkillFromMap(pet.getSkillName());
        List<Material> materials = new ArrayList<>();
        skill.getAdderList().stream()
                .filter(adder1 -> adder1.getAdder().equals(SkillEnums.SkillsAdders.HARVESTING))
                .map(Adder::getTypes)
                .forEach(materials1 -> materials.addAll(List.of(materials1)));
        if (!materials.contains(event.getHarvestedBlock().getType())) {
            return;
        }
        database.addPoint(SkillEnums.SkillsAdders.HARVESTING, pet, player);
    }

    /**
     * Player harvest.
     *
     * @param event the event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerHarvest(@NotNull BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (Database.getDatabase().getCurrentPet(player.getUniqueId()) == null) {
            return;
        }
        Pet pet = Database.getDatabase().getCurrentPet(player.getUniqueId());
        SkillDatabase database = Database.getOpPets().getSkillDatabase();
        List<Material> materials = new ArrayList<>();
        database.getSkillFromMap(pet.getSkillName()).getAdderList().stream()
                .filter(adder1 -> adder1.getAdder().equals(SkillEnums.SkillsAdders.HARVESTING))
                .map(Adder::getTypes)
                .forEach(materials1 -> materials.addAll(List.of(materials1)));
        if (!materials.contains(event.getBlock().getType())) {
            return;
        }
        database.addPoint(SkillEnums.SkillsAdders.HARVESTING, pet, player);
    }

    /**
     * Player craft.
     *
     * @param event the event
     */
    @EventHandler
    public void playerCraft(@NotNull CraftItemEvent event) {
        if (event.getCurrentItem() == null) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (Database.getDatabase().getCurrentPet(player.getUniqueId()) == null) {
            return;
        }
        Pet pet = Database.getDatabase().getCurrentPet(player.getUniqueId());
        SkillDatabase database = Database.getOpPets().getSkillDatabase();
        List<Material> materials = new ArrayList<>();
        database.getSkillFromMap(pet.getSkillName()).getAdderList().stream()
                .filter(adder1 -> adder1.getAdder().equals(SkillEnums.SkillsAdders.CRAFTING))
                .map(Adder::getTypes)
                .forEach(materials1 -> materials.addAll(List.of(materials1)));
        if (!materials.contains(event.getCurrentItem().getType())) {
            return;
        }
        database.addPoint(SkillEnums.SkillsAdders.CRAFTING, pet, player);
    }

    /**
     * Player smelt.
     *
     * @param event the event
     */
    @EventHandler
    public void playerSmelt(@NotNull FurnaceExtractEvent event) {
        Player player = event.getPlayer();
        if (Database.getDatabase().getCurrentPet(player.getUniqueId()) == null) {
            return;
        }
        Pet pet = Database.getDatabase().getCurrentPet(player.getUniqueId());
        SkillDatabase database = Database.getOpPets().getSkillDatabase();
        List<Material> materials = new ArrayList<>();
        database.getSkillFromMap(pet.getSkillName()).getAdderList().stream()
                .filter(adder1 -> adder1.getAdder().equals(SkillEnums.SkillsAdders.SMELTING))
                .map(Adder::getTypes)
                .forEach(materials1 -> materials.addAll(List.of(materials1)));
        if (!materials.contains(event.getBlock().getType())) {
            return;
        }
        database.addPoint(SkillEnums.SkillsAdders.SMELTING, pet, player);
    }

    /**
     * Player fish.
     *
     * @param event the event
     */
    @EventHandler
    public void playerFish(@NotNull PlayerFishEvent event) {
        Player player = event.getPlayer();
        if (Database.getDatabase().getCurrentPet(player.getUniqueId()) == null) {
            return;
        }
        Item item = (Item) event.getCaught();
        if (event.getCaught() == null || item == null) {
            return;
        }
        Pet pet = Database.getDatabase().getCurrentPet(player.getUniqueId());
        SkillDatabase database = Database.getOpPets().getSkillDatabase();
        List<Material> materials = new ArrayList<>();
        database.getSkillFromMap(pet.getSkillName()).getAdderList().stream()
                .filter(adder1 -> adder1.getAdder().equals(SkillEnums.SkillsAdders.FISHING))
                .map(Adder::getTypes)
                .forEach(materials1 -> materials.addAll(List.of(materials1)));
        if (!materials.contains(item.getItemStack().getType())) {
            return;
        }
        database.addPoint(SkillEnums.SkillsAdders.FISHING, pet, player);
    }
}
