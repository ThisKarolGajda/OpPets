package me.opkarol.oppets.utils.external;

/*
 * Copyright (c) 2021-2022.
 * [OpPets] ThisKarolGajda
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.items.builder.OpItemBuilder;
import me.opkarol.oppets.items.OpItemEnums;
import me.opkarol.oppets.misc.StringTransformer;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class InventoryUtils {
    private static final OpItemBuilder builder = OpItemBuilder.getBuilder().setType(OpItemEnums.PATH_BUILDER_TYPE.NORMAL);

    public static void fillInventory(Inventory inventory, Material... material) {
        List<Material> materials = Arrays.stream(material).collect(Collectors.toList());
        if (materials.isEmpty()) {
            for (int i = 0; i < inventory.getSize(); i++) {
                if (inventory.getItem(i) == null) {
                    inventory.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
                }
            }
            return;
        }
        int size = material.length;
        int currentValue = 0;
        for (int itemNumber = 0; itemNumber < inventory.getSize(); itemNumber++) {
            if ((size - 1) == currentValue) {
                currentValue = 0;
            } else {
                currentValue++;
            }
            ItemStack item = inventory.getItem(itemNumber);
            if (item == null) {
                inventory.setItem(itemNumber, builder.dump().setType(OpItemEnums.PATH_BUILDER_TYPE.NORMAL).setMaterial(materials.get(currentValue)).getItem());
            }
        }
    }

    public static void fillStyledInventory(Inventory inventory, @NotNull InventoryStyles style) {
        switch (style) {
            case BOOK: {
                ItemStack item = builder.dump().setType(OpItemEnums.PATH_BUILDER_TYPE.NORMAL).setMaterial(Material.BLACK_STAINED_GLASS_PANE).getItem();
                int size = inventory.getSize() / 9;
                Optional<FillSizes> optional = StringTransformer.getEnumValue("R" + size, FillSizes.class);
                if (!optional.isPresent()) {
                    optional = Optional.of(FillSizes.R6);
                }
                for (int i : optional.get().getInts()) {
                    inventory.setItem(i, item);
                }
                break;
            }
            case SQUARE: {
                ItemStack item = builder.dump().setType(OpItemEnums.PATH_BUILDER_TYPE.NORMAL).setMaterial(Material.BLACK_STAINED_GLASS_PANE).getItem();
                ItemStack corner = builder.dump().setType(OpItemEnums.PATH_BUILDER_TYPE.NORMAL).setMaterial(Material.GREEN_STAINED_GLASS_PANE).getItem();
                int size = inventory.getSize() / 9;
                Optional<FillSizes> optional = StringTransformer.getEnumValue("R" + size, FillSizes.class);
                if (!optional.isPresent()) {
                    optional = Optional.of(FillSizes.R6);
                }
                for (int i : optional.get().getInts()) {
                    inventory.setItem(i, item);
                }
                Optional<FillCorners> optional2 = StringTransformer.getEnumValue("R" + size, FillCorners.class);
                if (!optional2.isPresent()) {
                    optional2 = Optional.of(FillCorners.R6);
                }
                for (int i : optional2.get().getInts()) {
                    inventory.setItem(i, corner);
                }
                break;
            }
            case PIXEL: {
                ItemStack down = builder.dump().setType(OpItemEnums.PATH_BUILDER_TYPE.NORMAL).setMaterial(Material.GREEN_STAINED_GLASS_PANE).getItem();
                for (int i : FillSizes.D1.getInts()) {
                    inventory.setItem(i, down);
                }
                fillInventory(inventory);
                break;
            }
            default: throw new IllegalStateException("Unexpected value: " + style);
        }
    }

    public enum InventoryStyles {
        BOOK,
        SQUARE,
        PIXEL
    }

    public enum FillSizes {
        R1(new int[]{1, 2}),
        R2(new int[]{0, 8, 9, 17}),
        R3(new int[]{0, 8, 9, 17, 18, 26}),
        R4(new int[]{0, 8, 9, 17, 18, 26, 27, 35}),
        R5(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44}),
        R6(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53}),
        D1(new int[]{45, 46, 47, 48, 50, 51, 52, 53});

        private final int[] ints;

        FillSizes(int[] ints) {
            this.ints = ints;
        }

        public int[] getInts() {
            return ints;
        }
    }

    public enum FillCorners {
        R1(new int[]{0, 8}),
        R2(new int[]{0, 8, 9, 17}),
        R3(new int[]{0, 8, 18, 26}),
        R4(new int[]{0, 8, 27, 35}),
        R5(new int[]{0, 8, 36, 44}),
        R6(new int[]{0, 8, 45, 53});

        private final int[] ints;

        FillCorners(int[] ints) {
            this.ints = ints;
        }

        public int[] getInts() {
            return ints;
        }
    }
}