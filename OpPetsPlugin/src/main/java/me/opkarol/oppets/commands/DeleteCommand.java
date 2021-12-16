package me.opkarol.oppets.commands;

import dir.pets.Pet;
import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.inventories.DeleteAnvilInventory;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class DeleteCommand implements SubCommandInterface {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return returnMessage(sender, "");
        }
        Player player = (Player) sender;

        if (args.length != 2) {
            return returnMessage(sender, "");
        }

        Pet pet = getPetByName(player.getUniqueId(), args[1]);
        if (pet == null) {
            return returnMessage(sender, "");
        } else {
            new DeleteAnvilInventory(pet, player);
            return true;
        }
    }

    @Override
    public String getPermission() {
        return "oppets.command.delete";
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
        return "delete";
    }

    private Pet getPetByName(UUID playerUUID, String petName) {
        final Pet[] petI = new Pet[1];
        OpPets.getDatabase().getPetList(playerUUID).forEach(pet -> {
            if (Objects.equals(ChatColor.stripColor(pet.getPetName()), ChatColor.stripColor(petName))) {
                petI[0] = pet;
            }
        });
        return petI[0];
    }
}
