package me.opkarol.oppets.api.entities.manager;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.map.OpMap;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.api.entities.IEntityPet;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.TypeOfEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.UUID;

public abstract class EntityManager implements IEntityManager {
    public OpMap<TypeOfEntity, Constructor<?>> getMap() {
        return map;
    }

    private OpMap<TypeOfEntity, Constructor<?>> map;

    public void setMap(OpMap<TypeOfEntity, Constructor<?>> map) {
        this.map = map;
    }

    public Optional<IEntityPet> getPetFromType(Player player, @NotNull Pet pet) {
        TypeOfEntity type = pet.getPetType();
        Optional<Constructor<?>> optional = getMap().getByKey(type);
        if (!optional.isPresent()) {
            return Optional.empty();
        }
        IEntityPet iPet = null;
        Constructor<?> constructor = optional.get();
        try {
            iPet = (IEntityPet) constructor.newInstance(player);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(iPet);
    }

    public void setPrivate(@NotNull IEntityPet iPet, @NotNull Pet pet, Player player, @NotNull Database database) {
        int id = iPet.getEntityId();
        database.getDatabase().addIdPet(pet.petUUID.getOwnUUID(), id);
        if (!pet.settings.isVisibleToOthers()) {
            database.getUtils().hideEntityFromServer(player, id);
        }
    }

    public void setPetIDInDatabase(@NotNull Player player, UUID uuid, @NotNull Pet pet, @NotNull Database database) {
        pet.petUUID.setOwnUUID(uuid);
        database.getDatabase().setCurrentPet(player.getUniqueId(), pet);
    }

    public abstract void setAge(IEntityPet iPet, Pet pet);
}
