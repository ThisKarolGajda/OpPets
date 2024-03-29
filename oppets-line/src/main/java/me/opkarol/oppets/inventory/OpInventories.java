package me.opkarol.oppets.inventory;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.addons.types.AddonConfig;
import me.opkarol.oppets.addons.AddonManager;
import me.opkarol.oppets.addons.types.IAddon;
import me.opkarol.oppets.inventory.cache.InventoriesCache;
import me.opkarol.oppets.cache.PageCache;
import me.opkarol.oppets.api.map.OpMap;
import me.opkarol.oppets.databases.external.APIDatabase;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.eggs.types.EggItem;
import me.opkarol.oppets.eggs.EggManager;
import me.opkarol.oppets.eggs.types.EggRecipe;
import me.opkarol.oppets.api.exceptions.ExceptionLogger;
import me.opkarol.oppets.api.files.MessagesHolder;
import me.opkarol.oppets.api.graphic.IGetter;
import me.opkarol.oppets.interfaces.IUtils;
import me.opkarol.oppets.inventory.accessors.InventoryAccessor;
import me.opkarol.oppets.inventory.accessors.PagesInventoryAccessor;
import me.opkarol.oppets.inventory.accessors.PetInventoryAccessor;
import me.opkarol.oppets.inventory.accessors.ShopInventoryAccessor;
import me.opkarol.oppets.inventory.anvil.PrestigeConfirmAnvilInventory;
import me.opkarol.oppets.inventory.anvil.RenameAnvilInventory;
import me.opkarol.oppets.inventory.builder.OpInventoryBuilder;
import me.opkarol.oppets.api.items.builder.OpItemBuilder;
import me.opkarol.oppets.api.items.OpItemEnums;
import me.opkarol.oppets.api.items.data.OpItemLampData;
import me.opkarol.oppets.api.items.data.OpItemShopData;
import me.opkarol.oppets.leaderboards.Leaderboard;
import me.opkarol.oppets.leaderboards.LeaderboardCounter;
import me.opkarol.oppets.api.misc.StringTransformer;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.pets.converter.PetsConverter;
import me.opkarol.oppets.pets.TypeOfEntity;
import me.opkarol.oppets.prestiges.PrestigeManager;
import me.opkarol.oppets.shops.Shop;
import me.opkarol.oppets.utils.*;
import me.opkarol.oppets.utils.external.FormatUtils;
import me.opkarol.oppets.utils.external.InventoryUtils;
import me.opkarol.oppets.utils.external.MathUtils;
import me.opkarol.oppets.utils.external.PDCUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static me.opkarol.oppets.cache.NamespacedKeysCache.summonItemKey;
import static me.opkarol.oppets.utils.external.FormatUtils.returnMessage;
import static me.opkarol.oppets.utils.external.InventoryUtils.fillStyledInventory;

public class OpInventories {
    public static class LevelInventory extends PetInventoryAccessor {
        private final OpInventoryBuilder inventoryBuilder = getEmptyBuilder()
                .setHolder(getClass())
                .setSize(27)
                .setConsumer(inventory -> InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE))
                .addEmptyTranslation("%max_pet_level%")
                .addEmptyTranslation("%percentage_of_next_experience%")
                .addEmptyTranslation("%pet_experience_next%")
                .addEmptyTranslation("%pet_level%")
                .setAutoDefaultPath();

        public LevelInventory(Pet pet) {
            super(pet);
        }

        @Override
        public Inventory buildInventory() {
            return inventoryBuilder
                    .cloneWithClearedGraphic()
                    .setTitle(InventoriesCache.levelInventoryTitle.replace("%pet_name%", FormatUtils.formatMessage(getPet().getPetName())))
                    .fillVariable(0, String.valueOf(MathUtils.getPrestigeLevel(getPet())))
                    .fillVariable(1, MathUtils.getPercentageOfNextLevel(getPet()) + "%")
                    .fillVariable(2, String.valueOf(MathUtils.getPetLevelExperience(getPet())))
                    .fillVariable(3, String.valueOf(getPet().getLevel()))
                    .updateTranslationsAsFunction()
                    .addTextButton("informationBook", 10, player -> {
                        Pet pet = Database.getInstance().getDatabase().getCurrentPet(player.getUniqueId());
                        player.sendMessage(pet.getSkillName());
                        pet.setRandomSkillName();
                        player.sendMessage(pet.getSkillName());
                    })
                    .addTextButton("level", 13)
                    .addTextButton("abilities", 16)
                    .getInventory();
        }
    }

    public static class LeaderboardInventory extends InventoryAccessor {
        private final OpInventoryBuilder inventoryBuilder = getEmptyBuilder()
                .setHolder(getClass())
                .setSize(27)
                .setConsumer(inventory -> InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE))
                .setTitle(InventoriesCache.leaderboardInventoryTitle)
                .setAutoDefaultPath()
                .getOwnConsumer(builder -> {
                    MessagesHolder.InventoriesMessages holder = MessagesHolder.getInstance().getInventoriesMessages();
                    Optional<ConfigurationSection> section = holder.getConfigurationSection(builder.getDefaultPath());
                    if (!section.isPresent()) {
                        return;
                    }
                    OpItemBuilder itemBuilder = OpItemBuilder.getBuilder();
                    LeaderboardCounter counter = APIDatabase.getInstance().getLeaderboard();
                    section.ifPresent(sec -> sec.getKeys(false).forEach(key -> {
                        String currentPath = builder.getDefaultPath() + key + ".";
                        Leaderboard leaderboard = counter.getLeaderboardsFromName(holder.getString(currentPath + "leaderboardName")).get(0);
                        if (leaderboard == null) {
                            return;
                        }
                        int slot = StringTransformer.getIntFromString(holder.getString(currentPath + "slot"));
                        if (slot > -1 && slot < 27) {
                            builder.addUnsafeButton(itemBuilder.dump().setPath(currentPath).setLoreAction(getFunction(leaderboard)), slot);
                        }
                    }));
                })
                .setAutoCache(60);

        @Override
        public Inventory buildInventory() {
            return inventoryBuilder.getCachedInventory();
        }

        @Contract(pure = true)
        private @NotNull Function<List<String>, List<String>> getFunction(@NotNull Leaderboard leaderboard) {
            return strings -> strings.stream().map(lambdaString -> {
                List<Pet> pets = new ArrayList<>(leaderboard.getPlaces());
                String currentString = lambdaString;
                String[] s = lambdaString.split(" ");
                for (String eachString : s) {
                    if (!eachString.startsWith("%") && !eachString.endsWith("%")) {
                        continue;
                    }
                    String replaced = eachString.replace("%", "");
                    int number = Integer.parseInt(replaced.replaceAll("[A-z]", ""));
                    Pet pet = pets.size() >= number ? pets.get(number - 1) : null;
                    if (pet == null) {
                        continue;
                    }
                    String value = replaced.substring(2);
                    String directory = "%" + number + "_" + value + "%";
                    switch (value) {
                        case "player_name": {
                            currentString = currentString.replace(directory, Utils.getNameFromUUID(pet.petUUID.getOwnerUUID()));
                            break;
                        }
                        case "player_object": {
                            switch (leaderboard.getType()) {
                                case TOP_LEVEL: {
                                    currentString = currentString.replace(directory, String.valueOf(pet.getLevel()));
                                    break;
                                }
                                case TOP_PRESTIGE: {
                                    currentString = currentString.replace(directory, new PrestigeManager().getFilledPrestige(pet.getPrestige()));
                                    break;
                                }
                                case TOP_EXPERIENCE: {
                                    currentString = currentString.replace(directory, String.valueOf(pet.getPetExperience()));
                                    break;
                                }
                                default: {
                                    throw new IllegalStateException("Unexpected value: " + leaderboard.getType());
                                }
                            }
                        }
                    }
                }
                return currentString;
            }).collect(Collectors.toList());
        }
    }

    public static class ShopInventory extends PagesInventoryAccessor<Shop> {
        private final OpInventoryBuilder inventoryBuilder = getEmptyBuilder()
                .setHolder(getClass())
                .setSize(54)
                .setTitle(InventoriesCache.shopInventoryTitle)
                .setAutoDefaultPath()
                .getOwnConsumer(builder -> {
                    MessagesHolder holder = MessagesHolder.getInstance();
                    String path = "ShopInventory.items";
                    Optional<ConfigurationSection> sec = holder.getInventoriesMessages().getConfigurationSection(path);
                    if (!sec.isPresent()) {
                        ExceptionLogger.getInstance().throwException("Configuration section ShopInventory.items is null!");
                    }
                    List<Shop> shList = new ArrayList<>();
                    sec.ifPresent(section -> section.getKeys(false).forEach(key -> {
                        String directory = path + "." + key;
                        int price = StringTransformer.getIntFromString(String.valueOf(holder.getValue(directory + ".options.price")));
                        String type = String.valueOf(holder.getValue(directory + ".options.type"));
                        if (price != -1 && type != null) {
                            shList.add(new Shop(price, type, directory));
                        }
                    }));
                    setCache(new PageCache<>(shList, 28));
                    getCache().setupInventory(getPage());
                });

        public ShopInventory(int page) {
            super(page);
        }

        @Override
        public Inventory buildInventory() {
            return inventoryBuilder
                    .getOwnConsumer(builder -> {
                        OpMap<Integer, IGetter> map = getCache().getPagedInventory(getPage());
                        for (int i : map.keySet()) {
                            IGetter object = map.getOrDefault(i, null);
                            switch (object.getGetterType()) {
                                case SHOP:
                                    Shop shop = (Shop) object;
                                    builder.addButton(OpItemBuilder.getBuilder().setPath(shop.getPath()).setLoreAction(shop.getFunction()), i, (player, item) -> {
                                        ItemMeta meta = item.getItemMeta();
                                        if (meta != null) {
                                            Material type = item.getType();
                                            if (!type.equals(Material.BARRIER) && !type.name().contains("STAINED_GLASS_PANE") && !type.equals(Material.AIR)) {
                                                player.openInventory(new BuyerAdmitInventory(shop).buildInventory());
                                            }
                                        }
                                    });
                                    break;
                                case ITEM_STACK: {
                                    builder.addButton(OpItemBuilder.getBuilder().setItem(((ItemStack) object.getObject())), i);
                                    break;
                                }
                                default: {
                                    ExceptionLogger.getInstance().throwException("Unexpected value: " + map.getOrDefault(i, null).getGetterType());
                                }
                            }
                        }
                    })
                    .getCachedInventory();
        }
    }

    public static class BuyerAdmitInventory extends ShopInventoryAccessor {
        private final OpInventoryBuilder inventoryBuilder = getEmptyBuilder()
                .setTitle(InventoriesCache.buyerAdmitInventoryTitle)
                .setSize(27)
                .setHolder(getClass())
                .setConsumer(inventory -> InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE))
                .setAutoDefaultPath()
                .getOwnConsumer(builder -> {
                    builder.addButton(b -> b.setPath(builder.getDefaultPath() + "decline"), 10, HumanEntity::closeInventory);
                    builder.addButton(b -> b.setPath(builder.getDefaultPath() + "informationBook").setLoreAction(getShop().getFunction()), 13);
                    builder.addButton(b -> b.setPath(builder.getDefaultPath() + "confirm").setShopData(new OpItemShopData(getShop())), 16, Utils::tryToBuyItemFromInventory);
                });


        public BuyerAdmitInventory(Shop shop) {
            super(shop);
        }

        @Override
        public Inventory buildInventory() {
            return inventoryBuilder.getInventory();
        }
    }

    public static class GuestInventory extends PetInventoryAccessor {
        private final OpInventoryBuilder inventoryBuilder = getEmptyBuilder()
                .setSize(27)
                .setHolder(getClass())
                .setConsumer(inventory -> InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE))
                .setAutoDefaultPath()
                .addEmptyTranslation("%max_pet_level%")
                .addEmptyTranslation("%percentage_of_next_experience%")
                .addEmptyTranslation("%current_prestige%")
                .addEmptyTranslation("%pet_owner%")
                .addEmptyTranslation("%pet_name%")
                .addEmptyTranslation("%pet_experience%")
                .addEmptyTranslation("%pet_level%")
                .addEmptyTranslation("%pet_type%")
                .addEmptyTranslation("%pet_skill%");


        public GuestInventory(Pet pet) {
            super(pet);
        }

        @Override
        public Inventory buildInventory() {
            return inventoryBuilder
                    .cloneWithClearedGraphic()
                    .setTitle(InventoriesCache.guestInventoryTitle.replace("%pet_name%", FormatUtils.formatMessage(getPet().getPetName())))
                    .fillVariable(0, String.valueOf(MathUtils.getPrestigeLevel(getPet())))
                    .fillVariable(1, MathUtils.getPercentageOfNextLevel(getPet()) + "%")
                    .fillVariable(2, Utils.getPrestige(getPet()))
                    .fillVariable(3, FormatUtils.formatMessage(Utils.getNameFromUUID(getPet().petUUID.getOwnerUUID())))
                    .fillVariable(4, Utils.getFilledPetName(getPet()))
                    .fillVariable(5, String.valueOf(MathUtils.getPetLevelExperience(getPet())))
                    .fillVariable(6, String.valueOf(getPet().getLevel()))
                    .fillVariable(7, getPet().getPetType().getName())
                    .fillVariable(8, getPet().getSkillName())
                    .updateTranslationsAsFunction()
                    .addTextButton("informationBook", 11)
                    .addTextButton("level", 15)
                    .getInventory();
        }
    }

    public static class AddonsInventory extends PagesInventoryAccessor<IAddon> {
        private final OpInventoryBuilder inventoryBuilder = getEmptyBuilder()
                .setHolder(getClass())
                .setTitle(InventoriesCache.addonsInventoryTitle)
                .setSize(54)
                .getOwnConsumer(builder -> {
                    setCache(new PageCache<>(AddonManager.getAddons().stream()
                            .filter(IAddon::canBeLaunched).collect(Collectors.toList()), 28));
                    getCache().setupInventory(getPage());
                });

        public AddonsInventory(int page) {
            super(page);
        }

        @Override
        public Inventory buildInventory() {
            return inventoryBuilder
                    .getOwnConsumer(builder -> {
                        OpMap<Integer, IGetter> map = getCache().getPagedInventory(getPage());
                        for (int i : map.keySet()) {
                            IGetter object = map.getOrDefault(i, null);
                            switch (object.getGetterType()) {
                                case ADDON:
                                    AddonConfig config = (AddonConfig) object;
                                    builder.addButton(b -> b.setMaterial(Material.BOOK).setName(config.getName()).setLore(config.getDescription()), i);
                                    break;
                                case ITEM_STACK: {
                                    builder.addButton(b -> b.setItem(((ItemStack) object.getObject())), i);
                                    break;
                                }
                                default: {
                                    ExceptionLogger.getInstance().throwException("Unexpected value: " + map.getOrDefault(i, null).getGetterType());
                                }
                            }
                        }
                    })
                    .getInventory();
        }
    }

    public static class PetMainInventory extends InventoryAccessor {
        private final Database database = Database.getInstance();
        private final OpInventoryBuilder inventoryBuilder = getEmptyBuilder()
                .setTitle(InventoriesCache.mainInventoryTitle)
                .setSize(27)
                .setConsumer(inventory -> InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE))
                .setHolder(getClass())
                .setAutoDefaultPath()
                .addTextButton("level", 10, player -> {
                    Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
                    player.openInventory(new OpInventories.LevelInventory(pet).buildInventory());
                })
                .addTextButton("name", 12, player -> {
                    Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
                    new RenameAnvilInventory(pet, player);
                })
                .addTextButton("settings", 14, player -> {
                    Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
                    player.openInventory(new SettingsInventory(pet).buildInventory());
                })
                .addTextButton("respawn", 16, player -> {
                    Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
                    database.getUtils().respawnPet(pet, player);
                });

        @Override
        public Inventory buildInventory() {
            return inventoryBuilder.getCachedInventory();
        }
    }

    public static class SettingsInventory extends PetInventoryAccessor {
        private final IUtils utils = APIDatabase.getInstance().getUtils();
        private final OpInventoryBuilder inventoryBuilder = getEmptyBuilder()
                .setSize(27)
                .setHolder(getClass())
                .setConsumer(inventory -> InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE))
                .setAutoDefaultPath()
                .addEmptyTranslation("%state_visibleToOthers%")
                .addEmptyTranslation("%state_giftable%")
                .addEmptyTranslation("%state_glows%")
                .addEmptyTranslation("%state_followPlayer%")
                .addEmptyTranslation("%state_teleportToPlayer%")
                .addEmptyTranslation("%state_rideable%")
                .addEmptyTranslation("%state_otherRideable%")
                .addEmptyTranslation("%state_particlesEnabled%");

        public SettingsInventory(Pet pet) {
            super(pet);
        }

        @Override
        public Inventory buildInventory() {
            return inventoryBuilder
                    .cloneWithClearedGraphic()
                    .setTitle(InventoriesCache.settingsInventoryTitle.replace("%pet_name%", FormatUtils.formatMessage(getPet().getPetName())))
                    .fillVariable(0, getPet().settings.isVisibleToOthers())
                    .fillVariable(1, getPet().settings.isGiftable())
                    .fillVariable(2, getPet().settings.isGlowing())
                    .fillVariable(3, getPet().settings.isFollowingPlayer())
                    .fillVariable(4, getPet().settings.isTeleportingToPlayer())
                    .fillVariable(5, getPet().settings.isRideable())
                    .fillVariable(6, getPet().settings.isOtherRideable())
                    .fillVariable(7, getPet().settings.areParticlesEnabled())
                    .updateTranslationsAsFunction()
                    .getOwnConsumer(builder -> {
                        Pet pet = getPet();
                        String defaultPath = builder.getDefaultPath();
                        int i = 9;
                        for (String s : Arrays.asList("visibleToOthers", "giftable", "glows", "followPlayer", "teleportToPlayer", "rideable", "otherRideable", "particlesEnabled")) {
                            builder.addButton(b -> b.setPath(defaultPath + s).setLampData(new OpItemLampData(pet.settings.getSettings().getBoolean(s, false))), i, player -> {
                                pet.setSettings(pet.settings.negate(s));
                                saveProgress(pet, player);
                            });
                            i++;
                        }
                    })
                    .addTextButton("resetSettings", 17, player -> {
                        getPet().settings.resetSettings();
                        saveProgress(getPet(), player);
                    })
                    .getInventory();
        }

        private void saveProgress(Pet pet, @NotNull Player player) {
            Utils.savePetProgress(pet, player.getUniqueId());
            utils.respawnPet(pet, player);
            player.openInventory(new SettingsInventory(pet).buildInventory());
        }
    }

    public static class SummonInventory extends PagesInventoryAccessor<Pet> {
        private UUID uuid;
        private final Database database = Database.getInstance();

        public SummonInventory(UUID uuid, int page) {
            this.uuid = uuid;
            setPage(page);
        }

        private final OpInventoryBuilder inventoryBuilder = getEmptyBuilder()
                .setTitle(InventoriesCache.summonInventoryTitle)
                .setHolder(getClass())
                .setSize(54)
                .getOwnConsumer(builder -> {
                    setPage(getPage());
                    setCache(new PageCache<>(database.getDatabase().getPetList(uuid), 28));
                    getCache().setupInventory(getPage());
                });

        @Override
        public Inventory buildInventory() {
            return inventoryBuilder
                    .cloneWithClearedGraphic()
                    .getOwnConsumer(builder -> {
                        OpMap<Integer, IGetter> map = getCache().getPagedInventory(getPage());
                        for (int i : map.keySet()) {
                            IGetter object = map.getOrDefault(i, null);
                            if (object == null) {
                                return;
                            }
                            switch (object.getGetterType()) {
                                case PET: {
                                    Pet pet = (Pet) object.getObject();
                                    builder.addButton(b -> b.setMaterial(Utils.getMaterialByPetType(pet.getPetType())).setName(pet.getPetName()).setGlows(true).setType(OpItemEnums.PATH_BUILDER_TYPE.NORMAL), i, (player, item) -> {
                                                if (item != null) {
                                                    UUID uuid = player.getUniqueId();
                                                    if (PDCUtils.hasNBT(item, summonItemKey)) {
                                                        String nbtString = PDCUtils.getNBT(item, summonItemKey);
                                                        if (nbtString != null) {
                                                            int page = StringTransformer.getIntFromString(nbtString);
                                                            player.openInventory(new SummonInventory(uuid, page).buildInventory());
                                                        }
                                                    } else {
                                                        ItemMeta meta = item.getItemMeta();
                                                        if (meta != null) {
                                                            String name = meta.getDisplayName();
                                                            if (Utils.summonPet(name, player)) {
                                                                player.closeInventory();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                    );
                                }
                                case ITEM_STACK: {
                                    builder.addButton(b -> b.setItem(((ItemStack) object.getObject())), i);
                                    break;
                                }
                                default: {
                                    throw new IllegalStateException("Unexpected value: " + map.getOrDefault(i, null).getGetterType());
                                }
                            }

                        }
                    })
                    .getInventory();
        }
    }

    public static class PrestigeInventory extends InventoryAccessor {
        private final Database database = Database.getInstance();
        private final MessagesHolder messages = MessagesHolder.getInstance();

        private final OpInventoryBuilder inventoryBuilder = getEmptyBuilder()
                .setTitle(InventoriesCache.prestigeInventoryTitle)
                .setSize(27)
                .setHolder(getClass())
                .setConsumer(inventory -> InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE))
                .setAutoDefaultPath()
                .addTextButton("informationBook", 11)
                .addTextButton("prestige", 15, player -> {
                    Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
                    if (!Utils.canPrestige(pet)) {
                        returnMessage(player, messages.getString("Messages.cantPrestige").replace("%more_levels%", String.valueOf(MathUtils.getLevelsForPrestige(pet))));
                    } else {
                        player.closeInventory();
                        new PrestigeConfirmAnvilInventory(pet, player);
                    }
                });


        @Override
        public Inventory buildInventory() {
            return inventoryBuilder.getCachedInventory();
        }
    }

    public static class PreferencesInventory extends PetInventoryAccessor {
        private final PetsConverter converter = new PetsConverter();
        private final Database database = Database.getInstance();
        private final OpInventoryBuilder inventoryBuilder = getEmptyBuilder()
                .setHolder(getClass())
                .setConsumer(inventory -> InventoryUtils.fillInventory(inventory, Material.BLACK_STAINED_GLASS_PANE, Material.GREEN_STAINED_GLASS_PANE))
                .setSize(27)
                .setAutoDefaultPath()
                .setTitle(InventoriesCache.preferencesInventoryTitle)
                .addEmptyTranslations("%state_ageable%", "%state_ageable_current%");

        public PreferencesInventory(Pet pet) {
            super(pet);
        }

        @Override
        public Inventory buildInventory() {
            return inventoryBuilder
                    .cloneWithClearedGraphic()
                    .fillVariablesFromStart(converter.hasPetPreference(getPet(), "ageable"), converter.readPetPreference(getPet(), "ageable"))
                    .updateTranslationsAsFunction()
                    .addTextButton("ageable", 9, player -> saveProgress(player, "ageable"))
                    .getInventory();
        }

        private void saveProgress(@NotNull Player player, String s) {
            Pet pet = getPet();
            pet.preferences.setPreferences(converter.negatePetPreference(pet, s));
            Utils.savePetProgress(pet, player.getUniqueId());
            database.getUtils().respawnPet(pet, player);
            player.openInventory(new PreferencesInventory(pet).buildInventory());
        }
    }

    public static class EggRecipeInventory extends InventoryAccessor {
        private final EggRecipe recipe;
        private final ItemStack item;
        private final String backItemName = MessagesHolder.getInstance().getInventoriesMessages().getString("EggRecipeInventory.back");

        private final OpInventoryBuilder inventoryBuilder = getEmptyBuilder()
                .setSize(54)
                .setHolder(getClass())
                .setConsumer(inventory -> fillStyledInventory(inventory, InventoryUtils.InventoryStyles.PIXEL));

        public EggRecipeInventory(EggRecipe recipe, ItemStack item) {
            this.recipe = recipe;
            this.item = item;
        }

        @Override
        public Inventory buildInventory() {
            return inventoryBuilder
                    .cloneWithClearedGraphic()
                    .setTitle(InventoriesCache.eggRecipeInventoryTitle.replace("%recipe%", recipe.getRecipeName()))
                    .getOwnConsumer(builder -> {
                        loopThroughValues(builder, "TOP", 10);
                        loopThroughValues(builder, "MIDDLE", 19);
                        loopThroughValues(builder, "DOWN", 28);
                    })
                    .addUnsafeButton(OpItemBuilder.getBuilder().setItem(item), 24)
                    .addUnsafeButton(OpItemBuilder.getBuilder().setMaterial(Material.ARROW).setName(backItemName), 49, player -> {
                        EggManager manager = APIDatabase.getInstance().getEggManager();
                        Optional<EggItem> optional = manager.getEggFromType(recipe.getType());
                        optional.ifPresent(eggItem -> player.openInventory(new EggRecipesInventory(eggItem.getType(), eggItem.getRecipes(), 0).buildInventory()));
                    })
                    .getInventory();
        }

        private void loopThroughValues(OpInventoryBuilder builder, String part, int startValue) {
            OpMap<String, List<Material>> map = recipe.getMap();
            map.getByKey(part).ifPresent(materials -> {
                int i = startValue;
                for (Material material : materials) {
                    if (builder.getGraphicInterface().isPlaceOccupied(builder.getHolderName(), i)) {
                        i++;
                    }
                    builder.addButton(b -> b.setMaterial(material), i);
                }
            });
        }
    }

    public static class EggRecipesInventory extends PagesInventoryAccessor<EggRecipe> {
        private TypeOfEntity type;
        private List<EggRecipe> recipes;
        private OpInventoryBuilder inventoryBuilder;

        public EggRecipesInventory(Optional<EggItem> item, int page) {
            setPage(page);
            if (!item.isPresent()) {
                return;
            }
            item.ifPresent(eggItem -> {
                this.type = eggItem.getType();
                this.recipes = eggItem.getRecipes();
            });
            inventoryBuilder = getEmptyBuilder()
                    .setHolder(getClass())
                    .setSize(54)
                    .getOwnConsumer(builder -> {
                        setCache(new PageCache<>(recipes, 28));
                        getCache().setupInventory(getPage());
                    })
                    .setAutoDefaultPath();
        }

        public EggRecipesInventory(TypeOfEntity type, List<EggRecipe> recipes, int page) {
            setPage(page);
            this.type = type;
            this.recipes = recipes;

            inventoryBuilder = getEmptyBuilder()
                    .setHolder(getClass())
                    .setSize(54)
                    .getOwnConsumer(builder -> {
                        setCache(new PageCache<>(recipes, 28));
                        getCache().setupInventory(getPage());
                    })
                    .setAutoDefaultPath();
        }

        @Override
        public Inventory buildInventory() {
            return inventoryBuilder
                    .cloneWithClearedGraphic()
                    .setTitle(InventoriesCache.eggRecipesInventoryTitle.replace("%type%", type.name()))
                    .getOwnConsumer(builder -> {
                        OpMap<Integer, IGetter> map = getCache().getPagedInventory(getPage());
                        for (int i : map.keySet()) {
                            IGetter object = map.getOrDefault(i, null);
                            switch (object.getGetterType()) {
                                case EGG_RECIPE: {
                                    EggRecipe recipe = (EggRecipe) object.getObject();
                                    if (recipe.getResult().getItemMeta() == null) {
                                        continue;
                                    }
                                    builder.addButton(b -> b.setItem(recipe.getResult()), i, player ->
                                            player.openInventory(new OpInventories.EggRecipeInventory(recipe, recipe.getResult()).buildInventory()));
                                    break;
                                }
                                case ITEM_STACK:  {
                                    builder.addButton(b -> b.setItem(((ItemStack) object.getObject())), i);
                                    break;
                                }
                                default: ExceptionLogger.getInstance().throwException("Unexpected value: " + map.getOrDefault(i, null).getGetterType());
                            }
                        }
                    })
                    .getInventory();
        }
    }
}