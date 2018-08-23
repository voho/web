package ds.list;

import java.util.Iterator;

public interface List<DATA> extends Iterable<DATA> {
    /**
     * Adds an element to the end of this list.
     *
     * @param value value to be added
     */
    void add(DATA value);

    /**
     * Removes a value from this list.
     *
     * @param value value to be removed
     * @return TRUE if the value was found and removed, FALSE otherwise
     */
    boolean remove(DATA value);

    /**
     * Returns an iterator.
     *
     * @return new iterator instance
     */
    @Override
    Iterator<DATA> iterator();
}
