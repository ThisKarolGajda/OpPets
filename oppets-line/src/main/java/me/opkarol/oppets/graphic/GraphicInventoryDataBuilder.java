package me.opkarol.oppets.graphic;

import me.opkarol.oppets.interfaces.IHolder;

public class GraphicInventoryDataBuilder implements IGraphicInventoryData {
    private IHolder holder;
    private int size;
    private String title;

    public GraphicInventoryDataBuilder(IHolder holder, int size, String title) {
        this();
        setHolder(holder);
        setSize(size);
        setTitle(title);
    }

    public GraphicInventoryDataBuilder() {}

    @Override
    public IHolder getHolder() {
        return holder;
    }

    public GraphicInventoryDataBuilder setHolder(IHolder holder) {
        this.holder = holder;
        return this;
    }

    @Override
    public int getSize() {
        return size;
    }

    public GraphicInventoryDataBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public GraphicInventoryDataBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public IGraphicInventoryData getData() {
        return this;
    }

    public GraphicInventoryDataBuilder dump() {
        return new GraphicInventoryDataBuilder();
    }
}
