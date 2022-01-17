package me.opkarol.oppets.inventories;

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
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static me.opkarol.oppets.utils.ConfigUtils.getMessage;

public class DeleteAnvilInventory {
    private final String deletedMessage = getMessage("AnvilInventories.DeleteInventory.deletedMessage");
    private final String confirmMessage = getMessage("AnvilInventories.DeleteInventory.confirmMessage");

    public DeleteAnvilInventory(@NotNull Pet pet, Player playerOpened) {
        String title = getMessage("AnvilInventories.DeleteInventory.title");
        String petName = Objects.requireNonNull(pet.getPetName());
        new AnvilGUI.Builder()
                .onComplete((player, s) -> {
                    if (FormatUtils.getNameString(s).equals(FormatUtils.getNameString(petName))) {
                        OpPets.getDatabase().removePet(player.getUniqueId(), pet);
                        player.sendMessage(deletedMessage.replace("%pet_name%", petName));
                        OpPets.getUtils().killPetFromPlayerUUID(player.getUniqueId());
                        return AnvilGUI.Response.close();
                    } else return AnvilGUI.Response.text(confirmMessage);
                })
                .plugin(OpPets.getInstance())
                .title(title.replace("%pet_name%", petName))
                .text("=")
                .itemLeft(new ItemStack(Material.WRITABLE_BOOK))
                .open(playerOpened);
    }
}
