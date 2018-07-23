import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Utility class for encoding and decoding with LZ77 algorithm.
 */
public final class LZ77 {
    private static final Logger log = LoggerFactory.getLogger(LZ77.class);

    private LZ77() {
        // utility class
    }

    // ENCODING
    // ========

    public static List<LZ77Codeword> encode(final char[] input, final int backBufferSize, final int frontBufferSize) {
        final List<LZ77Codeword> result = new LinkedList<>();

        int middle = 0;
        int start = middle - backBufferSize;
        int end = middle + frontBufferSize;

        while (middle < input.length) {
            log.debug("Window (start,middle,end): ({},{},{})", start, middle, end);

            // prepare necessary structures to perform lookup
            final String backBuffer = safeSubString(input, start, middle);
            final String frontBuffer = safeSubString(input, middle, end);
            log.debug("Buffers: ({}|{})", backBuffer, frontBuffer);
            final int maxPrefixLength = frontBuffer.length();
            final String buffer = safeSubString(input, start, end);

            // initialize the encoded word
            int prefixIndex = 0;
            int prefixLength = 0;
            char prefixFollow = safeSubChar(input, middle);

            for (int i = 1; i <= maxPrefixLength; i++) {
                // create prefix of length "i"
                final String newPrefix = safeSubString(input, middle, middle + i);
                // find prefix in the whole window
                final int newPrefixIndex = buffer.indexOf(newPrefix);
                // check we have found it
                final boolean foundInWindow = newPrefixIndex != -1;
                // check it starts in the back buffer
                final boolean foundInBackBuffer = newPrefixIndex < backBuffer.length();

                if (foundInWindow && foundInBackBuffer) {
                    // replace the best found prefix with the new longer one
                    prefixIndex = backBuffer.length() - newPrefixIndex;
                    prefixLength = i;
                    prefixFollow = safeSubChar(input, middle + i);
                }
            }

            final int skip = prefixLength + 1;
            start += skip;
            middle += skip;
            end += skip;

            log.debug("New codeword: ({},{},{}) skip +{}", prefixIndex, prefixLength, prefixFollow, skip);
            result.add(new LZ77Codeword(prefixIndex, prefixLength, prefixFollow));
        }

        return result;
    }

    // DECODING
    // ========

    public static char[] decode(final List<LZ77Codeword> input) {
        final StringBuilder buffer = new StringBuilder();

        for (final LZ77Codeword word : input) {
            final int numCharsToGoBack = word.getI();
            final int numCharsToCopy = word.getJ();
            final char characterToAppend = word.getX();

            if (numCharsToCopy > 0) {
                final int firstCopyIndex = buffer.length() - numCharsToGoBack;
                log.debug("Copying {} char(s) from index {}.", numCharsToCopy, firstCopyIndex);

                for (int i = 0; i < numCharsToCopy; i++) {
                    final char charToCopy = buffer.charAt(firstCopyIndex + i);
                    log.debug("Copying: `{}`", charToCopy);
                    buffer.append(charToCopy);
                }
            }

            if (characterToAppend != 0) {
                log.debug("Appending: `{}`", characterToAppend);
                buffer.append(characterToAppend);
            }
        }

        return buffer.toString().toCharArray();
    }

    // UTILITY
    // =======

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
