package me.opkarol.oppets.storage;

public class OpDoubleObject extends OpObject {
    private final String name;
    private final Object value;

    protected OpDoubleObject(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public OpObjectTypes getType() {
        return OpObjectTypes.DOUBLE;
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
