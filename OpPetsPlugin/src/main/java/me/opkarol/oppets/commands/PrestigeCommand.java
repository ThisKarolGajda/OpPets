package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.pets.Pet;
import me.opkarol.oppets.Messages;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.inventories.anvil.PrestigeConfirmAnvilInventory;
import me.opkarol.oppets.utils.OpUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class PrestigeCommand implements ICommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, Messages.stringMessage("noConsole"));
        }

        if (args.length != 1) {
            return returnMessage(sender, Messages.stringMessage("badCommandUsage").replace("%proper_usage%", "/oppets prestige"));
        }

        Pet pet = OpPets.getDatabase().getCurrentPet(player.getUniqueId());
        if (pet == null) {
            return returnMessage(sender, Messages.stringMessage("invalidPet"));
        }

        if (!OpUtils.canPrestige(pet)) {
            return returnMessage(sender, Messages.stringMessage("cantPrestige").replace("%more_levels%", String.valueOf(OpUtils.getLevelsForPrestige(pet))));
        }

        new PrestigeConfirmAnvilInventory(pet, player);

        return true;
    }

    @Override
    public String getPermission() {
        return "oppets.command.prestige";
    }

    @Override
    public String getSubCommand() {
        return "prestige";
    }
}
