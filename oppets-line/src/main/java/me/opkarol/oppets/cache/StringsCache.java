package me.opkarol.oppets.cache;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import static me.opkarol.oppets.utils.ConfigUtils.getString;

/**
 * The type Strings cache.
 */
public class StringsCache {
    /**
     * The constant boosterEnabledMessage.
     */
    public static String boosterEnabledMessage = getString("Formats.boosterEnabledMessage");
    /**
     * The constant defaultServerValue.
     */
    public static String defaultServerValue = "SERVER";
    /**
     * The constant broadcastFormatMessage.
     */
    public static String broadcastFormatMessage = getString("Formats.broadcastFormat");
}
