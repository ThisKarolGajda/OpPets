package me.opkarol.oppets.prestiges;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.jetbrains.annotations.NotNull;

/**
 * The type Random string.
 */
public class RandomString {

    /**
     * Gets string.
     *
     * @param length the length
     * @param mode   the mode
     * @return the string
     */
    public @NotNull String getString(int length, @NotNull Mode mode) {
        StringBuilder builder = new StringBuilder();
        String string;
        switch (mode) {
            case ALPHA: {
                string = "ABCDEFGHIJKLMNOPQRSTUVWXYZqwertyuiopasdfghjklzxcvbnm";
                break;
            }
            case NUMERIC: {
                string = "1234567890";
                break;
            }
            case ALPHANUMERIC: {
                string = "ABCDEFGHIJKLMNOPQRSTUVWXYZqwertyuiopasdfghjklzxcvbnm1234567890";
                break;
            }
            case CODES: {
                string = "0123456789abcdef";
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + mode);
            }
        }

        if (mode != Mode.CODES) {
            for (int i = 0; i < length; i++) {
                double index = Math.random() * string.length();
                builder.append(string.charAt((int) index));
            }
        } else {
            for (int i = 0; i < length; i++) {
                double index = Math.random() * string.length();
                builder.append("&").append(string.charAt((int) index));
            }
        }

        return builder.toString();
    }

    /**
     * The enum Mode.
     */
    public enum Mode {
        /**
         * Alpha mode.
         */
        ALPHA,
        /**
         * Numeric mode.
         */
        NUMERIC,
        /**
         * Alphanumeric mode.
         */
        ALPHANUMERIC,
        /**
         * Codes mode.
         */
        CODES
    }
}
