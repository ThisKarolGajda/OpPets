package me.opkarol.oppets.storage;

/*
 = Copyright (c) 2021-2022.
 = [OpPets] ThisKarolGajda
 = Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 = http://www.apache.org/licenses/LICENSE-2.0
 = Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

import me.opkarol.oppets.collections.map.OpMap;
import me.opkarol.oppets.misc.StringTransformer;
import me.opkarol.oppets.storage.builder.OpObjectCreator;
import me.opkarol.oppets.storage.builder.OpObjectTypes;
import me.opkarol.oppets.storage.types.OpBooleanObject;
import me.opkarol.oppets.storage.types.OpDoubleObject;
import me.opkarol.oppets.storage.types.OpIntegerObject;
import me.opkarol.oppets.storage.types.OpStringObject;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class OpObjects implements Serializable {
    private OpMap<String, OpObject> map = new OpMap<>();

    public OpObjects(OpMap<String, OpObject> map) {
        this.map = map;
    }

    public OpObjects(OpObject... objects) {
        if (objects == null || objects.length == 0) {
            return;
        }
        for (OpObject object : objects) {
            map.replace(object.getName(), object);
        }
    }

    public OpObjects(OpObjectCreator... objectCreators) {
        if (objectCreators == null || objectCreators.length == 0) {
            return;
        }
        for (OpObjectCreator objectCreator : objectCreators) {
            map.replace(objectCreator.getOpName(), objectCreator.getObject());
        }
    }

    public OpObjects() {}

    public OpObject getObject(String s) {
        Optional<OpObject> optional = map.getByKey(s);
        return optional.orElse(null);
    }

    public OpObjects addObject(OpObject object) {
        map.set(object.getName(), object);
        return this;
    }

    public boolean hasObject(String s) {
        return getObject(s) != null;
    }

    public void hasObjectAction(String s, Consumer<OpObject> consumer) {
        if (hasObject(s)) {
            consumer.accept(getObject(s));
        }
    }

    public OpMap<String, OpObject> getMap() {
        return map;
    }

    public String getString(String s, String defaultValue) {
        if (hasObject(s)) {
            OpObject object = getObject(s);
            if (object instanceof OpStringObject) {
                return (String) object.getValue();
            }
        }
        return defaultValue;
    }

    public boolean getBoolean(String s, boolean defaultValue) {
        if (hasObject(s)) {
            OpObject object = getObject(s);
            if (object instanceof OpBooleanObject) {
                return (boolean) object.getValue();
            }
        }
        return defaultValue;
    }

    public int getInteger(String s, int defaultValue) {
        if (hasObject(s)) {
            OpObject object = getObject(s);
            if (object instanceof OpIntegerObject) {
                return (int) object.getValue();
            }
        }
        return defaultValue;
    }

    public double getDouble(String s, double defaultValue) {
        if (hasObject(s)) {
            OpObject object = getObject(s);
            if (object instanceof OpDoubleObject) {
                return (double) object.getValue();
            }
        }
        return defaultValue;
    }

    public OpObjects get() {
        return this;
    }

    public OpObjects replaceObject(String s, Object value) {
        if (hasObject(s)) {
            OpObject object = getObject(s);
            OpObject object1 = new OpObjectCreator(object.getName(), object.getType(), value).getObject();
            map.set(s, object1);
        } else {
            map.set(s, new OpObjectCreator(s, OpObjectTypes.CUSTOM, value));
        }
        return this;
    }

    public OpObjects dump() {
        return new OpObjects();
    }

    public OpObjects negate(String s) {
        replaceObject(s, !getBoolean(s, true));
        return this;
    }

    public String toString() {
         return getMap().getValues().stream().map(OpObject::toString).collect(Collectors.joining(";"));
    }

    public OpObjects fromString(String s) {
        if (s == null || s.length() == 0) {
            return this;
        }
        String[] lines = s.split(";");
        for (String line : lines) {
            String[] rows = line.split("=");
            if (rows.length != 2) {
                continue;
            }
            String name = rows[0];
            Object value = rows[1];
            if (StringTransformer.isBoolean(value)) {
                boolean b = StringTransformer.getBooleanFromObject(value);
                addObject(new OpObjectCreator(name, OpObjectTypes.BOOL, b).getObject());
            } else {
                addObject(new OpObjectCreator(name, OpObjectTypes.STRING, value).getObject());
            }
        }
        return this;
    }

    public static OpObjects get(String s) {
        return new OpObjects().fromString(s);
    }

}
