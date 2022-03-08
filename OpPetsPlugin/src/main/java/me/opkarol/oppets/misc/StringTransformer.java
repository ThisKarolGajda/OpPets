package me.opkarol.oppets.misc;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.jetbrains.annotations.NotNull;

public class StringTransformer {

    public Integer getIntFromString(@NotNull String s) {
        if (containsNotNumbers(s)) {
            return null;
        }
        return Integer.parseInt(s);
    }

    public Double getDoubleFromString(@NotNull String s) {
        if (containsNotNumbers(s)) {
            return null;
        }
        return Double.parseDouble(s);
    }

    public Enum getEnumFromString(String s, Class e) {
        return Enum.valueOf(e, s);

    }

    public boolean containsNotNumbers(@NotNull String s) {
        for (char c : s.toCharArray()) {
            String s1 = String.valueOf(c).replaceAll("[A-z]", "").replace("&", "").replace(",", "");
            if (s1.length() != 1) {
                return true;
            }
        }
        return false;
    }
}
