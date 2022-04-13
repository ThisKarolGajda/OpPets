package me.opkarol.oppets.misc;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * The type String transformer.
 */
public class StringTransformer {

    /**
     * Gets int from string.
     *
     * @param s the s
     * @return the int from string
     */
    public Integer getIntFromString(@NotNull String s) {
        return Optional.of(s.replaceAll("\\s+", ""))
                .map(Integer::valueOf).orElse(-1);
    }

    /**
     * Gets double from string.
     *
     * @param s the s
     * @return the double from string
     */
    public Double getDoubleFromString(@NotNull String s) {
        return Optional.of(s.replaceAll("\\s+", ""))
                .map(Double::parseDouble).orElse(-1D);
    }

    /**
     * Gets enum from string.
     *
     * @param <K> the type parameter
     * @param s   the s
     * @param e   the e
     * @return the enum from string
     * @throws IllegalArgumentException the illegal argument exception
     */
    public <K extends Enum<K>> Enum<K> getEnumFromString(String s, Class<K> e) throws IllegalArgumentException {
        Enum<K> anEnum;
        try {
            anEnum = Enum.valueOf(e, s);
        } catch (IllegalArgumentException ignore) {
            throw new IllegalArgumentException(String.format("Couldn't find value with name %s in Enum %s.", s, e.getName()));
        }
        if (anEnum.getClass().equals(e)) {
            return anEnum;
        }
        throw new IllegalArgumentException("Couldn't compare an enum class which was created against specific Enum.");
    }

    /**
     * Contains not numbers boolean.
     *
     * @param s the s
     * @return the boolean
     */
    @Deprecated
    public boolean containsNotNumbers(@NotNull String s) {
        for (char c : s.toCharArray()) {
            String cs = String.valueOf(c);
            for (int i = 0; i < 10; i++) {
                if (cs.contains(String.valueOf(i))) {
                    return true;
                }
            }
        }
        return false;
    }
}
