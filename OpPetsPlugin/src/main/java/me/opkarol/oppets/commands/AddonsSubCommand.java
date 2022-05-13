package me.opkarol.oppets.commands;

import me.opkarol.oppets.addons.AddonManager;
import me.opkarol.oppets.cache.InventoriesCache;
import me.opkarol.oppets.collections.commands.OpSubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class AddonsSubCommand extends OpSubCommand {
    public AddonsSubCommand(String command, String permission) {
        super(command, permission);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, getMessages().getString("Commands.noConsole"));
        }
        if (args.length == 2) {
            String addonString = args[1];
            Optional<String> iAddon = AddonManager.getAddon(addonString).map(addon -> InventoriesCache.addonsInventoryMessage.replace("%name%", addon.getName()).replace("%version%", addon.getVersion()).replace("%description%", String.join("", addon.getDescription()))).stream().findFirst();
            iAddon.ifPresentOrElse(player::sendMessage, () -> player.sendMessage("Can't find addon for name " + addonString + "."));
        } else {
            player.openInventory(AddonManager.getCache(0).getInventory());
        }
        return true;
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {
        List<String> result = new ArrayList<>();
        if (args[0].equalsIgnoreCase("addons")) {
            List<String> completions = AddonManager.getStringAddons();
            StringUtil.copyPartialMatches(args[1], completions, result);
        }
        return result;
    }
}
