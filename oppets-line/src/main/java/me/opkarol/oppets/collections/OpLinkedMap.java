package me.opkarol.oppets.collections;

import java.util.*;

public class OpLinkedMap<K, V> implements IMap<K, V>, Cloneable {
    protected LinkedHashMap<K, V> map = new LinkedHashMap<>();

    @Override
    public void put(K k, V v) {
        this.map.put(k, v);
    }

    @Override
    public void remove(K k) {
        if (this.isEmpty()) {
            return;
        }
        this.map.remove(k);
    }

    @Override
    public void remove(K k, V v) {
        if (this.isEmpty()) {
            return;
        }
        this.map.remove(k, v);
    }

    @Override
    public void set(K k, V v) {
        if (this.containsKey(k)) {
            this.map.replace(k, v);
        } else {
            this.map.put(k, v);
        }
    }

    @Override
    public void setMap(HashMap<K, V> map) {
        this.map = (LinkedHashMap<K, V>) map;
    }

    @Override
    public boolean containsKey(K k) {
        if (this.isEmpty()) {
            return false;
        }
        return this.map.containsKey(k);
    }

    @Override
    public boolean containsValue(V v) {
        if (this.isEmpty()) {
            return false;
        }
        return this.map.containsValue(v);
    }

    @Override
    public Map<K, V> getMap() {
        return map;
    }

    @Override
    public Optional<V> getByKey(K k) {
        if (k == null || this.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(map.get(k));
    }

    @Override
    public Collection<V> getValues() {
        if (this.isEmpty()) {
            return new ArrayList<>();
        }
        return getMap().values();
    }

    @Override
    public V getOrDefault(K key, V defaultValue) {
        if (this.isEmpty()) {
            return defaultValue;
        }
        V get = getMap().get(key);
        return get == null ? defaultValue : get;
    }

    @Override
    public V replace(K key, V value) {
        this.set(key, value);
        Optional<V> val = this.getByKey(key);
        if (val.isPresent() && val.get().equals(value)) {
            return value;
        }
        return null;
    }

    @Override
    public Set<K> keySet() {
        if (this.isEmpty()) {
            return new HashSet<>();
        }
        return getMap().keySet();
    }

    @Override
    public boolean isEmpty() {
        return map == null || map.isEmpty();
    }

    @Override
    public Optional<K> getFromIndex(int index) {
        return Optional.empty();
    }

    public K getByIndex(int index){
        return (K) (map.keySet().toArray())[index];
    }

    public V getValueByIndex(int index) {
        return map.get(getByIndex(index));
    }
}
