package cz.voho.utility;

import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplacePatternCallback {
    private final Pattern pattern;

    public ReplacePatternCallback(final Pattern pattern) {
        this.pattern = pattern;
    }

    public String replace(final String value, final Function<MatchResult, String> matchReplacementCallback) {
        final Matcher matcher = pattern.matcher(value);
        String result = value;

        while (matcher.find()) {
            final MatchResult matchResult = matcher.toMatchResult();
            final String replacementPrefix = result.substring(0, matchResult.start());
            final String replacement = matchReplacementCallback.apply(matchResult);
            final String replacementSuffix = result.substring(matchResult.end());
            result = replacementPrefix + replacement + replacementSuffix;
            matcher.reset(result);
        }

        return result;
    }
}
