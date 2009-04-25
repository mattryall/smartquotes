package net.mattryall.smartquotes;

abstract class BaseToken implements Token
{
    private final String value;

    public BaseToken(String value)
    {
        this.value = value;
    }

    public final String getValue()
    {
        return value;
    }

    ///CLOVER:OFF
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseToken token = (BaseToken) o;
        return value != null ? value.equals(token.value) : token.value == null;
    }

    public int hashCode()
    {
        return value != null ? value.hashCode() : 0;
    }

    public String toString()
    {
        return getClass().getSimpleName() + "{value=\"" + value + "\"}";
    }
    ///CLOVER:ON
}
