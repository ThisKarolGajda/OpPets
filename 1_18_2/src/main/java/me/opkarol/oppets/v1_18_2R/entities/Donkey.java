package me.opkarol.oppets.v1_18_2R.entities;


/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.pets.TypeOfEntity;
import me.opkarol.oppets.v1_18_2R.entities.common.AgeableEntity;
import net.minecraft.world.entity.EntityType;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Donkey extends AgeableEntity {
    public Donkey(@NotNull Player player) {
        super(EntityType.DONKEY, (((CraftWorld) player.getWorld())).getHandle());
    }

    @Override
    public Object getEntity() {
        return this;
    }

    @Override
    public Object getBukkitCraftEntity() {
        return getBukkitEntity();
    }

    @Override
    public TypeOfEntity getTypeOfEntity() {
        return TypeOfEntity.DONKEY;
    }

    @Override
    public int getEntityId() {
        return getId();
    }
}
