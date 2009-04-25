package net.mattryall.smartquotes;

import static net.mattryall.smartquotes.StringUtils.join;

import static java.util.Collections.nCopies;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regular-expression-based HTML tokeniser. Supports nested tags used by
 * HTML variants like MovableType templates, which can appear like this:
 * {@code <a href="<MTFoo>">}.
 */
final class HtmlTokeniser implements Tokeniser
{
    private static final int NESTED_TAGS_DEPTH = 6;
    private static final Pattern TOKEN_PATTERN = buildTokenPattern(NESTED_TAGS_DEPTH);

    private static Pattern buildTokenPattern(int depth)
    {
        String nestedTagsPattern = join("|", nCopies(depth, "(?:<(?:[^<>]")) +
            join("", nCopies(depth, ")*>)"));
        String commentPattern = "(?:<!(--.*?--\\s*)+>)";
        String processingInstructionPattern = "(?:<\\?.*?\\?>)";
        return Pattern.compile(nestedTagsPattern + "|" + commentPattern + "|" + processingInstructionPattern);
    }

    public List<Token> tokenise(String value)
    {
        List<Token> result = new ArrayList<Token>();
        int pos = 0;
        Matcher matcher = TOKEN_PATTERN.matcher(value);
        while (matcher.find())
        {
            if (pos < matcher.start())
                result.add(new TextToken(value.substring(pos, matcher.start())));
            result.add(new TagToken(matcher.group()));
            pos = matcher.end();
        }
        if (pos < value.length())
            result.add(new TextToken(value.substring(pos)));
        return result;
    }
}
