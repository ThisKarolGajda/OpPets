package me.opkarol.oppets.utils;

import me.opkarol.oppets.OpPets;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConfigUtils {
    private static final OpPets opPets = OpPets.getInstance();

    @Contract("_ -> new")
    public static @NotNull String getMessage(String path) {
        return FormatUtils.formatMessage(opPets.getConfig().getString(path));
    }

    public static boolean getBoolean(String path) {
        return opPets.getConfig().getBoolean(path);
    }

    public static int getInt(String path) {
        return opPets.getConfig().getInt(path);
    }

    public static float getFloat(String path) {
        return (float) opPets.getConfig().get(path);
    }

    public static double getDouble(String path) {
        return opPets.getConfig().getDouble(path);
    }

    public static String getString(String path) {
        return opPets.getConfig().getString(path);
    }

    public static @NotNull List<String> getListString(String path) {
        return FormatUtils.formatList(opPets.getConfig().getStringList(path));
    }
}
