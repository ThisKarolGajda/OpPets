package me.opkarol.oppets.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommandInterface {

    boolean execute(CommandSender sender, String[] args);

    String getPermission();

    List<String> getDescription();

    String getDescriptionAsString();

    String getSubCommand();

}
