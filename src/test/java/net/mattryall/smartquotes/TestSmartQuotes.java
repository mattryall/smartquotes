package net.mattryall.smartquotes;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Collection;
import java.net.URISyntaxException;

public class TestSmartQuotes extends TestCase
{
    public static Test suite() throws Exception
    {
        TestSuite result = new TestSuite();
        for (File file : getTestFiles())
        {
            String name = file.getName().replaceAll("\\..*?$", "");
            InputStream data = TestSmartQuotes.class.getResourceAsStream(file.getName());
            result.addTest(buildSuite(name, data));
        }
        return result;
    }

    @SuppressWarnings({"unchecked"})
    private static Collection<File> getTestFiles() throws URISyntaxException
    {
        File directory = new File(TestSmartQuotes.class.getResource("").toURI());
        return FileUtils.listFiles(directory, new String[] { "txt" }, false);
    }

    @SuppressWarnings({"unchecked"})
    private static TestSuite buildSuite(String name, InputStream file) throws IOException
    {
        TestSuite result = new TestSuite(name);
        String input = null;
        for (String line : (List<String>) IOUtils.readLines(file))
        {
            if (line.matches("^\\s*$") || line.matches("^\\s*#.*$")) continue;
            if (input == null)
            {
                input = line;
                continue;
            }
            result.addTest(new SmartQuotesTestCase(input, input, line));
            input = null;
        }
        return result;
    }

    private static class SmartQuotesTestCase extends TestCase
    {
        private final String name;
        private final String input;
        private final String expectedOutput;
        private final SmartQuotes smartQuotes;

        public SmartQuotesTestCase(String name, String input, String expectedOutput)
        {
            this.name = name;
            this.input = input;
            this.expectedOutput = expectedOutput;
            this.smartQuotes = new SmartQuotes();
        }

        public String getName()
        {
            return name;
        }

        public void runBare() throws Throwable
        {
            assertEquals(expectedOutput, smartQuotes.educate(input));
        }
    }
}
