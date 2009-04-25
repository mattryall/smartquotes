package net.mattryall.smartquotes;

/**
 * Replaces straight quotes (" and ') with typographically correct quotes in HTML.
 * You can prevent replacement of specific quotes by escaping them with a
 * backslash, e.g. \".
 */
public final class SmartQuotes
{
    static final String OPEN_SINGLE = "&#8216;";
    static final String CLOSE_SINGLE = "&#8217;";
    static final String OPEN_DOUBLE = "&#8220;";
    static final String CLOSE_DOUBLE = "&#8221;";

    private final Tokeniser tokeniser;
    private final QuoteEducator educator;

    SmartQuotes(Tokeniser tokeniser, QuoteEducator educator)
    {
        this.tokeniser = tokeniser;
        this.educator = educator;
    }

    /**
     * Constructs a new instance with default configuration.
     */
    public SmartQuotes()
    {
        this(new HtmlTokeniser(), new HtmlQuoteEducator());
    }

    /**
     * Returns the value with straight quotes replaced with smart quotes. Quotes
     * which were escaped with a backslash are replaced with the corresponding
     * HTML entity.
     */
    public String educate(String value)
    {
        Visitor visitor = new Visitor(educator, value.length());
        for (Token token : tokeniser.tokenise(value))
        {
            token.accept(visitor);
        }
        return visitor.getResult();
    }

    /**
     * Manages the state of the token stream: whether we're currently
     * in a 'pre' block, what the previous token was, etc.
     */
    private static class Visitor implements TokenVisitor
    {
        private final StringBuffer result;
        private final QuoteEducator educator;

        private int preformattedDepth;
        private String lastCharacter = "";

        private Visitor(QuoteEducator educator, int length)
        {
            this.educator = educator;
            result = new StringBuffer(length * 2);
        }

        public void visitText(TextToken token)
        {
            result.append(preformattedDepth > 0 ? token.getValue() :
                educator.educate(token.getValue(), lastCharacter));
            lastCharacter = token.getLastCharacter();
        }

        public void visitTag(TagToken token)
        {
            if (token.isPreformattedTag())
                preformattedDepth += token.isPreformattedOpenTag() ? 1 : -1;
            result.append(token.getValue());
        }

        public String getResult()
        {
            return result.toString();
        }
    }
}
