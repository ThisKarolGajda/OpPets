package me.opkarol.oppets.storage;

public class OpIntegerObject extends OpObject {
    private final String name;
    private final Object value;

    protected OpIntegerObject(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public OpObjectTypes getType() {
        return OpObjectTypes.INT;
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
