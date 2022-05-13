package me.opkarol.oppets.commands;

import me.opkarol.oppets.collections.commands.OpSubCommand;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.inventory.OpInventories;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.PetsUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.opkarol.oppets.cache.NamespacedKeysCache.noPetsString;
import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class SummonSubCommand extends OpSubCommand {
    private final Database database = Database.getInstance();
    
    public SummonSubCommand(String command, String permission) {
        super(command, permission);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, getMessages().getString("Commands.noConsole"));
        }
        UUID playerUUID = player.getUniqueId();
        if (args.length == 1) {
            player.openInventory(new OpInventories.SummonInventory(playerUUID, 0).buildInventory());
            return true;
        }
        if (args.length != 2) {
            return returnMessage(sender, getMessages().getString("Commands.badCommandUsage").replace("%proper_usage%", "/oppets summon <PET>"));
        }
        if (args[1].equals(noPetsString)) {
            return returnMessage(sender, getMessages().getString("Commands.petBlockedName").replace("%blocked_word%", noPetsString));
        }
        List<Pet> playerPets = database.getDatabase().getPetList(playerUUID);
        if (playerPets == null || playerPets.size() == 0) {
            return returnMessage(sender, getMessages().getString("Commands.petListEmpty"));
        }
        if (PetsUtils.summonPet(args[1], player)) {
            return true;
        }
        return returnMessage(sender, getMessages().getString("Commands.invalidPet"));
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 2) {
            return getPetList(((Player) sender).getUniqueId(), database);
        }
        return new ArrayList<>();
    }
}
