package gof.proxy;

public class LoggingDatabase<K, V> implements Database<K, V> {
    private final Database<K, V> delegate;

    public LoggingDatabase(final Database<K, V> delegate) {
        this.delegate = delegate;
    }

    @Override
    public V read(final K key) {
        log("Reading from key: " + key);
        return delegate.read(key);
    }

    @Override
    public void write(final K key, final V value) {
        log("Writing to key: " + key);
        delegate.write(key, value);
    }

    private void log(final String message) {
        // (implement logging here)
        System.out.println(message);
    }
}