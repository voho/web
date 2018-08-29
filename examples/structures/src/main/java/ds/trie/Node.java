package ds.trie;

public class Node {
    final char keyFragment;
    final CharacterToNodeMap children;
    private boolean isWord;

    public Node(final char keyFragment, final CharacterToNodeMap characterToNodeMap) {
        this.keyFragment = keyFragment;
        this.children = characterToNodeMap;
        this.isWord = false;
    }

    public boolean isWord() {
        return isWord;
    }

    public void markAsWord() {
        this.isWord = true;
    }

    public void putChild(final Node node) {
        this.children.put(node.keyFragment, node);
    }

    public Node getChild(final char keyFragment) {
        return this.children.get(keyFragment);
    }

    public int sizeWords() {
        int sum = 0;

        if (isWord()) {
            sum++;
        }

        for (final Node child : children.children()) {
            sum += child.sizeWords();
        }

        return sum;
    }

    public int sizePrefixes() {
        int sum = 1;

        for (final Node child : children.children()) {
            sum += child.sizePrefixes();
        }

        return sum;
    }
}
