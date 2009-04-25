package net.mattryall.smartquotes;

import static java.lang.Math.max;

final class TextToken extends BaseToken
{
    public TextToken(String value)
    {
        super(value);
    }

    public void accept(TokenVisitor visitor)
    {
        visitor.visitText(this);
    }

    public String getLastCharacter()
    {
        return getValue().substring(max(getValue().length() - 1, 0));
    }
}
