package me.opkarol.oppets.commands;

import dir.databases.Database;
import dir.pets.Pet;
import me.opkarol.oppets.inventories.RenameAnvilInventory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class RenameCommand implements SubCommandInterface{
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, "");
        }

        if (args.length == 1) {
            new RenameAnvilInventory(Database.getDatabase().getCurrentPet(player.getUniqueId()), player);
            return true;
        } else if (args.length == 2) {
            String petName = args[1];
            for (Pet pet : Database.getDatabase().getPetList(player.getUniqueId())) {
                assert pet.getPetName() != null;
                if (pet.getPetName().equals(petName)) {
                    new RenameAnvilInventory(pet, player);
                }
            }
            return true;
        }

        return returnMessage(sender, "");
    }

    @Override
    public String getPermission() {
        return null;
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
        return null;
    }
}
