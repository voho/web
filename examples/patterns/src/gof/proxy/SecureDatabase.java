package gof.proxy;

public class SecureDatabase<K, V> implements Database<K, V> {
    private final Database<K, V> delegate;

    public SecureDatabase(final Database<K, V> delegate) {
        this.delegate = delegate;
    }

    @Override
    public V read(final K key) {
        if (canRead()) {
            return delegate.read(key);
        } else {
            throw new IllegalAccessError("Read access denied.");
        }
    }

    @Override
    public void write(final K key, final V value) {
        if (canWrite()) {
            delegate.write(key, value);
        } else {
            throw new IllegalAccessError("Write access denied.");
        }
    }

    private boolean canRead() {
        // add security logic here
        return false;
    }

    private boolean canWrite() {
        // add security logic here
        return false;
    }
}