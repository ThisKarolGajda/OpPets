package me.opkarol.oppets.misc;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import java.util.HashMap;

/**
 * The type Cooldown module.
 *
 * @param <K> the type parameter
 */
public class CooldownModule<K> {
    /**
     * The Cooldown map.
     */
    private final HashMap<K, Long> cooldownMap;
    /**
     * The Default value.
     */
    @SuppressWarnings("all")
    private final long DEFAULT_VALUE = 1000L;

    /**
     * Instantiates a new Cooldown module.
     */
    public CooldownModule() {
        this.cooldownMap = new HashMap<>();
    }

    /**
     * Add cooldown.
     *
     * @param object the object
     */
    public void addCooldown(K object) {
        if (!this.cooldownMap.containsKey(object)) {
            this.cooldownMap.put(object, (getCurrentUnix() + 30));
        } else {
            this.cooldownMap.replace(object, (getCurrentUnix() + 30));
        }
    }

    /**
     * Has active cooldown boolean.
     *
     * @param object the object
     * @return the boolean
     */
    public boolean hasActiveCooldown(K object) {
        if (this.cooldownMap.containsKey(object)) {
            long cooldown = this.cooldownMap.get(object);
            long current = getCurrentUnix();
            return cooldown >= current;
        }
        return false;
    }

    /**
     * Gets current unix.
     *
     * @return the current unix
     */
    private long getCurrentUnix() {
        return System.currentTimeMillis() / this.DEFAULT_VALUE;
    }

    /**
     * Gets cooldown map.
     *
     * @return the cooldown map
     */
    public HashMap<K, Long> getCooldownMap() {
        return this.cooldownMap;
    }
}