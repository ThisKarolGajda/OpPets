package me.opkarol.oppets.inventories;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.databases.Database;
import dir.pets.Pet;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.commands.MainCommand;
import me.opkarol.oppets.utils.FormatUtils;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

import static me.opkarol.oppets.utils.ConfigUtils.getMessage;

public class RenameAnvilInventory {
    private final String thisNameIsBlocked = getMessage("AnvilInventories.RenameInventory.thisNameIsBlocked");
    private final String nameWithSpaces = getMessage("AnvilInventories.RenameInventory.nameWithSpaces");
    private final String changedName = getMessage("AnvilInventories.RenameInventory.changedName");
    private final String incorrectValueName = getMessage("AnvilInventories.RenameInventory.incorrectValueName");
    private final String noPermission = getMessage("AnvilInventories.RenameInventory.noPermission");

    public RenameAnvilInventory(@NotNull Pet pet, @NotNull Player playerOpened) {
        String title = getMessage("AnvilInventories.RenameInventory.title");
        assert pet.getPetName() != null;
        String petName = pet.getPetName();
        new AnvilGUI.Builder()
                .itemLeft(new ItemStack(Material.PAPER))
                .onComplete(((player, s) -> {
                    if (s != null) {
                        if (s.equals(MainCommand.noPetsString)) {
                            return AnvilGUI.Response.text(thisNameIsBlocked);
                        }
                        for (char character : s.toCharArray()) {
                            if (String.valueOf(character).equals(" ")) {
                                return AnvilGUI.Response.text(nameWithSpaces);
                            }
                        }
                        OpPets.getUtils().killPetFromPlayerUUID(player.getUniqueId());

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
                                player.sendMessage(changedName.replace("%new_pet_name%", FormatUtils.formatMessage(s)));
                            } else {
                                player.sendMessage(noPermission.replace("%permission%", "oppets.pet.rename.colors"));
                                return AnvilGUI.Response.close();
                            }
                        } else {
                            pet.setPetName(s);
                            player.sendMessage(changedName.replace("%new_pet_name%", s));
                        }

                        OpPets.getCreator().spawnMiniPet(pet, player);
                        List<Pet> petList = OpPets.getDatabase().getPetList(uuid);
                        petList.add(pet);
                        OpPets.getDatabase().setPets(uuid, petList);
                        return AnvilGUI.Response.close();
                    } else {
                        return AnvilGUI.Response.text(incorrectValueName);
                    }
                }))
                .text(petName)
                .title(title)
                .plugin(OpPets.getInstance())
                .open(playerOpened);
    }


}
