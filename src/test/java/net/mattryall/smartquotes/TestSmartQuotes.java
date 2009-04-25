package net.mattryall.smartquotes;

import static net.mattryall.smartquotes.SmartQuotes.*;

import junit.framework.TestCase;

public class TestSmartQuotes extends TestCase
{
    private SmartQuotes smartQuotes;

    protected void setUp() throws Exception
    {
        super.setUp();
        smartQuotes = new SmartQuotes();
    }

    private String educate(String value)
    {
        return smartQuotes.educate(value);
    }

    public void testDoubleQuotes() throws Exception
    {
        assertEquals(OPEN_DOUBLE + "I am Sam" + CLOSE_DOUBLE, educate("\"I am Sam\""));
    }

    public void testSingleQuotes() throws Exception
    {
        assertEquals(OPEN_SINGLE + "I am Sam" + CLOSE_SINGLE, educate("'I am Sam'"));
    }

    public void testContractions() throws Exception
    {
        assertEquals("I don" + CLOSE_SINGLE + "t like it", educate("I don't like it"));
    }

    public void testNestedQuotes() throws Exception
    {
        assertEquals(OPEN_DOUBLE + OPEN_SINGLE + "Here I am" + CLOSE_SINGLE + ", said Sam" + CLOSE_DOUBLE,
            educate("\"'Here I am', said Sam\""));

        // document known dodgy behaviour -- double quotes nested inside singles aren't curled properly
        assertEquals(CLOSE_SINGLE + CLOSE_DOUBLE + "Here I am" + CLOSE_DOUBLE + ", said Sam" + CLOSE_SINGLE,
            educate("'\"Here I am\", said Sam'"));
    }

    public void testLeadingPunctuation() throws Exception
    {
        assertEquals(OPEN_DOUBLE + "...even better!" + CLOSE_DOUBLE, educate("\"...even better!\""));
    }

    public void testTrailingPunctuation() throws Exception
    {
        assertEquals("He said, " + OPEN_DOUBLE + "Done" + CLOSE_DOUBLE + ".",
            educate("He said, \"Done\"."));
        assertEquals("He said, " + OPEN_DOUBLE + "Done." + CLOSE_DOUBLE,
            educate("He said, \"Done.\""));
    }

    public void testLeadingNbsp() throws Exception
    {
        assertEquals("That" + CLOSE_SINGLE + "s&nbsp;" + OPEN_DOUBLE + "unusual" + CLOSE_DOUBLE + "&mdash;I think",
            educate("That's&nbsp;\"unusual\"&mdash;I think"));
        assertEquals("That" + CLOSE_SINGLE + "s&nbsp;" + OPEN_SINGLE + "unusual" + CLOSE_SINGLE + "&mdash;I think",
            educate("That's&nbsp;'unusual'&mdash;I think"));
    }

    public void testDecadeSpecialCase() throws Exception
    {
        assertEquals("Back to the " + OPEN_SINGLE + "80s", educate("Back to the '80s"));
    }

    public void testQuotesAroundTaggedExpression() throws Exception
    {
        assertEquals(OPEN_DOUBLE + "<b>No!</b>" + CLOSE_DOUBLE, educate("\"<b>No!</b>\""));
        assertEquals(OPEN_SINGLE + "<b>No!</b>" + CLOSE_SINGLE, educate("'<b>No!</b>'"));
    }

    public void testQuotesAroundTaggedExpressionSpecialCase() throws Exception
    {
        assertEquals("<em>Custer</em>" + CLOSE_SINGLE + "s last stand", educate("<em>Custer</em>'s last stand"));
    }

    public void testQuotesInsideTaggedExpression() throws Exception
    {
        assertEquals("<b>" + OPEN_DOUBLE + "No!" + CLOSE_DOUBLE + "</b>", educate("<b>\"No!\"</b>"));
        assertEquals("<cite>-" + OPEN_DOUBLE + "John" + CLOSE_DOUBLE + "</cite>", educate("<cite>-\"John\"</cite>"));
    }

    public void testEscaping() throws Exception
    {
        assertEquals("He was only 5&#39;7&#34; tall!", educate("He was only 5\\'7\\\" tall!"));
        assertEquals("C:&#92;WINDOWS&#92;", educate("C:\\\\WINDOWS\\\\"));
    }

    public void testPreformattedBlocksAreNotEducated() throws Exception
    {
        assertEquals("<pre>Some \"quotes\" in 'here'?</pre>", educate("<pre>Some \"quotes\" in 'here'?</pre>"));
        assertEquals("<pre>Some <notag>\"quotes\"</notag> in 'here'?</pre>",
            educate("<pre>Some <notag>\"quotes\"</notag> in 'here'?</pre>"));
        assertEquals("<pre>Some <kbd>\"quotes\"</kbd> in 'here'?</pre>",
            educate("<pre>Some <kbd>\"quotes\"</kbd> in 'here'?</pre>"));
    }
}
