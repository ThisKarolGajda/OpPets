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
import me.opkarol.oppets.inventories.anvil.DeleteAnvilInventory;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.FormatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

/**
 * The type Delete command.
 */
public class DeleteCommand implements ICommand {
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

        if (args.length != 2) {
            return returnMessage(sender, database.getOpPets().getMessages().getMessagesAccess().stringMessage("badCommandUsage").replace("%proper_usage%", "/oppets delete <PET>"));
        }

        Pet pet = getPetByName(player.getUniqueId(), FormatUtils.getNameString(args[1]));
        if (pet == null) {
            return returnMessage(sender, database.getOpPets().getMessages().getMessagesAccess().stringMessage("invalidPet"));
        } else {
            new DeleteAnvilInventory(pet, player);
            return true;
        }
    }

    /**
     * Gets permission.
     *
     * @return the permission
     */
    @Override
    public String getPermission() {
        return "oppets.command.delete";
    }

    /**
     * Gets sub command.
     *
     * @return the sub command
     */
    @Override
    public String getSubCommand() {
        return "delete";
    }

    /**
     * Gets pet by name.
     *
     * @param playerUUID the player uuid
     * @param petName    the pet name
     * @return the pet by name
     */
    private Pet getPetByName(UUID playerUUID, String petName) {
        return database.getDatabase().getPetList(playerUUID).stream()
                .filter(pet -> Objects.equals(FormatUtils.getNameString(pet.getPetName()), FormatUtils.getNameString(petName))).collect(Collectors.toList())
                .get(0);
    }
}
