package me.opkarol.oppets.storage;

public enum OpObjectTypes {
    STRING("String"),
    DOUBLE("Double"),
    INT("Integer"),
    BOOL("Boolean"),
    CUSTOM("Custom");

    private final String string;

    OpObjectTypes(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
