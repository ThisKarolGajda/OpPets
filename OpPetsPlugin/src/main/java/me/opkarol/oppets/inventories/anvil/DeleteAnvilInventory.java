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
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class DeleteAnvilInventory {

    public DeleteAnvilInventory(@NotNull Pet pet, @NotNull Player playerOpened) {
        String title = Messages.stringMessage("titleDelete");
        String petName = FormatUtils.getNameString(Objects.requireNonNull(pet.getPetName()));
        UUID uuid = playerOpened.getUniqueId();
        final boolean[] b = {true};
        new AnvilGUI.Builder()
                .onComplete((player, s) -> {
                    if (b[0] && FormatUtils.getNameString(s).equals(FormatUtils.getNameString(petName))) {
                        b[0] = false;
                        Database.getOpPets().getDatabase().removePet(uuid, pet);
                        player.sendMessage(Messages.stringMessage("deletedMessage").replace("%pet_name%", petName));
                        return AnvilGUI.Response.close();
                    } else return AnvilGUI.Response.text(Messages.stringMessage("confirmMessage"));
                })
                .plugin(Database.getInstance())
                .title(title.replace("%pet_name%", petName))
                .text(String.valueOf(petName.charAt(0)))
                .itemLeft(new ItemStack(Material.WRITABLE_BOOK))
                .open(playerOpened);
    }
}
