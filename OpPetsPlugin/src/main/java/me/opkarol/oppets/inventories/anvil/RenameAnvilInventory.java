package me.opkarol.oppets.inventories.anvil;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.commands.OpPetsCommand;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.FormatUtils;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * The type Rename anvil inventory.
 */
public class RenameAnvilInventory {
    /**
     * The Database.
     */
    private final Database database = Database.getInstance(OpPets.getInstance().getSessionIdentifier().getSession());

    /**
     * Instantiates a new Rename anvil inventory.
     *
     * @param pet          the pet
     * @param playerOpened the player opened
     */
    public RenameAnvilInventory(@NotNull Pet pet, @NotNull Player playerOpened) {
        String title = database.getOpPets().getMessages().getMessagesAccess().stringMessage("titleRename");
        assert pet.getPetName() != null;
        String petName = pet.getPetName();
        new AnvilGUI.Builder()
                .itemLeft(new ItemStack(Material.PAPER))
                .onComplete(((player, s) -> {
                    if (s != null) {
                        if (s.equals(OpPetsCommand.noPetsString)) {
                            return AnvilGUI.Response.text(database.getOpPets().getMessages().getMessagesAccess().stringMessage("thisNameIsBlocked"));
                        }
                        for (char character : s.toCharArray()) {
                            if (String.valueOf(character).equals(" ")) {
                                return AnvilGUI.Response.text(database.getOpPets().getMessages().getMessagesAccess().stringMessage("nameWithSpaces"));
                            }
                        }
                        database.getOpPets().getUtils().killPetFromPlayerUUID(player.getUniqueId());

                        /*
                            If formatted message (s) has not equal length to not-formatted one
                            it means that s contains color characters (&x),
                            in other case, which is default -> message has the same length to string
                            it goes through a normal process.
                        */
                        UUID uuid = player.getUniqueId();
                        database.getDatabase().removePet(uuid, pet);

                        if (FormatUtils.formatMessage(s).length() != s.length()) {
                            if (player.hasPermission("oppets.pet.rename.colors") || player.isOp()) {
                                pet.setPetName(s);
                                player.sendMessage(database.getOpPets().getMessages().getMessagesAccess().stringMessage("changedName").replace("%new_pet_name%", FormatUtils.formatMessage(s)));
                            } else {
                                player.sendMessage(database.getOpPets().getMessages().getMessagesAccess().stringMessage("noPermission").replace("%permission%", "oppets.pet.rename.colors"));
                                return AnvilGUI.Response.close();
                            }
                        } else {
                            pet.setPetName(s);
                            player.sendMessage(database.getOpPets().getMessages().getMessagesAccess().stringMessage("changedName").replace("%new_pet_name%", s));
                        }

                        database.getOpPets().getCreator().spawnMiniPet(pet, player);
                        List<Pet> petList = database.getDatabase().getPetList(uuid);
                        petList.add(pet);
                        database.getDatabase().setPets(uuid, petList);
                        return AnvilGUI.Response.close();
                    } else {
                        return AnvilGUI.Response.text(database.getOpPets().getMessages().getMessagesAccess().stringMessage("incorrectValueName"));
                    }
                }))
                .text(petName)
                .title(title)
                .plugin(database.getPlugin())
                .open(playerOpened);
    }


}
