package me.opkarol.oppets;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.cache.NamespacedKeysCache;
import me.opkarol.oppets.collections.map.OpMap;
import me.opkarol.oppets.commands.builder.OpCommandBuilder;
import me.opkarol.oppets.commands.OpSubCommand;
import me.opkarol.oppets.commands.OpPetsCommandExecutor;
import me.opkarol.oppets.databases.Database;
import me.opkarol.oppets.entities.manager.IEntityManager;
import me.opkarol.oppets.files.manager.FileManager;
import me.opkarol.oppets.files.MessagesHolder;
import me.opkarol.oppets.graphic.GraphicInterface;
import me.opkarol.oppets.interfaces.IUtils;
import me.opkarol.oppets.inventory.OpInventories;
import me.opkarol.oppets.listeners.*;
import me.opkarol.oppets.misc.external.bstats.Metrics;
import me.opkarol.oppets.versions.ServerVersion;
import me.opkarol.oppets.packets.IPacketPlayInSteerVehicleEvent;
import me.opkarol.oppets.packets.PacketManager;
import me.opkarol.oppets.pets.Pet;
import me.opkarol.oppets.utils.external.MathUtils;
import me.opkarol.oppets.utils.OpUtils;
import me.opkarol.oppets.utils.external.PDCUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class PetPluginController {
    private final Database database = Database.getInstance();
    private final OpPets instance;
    private IPacketPlayInSteerVehicleEvent packetEvent;
    private String version;
    private OpPetsCommandExecutor executor;

    public PetPluginController(OpPets opPets) {
        this.instance = opPets;
        init();
    }

    public OpPets getInstance() {
        return instance;
    }

    public void init() {
        getInstance().saveDefaultConfig();
        registerCommands();
        bStatsActivation(13211);
    }

    public void bStatsActivation(int pluginId) {
        Metrics metrics = new Metrics(getInstance(), pluginId);
        metrics.addCustomChart(new Metrics.SingleLineChart("pets", () -> database.getDatabase().getPetsMap().keySet().stream().mapToInt(uuid -> database.getDatabase().getPetList(uuid).size()).sum()));
    }

    public void saveFiles() {
        executor.getCommandListener().unregister();
        Bukkit.getOnlinePlayers().forEach(player -> database.getDatabase().databaseUUIDSaver(player.getUniqueId(), false));
        new FileManager<OpMap<UUID, List<Pet>>>("database/Pets.txt").saveObject(database.getDatabase().getPetsMap());
        new FileManager<OpMap<UUID, Pet>>("database/ActivePets.txt").saveObject(database.getDatabase().getActivePetMap());
        killAllPets();
    }

    public void registerEvents() {
        PluginManager manager = instance.getServer().getPluginManager();
        manager.registerEvents(new PlayerListeners(), instance);
        manager.registerEvents(new SkillsListeners(), instance);
        manager.registerEvents(new PetListeners(), instance);
        manager.registerEvents(new ChatReceiver(), instance);
        manager.registerEvents(new PetAbilities(), instance);
        manager.registerEvents(new GraphicInterface(), instance);
    }

    public void registerCommands() {
        OpMap<String, OpSubCommand> builder = new OpCommandBuilder().addKey("Addons").addKey("Admin").addKey("Booster").addKey("Call").addKey("Create").addKey("Delete").addKey("Eggs").addKey("Gift").addKey("Help").addKey("Leaderboard").addKey("Preferences").addKey("Prestige").addKey("Rename").addKey("Ride").addKey("Shop").addKey("Summon")
                .addDummyKey("respawn", (commandSender, strings) -> {
                    //TODO add cooldown
                    if (commandSender instanceof Player player) {
                        Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
                        database.getUtils().respawnPet(pet, player);
                        player.sendMessage(MessagesHolder.getInstance().getString("Commands.respawnedSuccessfully"));
                    }
                })
                .addDummyKey("settings", (commandSender, strings) -> {
                    if (commandSender instanceof Player player) {
                        Pet pet = database.getDatabase().getCurrentPet(player.getUniqueId());
                        player.openInventory(new OpInventories.SettingsInventory(pet).buildInventory());
                    }
                })
                .getMap();
        executor = new OpPetsCommandExecutor(builder);
    }

    public void killAllPets() {
        database.getDatabase().getActivePetMap().keySet().forEach(uuid -> {
            if (uuid != null) {
                OpUtils.killPetFromPlayerUUID(uuid);
            }
        });
        Bukkit.getWorlds()
                .forEach(world -> world.getLivingEntities().stream()
                        .filter(entity -> (!(entity instanceof Player)))
                        .filter(entity -> PDCUtils.hasNBT(entity, NamespacedKeysCache.petKey))
                        .forEach(LivingEntity::remove));
    }

    public boolean setupVersion() {
        String version;
        try {
            version = Bukkit.getBukkitVersion().split("-")[0];
            this.setVersion(version);
        } catch (ArrayIndexOutOfBoundsException exception) {
            instance.disablePlugin(exception.getCause().getMessage());
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
            case "1.18", "1.18.1" -> versionR = "v1_18_1R.";
            case "1.18.2" -> versionR = "v1_18_2R.";
            default -> throw new IllegalStateException("Unexpected value: " + getVersion());
        }
        String substring = MathUtils.substringFromEnd(versionR, 2);
        try {
            ServerVersion.setSeverVersion(MathUtils.substringFromEnd(versionR, 1));
            versionR = "me.opkarol.oppets." + versionR;
            IUtils utils = (IUtils) Class.forName(versionR + "Utils").newInstance();
            instance.setUtils(utils);
            PacketManager.setUtils(utils);
            instance.setEntityManager((IEntityManager) Class.forName(versionR + "entities.EntityManager").newInstance());
            this.setPacketEvent((IPacketPlayInSteerVehicleEvent) Class.forName(versionR + "PacketPlayInSteerVehicleEvent_" + substring).newInstance());
            manager.registerEvents((Listener) Class.forName(versionR + "PlayerSteerVehicleEvent_" + substring).newInstance(), instance);
            PacketManager.setEvent(getPacketEvent());
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    public IPacketPlayInSteerVehicleEvent getPacketEvent() {
        return this.packetEvent;
    }

    public void setPacketEvent(IPacketPlayInSteerVehicleEvent packetEvent) {
        this.packetEvent = packetEvent;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public @Nullable Object setupEconomy() {
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
