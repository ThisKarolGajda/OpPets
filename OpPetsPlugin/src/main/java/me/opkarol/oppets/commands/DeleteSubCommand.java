package me.opkarol.oppets.commands;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.collections.commands.OpSubCommand;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.inventory.anvil.DeleteAnvilInventory;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.FormatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;
import static me.opkarol.oppets.utils.OpUtils.getPetByName;

public class DeleteSubCommand extends OpSubCommand {
    private final Database database = Database.getInstance();

    public DeleteSubCommand(String command, String permission) {
        super(command, permission);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, getMessages().getString("Commands.noConsole"));
        }
        if (args.length == 1) {
            UUID uuid = player.getUniqueId();
            Pet pet = database.getDatabase().getCurrentPet(uuid);
            if (pet == null) {
                return returnMessage(sender, getMessages().getString("Commands.invalidPet"));
            }
            new DeleteAnvilInventory(pet, player);
            return true;
        }
        if (args.length != 2) {
            return returnMessage(sender, getMessages().getString("Commands.badCommandUsage").replace("%proper_usage%", "/oppets delete <PET>"));
        }
        Pet pet = getPetByName(player.getUniqueId(), FormatUtils.getNameString(args[1]));
        if (pet == null) {
            return returnMessage(sender, getMessages().getString("Commands.invalidPet"));
        }
        new DeleteAnvilInventory(pet, player);
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 2) {
            return getPetList(((Player) sender).getUniqueId(), database);
        }
        return new ArrayList<>();
    }
}
