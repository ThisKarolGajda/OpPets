package me.opkarol.oppets.storage;

public class OpCustomObject<K> extends OpObject {
    private final String name;
    private final K value;

    public OpCustomObject(String name, K value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public OpObjectTypes getType() {
        return OpObjectTypes.CUSTOM;
    }

    @Override
    public K getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }
}
