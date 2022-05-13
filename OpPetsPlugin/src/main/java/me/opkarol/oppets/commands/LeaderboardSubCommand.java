package me.opkarol.oppets.commands;

import me.opkarol.oppets.cache.InventoryCache;
import me.opkarol.oppets.collections.commands.OpSubCommand;
import me.opkarol.oppets.leaderboards.LeaderboardCounter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class LeaderboardSubCommand extends OpSubCommand {
    public LeaderboardSubCommand(String command, String permission) {
        super(command, permission);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, getMessages().getString("Commands.noConsole"));
        }
        LeaderboardCounter leaderboard = getAPIDatabase().getLeaderboard();
        InventoryCache cache = leaderboard.getCache();
        player.openInventory(cache.getInventory());
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        return new ArrayList<>();
    }
}
