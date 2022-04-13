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
import me.opkarol.oppets.inventories.PrestigeInventory;
import me.opkarol.oppets.pets.Pet;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

/**
 * The type Prestige command.
 */
public class PrestigeCommand implements ICommand {
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
        Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
        if (pet == null) {
            return returnMessage(sender, database.getOpPets().getMessages().getMessagesAccess().stringMessage("invalidPet"));
        }
        if (args.length > 1) {
            return returnMessage(sender, database.getOpPets().getMessages().getMessagesAccess().stringMessage("badCommandUsage").replace("%proper_usage%", "/oppets prestige"));
        }
        player.openInventory(new PrestigeInventory().getInventory());
        return true;
    }

    /**
     * Gets permission.
     *
     * @return the permission
     */
    @Override
    public String getPermission() {
        return "oppets.command.prestige";
    }

    /**
     * Gets sub command.
     *
     * @return the sub command
     */
    @Override
    public String getSubCommand() {
        return "prestige";
    }
}
