package me.opkarol.oppets.inventories.anvil;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.databases.Database;
import dir.files.Messages;
import dir.pets.Pet;
import dir.utils.FormatUtils;
import me.opkarol.oppets.commands.OpPetsCommand;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class RenameAnvilInventory {

    public RenameAnvilInventory(@NotNull Pet pet, @NotNull Player playerOpened) {
        String title = Messages.stringMessage("titleRename");
        assert pet.getPetName() != null;
        String petName = pet.getPetName();
        new AnvilGUI.Builder()
                .itemLeft(new ItemStack(Material.PAPER))
                .onComplete(((player, s) -> {
                    if (s != null) {
                        if (s.equals(OpPetsCommand.noPetsString)) {
                            return AnvilGUI.Response.text(Messages.stringMessage("thisNameIsBlocked"));
                        }
                        for (char character : s.toCharArray()) {
                            if (String.valueOf(character).equals(" ")) {
                                return AnvilGUI.Response.text(Messages.stringMessage("nameWithSpaces"));
                            }
                        }
                        Database.getOpPets().getUtils().killPetFromPlayerUUID(player.getUniqueId());

                        /*
                            If formatted message (s) has not equal length to not-formatted one
                            it means that s contains color characters (&x),
                            in other case, in which message has the same length to string
                            it goes through a normal process.
                        */
                        UUID uuid = player.getUniqueId();
                        Database.getDatabase().getPetList(uuid).removeIf(pet1 -> {
                            assert pet1.getPetName() != null;
                            return pet1.getPetName().equals(pet.getPetName());
                        });

                        if (FormatUtils.formatMessage(s).length() != s.length()) {
                            if (player.hasPermission("oppets.pet.rename.colors") || player.isOp()) {
                                pet.setPetName(s);
                                player.sendMessage(Messages.stringMessage("changedName").replace("%new_pet_name%", FormatUtils.formatMessage(s)));
                            } else {
                                player.sendMessage(Messages.stringMessage("noPermission").replace("%permission%", "oppets.pet.rename.colors"));
                                return AnvilGUI.Response.close();
                            }
                        } else {
                            pet.setPetName(s);
                            player.sendMessage(Messages.stringMessage("changedName").replace("%new_pet_name%", s));
                        }

                        Database.getOpPets().getCreator().spawnMiniPet(pet, player);
                        List<Pet> petList = Database.getOpPets().getDatabase().getPetList(uuid);
                        petList.add(pet);
                        Database.getOpPets().getDatabase().setPets(uuid, petList);
                        return AnvilGUI.Response.close();
                    } else {
                        return AnvilGUI.Response.text(Messages.stringMessage("incorrectValueName"));
                    }
                }))
                .text(petName)
                .title(title)
                .plugin(Database.getInstance())
                .open(playerOpened);
    }


}
