package me.opkarol.oppets.files;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import org.jetbrains.annotations.Nullable;

import java.io.*;

public class FileManager {

    @Nullable
    public static Object loadObject(String path) {
        try {
            File file = new File(path);
            if (!file.isFile() || !file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return readFile(path);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveObject(String path, Object objectToSave) {
        try {
            saveFile(objectToSave, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveFile(Object object, String path)
            throws IOException {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path))) {
            os.writeObject(object);
        }
    }

    private static Object readFile(String path)
            throws ClassNotFoundException, IOException {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(path))) {
            return is.readObject();
        }
    }
}
