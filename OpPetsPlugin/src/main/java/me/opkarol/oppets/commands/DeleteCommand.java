package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.pets.Pet;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.inventories.DeleteAnvilInventory;
import me.opkarol.oppets.utils.FormatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class DeleteCommand implements SubCommandInterface {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, "");
        }

        if (args.length != 2) {
            return returnMessage(sender, "");
        }

        Pet pet = getPetByName(player.getUniqueId(), FormatUtils.getNameString(args[1]));
        if (pet == null) {
            return returnMessage(sender, "");
        } else {
            new DeleteAnvilInventory(pet, player);
            return true;
        }
    }

    @Override
    public String getPermission() {
        return "oppets.command.delete";
    }

    @Override
    public List<String> getDescription() {
        return null;
    }

    @Override
    public String getDescriptionAsString() {
        return null;
    }

    @Override
    public String getSubCommand() {
        return "delete";
    }

    private Pet getPetByName(UUID playerUUID, String petName) {
        final Pet[] petI = new Pet[1];
        OpPets.getDatabase().getPetList(playerUUID).forEach(pet -> {
            if (Objects.equals(FormatUtils.getNameString(pet.getPetName()), FormatUtils.getNameString(petName))) {
                petI[0] = pet;
            }
        });
        return petI[0];
    }
}
