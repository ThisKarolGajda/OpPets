package me.opkarol.oppets.api.misc;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class StringTransformer {

    public static Integer getIntFromString(String s) {
        if (s != null) {
            return Optional.of(s.replaceAll("\\s+", "")
                            .replaceAll("[^-0-9]", ""))
                    .filter(s1 -> s1.length() > 0)
                    .map(Integer::valueOf).orElse(-1);
        }
        return -1;
    }

    public static Double getDoubleFromString(String s) {
        if (s != null) {
            return Optional.of(s.replaceAll("\\s+", "")
                            .replaceAll("[^-.,0-9]", ""))
                    .filter(s1 -> s1.length() > 0)
                    .map(Double::parseDouble).orElse(-1D);
        }
        return -1D;
    }

    public static <K extends Enum<K>> boolean containsEnumFromString(String s, Class<K> e) throws IllegalArgumentException {
        Enum<K> anEnum;
        try {
            anEnum = Enum.valueOf(e, s);
        } catch (IllegalArgumentException ignore) {
            return false;
        }
        return anEnum.getClass().equals(e);
    }

    public static <K extends Enum<K>> Optional<K> getEnumValue(String s, Class<K> e) {
        if (!StringTransformer.containsEnumFromString(s, e)) {
            return Optional.empty();
        }
        return Optional.of(Enum.valueOf(e, s));
    }

    public static Material getMaterialFromString(String s) {
        if (s != null) {
            Material material;
            try {
                 material = Material.valueOf(s);
            } catch (IllegalArgumentException ignore) {
                return Material.STONE;
            }
            return material;
        }
        return Material.STONE;
    }

    public static boolean getBooleanFromObject(Object obj) {
        if (obj != null) {
            try {
                return (boolean) obj;
            } catch (ClassCastException ignore) {
                if (obj instanceof String) {
                    String s = (String) obj;
                    return Objects.equals(s, "true");
                }
            }
        }
        return false;
    }

    public static boolean isBoolean(Object obj) {
        if (obj == null) {
            return false;
        }
        try {
            return (boolean) obj;
        } catch (ClassCastException ignore) {
            if (obj instanceof String) {
                String s = ((String) obj).toLowerCase();
                return Objects.equals(s, "true") || Objects.equals(s, "false");
            }
        }
        return false;
    }

    public static @NotNull List<String> getListStringsFromObject(Object obj) {
        List<String> defaultList = new ArrayList<>();
        if (obj != null) {
            if (obj instanceof JSONArray) {
                for (Object object : (JSONArray) obj) {
                    if (object instanceof String) {
                        defaultList.add((String) object);
                    }
                }
            } else if (obj instanceof List<?>) {
                for (Object object : (List<?>) obj) {
                    if (object instanceof String) {
                        defaultList.add((String) object);
                    }
                }
            }
        }
        return defaultList;
    }
}
