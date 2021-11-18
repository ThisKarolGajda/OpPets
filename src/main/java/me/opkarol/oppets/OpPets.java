package me.opkarol.oppets;

import me.opkarol.oppets.api.OpPetsAPI;
import me.opkarol.oppets.inventories.InventoryManager;
import me.opkarol.oppets.pets.BabyEntityCreator;
import me.opkarol.oppets.pets.MiniPetsDatabase;
import org.bukkit.plugin.java.JavaPlugin;

public final class OpPets extends JavaPlugin {
    private static OpPets opPets;
    private static PetPluginController controller;
    private static MiniPetsDatabase database;
    private static BabyEntityCreator creator;
    private static OpPetsAPI api;
    private static InventoryManager inventoryManager;

    @Override
    public void onEnable() {
        opPets = this;
        database = new MiniPetsDatabase();
        creator = new BabyEntityCreator();
        inventoryManager = new InventoryManager();
        api = new OpPetsAPI();
        controller = new PetPluginController(opPets);

    }

    @Override
    public void onDisable() {
        getController().saveFiles();
        opPets = null;
        database = null;
        creator = null;
        controller = null;
    }

    public static PetPluginController getController() {
        return controller;
    }

    public static OpPets getInstance() {
        return opPets;
    }

    public static MiniPetsDatabase getDatabase() {
        return database;
    }

    public static BabyEntityCreator getCreator() {
        return creator;
    }

    public static OpPetsAPI getAPI() {
        return api;
    }

    public static InventoryManager getInventoryManager() {
        return inventoryManager;
    }
}
