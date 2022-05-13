package me.opkarol.oppets.commands;

import me.opkarol.oppets.collections.commands.OpSubCommand;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.inventory.OpInventories;
import me.opkarol.oppets.pets.Pet;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class PreferencesSubCommand extends OpSubCommand {
    private final Database database = Database.getInstance();

    public PreferencesSubCommand(String command, String permission) {
        super(command, permission);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, getMessages().getString("Commands.noConsole"));
        }
        UUID playerUUID = player.getUniqueId();
        if (args.length == 1) {
            Pet pet = database.getDatabase().getCurrentPet(playerUUID);
            if (pet == null) {
                return returnMessage(sender, getMessages().getString("Commands.invalidPet"));
            }
            player.openInventory(new OpInventories.PreferencesInventory(pet).buildInventory());
            return true;
        }
        return returnMessage(sender, getMessages().getString("Commands.badCommandUsage").replace("%proper_usage%", "/oppets preference"));
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return new ArrayList<>();
    }
}
