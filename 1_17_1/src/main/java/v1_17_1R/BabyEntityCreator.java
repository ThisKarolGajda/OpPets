package v1_17_1R;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.interfaces.BabyEntityCreatorInterface;
import dir.databases.Database;
import dir.pets.Pet;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import v1_17_1R.entities.*;

public class BabyEntityCreator implements BabyEntityCreatorInterface {

    @Override
    public void spawnMiniPet(@NotNull Pet pet, @NotNull Player player) {
        pet.setOwnerUUID(player.getUniqueId());
        ServerLevel world = ((CraftWorld) player.getWorld()).getHandle();
        Animal entityAnimal = getPet(pet, player);
        entityAnimal.setCustomName(Component.nullToEmpty(pet.getPetName()));
        entityAnimal.setGlowingTag(pet.isGlowing());
        pet.setOwnUUID(entityAnimal.getUUID());
        Database.getDatabase().setCurrentPet(player.getUniqueId(), pet);
        world.addFreshEntity(entityAnimal);
        int id = entityAnimal.getId();
        Database.getDatabase().addIdPet(pet.getOwnUUID(), id);
        if (pet.isVisibleToOthers()) return;
        new Utils().hideEntityFromServer(player, id);
    }

    public Animal getPet(@NotNull Pet pet, Player player) {
        switch (pet.getPetType()) {
            case AXOLOTL: new Axolotl(player.getLocation(), player, pet);
            case CAT: new Cat(player.getLocation(), player, pet);
            case CHICKEN: new Chicken(player.getLocation(), player, pet);
            case COW: return new Cow(player.getLocation(), player, pet);
            case DONKEY: return new Donkey(player.getLocation(), player, pet);
            case FOX: return new Fox(player.getLocation(), player, pet);
            case GOAT: return new Goat(player.getLocation(), player, pet);
            case HORSE: return new Horse(player.getLocation(), player, pet);
            case LLAMA: new Llama(player.getLocation(), player, pet);
            case MULE: return new Mule(player.getLocation(), player, pet);
            case MUSHROOM_COW: return new MushroomCow(player.getLocation(), player, pet);
            case OCELOT: return new Ocelot(player.getLocation(), player, pet);
            case PANDA: return new Panda(player.getLocation(), player, pet);
            case PARROT: return new Parrot(player.getLocation(), player, pet);
            case PIG: return new Pig(player.getLocation(), player, pet);
            case POLAR_BEAR: return new PolarBear(player.getLocation(), player, pet);
            case RABBIT: return new Rabbit(player.getLocation(), player, pet);
            case SHEEP: return new Sheep(player.getLocation(), player, pet);
            case TURTLE: return new Turtle(player.getLocation(), player, pet);
            case WOLF: return new Wolf(player.getLocation(), player, pet);
            default: throw new IllegalStateException("Unexpected value: " + pet.getPetType());
        }
    }

}
