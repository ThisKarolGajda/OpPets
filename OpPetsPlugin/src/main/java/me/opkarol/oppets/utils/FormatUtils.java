package me.opkarol.oppets.utils;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FormatUtils {

    @Contract("_ -> new")
    public static @NotNull String formatMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static @NotNull List<String> formatList(@NotNull List<String> list) {
        List<String> current = new ArrayList<>();
        list.forEach(s -> current.add(formatMessage(s)));
        return current;
    }

    public static boolean returnMessage(@NotNull CommandSender sender, String message) {
        sender.sendMessage(message);
        return true;
    }

    //TODO
    @Contract(pure = true)
    public static @NotNull String getStringFromFormattedMessage(@NotNull String message) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : message.toCharArray()) {
            stringBuilder.append(ChatColor.stripColor(String.valueOf(c)));
        }
        return stringBuilder.toString();
    }

    public static String getNameString(String string) {
        return ChatColor.stripColor(formatMessage(string));
    }
}
