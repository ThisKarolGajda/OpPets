package me.opkarol.oppets.skills;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

public class Requirement {
    private SkillEnums.SkillsRequirements requirement;

    public Requirement(SkillEnums.SkillsRequirements requirements){
        setRequirement(requirements);
    }

    public SkillEnums.SkillsRequirements getRequirement() {
        return requirement;
    }

    public void setRequirement(SkillEnums.SkillsRequirements requirement) {
        this.requirement = requirement;
    }
}
