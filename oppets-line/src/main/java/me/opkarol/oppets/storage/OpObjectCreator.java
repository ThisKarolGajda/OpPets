package me.opkarol.oppets.storage;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

public class OpObjectCreator extends OpObject {
    private final String name;
    private final OpObjectTypes type;
    private final Object value;
    private OpObject classType;

    public OpObjectCreator(String name, @NotNull OpObjectTypes type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
        if (type.getString() != null) {
            try {
                Class<?> clazz = Class.forName("me.opkarol.oppets.storage.Op" + type.getString() + "Object");
                classType = (OpObject) clazz.getDeclaredConstructor(String.class, Object.class).newInstance(name, value);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public OpObject getObject() {
        return classType == null ? this : classType;
    }

    public String getOpName() {
        return name;
    }

    @Override
    public OpObjectTypes getType() {
        return type;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }
}
