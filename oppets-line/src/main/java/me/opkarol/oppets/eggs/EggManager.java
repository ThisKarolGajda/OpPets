package me.opkarol.oppets.eggs;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.exceptions.Exception;
import me.opkarol.oppets.exceptions.ExceptionLogger;
import me.opkarol.oppets.exceptions.InvalidDatabaseException;
import me.opkarol.oppets.files.IConfigFile;
import me.opkarol.oppets.items.OpItemBuilder;
import me.opkarol.oppets.misc.StringTransformer;
import me.opkarol.oppets.pets.TypeOfEntity;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EggManager extends IConfigFile<String> {
    private List<EggItem> list;
    private ItemStack defaultItem;
    private final String DEFAULT_ERROR = "File eggs.yml contains invalid information. Couldn't find enough information for %s";
    private final ExceptionLogger logger;
    private static final OpItemBuilder builder = OpItemBuilder.getBuilder();

    public EggManager() {
        super("eggs");
        logger = new ExceptionLogger();
        createConfig();
        setupList();
    }

    private void setupList() {
        FileConfiguration config = getConfiguration();
        if (!containsObject("Eggs.enabled") && !config.getBoolean("Eggs.enabled")) {
            return;
        }
        list = new ArrayList<>();
        Optional<ConfigurationSection> section = getConfigurationSection("Eggs.types");
        if (!section.isPresent()) {
            logger.throwException(String.format(DEFAULT_ERROR, "configuration section 'Eggs.types'"));
            return;
        }
        try {
            section.ifPresent(section1 -> section1.getKeys(false).forEach(type -> {
                TypeOfEntity entityType = TypeOfEntity.valueOf(type);
                ItemStack item;
                String prefix = "Eggs.types." + type + ".";
                if (containsObjects(prefix, "item.material", "item.name", "item.lore", "item.glow")) {
                    item = builder.dump().setMaterial(StringTransformer.getMaterialFromString(config.getString(prefix + "item.material"))).setName(config.getString(prefix + "item.name")).setLore(config.getStringList(prefix + "item.lore")).setGlows(config.getBoolean(prefix + "item.glow")).getItem();
                } else {
                    item = getDefaultItem();
                }
                List<EggCost> costs = new ArrayList<>();
                List<String> cost = config.getStringList(prefix + "cost");
                if (cost.size() == 0) {
                    logger.throwException(String.format(DEFAULT_ERROR, "configuration section '" + prefix + "cost'"));
                } else {
                    for (String key : cost) {
                        String[] keys = key.split(":");
                        Material material = StringTransformer.getMaterialFromString(keys[0]);
                        Integer value = StringTransformer.getIntFromString(keys[1]);
                        if (value > 0 && material != null) {
                            costs.add(new EggCost(material, value));
                        }
                    }
                }
                Optional<ConfigurationSection> recipesSection = getConfigurationSection(prefix + "recipes");
                List<EggRecipe> recipes = new ArrayList<>();
                if (!recipesSection.isPresent()) {
                    logger.throwException(String.format(DEFAULT_ERROR, "configuration section '" + prefix + "recipes'"));
                } else {
                    recipesSection.ifPresent(sec -> sec.getKeys(false).forEach(key -> recipes.add(new EggRecipe(entityType, key, this, item))));
                    list.add(new EggItem(item, entityType, costs, recipes));
                }
            }));
        } catch (IllegalArgumentException e) {
            logger.addException(new Exception(this.getClass().toString(), e.fillInStackTrace(), new InvalidDatabaseException(String.format(DEFAULT_ERROR, "configuration section 'Eggs.types' and it contains invalid resources"))));
        }
    }

    public List<EggItem> getList() {
        return list;
    }

    public Optional<EggItem> getEggFromType(TypeOfEntity type) {
        List<EggItem> itemList1 = getList().stream().filter(eggItem -> eggItem.getType().equals(type)).collect(Collectors.toList());
        if (itemList1.isEmpty()) {
            return Optional.empty();
        }
        if (itemList1.size() > 1) {
            logger.throwException(String.format(DEFAULT_ERROR, "Type of Entity " + type));
        }
        return Optional.of(itemList1.get(0));
    }

    public ItemStack getDefaultItem() {
        if (defaultItem == null) {
            if (!containsObjects("Eggs.default_item.", "material", "name", "lore", "glow")) {
                logger.throwException(String.format(DEFAULT_ERROR, "Eggs.default_item"));
                return null;
            }
            FileConfiguration config = getConfiguration();
            defaultItem = builder.dump().setMaterial(StringTransformer.getMaterialFromString(config.getString("Eggs.default_item.material"))).setName(config.getString("Eggs.default_item.name")).setLore(config.getStringList("Eggs.default_item.lore")).setGlows(config.getBoolean("Eggs.default_item.glow")).getItem();
        }
        return defaultItem;
    }

    public List<String> getTypes() {
        return getList().stream().map(EggItem::getType).map(TypeOfEntity::getName).collect(Collectors.toList());
    }
}
