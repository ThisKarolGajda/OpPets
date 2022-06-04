package me.opkarol.oppets.commands;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.collections.map.OpMap;
import me.opkarol.oppets.inventory.OpInventories;

public class OpPetsCommandExecutor extends OpCommandExecutor {
    public OpPetsCommandExecutor(OpMap<String, OpSubCommand> map) {
        super("oppets", map, player -> player.openInventory(new OpInventories.PetMainInventory().buildInventory()));
    }

}
