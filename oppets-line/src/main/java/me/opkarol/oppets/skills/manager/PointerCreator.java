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

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.map.OpMap;
import me.opkarol.oppets.api.misc.StringTransformer;
import me.opkarol.oppets.skills.pointers.IPointer;
import me.opkarol.oppets.skills.pointers.Pointer;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PointerCreator {

    public static IPointer getPointer(String type, String materials, String pointsAwarded, String pointerName, String skillName) {
        final IPointer[] pointer = new IPointer[1];
        double points = StringTransformer.getDoubleFromString(pointsAwarded);
        List<Material> materialList = getMaterialList(materials);
        getType(type).ifPresent(points_type -> pointer[0] = new Pointer(pointerName, skillName, points, materialList, points_type));
        return pointer[0];
    }

    private static @NotNull List<Material> getMaterialList(@NotNull String string) {
        String[] s = string.split(";");
        List<Material> temp = new ArrayList<>();
        Arrays.stream(s).forEach(str -> temp.add(Material.getMaterial(str)));
        return temp;
    }

    public static Optional<IPointer.POINTS_TYPE> getType(String string) {
        return StringTransformer.getEnumValue(string == null ? "" : string.toUpperCase(), IPointer.POINTS_TYPE.class);
    }

    public static @NotNull OpMap<IPointer.POINTS_TYPE, List<IPointer>> getMap(List<IPointer> list) {
        OpMap<IPointer.POINTS_TYPE, List<IPointer>> temp = new OpMap<>();
        if (list != null) {
            for (IPointer pointer : list) {
                IPointer.POINTS_TYPE type = pointer.getType();
                if (temp.containsKey(type)) {
                    List<IPointer> list1 = temp.getOrDefault(type, new ArrayList<>());
                    list1.add(pointer);
                    temp.set(type, list1);
                } else {
                    temp.set(pointer.getType(), new ArrayList<>(Collections.singletonList(pointer)));
                }
            }
        }
        return temp;
    }
}
