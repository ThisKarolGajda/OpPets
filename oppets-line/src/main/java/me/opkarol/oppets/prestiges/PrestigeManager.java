package me.opkarol.oppets.prestiges;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PrestigeManager {
    public String format;

    public PrestigeManager() {
        FileConfiguration config = Database.getInstance().getConfig();
        format = config.getString("Prestiges.format");
        if (!config.getBoolean("Prestiges.enabled")) {
            format = null;
        }
    }

    @Contract(pure = true)
    public @NotNull String getFormatForNumber(int prestigeLevel) {
        if (format == null) return "";
        StringBuilder builder = new StringBuilder();
        builder.append(prestigeLevel).append(";");
        for (char ignore : String.valueOf(prestigeLevel).toCharArray()) {
            builder.append(new RandomString().getString(1, RandomString.Mode.CODES)).append("-");
        }
        return builder.toString();
    }

    public int getPrestigeLevel(@NotNull String prestigeString) {
        String[] strings = prestigeString.split(";");
        return Integer.parseInt(strings[0]);
    }

    public String getPrestigeCodes(@NotNull String prestigeString) {
        String[] strings = prestigeString.split(";");
        return strings[1];
    }

    private @NotNull String formatString(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public String getFilledPrestige(String prestige) {
        String prestigeLevel = String.valueOf(getPrestigeLevel(prestige));
        String prestigeCode = getPrestigeCodes(prestige);
        String[] codes = prestigeCode.split("-");

        if (prestigeLevel.length() != codes.length) {
            return prestige;
        }

        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (char c : prestigeLevel.toCharArray()) {
            builder.append(codes[i]).append(c);
            i++;
        }

        return formatString(format.replace("%prestige%", formatString(builder.toString())));
    }

}
