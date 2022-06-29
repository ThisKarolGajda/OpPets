package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.external.APIDatabase;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.api.files.MessagesHolder;
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
