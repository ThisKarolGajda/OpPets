package me.opkarol.oppets.pets.converter;

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.collections.map.OpMap;
import me.opkarol.oppets.misc.StringTransformer;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.TypeOfEntity;
import me.opkarol.oppets.storage.builder.OpObjectCreator;
import me.opkarol.oppets.storage.builder.OpObjectTypes;
import me.opkarol.oppets.storage.OpObjects;
import me.opkarol.oppets.pets.id.UniquePet;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.UUID;

public class PetsConverter implements IPetsConverter, Serializable {
    public OpObjects getObjectFromPet(@NotNull Pet pet) {
        return new OpObjects().addObject(new OpObjectCreator("name", OpObjectTypes.STRING, pet.getPetName()).getObject())
                .addObject(new OpObjectCreator("type", OpObjectTypes.STRING, pet.getPetType().name()).getObject())
                .addObject(new OpObjectCreator("settings", OpObjectTypes.STRING, pet.settings.toString()).getObject())
                .addObject(new OpObjectCreator("preferences", OpObjectTypes.STRING, pet.preferences.getPreferences().toString()).getObject())
                .addObject(new OpObjectCreator("prestige", OpObjectTypes.STRING, pet.getPrestige()).getObject())
                .addObject(new OpObjectCreator("skill", OpObjectTypes.STRING, pet.getSkillName()).getObject())
                .addObject(new OpObjectCreator("experience", OpObjectTypes.DOUBLE, pet.getPetExperience()).getObject())
                .addObject(new OpObjectCreator("level", OpObjectTypes.INT, pet.getLevel()).getObject())
                .addObject(new OpObjectCreator("ownID", OpObjectTypes.CUSTOM, pet.petUUID.getOwnUUID()))
                .addObject(new OpObjectCreator("ownerID", OpObjectTypes.CUSTOM, pet.petUUID.getOwnerUUID()))
                .addObject(new OpObjectCreator("petUUID", OpObjectTypes.CUSTOM, pet.petUUID))
                .addObject(new OpObjectCreator("active", OpObjectTypes.BOOL, pet.isActive()).getObject())
                .get();
    }

    @Contract("_ -> new")
    public @NotNull Pet getPetFromObject(@NotNull OpObjects object) {
        String name = object.getString("name", null), settings = object.getString("settings", null), preferences = object.getString("preferences", null), skill = object.getString("skill", null), prestige = String.valueOf(object.getString("prestige", null));
        double experience = object.getDouble("experience", -1D);
        int level = Integer.parseInt(String.valueOf(object.getInteger("level", -1)));
        final TypeOfEntity[] type = new TypeOfEntity[1];
        StringTransformer.getEnumValue(object.getString("type", null), TypeOfEntity.class).ifPresent(typeOfEntity -> type[0] = typeOfEntity);
        boolean active = object.getBoolean("active", false);
        UUID ownerUUID = UUID.fromString(String.valueOf(object.getObject("ownerID").getValue()));
        int databaseId = Integer.parseInt(String.valueOf(object.getObject("id").getValue()));
        UniquePet petUUID = new UniquePet(databaseId, null, ownerUUID);
        return new Pet(name, experience, level, type[0], active, null, ownerUUID, skill, prestige, petUUID, OpObjects.get(preferences), OpObjects.get(settings));
    }

    public @NotNull OpObjects createPetPreferences(@NotNull Pet pet) {
        OpObjects objects = new OpObjects();
        TypeOfEntity type = pet.getPetType();
        for (TypeOfEntity.VARIANTS variant : type.getVariants()) {
            objects.addObject(new OpObjectCreator(variant.name().toLowerCase(), OpObjectTypes.BOOL, false).getObject());
        }
        return objects;
    }

    public @NotNull OpMap<TypeOfEntity.VARIANTS, Boolean> readPetPreferences(@NotNull Pet pet) {
        OpMap<TypeOfEntity.VARIANTS, Boolean> map = new OpMap<>();
        OpObjects objects = pet.preferences.getPreferences();
        TypeOfEntity type = pet.getPetType();
        for (TypeOfEntity.VARIANTS variant : type.getVariants()) {
            objects.hasObjectAction(variant.name().toLowerCase(), opObject -> map.put(variant, (boolean) opObject.getValue()));
        }
        return map;
    }

    public boolean readPetPreference(@NotNull Pet pet, String preference) {
        return readPetObject(pet.preferences.getPreferences(), preference);
    }

    public boolean hasPetPreference(@NotNull Pet pet, String preference) {
        return hasPetObject(pet.preferences.getPreferences(), preference);
    }

    public OpObjects negatePetPreference(@NotNull Pet pet, String preference) {
        return negatePetObjects(pet.preferences.getPreferences(), preference);
    }

    public OpObjects createPetSettings(@NotNull Pet pet) {
        return pet.settings.getSettings().dump()
                .addObject(new OpObjectCreator("visibleToOthers", OpObjectTypes.BOOL, true).getObject())
                .addObject(new OpObjectCreator("giftable", OpObjectTypes.BOOL, false).getObject())
                .addObject(new OpObjectCreator("glows", OpObjectTypes.BOOL, false).getObject())
                .addObject(new OpObjectCreator("followPlayer", OpObjectTypes.BOOL, true).getObject())
                .addObject(new OpObjectCreator("teleportToPlayer", OpObjectTypes.BOOL, true).getObject())
                .addObject(new OpObjectCreator("rideable", OpObjectTypes.BOOL, true).getObject())
                .addObject(new OpObjectCreator("otherRideable", OpObjectTypes.BOOL, false).getObject())
                .addObject(new OpObjectCreator("particlesEnabled", OpObjectTypes.BOOL, true).getObject()).get();
    }

    public boolean readPetSetting(@NotNull Pet pet, String setting) {
        return readPetObject(pet.settings.getSettings(), setting);
    }

    public boolean hasPetSetting(@NotNull Pet pet, String setting) {
        return hasPetObject(pet.settings.getSettings(), setting);
    }

    public OpObjects setPetSetting(@NotNull Pet pet, String setting, boolean value) {
        return pet.settings.getSettings().replaceObject(setting, value).get();
    }
}
