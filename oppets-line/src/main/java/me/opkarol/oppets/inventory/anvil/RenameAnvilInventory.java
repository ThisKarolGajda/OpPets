package me.opkarol.oppets.inventory.anvil;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.files.MessagesHolder;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.Utils;
import me.opkarol.oppets.utils.external.FormatUtils;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static me.opkarol.oppets.cache.NamespacedKeysCache.noPetsString;

public class RenameAnvilInventory {
    private final Database database = Database.getInstance();
    private final MessagesHolder messages = MessagesHolder.getInstance();

    public RenameAnvilInventory(@NotNull Pet pet, @NotNull Player playerOpened) {
        String title = messages.getString("RenameInventory.anvilTitle");
        String petName = pet.getPetName();
        new AnvilGUI.Builder()
                .itemLeft(new ItemStack(Material.PAPER))
                .onComplete(((player, str) -> {
                    if (str != null) {
                        UUID uuid = player.getUniqueId();
                        if (Utils.hasPetByName(uuid, str)) {
                            return AnvilGUI.Response.text(messages.getString("Commands.petWithSameName").replace("%pet_name%", str));
                        }
                        if (str.equals(noPetsString)) {
                            return AnvilGUI.Response.text(messages.getString("RenameInventory.thisNameIsBlocked"));
                        }
                        for (char character : str.toCharArray()) {
                            if (String.valueOf(character).equals(" ")) {
                                return AnvilGUI.Response.text(messages.getString("RenameInventory.nameWithSpaces"));
                            }
                        }
                        Utils.killPetFromPlayerUUID(player.getUniqueId());
                        database.getDatabase().removePet(uuid, pet);
                        if (Utils.isStringFormatted(str)) {
                            if (player.hasPermission("oppets.pet.rename.colors") || player.isOp()) {
                                pet.setPetName(str);
                                player.sendMessage(messages.getString("RenameInventory.changedName").replace("%new_pet_name%", FormatUtils.formatMessage(str)));
                            } else {
                                player.sendMessage(messages.getString("RenameInventory.noPermission").replace("%permission%", "oppets.pet.rename.colors"));
                                return AnvilGUI.Response.close();
                            }
                        } else {
                            pet.setPetName(str);
                            player.sendMessage(messages.getString("RenameInventory.changedName").replace("%new_pet_name%", str));
                        }
                        database.getDatabase().addPetToPetsList(uuid, pet);
                        Utils.summonPet(pet, player, true);
                        return AnvilGUI.Response.close();
                    } else {
                        return AnvilGUI.Response.text(messages.getString("RenameInventory.incorrectValueName"));
                    }
                }))
                .text(petName)
                .title(title)
                .plugin(database.getPlugin())
                .open(playerOpened);
    }


}
