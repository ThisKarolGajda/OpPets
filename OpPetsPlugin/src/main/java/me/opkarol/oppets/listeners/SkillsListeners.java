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
import me.opkarol.oppets.databases.external.SkillDatabase;
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

public class SkillsListeners implements Listener {
    private final Database database = Database.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerMine(@NotNull BlockBreakEvent event) {
        ItemStack item = event.getPlayer().getItemInHand();
        if (item.getType() == Material.AIR || item.getItemMeta() == null || item.getItemMeta().getEnchants().containsKey(Enchantment.SILK_TOUCH)) {
            return;
        }
        Player player = event.getPlayer();
        if (database.getDatabase().getCurrentPet(player.getUniqueId()) == null) {
            return;
        }
        Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
        SkillDatabase skillDatabase = database.getOpPets().getSkillDatabase();
        Material material = event.getBlock().getType();
        skillDatabase.addPoints(pet, player, "MINING", material);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerHarvest(@NotNull PlayerHarvestBlockEvent event) {
        Player player = event.getPlayer();
        if (database.getDatabase().getCurrentPet(player.getUniqueId()) == null) {
            return;
        }
        Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
        SkillDatabase skillDatabase = database.getOpPets().getSkillDatabase();
        Material material = event.getHarvestedBlock().getType();
        skillDatabase.addPoints(pet, player, "HARVESTING", material);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerHarvest(@NotNull BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (database.getDatabase().getCurrentPet(player.getUniqueId()) == null) {
            return;
        }
        Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
        SkillDatabase skillDatabase = database.getOpPets().getSkillDatabase();
        Material material = event.getBlock().getType();
        skillDatabase.addPoints(pet, player, "HARVESTING", material);
    }

    @EventHandler
    public void playerCraft(@NotNull CraftItemEvent event) {
        ItemStack item = event.getCurrentItem();
        if (item == null) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        if (database.getDatabase().getCurrentPet(player.getUniqueId()) == null) {
            return;
        }
        Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
        SkillDatabase skillDatabase = database.getOpPets().getSkillDatabase();
        Material material = item.getType();
        skillDatabase.addPoints(pet, player, "CRAFTING", material, item.getAmount());
    }

    @EventHandler
    public void playerSmelt(@NotNull FurnaceExtractEvent event) {
        Player player = event.getPlayer();
        if (database.getDatabase().getCurrentPet(player.getUniqueId()) == null) {
            return;
        }
        Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
        SkillDatabase skillDatabase = database.getOpPets().getSkillDatabase();
        Material material = event.getBlock().getType();
        skillDatabase.addPoints(pet, player, "SMELTING", material, event.getItemAmount());
    }

    @EventHandler
    public void playerFish(@NotNull PlayerFishEvent event) {
        Player player = event.getPlayer();
        if (database.getDatabase().getCurrentPet(player.getUniqueId()) == null) {
            return;
        }
        Item item = (Item) event.getCaught();
        if (item == null) {
            return;
        }
        Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
        SkillDatabase skillDatabase = database.getOpPets().getSkillDatabase();
        Material material = item.getItemStack().getType();
        skillDatabase.addPoints(pet, player, "FISHING", material);
    }
}
