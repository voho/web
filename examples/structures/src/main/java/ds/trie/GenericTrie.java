package ds.trie;

public class GenericTrie implements Trie {
    private final Node root;

    public GenericTrie() {
        this.root = new Node(Character.MIN_VALUE, createNewCharacterToNodeMap());
    }

    @Override
    public boolean containsWord(final char[] key) {
        final Node found = lookup(key, false);
        return found != null && found.isWord();
    }

    @Override
    public boolean containsPrefix(final char[] keyPrefix) {
        final Node found = lookup(keyPrefix, false);
        return found != null;
    }

    @Override
    public int numWords(final char[] key) {
        final Node found = lookup(key, false);
        return found != null ? found.sizeWords() : 0;
    }

    @Override
    public int numPrefixes(final char[] key) {
        final Node found = lookup(key, false);
        return found != null ? found.sizePrefixes() : 0;
    }

    @Override
    public void addWord(final char[] key) {
        lookup(key, true).markAsWord();
    }

    private Node lookup(final char[] key, final boolean createNodes) {
        Node activeRoot = root;

        for (final char activeKeyFragment : key) {
            Node child = activeRoot.getChild(activeKeyFragment);

            if (child == null) {
                if (createNodes) {
                    // must create new child of the current node
                    child = new Node(activeKeyFragment, createNewCharacterToNodeMap());
                    activeRoot.putChild(child);
                } else {
                    // child is missing but we are not allowed to create it
                    return null;
                }
            }

            activeRoot = child;
        }

        return activeRoot;
    }

    private static CharacterToNodeMap createNewCharacterToNodeMap() {
        //return new CharacterToNodeHashMap('z' - 'a');
        return new CharacterToNodeArrayMap();
    }
}
