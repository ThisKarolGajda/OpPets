package me.opkarol.oppets.files;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.collections.OpMap;
import me.opkarol.oppets.databases.APIDatabase;
import me.opkarol.oppets.exceptions.ExceptionLogger;
import me.opkarol.oppets.utils.FormatUtils;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class MessagesHolder {
    private final Messages messages;
    private final InventoriesMessages inventoriesMessages;
    private static MessagesHolder messagesHolder;
    private final ExceptionLogger logger;

    public MessagesHolder() {
        messagesHolder = this;
        messages = new Messages();
        inventoriesMessages = new InventoriesMessages();
        logger = ExceptionLogger.getInstance();
    }

    public Messages getMessages() {
        return messages;
    }

    public InventoriesMessages getInventoriesMessages() {
        return inventoriesMessages;
    }

    public static MessagesHolder getInstance() {
        if (messagesHolder == null) {
            new MessagesHolder();
        }
        return messagesHolder;
    }

    public @NotNull String getString(String path) {
        String current = getMessages().getString(path) == null ? getInventoriesMessages().getString(path) : getMessages().getString(path);
        if (current != null) {
            return current;
        }
        logger.throwException("Cannot find specified type in configuration file: " + path);
        return path;
    }

    public Object getValue(String path) {
        return getInventoriesMessages().getValue(path);
    }

    public static class InventoriesMessages extends IConfigFile<Object> {

        public InventoriesMessages() {
            super("inventories.json");
            createConfig();
            setupFile();
        }

        private void setupFile() {
            JSONParser jsonParser = new JSONParser();
            try (FileReader reader = new FileReader(APIDatabase.getInstance().getPlugin().getDataFolder() + "\\" + getYmlConfiguration().getName())) {
                Object current = jsonParser.parse(reader);
                if (current instanceof JSONObject) {
                    loopValue((JSONObject) current, null);
                }
            } catch (IOException | ParseException e) {
                ExceptionLogger.getInstance().throwException("Couldn't find inventories.json file in OpPets directory!");
            }
        }

        private void loopValue(@NotNull JSONObject object, String path) {
            String NULL_STARTER = "null.";
            if (path != null && path.startsWith(NULL_STARTER)) {
                path = path.substring(NULL_STARTER.length());
            }
            for (Object key : object.keySet()) {
                Object obj = object.get(key);
                final String directory = path + "." + key;
                if (obj instanceof JSONObject) {
                    loopValue((JSONObject) obj, directory);
                } else {
                    addValue(directory, obj);
                }
            }
        }

        public String getString(String path) {
            OpMap<String, Object> map = getMap();
            if (map == null) {
                return null;
            }
            if (map.containsKey(path)) {
                Object object = map.getOrDefault(path, null);
                if (object instanceof String) {
                    return FormatUtils.formatMessage((String) object);
                }
            }
            String configValue = getConfiguration().getString(path);
            if (configValue != null) {
                return FormatUtils.formatMessage(configValue);
            }
            return null;
        }

        public Object getValue(String path) {
            OpMap<String, Object> map = getMap();
            if (map == null) {
                return null;
            }
            if (map.containsKey(path)) {
                return map.getOrDefault(path, null);
            }
            return null;
        }
    }

    public static class Messages extends IConfigFile<String> {

        public Messages() {
            super("messages");
            createConfig();
            addSectionToMap(true,"Commands", "Messages", "Formats");
        }

        public String getString(String name) {
            OpMap<String, String> map = getMap();
            if (map == null) {
                return null;
            }
            if (map.containsKey(name)) {
                return FormatUtils.formatMessage(map.getOrDefault(name, null));
            }
            String configValue = getConfiguration().getString(name);
            if (configValue != null) {
                return FormatUtils.formatMessage(configValue);
            }
            return null;
        }
    }
}