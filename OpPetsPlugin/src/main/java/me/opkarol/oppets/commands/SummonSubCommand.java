package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.inventory.OpInventories;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.PetsUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.opkarol.oppets.cache.NamespacedKeysCache.noPetsString;
import static me.opkarol.oppets.utils.external.FormatUtils.returnMessage;

public class SummonSubCommand extends OpSubCommand {
    private final Database database = Database.getInstance();
    
    public SummonSubCommand(String command, String permission) {
        super(command, permission);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, getMessages().getString("Commands.noConsole"));
        }
        UUID playerUUID = player.getUniqueId();
        if (args.length == 1) {
            player.openInventory(new OpInventories.SummonInventory(playerUUID, 0).buildInventory());
            return true;
        }
        if (args.length != 2) {
            return returnMessage(sender, getMessages().getString("Commands.badCommandUsage").replace("%proper_usage%", "/oppets summon <PET>"));
        }
        if (args[1].equals(noPetsString)) {
            return returnMessage(sender, getMessages().getString("Commands.petBlockedName").replace("%blocked_word%", noPetsString));
        }
        List<Pet> playerPets = database.getDatabase().getPetList(playerUUID);
        if (playerPets == null || playerPets.size() == 0) {
            return returnMessage(sender, getMessages().getString("Commands.petListEmpty"));
        }
        if (PetsUtils.summonPet(args[1], player)) {
            return true;
        }
        return returnMessage(sender, getMessages().getString("Commands.invalidPet"));
    }

    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String[] args) {
        if (args.length == 2) {
            return getPetList(((Player) sender).getUniqueId(), database);
        }
        return new ArrayList<>();
    }
}
