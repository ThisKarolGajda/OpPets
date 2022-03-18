package me.opkarol.oppets.pets;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

public class PetsConverter {

    public Pet convertJSONToPet(@NotNull JSONObject object) {
        String name, settings, skill, prestige;
        double experience;
        int level;
        OpPetsEntityTypes.TypeOfEntity type;
        boolean active;
        UUID ownUUID, ownerUUID;

        name = (String) object.get("name");
        settings = (String) object.get("settings");
        skill = (String) object.get("skill");
        experience = (double) object.get("experience");
        level = Integer.parseInt(String.valueOf(object.get("level")));
        type = OpPetsEntityTypes.TypeOfEntity.valueOf((String) object.get("type"));
        active = (boolean) object.get("active");
        ownUUID = null;
        ownerUUID = UUID.fromString(String.valueOf(object.get("ownerUUID")));
        prestige = String.valueOf(object.get("prestige"));


        return new Pet(name, experience, level, type, active, ownUUID, ownerUUID, settings, skill, prestige);
    }

    public JSONObject convertPetToJSON(@NotNull Pet pet) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", pet.getPetName());
        map.put("experience", pet.getPetExperience());
        map.put("level", pet.getLevel());
        map.put("type", String.valueOf(pet.getPetType()));
        map.put("active", pet.isActive());
        map.put("ownUUID", null);
        map.put("ownerUUID", String.valueOf(pet.getOwnerUUID()));
        map.put("settings", pet.getSettingsSerialized());
        map.put("skill", pet.getSkillName());
        map.put("prestige", pet.getPrestige());
        return new JSONObject(map);
    }

    public List<JSONObject> convertPetListToJSONList(List<Pet> pet) {
        if (pet == null) {
            return null;
        }
        List<JSONObject> i = new ArrayList<>();
        pet.forEach(pet1 -> i.add(convertPetToJSON(pet1)));
        return i;
    }

    public Pet convertStringToPet(String o) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject json = (JSONObject) parser.parse(o);
            return new PetsConverter().convertJSONToPet(json);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Pet> convertJSONArrayToPetList(Object @NotNull [] array) {
        List<Pet> i = new ArrayList<>();
        for (Object o : array) {
            if (o instanceof JSONObject) {
                i.add(convertStringToPet(((JSONObject) o).toJSONString()));
            }
        }
        return i;
    }

    public List<Pet> convertJSONArrayToPetList(@NotNull JSONArray array) {
        List<Pet> i = new ArrayList<>();
        for (Object o : array) {
            if (o instanceof JSONObject) {
                i.add(convertStringToPet(((JSONObject) o).toJSONString()));
            }
        }
        return i;
    }

    public ArrayList<Object> getListFromPets(@NotNull List<Pet> list) {
        ArrayList<Object> i = new ArrayList<>();
        for (Pet pet : list) {
            i.add(convertPetToJSON(pet));
        }
        return i;
    }
}
