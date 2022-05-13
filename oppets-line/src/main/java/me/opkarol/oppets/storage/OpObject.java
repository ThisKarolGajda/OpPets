package me.opkarol.oppets.storage;

import java.io.Serializable;

public abstract class OpObject implements Serializable {

    public OpObjectTypes getType() {
        return OpObjectTypes.CUSTOM;
    }

    public abstract Object getValue();

    public abstract String getName();
}
