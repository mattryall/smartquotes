package net.mattryall.smartquotes;

import junit.framework.TestCase;

public class TestTagToken extends TestCase
{
    public void testIsPreformattedTag() throws Exception
    {
        String[] preTags = { "<pre>", "</pre>", "<pre class=\"code\">",
            "<code id=foo>", "</samp>", "<kbd>", "<script>", "<math>" };
        String[] nonPreTags = { "<a>", "</pref>", "<div class=\"pre\">",
            "<a code=foo>", "</kb>", "<img>", "<body>", "<meta>" };
        for (String tag : preTags)
        {
            assertTrue(tag + " should be preformatted",
                new TagToken(tag).isPreformattedTag());
        }
        for (String tag : nonPreTags)
        {
            assertFalse(tag + " should not be preformatted",
                new TagToken(tag).isPreformattedTag());
        }
    }

    public void testIsPreformattedOpenTag() throws Exception
    {
        String[] preOpenTags = { "<pre>", "<pre class=\"code\">",
            "<code id=foo>", "<kbd>", "<script>", "<math>" };
        String[] nonPreOpenTags = { "</pre>", "</samp>", "<a>", "</pref>",
            "<div class=\"pre\">", "<a code=foo>", "</kb>", "<img>",
            "<body>", "<meta>" };
        for (String tag : preOpenTags)
        {
            assertTrue(tag + " should be preformatted open tag",
                new TagToken(tag).isPreformattedOpenTag());
        }
        for (String tag : nonPreOpenTags)
        {
            assertFalse(tag + " should not be preformatted open tag",
                new TagToken(tag).isPreformattedOpenTag());
        }
    }
}
