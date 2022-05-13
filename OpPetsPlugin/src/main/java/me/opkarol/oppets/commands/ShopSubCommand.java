package me.opkarol.oppets.commands;

import me.opkarol.oppets.cache.InventoryCache;
import me.opkarol.oppets.collections.commands.OpSubCommand;
import me.opkarol.oppets.shops.ShopCache;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class ShopSubCommand extends OpSubCommand {
    public ShopSubCommand(String command, String permission) {
        super(command, permission);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, getMessages().getString("Commands.noConsole"));
        }
        if (args.length != 1) {
            return returnMessage(sender, getMessages().getString("Commands.badCommandUsage").replace("%proper_usage%", "/oppets shop"));
        }
        InventoryCache cache = ShopCache.getCache(0);
        player.openInventory(cache.getInventory());
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return new ArrayList<>();
    }
}
