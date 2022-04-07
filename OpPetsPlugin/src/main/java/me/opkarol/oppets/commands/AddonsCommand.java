package me.opkarol.oppets.commands;

import me.opkarol.oppets.addons.AddonManager;
import me.opkarol.oppets.cache.InventoriesCache;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.interfaces.ICommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class AddonsCommand implements ICommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("noConsole"));
        }
        if (args.length == 2) {
            String addonString = args[1];
            Optional<String> iAddon = AddonManager.getAddon(addonString).map(addon -> InventoriesCache.addonsInventoryMessage.replace("%name%", addon.getName()).replace("%version%", addon.getVersion()).replace("%description%", String.join("", addon.getDescription()))).stream().findFirst();
            iAddon.ifPresentOrElse(player::sendMessage, () -> player.sendMessage("Can't find addon for name " + addonString + "."));
        } else {
            player.openInventory(AddonManager.getCachedInventory());
        }
        return true;
    }

    @Override
    public String getPermission() {
        return "oppets.command.addons";
    }

    @Override
    public String getSubCommand() {
        return "addons";
    }
}
