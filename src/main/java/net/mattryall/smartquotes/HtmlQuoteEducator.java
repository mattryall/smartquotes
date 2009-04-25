package net.mattryall.smartquotes;

import static net.mattryall.smartquotes.SmartQuotes.*;

/**
 * The guts of the algorithm which finds and replaces dumb quotes with smart ones. 
 */
final class HtmlQuoteEducator implements QuoteEducator
{
    private static final String HTML_DASHES_PATTERN = "--|" + // dashes or
        "&[mn]dash;|" + // named entities or
        "&#8211;|&#8212;|" + // decimal entities or
        "&\\#x201[34];"; // hex entities
    private static final String HTML_SPACE_PATTERN = "\\s|" + // whitespace character or
        "&nbsp;"; // HTML non-breaking space entity
    private static final String CHARACTER_BEFORE_CLOSE_PATTERN = "[^ \\t\\r\\n\\[{(-]";

    /**
     * Returns the value with straight quotes replaced with smart quotes. Quotes
     * which were escaped with a backslash are replaced with the corresponding
     * HTML entity.
     *
     * @param value a text token from an HTML document, which shouldn't
     * include any tags
     * @param previousCharacter the last character in the previous text token,
     * or empty string if there was no previous character
     * @return the text with smart quotes replacing the straight ones
     */
    public String educate(String value, String previousCharacter)
    {
        // special case for lone double quote
        if (value.equals("\""))
            return previousCharacter.matches("\\S") ? CLOSE_DOUBLE : OPEN_DOUBLE;

        // special case for lone single quote
        if (value.equals("'"))
            return previousCharacter.matches("\\S") ? CLOSE_SINGLE : OPEN_SINGLE;

        return educateQuotes(processEscapes(value));
    }

    private String processEscapes(String value)
    {
        String result = value;
        result = result.replace("\\\\", "&#92;");
        result = result.replace("\\\"", "&#34;");
        result = result.replace("\\\'", "&#39;");
        return result.toString();
    }

    private String educateQuotes(String value)
    {
        ReplaceableString result = new ReplaceableString(value);

        // special case for double sets of quotes, e.g.:
        //     <p>He said, "'Quoted' words in a larger quote."</p>
        result.replace("\"'(?=\\w)", OPEN_DOUBLE + OPEN_SINGLE);
        result.replace("'\"(?=\\w)", CLOSE_SINGLE + CLOSE_DOUBLE);

        // get most opening single quotes
        result.replace("(" + HTML_SPACE_PATTERN + "|" + HTML_DASHES_PATTERN + ")'(?=\\w)",
            "$1" + OPEN_SINGLE);

        // closing single quotes
        result.replace(
            "(?<=" + CHARACTER_BEFORE_CLOSE_PATTERN + ")'|" + // quote preceded by a matching character, or
            "'(?=\\s|s\\b)", // followed by whitespace or an 's' at the end of a word
            CLOSE_SINGLE);

        // any remaining single quotes should be opening ones
        result.replace("'", OPEN_SINGLE);

        // get most opening double quotes
        result.replace("(" + HTML_SPACE_PATTERN + "|" + HTML_DASHES_PATTERN +")\"(?=\\w)",
            "$1" + OPEN_DOUBLE);

        // closing double quotes
        result.replace(
            "(?<=" + CHARACTER_BEFORE_CLOSE_PATTERN + ")\"|" + // quote preceded by a matching character, or
            "\"(?=\\s)", // followed by whitespace
            CLOSE_DOUBLE);

        // any remaining double quotes should be opening ones
        result.replace("\"", OPEN_DOUBLE);
        
        return result.toString();
    }

    private static class ReplaceableString
    {
        private String value;

        private ReplaceableString(String value)
        {
            this.value = value;
        }

        public void replace(String regex, String replacement)
        {
            value = value.replaceAll(regex, replacement);
        }

        public String toString()
        {
            return value;
        }
    }
}
