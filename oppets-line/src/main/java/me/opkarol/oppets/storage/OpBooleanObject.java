package me.opkarol.oppets.storage;

public class OpBooleanObject extends OpObject {
    private final String name;
    private final Object value;

    protected OpBooleanObject(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public OpObjectTypes getType() {
        return OpObjectTypes.BOOL;
    }

    @Override
    public Object getValue() {
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
