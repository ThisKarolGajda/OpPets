package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.interfaces.ICommand;
import me.opkarol.oppets.inventories.SummonInventory;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.PetsUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

import static me.opkarol.oppets.commands.OpPetsCommand.noPetsString;
import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

/**
 * The type Summon command.
 */
public class SummonCommand implements ICommand {

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
            return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("noConsole"));
        }
        UUID playerUUID = player.getUniqueId();
        if (args.length == 1) {
            player.openInventory(new SummonInventory(playerUUID).getInventory());
            return true;
        }
        if (args.length != 2) {
            return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("badCommandUsage").replace("%proper_usage%", "/oppets summon <PET>"));
        }
        Pet activePet = Database.getOpPets().getDatabase().getCurrentPet(playerUUID);
        if (args[1].equals(noPetsString)) {
            return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("petBlockedName").replace("%blocked_word%", noPetsString));
        }
        List<Pet> playerPets = Database.getOpPets().getDatabase().getPetList(playerUUID);
        if (playerPets == null || playerPets.size() == 0) {
            return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("petListEmpty"));
        }
        PetsUtils.summonPet(args[1], playerUUID, player);
        return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("invalidPet"));
    }

    /**
     * Gets permission.
     *
     * @return the permission
     */
    @Override
    public String getPermission() {
        return "oppets.command.summon";
    }

    /**
     * Gets sub command.
     *
     * @return the sub command
     */
    @Override
    public String getSubCommand() {
        return "summon";
    }

}
