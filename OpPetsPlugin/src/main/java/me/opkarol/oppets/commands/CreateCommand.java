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
import me.opkarol.oppets.pets.OpPetsEntityTypes;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.skills.SkillUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.stream.Collectors;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

/**
 * The type Create command.
 */
public class CreateCommand implements ICommand {
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
            return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("badCommandUsage").replace("%proper_usage%", "/oppets create <TYPE> <NAME>"));
        }
        UUID playerUUID = player.getUniqueId();
        String petType = args[1];
        if (petType.equalsIgnoreCase("polarbear")) {
            petType = "Polar_Bear";
        } else if (petType.equalsIgnoreCase("mushroom")) {
            petType = "Mushroom_Cow";
        } else if (!(Database.getOpPets().getEntityManager().getAllowedEntities()
                .stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList()).contains(petType.toLowerCase()))) {
            return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("wrongType").replace("%pet_type%", petType));
        }
        String petName = args[2];
        if (Database.getOpPets().getDatabase().getPetList(playerUUID) != null) {
            for (Pet pet : Database.getOpPets().getDatabase().getPetList(playerUUID)) {
                assert pet.getPetName() != null;
                if (pet.getPetName().equals(petName)) {
                    return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("petWithSameName").replace("%pet_name%", petName));
                }
            }
        }
        OpPetsEntityTypes.TypeOfEntity type;
        try {
            type = OpPetsEntityTypes.TypeOfEntity.valueOf(petType.toUpperCase());
        } catch (IllegalArgumentException e) {
            return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("wrongType").replace("%pet_type%", petType));
        }
        Pet pet = new Pet(petName, type, null, playerUUID, new SkillUtils().getRandomSkillName(type), true);
        if (Database.getOpPets().getDatabase().addPetToPetsList(playerUUID, pet)) {
            return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("createdPet").replace("%pet_name%", petName).replace("%pet_type%", petType));
        }
        try {
            throw new Exception("Unexpected error: AddPetToPetsList ins't working");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets permission.
     *
     * @return the permission
     */
    @Override
    public String getPermission() {
        return "oppets.command.create";
    }

    /**
     * Gets sub command.
     *
     * @return the sub command
     */
    @Override
    public String getSubCommand() {
        return "create";
    }
}
