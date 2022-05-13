package me.opkarol.oppets.utils;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.misc.StringTransformer;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.skills.Adder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static me.opkarol.oppets.utils.OpUtils.canPrestige;

public final class MathUtils {
    private static Database database;

    public static void setDatabase(Database database) {
        MathUtils.database = database;
    }

    public static double getAdderPoints(String skillName, boolean lowestOutput, Pet pet) {
        double number = 0D;
        List<Adder> list = database.getOpPets().getSkillDatabase().getSkillFromMap(skillName).getAdderList();
        for (Adder adder : list) {
            double calculated = adder.calculateMaxCurrent(pet.getLevel());
            if (number == 0) {
                number = calculated;
                continue;
            }
            if (lowestOutput) {
                if (calculated < number) {
                    number = calculated;
                }
            } else {
                if (calculated > number) {
                    number = calculated;
                }
            }
        }
        return number;
    }

    public static int getLevelsForPrestige(Pet pet) {
        if (canPrestige(pet)) {
            return 0;
        }
        return getMaxLevel(pet) - pet.getLevel();
    }

    public static int getMaxLevel(@NotNull Pet pet) {
        return database.getOpPets().getSkillDatabase().getSkillFromMap(pet.getSkillName()).getMaxLevel();
    }

    public static int getPetLevelExperience(@NotNull Pet pet) {
        return Math.toIntExact(Math.round(getAdderPoints(pet.getSkillName(), true, pet) - pet.getPetExperience()));
    }

    public static double getPercentageOfNextLevel(@NotNull Pet pet) {
        String skillName = pet.getSkillName();
        String s = String.valueOf(((pet.getPetExperience() * 100) / getAdderPoints(skillName, false, pet)));
        if (s.length() > 4) {
            s = s.substring(0, 4);
        }
        return StringTransformer.getDoubleFromString(s);
    }

    public static @NotNull String substringFromEnd(@NotNull String text, int cutFromEnd) {
        return text.substring(0, text.length() - cutFromEnd);
    }

}
