package net.mattryall.smartquotes;

import java.util.List;

/**
 * Splits an HTML string into a list of tokens which are either tags or text.
 */
interface Tokeniser
{
    List<Token> tokenise(String value);
}
