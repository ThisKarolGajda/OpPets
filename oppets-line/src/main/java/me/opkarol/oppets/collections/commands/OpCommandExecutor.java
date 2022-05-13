package me.opkarol.oppets.collections.commands;

import me.opkarol.oppets.collections.OpMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class OpCommandExecutor {
    private final OpMap<String, OpSubCommand> subCommands;
    private final String name;
    private final OpCommand commandListener;
    private final Consumer<Player> defaultAction;

    public OpCommandExecutor(String name, OpMap<String, OpSubCommand> map, Consumer<Player> defaultAction) {
        this.name = name;
        this.subCommands = map;
        this.defaultAction = defaultAction;
        this.commandListener = new OpCommand(this);
        this.getCommandListener().register();
    }

    public OpSubCommand getSubCommand(String name) {
        Optional<OpSubCommand> optional = subCommands.getByKey(name);
        return optional.orElse(new OpSubCommand(name, null) {
            @Override
            public boolean onCommand(@NotNull CommandSender sender, @NotNull String @NotNull [] args) {
                return true;
            }

            @Override
            public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
                return null;
            }
        });
    }

    public Optional<OpSubCommand> findSubCommand(String name) {
        return subCommands.getByKey(name);
    }

    public OpMap<String, OpSubCommand> getSubCommands() {
        return subCommands;
    }

    public String getName() {
        return name;
    }

    public OpCommand getCommandListener() {
        return commandListener;
    }

    public Consumer<Player> getDefaultAction() {
        return defaultAction;
    }
}
