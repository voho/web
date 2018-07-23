import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Utility class for encoding and decoding with LZ77 algorithm.
 */
public final class LZ78 {
    private static final Logger log = LoggerFactory.getLogger(LZ78.class);

    private LZ78() {
        // utility class
    }

    // ENCODING
    // ========

    public static List<LZ78Codeword> encode(final char[] input) {
        final List<LZ78Codeword> result = new LinkedList<>();
        int position = 0;
        final Tree tree = new Tree();

        while (position <= input.length - 1) {
            log.debug("Encoding from position: {}", position);
            Node bestLeafSoFar = tree.root;
            int longestPrefixSoFar = 0;

            // try to find prefixes with increasing length
            for (int prefixEnd = position + 1; prefixEnd <= input.length; prefixEnd++) {
                final String prefix = safeSubString(input, position, prefixEnd);
                assert prefix.length() >= 1;
                log.debug("Trying to find prefix in dictionary: {} (length = {})", prefix, prefix.length());
                final char prefixChar = prefix.charAt(longestPrefixSoFar);

                if (bestLeafSoFar.hasChild(prefixChar)) {
                    bestLeafSoFar = bestLeafSoFar.getChild(prefixChar);
                    longestPrefixSoFar = prefix.length();
                } else {
                    break;
                }
            }

            // we have the longest prefix
            final char firstTerminalAfterPrefix = safeSubChar(input, position + longestPrefixSoFar);
            log.debug("Longest prefix found: (length = {}, start = {})", longestPrefixSoFar, bestLeafSoFar.id);
            log.debug("Extending node {} with {}.", bestLeafSoFar.id, firstTerminalAfterPrefix);
            tree.extend(bestLeafSoFar, firstTerminalAfterPrefix);
            final LZ78Codeword newCodeword = new LZ78Codeword(bestLeafSoFar.id, firstTerminalAfterPrefix);
            log.debug("Adding codeword: {}", newCodeword);
            result.add(newCodeword);
            position += longestPrefixSoFar + 1;
        }

        return result;
    }

    // DECODING
    // ========

    public static char[] decode(final List<LZ78Codeword> input) {
        // table of words
        final List<String> table = new LinkedList<>();
        // output string buffer
        final StringBuilder buffer = new StringBuilder();

        for (final LZ78Codeword codeword : input) {
            final String wordToAdd;

            if (codeword.getI() == 0) {
                // non-existing word (new symbol)
                log.debug("Appending non-existing word: {}", codeword.getX());
                wordToAdd = String.valueOf(codeword.getX());
            } else {
                final String wordFromTable = table.get(codeword.getI() - 1);

                if (codeword.getX() != 0) {
                    // existing word + terminal
                    log.debug("Appending existing word: {} + terminal `{}`", wordFromTable, codeword.getX());
                    wordToAdd = wordFromTable + codeword.getX();
                } else {
                    // existing word only
                    log.debug("Appending existing word: {}", wordFromTable);
                    wordToAdd = wordFromTable;
                }
            }

            // append the word to output
            buffer.append(wordToAdd);
            // extend the table of words
            table.add(wordToAdd);
            log.debug("Table of words extended: {}", wordToAdd);
        }

        return buffer.toString().toCharArray();
    }

    // UTILITY
    // =======

    private static class Tree {
        int counter = 0;
        final Node root = new Node(counter++);

        void extend(final Node parent, final char terminal) {
            parent.addChild(counter++, terminal);
        }
    }

    private static class Node {
        final int id;
        final Map<Character, Node> children;

        Node(final int id) {
            this.id = id;
            this.children = new LinkedHashMap<>();
        }

        void addChild(final int newId, final char newTerminal) {
            assert !children.containsKey(newTerminal);
            children.put(newTerminal, new Node(newId));
        }

        Node getChild(final char terminal) {
            return children.get(terminal);
        }

        boolean hasChild(final char terminal) {
            return children.containsKey(terminal);
        }
    }

    private static char safeSubChar(final char[] input, final int index) {
        if (index >= 0 && index <= input.length - 1) {
            return input[index];
        }

        return 0;
    }

    private static String safeSubString(final char[] input, final int start, final int end) {
        final StringBuilder buffer = new StringBuilder();

        for (int i = start; i < end; i++) {
            if (i >= 0 && i <= input.length - 1) {
                buffer.append(input[i]);
            }
        }

        return buffer.toString();
    }
}
