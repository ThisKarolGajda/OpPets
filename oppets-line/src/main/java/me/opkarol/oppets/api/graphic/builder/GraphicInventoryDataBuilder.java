package me.opkarol.oppets.api.graphic.builder;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

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
