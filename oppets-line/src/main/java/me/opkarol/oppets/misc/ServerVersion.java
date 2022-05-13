package me.opkarol.oppets.misc;

import me.opkarol.oppets.annotations.MinecraftVersionSupported;
import me.opkarol.oppets.utils.MathUtils;
import org.jetbrains.annotations.NotNull;

public class ServerVersion {
    private static String currentVersionString;
    private static int currentVersion;
    private static int currentVersionLength;

    public static boolean canBeUsedNow(String version) {
        int versionInt = StringTransformer.getIntFromString(version);
        int versionLength = String.valueOf(versionInt).length();
        if (versionLength == currentVersionLength) {
            return currentVersion <= versionInt;
        } else {
            if (versionLength > currentVersionLength) {
                return currentVersion <= StringTransformer.getIntFromString(MathUtils.substringFromEnd(String.valueOf(versionInt), 1));
            } else {
                return StringTransformer.getIntFromString(MathUtils.substringFromEnd(String.valueOf(currentVersion), 1)) <= versionInt;
            }
        }
    }

    public static String getCurrentVersion() {
        return currentVersionString;
    }

    public static void setSeverVersion(String version) {
        currentVersionString = version;
        currentVersion = StringTransformer.getIntFromString(currentVersionString);
        currentVersionLength = String.valueOf(currentVersion).length();
    }

    public static boolean isCompatible(@NotNull Class<?> clazz) {
        if (clazz.isAnnotationPresent(MinecraftVersionSupported.class)) {
            MinecraftVersionSupported versionSupported = clazz.getAnnotation(MinecraftVersionSupported.class);
            String version = versionSupported.version();
            return canBeUsedNow(version);
        }
        return true;
    }
}
