package me.opkarol.oppets.pets;

import me.opkarol.oppets.storage.OpObjects;
import org.jetbrains.annotations.NotNull;

public interface IPetsConverter {

    default OpObjects negatePetObjects(OpObjects objects, String s) {
        if (hasPetObject(objects, s)) {
            objects.negate(s);
            return objects;
        }
        return objects;
    }

    default boolean hasPetObject(@NotNull OpObjects objects, String s) {
        return objects.hasObject(s);
    }

    default boolean readPetObject(OpObjects objects, String s) {
        if (hasPetObject(objects, s)) {
            return objects.getBoolean(s, false);
        }
        return false;
    }
}
