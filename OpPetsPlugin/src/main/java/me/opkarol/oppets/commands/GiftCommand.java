package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.pets.Pet;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class GiftCommand implements SubCommandInterface {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, "");
        }
        ///oppets gift PET PLAYER_NAME
        if (args.length != 3) {
            return returnMessage(sender, "");
        }

        UUID uuid = player.getUniqueId();
        String petName = args[1];
        if (OpPets.getDatabase().getPetList(uuid).stream().noneMatch(pet -> FormatUtils.getNameString(pet.getPetName()).equals(FormatUtils.getNameString(petName)))) {
            return returnMessage(sender, "");
        }
        Pet pet = null;
        for (Pet pet1 : OpPets.getDatabase().getPetList(uuid)) {
            assert pet1.getPetName() != null;
            if (FormatUtils.getNameString(pet1.getPetName()).equals(FormatUtils.getNameString(petName))) {
                pet = pet1;
            }
        }

        String playerName = args[2];
        boolean b;
        UUID uuid1;
        try {
            Player playerI = Bukkit.getPlayer(playerName);
            b = playerI.hasPlayedBefore();
            uuid1 = playerI.getUniqueId();
        } catch (Exception ignore) {
            OfflinePlayer playerI = Bukkit.getOfflinePlayer(playerName);
            b = playerI.hasPlayedBefore();
            uuid1 = playerI.getUniqueId();
        }

        if (!b || uuid1.toString().length() == 0) {
            return returnMessage(sender, "");
        }

        OpPets.getDatabase().removePet(uuid, pet);
        OpPets.getDatabase().addPetToPetsList(uuid1, pet);

        return returnMessage(sender, "transformed");
    }



    @Override
    public String getPermission() {
        return "oppets.command.gift";
    }

    @Override
    public List<String> getDescription() {
        return null;
    }

    @Override
    public String getDescriptionAsString() {
        return null;
    }

    @Override
    public String getSubCommand() {
        return "gift";
    }
}
