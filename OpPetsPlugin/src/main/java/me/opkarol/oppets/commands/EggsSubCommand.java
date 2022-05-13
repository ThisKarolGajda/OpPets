package me.opkarol.oppets.commands;

import me.opkarol.oppets.collections.commands.OpSubCommand;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.eggs.EggItem;
import me.opkarol.oppets.files.MessagesHolder;
import me.opkarol.oppets.inventory.OpInventories;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;
import static me.opkarol.oppets.utils.OpUtils.getEggItemFromString;

@Deprecated
public class EggsSubCommand extends OpSubCommand {
    private final Database database = Database.getInstance();
    private final MessagesHolder messages = MessagesHolder.getInstance();

    public EggsSubCommand(String command, String permission) {
        super(command, permission);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, getMessages().getString("Commands.noConsole"));
        }
        if (args.length == 2) {
            Optional<EggItem> optional = getEggItemFromString(args[1]);
            if (optional.isEmpty()) {
                return returnMessage(player, messages.getString("Commands.invalidObjectProvided"));
            }
            EggItem eggItem = optional.get();
            player.openInventory(new OpInventories.EggRecipesInventory(eggItem.getType(), eggItem.getRecipes(), 0).buildInventory());
        }
        if (args.length == 3) {
            Optional<EggItem> optional = getEggItemFromString(args[1]);
            if (optional.isEmpty()) {
                return returnMessage(player, messages.getString("Commands.invalidObjectProvided"));
            }
            EggItem eggItem = optional.get();
            //TODO select recipe
            player.openInventory(new OpInventories.EggRecipeInventory(eggItem.getRecipes().get(0), eggItem.getItem()).buildInventory());
            return true;
        }
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {
        if (args.length == 2) {
            return database.getOpPets().getEggManager().getTypes();
        }
        return new ArrayList<>();
    }
}
