package net.mattryall.smartquotes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.mattryall.smartquotes.StringUtils.join;

/**
 * Represents a tag in HTML, such as {@code <em>} or {@code </div>}.
 */
final class TagToken extends BaseToken
{
    private static final Pattern PRE_PATTERN = Pattern.compile("<(/?)(?:" +
        join("|", "pre", "code", "kbd", "samp", "script", "math") + ")[\\s>]");

    public TagToken(String value)
    {
        super(value);
    }

    /**
     * Returns <tt>true</tt> if this token is an opening tag of the several types
     * for preformatted text, such as 'pre', 'code' or 'script'.
     */
    public boolean isPreformattedOpenTag()
    {
        Matcher matcher = PRE_PATTERN.matcher(getValue());
        return matcher.find() && matcher.group(1).length() == 0;
    }

    /**
     * Returns <tt>true</tt> if this token is a tag of the several types for
     * preformatted text, such as 'pre', 'code' or 'script'.
     */
    public boolean isPreformattedTag()
    {
        Matcher matcher = PRE_PATTERN.matcher(getValue());
        return matcher.find();
    }

    public void accept(TokenVisitor visitor)
    {
        visitor.visitTag(this);
    }
}
