package me.opkarol.oppets.skills.manager;

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

import me.opkarol.oppets.api.misc.StringTransformer;

public class ProgressingLevel {
    private final int startValue;
    private final int value;
    private boolean isPercentage;

    public ProgressingLevel(String string) {
        String[] strings = (string == null ? "" : string).split(";");
        startValue = StringTransformer.getIntFromString(strings[0]);
        value = StringTransformer.getIntFromString(strings[1].replace("%", ""));
        setPercentage(strings[1]);
    }

    public int getStartValue() {
        return startValue;
    }

    public int getValue() {
        return value;
    }

    public boolean isPercentage() {
        return isPercentage;
    }

    public double calculatePointsForLevel(int level) {
        double number = startValue;
        for (int i = 0; i < level; i++) {
            if (isPercentage()) {
                number += number * getValue() / 100;
            } else {
                number += number + getValue();
            }
        }
        return number;
    }

    public void setPercentage(String string) {
        this.isPercentage = string.contains("%");
    }
}
