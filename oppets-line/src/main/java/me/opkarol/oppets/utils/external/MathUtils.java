package me.opkarol.oppets.utils.external;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.misc.StringTransformer;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.skills.Skill;
import org.jetbrains.annotations.NotNull;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Optional;

import static me.opkarol.oppets.utils.Utils.canPrestige;

public final class MathUtils {
    private static final Database database = Database.getInstance();
    private static final DecimalFormat dfZero = new DecimalFormat("0.00");

    public static int getLevelsForPrestige(Pet pet) {
        if (canPrestige(pet)) {
            return 0;
        }
        return getPrestigeLevel(pet) - pet.getLevel();
    }

    public static double getCutNumber(double number) {
        dfZero.setRoundingMode(RoundingMode.DOWN);
        try {
            return dfZero.parse(dfZero.format(number)).doubleValue();
        } catch (ParseException e) {
            return StringTransformer.getDoubleFromString(dfZero.format(number).replace(",", "."));
        }
    }

    public static int getPrestigeLevel(@NotNull Pet pet) {
        Optional<Skill> skillOptional = database.getOpPets().getSkillDatabase().getSkill(pet.getSkillName());
        return skillOptional.map(Skill::getMinPrestigeLevel).orElse(-1);
    }

    public static double getPetLevelExperience(@NotNull Pet pet) {
        return getCutNumber(database.getOpPets().getSkillDatabase().getAllPointsForSkill(pet) - pet.getPetExperience());
    }

    public static double getPercentageOfNextLevel(@NotNull Pet pet) {
        return getCutNumber((pet.getPetExperience() * 100) / database.getOpPets().getSkillDatabase().getAllPointsForSkill(pet));
    }

    public static @NotNull String substringFromEnd(@NotNull String text, int cutFromEnd) {
        return text.substring(0, text.length() - cutFromEnd);
    }

    public static int getRandomNumber(int min, int max) {
        if (min == max) {
            return min;
        }
        return (int) ((Math.random() * (max - min)) + min);
    }

}
