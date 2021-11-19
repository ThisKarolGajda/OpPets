package me.opkarol.oppets.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class HelpCommand implements SubCommandInterface{
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        return returnMessage(sender, "help");
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
