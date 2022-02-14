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
import me.opkarol.oppets.utils.FormatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static me.opkarol.oppets.commands.OpPetsCommand.noPetsString;
import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class SummonCommand implements ICommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, Messages.stringMessage("noConsole"));
        }

        if (args.length != 2) {
            return returnMessage(sender, Messages.stringMessage("badCommandUsage").replace("%proper_usage%", "/oppets summon <PET>"));
        }

        UUID playerUUID = player.getUniqueId();
        List<Pet> playerPets = OpPets.getDatabase().getPetList(playerUUID);
        Pet activePet = OpPets.getDatabase().getCurrentPet(playerUUID);

        if (args[1].equals(noPetsString)) {
            return returnMessage(sender, Messages.stringMessage("petBlockedName").replace("%blocked_word%", noPetsString));
        }

        if (playerPets == null || playerPets.size() == 0) {
            return returnMessage(sender, Messages.stringMessage("petListEmpty"));
        }

        for (Pet pet : playerPets) {
            if (FormatUtils.getNameString(Objects.requireNonNull(pet.getPetName())).equals(FormatUtils.getNameString(args[1]))) {
                if (activePet == pet) {
                    return returnMessage(sender, Messages.stringMessage("samePet"));
                } else {
                    OpPets.getUtils().killPetFromPlayerUUID(playerUUID);
                    OpPets.getCreator().spawnMiniPet(pet, player);
                    return returnMessage(sender, Messages.stringMessage("summonedPet").replace("%pet_name%", FormatUtils.formatMessage(pet.getPetName())));
                }
            }
        }
        return returnMessage(sender, Messages.stringMessage("invalidPet"));
    }

    @Override
    public String getPermission() {
        return "oppets.command.summon";
    }

    @Override
    public String getSubCommand() {
        return "summon";
    }

}
