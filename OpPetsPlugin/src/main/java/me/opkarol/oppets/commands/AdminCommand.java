package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.databases.Database;
import dir.files.Messages;
import dir.interfaces.ICommand;
import dir.pets.OpPetsEntityTypes;
import dir.pets.Pet;
import dir.pets.PetsUtils;
import dir.utils.FormatUtils;
import dir.utils.OpUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

import static dir.utils.FormatUtils.returnMessage;

public class AdminCommand implements ICommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return returnMessage(sender, Messages.stringMessage("noConsole"));
        }

        if (args.length != 5) {
            return returnMessage(sender, Messages.stringMessage("badCommandUsage").replace("%proper_usage%", "/oppets admin <PLAYER> <PET> <KEY> <VALUE>"));
        }

        String name = args[1];
        UUID uuid = OpUtils.getUUIDFromName(name);

        List<Pet> petList = Database.getOpPets().getDatabase().getPetList(uuid);
        if (petList.size() == 0) {
            return returnMessage(sender, Messages.stringMessage("petListEmpty"));
        }

        String petName = args[2];
        Pet pet = null;
        for (Pet petI : petList) {
            if (FormatUtils.getNameString(petName).equals(FormatUtils.getNameString(petI.getPetName()))) {
                pet = petI;
                break;
            }
        }

        if (pet == null) {
            return returnMessage(sender, Messages.stringMessage("invalidPet"));
        }

        petList.removeIf(pet1 -> FormatUtils.getNameString(pet1.getPetName()).equals(FormatUtils.getNameString(petName)));

        String value = args[4];
        switch (args[3]) {
            case "level" -> pet.setLevel(Integer.parseInt(value));
            case "xp" -> pet.setPetExperience(Double.parseDouble(value));
            case "prestige" -> pet.setPrestige(value);
            case "name" -> pet.setPetName(value);
            case "settings" -> pet.setSettingsSerialized(value);
            case "type" -> pet.setPetType(OpPetsEntityTypes.TypeOfEntity.valueOf(value));
            case "skill" -> pet.setSkillName(value);
            default -> throw new IllegalStateException("Unexpected value: " + args[3]);
        }

        petList.add(pet);
        Database.getOpPets().getDatabase().setPets(uuid, petList);

        PetsUtils.savePetProgress(pet, uuid);

        return returnMessage(sender, Messages.stringMessage("changedAdminValue").replace("%value%", value).replace("%key%", args[3]).replace("%player_name%", name).replace("%pet_name%", petName));
    }

    @Override
    public String getPermission() {
        return "oppets.command.admin";
    }

    @Override
    public String getSubCommand() {
        return "admin";
    }
}
