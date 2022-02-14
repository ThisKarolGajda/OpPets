package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.pets.OpPetsEntityTypes;
import dir.pets.Pet;
import me.opkarol.oppets.Messages;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.skills.SkillUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.stream.Collectors;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class CreateCommand implements ICommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, Messages.stringMessage("noConsole"));
        }

        if (args.length != 3) {
            return returnMessage(sender, Messages.stringMessage("badCommandUsage").replace("%proper_usage%", "/oppets create <TYPE> <NAME>"));
        }

        UUID playerUUID = player.getUniqueId();
        String petType = args[1];
        if (petType.toLowerCase().equals("polarbear")) {
            petType = "Polar_Bear";
        } else if (petType.toLowerCase().equals("mushroom")) {
            petType = "Mushroom_Cow";
        } else if (!(OpPets.getEntityManager().getAllowedEntities()
                .stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList()).contains(petType.toLowerCase()))) {
            return returnMessage(sender, Messages.stringMessage("wrongType").replace("%pet_type%", petType));
        }

        String petName = args[2];
        if (OpPets.getDatabase().getPetList(playerUUID) != null) {
            for (Pet pet : OpPets.getDatabase().getPetList(playerUUID)) {
                assert pet.getPetName() != null;
                if (pet.getPetName().equals(petName)) {
                    return returnMessage(sender, Messages.stringMessage("petWithSameName").replace("%pet_name%", petName));
                }
            }
        }
        OpPetsEntityTypes.TypeOfEntity type;

        try {
            type = OpPetsEntityTypes.TypeOfEntity.valueOf(petType.toUpperCase());
        } catch (IllegalArgumentException e) {
            return returnMessage(sender, Messages.stringMessage("wrongType").replace("%pet_type%", petType));
        }

        Pet pet = new Pet(petName, type, null, playerUUID, new SkillUtils().getRandomSkillName(type), true);
        if (OpPets.getDatabase().addPetToPetsList(playerUUID, pet)) {
            return returnMessage(sender, Messages.stringMessage("createdPet").replace("%pet_name%", petName).replace("%pet_type%", petType));
        }

        try {
            throw new Exception("Unexpected error: AddPetToPetsList ins't working");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public String getPermission() {
        return "oppets.command.create";
    }

    @Override
    public String getSubCommand() {
        return "create";
    }
}
