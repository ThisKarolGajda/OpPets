package me.opkarol.oppets.items;

import me.opkarol.oppets.collections.OpMap;
import me.opkarol.oppets.databases.APIDatabase;
import me.opkarol.oppets.files.IConfigFile;
import me.opkarol.oppets.files.MessagesHolder;
import me.opkarol.oppets.misc.StringTransformer;
import me.opkarol.oppets.utils.PDCUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static me.opkarol.oppets.cache.NamespacedKeysCache.priceKey;
import static me.opkarol.oppets.cache.NamespacedKeysCache.typeKey;
import static me.opkarol.oppets.utils.FormatUtils.formatList;
import static me.opkarol.oppets.utils.FormatUtils.formatMessage;

public class OpItemBuilder {
    public final ItemStack DEFAULT_ITEM = new ItemStack(Material.STONE);
    private ItemStack cached;
    private Material material;
    private String name;
    private List<String> lore;
    private OpMap<String, String> pdc;
    private boolean glows, unbreakable;
    private OpMap<Enchantment, Integer> enchantments;
    private Function<List<String>, List<String>> loreAction;
    private OpItemEnums.PATH_BUILDER_TYPE type = OpItemEnums.PATH_BUILDER_TYPE.NORMAL;
    private IConfigFile<?> configFile;
    private OpItemLampData lampData;
    private OpItemShopData shopData;
    private String path;

    public OpItemBuilder(ItemStack cached, Material material, String name, List<String> lore, OpMap<String, String> pdc, boolean glows, OpMap<Enchantment, Integer> enchantments, Function<List<String>, List<String>> loreAction) {
        this();
        this.cached = cached;
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.pdc = pdc;
        this.glows = glows;
        this.enchantments = enchantments;
        this.loreAction = loreAction;
    }

    public OpItemBuilder(String path, IConfigFile<?> configFile, OpItemEnums.PATH_BUILDER_TYPE type) {
        this();
        setPath(path);
        setConfigFile(configFile);
        setType(type);
    }

    public OpItemBuilder() {
        setConfigFile(OpItemEnums.PATH_BUILDER_CONFIG.INVENTORIES);
    }

    public ItemStack getItem() {
        return cached == null ? generateItem() : applyPdc(cached);
    }

    public OpItemBuilder setItem(ItemStack cached) {
        this.cached = cached;
        return this;
    }

    public Material getMaterial() {
        return material;
    }

    public OpItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public String getName() {
        return name;
    }

    public OpItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getLore() {
        return lore;
    }

    public OpItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public OpMap<String, String> getPdc() {
        return pdc;
    }

    public OpItemBuilder setPdc(OpMap<String, String> pdc) {
        this.pdc = pdc;
        return this;
    }

    public OpMap<String, String> readPdcOnItem() {
        return PDCUtils.getAllValues(getItem());
    }

    public boolean isGlowing() {
        return glows;
    }

    public OpItemBuilder setGlows(boolean glows) {
        this.glows = glows;
        return this;
    }

    public OpMap<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public OpItemBuilder setEnchantments(OpMap<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    public Function<List<String>, List<String>> getLoreAction() {
        return loreAction;
    }

    public OpItemBuilder setLoreAction(Function<List<String>, List<String>> loreAction) {
        this.loreAction = loreAction;
        return this;
    }

    public OpItemBuilder get() {
        return this;
    }

    public OpItemBuilder dump() {
        return new OpItemBuilder();
    }

    public ItemStack generateItem() {
        switch (type) {
            case PATH: {
                FileConfiguration configuration = getConfigFile().getConfiguration();
                Material material = getMaterial();
                if (material == null) {
                    material = StringTransformer.getMaterialFromString(configuration.getString(path + "material"));
                }
                String name = configuration.getString(path + "name");
                List<String> lore = configuration.getStringList(path + "lore");
                boolean glows = configuration.getBoolean(path + "glow");
                if (material == null) {
                    return DEFAULT_ITEM;
                }
                this.setName(name)
                        .setLore(lore)
                        .setGlows(glows)
                        .setMaterial(material)
                        .setLoreAction(loreAction)
                        .setType(OpItemEnums.PATH_BUILDER_TYPE.NORMAL);
                return generateItem();
            }
            case SHOP: {
                if (shopData == null) {
                    return DEFAULT_ITEM;
                }
                ItemStack item = new OpItemBuilder(path, configFile, OpItemEnums.PATH_BUILDER_TYPE.PATH).getItem();
                PDCUtils.addNBT(item, priceKey, String.valueOf(shopData.getPrice()));
                PDCUtils.addNBT(item, typeKey, shopData.getType());
                this.setItem(item);
                return item;
            }
            case LAMP: {
                if (lampData == null) {
                    return DEFAULT_ITEM;
                }
                ItemStack item = new OpItemBuilder(path, configFile, OpItemEnums.PATH_BUILDER_TYPE.PATH).setMaterial(lampData.getSelectedType()).getItem();
                this.setItem(item);
                return item;
            }
            case NORMAL: {
                if (material == null) {
                    setMaterial(DEFAULT_ITEM.getType());
                }
                ItemStack item = new ItemStack(material);
                ItemMeta meta = item.getItemMeta();
                if (meta == null) {
                    return DEFAULT_ITEM;
                }
                if (name != null) {
                    meta.setDisplayName(formatMessage(name));
                } else {
                    meta.setDisplayName(formatMessage("&l"));
                }
                if (loreAction != null) {
                    lore = loreAction.apply(lore);
                }
                if (lore != null) {
                    meta.setLore(formatList(lore));
                }
                applyPdc(item);
                meta.setUnbreakable(isUnbreakable());
                if (glows) {
                    meta.addEnchant(Enchantment.LUCK, 1, false);
                }
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
                item.setItemMeta(meta);
                setItem(item);
                return item;
            }
            default:
                return DEFAULT_ITEM;
        }
    }

    public boolean isUnbreakable() {
        return unbreakable;
    }

    public void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }

    private ItemStack applyPdc(ItemStack item) {
        if (pdc != null) {
            for (String s : pdc.keySet()) {
                PDCUtils.addNBT(item, new NamespacedKey(APIDatabase.getInstance().getPlugin(), s), pdc.getOrDefault(s, null));
            }
        }
        return item;
    }

    public static OpItemBuilder getBuilder() {
        return new OpItemBuilder();
    }

    public String getPath() {
        return path;
    }

    public OpItemBuilder setPath(String path) {
        this.path = path.endsWith(".") ? path : path + ".";
        this.setType(OpItemEnums.PATH_BUILDER_TYPE.PATH);
        return this;
    }

    public OpItemShopData getShopData() {
        return shopData;
    }

    public OpItemBuilder setShopData(OpItemShopData shopData) {
        this.shopData = shopData;
        this.setType(OpItemEnums.PATH_BUILDER_TYPE.SHOP);
        return this;
    }

    public OpItemLampData getLampData() {
        return lampData;
    }

    public OpItemBuilder setLampData(OpItemLampData lampData) {
        this.lampData = lampData;
        this.setType(OpItemEnums.PATH_BUILDER_TYPE.LAMP);
        return this;
    }

    public IConfigFile<?> getConfigFile() {
        return configFile;
    }

    public OpItemBuilder setConfigFile(IConfigFile<?> configFile) {
        this.configFile = configFile;
        return this;
    }

    public OpItemEnums.PATH_BUILDER_TYPE getType() {
        return type;
    }

    public OpItemBuilder setType(OpItemEnums.PATH_BUILDER_TYPE type) {
        this.type = type;
        return this;
    }

    public OpItemBuilder setConfigFile(OpItemEnums.@NotNull PATH_BUILDER_CONFIG config, IConfigFile<?>... configFile) {
        switch (config) {
            case CUSTOM: {
                if (configFile != null && configFile.length > 0) {
                    setConfigFile(configFile[0]);
                }
                break;
            }
            case MESSAGES: {
                setConfigFile(MessagesHolder.getInstance().getMessages());
                break;
            }
            case INVENTORIES: {
                setConfigFile(MessagesHolder.getInstance().getInventoriesMessages());
                break;
            }
        }
        return this;
    }

    @Override
    public String toString() {
        return "OpItemBuilder{" +
                "DEFAULT_ITEM=" + DEFAULT_ITEM +
                ", cached=" + cached +
                ", material=" + material +
                ", name='" + name + '\'' +
                ", lore=" + lore +
                ", pdc=" + pdc +
                ", glows=" + glows +
                ", unbreakable=" + unbreakable +
                ", enchantments=" + enchantments +
                ", loreAction=" + loreAction +
                ", type=" + type +
                ", configFile=" + configFile +
                ", lampData=" + lampData +
                ", shopData=" + shopData +
                ", path='" + path + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OpItemBuilder builder = (OpItemBuilder) o;

        if (glows != builder.glows) return false;
        if (unbreakable != builder.unbreakable) return false;
        if (!Objects.equals(cached, builder.cached)) return false;
        if (material != builder.material) return false;
        if (!Objects.equals(name, builder.name)) return false;
        if (!Objects.equals(lore, builder.lore)) return false;
        if (!Objects.equals(pdc, builder.pdc)) return false;
        if (!Objects.equals(enchantments, builder.enchantments))
            return false;
        if (!Objects.equals(loreAction, builder.loreAction)) return false;
        if (type != builder.type) return false;
        if (!Objects.equals(configFile, builder.configFile)) return false;
        if (!Objects.equals(lampData, builder.lampData)) return false;
        if (!Objects.equals(shopData, builder.shopData)) return false;
        return Objects.equals(path, builder.path);
    }

    @Override
    public int hashCode() {
        int result = DEFAULT_ITEM.hashCode();
        result = 31 * result + (cached != null ? cached.hashCode() : 0);
        result = 31 * result + (material != null ? material.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lore != null ? lore.hashCode() : 0);
        result = 31 * result + (pdc != null ? pdc.hashCode() : 0);
        result = 31 * result + (glows ? 1 : 0);
        result = 31 * result + (unbreakable ? 1 : 0);
        result = 31 * result + (enchantments != null ? enchantments.hashCode() : 0);
        result = 31 * result + (loreAction != null ? loreAction.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (configFile != null ? configFile.hashCode() : 0);
        result = 31 * result + (lampData != null ? lampData.hashCode() : 0);
        result = 31 * result + (shopData != null ? shopData.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }
}
