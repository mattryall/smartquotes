package net.mattryall.smartquotes;

import static net.mattryall.smartquotes.StringUtils.join;
import junit.framework.TestCase;

public class TestStringUtils extends TestCase
{
    public void testJoin() throws Exception
    {
        assertEquals("", join("delim"));
        assertEquals("foo", join("delim", "foo"));
        assertEquals("foodelimbar", join("delim", "foo", "bar"));
    }
}
