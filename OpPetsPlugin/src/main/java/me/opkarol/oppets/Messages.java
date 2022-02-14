package me.opkarol.oppets;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.utils.ConfigUtils;
import me.opkarol.oppets.utils.FormatUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Messages extends ConfigUtils {

    private static final HashMap<String, String> map = new HashMap<>();
    private String path;

    /**
     * Returns a formatted message which is taken from hash map, which values were saved at the onEnable method.
     *
     * @param name string object from which map will try to get a result
     * @return formatted string message / basic message
     */
    public static @NotNull String stringMessage(String name) {
        if (map.containsKey(name)) return FormatUtils.formatMessage(map.get(name));
        try {
            throw new Exception("Database error - cannot find type: " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    public Messages onEnable() {
        path = "Commands.";
        setValue("badCommandUsage");
        setValue("noPermission");
        setValue("noConsole");
        setValue("petWithSameName");
        setValue("wrongType");
        setValue("createdPet");
        setValue("invalidPet");
        setValue("petIsntGiftable");
        setValue("petIsntRideable");
        setValue("receiverSameNamedPet");
        setValue("receiverInvalid");
        setValue("petGifted");
        setValue("petBlockedName");
        setValue("petListEmpty");
        setValue("samePet");
        setValue("summonedPet");
        setValue("cantPrestige");
        setValue("changedAdminValue");
        path = "AnvilInventories.RenameInventory.";
        setValue("titleRename");
        setValue("thisNameIsBlocked");
        setValue("nameWithSpaces");
        setValue("changedName");
        setValue("incorrectValueName");
        path = "AnvilInventories.DeleteInventory.";
        setValue("titleDelete");
        setValue("deletedMessage");
        setValue("confirmMessage");
        path = "AnvilInventories.PrestigeInventory.";
        setValue("titlePrestige");
        setValue("confirmPrestigeMessage");
        path = "Messages.";
        setValue("petLevelUpMessage");
        setValue("prestigeUpMessage");
        return this;
    }

    private void setValue(String value) {
        map.put(value, getString(path + value));
    }

}
