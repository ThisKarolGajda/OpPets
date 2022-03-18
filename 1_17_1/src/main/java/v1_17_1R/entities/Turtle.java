package v1_17_1R.entities;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.pets.Pet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Turtle extends net.minecraft.world.entity.animal.Turtle {
    public Turtle(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityType.TURTLE, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        new EntityManager().spawnEntity(this, player, pet);
    }

    @Override
    public void registerGoals() {
    }

    @Override
    protected void dropExperience() {
    }

    @Override
    protected void dropFromLootTable(DamageSource damagesource, boolean flag) {
    }

    @Override
    public boolean hurt(DamageSource damagesource, float f) {
        return false;
    }

}
