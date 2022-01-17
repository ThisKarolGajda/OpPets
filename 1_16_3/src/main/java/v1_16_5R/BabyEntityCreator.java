package v1_16_5R;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import dir.databases.Database;
import dir.interfaces.BabyEntityCreatorInterface;
import dir.pets.Pet;
import net.minecraft.server.v1_16_R3.ChatMessage;
import net.minecraft.server.v1_16_R3.EntityAgeable;
import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import v1_16_5R.entities.*;

public class BabyEntityCreator implements BabyEntityCreatorInterface {

    @Override
    public void spawnMiniPet(@NotNull Pet pet, @NotNull Player player) {
        pet.setOwnerUUID(player.getUniqueId());
        WorldServer world = ((CraftWorld) player.getWorld()).getHandle();
        EntityAgeable entityAnimal = getPet(pet, player);
        assert pet.getPetName() != null;
        entityAnimal.setCustomName(new ChatMessage(ChatColor.translateAlternateColorCodes('&', pet.getPetName())));
        entityAnimal.glowing = pet.isGlowing();
        pet.setOwnUUID(entityAnimal.getUniqueID());
        Database.getDatabase().setCurrentPet(player.getUniqueId(), pet);
        world.addEntity(entityAnimal);
        int id = entityAnimal.getId();
        Database.getDatabase().addIdPet(pet.getOwnUUID(), id);
        if (pet.isVisibleToOthers()) return;
        new Utils().hideEntityFromServer(player, id);
    }

    public net.minecraft.server.v1_16_R3.EntityAgeable getPet(@NotNull Pet pet, Player player) {
        switch (pet.getPetType()) {
            case CAT: return new Cat(player.getLocation(), player, pet);
            case CHICKEN: return new Chicken(player.getLocation(), player, pet);
            case COW: return new Cow(player.getLocation(), player, pet);
            case DONKEY: return new Donkey(player.getLocation(), player, pet);
            case FOX: return new Fox(player.getLocation(), player, pet);
            case HORSE: return new Horse(player.getLocation(), player, pet);
            case LLAMA: return new Llama(player.getLocation(), player, pet);
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

