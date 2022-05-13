package me.opkarol.oppets.shops;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.graphic.IGetter;
import me.opkarol.oppets.items.OpItemBuilder;
import me.opkarol.oppets.items.OpItemShopData;
import me.opkarol.oppets.utils.FormatUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Shop implements IGetter {
    private final int price;
    private final String type;
    private final String path;
    private final OpItemBuilder item;

    public Shop(int price, String type, String path) {
        this.price = price;
        this.type = type;
        this.path = path;
        item = OpItemBuilder.getBuilder().setPath(getPath()).setShopData(new OpItemShopData(String.valueOf(price), type));
    }

    public int getPrice() {
        return price;
    }

    public String getPath() {
        return path;
    }

    public String getShopType() {
        return type;
    }

    @Override
    public GETTER_TYPE getGetterType() {
        return GETTER_TYPE.SHOP;
    }

    @Override
    public Object getObject() {
        return this;
    }

    public OpItemBuilder getItem() {
        return item;
    }

    public String getShopPrice() {
        return String.valueOf(price);
    }

    public Function<List<String>, List<String>> getFunction() {
        return strings -> strings.stream().map(s -> FormatUtils.formatMessage(s
                        .replace("%type%", getShopType())
                        .replace("%price%", getShopPrice())))
                .collect(Collectors.toList());
    }
}
