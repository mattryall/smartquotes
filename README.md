> **Note**
This project is not actively maintained and replicates most of the behaviour and limitations of SmartyPants. If you want a more full-featured Java smart quotes library, check out [KeenQuotes](https://github.com/DaveJarvis/KeenQuotes).

Smart Quotes converts straight ASCII quotes in HTML content to their typographically-correct equivalents.

For example, it converts

> "She's a ripper, mate!"

into

> “She’s a ripper, mate!”

It is an easy-to-use Java library with no dependencies other than Java 1.5 or higher. To correct quotes in your application which produces HTML, you can simply drop the JAR into your classpath and use it this way:

    SmartQuotes smartQuotes = new SmartQuotes();
    String educated = smartQuotes.educate("...");

### Downloads ###

<table>
<tr><th>Version</th><th>Date</th><th>Download</th></tr>
<tr><td>1.0</td><td>25 April 2009</td><td><a href="https://github.com/downloads/mattryall/smartquotes/smartquotes-1.0.jar">smartquotes-1.0.jar</a></td></tr>
</table>

### Related projects ###

The [Smart Quotes Confluence plugin](http://labs.atlassian.com/wiki/display/QUOT/Confluence+Smart+Quotes+Plugin) uses this library to render smart quotes in the Confluence wiki.

### Author ###

**Matt Ryall**  
[smartquotes@mattryall.net](mailto:smartquotes@mattryall.net)  
[http://mattryall.net](http://mattryall.net)

### Credits ###

John Gruber's [SmartyPants](http://daringfireball.net/projects/smartypants/) was the inspiration for this project and provided a lot of guidance with the algorithm and regular expressions.

