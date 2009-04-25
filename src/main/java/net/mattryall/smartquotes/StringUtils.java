package net.mattryall.smartquotes;

import java.util.Collection;
import java.util.List;

final class StringUtils
{
    ///CLOVER:OFF
    // prevent instantiation of util class
    private StringUtils()
    {
    }
    ///CLOVER:ON

    public static String join(String delimiter, Collection<String> values)
    {
        return join(delimiter, values.toArray(new String[values.size()]));
    }

    public static String join(String delimiter, String... values)
    {
        if (values.length == 0) return "";

        int capacity = (values[0].length() + delimiter.length()) * values.length;
        StringBuffer result = new StringBuffer(capacity);
        for (int i = 0; i < values.length; i++)
        {
            if (i > 0)
                result.append(delimiter);
            result.append(values[i]);
        }
        return result.toString();
    }

    public static String join(List<String> values)
    {
        return join("", values);
    }
}
