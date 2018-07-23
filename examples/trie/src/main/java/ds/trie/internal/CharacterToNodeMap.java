package ds.trie.internal;

public interface CharacterToNodeMap {
    Node get(char keyFragment);

    void put(char keyFragment, Node newNode);

    Iterable<Node> children();
}
