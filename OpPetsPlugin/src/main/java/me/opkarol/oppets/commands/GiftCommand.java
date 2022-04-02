package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.files.Messages;
import me.opkarol.oppets.interfaces.ICommand;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

/**
 * The type Gift command.
 */
public class GiftCommand implements ICommand {
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

        if (args.length != 3) {
            return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("badCommandUsage").replace("%proper_usage%", "/oppets gift <PET> <PLAYER>"));
        }

        UUID uuid = player.getUniqueId();
        String petName = args[1];
        Pet pet = null;

        for (Pet pet1 : Database.getOpPets().getDatabase().getPetList(uuid)) {
            assert pet1.getPetName() != null;
            if (FormatUtils.getNameString(pet1.getPetName()).equals(FormatUtils.getNameString(petName))) {
                pet = pet1;
            }
        }

        if (pet == null) {
            return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("invalidPet"));
        }

        if (!pet.isGiftable()) {
            return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("petIsntGiftable"));
        }

        String playerName = args[2];
        boolean b;
        UUID uuid1;
        String playerIName = "";
        try {
            Player playerI = Bukkit.getPlayer(playerName);
            assert playerI != null;
            b = playerI.hasPlayedBefore();
            uuid1 = playerI.getUniqueId();
            playerIName = playerI.getName();
        } catch (Exception ignore) {
            OfflinePlayer playerI = Bukkit.getOfflinePlayer(playerName);
            b = playerI.hasPlayedBefore();
            uuid1 = playerI.getUniqueId();
            playerIName = playerI.getName();
        }

        if (Database.getOpPets().getDatabase().getPetList(uuid1).stream().anyMatch(pet1 -> FormatUtils.getNameString(pet1.getPetName()).equals(FormatUtils.getNameString(petName)))) {
            return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("receiverSameNamedPet").replace("%pet_name%", petName));
        }

        if (!b || uuid1.toString().length() == 0) {
            return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("receiverInvalid"));
        }

        Database.getOpPets().getDatabase().removePet(uuid, pet);
        Database.getOpPets().getDatabase().addPetToPetsList(uuid1, pet);

        return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("petGifted").replace("%pet_name%", petName).replace("%player_name%", playerIName));
    }

    /**
     * Gets permission.
     *
     * @return the permission
     */
    @Override
    public String getPermission() {
        return "oppets.command.gift";
    }

    /**
     * Gets sub command.
     *
     * @return the sub command
     */
    @Override
    public String getSubCommand() {
        return "gift";
    }
}
