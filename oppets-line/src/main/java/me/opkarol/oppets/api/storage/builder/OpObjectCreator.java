package me.opkarol.oppets.api.storage.builder;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.api.storage.OpObject;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

public class OpObjectCreator extends OpObject {
    private final String name;
    private final OpObjectTypes type;
    private final Object value;
    private OpObject classType;

    public OpObjectCreator(String name, @NotNull OpObjectTypes type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
        if (type.getString() != null) {
            try {
                Class<?> clazz = Class.forName("me.opkarol.oppets.api.storage.types.Op" + type.getString() + "Object");
                classType = (OpObject) clazz.getDeclaredConstructor(String.class, Object.class).newInstance(name, value);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public OpObject getObject() {
        return classType == null ? this : classType;
    }

    public String getOpName() {
        return name;
    }

    @Override
    public OpObjectTypes getType() {
        return type;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String getName() {
        return name;
    }
}
