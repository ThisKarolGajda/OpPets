package me.opkarol.oppets.misc;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */


import me.opkarol.oppets.collections.OpMap;

public class CooldownModule<K> {
    private final OpMap<K, Long> cooldownMap;
    @SuppressWarnings("all")
    private final long DEFAULT_VALUE = 1000L;

    public CooldownModule() {
        this.cooldownMap = new OpMap<>();
    }

    public void addCooldown(K object) {
        if (!this.cooldownMap.containsKey(object)) {
            this.cooldownMap.put(object, (getCurrentUnix() + 30));
        } else {
            this.cooldownMap.replace(object, (getCurrentUnix() + 30));
        }
    }

    public boolean hasActiveCooldown(K object) {
        if (this.cooldownMap.containsKey(object)) {
            long cooldown = this.cooldownMap.getOrDefault(object, null);
            long current = getCurrentUnix();
            return cooldown >= current;
        }
        return false;
    }

    private long getCurrentUnix() {
        return System.currentTimeMillis() / this.DEFAULT_VALUE;
    }

    public OpMap<K, Long> getCooldownMap() {
        return this.cooldownMap;
    }
}