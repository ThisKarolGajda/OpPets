package me.opkarol.oppets.commands.builder;

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

import me.opkarol.oppets.api.map.OpMap;
import me.opkarol.oppets.commands.OpSubCommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class OpCommandBuilder {
    private final OpMap<String, OpSubCommand> map = new OpMap<>();

    public OpCommandBuilder addKey(@NotNull String command) {
        String lCommand = command.toLowerCase();
        OpSubCommand subCommand = null;
        try {
            subCommand = (OpSubCommand) Class.forName("me.opkarol.oppets.commands." + command + "SubCommand").getDeclaredConstructor(String.class, String.class).newInstance(lCommand, "oppets.command." + lCommand);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        map.put(lCommand, subCommand);
        return this;
    }

    public OpCommandBuilder addDummyKey(@NotNull String command, BiConsumer<CommandSender, String[]> consumer) {
        String lCommand = command.toLowerCase();
        OpSubCommand command1 = new OpSubCommand(command,  "oppets.command." + lCommand) {
            @Override
            public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
                consumer.accept(sender, args);
                return false;
            }

            @Contract(value = "_, _ -> new", pure = true)
            @Override
            public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
                return new ArrayList<>();
            }
        };
        map.put(lCommand, command1);
        return this;
    }

    public OpCommandBuilder removeKey(String key) {
        map.remove(key);
        return this;
    }

    public OpCommandBuilder dump() {
        return new OpCommandBuilder();
    }

    public OpMap<String, OpSubCommand> getMap() {
        return map;
    }
}
