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

/**
 * The type File manager.
 *
 * @param <K> the type parameter
 */
public class FileManager<K> {

    /**
     * Load object k.
     *
     * @param path the path
     * @return the k
     */
    @Nullable
    public K loadObject(String path) {
        File file = new File(path);
        if (!file.isFile() || !file.exists()) {
            return null;
        }
        try {
            return readFile(path);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Save object.
     *
     * @param path         the path
     * @param objectToSave the object to save
     */
    public void saveObject(String path, K objectToSave) {
        try {
            saveFile(objectToSave, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save file.
     *
     * @param object the object
     * @param path   the path
     * @throws IOException the io exception
     */
    private void saveFile(K object, String path)
            throws IOException {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path))) {
            os.writeObject(object);
        }
    }

    /**
     * Read file k.
     *
     * @param path the path
     * @return the k
     * @throws ClassNotFoundException the class not found exception
     * @throws IOException            the io exception
     */
    private K readFile(String path)
            throws ClassNotFoundException, IOException {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(path))) {
            return (K) is.readObject();
        }
    }
}
