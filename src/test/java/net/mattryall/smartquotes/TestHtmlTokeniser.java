package net.mattryall.smartquotes;

import junit.framework.TestCase;

import java.util.List;
import java.util.Iterator;
import static java.util.Collections.nCopies;

import static net.mattryall.smartquotes.StringUtils.join;

public class TestHtmlTokeniser extends TestCase
{
    private Tokeniser tokeniser = new HtmlTokeniser();

    public void testPlainText() throws Exception
    {
        List<Token> tokens = tokeniser.tokenise("Hello, world");
        assertEquals(new TextToken("Hello, world"), tokens.get(0));
    }

    public void testSingleTag() throws Exception
    {
        List<Token> tokens = tokeniser.tokenise("<b>Hello, world</b>");
        assertEquals(new TagToken("<b>"), tokens.get(0));
        assertEquals(new TextToken("Hello, world"), tokens.get(1));
        assertEquals(new TagToken("</b>"), tokens.get(2));
    }

    public void testContentAroundTag() throws Exception
    {
        Iterator<Token> tokens = tokeniser.tokenise("\"<b>Hello, world</b>\"").iterator();
        assertEquals(new TextToken("\""), tokens.next());
        assertEquals(new TagToken("<b>"), tokens.next());
        assertEquals(new TextToken("Hello, world"), tokens.next());
        assertEquals(new TagToken("</b>"), tokens.next());
        assertEquals(new TextToken("\""), tokens.next());
        assertTrue("should be no more tokens", !tokens.hasNext());
    }

    public void testNestedElements() throws Exception
    {
        Iterator<Token> tokens = tokeniser.tokenise("<p>This is some <b>formatted</b> <em>text</em></p>").iterator();
        assertEquals(new TagToken("<p>"), tokens.next());
        assertEquals(new TextToken("This is some "), tokens.next());
        assertEquals(new TagToken("<b>"), tokens.next());
        assertEquals(new TextToken("formatted"), tokens.next());
        assertEquals(new TagToken("</b>"), tokens.next());
        assertEquals(new TextToken(" "), tokens.next());
        assertEquals(new TagToken("<em>"), tokens.next());
        assertEquals(new TextToken("text"), tokens.next());
        assertEquals(new TagToken("</em>"), tokens.next());
        assertEquals(new TagToken("</p>"), tokens.next());
        assertTrue("should be no more tokens", !tokens.hasNext());
    }

    // Testing cases like <a href="<MTFoo>">
    public void testNestedTags() throws Exception
    {
        final int depth = 6;
        String nestedTags = join(nCopies(depth, "<img attr=\"")) + join(nCopies(depth, "\">"));
        Iterator<Token> tokens = tokeniser.tokenise(nestedTags).iterator();
        assertEquals(new TagToken(nestedTags), tokens.next());
        assertTrue("should be no more tokens", !tokens.hasNext());
    }

    public void testBeyondNestingLimit() throws Exception
    {
        final int depth = 6;
        String nestedTags = join(nCopies(depth, "<img attr=\"")) + join(nCopies(depth, "\">"));
        Iterator<Token> tokens = tokeniser.tokenise("<outer attr=\"" + nestedTags + "\">").iterator();
        assertEquals(new TextToken("<outer attr=\""), tokens.next());
        assertEquals(new TagToken(nestedTags), tokens.next());
        assertEquals(new TextToken("\">"), tokens.next());
        assertTrue("should be no more tokens", !tokens.hasNext());
    }
}
