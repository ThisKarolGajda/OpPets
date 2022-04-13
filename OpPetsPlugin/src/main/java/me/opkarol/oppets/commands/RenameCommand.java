package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.interfaces.ICommand;
import me.opkarol.oppets.inventories.anvil.RenameAnvilInventory;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.FormatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

/**
 * The type Rename command.
 */
public class RenameCommand implements ICommand {
    /**
     * The Database.
     */
    private final Database database = Database.getInstance(OpPets.getInstance().getSessionIdentifier().getSession());

    /**
     * Execute boolean.
     *
     * @param sender the sender
     * @param args   the args
     * @return the boolean
     */
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, database.getOpPets().getMessages().getMessagesAccess().stringMessage("noConsole"));
        }

        UUID uuid = player.getUniqueId();
        if (database.getDatabase().getPetList(uuid) == null) {
            return returnMessage(sender, database.getOpPets().getMessages().getMessagesAccess().stringMessage("invalidPet"));
        }

        if (args.length == 1) {
            new RenameAnvilInventory(database.getDatabase().getCurrentPet(uuid), player);
            return true;
        } else if (args.length == 2) {
            String petName = args[1];
            List<Pet> list = database.getDatabase().getPetList(uuid);
            for (Pet pet : list) {
                assert pet.getPetName() != null;
                if (FormatUtils.getNameString(pet.getPetName()).equals(FormatUtils.getNameString(petName))) {
                    new RenameAnvilInventory(pet, player);
                }
            }
            return true;
        }

        return returnMessage(sender, database.getOpPets().getMessages().getMessagesAccess().stringMessage("badCommandUsage").replace("%proper_usage%", "/oppets rename <PET>"));
    }

    /**
     * Gets permission.
     *
     * @return the permission
     */
    @Override
    public String getPermission() {
        return "oppets.command.rename";
    }

    /**
     * Gets sub command.
     *
     * @return the sub command
     */
    @Override
    public String getSubCommand() {
        return "rename";
    }
}
