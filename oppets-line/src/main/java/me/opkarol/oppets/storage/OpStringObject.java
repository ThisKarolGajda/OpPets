package me.opkarol.oppets.storage;

public class OpStringObject extends OpObject {
    private final String name;
    private final Object value;

    protected OpStringObject(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public OpObjectTypes getType() {
        return OpObjectTypes.STRING;
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
