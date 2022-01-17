package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.inventories.ShopInventory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static me.opkarol.oppets.utils.FormatUtils.returnMessage;

public class ShopCommand implements SubCommandInterface {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return returnMessage(sender, "");
        }

        if (args.length != 1) {
            return returnMessage(sender, "");
        }

        player.openInventory(new ShopInventory().getInventory());
        return true;
    }

    @Override
    public String getPermission() {
        return "oppets.command.shop";
    }

    @Override
    public List<String> getDescription() {
        return null;
    }

    @Override
    public String getDescriptionAsString() {
        return null;
    }

    @Override
    public String getSubCommand() {
        return "shop";
    }
}
