package me.opkarol.oppets.v1_18_2R.entities.common;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.entities.IAgeablePet;
import me.opkarol.oppets.api.entities.IEntityPet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public abstract class AgeableEntity extends AgeableMob implements IAgeablePet, IEntityPet {
    protected AgeableEntity(EntityType<? extends AgeableMob> entitytypes, Level world) {
        super(entitytypes, world);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
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
    public int getEntityId() {
        return getId();
    }

}
