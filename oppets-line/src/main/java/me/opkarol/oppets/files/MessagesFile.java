package me.opkarol.oppets.files;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.databases.APIDatabase;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * The type Messages file.
 */
public class MessagesFile {
    /**
     * The File.
     */
    private final File file;
    /**
     * The Data.
     */
    private FileConfiguration data;
    /**
     * The Messages.
     */
    private final Messages messages;

    /**
     * Instantiates a new Messages file.
     */
    public MessagesFile() {
        APIDatabase database = APIDatabase.getInstance();
        File childFile = database.getDataFolder();
        if (!childFile.exists()) {
            childFile.mkdir();
        }
        this.file = new File(childFile, "messages.yml");
        this.data = YamlConfiguration.loadConfiguration(this.file);
        this.setupFile();
        this.messages = new Messages(this.data).onEnable();
    }

    /**
     * Gets file.
     *
     * @return the file
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Sets file.
     */
    public void setupFile() {
        if (file.exists()) {
            return;
        }
        setPath("AnvilInventories.RenameInventory.titleRename", "&7Enter &f&lnew &7pet name!");
        setPath("AnvilInventories.RenameInventory.thisNameIsBlocked", "&cThis name is blocked.");
        setPath("AnvilInventories.RenameInventory.nameWithSpaces", "&c Pet name can't have spaces.");
        setPath("AnvilInventories.RenameInventory.changedName", "&7Changed pet name to &f&l%new_pet_name%&7!");
        setPath("AnvilInventories.RenameInventory.incorrectValueName", "&cInvalid value entered.");
        setPath("AnvilInventories.DeleteInventory.titleDelete", "&7Write &f&l%pet_name%&7 to confirm &ndeleting&r &7pet.");
        setPath("AnvilInventories.DeleteInventory.deletedMessage", "&7Deleted &f&l%pet_name%&7!");
        setPath("AnvilInventories.DeleteInventory.confirmMessage", "&7Write your pet name to confirm!");
        setPath("AnvilInventories.PrestigeInventory.titlePrestige", "&7Write &f&l%pet_name%&7 to confirm prestige-up.");
        setPath("AnvilInventories.PrestigeInventory.confirmPrestigeMessage", "&7Write your pet name to confirm!");
        setPath("Commands.badCommandUsage", "&cBad command usage! Use &f&l%proper_usage% &cinstead.");
        setPath("Commands.noPermission", "&cYou don't have permission &f&l%permission% &cto use this command.");
        setPath("Commands.noConsole", "&7Console cannot use this command.");
        setPath("Commands.petWithSameName", "&cYour pets have already the name &f&l%pet_name%&c.");
        setPath("Commands.wrongType", "&cSelected type &f&l%pet_type% &cis not available");
        setPath("Commands.createdPet", "&7Created &f&l%pet_name%&7, pet type of &f&l%pet_type%&7.");
        setPath("Commands.invalidPet", "&cPet is invalid!");
        setPath("Commands.petIsntGiftable", "&cPet isn't giftable!");
        setPath("Commands.petIsntRideable", "&cPet isn't rideable");
        setPath("Commands.receiverSameNamedPet", "&cReceiver already has pet named &f&l%pet_name%&c!");
        setPath("Commands.receiverInvalid", "&cReceiver is invalid.");
        setPath("Commands.petGifted", "&f&l%pet_name% &7has been gifted to &f&l%player_name%&7!");
        setPath("Commands.petBlockedName", "&cPet name contains blocked phrase &f&l%blocked_word%&c!");
        setPath("Commands.petListEmpty", "&cPet list is empty.");
        setPath("Commands.samePet", "&cYou have the same pet equipped.");
        setPath("Commands.summonedPet", "&7You have summoned pet &f&l%pet_name%&7!");
        setPath("Commands.cantPrestige", "&cYour pet cannot prestige now. Need &f&l%more_levels% &cmore levels to be able to!");
        setPath("Commands.changedAdminValue", "&7Set value: &f&l%value%&7 for object: &f&l%key%&7, for &f&l%player_name%&7 pet: &f&l%pet_name%&7!");
        setPath("Commands.calledSuccessfully", "&7Your pet has been called successfully!");
        setPath("Commands.boostersListFormat", "%name% - %type% - %multiplier% - %running%");
        setPath("Commands.broadcastListFormat", "%prefix% - %type% - %format%");
        setPath("Commands.invalidBooster", "&cInvalid booster.");
        setPath("Commands.invalidObjectProvided", "&cProvided object / argument is invalid.");
        setPath("Messages.petLevelUpMessage", "&2-------------------------------%newline%&eNew level &6&l%pet_name%&e!%newline%&eYour pet is now level &6&l%current_level%&e!%newline%Your pet needs &6&l%experience_level% &eexp &eto reach next level.%newline%&2-------------------------------%newline%");
        setPath("Messages.prestigeUpMessage", "&2-------------------------------%newline%&eNew prestige &6&l%pet_name%&e!%newline%&eYour pet is now on prestige &6&l%current_prestige%&e!%newline%Your pet needs &6&l%max_level% &elevels &eto reach next prestige.%newline%&2-------------------------------%newline%");
        setPath("Messages.notEnoughMoney", "&cYou don't have enough money to buy it.");
        setPath("Messages.successfullyBought", "&7You successfully purchased it.");
        saveData();
    }

    /**
     * Reload data.
     */
    public void reloadData() {
        this.data = YamlConfiguration.loadConfiguration(this.file);
    }

    /**
     * Gets messages access.
     *
     * @return the messages access
     */
    public Messages getMessagesAccess() {
        return this.messages;
    }

    /**
     * Sets path.
     *
     * @param path   the path
     * @param object the object
     */
    private void setPath(String path, Object object) {
        this.data.set(path, object);
    }

    /**
     * Save data.
     */
    public void saveData() {
        try {
            data.save(file);
        }
        catch (IOException e) {
            Bukkit.getServer().getLogger().warning("Could not save messages.yml!");
        }
    }

}
