package me.opkarol.oppets.skills;

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
import me.opkarol.oppets.api.misc.StringTransformer;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.pets.TypeOfEntity;
import me.opkarol.oppets.skills.abilities.IAbility;
import me.opkarol.oppets.skills.pointers.IPointer;
import me.opkarol.oppets.skills.manager.PointerCreator;
import me.opkarol.oppets.skills.manager.ProgressingLevel;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Skill {
    private final String skillPath;
    private final String skillName;
    private String skillCustomName;
    private String skillAvailableTypes;
    private List<TypeOfEntity> availableTypes;
    private int minPrestigeLevel;
    private final OpMap<IPointer.POINTS_TYPE, List<IPointer>> pointers;
    private final OpMap<IAbility.ABILITY_TYPE, List<IAbility>> abilities;
    private ProgressingLevel progressingLevel;

    public Skill(String skillPath, String skillName, String skillCustomName, String skillAvailableTypes, int minPrestigeLevel, OpMap<IPointer.POINTS_TYPE, List<IPointer>> pointers, OpMap<IAbility.ABILITY_TYPE, List<IAbility>> abilities, ProgressingLevel progressingLevel) {
        this.skillPath = skillPath;
        this.skillName = skillName;
        this.skillCustomName = skillCustomName;
        this.skillAvailableTypes = skillAvailableTypes;
        this.minPrestigeLevel = minPrestigeLevel;
        this.pointers = pointers;
        this.abilities = abilities;
        this.progressingLevel = progressingLevel;
        setAvailableTypes(skillAvailableTypes);
        Database.getInstance().getPlugin().getLogger().info(skillPath + " 1");
        Database.getInstance().getPlugin().getLogger().info(skillName+ " 2");
        Database.getInstance().getPlugin().getLogger().info(skillCustomName+ " 3");
        Database.getInstance().getPlugin().getLogger().info(skillAvailableTypes+ " 4");
        Database.getInstance().getPlugin().getLogger().info(minPrestigeLevel + " 5");
        Database.getInstance().getPlugin().getLogger().info(pointers.getValues().size() + " 6");
        Database.getInstance().getPlugin().getLogger().info(abilities.getValues().size() + " 7");
        Database.getInstance().getPlugin().getLogger().info(progressingLevel.calculatePointsForLevel(1) + " 8");

    }

    public IPointer getPointer(String type, Material material) {
        Optional<IPointer.POINTS_TYPE> optional = PointerCreator.getType(type);
        if (!optional.isPresent()) {
            return null;
        }
        List<IPointer> list = pointers.getOrDefault(optional.get(), new ArrayList<>());
        for (IPointer pointer : list) {
            Optional<Material> optional1 = pointer.getMaterialList().stream()
                    .filter(material1 -> material1.equals(material)).findFirst();
            if (optional1.isPresent()) {
                return pointer;
            }
        }
        return null;
    }

    public String getSkillPath() {
        return skillPath;
    }

    public String getSkillName() {
        return skillName;
    }

    public String getSkillAvailableTypes() {
        return skillAvailableTypes;
    }

    public void setSkillAvailableTypes(String skillAvailableTypes) {
        this.skillAvailableTypes = skillAvailableTypes;
    }

    public List<TypeOfEntity> getAvailableTypes() {
        return availableTypes;
    }

    public void setAvailableTypes(List<TypeOfEntity> availableTypes) {
        this.availableTypes = availableTypes;
    }

    public int getMinPrestigeLevel() {
        return minPrestigeLevel;
    }

    public void setMinPrestigeLevel(int minPrestigeLevel) {
        this.minPrestigeLevel = minPrestigeLevel;
    }

    public ProgressingLevel getProgressingLevel() {
        return progressingLevel;
    }

    public String getSkillCustomName() {
        return skillCustomName;
    }

    public void setProgressingLevel(ProgressingLevel progressingLevel) {
        this.progressingLevel = progressingLevel;
    }

    public void setSkillCustomName(String skillCustomName) {
        this.skillCustomName = skillCustomName;
    }

    public OpMap<IAbility.ABILITY_TYPE, List<IAbility>> getAbilities() {
        return abilities;
    }

    public void setAvailableTypes(@NotNull String string) {
        String[] strings = string.split(";");
        if (string.equalsIgnoreCase("all")) {
            availableTypes = new ArrayList<>(Arrays.asList(TypeOfEntity.values()));
        } else {
            List<TypeOfEntity> types = new ArrayList<>();
            Arrays.stream(strings).forEach(s -> {
                StringTransformer.getEnumValue(s, TypeOfEntity.class).ifPresent(types::add);
            });
            availableTypes = types;
        }
        Database.getInstance().getPlugin().getLogger().info(availableTypes.size() + " size");
    }
}
