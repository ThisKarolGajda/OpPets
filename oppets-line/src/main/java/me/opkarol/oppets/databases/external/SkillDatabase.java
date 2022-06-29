package me.opkarol.oppets.databases.external;

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.map.OpMap;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.events.PetLevelupEvent;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.TypeOfEntity;
import me.opkarol.oppets.skills.manager.ProgressingLevel;
import me.opkarol.oppets.skills.Skill;
import me.opkarol.oppets.skills.SkillLoader;
import me.opkarol.oppets.skills.pointers.IPointer;
import me.opkarol.oppets.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static me.opkarol.oppets.utils.external.MathUtils.getRandomNumber;

public class SkillDatabase {
    private final OpMap<String, Skill> skillsMap = new OpMap<>();

    public SkillDatabase() {
        new SkillLoader().startup().forEach(this::addSkill);
    }

    public void addSkill(Skill skill) {
        skillsMap.set(skill.getSkillName(), skill);
    }

    public Optional<Skill> getSkill(String skillName) {
        return skillsMap.getByKey(skillName);
    }

    public List<Skill> getSkills() {
        return new ArrayList<>(skillsMap.getValues());
    }

    public List<Skill> getSkillsToType(TypeOfEntity type) {
        if (skillsMap.isEmpty()) {
            return new ArrayList<>();
        }
        return skillsMap.getValues().stream().filter(skill -> skill.getAvailableTypes().contains(type)).collect(Collectors.toList());
    }

    public void addPoints(@NotNull Pet pet, @NotNull Player player, String type, Material material, double... multipliers) {
        double multiplier = Database.getInstance().getOpPets().getBoosterProvider().getMultiplier(player.getUniqueId().toString());
        double experience = pet.getPetExperience();
        String skillName = pet.getSkillName();
        Optional<Skill> optional = getSkill(skillName);
        if (!optional.isPresent()) {
            return;
        }
        if (multipliers.length != 0) {
            multiplier *= Arrays.stream(multipliers).reduce(1, (a, b) -> a * b);
        }
        Skill skill = optional.get();
        ProgressingLevel progressingLevel = skill.getProgressingLevel();
        IPointer pointer = skill.getPointer(type, material);
        if (pointer == null) {
            return;
        }
        double maxPoints = progressingLevel.calculatePointsForLevel(pet.getLevel());
        pet.setPetExperience(experience + pointer.getPointsAwarded() * multiplier);
        if (maxPoints <= pet.getPetExperience()) {
            Bukkit.getPluginManager().callEvent(new PetLevelupEvent(player, pet));
        }
        Utils.savePetProgress(pet, pet.petUUID.getOwnerUUID());
    }

    public String getRandomSkillName(TypeOfEntity type) {
        List<Skill> list = getSkillsToType(type);
        if (list == null || list.size() == 0) {
            return "";
        }
        if (list.size() == 1) {
            return list.get(0).getSkillName();
        } else {
            return list.get(getRandomNumber(0, list.size() - 1)).getSkillName();
        }
    }

    public double getAllPointsForSkill(@NotNull Pet pet) {
        Optional<Skill> optional = getSkill(pet.getSkillName());
        return optional.map(skill -> skill.getProgressingLevel().calculatePointsForLevel(pet.getLevel())).orElse(-1D);
    }
}
