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

/**
 * The type Sheep.
 */
public class Sheep extends net.minecraft.world.entity.animal.Sheep {

    /**
     * Instantiates a new Sheep.
     *
     * @param location the location
     * @param player   the player
     * @param pet      the pet
     */
    public Sheep(@NotNull Location location, @NotNull Player player, @NotNull Pet pet) {
        super(EntityType.SHEEP, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        new EntityManager().spawnEntity(this, player, pet);
    }

    /**
     * Register goals.
     */
    @Override
    public void registerGoals() {
    }

    /**
     * Drop experience.
     */
    @Override
    protected void dropExperience() {
    }

    /**
     * Drop from loot table.
     *
     * @param damagesource the damagesource
     * @param flag         the flag
     */
    @Override
    protected void dropFromLootTable(DamageSource damagesource, boolean flag) {
    }

    /**
     * Hurt boolean.
     *
     * @param damagesource the damagesource
     * @param f            the f
     * @return the boolean
     */
    @Override
    public boolean hurt(DamageSource damagesource, float f) {
        return false;
    }

}
