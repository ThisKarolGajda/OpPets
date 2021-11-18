package me.opkarol.oppets;

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

    public static void saveObject(String path, Object objectToSave){
        try {
            saveFile(objectToSave, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void saveFile(Object object, String path)
            throws IOException
    {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path))) {
            os.writeObject(object);
        }
    }

    private static Object readFile(String path)
            throws ClassNotFoundException, IOException
    {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(path))) {
            return is.readObject();
        }
    }
}
