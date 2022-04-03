package me.opkarol.oppets.commands;

import me.opkarol.oppets.addons.AddonManager;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.interfaces.ICommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class AddonsCommand implements ICommand {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, Database.getOpPets().getMessages().getMessagesAccess().stringMessage("noConsole"));
        }
        player.openInventory(AddonManager.getCachedInventory());
        return false;
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
