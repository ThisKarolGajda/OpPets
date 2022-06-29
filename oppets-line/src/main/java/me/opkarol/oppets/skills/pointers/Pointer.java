package me.opkarol.oppets.skills.pointers;

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

import org.bukkit.Material;

import java.util.List;

public class Pointer extends IPointer {
    private final String pointerName;
    private final String skillName;
    private double pointsAwarded;
    private List<Material> materialList;
    private final POINTS_TYPE type;

    public Pointer(String pointerName, String skillName, double pointsAwarded, List<Material> materialList, POINTS_TYPE type) {
        this.pointerName = pointerName;
        this.skillName = skillName;
        this.pointsAwarded = pointsAwarded;
        this.materialList = materialList;
        this.type = type;
    }

    @Override
    public POINTS_TYPE getType() {
        return type;
    }

    @Override
    public String getPointerName() {
        return pointerName;
    }

    @Override
    public String getSkillName() {
        return skillName;
    }

    @Override
    public double getPointsAwarded() {
        return pointsAwarded;
    }

    @Override
    public List<Material> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<Material> materialList) {
        this.materialList = materialList;
    }

    public void setPointsAwarded(double pointsAwarded) {
        this.pointsAwarded = pointsAwarded;
    }
}
