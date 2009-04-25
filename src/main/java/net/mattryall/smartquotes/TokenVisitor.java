package net.mattryall.smartquotes;

interface TokenVisitor
{
    void visitText(TextToken token);
    void visitTag(TagToken token);
}
