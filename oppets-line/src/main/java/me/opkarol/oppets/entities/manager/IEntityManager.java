package me.opkarol.oppets.entities.manager;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.collections.OpMap;
import me.opkarol.oppets.entities.IEntityPet;
import me.opkarol.oppets.misc.ServerVersion;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.TypeOfEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.Optional;
import java.util.stream.Stream;

public interface IEntityManager {
    void initPathfinder(@NotNull Object pathfinderMob, TypeOfEntity type);

    void spawnEntity(@NotNull Object entity, @NotNull Object player, @NotNull Object pet);

    Optional<IEntityPet> spawnEntity(Player player, @NotNull Pet pet);

    Stream<String> getAllowedEntities();

    OpMap<TypeOfEntity, Constructor<?>> getMap();

    default OpMap<TypeOfEntity, Constructor<?>> setupMap() {
        OpMap<TypeOfEntity, Constructor<?>> map = new OpMap<>();
        for (TypeOfEntity type : TypeOfEntity.values()) {
            String name = type.getName();
            if (name != null) {
                try {
                    Class<?> clazz = Class.forName("me.opkarol.oppets." + ServerVersion.getCurrentVersion() + ".entities." + name);
                    if (ServerVersion.isCompatible(clazz)) {
                        Constructor<?> constructor = clazz.getConstructor(Player.class);
                        map.put(type, constructor);
                    }
                } catch (ClassNotFoundException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
}
