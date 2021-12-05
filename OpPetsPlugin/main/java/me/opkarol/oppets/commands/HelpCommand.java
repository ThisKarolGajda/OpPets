package me.opkarol.oppets.commands;

import me.opkarol.oppets.OpPets;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class HelpCommand implements SubCommandInterface{
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, "");
        }

        player.openInventory(OpPets.getInventoryManager().getInventoryByIndex(0));
        return true;
    }

    @Override
    public String getPermission() {
        return "oppets.command.help";
    }

    @Override
    public List<String> getDescription() {
        return null;
    }

    @Override
    public String getDescriptionAsString() {
        return null;
    }

    @Override
    public String getSubCommand() {
        return "help";
    }
}
