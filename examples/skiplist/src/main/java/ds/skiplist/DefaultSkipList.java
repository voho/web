package ds.skiplist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.Optional;

/**
 * Skip list implementation strongly based on the original article.
 */
public class DefaultSkipList<K extends Comparable<? super K>, V> implements SkipList<K, V> {
    /**
     * logger instance
     */
    private static final Logger log = LoggerFactory.getLogger(DefaultSkipList.class);
    /**
     * probability of skipping to a higher level
     */
    private static final double P_LEVEL_SKIP = 0.5;
    /**
     * header element (always the same, never empty)
     */
    private final Element header;
    /**
     * maximum number of levels allowed
     */
    private final int maxNumberOfLevels;
    /**
     * top level currently in the list
     */
    private int topLevel;

    public DefaultSkipList(final int maxNumberOfLevels) {
        this.maxNumberOfLevels = maxNumberOfLevels;
        this.topLevel = 0;
        this.header = new Header();
    }

    // READING
    // =======

    @Override
    public Optional<V> get(final K keyToSearchFor) {
        final Element x = lookup(keyToSearchFor, null);

        if (x != null && x.key.equals(keyToSearchFor)) {
            return Optional.of(x.value);
        }

        return Optional.empty();
    }

    // WRITING
    // =======

    @Override
    public void insert(final K desiredKey, final V desiredValue) {
        final Element[] update = createArrayOfElements();
        final Element x = lookup(desiredKey, update);

        if (x != null && x.key.equals(desiredKey)) {
            log.debug("Overriding value: {}", x);
            x.value = desiredValue;
            return;
        }

        final int randomItemLevel = getRandomLevel();
        topLevel = Math.max(topLevel, randomItemLevel);

        final Element newElement = new Element(desiredKey, desiredValue);

        for (int i = 0; i <= randomItemLevel; i++) {
            final Element insertAfter = update[i];
            newElement.forward[i] = insertAfter.forward[i];
            insertAfter.forward[i] = newElement;
            log.debug("Inserted new element {} after {}.", newElement, insertAfter);
        }
    }

    @Override
    public boolean delete(final K desiredKey) {
        final Element[] update = createArrayOfElements();
        final Element x = lookup(desiredKey, update);

        if (x == null || !x.key.equals(desiredKey)) {
            // not present - deletion not necessary

            log.debug("Not deleting - the key [{}] is not present.", desiredKey);
            return false;
        }

        // present - delete node by joining the list

        for (int i = 0; i <= topLevel; i++) {
            if (update[i].forward[i] == x) {
                // skip the node being removed
                update[i].forward[i] = x.forward[i];
            } else {
                // no need to continue further
                break;
            }
        }

        // lower the list level if necessary

        while (topLevel > 0 && header.forward[topLevel] == null) {
            log.debug("Lowering list level from {} to one less.", topLevel);
            topLevel--;
        }

        log.debug("Removed key [{}].", desiredKey);
        return true;
    }

    // HELPER METHODS
    // ==============

    /**
     * Important helper method for performing key lookup.
     * Besides the lookup it can also build an array of closest predecessors for each level.
     * If the target array is given as argument instead of NULL, it will result to this:
     * <ul>
     * <li>levels outside of range - initialized with header</li>
     * <li>levels in range - the closest element preceding the key looked up is stored</li>
     * </ul>
     * This array of predecessors can be used to simplify other operations.
     *
     * @param key                       key to lookup
     * @param closestPredecessorsTarget array of predecessors to update (must be of sufficient length)
     * @return best candidate found or NULL
     */
    private Element lookup(final K key, final Element[] closestPredecessorsTarget) {
        if (closestPredecessorsTarget != null) {
            // initialize predecessors with header

            for (int i = 0; i < maxNumberOfLevels; i++) {
                closestPredecessorsTarget[i] = header;
            }
        }

        log.debug("Looking up key [{}]...", key);
        Element x = header;

        for (int i = topLevel; i >= 0; i--) {
            // move forward through level as long as the keys are lower

            while (x.forward[i] != null && x.forward[i].key.compareTo(key) < 0) {
                x = x.forward[i];
            }

            if (closestPredecessorsTarget != null) {
                // store closest element on each level

                log.debug("Storing closest predecessor for level {}: {}", i, x);
                closestPredecessorsTarget[i] = x;
            }
        }

        // only the lowest-level successor can be the candidate for sure

        x = x.forward[0];
        log.debug("Candidate returned: {}", x);
        return x;
    }

    private int getRandomLevel() {
        int level = 0;

        while (Math.random() < P_LEVEL_SKIP && level < maxNumberOfLevels - 1) {
            level++;
        }

        return level;
    }

    @SuppressWarnings("unchecked")
    private Element[] createArrayOfElements() {
        return (Element[]) Array.newInstance(Element.class, maxNumberOfLevels);
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder(256);

        for (int i = 0; i <= topLevel; i++) {
            buffer.append("L");
            buffer.append(String.valueOf(i));
            buffer.append(": ");

            Element e = header.forward[i];

            while (e != null) {
                buffer.append(String.valueOf(e));
                buffer.append(",");

                e = e.forward[i];
            }

            buffer.append("<END>\n");
        }

        return buffer.toString();
    }

    /**
     * Generic element.
     */
    private class Element {
        private final Element[] forward;
        private final K key;
        private V value;

        private Element(final K key, final V value) {
            this.key = key;
            this.value = value;
            this.forward = createArrayOfElements();
        }

        @Override
        public String toString() {
            return String.format("{%s => %s}", key, value);
        }
    }

    /**
     * Special header element.
     */
    private class Header extends Element {
        private Header() {
            super(null, null);
        }

        @Override
        public String toString() {
            return "<HEADER>";
        }
    }
}
