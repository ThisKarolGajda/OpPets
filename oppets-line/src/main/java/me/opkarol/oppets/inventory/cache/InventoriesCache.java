package me.opkarol.oppets.inventory.cache;

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.files.MessagesHolder;

public class InventoriesCache {
    private static final MessagesHolder holder = MessagesHolder.getInstance();
    public static String buyerAdmitInventoryTitle = holder.getString("BuyerAdmitInventory.title");
    public static String levelInventoryTitle = holder.getString("LevelInventory.title");
    public static String mainInventoryTitle = holder.getString("PetMainInventory.title");
    public static String prestigeInventoryTitle = holder.getString("PrestigeInventory.title");
    public static String settingsInventoryTitle = holder.getString("SettingsInventory.title");
    public static String leaderboardInventoryTitle = holder.getString("LeaderboardInventory.title");
    public static String guestInventoryTitle = holder.getString("GuestInventory.title");
    public static String shopInventoryTitle = holder.getString("ShopInventory.title");
    public static String addonsInventoryTitle = holder.getString("AddonsInventory.title");
    public static String addonsInventoryMessage = holder.getString("AddonsInventory.stringMessage");
    public static String summonInventoryTitle = holder.getString("SummonInventory.title");
    public static String eggRecipeInventoryTitle = holder.getString("EggRecipeInventory.title");
    public static String eggRecipesInventoryTitle = holder.getString("EggRecipesInventory.title");
    public static String preferencesInventoryTitle = holder.getString("PreferencesInventory.title");
}
