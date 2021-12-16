package me.opkarol.oppets.utils;

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
}
