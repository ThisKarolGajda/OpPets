package me.opkarol.oppets.commands;

import me.opkarol.oppets.OpPets;
import me.opkarol.oppets.collections.commands.OpSubCommand;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.inventory.anvil.RenameAnvilInventory;
import me.opkarol.oppets.pets.Pet;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;
import static me.opkarol.oppets.utils.OpUtils.getPetByName;

public class RenameSubCommand extends OpSubCommand {
    private final Database database = Database.getInstance();

    public RenameSubCommand(String command, String permission) {
        super(command, permission);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, getMessages().getString("Commands.noConsole"));
        }
        UUID uuid = player.getUniqueId();
        if (args.length == 1) {
            new RenameAnvilInventory(database.getDatabase().getCurrentPet(uuid), player);
            return true;
        }
        if (args.length == 2) {
            String petName = args[1];
            Pet pet = getPetByName(uuid, petName);
            if (pet == null) {
                return returnMessage(sender, getMessages().getString("Commands.invalidPet"));
            }
            new RenameAnvilInventory(pet, player);
            return true;
        }
        return returnMessage(sender, getMessages().getString("Commands.badCommandUsage").replace("%proper_usage%", "/oppets rename <PET>"));
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {
        if (args.length == 2) {
            return getPetList(((Player) sender).getUniqueId(), database);
        }
        return new ArrayList<>();
    }
}
