package gof.proxy;

public interface Database<K, V> {
    V read(K key);

    void write(K key, V value);
}
