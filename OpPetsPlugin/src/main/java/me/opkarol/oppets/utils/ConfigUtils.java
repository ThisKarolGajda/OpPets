package me.opkarol.oppets.utils;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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
