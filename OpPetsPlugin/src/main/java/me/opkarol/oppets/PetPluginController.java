package me.opkarol.oppets;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.cache.NamespacedKeysCache;
import me.opkarol.oppets.commands.OpPetsCommand;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.files.FileManager;
import me.opkarol.oppets.interfaces.IBabyEntityCreator;
import me.opkarol.oppets.interfaces.IEntityManager;
import me.opkarol.oppets.interfaces.IPacketPlayInSteerVehicleEvent;
import me.opkarol.oppets.interfaces.IUtils;
import me.opkarol.oppets.listeners.*;
import me.opkarol.oppets.misc.Metrics;
import me.opkarol.oppets.packets.PacketManager;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.PDCUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * PetPluginController is a controlling class that can run methods
 * which would be redundant in the main OpPets class.
 * <p>
 * This public class contains methods that helps build and compile
 * OpPets but also has some Logic, File Managers and MySQL access.
 */
public class PetPluginController {
    /**
     * The Instance.
     */
    private final OpPets instance;
    /**
     * The Local path.
     */
    private final String localPath = Database.getInstance().getDataFolder().getAbsolutePath();
    /**
     * The Packet event.
     */
    private IPacketPlayInSteerVehicleEvent packetEvent;
    /**
     * The Version.
     */
    private String version;

    /**
     * Instantiates a new Pet plugin controller.
     *
     * @param opPets the op pets
     */
    public PetPluginController(OpPets opPets) {
        this.instance = opPets;
        init();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public OpPets getInstance() {
        return instance;
    }

    /**
     * Plugin initialization method that runs File Configuration saver, loading files
     * Database logic and Registers event and Commands.
     * It's also used to set up InventoryManager and activate bStats with plugin id.
     */
    public void init() {
        getInstance().saveDefaultConfig();
        loadFiles();
        registerEvents();
        registerCommands();
        bStatsActivation(13211);
    }

    /**
     * Void method which run track operation that hooks to your server,
     * which also counts pet numbers in the server.
     *
     * @param pluginId plugin id used to connect with bStats site
     * @see me.opkarol.oppets.misc.Metrics
     */
    public void bStatsActivation(int pluginId) {
        Metrics metrics = new Metrics(getInstance(), pluginId);
        metrics.addCustomChart(new Metrics.SingleLineChart("pets", () -> Database.getDatabase().getPetsMap().keySet().stream().mapToInt(uuid -> Database.getDatabase().getPetList(uuid).size()).sum()));
    }

    /**
     * Method which runs in OpPets onDisable method.
     * This saves mySql and normal database files to a simple connector.
     * Can be used in both ways.
     *
     * @see me.opkarol.oppets.OpPets
     */
    public void saveFiles() {
        if (!Database.mySQLAccess) {
            new FileManager<HashMap<UUID, List<Pet>>>().saveObject(localPath + "/PetsMap.db", Database.getDatabase().getPetsMap());
            new FileManager<HashMap<UUID, Pet>>().saveObject(localPath + "/ActivePetMap.db", Database.getDatabase().getActivePetMap());
        } else {
            Bukkit.getOnlinePlayers().forEach(player -> Database.getDatabase().databaseUUIDSaver(player.getUniqueId(), false));
        }
        killAllPets();
    }

    /**
     * Method which runs in OpPets onEnable method.
     * Only used if there isn't active mysql connection,
     * because mysql updates itself with every type connection.
     *
     * @see me.opkarol.oppets.OpPets
     */
    public void loadFiles() {
        if (!Database.mySQLAccess) {
            Database.getDatabase().setPetsMap(new FileManager<HashMap<UUID, List<Pet>>>().loadObject(localPath + "/PetsMap.db"));
            Database.getDatabase().setActivePetMap(new FileManager<HashMap<UUID, Pet>>().loadObject(localPath + "/ActivePetMap.db"));
        }
    }

    /**
     * Method used to registerEvents which were previously provided.
     *
     * @see org.bukkit.plugin.PluginManager
     */
    public void registerEvents() {
        PluginManager manager = instance.getServer().getPluginManager();
        manager.registerEvents(new PlayerJoin(), instance);
        manager.registerEvents(new PlayerLeaves(), instance);
        manager.registerEvents(new PlayerInteract(), instance);
        manager.registerEvents(new SkillsListeners(), instance);
        manager.registerEvents(new PetListeners(), instance);
        manager.registerEvents(new ChatReceiver(), instance);
        manager.registerEvents(new PetAbilities(), instance);
    }

    /**
     * Method used to register not null commands which registers both
     * Executor and TabCompleter in a MainCommand class.
     *
     * @see me.opkarol.oppets.commands.OpPetsCommand
     */
    public void registerCommands() {
        Objects.requireNonNull(getInstance().getCommand("oppets")).setExecutor(new OpPetsCommand());
        Objects.requireNonNull(getInstance().getCommand("oppets")).setTabCompleter(new OpPetsCommand());
    }

    /**
     * Final method which loops through every UUID from activePetsMap key set
     * and checks if it's valid.
     * If result is valid, it can be successfully removed using Bukkit method.
     */
    public void killAllPets() {
        Database.getDatabase().getActivePetMap().keySet().forEach(uuid -> {
            if (uuid != null) {
                Database.getUtils().killPetFromPlayerUUID(uuid);
            }
        });
        Bukkit.getWorlds()
                .forEach(world -> world.getLivingEntities().stream()
                        .filter(entity -> (!(entity instanceof Player)))
                        .filter(entity -> PDCUtils.hasNBT(entity, NamespacedKeysCache.petKey))
                        .forEach(LivingEntity::remove));
    }

    /**
     * This is a main method of this class, which provides classified information about every
     * version, and sets it to main OpPets class, PacketManager class and Database class.
     *
     * @return the version
     */
    public boolean setupVersion() {
        String version;
        try {
            version = Bukkit.getBukkitVersion().split("-")[0];
            this.setVersion(version);
        } catch (ArrayIndexOutOfBoundsException var5) {
            new OpPets().disablePlugin(var5.getCause().getMessage());
            return false;
        }

        this.instance.getLogger().info("Your server is running version " + version);
        PluginManager manager = this.instance.getServer().getPluginManager();
        String versionR;
        switch (getVersion()) {
            case "1.16", "1.16.1", "1.16.2" -> versionR = "v1_16_1R.";
            case "1.16.3", "1.16.4" -> versionR = "v1_16_3R.";
            case "1.16.5" -> versionR = "v1_16_5R.";
            case "1.17" -> versionR = "v1_17R.";
            case "1.17.1" -> versionR = "v1_17_1R.";
            case "1.18" -> versionR = "v1_18_1R.";
            default -> throw new IllegalStateException("Unexpected value: " + getVersion());
        }
        String substring = versionR.substring(0, versionR.length() - 2);

        try {
            OpPets.setCreator((IBabyEntityCreator) Class.forName(versionR + "BabyEntityCreator").newInstance());
            OpPets.setEntityManager((IEntityManager) Class.forName(versionR + "entities.EntityManager").newInstance());
            this.setPacketEvent((IPacketPlayInSteerVehicleEvent) Class.forName(versionR + "PacketPlayInSteerVehicleEvent_" + substring).newInstance());
            manager.registerEvents((Listener) Class.forName(versionR + "PlayerSteerVehicleEvent_" + substring).newInstance(), this.instance);
            IUtils utils = (IUtils) Class.forName(versionR + "Utils").newInstance();
            OpPets.setUtils(utils);
            PacketManager.setUtils(utils);
            Database.setUtils(utils);
            PacketManager.setEvent(getPacketEvent());
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Gets packet event.
     *
     * @return the packet event
     */
    public IPacketPlayInSteerVehicleEvent getPacketEvent() {
        return this.packetEvent;
    }

    /**
     * Sets packet event.
     *
     * @param packetEvent the packet event
     */
    public void setPacketEvent(IPacketPlayInSteerVehicleEvent packetEvent) {
        this.packetEvent = packetEvent;
    }

    /**
     * Gets version.
     *
     * @return the version
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Sets version.
     *
     * @param version the version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Sets economy.
     *
     * @return the economy
     */
    public @Nullable Economy setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return null;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return null;
        }
        return rsp.getProvider();
    }
}
