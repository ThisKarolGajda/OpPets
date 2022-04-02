package me.opkarol.oppets.utils;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * The type Config utils.
 */
public class ConfigUtils {
    /**
     * The constant instance.
     */
    private static final Plugin instance = Database.getInstance();

    /**
     * Gets message.
     *
     * @param path the path
     * @return the message
     */
    @Contract("_ -> new")
    public static @NotNull String getMessage(String path) {
        return FormatUtils.formatMessage(instance.getConfig().getString(path));
    }

    /**
     * Gets int.
     *
     * @param path the path
     * @return the int
     */
    public static int getInt(String path) {
        return instance.getConfig().getInt(path);
    }

    /**
     * Gets string.
     *
     * @param path the path
     * @return the string
     */
    public static String getString(String path) {
        return instance.getConfig().getString(path);
    }

}
