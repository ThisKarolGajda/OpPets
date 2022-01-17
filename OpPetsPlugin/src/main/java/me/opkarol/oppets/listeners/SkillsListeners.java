package me.opkarol.oppets.listeners;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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
        if (!event.getBlock().getType().equals(Material.STONE)) {
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
