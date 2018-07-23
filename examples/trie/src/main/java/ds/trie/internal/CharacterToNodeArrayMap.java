package ds.trie.internal;

import java.util.Arrays;
import java.util.Objects;

public class CharacterToNodeArrayMap implements CharacterToNodeMap {
    private final Node[] array;

    public CharacterToNodeArrayMap() {
        array = new Node['z' - 'a'];
    }

    @Override
    public Node get(final char keyFragment) {
        return array[charToIndex(keyFragment)];
    }

    @Override
    public void put(final char keyFragment, final Node newNode) {
        array[charToIndex(keyFragment)] = newNode;
    }

    @Override
    public Iterable<Node> children() {
        return () -> Arrays.stream(array).filter(Objects::nonNull).iterator();
    }

    private int charToIndex(final char keyFragment) {
        final int index = keyFragment - 'a';

        if (index < 0 || index >= array.length) {
            throw new IllegalArgumentException("Only supports lower case ASCII characters. Not this: " + keyFragment);
        }

        return index;
    }
}
