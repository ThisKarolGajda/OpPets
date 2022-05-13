package me.opkarol.oppets.inventory;

import me.opkarol.oppets.collections.OpLinkedMap;
import me.opkarol.oppets.databases.APIDatabase;
import me.opkarol.oppets.graphic.GraphicInterface;
import me.opkarol.oppets.graphic.GraphicInventoryDataBuilder;
import me.opkarol.oppets.interfaces.IHolder;
import me.opkarol.oppets.items.OpItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static me.opkarol.oppets.utils.OpUtils.replaceAndGet;

public class OpInventoryBuilder implements Cloneable {
    private String title;
    private int size;
    private IHolder holder;
    private Consumer<Inventory> consumer;
    private Function<List<String>, List<String>> function;
    private GraphicInterface graphicInterface;
    private Inventory cachedInventory;
    private boolean canCache = true;
    private String holderName;
    private String defaultPath;
    private final OpLinkedMap<String, String> translations = new OpLinkedMap<>();
    private final int DEFAULT_SIZE = 27;
    private final String DEFAULT_TITLE = "DEFAULT";
    private final IHolder DEFAULT_HOLDER = () -> "HOLDER";

    public OpInventoryBuilder(String title, int size, @NotNull IHolder holder) {
        this();
        this.title = title;
        this.size = size;
        this.holder = holder;
        this.holderName = holder.getName();
    }

    public OpInventoryBuilder(String title, int size, String holder) {
        this();
        this.title = title;
        this.size = size;
        this.holderName = holder;
        this.holder = () -> holderName;
    }

    public OpInventoryBuilder() {
        updateGraphicInterface();
    }

    public int getSize() {
        return size < 0 ? DEFAULT_SIZE : size;
    }

    public OpInventoryBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    public String getTitle() {
        return title == null ? DEFAULT_TITLE : title;
    }

    public OpInventoryBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public IHolder getHolder() {
        return holder == null ? DEFAULT_HOLDER : holder;
    }

    public OpInventoryBuilder setHolder(@NotNull IHolder holder) {
        this.holder = holder;
        this.holderName = holder.getName();
        return this;
    }

    public OpInventoryBuilder setHolder(String holderName) {
        this.holderName = holderName;
        this.holder = () -> holderName;
        return this;
    }

    public OpInventoryBuilder setHolder(@NotNull Class<? extends IInventoryAccess> clazz) {
        this.holderName = clazz.getSimpleName();
        this.holder = () -> holderName;
        return this;
    }

    public OpInventoryBuilder dump() {
        return new OpInventoryBuilder();
    }

    public Consumer<Inventory> getConsumer() {
        return consumer;
    }

    public OpInventoryBuilder setConsumer(Consumer<Inventory> consumer) {
        this.consumer = consumer;
        return this;
    }

    public Function<List<String>, List<String>> getFunction() {
        return function;
    }

    public OpInventoryBuilder setFunction(Function<List<String>, List<String>> function) {
        this.function = function;
        return this;
    }

    public GraphicInterface getGraphicInterface() {
        return graphicInterface == null ? updateGraphicInterface().graphicInterface : graphicInterface;
    }

    public OpInventoryBuilder setGraphicInterface(GraphicInterface graphicInterface) {
        this.graphicInterface = graphicInterface;
        return this;
    }

    public OpInventoryBuilder updateGraphicInterface() {
        setGraphicInterface(GraphicInterface.getInstance());
        return this;
    }

    public Inventory getInventory() {
        cachedInventory = GraphicInterface.getInventory(new GraphicInventoryDataBuilder(holder, size, title), consumer);
        return cachedInventory;
    }

    public Inventory getCachedInventory() {
        return cachedInventory == null ? getInventory() : cachedInventory;
    }

    public OpInventoryBuilder cacheInventory() {
        this.cachedInventory = getInventory();
        return this;
    }

    public OpInventoryBuilder setCachedInventory(Inventory inventory) {
        this.cachedInventory = inventory;
        return this;
    }

    public OpInventoryBuilder setAutoCache(int delayInSeconds) {
        final long delay = delayInSeconds * 20L;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (canCache) {
                    cacheInventory();
                }
            }
        }.runTaskTimerAsynchronously(APIDatabase.getInstance().getPlugin(), 0, delay);
        return this;
    }

    public OpInventoryBuilder getOwnConsumer(@NotNull Consumer<OpInventoryBuilder> consumer) {
        consumer.accept(this);
        return this;
    }

    public boolean canCache() {
        return canCache;
    }

    public OpInventoryBuilder setCanCache(boolean canCache) {
        this.canCache = canCache;
        return this;
    }

    public OpInventoryBuilder addUnsafeButton(@NotNull OpItemBuilder builder, int slot) {
        getGraphicInterface().setButton(holderName, builder.getLoreAction() == null ? builder.setLoreAction(getFunction()) : builder, slot);
        return this;
    }

    public OpInventoryBuilder addUnsafeButton(@NotNull OpItemBuilder builder, int slot, Consumer<Player> action) {
        getGraphicInterface().setButton(holderName, builder.getLoreAction() == null ? builder.setLoreAction(getFunction()) : builder, slot, action);
        return this;
    }

    public OpInventoryBuilder addUnsafeButton(@NotNull OpItemBuilder builder, int slot, BiConsumer<Player, ItemStack> action) {
        getGraphicInterface().setButton(holderName, builder.getLoreAction() == null ? builder.setLoreAction(getFunction()) : builder, slot, action);
        return this;
    }

    public OpInventoryBuilder addTextButton(String path, int slot) {
        OpItemBuilder builder = new OpItemBuilder();
        if (defaultPath != null) {
            builder.setPath(defaultPath + path);
        } else {
            builder.setPath(path);
        }
        return addButton(builder, slot);
    }

    public OpInventoryBuilder addTextButton(String path, int slot, Consumer<Player> action) {
        OpItemBuilder builder = new OpItemBuilder();
        if (defaultPath != null) {
            builder.setPath(defaultPath + path);
        } else {
            builder.setPath(path);
        }
        return addButton(builder, slot, action);
    }

    public OpInventoryBuilder addTextButton(String path, int slot, BiConsumer<Player, ItemStack> action) {
        OpItemBuilder builder = new OpItemBuilder();
        if (defaultPath != null) {
            builder.setPath(defaultPath + path);
        } else {
            builder.setPath(path);
        }
        return addButton(builder, slot, action);
    }

    public OpInventoryBuilder addButton(OpItemBuilder builder, int slot) {
        if (!getGraphicInterface().isPlaceOccupied(holderName, slot)) {
            return addUnsafeButton(builder, slot);
        }
        return this;
    }

    public OpInventoryBuilder addButton(OpItemBuilder builder, int slot, Consumer<Player> action) {
        if (!getGraphicInterface().isPlaceOccupied(holderName, slot)) {
            return addUnsafeButton(builder, slot, action);
        }
        return this;
    }

    public OpInventoryBuilder addButton(OpItemBuilder builder, int slot, BiConsumer<Player, ItemStack> action) {
        if (!getGraphicInterface().isPlaceOccupied(holderName, slot)) {
            return addUnsafeButton(builder, slot, action);
        }
        return this;
    }

    public OpInventoryBuilder addButton(@NotNull Function<OpItemBuilder, OpItemBuilder> builder, int slot) {
        OpItemBuilder builder1 = builder.apply(new OpItemBuilder());
        return addButton(builder1, slot);
    }

    public OpInventoryBuilder addButton(@NotNull Function<OpItemBuilder, OpItemBuilder> builder, int slot, Consumer<Player> action) {
        OpItemBuilder builder1 = builder.apply(new OpItemBuilder());
        return addButton(builder1, slot, action);
    }

    public OpInventoryBuilder addButton(@NotNull Function<OpItemBuilder, OpItemBuilder> builder, int slot, BiConsumer<Player, ItemStack> action) {
        OpItemBuilder builder1 = builder.apply(new OpItemBuilder());
        return addButton(builder1, slot, action);
    }

    public String getHolderName() {
        return holderName;
    }

    private @NotNull Function<List<String>, List<String>> getTranslationsFunction() {
        return strings -> strings.stream().map(string -> {
            final String[] s2 = {string};
            translations.keySet().forEach(s -> s2[0] = replaceAndGet(s2[0], s, translations.getOrDefault(s, "")));
            return s2[0];
        }).collect(Collectors.toList());
    }

    public OpInventoryBuilder updateTranslationsAsFunction() {
        this.function = getTranslationsFunction();
        return this;
    }

    public OpInventoryBuilder addTranslationFunction(String key, String translation) {
        translations.replace(key, translation);
        return this;
    }

    public OpInventoryBuilder addTranslationsWithValue(@NotNull List<String> keys, String translation) {
        keys.forEach(key -> addTranslationFunction(key, translation));
        return this;
    }

    public OpInventoryBuilder addEmptyTranslation(String key) {
        return addTranslationFunction(key, "empty");
    }

    public OpInventoryBuilder fillVariable(int translationIndex, String translation) {
        String key = translations.getByIndex(translationIndex);
        if (key == null) {
            return this;
        }
        return addTranslationFunction(key, translation);
    }

    public OpInventoryBuilder fillVariable(int translationIndex, boolean translation) {
        String key = translations.getByIndex(translationIndex);
        if (key == null) {
            return this;
        }
        return addTranslationFunction(key, String.valueOf(translation));
    }

    public String getVariableValue(int index) {
        return translations.getValueByIndex(index);
    }

    public boolean hasVariable(int index) {
        return translations.keySet().size() > index;
    }

    public OpInventoryBuilder clearTranslations() {
        translations.getMap().clear();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OpInventoryBuilder builder = (OpInventoryBuilder) o;

        if (size != builder.size) return false;
        if (canCache != builder.canCache) return false;
        if (!Objects.equals(title, builder.title)) return false;
        if (!Objects.equals(holder, builder.holder)) return false;
        if (!Objects.equals(consumer, builder.consumer)) return false;
        if (Objects.equals(function, builder.function)) return false;
        if (!Objects.equals(graphicInterface, builder.graphicInterface)) return false;
        if (!Objects.equals(cachedInventory, builder.cachedInventory)) return false;
        if (!Objects.equals(holderName, builder.holderName)) return false;
        if (!translations.equals(builder.translations)) return false;
        return Objects.equals(DEFAULT_HOLDER, builder.DEFAULT_HOLDER);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + size;
        result = 31 * result + (holder != null ? holder.hashCode() : 0);
        result = 31 * result + (consumer != null ? consumer.hashCode() : 0);
        result = 31 * result + (function != null ? function.hashCode() : 0);
        result = 31 * result + (graphicInterface != null ? graphicInterface.hashCode() : 0);
        result = 31 * result + (cachedInventory != null ? cachedInventory.hashCode() : 0);
        result = 31 * result + (canCache ? 1 : 0);
        result = 31 * result + (holderName != null ? holderName.hashCode() : 0);
        result = 31 * result + translations.hashCode();
        result = 31 * result + DEFAULT_SIZE;
        result = 31 * result + DEFAULT_TITLE.hashCode();
        result = 31 * result + DEFAULT_HOLDER.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OpInventoryBuilder{" +
                "title='" + title + '\'' +
                ", size=" + size +
                ", holder=" + holder +
                ", consumer=" + consumer +
                ", function=" + function +
                ", cachedInventory=" + cachedInventory +
                ", canCache=" + canCache +
                ", holderName='" + holderName + '\'' +
                ", translations=" + translations +
                '}';
    }

    public OpInventoryBuilder clone() {
        try {
            return (OpInventoryBuilder) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public OpInventoryBuilder cloneWithClearGraphic() {
        return clone().clearGraphicInterface();
    }

    public OpInventoryBuilder clearGraphicInterface() {
        if (holderName != null) {
            graphicInterface.clearMapString(holderName);
        }
        return this;
    }

    public String getDefaultPath() {
        return defaultPath;
    }

    public OpInventoryBuilder setDefaultPath(String defaultPath) {
        this.defaultPath = defaultPath;
        return this;
    }

    public OpInventoryBuilder setAutoDefaultPath() {
        if (holderName != null) {
            return setDefaultPath(holderName + ".items.");
        }
        return this;
    }
}
