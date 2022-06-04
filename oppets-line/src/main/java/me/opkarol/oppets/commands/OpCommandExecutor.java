package me.opkarol.oppets.commands;

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.collections.map.OpMap;
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
