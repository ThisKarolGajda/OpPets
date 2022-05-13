package me.opkarol.oppets.collections.commands;

import me.opkarol.oppets.databases.APIDatabase;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.files.MessagesHolder;
import me.opkarol.oppets.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static me.opkarol.oppets.cache.NamespacedKeysCache.noPetsString;

public abstract class OpSubCommand {
    private final String permission, command;
    private final MessagesHolder messages = MessagesHolder.getInstance();
    private final APIDatabase apiDatabase = APIDatabase.getInstance();

    public OpSubCommand(String command, String permission) {
        this.permission = permission;
        this.command = command;
    }

    public final String getCommand() {
        return this.command;
    }

    public final String getPermission() {
        return this.permission;
    }

    public MessagesHolder getMessages() {
        return messages;
    }

    public abstract boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args);

    public abstract List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args);

    public APIDatabase getAPIDatabase() {
        return apiDatabase;
    }

    public List<String> getPetList(UUID uuid, @NotNull Database database) {
        List<Pet> petList = database.getDatabase().getPetList(uuid);
        if (petList == null || petList.size() == 0) {
            return Collections.singletonList(noPetsString);
        }
        return petList.stream().map(Pet::getPetName).collect(Collectors.toList());
    }

    public List<String> getPlayerList() {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }
}
