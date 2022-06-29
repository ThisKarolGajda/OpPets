package me.opkarol.oppets.cache;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.external.APIDatabase;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

public class NamespacedKeysCache {
    private static final Plugin plugin = APIDatabase.getInstance().getPlugin();
    public static NamespacedKey typeKey = new NamespacedKey(plugin, "oppets-shop-type");
    public static NamespacedKey priceKey = new NamespacedKey(plugin, "oppets-shop-price");
    public static NamespacedKey petKey = new NamespacedKey(plugin, "oppets-entity-key");
    public static NamespacedKey summonItemKey = new NamespacedKey(plugin, "oppets-summon-item-key");
    public static String noPetsString = "<NO-PETS>";
}
