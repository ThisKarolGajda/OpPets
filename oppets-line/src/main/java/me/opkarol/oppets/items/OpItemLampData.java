package me.opkarol.oppets.items;

import org.bukkit.Material;

public class OpItemLampData {
    private final Material itemLighted;
    private final Material itemNotLighted;
    private final boolean light;

    public OpItemLampData(Material itemLighted, Material itemNotLighted, boolean light) {
        this.itemLighted = itemLighted;
        this.itemNotLighted = itemNotLighted;
        this.light = light;
    }

    public OpItemLampData(boolean light) {
        this.itemLighted = Material.GREEN_CONCRETE;
        this.itemNotLighted = Material.BLACK_CONCRETE;
        this.light = light;
    }

    public boolean isLighted() {
        return light;
    }

    public Material getItemLighted() {
        return itemLighted;
    }

    public Material getItemNotLighted() {
        return itemNotLighted;
    }

    public Material getSelectedType() {
        return this.isLighted() ? this.getItemLighted() : this.getItemNotLighted();
    }
}
