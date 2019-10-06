package gof.proxy;

import java.util.HashMap;
import java.util.Map;

public class SimpleDatabase<K, V> implements Database<K, V> {
    private final Map<K, V> storage = new HashMap<>();

    @Override
    public V read(final K key) {
        return storage.get(key);
    }

    @Override
    public void write(final K key, final V value) {
        storage.put(key, value);
    }
}
