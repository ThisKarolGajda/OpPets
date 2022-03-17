package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.databases.Database;
import dir.interfaces.ICommand;
import dir.pets.Pet;
import dir.files.Messages;
import me.opkarol.oppets.inventories.anvil.RenameAnvilInventory;
import dir.utils.FormatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

import static dir.utils.FormatUtils.returnMessage;

public class RenameCommand implements ICommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, Messages.stringMessage("noConsole"));
        }

        UUID uuid = player.getUniqueId();
        if (Database.getOpPets().getDatabase().getPetList(uuid) == null) {
            return returnMessage(sender, Messages.stringMessage("invalidPet"));
        }

        if (args.length == 1) {
            new RenameAnvilInventory(Database.getDatabase().getCurrentPet(uuid), player);
            return true;
        } else if (args.length == 2) {
            String petName = args[1];
            List<Pet> list = Database.getDatabase().getPetList(uuid);
            for (Pet pet : list) {
                assert pet.getPetName() != null;
                if (FormatUtils.getNameString(pet.getPetName()).equals(FormatUtils.getNameString(petName))) {
                    new RenameAnvilInventory(pet, player);
                }
            }
            return true;
        }

        return returnMessage(sender, Messages.stringMessage("badCommandUsage").replace("%proper_usage%", "/oppets rename <PET>"));
    }

    @Override
    public String getPermission() {
        return "oppets.command.rename";
    }

    @Override
    public String getSubCommand() {
        return "rename";
    }
}
