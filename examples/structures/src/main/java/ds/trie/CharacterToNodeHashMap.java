package ds.trie;

import java.util.HashMap;
import java.util.Map;

public class CharacterToNodeHashMap implements CharacterToNodeMap {
    private final Map<Character, Node> nodes;

    public CharacterToNodeHashMap(final int expectedNumberOfChildren) {
        this.nodes = new HashMap<>(expectedNumberOfChildren);
    }

    @Override
    public Node get(final char keyFragment) {
        return nodes.get(keyFragment);
    }

    @Override
    public void put(final char keyFragment, final Node newNode) {
        nodes.put(keyFragment, newNode);
    }

    @Override
    public Iterable<Node> children() {
        return nodes.values();
    }
}
