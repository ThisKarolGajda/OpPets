package me.opkarol.oppets.inventories;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.commands.MainCommand;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.FormatUtils;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static me.opkarol.oppets.utils.ConfigUtils.getMessage;

public class RenameAnvilInventory {
    private final String thisNameIsBlocked = getMessage("AnvilInventories.RenameInventory.thisNameIsBlocked");
    private final String nameWithSpaces = getMessage("AnvilInventories.RenameInventory.nameWithSpaces");
    private final String changedName = getMessage("AnvilInventories.RenameInventory.changedName");
    private final String incorrectValueName = getMessage("AnvilInventories.RenameInventory.incorrectValueName");

    public RenameAnvilInventory(@NotNull Pet pet, Player playerOpened){
        String title = getMessage("AnvilInventories.RenameInventory.title");
        new AnvilGUI.Builder()
                .itemLeft(new ItemStack(Material.PAPER))
                .preventClose()
                .onComplete(((player, s) -> {
                    if(s != null) {
                        if (s.equals(MainCommand.noPetsString)){
                            return AnvilGUI.Response.text(thisNameIsBlocked);
                        }
                        for (char character : s.toCharArray()){
                            if (String.valueOf(character).equals(" ")){
                                return AnvilGUI.Response.text(nameWithSpaces);
                            }
                        }
                        OpPets.getUtils().killPetFromPlayerUUID(player.getUniqueId());
                        if (player.hasPermission("oppets.pet.rename.colors")) {
                            pet.setPetName(FormatUtils.formatMessage(s));
                            player.sendMessage(changedName.replace("%new_pet_name%", FormatUtils.formatMessage(s)));
                        } else {
                            pet.setPetName(s);
                            player.sendMessage(changedName.replace("%new_pet_name%", s));
                        }
                        OpPets.getCreator().spawnMiniPet(pet, player);
                        return AnvilGUI.Response.close();
                    } else {
                        return AnvilGUI.Response.text(incorrectValueName);
                    }
                }))
                .text(Objects.requireNonNull(pet.getPetName()))
                .title(title)
                .plugin(OpPets.getInstance())
                .open(playerOpened);
    }



}
