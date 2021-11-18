package me.opkarol.oppets.inventories;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.PetsUtils;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import static me.opkarol.oppets.utils.ConfigUtils.getMessage;

public class DeleteAnvilInventory {
    private final String title = getMessage("AnvilInventories.DeleteInventory.title");
    private final String deletedMessage = getMessage("AnvilInventories.DeleteInventory.deletedMessage");
    private final String confirmMessage = getMessage("AnvilInventories.DeleteInventory.confirmMessage");

    public DeleteAnvilInventory(@NotNull Pet pet, Player playerOpened) {
        new AnvilGUI.Builder()
                .onComplete((player, s) -> {
                    if (ChatColor.stripColor(s).equals(ChatColor.stripColor(pet.getPetName()))) {
                        OpPets.getDatabase().removePet(player.getUniqueId(), pet);
                        player.sendMessage(deletedMessage.replace("%pet_name%", pet.getPetName()));
                        PetsUtils.killPetFromPlayerUUID(player.getUniqueId());
                        return AnvilGUI.Response.close();
                    } else return AnvilGUI.Response.text(confirmMessage);
                })
                .plugin(OpPets.getInstance())
                .title(title.replace("%pet_name%", pet.getPetName()))
                .text("-")
                .itemLeft(new ItemStack(Material.WRITABLE_BOOK))
                .open(playerOpened);
    }
}
